// ECE:3350 SISC processor project
// main SISC module, part 1

`timescale 1ns/100ps  

module sisc (clk, rst_f, ir);

	input clk, rst_f;

// declare all internal wires here
	wire [31:0] mux32out;	   // mux32 output
	wire [31:0] rsa;	       // RSA line running between the register file and ALU
	wire [31:0] rsb;	       // RSB line running between the register file and ALU
	wire [31:0] alu_result;	   // output of the ALU that feeds into the zero input of mux32
	wire [31:0] ir;
	wire [31:0] im_out;

	wire [15:0] pc_out;
	wire [15:0] br_addr;

	wire [3:0] mux4out;	       // mux4 output
	wire [3:0] stat;	       // status output from the ALU that feeds into the status register
	wire [3:0] srout;	       // output of the status register that feeds into ctrl

	wire [1:0] alu_op;	       // ALU control signal

	wire rf_we;		           // register file write enable control signal
	wire stat_en;		       // the status register enabling control signal
	wire wb_sel;		       // select signal for mux32; decides whether to write back zero or the ALU output
	wire br_sel;
	wire pc_sel;
	wire pc_write;
	wire pc_rst;
	wire ir_load;
	wire rb_sel;

// component instantiation goes here
	ctrl cu (clk, rst_f, ir[31:28], ir[27:24], srout, rf_we, alu_op, wb_sel, br_sel, pc_rst, pc_write, pc_sel, rb_sel, ir_load);
	mux4 m4 (ir[15:12], ir[23:20], rb_sel, mux4out);
	rf regFile (clk, ir[19:16], mux4out, ir[23:20], mux32out, rf_we, rsa, rsb);
	alu a1 (clk, rsa, rsb, ir[15:0], alu_op, alu_result, stat, stat_en);
	mux32 m32 (alu_result, 0, wb_sel, mux32out);
	statreg sr (clk, stat, stat_en, srout);
	  
	br branchLogic (pc_out, ir[15:0], br_sel, br_addr);
	pc progCount (clk, br_addr, pc_sel, pc_write, pc_rst, pc_out);
	im instrMem (pc_out, im_out);
	ir instrReg (clk, ir_load, im_out, ir);

	initial
  
// put a $monitor statement here.  
	$monitor("IR=%h \nR1=%h \nR2=%h \nR3=%h \nR4=%h \nR5=%h \nALU_OP=%h \nWB_SEL=%h \nRF_WE=%h \nSTAT=%h \n",ir,regFile.ram_array[1],regFile.ram_array[2],regFile.ram_array[3],regFile.ram_array[4],regFile.ram_array[5],alu_op,wb_sel,rf_we,stat);


endmodule


