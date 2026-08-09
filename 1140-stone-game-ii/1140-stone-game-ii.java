class Solution {
    int n;
    int[] suffix;
    int[][] dp;

    public int stoneGameII(int[] piles) {
        n = piles.length;

        suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        dp = new int[n][n + 1];

        return solve(0, 1, piles);
    }

    private int solve(int i, int M, int[] piles) {

        // All remaining stones can be taken
        if (i >= n) {
            return 0;
        }

        // If 2*M or more piles remain, take all
        if (2 * M >= n - i) {
            return suffix[i];
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        int best = 0;

        // Alice can take X piles, 1 <= X <= 2*M
        for (int X = 1; X <= 2 * M && i + X <= n; X++) {

            // Current player gets X piles.
            // Opponent gets solve(i+X, max(M,X)).
            int opponent = solve(i + X, Math.max(M, X), piles);

            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        return dp[i][M] = best;
    }
}