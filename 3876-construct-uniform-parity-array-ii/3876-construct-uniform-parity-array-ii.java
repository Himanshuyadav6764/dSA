class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = Integer.MAX_VALUE;

        for (int x : nums1) {
            min = Math.min(min, x);
        }

   
        if (min % 2 == 1) {
            return true;
        }

       
        for (int x : nums1) {
            if (x % 2 == 1) {
                return false;
            }
        }

        return true;
    }
}