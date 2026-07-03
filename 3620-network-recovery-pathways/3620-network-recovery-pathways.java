class Solution {

    static class Edge {
        int to;
        int cost;

        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {

        int n = online.length;

        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        int[] indegree = new int[n];
        int maxCost = 0;

        for (int[] e : edges) {
            graph[e[0]].add(new Edge(e[1], e[2]));
            indegree[e[1]]++;
            maxCost = Math.max(maxCost, e[2]);
        }

        // Topological Order
        Queue<Integer> q = new LinkedList<>();
        int[] topo = new int[n];
        int idx = 0;

        for (int i = 0; i < n; i++)
            if (indegree[i] == 0)
                q.offer(i);

        while (!q.isEmpty()) {
            int u = q.poll();
            topo[idx++] = u;

            for (Edge e : graph[u]) {
                if (--indegree[e.to] == 0)
                    q.offer(e.to);
            }
        }

        int ans = -1;
        int lo = 0, hi = maxCost;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (canReach(mid, graph, topo, online, k, n)) {
                ans = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return ans;
    }

    private boolean canReach(int limit, ArrayList<Edge>[] graph, int[] topo,
                             boolean[] online, long k, int n) {

        long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;

        for (int u : topo) {

            if (dist[u] == INF)
                continue;

            // Offline intermediate nodes cannot be used
            if (u != 0 && u != n - 1 && !online[u])
                continue;

            for (Edge e : graph[u]) {

                if (e.cost < limit)
                    continue;

                int v = e.to;

                if (v != 0 && v != n - 1 && !online[v])
                    continue;

                if (dist[u] + e.cost < dist[v]) {
                    dist[v] = dist[u] + e.cost;
                }
            }
        }

        return dist[n - 1] <= k;
    }
}