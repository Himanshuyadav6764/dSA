class Solution {

    class Node {
        int leftChar;
        int rightChar;
        int leftLen;
        int rightLen;
        int maxLen;
        int len;

        Node() {}

        Node(char c) {
            leftChar = c;
            rightChar = c;
            leftLen = rightLen = maxLen = len = 1;
        }
    }

    Node[] tree;
    String s;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {

        this.s = s;

        int n = s.length();
        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int index = queryIndices[i];
            char ch = queryCharacters.charAt(i);

            update(1, 0, n - 1, index, ch);

            ans[i] = tree[1].maxLen;
        }

        return ans;
    }

    // Build segment tree
    void build(int node, int start, int end) {

        if (start == end) {
            tree[node] = new Node(s.charAt(start));
            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid);
        build(node * 2 + 1, mid + 1, end);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Update one character
    void update(int node, int start, int end, int index, char ch) {

        if (start == end) {
            tree[node] = new Node(ch);
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(node * 2, start, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, end, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Merge two segments
    Node merge(Node left, Node right) {

        Node res = new Node();

        res.len = left.len + right.len;

        res.leftChar = left.leftChar;
        res.rightChar = right.rightChar;

        // Left prefix
        res.leftLen = left.leftLen;

        if (left.leftLen == left.len &&
            left.rightChar == right.leftChar) {

            res.leftLen = left.len + right.leftLen;
        }

        // Right suffix
        res.rightLen = right.rightLen;

        if (right.rightLen == right.len &&
            left.rightChar == right.leftChar) {

            res.rightLen = right.len + left.rightLen;
        }

        // Maximum inside either segment
        res.maxLen = Math.max(left.maxLen, right.maxLen);

        // Maximum crossing the middle
        if (left.rightChar == right.leftChar) {
            res.maxLen = Math.max(
                res.maxLen,
                left.rightLen + right.leftLen
            );
        }

        return res;
    }
}