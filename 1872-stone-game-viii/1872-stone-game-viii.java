class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        long[] prefix = new long[n];
        prefix[0] = stones[0];

        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }

        // If Alice takes all stones
        long ans = prefix[n - 1];

        // Try stopping at every possible prefix of size >= 2
        for (int i = n - 2; i >= 1; i--) {
            ans = Math.max(ans, prefix[i] - ans);
        }

        return (int) ans;
    }
}