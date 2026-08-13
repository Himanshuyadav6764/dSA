class Solution {

    class Node {
        char leftChar, rightChar;
        int prefix, suffix, best, len;

        Node(char c) {
            leftChar = rightChar = c;
            prefix = suffix = best = len = 1;
        }

        Node() {}
    }

    Node[] tree;
    String s;

    void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = new Node(s.charAt(l));
            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node merge(Node a, Node b) {

        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.prefix = a.prefix;
        res.suffix = b.suffix;
        res.best = Math.max(a.best, b.best);

        // If boundary characters are same,
        // we can combine suffix of left + prefix of right
        if (a.rightChar == b.leftChar) {

            if (a.prefix == a.len) {
                res.prefix = a.len + b.prefix;
            }

            if (b.suffix == b.len) {
                res.suffix = b.len + a.suffix;
            }

            res.best = Math.max(
                res.best,
                a.suffix + b.prefix
            );
        }

        return res;
    }

    void update(int node, int l, int r, int idx, char c) {

        if (l == r) {
            tree[node] = new Node(c);
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, c);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, c);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {

        this.s = s;

        int n = s.length();

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {

            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);

            update(1, 0, n - 1, idx, c);

            ans[i] = tree[1].best;
        }

        return ans;
    }
}