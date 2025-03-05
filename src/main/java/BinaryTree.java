import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.String;

import DS.*;

public class BinaryTree {
    public static void main(String[] args) {
    }
    //1. Binary Tree Traversals in Binary Tree
    public List<List<Integer>> getTreeTraversal(TreeNode root) {
        // Write your code here.
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();
        List<Integer> post = new ArrayList<>();

        dfs(root, pre,in, post);

        res.add(in);
        res.add(pre);
        res.add(post);
        return res;
    }
    void dfs(TreeNode root, List<Integer> pre, List<Integer> in, List<Integer> post){
        if(root == null)
            return;
        pre.add(root.data);
        dfs(root.left, pre, in, post);
        in.add(root.data);
        dfs(root.right, pre, in, post);
        post.add(root.data);
    }
    //2. preOrder Iteration
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        List<Integer> res = new ArrayList<>();

        while(!st.isEmpty() || root != null){
            while(root != null){
                res.add(root.data);
                st.add(root);
                root = root.left;
            }
            root = st.pop();
            root = root.right;
        }
        return res;
    }
    //3. inOrder Iteration
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        List<Integer> res = new ArrayList<>();
        while(root != null || !st.isEmpty()){
            while(root != null){
                st.add(root);
                root = root.left;
            }
            root = st.pop();
            res.add(root.data);
            root = root.right;
        }
        return res;
    }
    //4. postOrder Iteration
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        List<Integer> res = new ArrayList<>();
        while(root != null || !st.isEmpty()){
            while(root != null){
                res.add(root.data);
                st.push(root);
                root = root.right;
            }
            root = st.pop();
            root = root.left;
        }
        Collections.reverse(res);
        return res;
    }
    //5. Binary Tree Level Order Traversal
    public List<List<Integer>> levelOrder(TreeNode root) {
        if(root == null)
            return Collections.emptyList();
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int size = q.size();
            List<Integer> list = new ArrayList<>();
            for(int i=0;i<size;i++){
                root = q.remove();
                list.add(root.data);
                if(root.left != null)
                    q.add(root.left);
                if(root.right != null)
                    q.add(root.right);
            }
            res.add(new ArrayList<>(list));
        }
        return res;
    }
    //6. Maximum Depth/ Height of Binary Tree
    public int maxDepth(TreeNode root) {
        if(root == null)
            return 0;
        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        return 1+ Math.max(leftHeight, rightHeight);
    }
    //7. Check if a Binary Tree is Height Balanced or not
    boolean res;
    public boolean isBalanced(TreeNode root) {
        res = true;
        solve(root);
        return res;
    }
    int solve(TreeNode root){
        if(root == null)
            return 0;
        int leftHeight = solve(root.left);
        int rightHeight = solve(root.right);
        if(Math.abs(leftHeight - rightHeight) > 1)
            res = false;
        return 1 + Math.max(leftHeight, rightHeight);
    }
    //8. Diameter of Binary Tree
    public int diameterOfBinaryTree(TreeNode root) {
        AtomicInteger res = new AtomicInteger(0);
        solve(root, res);
        return res.get();
    }
    int solve(TreeNode root, AtomicInteger res){
        if(root == null)
            return 0;
        AtomicInteger ans = new AtomicInteger(1);
        ans.compareAndSet(2,4);

        int left = solve(root.left, res);
        int right = solve(root.right, res);

        res.set(Math.max(res.get(), left + right));
        return 1 + Math.max(left, right);
    }
    //9. Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {
        AtomicInteger res = new AtomicInteger(Integer.MIN_VALUE);
        solve1(root, res);
        return res.get();
    }
    int solve1(TreeNode root, AtomicInteger res){
        if(root == null)
            return 0;
        int left = Math.max(0, solve(root.left, res));
        int right = Math.max(0, solve(root.right, res));

        res.set(Math.max(left + right + root.data, res.get()));
        return root.data + Math.max(left, right);
    }
    //10. Check if two trees are identical or not
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null)
            return true;
        else if(p == null || q == null)
            return false;
        else
            return p.data == q.data && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    //11. Binary Tree Zigzag Level Order Traversal
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if(root == null)
            return Collections.emptyList();
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean fromLeft = true;
        while(!q.isEmpty()){

            int size = q.size();
            List<Integer> list = new ArrayList<>();

            for(int i=0;i<size;i++){

                root = q.remove();
                list.add(root.data);

                if(root.left != null)
                    q.add(root.left);
                if(root.right != null)
                    q.add(root.right);
            }
            if(fromLeft){
                fromLeft = false;
            }else{
                Collections.reverse(list);
                fromLeft = true;
            }
            res.add(new ArrayList<>(list));
        }
        return res;
    }
    //12. Tree Boundary Traversal
    ArrayList<Integer> boundaryTraversal(TreeNode node) {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(node.data);
        if(isLeaf(node))
            return res;

        left(node.left, res);
        leaf(node, res);

        Stack<Integer> st = new Stack<>();
        right(node.right, st);

        while(!st.isEmpty())
            res.add(st.pop());
        return res;
    }
    void left(TreeNode root, ArrayList<Integer> res){
        if(root == null || isLeaf(root))
            return;
        res.add(root.data);
        if(root.left != null){
            left(root.left, res);
        }else{
            left(root.right, res);
        }
    }
    void leaf(TreeNode root, ArrayList<Integer> res){
        if(root == null)
            return;
        if(isLeaf(root)){
            res.add(root.data);
        }
        leaf(root.left, res);
        leaf(root.right, res);
    }
    void right(TreeNode root, Stack<Integer> st){
        if(root == null || isLeaf(root))
            return;
        st.add(root.data);
        if(root.right != null) {
            right(root.right, st);
        }else {
            right(root.left, st);
        }
    }
    boolean isLeaf(TreeNode node){
        return node.left == null && node.right == null;
    }
    //13. Vertical Order Traversal of a Binary Tree
    class DS implements Comparable<DS>{
        public int val, x, y;
        public DS(int val, int x, int y){
            this.val = val;
            this.x = x;
            this.y = y;
        }
        public int compareTo(DS that){
            if(this.x != that.x)
                return Integer.compare(this.x, that.x);
            else if(this.y != that.y)
                return Integer.compare(this.y, that.y);
            else
                return Integer.compare(this.val, that.val);
        }
    }
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<DS> list = new ArrayList<>();
        dfs(root, 0 ,0 ,list);
        Collections.sort(list);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> currLevel = new ArrayList<>();
        int level = list.get(0).x;
        for(int i=0;i<list.size();i++){
            if(list.get(i).x == level){
                currLevel.add(list.get(i).val);
            }else{
                res.add(new ArrayList<>(currLevel));
                level = list.get(i).x;
                currLevel.clear();
                currLevel.add( list.get(i).val);
            }
        }
        res.add(new ArrayList<>(currLevel));
        return res;
    }
    void dfs(TreeNode root, int x, int y, List<DS> list){
        if(root == null)
            return;
        list.add(new DS(root.data, x, y));
        dfs(root.left, x-1, y+1, list);
        dfs(root.right, x+1, y+1, list);
    }
    //14. Top View of Binary Tree
    class DS1{
        TreeNode node;
        int x;
        DS1(TreeNode node, int x){
            this.node = node;
            this.x = x;
        }
    }
    ArrayList<Integer> topView(TreeNode root) {
        Queue<DS1> q = new LinkedList<>();
        Map<Integer,DS1> mp = new TreeMap<>();
        q.add(new DS1(root, 0));

        while(!q.isEmpty()){
            DS1 ds = q.remove();
            if(ds.node.left != null){
                q.add(new DS1(ds.node.left, ds.x -1));
            }
            if(ds.node.right != null){
                q.add(new DS1(ds.node.right, ds.x+1));
            }
            if(!mp.containsKey(ds.x)){
                mp.put(ds.x, ds);
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        for(Map.Entry<Integer, DS1> entry : mp.entrySet()){
            int x = entry.getKey();
            DS1 ds = entry.getValue();
            res.add(ds.node.data);
        }
        return res;
    }
    //15. Bottom View of Binary Tree
    public ArrayList <Integer> bottomView(TreeNode root)
    {
        // Code here
        Queue<DS1> q = new LinkedList<>();
        Map<Integer, DS1> mp = new TreeMap<>();
        q.add(new DS1(root, 0));

        while(!q.isEmpty()){
            DS1 ds = q.remove();
            if(ds.node.left != null)
                q.add(new DS1(ds.node.left, ds.x -1));
            if(ds.node.right != null)
                q.add(new DS1(ds.node.right, ds.x+1));
            mp.put(ds.x, ds);
        }
        ArrayList<Integer> res = new ArrayList<>();
        for(Map.Entry<Integer,DS1> entry : mp.entrySet()){
            res.add(entry.getValue().node.data);
        }
        return res;
    }
    //16. Right view of binary tree
    public List<Integer> rightSideView(TreeNode root) {
        if(root == null)
            return Collections.emptyList();
        List<Integer> res= new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                root = q.remove();
                if(root.left != null)
                    q.add(root.left);
                if(root.right != null)
                    q.add(root.right);
                if(i == size -1)
                    res.add(root.data);
            }
        }
        return res;
    }
    //17. Symmetric Binary Tree
    public boolean isSymmetric(TreeNode root) {
        return solve(root.left, root.right);
    }
    boolean solve(TreeNode node1, TreeNode node2){
        if(node1 == null && node2 == null)
            return true;
        else if(node1 == null || node2 == null)
            return false;
        else
            return node1.data == node2.data && solve(node1.left, node2.right) &&
                    solve(node1.right, node2.left);
    }
    //18. Root to Leaf Paths
    ArrayList<ArrayList<Integer>> Paths(TreeNode root) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> ds = new ArrayList<>();
        solve2(root, ds,res);
        return res;
    }
    void solve2(TreeNode root, ArrayList<Integer> ds, ArrayList<ArrayList<Integer>> res){
        if(root == null)
            return;

        ds.add(root.data);
        if(isLeaf(root)){
            res.add(new ArrayList<>(ds));
        }
        solve2(root.left, ds, res);
        solve2(root.right, ds, res);
        ds.remove(ds.size()-1);
    }
    //19. LCA in Binary Tree
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left == null)
            return right;
        else if(right == null)
            return left;
        else
            return root;
    }
    //20. Maximum Width of Binary Tree
    public int widthOfBinaryTree(TreeNode root) {
        Queue<DS1> q = new LinkedList<>();
        int res = 0;
        q.add(new DS1(root, 0));
        while(!q.isEmpty()){
            int size = q.size();
            int prev = q.peek().x;
            int a = 0, b = 0;
            for(int i=0;i<size;i++){
                DS1 ds = q.remove();
                if(i == 0)
                    a = ds.x;
                if(i == size -1)
                    b = ds.x;

                if(ds.node.left != null)
                    q.add(new DS1(ds.node.left, (ds.x - prev)*2 +1));
                if(ds.node.right != null)
                    q.add(new DS1(ds.node.right, (ds.x - prev)*2 + 2));
            }
            res = Math.max(res, b - a + 1);
        }
        return res;
    }
    //21. Check for Children Sum Property
    int isSumProperty(TreeNode root) {
        AtomicInteger res = new AtomicInteger(1);
        solve3(root, res);
        return res.get();
    }
    int solve3(TreeNode root, AtomicInteger res){
        if(root == null)
            return 0;
        if(isLeaf(root))
            return root.data;
        int left = solve3(root.left, res);
        int right = solve3(root.right, res);
        if(root.data != left + right)
            res.set(0);
        return root.data;
    }
    //22. Print all the Nodes at a distance of K in a Binary Tree
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> mp = new HashMap<>();
        populatePC(root, null, mp);
        List<Integer> res = new ArrayList<>();
        solve(target, null, k, mp, res);
        return res;
    }
    void populatePC(TreeNode child, TreeNode parent, Map<TreeNode, TreeNode> mp){
        if(child == null)
            return;
        mp.put(child, parent);
        populatePC(child.left, child, mp);
        populatePC(child.right, child, mp);
    }

    void solve(TreeNode curr, TreeNode prev, int k, Map<TreeNode, TreeNode> mp, List<Integer> res){
        if(curr == null){
            return;
        }
        if(k == 0){
            res.add(curr.data);
            return;
        }

        if(curr.left != prev){
            solve(curr.left, curr, k-1, mp, res);
        }
        if(curr.right != prev){
            solve(curr.right,curr, k-1, mp, res);
        }

        if(mp.get(curr) != prev){
            solve(mp.get(curr), curr, k-1, mp, res);
        }
    }
    //23. Minimum time taken to BURN the Binary Tree from a Node
    static TreeNode tar;
    public static int minTime(TreeNode root, int target) {
        Map<TreeNode, TreeNode> mp = new HashMap<>();
        populateCPAndTarget(root, null, mp, target);
        return dfs(tar, null, mp)-1;
    }
    static int dfs(TreeNode curr, TreeNode prev, Map<TreeNode,TreeNode> mp){
        if(curr == null)
            return 0;
        int time = 0;
        if(curr.left != prev)
            time = Math.max(time, dfs(curr.left, curr, mp));
        if(curr.right != prev)
            time = Math.max(time, dfs(curr.right, curr, mp));
        if(mp.get(curr) != prev)
            time = Math.max(time, dfs(mp.get(curr), curr, mp));
        return time + 1;
    }
    static void populateCPAndTarget(TreeNode curr, TreeNode parent, Map<TreeNode, TreeNode> mp, int target){
        if(curr == null)
            return;
        mp.put(curr, parent);
        populateCPAndTarget(curr.left, curr, mp, target);
        populateCPAndTarget(curr.right, curr, mp, target);
        if(curr.data == target)
            tar = curr;
    }
    //24. Count total Nodes in a COMPLETE Binary Tree
    public int countNodes(TreeNode root) {
        int height = findHeight(root);
        int count = 0;

        while(root != null){
            if(findHeight(root.right) == height -1){
                count += (int) Math.pow(2, height-1); // adding left subtree Height and going right
                root = root.right;
            }else{
                count += (int) Math.pow(2, height-2);// adding right subtree height -1  ka nodes
                root = root.left;
            }
            height --;
        }
        return count;
    }
    int findHeight(TreeNode root){
        if(root == null)
            return 0;
        return findHeight(root.left) + 1;
    }
    //----------------------------------------------OR-------------------------
    public int countNodes1(TreeNode root) {
        if(root == null)
            return 0;

        int left = leftHeight(root.left);
        int right = rightHeight(root.right);
        if(left == right)
            return ((2<<left) -1);
        else
            return 1 + countNodes1(root.left)+countNodes1(root.right);
    }
    int leftHeight(TreeNode left){
        if(left == null)
            return 0;
        return 1 + leftHeight(left.left);
    }
    int rightHeight(TreeNode right){
        if(right == null)
            return 0;
        return 1 + rightHeight(right.right);
    }
    //25.Unique Binary Tree Requirements(tree constructed with the help of either of them) (preOrder = 1, inOrder = 2, postOrder = 3)
    public static boolean isPossible(int a, int b){
        return a+b == 3 || a+b == 5;
    }
    //26. Construct Binary Tree from Inorder and Preorder Traversal
    int index;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        index = 0;
        return solve(preorder, inorder, 0, inorder.length-1);
    }
    TreeNode solve(int[] preOrder, int[] inOrder, int start, int end){
        if(start > end || index == preOrder.length)
            return null;
        TreeNode node = new TreeNode(preOrder[index]);
        index ++;

        int i = start;
        while(i <= end && inOrder[i] != preOrder[index-1])
            i++;

        node.left = solve(preOrder, inOrder, start, i-1);
        node.right = solve(preOrder, inOrder, i+1, end);
        return node;
    }
    //27. Construct Binary Tree from Inorder and Postorder Traversal
    int index1;
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        int n = postorder.length;
        index1 = n-1;
        return solve(inorder, 0, n-1, postorder);
    }
    TreeNode solve(int[] inOrder, int start, int end, int[] postOrder){
        if(start > end || index1 < 0)
            return null;
        TreeNode node = new TreeNode(postOrder[index1]);
        index1--;

        int i = start;
        while(i <= end && inOrder[i] != postOrder[index1+1])
            i++;
        node.right = solve(inOrder, i+1, end, postOrder);
        node.left = solve(inOrder, start, i-1, postOrder);
        return node;
    }
    //28. Serialize and Deserialize Binary Tree
    public String serialize(TreeNode root) {
        if(root == null)
            return "";
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        StringBuilder sb = new StringBuilder();

        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                root = q.remove();
                if(root == null)
                    sb.append("#").append(",");
                else{
                    sb.append(root.data).append(",");
                    q.add(root.left);
                    q.add(root.right);
                }
            }
        }
        return sb.substring(0, sb.length()-1).toString();
    }
    public TreeNode deserialize(String data) {
        if(data.trim().isEmpty())
            return null;

        String[] arr = data.split(",");
        int index = 1;
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
        q.add(root);
        while(!q.isEmpty() && index < arr.length){
            int size = q.size();
            for(int i=0;i<size;i++){

                TreeNode node = q.remove();
                if(arr[index].equals("#"))
                    node.left = null;
                else{
                    node.left = new TreeNode(Integer.parseInt(arr[index]));
                    q.add(node.left);
                }
                index ++;
                if(arr[index].equals("#"))
                    node.left = null;
                else{
                    node.right = new TreeNode(Integer.parseInt(arr[index]));
                    q.add(node.right);
                }
                index++;
            }
        }
        return root;
    }
    //29. Flatten Binary Tree to Linked List
    public void flatten(TreeNode root) {
        while(root != null){
            if(root.left != null){
                TreeNode node = root.left;
                while(node.right != null)
                    node = node.right;
                node.right = root.right;
                root.right = root.left;
                root.left = null;
            }
            root = root.right;
        }
    }

    //30. 3425. Longest Special Path // Sliding window on trees
    int minLen = (int) 1e9, maxSum = 0;
    public int[] longestSpecialPath(int[][] edges, int[] nums) {
        int n = nums.length;
        List<List<int[]>> adj = new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }

        for(var edge : edges){
            int u = edge[0];
            int v = edge[1];
            int len = edge[2];
            adj.get(u).add(new int[]{v, len});
            adj.get(v).add(new int[]{u, len});
        }
        int[] path = new int[n];
        int pathSum = 0;
        int start = 0, end = 0;
        Map<Integer, Integer> colorIndMp = new HashMap<>();
        solve(0, -1, adj, path, start, end, pathSum, colorIndMp, nums);
        return new int[]{maxSum, minLen};
    }

    void solve(int node, int parent, List<List<int[]>> adj, int[] path, int start, int end, int pathSum, Map<Integer, Integer> colorIndMp, int[] color){

        int prevClrInd = colorIndMp.getOrDefault(color[node], -1);

        while(start <= prevClrInd){
            pathSum -= path[start++];
        }

        colorIndMp.put(color[node], end);

        if(maxSum < pathSum){
            maxSum = pathSum;
            minLen = end-start+1;
        }else if(maxSum == pathSum){
            minLen = Math.min(minLen, end-start+1);
        }


        for(var curr : adj.get(node)){
            int currNode = curr[0];
            int currLen = curr[1];
            if(currNode == parent)
                continue;

            pathSum += currLen;
            path[end] = currLen;

            solve(currNode, node, adj, path, start, end+1, pathSum, colorIndMp, color);

            pathSum -= currLen;
        }
        colorIndMp.remove(color[node]);
        if(prevClrInd >=0)
            colorIndMp.put(color[node], prevClrInd);
    }

    //31.  Populating Next Right Pointers in Each Node
    public TreeNode connect(TreeNode root) {
        if(root == null)
            return root;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            TreeNode node = q.remove();

            if(node.left != null){
                node.left.next = node.right;
            }

            if(node.right != null && node.next != null)
                node.right.next = node.next.left;

            if(node.left != null)
                q.add(node.left);
            if(node.right != null)
                q.add(node.right);
        }
        return root;
    }
    
}
