-- Copyright (C) 2020  Intel Corporation. All rights reserved.
-- Your use of Intel Corporation's design tools, logic functions 
-- and other software and tools, and any partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Intel Program License 
-- Subscription Agreement, the Intel Quartus Prime License Agreement,
-- the Intel FPGA IP License Agreement, or other applicable license
-- agreement, including, without limitation, that your use is for
-- the sole purpose of programming logic devices manufactured by
-- Intel and sold by Intel or its authorized distributors.  Please
-- refer to the applicable agreement for further details, at
-- https://fpgasoftware.intel.com/eula.

-- PROGRAM		"Quartus Prime"
-- VERSION		"Version 20.1.1 Build 720 11/11/2020 SJ Lite Edition"
-- CREATED		"Tue Oct 05 12:31:33 2021"

LIBRARY ieee;
USE ieee.std_logic_1164.all; 

LIBRARY work;

ENTITY exp05 IS 
	PORT
	(
		A :  IN  STD_LOGIC;
		B :  IN  STD_LOGIC;
		C :  IN  STD_LOGIC;
		Z :  OUT  STD_LOGIC;
		Y :  OUT  STD_LOGIC
	);
END exp05;

ARCHITECTURE bdf_type OF exp05 IS 

SIGNAL	SYNTHESIZED_WIRE_0 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_1 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_10 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_11 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_6 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_7 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_8 :  STD_LOGIC;
SIGNAL	SYNTHESIZED_WIRE_9 :  STD_LOGIC;


BEGIN 



SYNTHESIZED_WIRE_9 <= NOT(SYNTHESIZED_WIRE_0 OR SYNTHESIZED_WIRE_1);


SYNTHESIZED_WIRE_8 <= C OR SYNTHESIZED_WIRE_10;


SYNTHESIZED_WIRE_6 <= NOT(SYNTHESIZED_WIRE_10 AND SYNTHESIZED_WIRE_11);


SYNTHESIZED_WIRE_7 <= NOT(C AND SYNTHESIZED_WIRE_11);


SYNTHESIZED_WIRE_11 <= NOT(A);



SYNTHESIZED_WIRE_10 <= NOT(B);



SYNTHESIZED_WIRE_1 <= NOT(SYNTHESIZED_WIRE_6);



SYNTHESIZED_WIRE_0 <= NOT(SYNTHESIZED_WIRE_7);



Y <= NOT(SYNTHESIZED_WIRE_8);



Z <= NOT(SYNTHESIZED_WIRE_9);



END bdf_type;