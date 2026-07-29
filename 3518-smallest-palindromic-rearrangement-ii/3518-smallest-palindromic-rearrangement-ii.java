class Solution {

    long LIMIT;
    long[][] C;

    public String smallestPalindrome(String s, int k) {

        LIMIT = k;

        int[] freq = new int[26];

        for (char ch : s.toCharArray())
            freq[ch - 'a']++;

        int[] half = new int[26];
        int len = 0;
        char mid = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            len += half[i];

            if ((freq[i] & 1) == 1)
                mid = (char) ('a' + i);
        }

        buildCombination(len);

        if (countWays(half) < k)
            return "";

        StringBuilder first = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half);

                if (ways >= k) {
                    first.append((char) ('a' + c));
                    break;
                }

                k -= ways;
                half[c]++;
            }
        }

        StringBuilder ans = new StringBuilder(first);

        if (mid != 0)
            ans.append(mid);

        ans.append(new StringBuilder(first).reverse());

        return ans.toString();
    }

    private void buildCombination(int n) {

        C = new long[n + 1][27];

        for (int i = 0; i <= n; i++) {

            C[i][0] = 1;

            for (int j = 1; j <= Math.min(i, 26); j++) {

                long val = C[i - 1][j - 1] + C[i - 1][j];

                if (val > LIMIT)
                    val = LIMIT;

                C[i][j] = val;
            }
        }
    }

    private long countWays(int[] half) {

        int rem = 0;

        for (int x : half)
            rem += x;

        long ans = 1;

        int left = rem;

        for (int x : half) {

            if (x == 0)
                continue;

            ans = multiply(ans, comb(left, x));

            if (ans >= LIMIT)
                return LIMIT;

            left -= x;
        }

        return ans;
    }

    private long comb(int n, int r) {

        if (r == 0 || r == n)
            return 1;

        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {

            res = res * (n - r + i) / i;

            if (res >= LIMIT)
                return LIMIT;
        }

        return res;
    }

    private long multiply(long a, long b) {

        if (a == 0 || b == 0)
            return 0;

        if (a >= LIMIT || b >= LIMIT)
            return LIMIT;

        if (a > LIMIT / b)
            return LIMIT;

        long val = a * b;

        return Math.min(val, LIMIT);
    }
}