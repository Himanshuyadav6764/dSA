class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        // dp[i] = maximum length of suffix of word2
        // that can be matched exactly in word1[i...]
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {

            dp[i] = dp[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        while (i < n && j < m) {

            // Exact match -> always prefer this index
            if (word1.charAt(i) == word2.charAt(j)) {

                ans[j] = i;
                j++;
                i++;

            } else {

                // Use our one allowed mismatch
                int remaining = m - j - 1;

                // Can the rest be matched exactly?
                if (dp[i + 1] >= remaining) {

                    ans[j] = i;
                    j++;
                    i++;

                    // Mismatch is consumed.
                    // Now match the rest exactly.
                    break;
                }

                i++;
            }
        }

        // Match remaining characters exactly
        while (i < n && j < m) {

            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }

            i++;
        }

        // Could not create complete sequence
        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}