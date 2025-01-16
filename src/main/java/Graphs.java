import DS.Pair;
import DS.TreeNode;

import java.security.KeyPair;
import java.util.*;

public class Graphs {
    public static void main(String[] args) {

    }
    //1. Find the number of undirected graph when vertices are given
    long count(int n) {
        // code here
        long edges = (long) n * (n-1) /2;
        return (long) Math.pow(2, edges);
    }
    //2.Create adjacency list
    public List<List<Integer>> printGraph(int V, int edges[][]) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        return adj;
    }
    //3. Connected Components in an Undirected Graph
    public ArrayList<ArrayList<Integer>> connectedcomponents(int v, int[][] edges) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        //create adj list
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<v;i++){
            adj.add(new ArrayList<>());
        }
        //filling adj list
        for(int i=0;i<edges.length;i++){
            int u1 = edges[i][0];
            int v1 = edges[i][1];
            adj.get(u1).add(v1);
            adj.get(v1).add(u1);
        }
        //for visited nodes
        int[] vis = new int[v];
        for(int i=0;i<v;i++){
            if(vis[i] == 1)
                continue;
            ArrayList<Integer> ds = new ArrayList<>();
            dfs(adj, i, vis, ds);
            Collections.sort(ds);
            //sorting list as out should be sorted in question requirement
            res.add(new ArrayList<>(ds));
        }
        return res;
    }
    void dfs(List<List<Integer>> adj, int node, int[] vis, ArrayList<Integer> ds){
        vis[node] = 1;
        ds.add(node);
        for(int curr : adj.get(node)){
            if(vis[curr] == 1)
                continue;
            dfs(adj, curr, vis, ds);
        }
    }
    //4. BFS traversal of graph
    public ArrayList<Integer> bfsOfGraph(int V, ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> res = new ArrayList<>();
        int[] vis = new int[V];
        Queue<Integer> q = new LinkedList<>();

        q.add(0);
        vis[0] = 1;
        while(!q.isEmpty()){
            int parent = q.remove();
            res.add(parent);

            for(int child : adj.get(parent)){
                if(vis[child] == 1)
                    continue;
                vis[child] = 1;
                q.add(child);
            }
        }
        return res;
    }
    //5. DFS traversal of graph
    public ArrayList<Integer> dfsOfGraph(ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> res = new ArrayList<>();
        int v = adj.size();
        int[] vis = new int[v];
        dfs(0, adj, vis, res);
        return res;
    }
    void dfs(int node, ArrayList<ArrayList<Integer>> adj, int[] vis, ArrayList<Integer> res){
        vis[node] = 1;
        res.add(node);
        for(int curr : adj.get(node)){
            if(vis[curr] == 1)
                continue;
            dfs(curr, adj, vis, res);
        }
    }
    //6. Number of Provinces
    public int numProvinces(int[][] isConnected) {

        int v = isConnected.length;
        int count = 0;
        int[] vis = new int[v];
        for(int i = 0;i < v;i++){
            if(vis[i] == 1)
                continue;
            dfs(i, isConnected, vis);
            count++;
        }
        return count;
    }
    void dfs(int node, int[][] graph, int[] vis){
        vis[node] = 1;
        for(int curr = 0;curr<graph.length; curr++){
            if(vis[curr] == 1 || graph[node][curr] == 0)
                continue;
            dfs(curr, graph, vis);
        }
    }
    //7. Rotting Oranges
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<Pair> q = new LinkedList<>();
        int freshOrange = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1)
                    freshOrange++;
                if(grid[i][j] == 2)
                    q.add(new Pair(i, j));
            }
        }

        int minutes =0;
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        while(!q.isEmpty()){
            int size = q.size();
            minutes ++;
            for(int i =0;i<size;i++){
                Pair<Integer, Integer> p = q.remove();
                for(int j=0;j<4;j++){
                    int r = p.first + delRow[j];
                    int c = p.second + delCol[j];
                    if(r < n && r >=0 && c <m && c >= 0 && grid[r][c] == 1){
                        grid[r][c] = 2;
                        q.add(new Pair(r, c));
                        freshOrange--;
                    }

                }
            }
        }
        if(freshOrange != 0)
            return -1;
        return minutes - 1;
    }
    //8. Flood Fill
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if(image[sr][sc] == color)
            return image;
        dfs(image, sr, sc, color, image[sr][sc]);
        return image;
    }
    void dfs(int[][] image, int row, int col, int color, int initClr){
        int n = image.length;
        int m = image[0].length;
        image[row][col] = color;
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];
            if(r <n && r >=0 && c < m && c >=0 && image[r][c] == initClr){
                dfs(image, r, c, color, initClr);
            }
        }
    }
    //9. Cycle Detection in Undirected Graph (DFS)
    public boolean isCycle(ArrayList<ArrayList<Integer>> adj) {
        // Code here
        int v = adj.size();
        int[] vis = new int[v];
        for(int i=0;i<v;i++){
            if(vis[i] == 1)
                continue;
            if(dfs(i, adj, -1, vis))
                return true;
        }
        return false;
    }
    boolean dfs(int node, ArrayList<ArrayList<Integer>> adj, int parent, int[] vis){
        vis[node] = 1;
        for(int curr : adj.get(node)){
            if(vis[curr] == 1){
                if(curr != parent)
                    return true;
                continue;
            }
            if(dfs(curr, adj, node, vis))
                return true;
        }
        return false;
    }
    //10. Cycle Detection in Undirected Graph (BFS)
    public boolean isCycle1(ArrayList<ArrayList<Integer>> adj) {
        // Code here
        int v = adj.size();
        int[] vis = new int[v];
        for(int i=0;i<v;i++){
            if(vis[i] == 1)
                continue;
            if(bfs1(i, adj, vis))
                return true;
        }
        return false;
    }
    boolean bfs1(int node, ArrayList<ArrayList<Integer>> adj, int[] vis){

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(node, -1));
        while(!q.isEmpty()){
            Pair<Integer, Integer> p = q.remove();
            int curr = p.first;
            int parent = p.second;
            vis[curr] = 1;
            for(int child : adj.get(curr)){
                if(vis[child] == 1){
                    if(child != parent)
                        return true;
                    continue;
                }
                q.add(new Pair(child, curr));
            }
        }
        return false;
    }
    //11. 0/1 Matrix (Bfs Problem) every 1's distance from 0
    public int[][] updateMatrix(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        Queue<Pair> q = new LinkedList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(mat[i][j] == 0)
                    q.add(new Pair(i, j));
                else
                    mat[i][j] = -1;
            }
        }

        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                Pair<Integer, Integer> p = q.remove();
                int row = p.first;
                int col = p.second;

                for(int j=0;j<4;j++){
                    int r = row + delRow[j];
                    int c = col + delCol[j];
                    if(r >=0 && r < n && c >=0 && c <m && mat[r][c] == -1){
                        mat[r][c] = mat[row][col] + 1;
                        q.add(new Pair(r, c));
                    }
                }
            }
        }
        return mat;
    }
    //12. 
}
