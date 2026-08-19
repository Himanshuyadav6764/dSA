class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        // Map: row -> bitmask of reserved seats (only seats 2 to 9 matter)
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        int ans = (n - map.size()) * 2;

        // Masks for:
        // [2,3,4,5]
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);

        // [4,5,6,7]
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);

        // [6,7,8,9]
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        for (int mask : map.values()) {
            boolean canLeft = (mask & left) == 0;
            boolean canMiddle = (mask & middle) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                // Both groups can sit without overlapping
                ans += 2;
            } else if (canLeft || canMiddle || canRight) {
                ans += 1;
            }
        }

        return ans;
    }
}