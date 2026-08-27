class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        char[] ans = new char[n];

        for (int i = 0; i < n; i++) {
            int x = target.charAt(i) - 'a';

            // Use same character if possible
            if (freq[x] > 0) {
                ans[i] = target.charAt(i);
                freq[x]--;
                continue;
            }

            // Same character unavailable:
            // directly choose the smallest greater character
            int greater = -1;

            for (int c = x + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    greater = c;
                    break;
                }
            }

            if (greater != -1) {
                ans[i] = (char) ('a' + greater);
                freq[greater]--;

                fillRemaining(ans, i + 1, freq);
                return new String(ans);
            }

            // Backtrack to find a previous position
            for (int j = i - 1; j >= 0; j--) {
                freq[ans[j] - 'a']++;

                int prev = ans[j] - 'a';
                int next = -1;

                for (int c = prev + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        next = c;
                        break;
                    }
                }

                if (next != -1) {
                    ans[j] = (char) ('a' + next);
                    freq[next]--;

                    fillRemaining(ans, j + 1, freq);
                    return new String(ans);
                }
            }

            return "";
        }

        // target exactly matches a permutation of s.
        // Find the next greater permutation by backtracking.
        for (int i = n - 1; i >= 0; i--) {
            freq[ans[i] - 'a']++;

            int current = ans[i] - 'a';
            int next = -1;

            for (int c = current + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    next = c;
                    break;
                }
            }

            if (next != -1) {
                ans[i] = (char) ('a' + next);
                freq[next]--;

                fillRemaining(ans, i + 1, freq);
                return new String(ans);
            }
        }

        return "";
    }

    private void fillRemaining(char[] ans, int start, int[] freq) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans[pos++] = (char) ('a' + c);
                freq[c]--;
            }
        }
    }
}