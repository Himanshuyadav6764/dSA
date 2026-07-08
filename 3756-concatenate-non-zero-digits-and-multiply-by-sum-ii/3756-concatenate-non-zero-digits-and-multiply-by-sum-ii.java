class Solution {
    static final int MOD = 1_000_000_007;

    class Node {
        long val;
        int len;
    }

    Node[] tree;
    long[] pow10;
    int[] prefNZ;
    int[] prefSum;
    int[] digits;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        prefNZ = new int[n + 1];
        prefSum = new int[n + 1];

        int cnt = 0;
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            prefNZ[i + 1] = prefNZ[i] + (d != 0 ? 1 : 0);
            prefSum[i + 1] = prefSum[i] + d;
            if (d != 0) cnt++;
        }

        digits = new int[cnt];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) digits[idx++] = d;
        }

        pow10 = new long[cnt + 1];
        pow10[0] = 1;
        for (int i = 1; i <= cnt; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        if (cnt > 0) {
            tree = new Node[4 * cnt];
            build(1, 0, cnt - 1);
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = prefNZ[l];
            int right = prefNZ[r + 1] - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            Node res = query(1, 0, cnt - 1, left, right);

            long sum = prefSum[r + 1] - prefSum[l];
            ans[i] = (int) ((res.val * sum) % MOD);
        }

        return ans;
    }

    private void build(int node, int l, int r) {
        tree[node] = new Node();

        if (l == r) {
            tree[node].val = digits[l];
            tree[node].len = 1;
            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        merge(node);
    }

    private void merge(int node) {
        Node left = tree[node * 2];
        Node right = tree[node * 2 + 1];

        tree[node].len = left.len + right.len;
        tree[node].val = (left.val * pow10[right.len] + right.val) % MOD;
    }

    private Node query(int node, int l, int r, int ql, int qr) {
        if (ql == l && qr == r) return tree[node];

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(node * 2, l, mid, ql, mid);
        Node right = query(node * 2 + 1, mid + 1, r, mid + 1, qr);

        Node res = new Node();
        res.len = left.len + right.len;
        res.val = (left.val * pow10[right.len] + right.val) % MOD;

        return res;
    }
}