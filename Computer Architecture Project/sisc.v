// ECE:3350 SISC processor project
// main SISC module, part 1

`timescale 1ns/100ps  

module sisc (clk, rst_f);

	input clk, rst_f;

// declare all internal wires here

	// 32 bit wires
	wire [31:0] mux32out;	   // Mux32 output
	wire [31:0] rsa;	       // RSA line running between the register file and ALU
	wire [31:0] rsb;	       // RSB line running between the register file and ALU
	wire [31:0] alu_result;	   // Output of the ALU that feeds into the zero input of mux32
	wire [31:0] ir;            // Instruction output from the instruction register
	wire [31:0] im_out;        // The instruction at the address provided by Read_Addr
	wire [31:0] dm_out;
	
	// 16 bit wires
	wire [15:0] pc_out;        // Current value of the program counter for im and br
	wire [15:0] br_addr;       // Computed address passed to the program counter
	wire [15:0] mux16out;
	
	// 4 bit wires
	wire [3:0] mux4out;	       // Mux4 output
	wire [3:0] stat;	       // Status output from the ALU that feeds into the status register
	wire [3:0] srout;	       // Output of the status register that feeds into ctrl
	
	// 2 bit wire
	wire [1:0] alu_op;	       // ALU control signal
	
	// 1 bit wires
	wire rf_we;		           // Register file write enable control signal
	wire stat_en;		       // The status register enabling control signal
	wire wb_sel;		       // Select signal for mux32; decides whether to write back zero or the ALU output
	wire br_sel;               // Controls whether to add the immediate to PC + 1 or 0
	wire pc_sel;               // Tells the pc to either save the branch or increment the pc
	wire pc_write;             // Saves the selected value to pc_out until it goes high again
	wire pc_rst;               // Reset for the program counter
	wire ir_load;              // When set to 1, IR is loaded with data from IM
	wire rb_sel;               // Chooses input in the mux
	wire mm_sel;
	wire dm_we;

// component instantiation goes here

	// Part 1 components
	ctrl cu (clk, rst_f, ir[31:28], ir[27:24], srout, rf_we, alu_op, wb_sel, br_sel, pc_rst, pc_write, pc_sel, rb_sel, ir_load);
	mux4 m4 (ir[15:12], ir[23:20], rb_sel, mux4out);
	rf regFile (clk, ir[19:16], mux4out, ir[23:20], mux32out, rf_we, rsa, rsb);
	alu a1 (clk, rsa, rsb, ir[15:0], alu_op, alu_result, stat, stat_en);
	mux32 m32 (alu_result, dm_out, wb_sel, mux32out);
	statreg sr (clk, stat, stat_en, srout);
	
	// Part 2 components
	br branchLogic (pc_out, ir[15:0], br_sel, br_addr);
	pc progCount (clk, br_addr, pc_sel, pc_write, pc_rst, pc_out);
	im instrMem (pc_out, im_out);
	ir instrReg (clk, ir_load, im_out, ir);
	
	// Part 3 components
	dm d1 (mux16out, mux16out, rsb, dm_we, dm_out);
	mux16 m16 (alu_result, ir[15:0], mux16out);

	initial
  
// put a $monitor statement here.  
	$monitor("IR=%h \nPC=%h \nR1=%h \nR2=%h \nR3=%h \nR4=%h \nR5=%h \nALU_OP=%h \nBR_SEL=%h \nPC_WRITE=%h \nPC_SEL=%h \n",ir,pc_out,regFile.ram_array[1],regFile.ram_array[2],regFile.ram_array[3],regFile.ram_array[4],regFile.ram_array[5],alu_op,br_sel,pc_write,pc_sel);


endmodule


