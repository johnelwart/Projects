library ieee;

use work.basic_gates.all;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


-- Project entity
entity xs3decode is
	port {
	x3, x2, x1, x0 : in std_logic;
	b3, b2, b1, b0 : out std_logic
	};

end xs3decode;


-- AND gate
entity AND2_OP is 
	port (
		A, B: in STD_LOGIC;
		Z: out STD_LOGIC );
		
end AND2_OP;

architecture BASIC_ARCH of AND2_OP is

begin
	Z <= A and B;
	
end BASIC_ARCH; 


-- OR gate
entity OR2_OP is 
	port (
		A, B: in STD_LOGIC;
		Z: out STD_LOGIC );
		
end OR2_OP;

architecture BASIC_ARCH of OR2_OP is

begin
	Z <= A or B;
	
end BASIC_ARCH; 


-- NOR gate
entity NOR2_OP is 
	port (
		A, B: in STD_LOGIC;
		Z: out STD_LOGIC );
		
end NOR2_OP;

architecture BASIC_ARCH of NOR2_OP is

begin
	Z <= not (A or B);
	
end BASIC_ARCH;


-- Structural architecture
 architecture structure of xs3decode is
 
	-- NOR IC
	component NOR2_OP is
		port ( 
			A, B: in STD_LOGIC; 
			Z: out STD_LOGIC );
			
	end component;


	-- AND IC
	component AND2_OP is
		port ( 
			A, B: in STD_LOGIC; 
			Z: out STD_LOGIC );
			
	end component;


	-- OR IC
	component OR2_OP is
		port ( 
			A, B: in STD_LOGIC; 
			Z: out STD_LOGIC );
			
	end component;


	-- Internal Signals
	Signal Wire_0 : std_logic;
	Signal Wire_1 : std_logic;
	Signal Wire_2 : std_logic;
	Signal Wire_3 : std_logic;
	Signal Wire_4 : std_logic;
	Signal Wire_5 : std_logic;
	Signal Wire_6 : std_logic;
	Signal Wire_7 : std_logic;
	Signal Wire_8 : std_logic;
	Signal Wire_9 : std_logic;
	Signal Wire_10 : std_logic;
	Signal Wire_11 : std_logic;


begin

	-- Start of b3 Circuit
	U2A: AND2_OP port map (x3, x2, Wire_0);         -- AB

	U2B: AND2_OP port map (x3, x1, Wire_1);         -- AC
	U2C: AND2_OP port map (Wire_1, D, Wire_2);      -- ACD

	U3A: OR2_OP port map (Wire_0, Wire_2, b3);      -- AB + ACD


	-- Start of b2 Circuit
	U1A: NOR2_OP port map (x2, x1, Wire_3);         -- B'C'

	U1B: NOR2_OP port map (x2, x0, Wire_4);         -- B'D'

	U2D: AND2_OP port map (x2, x1, Wire_5);         -- BC
	U4A: AND2_OP port map (x0, Wire_5, Wire_6);     -- BCD

	U3B: OR2_OP port map (Wire_4, Wire_6, Wire_7);  -- B'D' + BCD

	U3C: OR2_OP port map (Wire_3, Wire_7, b2);      -- B'C' + B'D' + BCD


	-- Start of b1 Circuit
	U1C: NOR2_OP port map (x1, x1, Wire_8);         -- C'
	
	U1D: NOR2_OP port map (x0, x0, Wire_9);           -- D'

	U4B: AND2_OP port map (Wire_8, x0, Wire_10);    -- C'D
	
	U4C: AND2_OP port map (Wire_9, x1, Wire_11);    -- CD'

	U3D: OR2_OP port map (Wire_10, Wire_11, b1);    -- C'D + CD'


	-- Start of b0 Circuit
	b0 <= Wire_9;                                   -- D'
	
end structure;