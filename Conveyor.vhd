library ieee;
use ieee.std_logic_1164.all;

entity Conveyor is
	Port ( Clk : in STD_LOGIC;
	Reset : in STD_LOGIC;
	SA : in STD_LOGIC;
	SB : in STD_LOGIC;
	CA : out STD_LOGIC;
	CB : out STD_LOGIC;
	S : out STD_LOGIC_VECTOR (3 downto 0));
end Conveyor;

architecture ConveyorArch of Conveyor is

	type stype is (A, B, C, D, E, F, G, H, I, J, K);
	Signal present_state, next_state : stype;

begin

	-- Process 1 - state_register: implements positive-edge-triggered state storage with asynchronous reset.
	state_register: process (Clk, Reset)
	begin
		if (Reset = '1') then
			present_state <= A;
		elsif (Clk'event and Clk = '1') then
			present_state <= next_state;
		end if;
	end process;
	
	-- Process 2 - next_state_function: implements next state as a function of inputs SA and SB and state.
	next_state_func: process (SA, SB, Clk)
	begin
		case present_state is
			when A =>
				if (SA = '1' and SB = '0') then
					next_state <= B;
				elsif (SA = '0' and SB = '1') then
					next_state <= G;
				else
					next_state <= A;
				end if;
			when B =>
				if (SA = '1' and SB = '0') then
					next_state <= C;
				elsif (SA = '0' and SB = '1') then
					next_state <= A;
				else
					next_state <= B;
				end if;
			when C =>
				if (SA = '1' and SB = '0') then
					next_state <= D;
				elsif (SA = '0' and SB = '1') then
					next_state <= B;
				else
					next_state <= C;
				end if;
			when D =>
				if (SB = '0') then
					next_state <= D;
				elsif (SB = '1') then
					next_state <= E;
				end if;
			when E =>
				if (SB = '0') then
					next_state <= E;
				elsif (SB = '1') then
					next_state <= F;
				end if;
			when F =>
				if (SB = '0') then
					next_state <= F;
				elsif (SB = '1') then
					next_state <= A;
				end if;
			when G =>
				if (SA = '1' and SB = '0') then
					next_state <= A;
				elsif (SA = '0' and SB = '1') then
					next_state <= H;
				else
					next_state <= G;
				end if;
			when H =>
				if (SA = '1' and SB = '0') then
					next_state <= G;
				elsif (SA = '0' and SB = '1') then
					next_state <= I;
				else
					next_state <= H;
				end if;
			when I =>
				if (SA = '0') then
					next_state <= I;
				elsif (SA = '1') then
					next_state <= J;
				end if;
			when J =>
				if (SA = '0') then
					next_state <= J;
				elsif (SA = '1') then
					next_state <= K;
				end if;
			when K =>
				if (SA = '0') then
					next_state <= K;
				elsif (SA = '1') then
					next_state <= A;
				end if;
		end case;
	end process;
	
	-- Process 3 - output_function: implements output as a function state.
	output_func: process (present_state)
	begin
		case present_state is
			when A =>
				CA <= '1';
				CB <= '1';
			when B =>
				CA <= '1';
				CB <= '1';
			when C =>
				CA <= '1';
				CB <= '1';
			when D =>
				CA <= '0';
				CB <= '1';
			when E =>
				CA <= '0';
				CB <= '1';
			when F =>
				CA <= '0';
				CB <= '1';
			when G =>
				CA <= '1';
				CB <= '1';
			when H =>
				CA <= '1';
				CB <= '1';
			when I =>
				CA <= '1';
				CB <= '0';
			when J =>
				CA <= '1';
				CB <= '0';
			when K =>	
				CA <= '1';
				CB <= '0';
		end case;
	end process;
	
	with present_state select S <= "0000" when A, "0001" when B, "0010" when C, "0011" when D, "0100" when E, "0101" when F, "0110" when G, 
												"0111" when H, "1000" when I, "1001" when J, "1010" when K;
	
end ConveyorArch;