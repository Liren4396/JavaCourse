package average;

public class Average {
    /**
     * Returns the average of an array of numbers
     *
     * @param nums the array of integer numbers
     * @return the average of the numbers
     */
    public float computeAverage(int[] nums) {
        float result = 0;
        // Add your code
        for (int i = 0; i < nums.length; i++) {
            result += nums[i];
        }
        result /= nums.length;
        return result;
    }

    public static void main(String[] args) {
        // Add your code
        int[] nums = {1, 2, 3, 4, 5, 6};
        Average a = new Average();
        System.out.println("The average is " + a.computeAverage(nums));
    }
}
