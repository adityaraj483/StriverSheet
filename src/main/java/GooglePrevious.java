import DS.Interval;
import DS.MountainArray;
import DS.TreeNode;

import java.util.*;
public class GooglePrevious {
    public static void main(String[] args) {
        // 1 1 1 2 3 5 7
        System.out.println(repeatedNumber(Arrays.asList(1,1,1,2,3,5,7)));
    }
    //1. Sum of bit differences -> https://practice.geeksforgeeks.org/problems/sum-of-bit-differences-1587115620/1
    long sumBitDifferences(int[] arr, int n) {
        // code here
        long res = 0;
        for(int i=0;i<32;i++){
            long cnt = 0;
            for(int j=0;j<n;j++){

                if((arr[j] &(1 <<i)) > 0)
                    cnt++;
            }

            res += cnt * (n-cnt)*2;
        }
        return res;
    }
    //2. Travelling Salesman Problem -> https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1
    public int tsp(int[][] cost) {
        // Code here
        int n = cost.length;
        int[][] dp = new int[n][1 << n];
        for(int[] row : dp)
            Arrays.fill(row, -1);
        return solve(cost, 0, n, 1, dp);
    }
    int solve(int[][] cost, int node, int n, int mask, int[][] dp){

        if(mask == (1 << n) -1){
            return cost[node][0];
        }
        if(dp[node][mask] != -1) return dp[node][mask];
        int res = (int) 1e9;
        for(int j=0;j<n;j++){
            if(j == node)
                continue;
            if((mask & (1 << j)) == 0){
                int ans = cost[node][j] + solve(cost, j, n, mask |(1<<j), dp);
                res = Math.min(res, ans);
            }
        }
        return dp[node][mask] = res;

    }
    //3. Matrix diagonally traversal -> https://leetcode.com/problems/diagonal-traverse/
    public int[] findDiagonalOrder(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int r = 0, c = 0;
        int[] ans = new int[n*m];
        int i = 0;
        while(r < n && c < m){

            while(r < n && r >=0 && c <m && c >=0){
                ans[i++] = mat[r--][c++];
            }
            r ++;
            c --;
            if(c<m-1) c++; else r++;

            while( r < n && r >=0 && c <m && c >=0){
                ans[i++] = mat[r++][c--];
            }
            c ++;
            r --;
            if(r < n-1) r++; else c++;
            if(i == n*m)
                break;
        }
        return ans;
    }
    //4. Josephus problem -> https://practice.geeksforgeeks.org/problems/josephus-problem/1
    public int josephus(int n, int k) {
        // code here
        return solve(n, k) + 1;
    }
    int solve(int n, int k) {
        // code here
        if(n == 1)
            return 0;
        return (solve(n-1, k) + k) % n;
    }

    //5. Find All Possible Recipes from Given Supplies ->
    class AllReceipe {
        Map<String,Integer> recipMp;
        Set<String> suppliesSet;
        Map<String, Boolean> created;
        Set<String> vis;
        public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {

            suppliesSet = new HashSet<>();
            for(int i=0;i<supplies.length;i++){
                suppliesSet.add(supplies[i]);
            }

            recipMp = new HashMap<>();
            for(int i =0;i<recipes.length;i++){
                recipMp.put(recipes[i], i);
            }

            List<String> res = new ArrayList<>();
            created = new HashMap<>();
            vis = new HashSet<>();

            for(int i=0;i<recipes.length;i++){
                if(canRecipes(recipes[i], i, ingredients)){
                    res.add(recipes[i]);
                }
            }
            return res;
        }

        boolean canRecipes(String recip, int index, List<List<String>> ingredients){
            if(created.containsKey(recip))
                return created.get(recip);

            if(vis.contains(recip))
                return false;
            vis.add(recip);

            boolean res = true;
            for(String ingredient : ingredients.get(index)){

                if(recipMp.containsKey(ingredient)){

                    res = res & canRecipes(ingredient, recipMp.get(ingredient), ingredients);
                }else{
                    res = res & suppliesSet.contains(ingredient);
                }
                if(res ==  false)
                    break;
            }
            created.put(recip, res);
            return res;
        }
    }
    //6. RLE Iterator
    class RLEIterator {
        int[] encoded;
        int index;
        int size;
        public RLEIterator(int[] encoding) {
            encoded = encoding;
            index = 0;
            size = encoding.length;
        }

        public int next(int n) {
            int val = -1;
            while(index < size){
                if(encoded[index] > n){
                    encoded[index] -= n;
                    val = encoded[index+1];
                    break;
                }else if(encoded[index] == n){
                    val = encoded[index+1];
                    encoded[index] = 0;
                    index +=2;
                    break;
                }else{
                    n -= encoded[index];
                    encoded[index] = 0;
                    index +=2;
                }
            }
            return val;
        }
    }
    //7. Find in Mountain Array
    public int findInMountainArray(int target, MountainArray arr) {

        int l =0, r = arr.length()-1;
        int n = arr.length();

        while(l<r){
            int mid = (l+r)/2;
            if(arr.get(mid) > arr.get(mid+1)){
                r = mid;
            }else{
                l = mid+1;
            }
        }
        System.out.println(l);
        int a = binarySearchSorted(arr, 0, l, target);
        int b = binarySearchReverse(arr, l+1, n-1, target);
        if( a >= 0 && b >= 0)
            return Math.min(a, b);
        else if(a >=0 || b >=0)
            return Math.max(a, b);
        else
            return -1;
    }
    int binarySearchSorted(MountainArray arr, int l , int r, int target){
        while(l<= r){
            int mid = (l + r)/2;
            if(arr.get(mid) == target)
                return mid;
            else if(arr.get(mid) < target)
                l = mid+1;
            else
                r = mid-1;
        }
        return -1;
    }
    int binarySearchReverse(MountainArray arr, int l , int r, int target){
        while(l<= r){
            int mid = (l + r)/2;
            if(arr.get(mid) == target)
                return mid;
            else if(arr.get(mid) < target)
                r = mid-1;
            else
                l = mid+1;
        }
        return -1;
    }

    //8. Next Smallest Palindrome
    public String solve(String num) {
        int len = num.length();
        if (allNines(num)) { // Special case: "999" to "1001"
            StringBuilder res = new StringBuilder();
            res.append("1");
            for(int i=0;i<len-1;i++)
                res.append("0");
            res.append("1");
            return res.toString();
        }

        String leftHalf = num.substring(0, (len + 1) / 2); // Left half (including middle if odd)
        String mirrored = mirror(leftHalf, len % 2 == 0);  // Create a palindrome by mirroring

        if (mirrored.compareTo(num) > 0) {
            return mirrored; // If mirrored is already larger, return it
        }

        // If not, increment leftHalf and mirror again
        leftHalf = incrementString(leftHalf);
        return mirror(leftHalf, len % 2 == 0);
    }
    private String mirror(String leftHalf, boolean evenLength) {
        StringBuilder sb = new StringBuilder(leftHalf);
        if (evenLength) {
            return sb.append(new StringBuilder(leftHalf).reverse()).toString();
        } else {
            return sb.append(new StringBuilder(leftHalf.substring(0, leftHalf.length() - 1)).reverse()).toString();
        }
    }
    private String incrementString(String str) {
        StringBuilder sb = new StringBuilder(str);
        int i = sb.length() - 1, carry = 1;

        while (i >= 0 && carry > 0) {
            int digit = sb.charAt(i) - '0' + carry;
            sb.setCharAt(i, (char) ((digit % 10) + '0'));
            carry = digit / 10;
            i--;
        }

        if (carry > 0) sb.insert(0, '1'); // Handle carry overflow (e.g., "999" to "1000")
        return sb.toString();
    }

    private static boolean allNines(String num) {
        return num.chars().allMatch(ch -> ch == '9');
    }
    //9. Word Search board
    public int exist(ArrayList<String> A, String B) {
        int n = A.size(), m = A.get(0).length();
        for(int i=0;i<n;i++){
            for(int j =0;j<m;j++){
                if(solve(A, i, j, n, m, B, 0, B.length()))
                    return 1;
            }
        }
        return 0;
    }
    boolean solve(List<String> A, int row, int col, int n, int m, String B, int k, int z){



        if( k == z)
            return true;
        if(B.charAt(k) != A.get(row).charAt(col))
            return false;

        int[] delRow = new int[]{-1, 0, 1, 0};
        int[] delCol = new int[]{0, 1, 0, -1};

        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];
            if( r >=0 && r < n && c >=0 && c < m){
                if(solve(A, r, c, n, m, B, k+1, z))
                    return true;
            }
        }
        return false;
    }
    //10. Remove Boxes
    public int removeBoxes1(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n+1];
        for(int[][] arr1 : dp){
            for(int[] col : arr1)
                Arrays.fill(col, -1);
        }

        return solve(boxes, 0, n-1, 0, dp);
    }
    int solve(int[] arr, int l, int r, int count, int[][][] dp){
        if(l > r)
            return 0;
        if(dp[l][r][count] != -1)
            return dp[l][r][count];
        while(l+1 <= r && arr[l] == arr[l+1]){
            l++;
            count++;
        }
        int res = (count+1) * (count+1) + solve(arr, l+1, r, 0, dp);

        for(int m = l+1;m <= r;m++){
            if(arr[m] == arr[l])
                res = Math.max(res, solve(arr, l+1, m-1, 0, dp) + solve(arr, m, r, count+1, dp));
        }
        return dp[l][r][count] = res;
    }
    //----------------------OR----------------------
    public int removeBoxes2(int[] arr) {
        int n = arr.length;
        int[][][] dp = new int[n][n][n];

        for(int r = 0;r<n;r++){
            for(int l = r;l >=0;l--){
                for(int count = 0; count <= l;count++){
                    int res = (count+1) * (count+1);
                    if(l+1 <= r)
                        res +=  dp[l+1][r][0];

                    for(int m = l+1;m <= r;m++){
                        if(arr[m] == arr[l])
                            res = Math.max(res, dp[l+1][m-1][0] + dp[m][r][count+1]);
                    }
                    dp[l][r][count] = res;
                }

            }
        }
        return dp[0][n-1][0];
    }
    //11`. Partition to K Equal Sum Subsets -> https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).reduce(0, Integer::sum);
        if(sum % k != 0)
            return false;
        Arrays.sort(nums);
        reverse(nums);
        int target = sum / k;

        boolean[] vis = new boolean[nums.length];
        return solve(nums, 0, 0, target, vis, k);
    }
    boolean solve(int[] nums, int i, int curr, int target, boolean[] vis, int k){
        if(k == 0)
            return true;
        if(curr == target){
            return solve(nums, 0, 0, target, vis, k-1);
        }
        for(int j=i;j<nums.length;j++){
            if(!vis[j] && curr + nums[j] <= target){
                vis[j] = true;
                if(solve(nums, j+1, curr+ nums[j], target, vis, k))
                    return true;
                vis[j] = false;
            }
        }
        return false;
    }
    void reverse(int[] arr){
        int i=0, j= arr.length-1;
        while(i<j){
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
            i++;
            j--;
        }
    }
    //12. Maximum Score of a Node Sequence -> https://leetcode.com/problems/maximum-score-of-a-good-subarray/
    public int maximumScore(int[] scores, int[][] edges) {
        int n = scores.length;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }

        for (int[] ints : edges) {
            int u = ints[0];
            int v = ints[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        for(int i=0;i<n;i++){
            graph.get(i).sort((a, b) -> scores[b] - scores[a]);
        }

        int maxScore = -1;
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];

            for (int i = 0; i < Math.min(3, graph.get(a).size()); i++) {
                int c = graph.get(a).get(i);

                if (c == b) continue;

                for (int j = 0; j < Math.min(3, graph.get(b).size()); j++) {
                    int d = graph.get(b).get(j);
                    if (d == a || d == c) continue;

                    maxScore = Math.max(maxScore, scores[a] + scores[b] + scores[c] + scores[d]);
                }
            }
        }
        return maxScore;
    }
    //13. Minimum number of refueling stops -> https://leetcode.com/problems/minimum-number-of-refueling-stops/
    public int minRefuelStops1(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        int[][] dp = new int[n+1][target+1];

        for(int[] row : dp)
            Arrays.fill(row, -1);
        int res =  solve1(stations, 0, startFuel, target, dp);
        if(res >= 1e9+7)
            return -1;
        return res;
    }
    int solve1(int[][] stations, int i, int start, int target, int[][] dp){

        if(start >= target)
            return 0;
        if(i == stations.length)
            return (int) 1e9+7;
        if(dp[i][start] != -1) return dp[i][start];

        int val = (int) 1e9+7;

        if(start - stations[i][0] >= 0){
            val =  Math.min(solve(stations, i+1, start, target, dp), 1 + solve(stations, i+1, start + stations[i][1], target, dp));

        }
        return dp[i][start] = val;
    }
    //----------------------OR----------------------
    public int minRefuelStops2(int target, int startFuel, int[][] stations) {

        Queue<Integer> pq = new PriorityQueue<>((a, b) -> b-a);
        int count = 0;
        int i= 0;
        int n = stations.length;
        while(startFuel < target){

            while(i < n && stations[i][0] <= startFuel)
                pq.add(stations[i++][1]);

            if(pq.isEmpty())
                return -1;
            else
                startFuel += pq.remove();
            count++;
        }
        return count;
    }
    //14. Justified Text -> https://www.interviewbit.com/problems/justified-text/
    public ArrayList<String> fullJustify(ArrayList<String> A, int B) {
        ArrayList<String> res = new ArrayList<>();
        if (B == 0 || A.size() == 0)
            return res;

        ArrayList<String> temp = new ArrayList<>();
        int currLen = 0;

        for (int i = 0; i < A.size(); i++) {
            String word = A.get(i);
            int wordLen = word.length();

            if (currLen + wordLen + temp.size() > B) {
                res.add(func(temp, B, false));
                temp.clear();
                currLen = 0;
            }

            temp.add(word);
            currLen += wordLen;
        }

        res.add(func(temp, B, true));
        return res;
    }

    String func(ArrayList<String> words, int B, boolean flag) {
        StringBuilder sb = new StringBuilder();
        int n = words.size();
        int totalChars = 0;

        for (String word : words) {
            totalChars += word.length();
        }

        int totalSpaces = B - totalChars;

        if (flag || n == 1) {
            for (int i = 0; i < words.size(); i++) {
                sb.append(words.get(i));
                if (i < words.size() - 1) {
                    sb.append(" ");
                    totalSpaces--;
                }
            }
            while (totalSpaces-- > 0) sb.append(" ");
        } else {
            int space = totalSpaces / (n - 1);
            int extra = totalSpaces % (n - 1);

            for (int i = 0; i < words.size(); i++) {
                sb.append(words.get(i));
                if (i < words.size() - 1) {
                    int spacesToAdd = space + (extra-- > 0 ? 1 : 0);
                    while (spacesToAdd-- > 0) sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
    //15. Order of People Heights -> https://www.interviewbit.com/problems/order-of-people-heights/
    public ArrayList<Integer> order(ArrayList<Integer> A, ArrayList<Integer> B) {
        int n = A.size();
        int[][] arr = new int[n][2];
        for(int i=0;i<n;i++){
            arr[i][0] = A.get(i);
            arr[i][1] = B.get(i);
        }

        Arrays.sort(arr, (a, b) ->{
            if(a[0] != b[0])
                return b[0] - a[0];
            else
                return a[1] - b[1];
        });

        ArrayList<Integer> res = new ArrayList<>();
        for(int i=0;i<n;i++){
            res.add(arr[i][1], arr[i][0]);
        }
        return res;
    }
    //13. Design Twitter
    class Twitter {
        class User {
            int userId;
            Set<Integer> followees;
            LinkedList<int[]> tweets; // LinkedList for fast insertion

            User(int userId) {
                this.userId = userId;
                this.followees = new HashSet<>();
                this.tweets = new LinkedList<>();
            }

            void follow(int followeeId) {
                followees.add(followeeId);
            }

            void unfollow(int followeeId) {
                followees.remove(followeeId);
            }

            void postTweet(int tweetId, int timestamp) {
                if (tweets.size() == 10) {
                    tweets.removeLast(); // Keep only the latest 10 tweets
                }
                tweets.addFirst(new int[]{tweetId, timestamp});
            }
        }

        private Map<Integer, User> users;
        private int timestamp;

        public Twitter() {
            users = new HashMap<>();
            timestamp = 0;
        }

        public void postTweet(int userId, int tweetId) {
            users.putIfAbsent(userId, new User(userId));
            users.get(userId).postTweet(tweetId, timestamp++);
        }

        public List<Integer> getNewsFeed(int userId) {
            if (!users.containsKey(userId)) return new ArrayList<>();

            PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]); // Max heap (latest timestamp first)
            User user = users.get(userId);


            maxHeap.addAll(user.tweets);


            for (int followeeId : user.followees) {
                if (users.containsKey(followeeId)) {
                    maxHeap.addAll(users.get(followeeId).tweets);
                }
            }

            List<Integer> newsFeed = new ArrayList<>();
            while (!maxHeap.isEmpty() && newsFeed.size() < 10) {
                newsFeed.add(maxHeap.poll()[0]);
            }
            return newsFeed;
        }

        public void follow(int followerId, int followeeId) {
            if (followerId == followeeId) return;

            users.putIfAbsent(followerId, new User(followerId));
            users.putIfAbsent(followeeId, new User(followeeId));
            users.get(followerId).follow(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            if (!users.containsKey(followerId)) return;
            users.get(followerId).unfollow(followeeId);
        }
    }
    //14. MK Average -> https://leetcode.com/problems/finding-mk-average/
    class MKAverage {
        int m, k;
        long midSum; // Store the sum of midMap elements
        Queue<Integer> stream;
        TreeMap<Integer, Integer> leftMap, rightMap, midMap;
        int leftSize, midSize, rightSize;

        public MKAverage(int m, int k) {
            this.m = m;
            this.k = k;
            this.midSum = 0;
            leftSize = 0;
            midSize = 0;
            rightSize = 0;
            this.stream = new LinkedList<>();
            this.leftMap = new TreeMap<>();
            this.rightMap = new TreeMap<>();
            this.midMap = new TreeMap<>();
        }

        public void addElement(int num) {
            stream.add(num);
            if (stream.size() > m) {
                int val = stream.remove();
                remove(val);
            }
            insert(num);
        }

        public int calculateMKAverage() {
            if (stream.size() < m)
                return -1;
            return (int) (midSum / (m - 2 * k));
        }

        private void insert(int num) {
            if (leftSize < k || num <= leftMap.lastKey()) {
                leftMap.put(num, leftMap.getOrDefault(num, 0) + 1);
                leftSize++;
            } else if (rightSize < k || num >= rightMap.firstKey()) {
                rightMap.put(num, rightMap.getOrDefault(num, 0) + 1);
                rightSize++;
            } else {
                midMap.put(num, midMap.getOrDefault(num, 0) + 1);
                midSum += num;
                midSize++;
            }
            balance();
        }

        private void remove(int num) {
            if (leftMap.containsKey(num)) {
                leftMap.put(num, leftMap.getOrDefault(num, 1)-1);
                if (leftMap.get(num) == 0) leftMap.remove(num);
                leftSize--;

            } else if (rightMap.containsKey(num)) {
                rightMap.put(num, rightMap.getOrDefault(num , 1)-1);
                if (rightMap.get(num) == 0) rightMap.remove(num);
                rightSize--;

            } else if (midMap.containsKey(num)) {
                midMap.put(num, midMap.getOrDefault(num , 1)-1);
                midSum -= num;
                if (midMap.get(num) == 0) midMap.remove(num);
                midSize--;
            }
            balance();
        }

        private void balance() {
            while (leftSize > k) {
                int move = leftMap.lastKey();
                leftMap.put(move, leftMap.getOrDefault(move , 1)-1);
                if (leftMap.get(move) == 0) leftMap.remove(move);
                midMap.put(move, midMap.getOrDefault(move, 0) + 1);
                midSum += move;

                leftSize--;
                midSize++;
            }

            while (rightSize > k) {
                int move = rightMap.firstKey();

                rightMap.put(move, rightMap.getOrDefault(move, 1)-1);
                if (rightMap.get(move) == 0) rightMap.remove(move);
                midMap.put(move, midMap.getOrDefault(move, 0) + 1);
                midSum += move;

                rightSize--;
                midSize++;
            }

            while (leftSize < k && !midMap.isEmpty()) {
                int move = midMap.firstKey();
                midMap.put(move, midMap.getOrDefault(move,1)-1);
                midSum -= move;
                if (midMap.get(move) == 0) midMap.remove(move);
                leftMap.put(move, leftMap.getOrDefault(move, 0) + 1);

                leftSize++;
                midSize--;
            }

            while (rightSize < k && !midMap.isEmpty()) {
                int move = midMap.lastKey();
                midMap.put(move, midMap.getOrDefault(move, 1) -1);
                midSum -= move;
                if (midMap.get(move) == 0) midMap.remove(move);
                rightMap.put(move, rightMap.getOrDefault(move, 0) + 1);

                midSize --;
                rightSize++;
            }
        }
    }
    //15. Gas Station
    public int canCompleteCircuit1(final List<Integer> A, final List<Integer> B) {
        int n = A.size();

        for(int i=0;i<n;i++){

            if(A.get(i) < B.get(i))
                continue;
            int sum = A.get(i) - B.get(i);

            for(int j=i + 1;j<=2*n;j++){
                int ind = j%n;
                if(ind == i)
                    return i;

                sum += A.get(ind) - B.get(ind);
                if(sum < 0)
                    break;
            }
        }
        return -1;
    }

    //----------------------OR----------------------
    public int canCompleteCircuit2(final List<Integer> A, final List<Integer> B) {

        int aSum = A.stream().reduce(0, Integer::sum);
        int bSum = B.stream().reduce(0, Integer::sum);
        if(aSum < bSum)
            return -1; // after this condition if we are moving forward means there will always be a answer.

        int n = A.size();
        int sum = 0;
        int res = 0;
        for(int i=0;i<n;i++){
            sum += A.get(i) - B.get(i);
            if(sum < 0){
                sum = 0;
                res = i+1;
            }
        }
        return res;
    }
    //16. Merge Intervals | insert intervals
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> res = new ArrayList<>();
        int n = intervals.size();
        int i = 0;

        while(i < n && intervals.get(i).end < newInterval.start){
            res.add(intervals.get(i++));
        }

        while(i < n && intervals.get(i).start <= newInterval.end ){
            newInterval.start = Math.min(newInterval.start, intervals.get(i).start);
            newInterval.end = Math.max(newInterval.end, intervals.get(i++).end);
        }

        res.add(newInterval);
        while(i < n ){
            res.add(intervals.get(i++));
        }
        return res;
    }
    //17. Majority Numbers
    public static int repeatedNumber(final List<Integer> a) {
        int cnt1 =0, cnt2 =0;
        int val1 = -1, val2 = -1;

        for(int i=0;i<a.size();i++){
            if(val1 == a.get(i)){
                cnt1++;
            }else if(val2 == a.get(i)){
                cnt2++;
            }else if(cnt1 == 0){
                val1 = a.get(i);
                cnt1 =1;
            }else if(cnt2 == 0){
                val2 = a.get(i);
                cnt2 = 1;
            }else{
                cnt1--;
                cnt2--;
            }
        }

        cnt1=0;
        cnt2=0;
        for(int i=0;i<a.size();i++){
            if(val1 == a.get(i))
                cnt1++;
            if(val2 == a.get(i))
                cnt2++;
        }

        if(cnt1 > a.size()/3)
            return val1;
        if(cnt2 > a.size()/3)
            return val2;
        return -1;
    }

    //18. Max Distance -> https://www.interviewbit.com/problems/max-distance/
    int res;
    public int maximumGap(final List<Integer> A) {
        int n = A.size();
        int[][] arr = new int[n][2];
        for(int i=0;i<n;i++){
            arr[i][0] = A.get(i);
            arr[i][1] = i;
        }

        res = 0;
        mergeSort(arr, 0, n-1);
        return res;
    }
    void mergeSort(int[][] arr, int i, int j){
        if(i>= j)
            return;

        int mid = (i+j)/2;
        mergeSort(arr, i, mid);
        mergeSort(arr, mid+1, j);
        merge(arr, i, mid, j);
    }
    void merge(int[][] arr, int low, int mid, int high){
        int[][] temp = new int[high-low+1][2];

        int l = low, m = mid+1;
        int k = 0;
        int minIndex = (int) 1e9;

        while(l <= mid && m <= high){

            if(arr[l][0] <= arr[m][0]){

                minIndex = Math.min(minIndex, arr[l][1]);
                temp[k][0] = arr[l][0];
                temp[k++][1] = arr[l++][1];

            }else{
                res = Math.max(res, arr[m][1] - minIndex);
                temp[k][1] = arr[m][1];
                temp[k++][0] = arr[m++][0];
            }

        }

        while(l <= mid){
            temp[k][0] = arr[l][0];
            temp[k++][1] = arr[l++][1];

        }

        while(m <= high){
            res = Math.max(res, arr[m][1]-minIndex);
            temp[k][0] = arr[m][0];
            temp[k++][1] = arr[m++][1];
        }

        for(int i=low;i<=high;i++){
            arr[i][0] = temp[i-low][0];
            arr[i][1] = temp[i-low][1];
        }
    }
    //19. Meeting Rooms III
    public int mostBooked(int n, int[][] meetings) {

        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0] ,b[0]));

        Queue<long[]> occupiedRooms = new PriorityQueue<>((a, b) -> {
            if(a[1] != b[1]){
                return Long.compare(a[1], b[1]);
            }else
                return Long.compare(a[0], b[0]);
        });

        Map<Integer, Integer> mp = new HashMap<>();
        Queue<Integer> avlRooms = new PriorityQueue<>((a, b) -> a-b);

        for(int i=0;i<n;i++){
            avlRooms.add(i);
            mp.put(i, 0);
        }


        for(int[] meeting : meetings){
            long start = meeting[0];
            long end = meeting[1];

            while(!occupiedRooms.isEmpty() && occupiedRooms.peek()[1] <= start){
                avlRooms.add((int)occupiedRooms.remove()[0]);
            }

            if(avlRooms.isEmpty()){
                long free = occupiedRooms.peek()[1];
                int room = (int) occupiedRooms.remove()[0];
                long actStart = Math.max(start, free);
                long acctEnd = actStart + end-start;
                occupiedRooms.add(new long[]{room, acctEnd});
                mp.put(room, mp.get(room)+1);
            }else{
                int room = avlRooms.remove();
                occupiedRooms.add(new long[]{room, end});
                mp.put(room, mp.get(room)+1);
            }
        }

        int room = 0, maxCount = -1;

        for(int i=0;i<n;i++){

            if(mp.get(i) > maxCount){
                room = i;
                maxCount = mp.get(i);
            }
        }
        return room;
    }
    //20. Height of Binary Tree After Subtree Removal Queries
    public int[] treeQueries(TreeNode root, int[] queries) {
        int n = findSize(root);
        int[] level = new int[n+1];
        int[] height = new int[n+1];
        int[] maxHeightOfLevel = new int[n+1];
        int[] secondMaxHeightOfLevel = new int[n+1];

        populate(root, 0, level, height, maxHeightOfLevel, secondMaxHeightOfLevel);

        int maxHeight = height[root.val];

        int[] ans = new int[queries.length];
        int k= 0;
        for(int q : queries){

            int currLevel = level[q];
            int currHeight = height[q];
            int res = 0;
            if(maxHeightOfLevel[currLevel] == currHeight){
                res = maxHeight - maxHeightOfLevel[currLevel] + secondMaxHeightOfLevel[currLevel] -1;
            }else
                res = maxHeight-1;
            ans[k++] = res;
        }
        return ans;
    }

    int populate(TreeNode root, int l, int[] level, int[] height, int[] maxHeightOfLevel, int[] secondMaxHeightOfLevel){

        if(root == null)
            return 0;

        int val = root.val;

        //level
        level[val] = l;

        int left = populate(root.left, l+1, level, height, maxHeightOfLevel, secondMaxHeightOfLevel);
        int right = populate(root.right, l+1, level, height, maxHeightOfLevel, secondMaxHeightOfLevel);

        //height
        int h = Math.max(left, right) +1;
        height[val] = h;


        //maxHeightOfCurrLevel and secondMaxHeightLevel
        if(h > maxHeightOfLevel[l]){
            secondMaxHeightOfLevel[l] = maxHeightOfLevel[l];
            maxHeightOfLevel[l] = h;
        }else if(h > secondMaxHeightOfLevel[l])
            secondMaxHeightOfLevel[l] = h;

        return h;
    }

    int findSize(TreeNode root){
        if(root == null)
            return 0;

        int l = findSize(root.left);
        int r = findSize(root.right);

        return l+r+1;
    }

}
