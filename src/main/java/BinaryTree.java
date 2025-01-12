import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    //25. 
}
