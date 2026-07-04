class Solution {

    List<int[]>[] graph;
    boolean[] visited;
    int ans = Integer.MAX_VALUE;

    public int minScore(int n, int[][] roads) {

        graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Undirected Graph
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int d = road[2];

            graph[u].add(new int[]{v, d});
            graph[v].add(new int[]{u, d});
        }

        visited = new boolean[n + 1];

        dfs(1);

        return ans;
    }

    private void dfs(int node) {

        visited[node] = true;

        for (int[] edge : graph[node]) {

            int next = edge[0];
            int dist = edge[1];

            ans = Math.min(ans, dist);

            if (!visited[next]) {
                dfs(next);
            }
        }
    }
}