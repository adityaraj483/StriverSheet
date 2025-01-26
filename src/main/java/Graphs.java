import DS.Pair;
import java.lang.String;
import DS.*;
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
    //27. Shortest Path in Undirected Graph with unit weights
    public int[] shortestPath(ArrayList<ArrayList<Integer>> adj, int src) {
        int v = adj.size();
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(src, 0));
        int[] res = new int[v];
        Arrays.fill(res, -1);

        while(!q.isEmpty()){
            Pair<Integer, Integer> p = q.remove();
            int node = p.first;
            int cost = p.second;
            if(res[node] == -1)
                res[node] = cost;
            else
                continue;
            for(int curr : adj.get(node)){
                q.add(new Pair(curr, cost+1));
            }
        }
        return res;
    }
    //28. Shortest Path in DAG
    public int[] shortestPath(int V, int E, int[][] edges) {
        List<List<Pair>> adj = new ArrayList<>();
        for(int i=0;i<V;i++)
            adj.add(new ArrayList<>());
        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            adj.get(u).add(new Pair(v, weight));
        }

        Queue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> a.second - b.second);

        int[] res = new int[V];
        Arrays.fill(res, -1);
        pq.add(new Pair(0, 0));

        while(!pq.isEmpty()){

            Pair p = pq.remove();
            int node = (int) p.first;
            int weight = (int) p.second;

            if(res[node] == -1){
                res[node] = weight;
            }else
                continue;

            for(Pair curr : adj.get(node)){
                pq.add(new Pair(curr.first, (int)curr.second + weight));
            }
        }
        return res;
    }
    //29. Djisktra's Algorithm
    ArrayList<Integer> dijkstra(ArrayList<ArrayList<Pair>> adj, int src) {
        int v = adj.size();
        Queue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b)-> a.second - b.second);
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(v, Integer.MAX_VALUE));

        pq.add(new Pair(src, 0));
        dist.set(src, 0);

        while(!pq.isEmpty()){
            Pair<Integer, Integer> p = pq.remove();
            int node = p.first;
            int weight = p.second;

            for(Pair<Integer, Integer> curr : adj.get(node)){

                int adjNode = curr.first;
                int adjWeight = curr.second;
                if(weight + adjWeight < dist.get(adjNode)){
                    dist.set(adjNode, weight + adjWeight);
                    pq.add(new Pair(adjNode, weight + adjWeight));
                }
            }
        }
        return dist;
    }
    //30. Shortest Path in Binary Matrix
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0] == 1 || grid[n-1][n-1] == 1)
            return -1;
        Queue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>((a, b) -> a.first - b.first);
        int[][] vis = new int[n][n];
        int[] delRow = new int[]{-1, 0, 1};
        int[] delCol = new int[]{-1, 0, 1};

        pq.add(new Pair<>(1, new Pair<>(0, 0)));
        vis[0][0] = 1;

        while(!pq.isEmpty()){
            Pair<Integer, Pair<Integer, Integer>> p = pq.remove();
            int cost = p.first;
            int row = p.second.first;
            int col = p.second.second;
            if(row == n-1 && col == n-1)
                return cost;

            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    int r = delRow[i] + row;
                    int c = delCol[j] + col;

                    if(r >=0 && r < n && c>=0 && c <n && grid[r][c] == 0 && vis[r][c] == 0){
                        pq.add(new Pair<>(cost+1, new Pair<>(r, c)));
                        vis[r][c] = 1;
                    }
                }
            }
        }
        return -1;
    }
    //31. Path With Minimum Effort
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;
        int[][] vis = new int[n][m];
        Queue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>((a, b) -> a.first - b.first);
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        pq.add(new Pair<>(0, new Pair<>(0, 0)));

        while(!pq.isEmpty()){
            Pair<Integer, Pair<Integer, Integer>> p = pq.remove();
            int effort = p.first;
            int row = p.second.first;
            int col = p.second.second;
            vis[row][col] = 1;
            if(row == n-1 && col == m-1)
                return effort;

            for(int i=0;i<4;i++){
                int r = row + delRow[i];
                int c = col + delCol[i];
                if(r >=0 && r <n && c >=0 && c < m && vis[r][c] == 0){
                    int newEffort = Math.max(effort, Math.abs(heights[r][c] - heights[row][col]));
                    pq.add(new Pair<>(newEffort, new Pair<>(r, c)));
                }
            }
        }
        return -1;
    }
    //32. Cheapest Flights Within K Stops
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<Pair>> adj = new ArrayList<>();
        int[] res = new int[n];

        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
            res[i] = Integer.MAX_VALUE;
        }
        for(int[] flight: flights){
            int u = flight[0];
            int v = flight[1];
            int cost = flight[2];
            adj.get(u).add(new Pair<>(v, cost));
        }

        Queue<Pair<Integer, Pair<Integer,Integer>>> q = new LinkedList<>();
        q.add(new Pair<>(src, new Pair<>(0, 0)));
        res[src] = 0;

        while(!q.isEmpty()){

            Pair<Integer, Pair<Integer,Integer>> p = q.remove();
            int node = p.first;
            int cost = p.second.first;
            int k1 = p.second.second;
            if(k1 > k || node == dst)
                continue;

            for(Pair<Integer,Integer> curr : adj.get(node)){
                int currNode = curr.first;
                int currCost = curr.second + cost;
                if(k1 <= k && res[currNode] > currCost){
                    q.add(new Pair<>(currNode, new Pair<>(currCost, k1+1)));
                    res[currNode] =  currCost;
                }
            }
        }
        return res[dst] == Integer.MAX_VALUE ? -1 : res[dst];
    }
    //33. Network Delay Time
    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<Pair>> adj = new ArrayList<>();
        for(int i=0;i<=n;i++)
            adj.add(new ArrayList<>());

        for(int[] time : times){
            int u = time[0];
            int v = time[1];
            int tm = time[2];
            adj.get(u).add(new Pair<>(v, tm));
        }

        Queue<Pair> q = new LinkedList<>();
        int[] dist = new int[n+1];

        Arrays.fill(dist, Integer.MAX_VALUE);
        q.add(new Pair<>(k, 0));
        dist[k] = 0;

        while(!q.isEmpty()){
            Pair<Integer, Integer> p = q.remove();
            int node = p.first;
            int time = p.second;
            for(Pair<Integer, Integer> curr : adj.get(node)){
                int currNode = curr.first;
                int currTime = curr.second + time;
                if(currTime < dist[currNode]){
                    dist[currNode] = currTime;
                    q.add(new Pair<>(currNode, currTime));
                }
            }
        }
        int ans = 0;
        for(int i=1;i<=n;i++){
            if(dist[i] == Integer.MAX_VALUE)
                return -1;
            else
                ans = Math.max(ans, dist[i]);
        }
        return ans;
    }
    //34. Number of Ways to Arrive at Destination
    public int countPaths(int n, int[][] roads) {
        int mod = (int) (1e9 + 7);
        List<List<Pair>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());

        for(int[] road : roads){
            int u = road[0];
            int v = road[1];
            long cost = road[2];
            adj.get(u).add(new Pair<>(v, cost));
            adj.get(v).add(new Pair<>(u, cost));
        }

        int[] ways = new int[n];
        long[] dist = new long[n];

        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1;

        Queue<Pair<Integer, Long>> pq = new PriorityQueue<>((a, b) -> Long.compare(a.second, b.second));
        pq.add(new Pair<>(0, 0L));

        while(!pq.isEmpty()){
            Pair<Integer, Long> p = pq.remove();
            int node = p.first;
            long cost = p.second;

            for(Pair<Integer, Long> curr : adj.get(node)){
                int currNode = curr.first;
                long currCost = curr.second + cost;
                if(currCost < dist[currNode]){
                    dist[currNode] = currCost;
                    pq.add(new Pair<>(currNode, currCost));
                    ways[currNode] = ways[node];
                }else if(currCost == dist[currNode]){
                    ways[currNode] = (ways[currNode] + ways[node]) % mod;
                }
            }
        }
        return ways[n-1] % mod;
    }
    //35. Minimum Multiplications to reach End
    int minimumMultiplications(int[] arr, int start, int end) {
        int mod = (int)(1e5);
        int[] steps = new int[mod];
        Queue<Pair<Integer, Integer>> pq = new LinkedList<>();

        Arrays.fill(steps, Integer.MAX_VALUE);
        pq.add(new Pair<>(0, start));
        steps[start] = 0;
        while(!pq.isEmpty()){
            Pair<Integer, Integer> p = pq.remove();
            int step = p.first;
            int val = p.second;

            for (int j : arr) {
                int newVal = (val * j) % mod;

                if (steps[newVal] > step + 1) {
                    steps[newVal] = step + 1;
                    pq.add(new Pair<>(step + 1, newVal));
                }

            }
        }
        return steps[end] == Integer.MAX_VALUE ? -1 : steps[end];
    }

    //36. Bellman-Ford
    int[] bellmanFord(int V, int[][] edges, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, (int) 1e8);
        dist[src] = 0;

        for(int i=0;i<V-1;i++){
            for(int[] edge : edges){
                int u = edge[0];
                int v = edge[1];
                int cost = edge[2];
                if(dist[u] != 1e8 && dist[u] + cost < dist[v])
                    dist[v] = dist[u]+cost;
            }
        }

        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            if(dist[u] != 1e8 && dist[u] + cost < dist[v]){
                return new int[]{-1};
            }
        }
        return dist;
    }
    //37. Floyd Warshal Algorithm -> when weight can be negative
    public void shortestDistance(int[][] mat) {
        int n = mat.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(mat[i][j] == -1){
                    mat[i][j] = (int) 1e9;
                }
                if(i == j)
                    mat[i][j] = 0;
            }
        }

        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    mat[i][j] = Math.min(mat[i][j], mat[i][k] + mat[k][j]);
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(mat[i][j] == 1e9){
                    mat[i][j] = -1;
                }
            }
        }
    }
    //-------------- using dikstra's algorithm -> when weight is positive-------------------
    public void shortestDistance1(int[][] mat) {
        int n = mat.length;
        List<List<Pair>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());
        for(int node =0;node<n;node++){
            for(int curr =0;curr<n;curr++){
                if(node == curr || mat[node][curr] == -1)
                    continue;
                adj.get(node).add(new Pair<>(curr, mat[node][curr]));
            }
        }

        for(int i=0;i<n;i++){
            mat[i] = dijkstra(adj, i, n);
        }
    }
    int[] dijkstra(List<List<Pair>> adj, int src, int v){
        int[] dist = new int[v];
        Queue<Pair<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> a.second - b.second);

        Arrays.fill(dist, (int)1e9);
        dist[src] = 0;
        pq.add(new Pair<>(src, 0));

        while(! pq.isEmpty()){
            Pair<Integer, Integer> p = pq.remove();
            int node = p.first;
            int cost = p.second;

            for(Pair<Integer, Integer> curr : adj.get(node)){
                int currNode = curr.first;
                int currCost = curr.second + cost;
                if(currCost < dist[currNode]){
                    dist[currNode] = currCost;
                    pq.add(new Pair<>(currNode, currCost));
                }
            }
        }
        for(int i=0;i<v;i++){
            if(dist[i] == 1e9)
                dist[i] = -1;
        }
        return dist;
    }
    //38.  Find the City With the Smallest Number of Neighbors at a Threshold Distance
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        List<List<Pair>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());

        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            adj.get(u).add(new Pair(v, cost));
            adj.get(v).add(new Pair(u, cost));
        }
        int minCount = n+1;
        int index = 0;
        for(int i=0;i<n;i++){
            int count = dijkstra(adj, i, n, distanceThreshold);
            if(count <= minCount){
                minCount = count;
                index = i;
            }
        }
        return index;
    }
    int dijkstra(List<List<Pair>> adj, int src, int v, int th){
        int[] dist = new int[v];
        Queue<Pair> q = new LinkedList<>();

        Arrays.fill(dist, (int)1e9);
        q.add(new Pair<>(src, 0));
        dist[src] = 0;

        while(!q.isEmpty()){
            Pair<Integer, Integer> p = q.remove();
            int node = p.first;
            int cost = p.second;

            for(Pair<Integer, Integer> curr : adj.get(node)){
                int currNode = curr.first;
                int currCost = curr.second + cost;

                if(currCost <= th && currCost < dist[currNode]){
                    dist[currNode] = currCost;
                    q.add(new Pair<>(currNode, currCost));
                }
            }
        }
        int count = 0;
        for(int i=0;i<v;i++){
            if(dist[i] != 1e9)
                count++;
        }
        return count;
    }
    //39. Minimum Spanning Tree
    static int spanningTree(int V, int E, List<List<int[]>> adj) {
        Queue<Pair<Integer,Pair<Integer, Integer>>> pq = new PriorityQueue<>((a, b)-> a.first - b.first);
        int[] vis = new int[V];

        pq.add(new Pair<>(0, new Pair<>(0, -1)));
        int weight = 0;

        while(!pq.isEmpty()){
            Pair<Integer, Pair<Integer, Integer>> p = pq.remove();
            int cost = p.first;
            int node = p.second.first;
            int parent = p.second.second;
            if(vis[node] == 1)
                continue;

            if(parent != -1 && vis[node] == 0){
                System.out.println(parent + " - "+ node); //used to print spanning tree
            }
            vis[node] = 1;
            weight += cost;

            for(int[] child : adj.get(node)){
                int childNode = child[0];
                int childCost = child[1];
                if(vis[childNode] == 0){
                    pq.add(new Pair<>(childCost, new Pair<>(childNode, node)));
                }
            }
        }
        return weight;
    }
    //------------------- Minimum spanning tree using Disjoint set (krunkal's algorithm) -------------------------------
    class DS{
        int node, adjNode, cost;
        DS(int n, int a, int c){
            node = n;
            adjNode = a;
            cost = c;
        }
    }
    int spanningTree1(int V, int E, List<List<int[]>> adj) {
        DisjointSet set = new DisjointSet(V);
        List<DS> mat = new ArrayList<>();
        for(int i=0;i<adj.size();i++){
            for(int j=0;j<adj.get(i).size();j++){
                int node = i;
                int adjNode = adj.get(i).get(j)[0];
                int cost = adj.get(i).get(j)[1];
                mat.add(new DS(node, adjNode, cost));
            }
        }
        mat.sort((a, b) -> a.cost - b.cost);

        int wt = 0;
        for (DS ds : mat) {
            int u = ds.node;
            int v = ds.adjNode;
            int cost = ds.cost;
            if (set.findUParent(u) == set.findUParent(v)) {
                continue;
            }
            wt += cost;
            set.unionBySize(u, v);
        }
        return wt;
    }
    //40. Number of operations to make network connected
    public int makeConnected(int n, int[][] connections) {
        DisjointSet set = new DisjointSet(n);
        int extraEdge = 0;

        for(int[] conn : connections){
            int u = conn[0];
            int v = conn[1];
            if(set.findUParent(u) != set.findUParent(v)){
                set.unionBySize(u, v);
            }else
                extraEdge ++;
        }

        int totalComponent =0;
        for(int i=0;i<n;i++){
            if(set.parent.get(i) == i)
                totalComponent ++;
        }
        if(totalComponent-1 <= extraEdge)
            return totalComponent -1;
        return -1;
    }
    //41. Most stones removed with same row or column
    public int removeStones(int[][] stones) {
        int n =0, m =0;
        int totalStones = stones.length;
        for(int[] stone : stones){
            int r = stone[0];
            int c = stone[1];
            n = Math.max(r, n);
            m = Math.max(c, m);
        }
        DisjointSet set = new DisjointSet(n+m+1);
        Set<Integer> hs = new HashSet<>();

        for(int[] stone : stones){
            int nodeRow = stone[0];
            int nodeCol = stone[1] + n + 1; // to differentiate between row and column (colume in between n+1 to n+m)
            set.unionBySize(nodeRow, nodeCol);
            hs.add(nodeRow);
            hs.add(nodeCol);
        }
        int count = 0;
        for(int node : hs){
            if(set.findUParent(node) == node)
                count++;
        }
        return (totalStones - count);
    }
    //42. Accounts Merge
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        Map<String, Integer> mailIndex = new HashMap<>();
        DisjointSet set = new DisjointSet(n);
        for(int i=0;i<n;i++){
            for(int j=1;j<accounts.get(i).size();j++){

                int node = i;
                String email = accounts.get(i).get(j);

                if(mailIndex.containsKey(email)){
                    set.unionBySize(mailIndex.get(email), node);
                }else
                    mailIndex.put(email, node);
            }
        }
        Map<Integer, Set<String>> mp = new HashMap<>();
        List<List<String>> res = new ArrayList<>();


        for(Map.Entry<String, Integer> entry : mailIndex.entrySet()){
            String email = entry.getKey();
            int index = set.findUParent(entry.getValue());
            if(mp.containsKey(index))
                mp.get(index).add(email);
            else
                mp.put(index, new TreeSet<>(List.of(email)));
        }

        for(Map.Entry<Integer, Set<String>> entry : mp.entrySet()){
            int index = entry.getKey();
            Set<String> st = entry.getValue();
            List<String> currList = new ArrayList<>(st);
            currList.add(0, accounts.get(index).get(0));
            res.add(currList);
        }
        return res;
    }
    //43. Number of Islands 2
    public static int[] numOfIslandsII(int n, int m, int[][] q) {
        int N = n * m;
        int[][] mat = new int[n][m];

        DisjointSet set = new DisjointSet(N);
        int[] res = new int[q.length];
        int count = 0;
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        for(int i=0;i<q.length;i++){

            int row = q[i][0];
            int col = q[i][1];
            mat[row][col] = 1;
            count++;

            int node = row * m + col;

            for(int j=0;j<4;j++){
                int r = row + delRow[j];
                int c = col + delCol[j];
                if(r >=0 && r <n && c>=0 && c <m){
                    if(mat[r][c] == 0)
                        continue;
                    int adjNode = r * m + c;

                    if(set.findUParent(node) != set.findUParent(adjNode)){
                        set.unionBySize(node, adjNode);
                        count--;
                    }
                }
            }
            res[i] = count;
        }
        return res;
    }
    //44. Making a Large Island
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        int res = 0;
        DisjointSet set = new DisjointSet(n*n);
        int[][] vis = new int[n][n];

        //Adding all connected items to disjoint set
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1 && vis[i][j] == 0)
                    res = Math.max(res, dfs(i, j, grid, vis, set));
            }
        }

        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        // Calculating the the max size of any component
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){

                if(grid[i][j] == 1)
                    continue;

                int count = 0;

                Set<Integer> up = new HashSet<>();
                for(int k =0;k<4;k++){
                    int r = i + delRow[k];
                    int c = j + delCol[k];
                    int adjNode = r * n + c;

                    if( r>= 0 && r < n && c >= 0 && c < n && grid[r][c] == 1){
                        int upAdj = set.findUParent(adjNode);
                        if(!up.contains(upAdj))
                            count += set.size.get(upAdj);
                        up.add(upAdj);
                    }
                }
                res = Math.max(res, count+1);
            }
        }
        return res;
    }
    int dfs(int row, int col, int[][] grid, int[][] vis, DisjointSet set){
        int n = grid.length;
        vis[row][col] = 1;
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        int node = row *n + col;
        int count = 0;

        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];
            int adjNode = r * n + c;
            if( r>= 0 && r < n && c >= 0 && c < n && vis[r][c] == 0 && grid[r][c] == 1){
                set.unionBySize(node, adjNode);
                count += dfs(r,c, grid, vis, set);
            }
        }
        return count +1;
    }
    //45. Swim in rising water
    public int swimInWater(int[][] grid) {
        Queue<Pair<Integer, Pair<Integer, Integer>>> pq = new PriorityQueue<>((a, b) -> a.first - b.first);
        int n = grid.length;
        pq.add(new Pair<>(grid[0][0], new Pair(0, 0)));
        int[][] vis = new int[n][n];
        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};
        int res = grid[0][0];
        while(!pq.isEmpty()){

            Pair<Integer, Pair<Integer, Integer>> p = pq.remove();
            int cost = p.first;
            int row = p.second.first;
            int col = p.second.second;
            vis[row][col] = 1;
            res = Math.max(res, cost);
            if(row == n-1 && col == n-1)
                return res;

            for(int i=0;i<4;i++){
                int r = row + delRow[i];
                int c = col + delCol[i];
                if(r >=0 && r <n && c >=0 && c < n && vis[r][c] == 0){
                    int currCost = Math.max(cost, grid[r][c]);
                    pq.add(new Pair<>(currCost, new Pair<>(r, c)));
                }
            }
        }
        return 0;
    }
    //46. Bridges in Graph (Tarjan's Algorithm) or Critical Connections in a network
    int time = 0;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> conn) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());
        for(int i=0;i<conn.size();i++){
            int u = conn.get(i).get(0);
            int v = conn.get(i).get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] vis = new int[n];
        int[] toin = new int[n];
        int[] low = new int[n];
        List<List<Integer>> bridge = new ArrayList<>();
        dfs(0, -1, adj, vis, toin, low, bridge);
        return bridge;
    }
    void dfs(int node, int parent, List<List<Integer>> adj, int[] vis, int[] toin, int[] low, List<List<Integer>> bridge){
        vis[node] = 1;
        toin[node] = time;
        low[node] = time;
        time ++;
        for(int child : adj.get(node)){
            if(child == parent) continue;

            if(vis[child] == 0){
                dfs(child, node, adj, vis, toin, low, bridge);
                low[node] = Math.min(low[child], low[node]);

                if(low[child] > toin[node]){
                    bridge.add(new ArrayList<>(List.of(node, child)));
                }
            }else{
                low[node] = Math.min(low[child], low[node]);
            }
        }
    }
    //47. Articulation Points 1
    public ArrayList<Integer> articulationPoints(int V,ArrayList<ArrayList<Integer>> adj){
        int[] vis = new int[V];
        int[] min = new int[V];
        int[] toin = new int[V];
        int[] ans = new int[V];
        ArrayList<Integer> res = new ArrayList<>();
        dfs(0, -1, adj, vis, min, toin, ans);
        for(int i=0;i<V;i++){
            if(ans[i] == 1)
                res.add(i);
        }
        if(res.isEmpty())
            res.add(-1);
        return res;
    }
    void dfs(int node, int parent, ArrayList<ArrayList<Integer>> adj, int[] vis, int[] min, int[] toin, int[] ans){
        vis[node] = 1;
        min[node] = toin[node] = time;
        time ++;
        int noOfChildren = 0;
        for(int child : adj.get(node)){
            if(child == parent) continue;
            if(vis[child] == 0){
                noOfChildren++;
                dfs(child, node, adj, vis, min, toin, ans);

                min[node] = Math.min(min[node], min[child]);
                if(min[child] >= toin[node] && parent != -1)
                    ans[node] = 1;
            }else{
                min[node] = Math.min(min[node], toin[child]);
            }
        }
        if(noOfChildren > 1 && parent == -1){
            ans[0] = 1;
        }
    }
    //48. Kosaraju's Algorithm
    public int kosaraju(ArrayList<ArrayList<Integer>> adj) {
        int n = adj.size();
        Stack<Integer> st = new Stack<>();

        int[] vis = new int[n];
        for(int i=0;i<n;i++){
            if(vis[i] == 0)
                dfs(i, adj, st, vis);
        }
        //Reversing the adj
        ArrayList<ArrayList<Integer>> newAdj = new ArrayList<>();
        for(int i=0;i<n;i++)
            newAdj.add(new ArrayList<>());
        for(int i=0;i<n;i++){
            for(int child : adj.get(i)){
                newAdj.get(child).add(i);
            }
        }
        // count total scc
        Arrays.fill(vis, 0);
        int res = 0;
        while(!st.isEmpty()){
            int node = st.pop();
            if(vis[node] == 1) continue;
            dfs(node, newAdj, vis);
            res++;
        }
        return res;
    }
    void dfs(int node, ArrayList<ArrayList<Integer>> adj, Stack<Integer> st, int[] vis){
        vis[node] = 1;
        for(int child : adj.get(node)){
            if(vis[child] == 0)
                dfs(child, adj, st, vis);
        }
        st.add(node);
    }

    void dfs(int node, ArrayList<ArrayList<Integer>> adj, int[] vis){
        vis[node] = 1;
        for(int child : adj.get(node)){
            if(vis[child] == 0)
                dfs(child, adj, vis);
        }
    }

}
