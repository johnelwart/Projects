import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ThanksgivingTest {

    /**
     * Capacity value defined in the test case that's passed to findMax
     */
    int capacity = 0;

    /**
     * Test case 1 using preset values. This method creates new Food objects, the Food object array, the total
     * stomach capacity, and then calls the findMax method to compare the actual and expected output.
     */
    @Test
    void testCase1(){
        System.out.println("Test case 1: ");

        Food turkey = new Food(3, 4, "Turkey");
        Food pie = new Food(2, 2, "Pie");
        Food potatoes = new Food(4, 5, "Potatoes");
        Food gravy = new Food(10, 10, "Gravy");
        Food stuffing = new Food(2, 3, "Stuffing");
        Food cranberries = new Food(7, 5, "Cranberries");
        Food casserole = new Food(12, 17, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};
        capacity = 20;

        assertEquals(30, Thanksgiving.findMax(foodArray,capacity));
    }

    /**
     * Test case 2 using preset values. This method creates new Food objects, the Food object array, the total
     * stomach capacity, and then calls the findMax method to compare the actual and expected output.
     */
    @Test
    void testCase2(){
        System.out.println("Test case 2: ");

        Food turkey = new Food(3, 4, "Turkey");
        Food pie = new Food(2, 2, "Pie");
        Food potatoes = new Food(4, 5, "Potatoes");
        Food gravy = new Food(1, 1, "Gravy");
        Food stuffing = new Food(2, 3, "Stuffing");
        Food cranberries = new Food(10, 14, "Cranberries");
        Food casserole = new Food(15, 24, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};
        capacity = 41;

        assertEquals(64, Thanksgiving.findMax(foodArray,capacity));
    }

    /**
     * Test case 3 using preset values. This method creates new Food objects, the Food object array, the total
     * stomach capacity, and then calls the findMax method to compare the actual and expected output.
     */
    @Test
    void testCase3(){
        System.out.println("Test case 3: ");

        Food turkey = new Food(3, 5, "Turkey");
        Food pie = new Food(4, 12, "Pie");
        Food potatoes = new Food(1, 1, "Potatoes");
        Food gravy = new Food(2, 5, "Gravy");
        Food stuffing = new Food(1, 1, "Stuffing");
        Food cranberries = new Food(2, 2, "Cranberries");
        Food casserole = new Food(3, 3, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};
        capacity = 7;

        assertEquals(18, Thanksgiving.findMax(foodArray,capacity));
    }

    /**
     * Test case 4 using preset values. This method creates new Food objects, the Food object array, the total
     * stomach capacity, and then calls the findMax method to compare the actual and expected output.
     */
    @Test
    void testCase4(){
        System.out.println("Test case 4: ");

        Food turkey = new Food(3, 6, "Turkey");
        Food pie = new Food(4, 9, "Pie");
        Food potatoes = new Food(1, 0.5, "Potatoes");
        Food gravy = new Food(2, 4, "Gravy");
        Food stuffing = new Food(1, 1, "Stuffing");
        Food cranberries = new Food(2, 2, "Cranberries");
        Food casserole = new Food(3, 3, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};
        capacity = 9;

        assertEquals(19, Thanksgiving.findMax(foodArray,capacity));
    }

    /**
     * Test case 5 using preset values. This method creates new Food objects, the Food object array, the total
     * stomach capacity, and then calls the findMax method to compare the actual and expected output.
     */
    @Test
    void testCase5(){
        System.out.println("Test case 5: ");

        Food turkey = new Food(6, 7, "Turkey");
        Food pie = new Food(7, 8, "Pie");
        Food potatoes = new Food(8, 9, "Potatoes");
        Food gravy = new Food(9, 10, "Gravy");
        Food stuffing = new Food(10, 11, "Stuffing");
        Food cranberries = new Food(11, 12, "Cranberries");
        Food casserole = new Food(12, 13, "Casserole");

        Food[] foodArray = {turkey, pie, potatoes, gravy, stuffing, cranberries, casserole};
        capacity = 5;

        assertEquals(0, Thanksgiving.findMax(foodArray,capacity));
    }
}
