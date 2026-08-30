class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIndex = 0;
        int maxIndex = 0;

        // Find minimum and maximum element indices
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Make sure minIndex is before maxIndex
        if (minIndex > maxIndex) {
            int temp = minIndex;
            minIndex = maxIndex;
            maxIndex = temp;
        }

        // 1. Remove both from front
        int front = maxIndex + 1;

        // 2. Remove both from back
        int back = n - minIndex;

        // 3. Remove min from front and max from back
        int bothSides = (minIndex + 1) + (n - maxIndex);

        return Math.min(front, Math.min(back, bothSides));
    }
}