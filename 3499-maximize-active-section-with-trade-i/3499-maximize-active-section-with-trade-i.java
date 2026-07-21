class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int ones = 0;
        for (char ch : s.toCharArray()) {
            if (ch == '1') {
                ones++;
            }
        }

        String t = "1" + s + "1";

        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Integer> lens = new ArrayList<>();

        // Run Length Encoding
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);

            if (chars.isEmpty() || chars.get(chars.size() - 1) != ch) {
                chars.add(ch);
                lens.add(1);
            } else {
                lens.set(lens.size() - 1, lens.get(lens.size() - 1) + 1);
            }
        }

        int gain = 0;

        // Check every pattern: 0-run, 1-run, 0-run
        for (int i = 1; i < chars.size() - 1; i++) {
            if (chars.get(i) == '1' &&
                chars.get(i - 1) == '0' &&
                chars.get(i + 1) == '0') {

                gain = Math.max(gain,
                        lens.get(i - 1) + lens.get(i + 1));
            }
        }

        return ones + gain;
    }
}