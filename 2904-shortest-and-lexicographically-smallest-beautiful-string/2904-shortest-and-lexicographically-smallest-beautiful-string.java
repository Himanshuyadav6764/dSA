class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int left = 0;
        int ones = 0;

        String ans = "";

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Too many 1s, shrink the window
            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // Exactly k ones
            if (ones == k) {

                // Remove leading zeroes
                while (left <= right && s.charAt(left) == '0') {
                    left++;
                }

                String curr = s.substring(left, right + 1);

                // Shorter is better, then lexicographically smaller
                if (ans.equals("")
                        || curr.length() < ans.length()
                        || (curr.length() == ans.length()
                            && curr.compareTo(ans) < 0)) {
                    ans = curr;
                }
            }
        }

        return ans;
    }
}