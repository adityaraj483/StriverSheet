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

}
