import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ChangeTest {

    /**
     * testCalcTotalAmount determines if the expected value passed by the tester is the same as the return value
     * passed by the method calcTotalAmount with the parameters passed into it.
     */
    @Test
    void testCalcTotalAmount(){
        assertEquals(0, Change.calcTotalAmt(0, 0, 0, 0, 0, 0, 0, 0));

        assertEquals(100, Change.calcTotalAmt(5, 0, 0, 0, 0, 0, 0, 0));

        assertEquals(36.41, Change.calcTotalAmt(1, 1, 1, 1, 1, 1, 1, 1));

        assertEquals(91.37, Change.calcTotalAmt(3, 2, 1, 5, 4, 2, 3, 2));

        assertEquals(89.27, Change.calcTotalAmt(3, 2, 1, 3, 4, 2, 1, 2));

        assertEquals(196.65, Change.calcTotalAmt(9, 1, 1, 1, 2, 1, 1, 0));

        assertEquals(383.71, Change.calcTotalAmt(19, 0, 0, 3, 2, 2, 0, 1));

        assertEquals(74.05, Change.calcTotalAmt(3, 1, 0, 4, 0, 0, 1, 0));

        assertEquals(237.27, Change.calcTotalAmt(11, 1, 1, 2, 1, 0, 0, 2));

        assertEquals(138.56, Change.calcTotalAmt(6, 1, 1, 3, 2, 0, 1, 1));
    }

    /**
     * testCalcChange creates a new array of bills and then calls calcChange with a parameter passed into it and determines
     * if they are equal by going through all the elements of both arrays.
     */
    @Test
    void testCalcChange(){
        // Test 1
        int[] billTest1;
        billTest1 = new int[] {1,0,1,0,0,1,1,1,3};

        int[] received1;
        received1 = Change.calcChange(30);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest1[i], received1[i]);
        }

        // Test 2
        int[] billTest2;
        billTest2 = new int[] {1,1,0,3,1,1,1,1,0};

        int[] received2;
        received2 = Change.calcChange(40);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest2[i], received2[i]);
        }

        // Test 3
        int[] billTest3;
        billTest3 = new int[] {4,0,0,4,1,0,1,0,3};

        int[] received3;
        received3 = Change.calcChange(100);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest3[i], received3[i]);
        }

        // Test 4
        int[] billTest4;
        billTest4 = new int[] {3,1,0,0,1,1,1,0,3};

        int[] received4;
        received4 = Change.calcChange(83.70);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest4[i], received4[i]);
        }

        // Test 5
        int[] billTest5;
        billTest5 = new int[] {4,1,1,2,1,1,0,1,4};

        int[] received5;
        received5 = Change.calcChange(115.56);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest5[i], received5[i]);
        }

        // Test 6
        int[] billTest6;
        billTest6 = new int[] {8,0,0,3,1,1,1,1,1};

        int[] received6;
        received6 = Change.calcChange(193.62);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest6[i], received6[i]);
        }

        // Test 7
        int[] billTest7;
        billTest7 = new int[] {1,1,1,1,1,0,1,0,0};

        int[] received7;
        received7 = Change.calcChange(43.25);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest7[i], received7[i]);
        }

        // Test 8
        int[] billTest8;
        billTest8 = new int[] {4,1,0,4,1,0,0,0,4};

        int[] received8;
        received8 = Change.calcChange(111.71);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest8[i], received8[i]);
        }

        // Test 9
        int[] billTest9;
        billTest9 = new int[] {7,1,0,4,1,1,1,0,0};

        int[] received9;
        received9 = Change.calcChange(182.92);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest9[i], received9[i]);
        }

        // Test 10
        int[] billTest10;
        billTest10 = new int[] {0,0,0,0,0,0,0,0,0};

        int[] received10;
        received10 = Change.calcChange(0);

        for (int i = 0; i < 9; i++){
            assertEquals(billTest10[i], received10[i]);
        }

    }
}