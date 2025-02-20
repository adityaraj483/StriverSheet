import java.util.*;

public class StackAndQueue {
    public static void main(String[] args) {

    }
    //1. Implement Stack using Arrays
    class MyStack {
        private int[] arr;
        private int top;
        public MyStack() {
            arr = new int[1000];
            top = -1;
        }
        public void push(int x) {
            // Your Code
            arr[++top] = x;
        }
        public int pop() {
            // Your Code
            if(top == -1)
                return -1;
            return arr[top--];
        }
    }
    //2. Implement Circular Queue using Arrays
    class MyQueue {
        int start, end;
        int arr[] = new int[100005];
        int currSize ;
        int maxSize = 100005;

        MyQueue(){
            start = -1;
            end = -1;
            currSize = 0;
        }

        void push(int x){
            if(end == -1){
                start = 0;
                end = 0;
            }else
                end = (end +1) % maxSize;

            arr[end] = x;
            currSize ++;
        }
        int pop(){
            if(start == -1)
                return -1;
            int res = arr[start];
            if(currSize == 1){
                start = -1;
                end = -1;
            }else
                start = (start +1) % maxSize;

            currSize --;
            return res;
        }
    }
    //3. Implement Stack using single or one Queue
    class MyStack1 {
        Queue<Integer> q1;
        public MyStack1() {
            q1 = new LinkedList<>();
        }
        public void push(int x) {
            q1.add(x);
        }
        public int pop() {
            int size = q1.size();
            for(int i=0;i<size-1;i++){
                q1.add(q1.remove());
            }
            return q1.remove();
        }
        public int top() {
            int size = q1.size();
            for(int i=0;i<size-1;i++){
                q1.add(q1.remove());
            }
            int t = q1.peek();
            q1.add(q1.remove());
            return t;
        }
        public boolean empty() {
            return q1.isEmpty();
        }
    }
    //4. Implement Queue using Stack
    class MyQueue1 {
        Stack<Integer> st1 = new Stack<>();
        Stack<Integer> st2 = new Stack<>();
        public MyQueue1() {
        }
        public void push(int x) {
            st1.add(x);
        }
        public int pop() {
            if(st2.isEmpty()){
                while(!st1.isEmpty())
                    st2.add(st1.pop());
            }
            return st2.pop();
        }
        public int peek() {
            if(st2.isEmpty()){
                while(!st1.isEmpty())
                    st2.add(st1.pop());
            }
            return st2.peek();
        }
        public boolean empty() {
            return st1.isEmpty() && st2.isEmpty();
        }
    }
    //7. Check for balanced paranthesis
    public boolean isValid(String str) {
        Stack<Character> st = new Stack<>();
        for(char ch : str.toCharArray()){
            if(ch == '(' || ch == '{' || ch == '[')
                st.add(ch);
            else if(st.size() > 0){
                if(ch == ')' && st.peek() == '(')
                    st.pop();
                else if(ch == '}' && st.peek() == '{')
                    st.pop();
                else if(ch == ']' && st.peek() == '[')
                    st.pop();
                else
                    return false;
            }else
                return false;
        }
        return st.isEmpty();
    }
    //8. Implement Min Stack
    class MinStack1 {
        Stack<Integer> st1, st2;
        public MinStack1() {
            st1 = new Stack<>();
            st2 = new Stack<>();
        }
        public void push(int val) {
            st1.push(val);
            if(st2.isEmpty() || st2.peek() >= val)
                st2.add(val);
        }

        public void pop() {
            int val = st1.pop();
            if(val == st2.peek())
                st2.pop();

        }
        public int top() {
            return st1.peek();
        }

        public int getMin() {
            return st2.peek();
        }
    }
    //--------------------OR--------------------
    class MinStack {
        long mini;
        Stack<Long> st = new Stack<>();
        public MinStack() {
            mini = 0;
        }

        public void push(int val1) {
            long val = val1;
            if(st.isEmpty()){
                mini = val;
                st.add(val);
            }else{

                if(val < mini){
                    long el = 2L* val - mini;
                    st.add(el);
                    mini = val;
                }else
                    st.add(val);
            }
        }
        public void pop() {
            long el = st.pop();
            if(el < mini)
                mini = 2L * mini - el;
        }

        public int top() {
            long el = st.peek();
            if(el < mini)
                return (int) mini;
            else
                return (int) el;
        }

        public int getMin() {
            return (int) mini;
        }
    }

    //9. Infix to Postfix Conversion using Stack
    public static String infixToPostfix(String s) {
        // Your code here
        Map<Character, Integer> mp = new HashMap<>();
        mp.put('-', 1);
        mp.put('+', 1);
        mp.put('/', 2);
        mp.put('*', 2);
        mp.put('^', 3);
        mp.put('(', -1);
        Stack<Character> st = new Stack<>();

        StringBuilder res = new StringBuilder();

        for(char ch : s.toCharArray()){

            if(Character.isLetterOrDigit(ch)){
                res.append(ch);

            }else if( ch == '('){
                st.push(ch);

            }else if(ch == ')'){

                while(!st.isEmpty() && st.peek() != '(')
                    res.append(st.pop());

                st.pop();
            }else{
                while(!st.isEmpty() && mp.get(ch) <= mp.get(st.peek())){
                    res.append(st.pop());
                }
                st.push(ch);
            }
        }
        while(!st.isEmpty())
            res.append(st.pop());
        return res.toString();
    }
    //10. Prefix to Infix Conversion
    String preToInfix(String pre_exp) {
        Stack<String> st = new Stack<>();
        int i= pre_exp.length()-1;

        while(i>=0){
            char ch = pre_exp.charAt(i);
            if(Character.isLetterOrDigit(ch)){
                st.push(Character.toString(ch));
            }else{

                String val1 = st.pop();
                String val2 = st.pop();
                st.push("("+ val1 + ch + val2  +")");
            }
            i--;
        }
        return st.pop();
    }
    //11. Prefix to Postfix Conversion
    String preToPost(String pre_exp) {
        int i=pre_exp.length()-1;
        Stack<String> st = new Stack<>();
        while(i>=0){
            char ch = pre_exp.charAt(i);

            if(Character.isLetterOrDigit(ch))
                st.push(Character.toString(ch));
            else{

                String val1 = st.pop();
                String val2 = st.pop();

                st.push(val1 + val2 + ch);
            }
            i--;
        }
        return st.pop();
    }

    //12. Postfix to Prefix Conversion
    static String postToPre(String post_exp) {
        Stack<String> st = new Stack<>();

        for(char ch : post_exp.toCharArray()){
            if(Character.isLetterOrDigit(ch)){
                st.add(Character.toString(ch));
            }else{
                String val1 = st.pop();
                String val2 = st.pop();

                st.push(ch + val2 + val1);
            }
        }
        return st.pop();
    }
    //13. Postfix to Infix Conversion
    static String postToInfix(String exp) {
        // code here
        Stack<String> st = new Stack<>();
        for(char ch : exp.toCharArray()){
            if(Character.isLetterOrDigit(ch)){
                st.push(Character.toString(ch));
            }else{
                String val1 = st.pop();
                String val2 = st.pop();
                st.push("("+ val2 + ch + val1 +")");
            }
        }
        return st.pop();
    }
    //14.Convert Infix To Prefix Notation
    static String InfixToPrefix(String exp) {
        StringBuilder sb  = new StringBuilder(exp).reverse();

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '(') {
                sb.setCharAt(i, ')');
                i++;
            } else if (sb.charAt(i) == ')') {
                sb.setCharAt(i, '(');
                i++;
            }
        }
        String s = infixToPostfix(sb.toString());
        return new StringBuilder(s).reverse().toString();
    }
    //15. Next Greater Element
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> mp = new HashMap<>();
        Stack<Integer> st = new Stack<>();
        int n = nums2.length;

        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && st.peek()<= nums2[i])
                st.pop();

            if(!st.isEmpty())
                mp.put(nums2[i], st.peek());
            else
                mp.put(nums2[i], -1);

            st.push(nums2[i]);
        }
        for(int i=0;i<nums1.length;i++){
            nums1[i] = mp.get(nums1[i]);
        }
        return nums1;
    }
    //16. Next Greater Element II (Circular Array)
    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        Stack<Integer> st = new Stack<>();

        for(int i=2*n-1;i>=0;i--){
            int index = i % n;
            while(!st.isEmpty() && st.peek() <= nums[index])
                st.pop();

            if(index < n){
                if(!st.isEmpty())
                    res[index] = st.peek();
            }
            st.push(nums[index]);
        }
        return res;
    }
    //17. Next Smaller Element from start || previous smaller element
    public ArrayList<Integer> prevSmaller(ArrayList<Integer> A) {
        int n = A.size();
        ArrayList<Integer> list = new ArrayList<>();

        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && A.get(i) <= st.peek()){
                st.pop();
            }

            if(!st.isEmpty())
                list.add(st.peek());
            else
                list.add(-1);

            st.push(A.get(i));
        }
        return list;
    }
    //18. Count of All Next greater elements to the right
    static Map<Integer, Integer> mp;
    public static int[] count_NGEs(int N, int arr[], int queries, int indices[]) {
        mp = new HashMap<>();
        for(int i=0;i<N;i++){
            mp.put(i, 0);
        }
        int[][] arrWI = new int[N][2];
        for(int i=0;i<N;i++){
            arrWI[i][0] = arr[i];
            arrWI[i][1] = i;
        }

        mergeSort(arrWI, 0, N-1);

        int[] res = new int[queries];

        for(int i=0;i<queries;i++){
            res[i] = mp.get(indices[i]);
        }
        return res;
    }

    static void mergeSort(int[][] arrWI, int i, int j){
        if(j <= i)
            return;

        int mid = (i+j)/2;
        mergeSort(arrWI, i, mid);
        mergeSort(arrWI, mid+1, j);
        merge(arrWI, i, mid, j);
    }

    static void merge(int[][] arrWI, int low, int mid, int high){
        int[][] temp = new int[high-low+1][2];
        int k = 0;

        int l = low, m = mid+1;

        while(l <= mid && m <= high){ // sorting in decreasing order , counting all the larger elements

            if(arrWI[l][0] < arrWI[m][0]){
                temp[k][0] = arrWI[m][0];
                temp[k++][1] = arrWI[m++][1];

            }else{
                mp.put(arrWI[l][1], mp.get(arrWI[l][1]) + m-mid-1); // m-mid-1 total bigger element than arr[l]
                temp[k][0] = arrWI[l][0];
                temp[k++][1] = arrWI[l++][1];
            }
        }
        while(l<=mid){
            mp.put(arrWI[l][1], mp.get(arrWI[l][1]) + high-mid); // high-mid or m-mid-1 total bigger element than arr[l]
            temp[k][0] = arrWI[l][0];
            temp[k++][1] = arrWI[l++][1];
        }

        while(m <= high){
            temp[k][0] = arrWI[m][0];
            temp[k++][1] = arrWI[m++][1];
        }

        for(int i = low;i<=high;i++){
            arrWI[i][0] = temp[i-low][0];
            arrWI[i][1] = temp[i-low][1];
        }
    }
    //19. Trapping Rain Water
    public int trap(int[] height) {
        int n = height.length;
        int l =0, r= n-1;
        int leftMax =0, rightMax =0;
        int res = 0;

        while(l<=r){

            if(leftMax <= rightMax){
                leftMax = Math.max(leftMax, height[l]);
                res += leftMax - height[l];
                l++;
            }else{
                rightMax = Math.max(rightMax, height[r]);
                res += rightMax - height[r];
                r--;
            }
        }
        return res;
    }
    //20. Sliding Window Maximum
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new LinkedList<>();
        int n = nums.length;
        int[] res = new int[n-k+1];

        for(int i=0;i<nums.length;i++){

            while(!dq.isEmpty() && nums[dq.getLast()] <= nums[i])
                dq.removeLast();

            dq.addLast(i);

            if(i >= k-1){
                res[i-k+1] = nums[dq.getFirst()];
            }

            if(dq.getFirst() == i-k+1)
                dq.removeFirst();
        }
        return res;
    }
    //21. Sum of subarray minimum
    public int sumSubarrayMins(int[] arr) {

        int mod = (int) 1e9 + 7;
        int n = arr.length;

        int[] preMin = new int[n];
        int[] postMin = new int[n];

        Arrays.fill(preMin, -1);
        Arrays.fill(postMin, n);

        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){

            while(!st.isEmpty() && arr[st.peek()] >= arr[i]) // pick the element
                st.pop();

            if(!st.isEmpty())
                preMin[i] = st.peek();
            st.push(i);
        }

        st.clear();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && arr[st.peek()] > arr[i]) // do not pick
                st.pop();

            if(!st.isEmpty())
                postMin[i] = st.peek();
            st.push(i);
        }

        long res =0;

        for(int i=0;i<n;i++){
            long val = (long)(i-preMin[i])*(postMin[i]-i);
            val = (long) val * arr[i];
            res +=val;
        }
        return (int)(res % mod);
    }
    //22. Asteroid Collision
    public int[] asteroidCollision(int[] arr) {
        Stack<Integer> st = new Stack<>();
        int n = arr.length;

        for(int i=0;i<n;i++){
            if(arr[i] >= 0){
                st.push(arr[i]);
                continue;
            }else {

                if(st.isEmpty() || st.peek() < 0)
                    st.push(arr[i]);
                else{
                    while(!st.isEmpty() && st.peek() < Math.abs(arr[i]) && st.peek() > 0)
                        st.pop();

                    if(st.isEmpty() || st.peek() < 0)
                        st.push(arr[i]);
                    else if(st.peek() == Math.abs(arr[i]))
                        st.pop();
                }
            }
        }

        int[] res = new int[st.size()];
        int k = st.size()-1;
        while(!st.isEmpty()){
            res[k--] = st.pop();
        }
        return res;
    }
    //23. Sum of Subarray Ranges
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        int[] leftMin = new int[n];
        int[] rightMin= new int[n];

        Arrays.fill(leftMin, -1);
        Arrays.fill(rightMin, n);
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && nums[st.peek()] >= nums[i])
                st.pop();
            if(!st.isEmpty())
                leftMin[i] = st.peek();
            st.push(i);
        }
        st.clear();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && nums[st.peek()] > nums[i])
                st.pop();
            if(!st.isEmpty())
                rightMin[i] = st.peek();
            st.push(i);
        }

        st.clear();


        int[] leftMax = new int[n];
        int[] rightMax= new int[n];

        Arrays.fill(leftMax, -1);
        Arrays.fill(rightMax, n);

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && nums[st.peek()] <= nums[i])
                st.pop();
            if(!st.isEmpty())
                leftMax[i] = st.peek();
            st.push(i);
        }
        st.clear();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && nums[st.peek()] < nums[i])
                st.pop();
            if(!st.isEmpty())
                rightMax[i] = st.peek();
            st.push(i);
        }

        long res = 0;
        for(int i=0;i<n;i++){
            long max = 1L*(i-leftMax[i]) * (rightMax[i] - i);
            max = max * nums[i];
            long min = 1L*(i-leftMin[i]) * (rightMin[i] - i);
            min = min * nums[i];
            res += max - min;
        }
        return res;
    }
    //24. Remove k Digits to make number smaller
    public String removeKdigits(String num, int k) {
        Stack<Character> st = new Stack<>();
        for(int i=0;i<num.length();i++){
            while(!st.isEmpty() && k > 0 && st.peek() > num.charAt(i)){
                st.pop();
                k--;
            }

            st.push(num.charAt(i));
        }

        while(k>0){
            st.pop();
            k--;
        }
        StringBuilder sb = new StringBuilder();
        while(!st.isEmpty())
            sb.append(st.pop());
        sb.reverse();
        int i= 0;
        while(i< sb.length()){
            if(sb.charAt(i) == '0')
                i++;
            else
                break;
        }
        String res  = sb.substring(i);
        return res.isEmpty() ? "0" : res;
    }
    //25. Largest Rectangle in Histogram
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] leftMin = new int[n];
        int[] rightMin = new int[n];
        Arrays.fill(leftMin, -1);
        Arrays.fill(rightMin, n);

        Stack<Integer> st = new Stack<>();
        for(int i=0;i<n;i++){
            while(!st.isEmpty() && heights[st.peek()] >= heights[i])
                st.pop();

            if(!st.isEmpty())
                leftMin[i] = st.peek();
            st.push(i);
        }

        st.clear();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && heights[st.peek()] >= heights[i])
                st.pop();

            if(!st.isEmpty())
                rightMin[i] = st.peek();
            st.push(i);
        }
        int res = 0;
        for(int i=0;i<n;i++){
            int val = (rightMin[i] - leftMin[i] -1)* heights[i];
            res = Math.max(val, res);
        }
        return res;
    }
    //26. Maximal Rectangles
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int currCol[] = new int[m];
        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){

                if(matrix[i][j] == '1')
                    currCol[j] = currCol[j] + 1;
                else
                    currCol[j] = 0;
            }
            res = Math.max(res, solve(currCol, m));
        }
        return res;
    }
    int solve(int[] arr, int n){
        int[] preMin = new int[n];
        Arrays.fill(preMin, -1);
        Stack<Integer> st = new Stack<>();

        for(int i=0;i<n;i++){
            while(!st.isEmpty() && arr[st.peek()] >= arr[i])
                st.pop();

            if(!st.isEmpty())
                preMin[i] = st.peek();
            st.push(i);
        }
        st.clear();

        int[] postMin = new int[n];
        Arrays.fill(postMin, n);

        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && arr[st.peek()] >= arr[i])
                st.pop();

            if(!st.isEmpty())
                postMin[i] = st.peek();
            st.push(i);
        }
        int res = 0;
        for(int i=0;i<n;i++){
            res = Math.max(res, (postMin[i]-preMin[i]-1) * arr[i]);
        }
        return res;
    }
    //27. Stock span problem
    class StockSpanner {
        Stack<int[]> st;
        public StockSpanner() {
            st = new Stack<>();
        }

        public int next(int price) {
            int res = 1;
            while(!st.isEmpty() && st.peek()[0] <= price){
                res += st.pop()[1];
            }
            st.push(new int[]{price, res});
            return res;
        }
    }
    //28. The Celebrity Problem
    public int celebrity(int mat[][]) {
        int n = mat.length;
        Stack<Integer> st = new Stack<>();
        for(int i=0;i<n;i++){
            st.push(i);
        }

        while(st.size() >=2){
            int first = st.pop();
            int second = st.pop();
            if(mat[first][second] == 1)
                st.push(second);
            else
                st.push(first);
        }

        int posCel = st.pop();

        for(int i=0;i<n;i++){
            if(posCel == i)
                continue;
            if(mat[posCel][i]  == 1 || mat[i][posCel] == 0)
                return -1;
        }
        return posCel;
    }
    //29 LRU cache
    class LRUCache {
        class DLLNode{
            int key, val;
            DLLNode next, prev;
            DLLNode(int key, int val){
                this.key = key;
                this.val = val;
            }
        }
        DLLNode start, end;
        Map<Integer, DLLNode> mp;
        int totalSize , currSize;
        public LRUCache(int capacity) {
            start = new DLLNode(-1, -1);
            end = new DLLNode(-1, -1);
            start.next = end;
            end.prev= start;
            mp = new HashMap<>();
            totalSize = capacity;
        }

        public int get(int key) {
            if(!mp.containsKey(key)){
                return -1;
            }

            DLLNode node = mp.get(key);
            updateNode(node);
            return node.val;
        }

        public void put(int key, int value) {
            if(mp.containsKey(key)){
                DLLNode node = mp.get(key);
                node.val = value;
                updateNode(node);
            }else{
                currSize++;
                DLLNode node = new DLLNode(key, value);
                mp.put(key, node);
                if(currSize > totalSize){

                    mp.remove(end.prev.key);
                    end.prev = end.prev.prev;

                    end.prev.next = end;
                    node.next = start.next;
                    start.next = node;
                    node.prev = start;
                    node.next.prev = node;
                    currSize--;
                }else{
                    node.next = start.next;
                    start.next = node;
                    node.prev = start;
                    node.next.prev = node;
                }
            }
        }
        void updateNode(DLLNode node){
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = start.next;
            start.next = node;
            node.prev = start;
            node.next.prev = node;
        }
    }
    //30 LFU cache
    class LFUCache {
        class DLLNode{
            int key,val,fre;
            DLLNode prev, next;
            DLLNode(int key, int val, int fre){
                this.key = key;
                this.val = val;
                this.fre = fre;
            }
        }
        class DLLNodeList{
            DLLNode start, end;
            int size;
            DLLNodeList(){
                start = new DLLNode(0, 0, 0);
                end = new DLLNode(0, 0, 0);
                start.next = end;
                end.prev = start;
                size = 0;
            }

            void addNode(DLLNode node){
                DLLNode next = start.next;
                start.next = node;
                node.next = next;
                next.prev = node;
                node.prev = start;
                size++;
            }
            void deleteNode(DLLNode node){
                DLLNode prev = node.prev ;
                DLLNode next = node.next;
                prev.next = next;
                next.prev = prev;
                size--;
            }
        }
        Map<Integer, DLLNode> cache;
        Map<Integer, DLLNodeList> freListMp;
        int totalSize , currSize;
        int minFre;

        public LFUCache(int capacity) {
            totalSize = capacity;
            currSize = 0;
            cache = new HashMap<>();
            freListMp = new HashMap<>();
            minFre = 0;
        }

        public int get(int key) {
            DLLNode node = cache.get(key);
            if(node == null){
                return -1;
            }

            updateNode(node);
            return node.val;
        }

        public void put(int key, int value) {

            if(cache.containsKey(key)){
                DLLNode node = cache.get(key);
                node.val = value;
                updateNode(node);
            }else {
                currSize ++;
                if(currSize > totalSize){
                    DLLNodeList currList = freListMp.get(minFre);
                    cache.remove(currList.end.prev.key);
                    currList.deleteNode(currList.end.prev);

                    currSize--;
                }
                minFre = 1;

                DLLNode node = new DLLNode(key, value, 1);
                DLLNodeList list = freListMp.getOrDefault(1, new DLLNodeList());
                list.addNode(node);
                freListMp.put(1, list);
                cache.put(key, node);
            }
        }
        void updateNode(DLLNode node){
            int currFre = node.fre;
            DLLNodeList currList = freListMp.get(currFre);
            currList.deleteNode(node);

            if(currFre == minFre && currList.size == 0)
                minFre++;


            node.fre++;
            DLLNodeList newList = freListMp.getOrDefault(node.fre, new DLLNodeList());

            newList.addNode(node);
            freListMp.put(node.fre, newList);
        }
    }

}
