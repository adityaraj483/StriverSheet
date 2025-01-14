import DS.TreeNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BinarySearchTree {
    public static void main(String[] args) {

    }
    //1. Ceil in a Binary Search Tree
    int findCeil(TreeNode root, int key) {
        AtomicInteger res = new AtomicInteger(-1);
        solve(root, key, res);
        return res.get();
    }
    void solve(TreeNode root, int key, AtomicInteger res){
        if(root == null)
            return;
        if(root.data >=key){
            res.set(root.data);
            solve(root.left, key, res);
        }else
            solve(root.right, key, res);
    }
    //2. Floor in BST
    public static int floor(TreeNode root, int x) {
        // Code here
        AtomicInteger res = new AtomicInteger(-1);
        solve1(root, x, res);
        return res.get();
    }
    static void solve1(TreeNode root, int x, AtomicInteger res){
        if(root == null)
            return;
        if(root.data <= x){
            res.set(root.data);
            solve1(root.right, x, res);
        }else
            solve1(root.left, x, res);
    }
    //3. Insert a given Node in Binary Search Tree
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root == null)
            return new TreeNode(val);
        if(root.data < val)
            root.right = insertIntoBST(root.right, val);
        else
            root.left = insertIntoBST(root.left, val);
        return root;
    }
    //4. Delete Node in a BST
    public TreeNode deleteNode(TreeNode root, int key) {
        if(root == null)
            return null;
        if(root.data == key){
            TreeNode curr = root;

            if(curr.right != null){

                TreeNode left = curr.left;
                curr = curr.right;
                while(curr.left != null)
                    curr = curr.left;

                curr.left = left;
                return root.right;

            }else if(root.left != null)
                return root.left;
            else
                return null;
        }
        if(root.data > key)
            root.left = deleteNode(root.left, key);
        else
            root.right = deleteNode(root.right, key);
        return root;
    }
    //5. Find K-th smallest/largest element in BST ( for largest we do {total nodes - k})
    public int kthSmallest(TreeNode root, int k) {
        AtomicInteger res = new AtomicInteger(-1);
        AtomicInteger cnt = new AtomicInteger(0);
        solve(root, k, cnt, res);
        return res.get();
    }
    void solve(TreeNode root, int k,  AtomicInteger cnt, AtomicInteger res){
        if(root == null)
            return;
        solve(root.left, k, cnt, res);
        cnt.set(cnt.get()+1);
        if(cnt.get() == k){
            res.set(root.data);
            return;
        }
        solve(root.right, k, cnt, res);
    }
    //6. Check if a tree is a BST or BT
    public boolean isValidBST(TreeNode root) {
        return checkBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    boolean checkBST(TreeNode root, long min, long max){
        if(root == null)
            return true;
        return root.data < max && root.data > min && checkBST(root.left, min, root.data) && checkBST(root.right, root.data, max);
    }
    //7.LCA or Lowest Common Ancestor of a Binary Search Tree
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q)
            return root;
        if(p.data < root.data && q.data < root.data)
            return lowestCommonAncestor(root.left, p, q);
        else if(p.data > root.data && q.data > root.data)
            return lowestCommonAncestor(root.right, p, q);
        else
            return root;
    }
    //8. Construct Binary Search Tree from Preorder Traversal
    int index;
    public TreeNode bstFromPreorder(int[] preorder) {
        return solve2(preorder, 0, preorder.length-1);
    }
    TreeNode solve2(int[] arr, int start, int end){
        if(index == arr.length || start > end)
            return null;

        TreeNode node = new TreeNode(arr[index]);

        int i = index +1;
        while(i <= end && arr[i] < arr[index])
            i++;
        index++;
        node.left = solve2(arr, start+1, i-1);
        node.right = solve2(arr, i, end);
        return node;
    }
    //------------------------------OR--------------------
    public TreeNode bstFromPreorder1(int[] preorder) {
        return solve3(preorder, Integer.MAX_VALUE);
    }
    TreeNode solve3(int[] arr, int bound){
        if(index == arr.length || arr[index] > bound)
            return null;

        TreeNode node = new TreeNode(arr[index++]);
        node.left = solve3(arr, arr[index-1]);
        node.right = solve3(arr, bound);
        return node;
    }
    //9. Inorder Successor in BST
    public int inorderSuccessor(TreeNode root, TreeNode x) {
        int res = -1;
        while(root != null){
            if(root.data <= x.data){
                root = root.right;
            }else{
                res = root.data;
                root = root.left;
            }
        }
        return res;
    }
    //10. Inorder Predecessor in BST
    TreeNode findPredecessor(TreeNode root, TreeNode p) {
        TreeNode res = null;
        while(root != null){
            if(root.data < p.data){
                res = root;
                root = root.right;
            }else
                root = root.left;
        }
        return res;
    }
    //11. Binary Search Tree Iterator
    Stack<TreeNode> st;
    public void BSTIterator(TreeNode root) {
        st = new Stack<>();
        pushInStack(root);
    }
    public int next() {
        TreeNode curr = st.pop();
        pushInStack(curr.right);
        return curr.data;
    }
    public boolean hasNext() {
        return !st.isEmpty();
    }
    void pushInStack(TreeNode root){
        while(root != null){
            st.add(root);
            root = root.left;
        }
    }
    //12. Merge BSTs to Create Single BST
    public TreeNode canMerge(List<TreeNode> trees) {
        Set<Integer> leafNodes = new HashSet<>();
        for(TreeNode node : trees){
            if(node.left != null)
                leafNodes.add(node.left.data);
            if(node.right != null)
                leafNodes.add(node.right.data);
        }

        Map<Integer, TreeNode> rootNodes = new HashMap<>();

        TreeNode root = null;
        for(TreeNode node : trees){
            if(!leafNodes.contains(node.data) && root == null)
                root = node;
            else
                rootNodes.put(node.data, node);
        }
        if(root == null)
            return null;

        root = mergeBST(root, rootNodes);
        if(rootNodes.isEmpty() && isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE))
            return root;
        return null;
    }
    TreeNode mergeBST(TreeNode root, Map<Integer, TreeNode> rootNodes){
        if(root ==null)
            return null;

        if(rootNodes.containsKey(root.data)){
            TreeNode node = rootNodes.get(root.data);
            root.left = node.left;
            root.right = node.right;
            rootNodes.remove(root.data);
        }
        root.left = mergeBST(root.left, rootNodes);
        root.right = mergeBST(root.right, rootNodes);
        return root;
    }
    boolean isBST(TreeNode root, int min, int max){
        if(root == null)
            return true;
        return root.data > min && root.data < max && isBST(root.left, min, root.data) && isBST(root.right, root.data, max);
    }
    //13. Two sum in BST
    /*
      class Solution {
        class BSTIterator{
            Stack<TreeNode> min, max;
            BSTIterator(TreeNode root){
                min = new Stack<>();
                max = new Stack<>();
                insertMinBST(root);
                insertMaxBST(root);
            }
            boolean hasMin(){
                return !min.isEmpty();
            }
            int getMin(){
                TreeNode node = min.pop();
                insertMinBST(node.right);
                return node.data;
            }
            int min(){
                return min.peek().data;
            }
            void insertMinBST(TreeNode root){
                while(root != null){
                    min.add(root);
                    root = root.left;
                }
            }

            boolean hasMax(){
                return !max.isEmpty();
            }
            int getMax(){
                TreeNode node = max.pop();
                insertMaxBST(node.left);
                return node.data;
            }
            int max(){
                return max.peek().data;
            }
            void insertMaxBST(TreeNode root){
                while(root != null){
                    max.add(root);
                    root = root.right;
                }
            }
        }
        public boolean findTarget(TreeNode root, int k) {
            BSTIterator it = new BSTIterator(root);
            while(it.hasMin() && it.hasMax()){
                int low = it.min();
                int high = it.max();
                if(low >= high)
                    break;
                int sum = low + high;

                if(sum > k){
                    it.getMax();
                }else if(sum < k){
                    it.getMin();
                }else
                    return true;
            }
            return false;
        }
    }
   */
    //14. Recover BST | Correct BST with two nodes swapped
    TreeNode first, mid, last, prev;
    public void recoverTree(TreeNode root) {
        inOrder(root);
        if(last == null){
            int t = first.data;
            first.data = mid.data;
            mid.data = t;
        }else{
            int t = first.data;
            first.data = last.data;
            last.data = t;
        }
    }

    void inOrder(TreeNode root){
        if(root == null)
            return;
        inOrder(root.left);
        if(prev != null && prev.data > root.data){
            if(first == null){
                first = prev;
                mid = root;
            }else
                last = root;
        }
        prev = root;
        inOrder(root.right);
    }
    //15. Largest BST in Binary Tree
    class DS2{
        int max, min, count;
        DS2(int max, int min, int count){
            this.max = max;
            this.min = min;
            this.count = count;
        }
    }
    int largestBst(TreeNode root) {
        return solve4(root).count;
    }
    DS2 solve4(TreeNode root){
        if(root == null){
            return new DS2(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }

        DS2 left = solve4(root.left);
        DS2 right = solve4(root.right);

        if(root.data > left.max && root.data < right.min){
            int count = left.count + right.count;
            int max = Math.max(right.max, root.data);
            int min = Math.min(root.data, left.min);
            return new DS2(max, min, count+1);
        }
        return new DS2(Integer.MAX_VALUE, Integer.MIN_VALUE, Math.max(left.count, right.count));
    }
}
