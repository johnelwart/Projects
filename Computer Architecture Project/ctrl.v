// ECE:3350 SISC computer project
// finite state machine

`timescale 1ns/100ps

module ctrl (clk, rst_f, opcode, mm, stat, rf_we, alu_op, wb_sel, br_sel, pc_rst, pc_write, pc_sel, rb_sel, ir_load, mm_sel, dm_we);

	/* Declare the ports listed above as inputs or outputs.  Note that
       you will add signals for parts 2, 3, and 4. */
    input clk, rst_f;
    input [3:0] opcode, mm, stat;
    output reg rf_we, wb_sel, rb_sel, br_sel, pc_rst, pc_write, pc_sel, ir_load, mm_sel, dm_we;
    output reg [1:0] alu_op;
  
	// state parameter declarations
	parameter start0 = 0, start1 = 1, fetch = 2, decode = 3, execute = 4, mem = 5, writeback = 6;
   
	// opcode paramenter declarations
	parameter NOOP = 0, LOD = 1, STR = 2, SWP = 3, BRA = 4, BRR = 5, BNE = 6, BNR = 7, ALU_OP = 8, HLT = 15;

	// addressing modes
	parameter AM_IMM = 8;

	// state register and next state value
	reg [2:0]  present_state, next_state;
	
	// Initialize present state to 'start0'.
	initial
		present_state = start0;

    /* Clock procedure that progresses the fsm to the next state on the positive 
    edge of the clock, OR resets the state to 'start1' on the negative edge
    of rst_f. Notice that the computer is reset when rst_f is low, not high. */
	always @(posedge clk, negedge rst_f)
	begin
		if (rst_f == 1'b0)
			present_state <= start1;
		else
			present_state <= next_state;
	end
  
	/* Combinational procedure that determines the next state of the fsm. */
	always @(present_state, rst_f)
	begin
		case(present_state)
			start0:
				next_state = start1;
				
			start1:
				if (rst_f == 1'b0) 
					next_state = start1;
				else
					next_state = fetch;
					
			fetch:
				next_state = decode;
				
			decode:
				next_state = execute;
				
			execute:
				next_state = mem;
				
			mem:
				next_state = writeback;
				
			writeback:
				next_state = fetch;
				
			default:
				next_state = start1;
				
		endcase
	end
  
	/* TODO: Generate the rf_we, alu_op, wb_sel outputs based on the FSM states 
    and inputs. For Parts 2, 3 you will add the new control signals here. 
    Note that the following procedure block is defining combinational logic. */
	always @(present_state, opcode, mm)
	begin

    // Put your default assignments for wb_sel, rf_we, and alu_op here.
    wb_sel <= 1'b0;
    rf_we <= 1'b0;
    alu_op <= 2'b10;
	
    // Added default assignments for br_sel, pc_rst, pc_write, pc_sel, ir_load, rb_sel.
    br_sel <= 1'b0;
    pc_rst <= 1'b0;
    pc_write <= 1'b0;
    pc_sel <= 1'b0;
    ir_load <= 1'b0;
    rb_sel <= 1'b0;
	
	// New default assignments for dm_we and mm_sel
	dm_we <= 1'b0;
	mm_sel <= 1'b0;
	
		case(present_state)
			start1:
			begin
				pc_rst <= 1'b1;
			end

			fetch:
			begin
				pc_write <= 1;		           // increment pc
				ir_load <= 1;		           // load ir
			end

			decode:
			begin

				pc_write <= 0;
				ir_load <= 0;

				if(opcode == BRA)
				begin
					if((mm & stat) != 0)
					begin
						pc_write <= 1'b1;
						pc_sel <= 1'b1;	       // save branch address to PC
						br_sel <= 1'b1;	       // absolute branch, add offset to 0
					end
			
					else
					begin
						pc_sel <= 1'b0;
					end
				end

				else if(opcode == BRR)
				begin
					if((mm & stat) != 0)
					begin
						pc_write <= 1'b1;
						pc_sel <= 1'b1;	       // save branch address to PC
						br_sel <= 1'b0;	       // relative branch (add offset to PC+1)
					end
			
					else
					begin
						pc_sel <= 1'b0;
					end
				end

				else if(opcode == BNE)
				begin 
					if((mm & stat) == 0)
					begin
						pc_write <= 1'b1;
						pc_sel <= 1'b1;	       // save branch address to PC
						br_sel <= 1'b1;	       // absolute branch, add offset to 0
					end
			 
					else
					begin
						pc_sel <= 1'b0;
					end
				end

				else if(opcode == BNR)
				begin
					if((mm & stat) == 0)
					begin
						pc_write <= 1'b1;
						pc_sel <= 1'b1;	       // save branch address to PC
						br_sel <= 1'b0;	       // relative branch (add offset to PC+1)
					end
			  
					else
					begin
						pc_sel <= 1'b0;
					end
				end
				
				else if(opcode == STR)
				begin
					rb_sel <= 1'b1;
				end
			end
		
			execute:
			begin

			/* Make assignments to alu_op based upon the opcode and mm values. 
			Note that the condition code is updated during the execute stage 
			only. */

				// all op codes are hex 8 for part 1
				if (opcode == ALU_OP)
				begin
					// all instructions for part one except addi have mm of 0000; we need to generate 00 as alu_op
					if (mm == 4'b0000)
						alu_op <= 2'b00;
					// if mm is 1000, it's an add immediate so we need to generate 01 as alu_up
					else if (mm == 4'b1000)
						alu_op <= 2'b01;
						
				end
				
				else if(opcode == STR)
				begin
					if(mm == 4'b0000)
					begin
						rb_sel <= 1'b1;
						alu_op <= 2'b01;
					end
					
					else if(mm == 4'b1000)
					begin
						rb_sel <= 1'b1;
						alu_op <= 2'b01;
					end
				end
				
				else if(opcode == LOD)
				begin
					if (mm == 4'b0000)
					begin
						alu_op <= 2'b01;
						mm_sel <= 1'b1;
					end
					
					else if (mm == 4'b1000)
					begin
						alu_op <= 2'b01;
					end
				end
			end

			mem:
			begin
			/* Make assignments to alu_op based upon the opcode and mm values. 
			Note that the ALU operation must be valid at the end of the mem 
			stage so the correct value can be written to the register file. */
			
			// all instructions for part one except addi have mm of 0000; we need to generate 00 as alu_op

				// all op codes are hex 8 for part 1
				if (opcode == ALU_OP)
				begin
					// all instructions for part one except addi have mm of 0000; we need to generate 00 as alu_op
					if (mm == 4'b0000)
						alu_op <= 2'b10;
					// if mm is 1000, it's an add immediate so we need to generate 01 as alu_up
					else if (mm == 4'b1000)
						alu_op <= 2'b11;
						
				end
				
				else if (opcode == STR)
				begin
					if(mm == 4'b0000)
					begin
						rb_sel <= 1'b1;
						alu_op <= 2'b01;
						dm_we <= 1'b1;
					end
					
					else if(mm == 4'b1000)
					begin
						rb_sel <= 1'b1;
						alu_op <= 2'b01;
						dm_we <= 1'b1;
					end
				end
				
				else if(opcode == LOD)
				begin
					if (mm == 4'b0000)
					begin
						alu_op <= 2'b01;
						mm_sel <= 1'b1;
					end
					
					else if (mm == 4'b1000)
					begin
						alu_op <= 1'b1;
					end
				end
			end

			writeback:
			begin
				/* Make the assignment to rf_we here conditional on the opcode = 
				ALU_OP.  We don't want to write to the register file if the 
				instruction is a NOOP. */
				if (opcode == ALU_OP) 
					rf_we <= 1;
					
				else if (opcode == LOD)
				begin
					rf_we <= 1'b1;
					wb_sel <= 1'b1;
				end
			end

			default:
			begin
				/* Put your default assignments for wb_sel, rf_we, and alu_op here.  */
				wb_sel <= 1'b0;
				rf_we <= 1'b0;
				alu_op <= 2'b10;

				br_sel <= 1'b0;
				pc_rst <= 1'b0;
				pc_write <= 1'b0;
				pc_sel <= 1'b0;
				ir_load <= 1'b0;
				rb_sel <= 1'b0;
				
				dm_we <= 1'b0;
				mm_sel <= 1'b0;

			end
		endcase
	end
  
	// Halt on HLT instruction
	always @(opcode)
	begin
		if (opcode == HLT)
		begin 
			#5 $display ("Halt."); //Delay 5 ns so $monitor will print the halt instruction
			$stop;
		end
	end
endmodule