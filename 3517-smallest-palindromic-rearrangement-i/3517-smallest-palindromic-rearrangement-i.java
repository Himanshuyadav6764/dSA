class Solution {
    public String smallestPalindrome(String s) {
        int[] freq = new int[26];


        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder left = new StringBuilder();
        String middle = "";

        for (int i = 0; i < 26; i++) {
            while (freq[i] >= 2) {
                left.append((char) ('a' + i));
                freq[i] -= 2;
            }

            if (freq[i] == 1) {
                middle = String.valueOf((char) ('a' + i));
            }
        }

 
        String right = new StringBuilder(left).reverse().toString();

        return left.toString() + middle + right;
    }
}