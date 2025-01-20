import DS.Pair;
import java.lang.String;
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
    //12. Surrounded Regions
    public void solve(char[][] board) {
        int n = board.length;
        int m = board[0].length;
        for(int i=0;i<n;i++){
            if(board[i][0] == 'O')
                dfs(i, 0, board, n, m);
            if(board[i][m-1] == 'O')
                dfs(i, m-1, board, n, m);
        }
        for(int i =0; i<m;i++){
            if(board[0][i] == 'O')
                dfs(0, i, board, n, m);
            if(board[n-1][i] == 'O')
                dfs(n-1, i, board, n, m);
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(board[i][j] == 'O')
                    board[i][j] = 'X';
                if(board[i][j] == '1')
                    board[i][j] = 'O';
            }
        }
    }
    void dfs(int row, int col, char[][] board, int n, int m){
        board[row][col] = '1';
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        for(int i=0;i<4;i++){
            int r =  row + delRow[i];
            int c = col + delCol[i];
            if(r >= 0 && r < n && c >= 0 && c < m && board[r][c] == 'O'){
                dfs(r, c, board, n, m);
            }
        }
    }
    //13. Number of Enclaves
    public int numEnclaves(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;
        for(int i=0;i<n;i++){
            if(grid[i][0] == 1)
                dfs(i, 0, grid, n, m);
            if(grid[i][m-1] == 1)
                dfs(i, m-1, grid, n, m);
        }
        for(int i=0;i<m;i++){
            if(grid[0][i] == 1)
                dfs(0, i, grid, n, m);
            if(grid[n-1][i] == 1)
                dfs(n-1, i, grid, n, m);
        }

        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1)
                    res ++;
            }
        }
        return res;
    }
    void dfs(int row, int col, int[][] grid, int n, int m){
        grid[row][col] = -1;
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];
            if(r >=0 && r < n && c >=0 && c < m && grid[r][c] == 1)
                dfs(r, c, grid, n, m);
        }
    }
    //14. Word ladder - 1
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> hs = new HashSet<>(wordList);
        if(!hs.contains(endWord))
            return 0;

        hs.remove(beginWord);
        return bfs(beginWord, endWord, hs);
    }

    int bfs(String beginWord, String endWord, Set<String> hs){
        Queue<Pair> q = new LinkedList<>();
        List<String> res = new ArrayList<>(new HashSet<>(List.of("a", "b", "c")));
        q.add(new Pair(beginWord, 1));
        int count = 0;

        while(!q.isEmpty()){
            beginWord = (String)q.peek().first;
            count = (int)q.peek().second;
            q.remove();

            if(beginWord.equals(endWord))
                return count == 1 ? 0 : count;

            for(int j=0;j<beginWord.length();j++){
                char[] word = beginWord.toCharArray();

                for(char ch = 'a';ch <= 'z';ch++){
                    word[j]= ch;
                    String newWord = new String(word);
                    if(hs.contains(newWord)){
                        q.add(new Pair(newWord, count+1));
                        hs.remove(newWord);
                    }
                }
            }
        }
        return 0;
    }
    //15. Word ladder - 2
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        Set<String> hs = new HashSet<>(wordList);

        if(!hs.contains(endWord))
            return Collections.emptyList();

        Queue<List<String>> q = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        q.add(new ArrayList<>(List.of(beginWord)));
        hs.remove(beginWord);
        List<String> usedWords = new ArrayList<>();
        int level = 0;

        while(!q.isEmpty()){

            List<String> curr = q.remove();
            if(curr.size()> level){
                level++;
                for(String t : usedWords)
                    hs.remove(t);
            }
            String word = curr.get(curr.size()-1);
            if(word.equals(endWord)){
                if(res.size() == 0 || res.get(0).size() == curr.size())
                    res.add(new ArrayList<>(curr));
            }

            for(int j=0;j<word.length();j++){
                for(char ch = 'a';ch <='z';ch++){
                    char[] newWord = word.toCharArray();
                    newWord[j] = ch;
                    String newWord1 = new String(newWord);
                    if(hs.contains(newWord1)){
                        curr.add(newWord1);
                        usedWords.add(newWord1);
                        q.add(new ArrayList<>(curr));
                        curr.remove(curr.size()-1);
                    }
                }
            }
        }
        return res;
    }
    //------------------------------------------------------------OR-----------------------------------------------------
    public List<List<String>> findLadder2(String beginWord, String endWord, List<String> wordList) {
        Set<String> set = new HashSet<String>(wordList);
        if(!set.contains(endWord))
            return Collections.emptyList();
        List<List<String>> res = new ArrayList<>();
        Map<String, Integer> mp = new HashMap<>();
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        set.remove(beginWord);

        int count = 0;

        while(!q.isEmpty()){
            int size = q.size();
            count ++;
            for(int i=0;i<size;i++){
                String word = q.remove();
                mp.put(word, count);

                if(word.equals(endWord)){
                    List<String> ds = new ArrayList<>(List.of(endWord));
                    dfs(endWord, mp, beginWord, ds, res);
                    return res;
                }
                char[] newWord;

                for(int j=0;j<word.length();j++){
                    newWord = word.toCharArray();
                    for(char ch = 'a';ch<='z';ch++){
                        newWord[j] = ch;
                        String newWord1 = new String(newWord);
                        if(set.contains(newWord1)){
                            set.remove(newWord1);
                            q.add(newWord1);
                        }
                    }
                }
            }
        }
        return res;
    }
    void dfs(String endWord, Map<String, Integer> mp, String beginWord, List<String> ds, List<List<String>> res){
        if(beginWord.equals(endWord)){
            List<String> t = new ArrayList<>(ds);
            Collections.reverse(t);
            res.add(t);
            return;
        }

        char[] newWord;
        for(int j=0;j<endWord.length();j++){
            newWord = endWord.toCharArray();
            for(char ch = 'a';ch<='z';ch++){
                newWord[j] = ch;
                String newWord1 = new String(newWord);
                if(mp.containsKey(newWord1) && mp.get(newWord1) < mp.get(endWord)){
                    ds.add(newWord1);
                    dfs(newWord1, mp, beginWord, ds, res);
                    ds.remove(ds.size()-1);
                }
            }
        }
    }
    //16. Number of Distinct Islands [dfs multisource] ( distinct island means different shape of island)
    int countDistinctIslands(int[][] grid) {
        Set<String> set = new HashSet<>();
        int n = grid.length;
        int m = grid[0].length;
        int[][] vis = new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j] == 1 && vis[i][j] == 0){
                    set.add(dfs(i, j, grid, n, m, vis));
                }
            }
        }
        return set.size();
    }
    String dfs(int row, int col, int[][] grid, int n, int m, int[][] vis){
        vis[row][col] = 1;

        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        StringBuilder sb = new StringBuilder();
        String direction = "URDL";
        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];
            if(r >=0 && r < n && c >= 0 && c < m && grid[r][c] == 1 && vis[r][c] == 0){
                sb.append(direction.charAt(i) + dfs(r, c, grid, n, m, vis) + " ");

            }
        }
        return sb.toString();
    }

    //17. Bipartite Graph (DFS) (The adjacent node of graph can be color with only 2 colors)
    public boolean isBipartite(int[][] graph) {
        int v = graph.length;
        int[] vis = new int[v];
        for(int i=0;i<v;i++){
            if(vis[i] == 0 && dfs(i, graph, 1, vis) == false)
                return false;
        }
        return true;
    }
    boolean dfs(int node, int[][] graph, int color, int[] vis){
        vis[node] = color;
        for(int curr : graph[node]){
            if(vis[curr] == 0){
                if(dfs(curr, graph, color+1, vis) == false)
                    return false;
            }else if(vis[curr]%2 == color%2)
                return false;
        }
        return true;
    }
    //18. Course Scheduler -> Cycle Detection in Directed Graph (DFS)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            adj.add(new ArrayList<>());
        for (int[] prerequisite : prerequisites) {
            int u = prerequisite[0];
            int v = prerequisite[1];
            adj.get(u).add(v);
        }
        int[] vis = new int[numCourses];
        int[] pathVis = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            if(vis[i] == 0 && dfs(i, adj, vis, pathVis) == true)
                return false;
        }
        return true;
    }
    boolean dfs(int node, List<List<Integer>> adj, int[] vis, int[] pathVis){
        vis[node] = 1;
        pathVis[node] = 1;
        for(int curr : adj.get(node)){
            if(vis[curr] == 0){
                if(dfs(curr, adj, vis, pathVis))
                    return true;
            }else if(pathVis[curr] == 1)
                return true;
        }
        pathVis[node] = 0;
        return false;
    }
    //19. Topological Sort
    static ArrayList<Integer> topologicalSort(ArrayList<ArrayList<Integer>> adj) {
        int v = adj.size();
        int[] vis = new int[v];
        Stack<Integer> st = new Stack<>();
        for(int i=0;i<v;i++){
            if(vis[i] == 0)
                dfs(i, adj, vis, st);
        }
        ArrayList<Integer> res = new ArrayList<>();
        while(!st.isEmpty())
            res.add(st.pop());
        return res;
    }
    static void dfs(int node, ArrayList<ArrayList<Integer>> adj, int[] vis, Stack<Integer> st){
        vis[node] = 1;

        for(int curr : adj.get(node)){
            if(vis[curr] == 0)
                dfs(curr, adj, vis, st);
        }
        st.add(node);
    }
    //20. Topological sort - BFS- Kahn's Algorithm
    static ArrayList<Integer> topologicalSort1(ArrayList<ArrayList<Integer>> adj) {

        int v = adj.size();
        int[] vis = new int[v];
        int[] inOrder = new int[v];
        Queue<Integer> q = new LinkedList<>();
        ArrayList<Integer> res = new ArrayList<>();

        for(int i=0;i<v;i++){
            for(int child : adj.get(i))
                inOrder[child]++;
        }
        for(int i=0;i<v;i++){
            if(inOrder[i] == 0){
                q.add(i);
            }
        }

        while(!q.isEmpty()){

            int node = q.remove();
            res.add(node);
            vis[node] = 1;
            for(int curr : adj.get(node)){
                if(vis[curr] == 0){
                    inOrder[curr]--;
                    if(inOrder[curr] == 0)
                        q.add(curr);
                }
            }
        }
        if(res.size() != v)
            return new ArrayList<>();
        return res;
    }
    //21. check cycle using BFS (Kaahn's Algorithm)
    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
        int[] inOrder = new int[V];
        Queue<Integer> q = new LinkedList<>();
        int count = 0;
        for(int i=0;i<V;i++){
            for(int j=0;j<adj.get(i).size();j++){
                inOrder[adj.get(i).get(j)]++;
            }
        }
        for(int i=0;i<V;i++){
            if(inOrder[i] == 0)
                q.add(i);
        }
        while(!q.isEmpty()){
            int node = q.remove();
            for(int curr : adj.get(node)){
                inOrder[curr]--;
                if(inOrder[curr] == 0){
                    q.add(curr);
                }
            }
            count++;
        }
        return count != V;
    }
    //22. Course Schedule 1
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            adj.add(new ArrayList<>());
        for(int i=0;i<prerequisites.length;i++){
            int u = prerequisites[i][1];
            int v = prerequisites[i][0];
            adj.get(u).add(v);
        }
        int[] vis = new int[numCourses];
        int[] pathVis = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            if(vis[i] == 0 && isCyclic(i, adj, vis, pathVis) == true)
                return false;
        }
        return true;
    }
    boolean isCyclic(int node, List<List<Integer>> adj, int[] vis, int[] pathVis){
        vis[node] = 1;
        pathVis[node] = 1;
        for(int curr : adj.get(node)){
            if(vis[curr] == 0){
                if(isCyclic(curr, adj, vis, pathVis))
                    return true;
            }else if(pathVis[curr] == 1)
                return true;
        }
        pathVis[node] = 0;
        return false;
    }
    //23. Course Schedule 2
    public List<Integer> findOrder(int numCourses, int[][] prerequisites) {
        Queue<Integer> q = new LinkedList<>();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        List<Integer> res = new ArrayList<>();

        for(int i=0;i<numCourses;i++)
            adj.add(new ArrayList<>());

        for (int[] prerequisite : prerequisites) {
            int u = prerequisite[0];
            int v = prerequisite[1];
            adj.get(u).add(v);
            inDegree[v]++;
        }

        for(int i=0;i< numCourses; i++){
            if(inDegree[i] == 0){
                q.add(i);
            }
        }

        while(!q.isEmpty()){
            int node = q.remove();
            res.add(node);

            for(int it : adj.get(node)){
                inDegree[it]--;
                if(inDegree[it] == 0)
                    q.add(it);
            }
        }

        Collections.reverse(res);
        if(res.size() == numCourses)
            return res;
        return Collections.emptyList();
    }
    //24. Find eventual safe states (DFS)
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int v = graph.length;
        int[] vis = new int[v];
        int[] pathVis = new int[v];
        int[] isSafe = new int[v];
        for(int i=0;i<v;i++){
            if(vis[i] == 0){
                isCyclic(i, graph, vis, pathVis, isSafe);
            }
        }
        List<Integer> res = new ArrayList<>();
        for(int i=0;i<v;i++){
            if(isSafe[i] == 1)
                res.add(i);
        }
        return res;
    }
    boolean isCyclic(int node, int[][] graph, int[] vis, int[] pathVis, int[] isSafe){
        vis[node] = 1;
        pathVis[node] = 1;

        for(int curr : graph[node]){
            if(vis[curr] == 0){
                if(isCyclic(curr, graph, vis, pathVis, isSafe))
                    return true;
            }else if(pathVis[curr] == 1)
                return true;
        }
        pathVis[node] = 0;
        isSafe[node] = 1;
        return false;
    }
    //---------------OR (BFS) here we have to first reverse the edges between nodes-----------------------------
    public List<Integer> eventualSafeNodes1(int[][] graph) {
        int v = graph.length;
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<v;i++)
            adj.add(new ArrayList<>());
        for(int i=0;i<v;i++){
            for(int node : graph[i])
                adj.get(node).add(i);
        }

        int[] inDegree = new int[v];
        for(int i=0;i<v;i++){
            for(int node : adj.get(i)){
                inDegree[node]++;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<v;i++){
            if(inDegree[i] == 0)
                q.add(i);
        }
        List<Integer> res = new ArrayList<>();
        while(!q.isEmpty()){
            int node = q.remove();
            res.add(node);
            for(int curr : adj.get(node)){
                inDegree[curr] --;
                if(inDegree[curr] == 0){
                    q.add(curr);
                }
            }
        }
        Collections.sort(res);
        return res;
    }
    //25. Alien Dictionary
    public String findOrder(String[] dict, int k) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<k;i++)
            adj.add(new ArrayList<>());
        int[] inDegree = new int[k];

        for(int i=0;i<dict.length-1;i++){
            String a = dict[i];
            String b = dict[i+1];
            int len = Math.min(a.length(), b.length());
            for(int j=0;j<len;j++){
                if(a.charAt(j) != b.charAt(j)){
                    int u = a.charAt(j) - 'a';
                    int v = b.charAt(j) - 'a';
                    adj.get(u).add(v);
                    inDegree[v]++;
                    break;
                }
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<k;i++){
            if(inDegree[i] == 0)
                q.add(i);
        }
        StringBuilder sb = new StringBuilder();
        while(!q.isEmpty()){
            int node = q.remove();
            sb.append((char)(node + 'a'));
            for(int curr : adj.get(node)){
                inDegree[curr] --;
                if(inDegree[curr] == 0)
                    q.add(curr);
            }
        }
        if(k == sb.length())
            return sb.toString();
        return "";
    }
    //26. Course Schedule III - not graph
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);

        Queue<Integer> pq = new PriorityQueue<>((a, b) -> b-a);
        int sum = 0;
        for(int[] course : courses){
            int duration = course[0];
            int lastDay = course[1];
            pq.add(duration);
            sum += duration;
            if(sum > lastDay){
                sum -= pq.poll();
            }
        }
        return pq.size();
    }
    //27.

}
