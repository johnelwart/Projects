library ieee;

use work.basic_gates.all;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity xs3decode is
	port {
	A, B, C, D ; in std_logic;
	b3, b2, b1, b0 ; out std_logic;
	};

end xs3decode;


architecture struct of xs3decode is
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

	Wire_0 <= A and B;           -- AB

	Wire_1 <= A and C;           --AC
	Wire_2 <= Wire_1 and D;      -- ACD

	b3 <= Wire_0 or Wire_2;      -- AB + ACD

-- End of b3 Circuit


-- Start of b2 Circuit

	Wire_3 <= B nor C;           -- B'C'

	Wire_4 <= B nor D;           -- B'D'

	Wire_5 <= B and C;           -- BC
	Wire_6 <= Wire_5 and D;      -- BCD

	Wire_7 <= Wire_4 or Wire_6;  -- B'D' + BCD
	b2 <= Wire_3 or Wire_7;      -- B'C' + B'D' + BCD

-- End of b2 Circuit


-- Start of b1 Circuit

	Wire_8 <= C nor C;         -- C'
	Wire_9 <= D nor D;         -- D'
	
	Wire_10 <= Wire_8 and D;   -- C'D
	Wire_11 <= Wire_9 and C;   -- CD'

	b1 <= Wire_10 or Wire_11;  -- C'D + CD'

-- End of b1 Circuit


-- Start of b0 Circuit

	b0 <= Wire_9;              -- D'

-- End of b0 Circuit

end struct;