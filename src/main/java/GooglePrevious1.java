import DS.*;

import java.util.*;
import java.util.stream.Collectors;


public class GooglePrevious1 {

    //1. Sum of bit differences ->
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
    //2. Travelling Salesman Problem ->
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
    static int solve(int n, int k) {
        // code here
        if(n == 1)
            return 0;
        return (solve(n-1, k) + k) % n;
    }
    //------------------------------
    static int func(int n , int k){
        int prev = 0;
        for(int i=2;i<=n;i++){
            prev = (prev + k) % i;
        }
        return prev;
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
        int mid = -1;
        while(l<=r){
            mid = (l+r)/2;
            if(mid +1 < n && arr.get(mid) < arr.get(mid+1)){
                l = mid+1;
            }else if(mid -1 >= 0 && arr.get(mid-1) > arr.get(mid))
                r = mid-1;
            else
                break;
        }

        int a = binarySearchSorted(arr, 0, mid, target);
        int b = binarySearchReverse(arr, mid+1, n-1, target);
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
    boolean solve(int[] nums, int i, int currSum, int target, boolean[] vis, int k){
        if(k == 0)
            return true;
        if(currSum == target){
            return solve(nums, 0, 0, target, vis, k-1);
        }
        for(int j=i;j<nums.length;j++){
            if(!vis[j] && currSum + nums[j] <= target){
                vis[j] = true;
                if(solve(nums, j+1, currSum+ nums[j], target, vis, k))
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
    //12. Maximum Score of a Node Sequence -> https://leetcode.com/problems/maximum-score-of-a-node-sequence/description/
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
    public List<String> fullJustify(String[] words, int maxWidth) {
        int n = words.length;

        List<String> res = new ArrayList<>();
        List<String> currLevel = new ArrayList<>();
        int wordLengthOfCurrLevel = 0;
        int i=0;
        while(i<n){
            String currWord = words[i];
            int totalSpace = currLevel.size();
            wordLengthOfCurrLevel += currWord.length();

            if(wordLengthOfCurrLevel + totalSpace > maxWidth){
                String currLine = build(currLevel, maxWidth, false);
                res.add(currLine);
                currLevel.clear();
                wordLengthOfCurrLevel = currWord.length();
            }

            currLevel.add(currWord);
            i++;
        }

        res.add(build(currLevel, maxWidth, true));
        return res;
    }

    String build(List<String> currLevel, int maxWidth, boolean isLast){
        int totalWords = currLevel.size();
        int totalWordLen = currLevel.stream().map(e -> e.length()).reduce(0, Integer::sum);
        int totalSpace = maxWidth - totalWordLen;
        if(totalSpace < 0)
            return "";


        StringBuilder sb = new StringBuilder();
        if(isLast == true || currLevel.size() == 1){

            for(int i=0;i<totalWords;i++){
                sb.append(currLevel.get(i));

                if(i != totalWords-1){
                    sb.append(" ");
                    totalSpace--;
                }
            }

            while(totalSpace -- > 0){
                sb.append(" ");
            }
        }else{

            int space = totalSpace/(totalWords-1);
            int extraSpace = totalSpace % (totalWords-1);

            for(int i=0;i<totalWords;i++){
                sb.append(currLevel.get(i));

                if(i != totalWords-1){
                    int currSpace = space + (extraSpace > 0 ? 1 : 0);
                    extraSpace--;
                    sb.append(" ".repeat(currSpace));
                }
            }
        }

        return sb.toString();

    }
    //15. Order of People Heights -> https://www.interviewbit.com/problems/order-of-people-heights/
    public ArrayList<Integer> order(ArrayList<Integer> A, ArrayList<Integer> B) { //will do
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
    //15. Gas Station -> https://leetcode.com/problems/gas-station/description/
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
    //----------------------------- OR --------------------
    public int maximumGap1(final List<Integer> A) {

        Stack<Integer> st = new Stack<>();
        for(int i=0;i<A.size();i++){
            if(st.isEmpty() || A.get(st.peek()) > A.get(i))
                st.add(i);
        }

        int res = 0;
        for(int i=A.size()-1;i>=0;i--){

            while(!st.isEmpty() && A.get(st.peek()) <= A.get(i)){
                res = Math.max(res, i-st.pop());
            }
        }
        return res;
    }
    //19. Meeting Rooms III
    public int mostBooked(int n, int[][] meetings) {

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        Queue<long[]> occupiedRooms = new PriorityQueue<>((a, b) -> {
            if(a[1] != b[1]) {
                return Long.compare(a[1], b[1]);
            } else
                return Long.compare(a[0], b[0]);
        });

        Map<Integer, Integer> mp = new HashMap<>();
        Queue<Integer> avlRooms = new PriorityQueue<>((a, b) -> a-b);

        for(int i=0;i<n;i++) {
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
    //20. Height of Binary Tree After Subtree Removal Queries -> https://leetcode.com/problems/height-of-binary-tree-after-subtree-removal-queries/description/
    public int[] treeQueries(TreeNode root, int[] queries) {
        int n = findSize(root);
        int[] level = new int[n+1];
        int[] height = new int[n+1];
        int[] maxHeightOfLevel = new int[n+1];
        int[] secondMaxHeightOfLevel = new int[n+1];

        populate(root, 0, level, height, maxHeightOfLevel, secondMaxHeightOfLevel);

        int maxHeight = height[root.val];

        int[] ans = new int[queries.length];
        int k = 0;
        for(int q : queries){

            int currLevel = level[q];
            int currHeight = height[q];
            int res = 0;
            if(maxHeightOfLevel[currLevel] == currHeight){
                res = maxHeight - maxHeightOfLevel[currLevel] + secondMaxHeightOfLevel[currLevel] -1;
            }else
                res = maxHeight-1; // we have to count edge not node , so -1
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

    //21. Detonate the Maximum Bombs -> https://leetcode.com/problems/detonate-the-maximum-bombs/description/
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;

        int res = 1;
        for(int i=0;i<n;i++){
            int[] vis = new int[n];
            int val = dfs(i, bombs, vis);
            res = Math.max(res, val);
        }
        return res;
    }
    int dfs(int index, int[][] bombs, int[] vis){
        vis[index] = 1;


        int cnt = 0;
        for(int i=0;i<bombs.length;i++){
            if(vis[i] == 1)
                continue;
            int dx = bombs[index][0] - bombs[i][0];
            int dy = bombs[index][1] - bombs[i][1];

            long distance = (long) dx * dx + (long) dy*dy;
            long r = (long) bombs[index][2] * bombs[index][2];

            if(distance <= r){
                cnt += dfs(i, bombs, vis);
            }
        }
        return cnt + 1;
    }
    //22. Guess the Word -> https://leetcode.com/problems/guess-the-word/description/
    public void findSecretWord(String[] words, Master master) {
        int n = words.length;

        List<String> list = new LinkedList<>();
        for(int i=0;i<n;i++){
            list.add(words[i]);
        }

        while(!list.isEmpty()){
            int rand = (int) (Math.random() * list.size());
            String selectedWord = list.get(rand);
            int val = master.guess(selectedWord);

            if(val == 6)
                return;

            List<String> l1 = new LinkedList<>();
            for(int i=0;i<list.size();i++){
                if(rand == i || filter(selectedWord, list.get(i)) != val)
                    continue;
                l1.add(list.get(i));
            }
            list = l1;
        }
    }
    int filter(String s1, String s2){
        int cnt = 0;
        for(int i=0;i<6;i++){
            if(s1.charAt(i) == s2.charAt(i))
                cnt++;
        }
        return cnt;
    }

    //23. Maximum Strictly Increasing Cells in a Matrix
    public int maxIncreasingCells(int[][] mat) {
        Map<Integer, List<int[]>> mp = new TreeMap<>((b, a) -> b-a);

        var n = mat.length;
        var m = mat[0].length;

        for(var i =0;i<n;i++){
            for(var j=0;j<m;j++){
                mp.computeIfAbsent(mat[i][j], key->new ArrayList<>()).add(new int[]{i, j});
            }
        }

        var dp = new int[n][m];
        var rowMax = new int[n];
        var colMax = new int[m];
        int res = 0;

        for(var entry : mp.entrySet()) {

            var value = entry.getValue();
            for(var curr : value){
                var r = curr[0];
                var c = curr[1];

                var currMaxCount = Math.max(rowMax[r], colMax[c]);
                dp[r][c] = currMaxCount + 1;
                res = Math.max(res, dp[r][c]);
            }

            for(var curr : value){
                var r = curr[0];
                var c = curr[1];
                rowMax[r] = Math.max(dp[r][c], rowMax[r]);
                colMax[c] = Math.max(colMax[c], dp[r][c]);
            }
        }
        return res;
    }
    //24. The Earliest Moment When Everyone Become Friends
    public static int minTime(int[][] logs, int n) {
       DisjointSet set = new DisjointSet(n);

        int res = 0;
        Arrays.sort(logs, (a, b) -> a[0] - b[0]);

        for (int[] log : logs) {

            int cost = log[0];
            int u = log[1];
            int v = log[2];

            if (set.findUParent(u) == set.findUParent(v))
                continue;

            res = Math.max(res, cost);
            set.unionBySize(u, v);
        }
        int cnt = 0;
        for(int i=0;i<n;i++){
            if(set.parent.get(i) == i)
                cnt++;
        }

        return cnt == 1 ? res : -1;
    }

    //25. 1713. Minimum Operations to Make a Subsequence -> https://leetcode.com/problems/minimum-operations-to-make-a-subsequence/
    public int minOperations(int[] target, int[] arr) {
        int n = target.length;
        int m = arr.length;

        Map<Integer, Integer> mp = new HashMap<>();
        for(int i=0;i<n;i++){
            mp.put(target[i], i);
        }

        List<Integer> list = new ArrayList<>();
        for(int i=0;i<m;i++){
            int val = arr[i];
            if(mp.containsKey(val))
                list.add(mp.get(val));
        }

        return n - lis(list);
    }

    int lis(List<Integer> list){

        List<Integer> curr = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            int pos = findPos(curr, list.get(i));

            if(pos == curr.size())
                curr.add(list.get(i));
            else
                curr.set(pos, list.get(i));
        }
        return curr.size();
    }

    int findPos(List<Integer> curr, int target){

        int low =0, high = curr.size()-1;

        while(low <= high){
            int mid = (low + high)/2;
            if(target <= curr.get(mid))
                high = mid-1;
            else
                low = mid+1;
        }
        return low;
    }
    //26. 1706. Where Will the Ball Fall
    public int[] findBall(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[] res = new int[m];

        for(int col=0;col<m;col++){
            int currCol = col;

            for(int row =0;row<n;row++){

                int nextCol = currCol + grid[row][currCol];

                if(nextCol < 0 || nextCol >=m || grid[row][nextCol] != grid[row][currCol]){
                    currCol = -1;
                    break;
                }
                currCol = nextCol;
            }
            res[col] = currCol;
        }
        return res;
    }
    //27. A town is building a watchtower. The watchtower is located at (0, 0).
    // Each unit height of the watchtower has a cost H. There are N houses located at (x, y)
    // coordinates. Each house will pay cost C if it comes under the surveillance of the watchtower.
    // The horizontal distance covered by the watchtower is the same as it's height.
    // Find out the max profit you can make.
    double solve(List<int[]> position, double H, double C) {

        List<Double> dist = new ArrayList<>();
        for (int[] pos : position) {
            double x = pos[0];
            double y = pos[1];
            double val = Math.sqrt(x * x  + y * y);
            dist.add(val);
        }
        Collections.sort(dist);
        double res = 0;
        for(int i=0;i<dist.size();i++){
            double dis = dist.get(i);
            double val = (i+1) * C - dis * H;
            res = Math.max(val, res);
        }
        return res;
    }
    //28. Given two strings s1 and s2, find out if they only differ by the insertion of a phrase
    // (more than one word is inserted in between them).
    static boolean checkIfPhrase(String s1, String s2){

        if(s1.length() > s2.length())
            return checkIfPhrase(s2, s1);

        String[] s11 = s1.split(" ");
        String[] s22 = s2.split(" ");

        int start = -1, end = -1;
        int i=0, j=0, n = s11.length, m = s22.length;
        int phraseCount = 0;

        while(j < m){
            if(i < n && s11[i].equals(s22[j])){
                i++;
            }else{
                if(start == -1)
                    start = j;
                end = j;
                phraseCount++;
            }
            j++;
        }
        if(i == n && (start == -1 || (end - start+1) >=2) && phraseCount <= 1)
            return true;
        return false;
    }
    //29. Longest increasing subsequence with one change allowed , strictly increasing
    static int LIS(int[] arr, int n){

        int[] dp1 = new int[n];
        Arrays.fill(dp1, 1);

        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(arr[j] < arr[i]){
                    dp1[i] = Math.max(dp1[i], dp1[j]+1);
                }
            }
        }
        int[] dp2 = new int[n];
        Arrays.fill(dp2, 1);

        for(int i=n-1;i>=0;i--){
            for(int j=i+1;j<n;j++){
                if(arr[i] < arr[j]){
                    dp2[i] = Math.max(dp2[i], dp2[j]+1);
                }
            }
        }

        int res = 1;
        for(int i=0;i<n;i++){
            int val = 0;
            if( i>0 && i <n-1  && arr[i-1] + 1 < arr[i+1]){
                val = dp1[i-1] + dp2[i+1] +1;
            }else
                val = Math.max(dp1[i], dp2[i]);
            res = Math.max(res, val);
        }
        return res;
    }
    //30. Android phone patterns : Given n,m which are a set of points,
    // we have to find the total possible unique patterns that can be drawn, min len of a
    // pattern is 1, n,m >=1. -> https://www.naukri.com/code360/problems/mobile-pattern-lock_1263698

    public int numberOfPatterns(int m, int n) {
        // Write Your Code here
        int[][] midEle = new int[10][10];
        midEle[1][3] = midEle[3][1] = 2;
        midEle[1][7] = midEle[7][1] = 4;

        midEle[3][9] = midEle[9][3] = 6;
        midEle[7][9] = midEle[9][7] = 8;

        midEle[1][9] = midEle[9][1] =
                midEle[2][8] = midEle[8][2] =
                        midEle[3][7] = midEle[7][3] =
                                midEle[4][6] = midEle[6][4] = 5;

        boolean[] vis = new boolean[10];

        int cnt = 0;
        for(int i=m;i<=n;i++){
            cnt += dfs(1, midEle, vis, i-1) * 4;
            cnt += dfs(2, midEle, vis, i-1) * 4;
            cnt += dfs(5, midEle, vis, i-1);
        }
        return cnt;
    }

    int dfs(int curr, int[][] midEle, boolean[] vis, int allowedCnt){

        if(allowedCnt <=0)
            return 1;

        vis[curr] = true;
        int cnt = 0;
        for(int i=1;i<=9;i++){
            int midVal = midEle[curr][i];
            if(vis[i] == false && (midVal == 0 || vis[midVal] == true)){
                cnt += dfs(i, midEle, vis, allowedCnt -1);
            }
        }
        vis[curr] = false;
        return cnt;
    }
    //31. There will be some tasks, and each task can have one or more subtasks, by default all
    // tasks have completion time 1 unit, but the comp_time of a parent task is the x if all
    // subtasks have time x, otherwise it is their sum, we have to find the total completion time.
    //    public static void main(String[] args) {
    //        int[][] arr = new int[][]{
    //                {0, 1},
    //                {0, 2},
    //                {1, 3},
    //                {1, 4},
    //                {2, 5},
    //                {6, 7},
    //        };
    //        int V = 9;  // 0-based indexing
    //        System.out.println(func(arr, V));
    //    }

    private static int func(int[][] edges, int V) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
        }
        return totalCompletionTime(adj, V);
    }

    static int totalCompletionTime(List<List<Integer>> adj, int V) {
        int cnt = 0;
        int[] vis = new int[V];
        int[] dp = new int[V];
        Arrays.fill(dp, -1); // Initialize memoization array

        for (int i = 0; i < V; i++) {
            if (dp[i] == -1) { // Ensures every node is computed correctly
                cnt += dfs(i, adj, vis, dp);
            }
        }
        return cnt;
    }

    static int dfs(int node, List<List<Integer>> adj, int[] vis, int[] dp) {
        if (dp[node] != -1) return dp[node]; // Return cached result
        vis[node] = 1; // Mark node as visited

        if (adj.get(node).isEmpty()) {
            return dp[node] = 1; // Store and return 1 if it's a leaf node
        }

        List<Integer> values = new ArrayList<>();
        for (int adjNode : adj.get(node)) {
            if (dp[adjNode] != -1) {
                values.add(dp[adjNode]); // Use stored result if available
            } else if (vis[adjNode] == 0) {
                values.add(dfs(adjNode, adj, vis, dp));
            }
        }

        int firstNum = values.get(0);
        boolean allEqual = values.stream().allMatch(val -> val == firstNum);
        return dp[node] = (allEqual ? firstNum : values.stream().reduce(0, Integer::sum));
    }
    //32. There are n people and each person has a specific order in which the songs should be played,
    //P1 : a,b,c
    //P2. : b, e , f
    static List<Character> getSongOrder(List<List<Character>> songs){
        Map<Character, List<Character>> graph = new LinkedHashMap<>();

        for (List<Character> song : songs) {
            char parent = song.get(0);
            for (int j = 1; j < song.size(); j++) {
                graph.computeIfAbsent(parent, e -> new ArrayList<>()).add(song.get(j));
                parent = song.get(j);
            }
            graph.computeIfAbsent(parent, e -> new ArrayList<>());
        }

        Stack<Character> st = new Stack<>();
        Set<Character> vis = new HashSet<>();
        for(var entry : graph.entrySet()){
            char node = entry.getKey();
            if(!vis.contains(node))
                dfs(node, graph, st, vis);
        }
        List<Character> res = new ArrayList<>(st);
        Collections.reverse(res);
        return res;
    }
    static void dfs(char node, Map<Character, List<Character>> graph, Stack<Character> st, Set<Character> vis){
        vis.add(node);

        for (Character adjNode : graph.get(node)) {
            if (!vis.contains(adjNode)) {
                dfs(adjNode, graph, st, vis);
            }
        }

        st.add(node);
    }

    //33. Given roots of 2 n-ary trees write code to merge them. Complete the following function:
    // https://leetcode.com/discuss/post/5847086/google-l3-phone-screen-by-anonymous_user-9ybo/
    static Node1 solve(Node1 t1, Node1 t2){

        if(t1 == null)
            return t2;
        if(t2 == null)
            return t1;

        Node1 mergedNode = new Node1(t2.name, t2.value);

        Map<String, Node1> childrenOfT1 = new LinkedHashMap<>();

        for(Node1 node: t1.children){
            childrenOfT1.put(node.name, node);
        }

        List<Node1> mergedChildren = new ArrayList<>();
        for(Node1 node : t2.children){

            if(childrenOfT1.containsKey(node.name)){
                mergedChildren.add(solve(childrenOfT1.remove(node.name),node));
            }else
                mergedChildren.add( node);
        }
        mergedChildren.addAll(childrenOfT1.values());

        mergedNode.children = mergedChildren;
        return mergedNode;
    }

    //34. 962. Maximum Width Ramp -

    public int maxWidthRamp(int[] nums) {
        Stack<Integer> st = new Stack<>();
        int n = nums.length;
        for(int i=0;i<n;i++){
            if(st.isEmpty() || nums[st.peek()] > nums[i])
                st.push(i);
        }
        int res = 0;
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && nums[st.peek()] <= nums[i]){
                int val = i- st.peek();
                res = Math.max(res, val);
                st.pop();
            }
        }
        return res;
    }

    //35. Question: Given n routers placed on a Cartesian plane and provided with a source and
    // destination vertex, the task was to determine whether it was possible to reach the
    // destination. Only adjacent vertices could be explored, and a vertex was considered
    // adjacent if it had the minimum distance from the current vertex while remaining within
    // a given threshold. Additionally, once a vertex was visited, the previously visited node became
    // inactive (i.e., could no longer be used). The goal was to determine if a path existed from the
    // source to the destination under these constraints.

    static double solve(int[][] points , int V, int src, int dest, double threshold){
        List<List<double[]>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<points.length;i++){
            for(int j=i+1;j<points.length;j++){
                double dist = getDist(points[i], points[j]);
                if(dist <= threshold){
                    adj.get(i).add(new double[]{j, dist});
                }
            }
        }

        double[] count = new double[V];
        Arrays.fill(count, 1e9);
        Queue<double[]> q = new LinkedList<>();
        q.add(new double[]{src, 0});
        while(!q.isEmpty()){

            int node = (int)q.peek()[0];
            double dist = q.remove()[1];

            if(node == dest)
                continue;
            for(double[] adjNode : adj.get(node)){
                int currNode = (int)adjNode[0];
                double currDist = adjNode[1] + dist;
                if(count[currNode] > currDist){
                    count[currNode] = currDist;
                    q.add(new double[]{currNode, currDist});
                }
            }
        }
        return count[dest];
    }
    static double getDist(int[] a, int[] b){
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1]-b[1], 2));
    }


    //36. Dungeon Game -> https://leetcode.com/problems/dungeon-game/description/
    public int calculateMinimumHP(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> b[3] - a[3]);

        pq.add(new int[]{0, 0, mat[0][0], mat[0][0]});
        int res = 0;
        while(!pq.isEmpty()){
            int row = pq.peek()[0];
            int col = pq.peek()[1];
            int cost = pq.peek()[2];
            int min = pq.remove()[3];
            if(row == n-1 && col == m-1){
                res = min;
                break;
            }

            if(valid(row+1, col, n, m)){
                int currCost = cost + mat[row+1][col];
                int currMin = Math.min(min, currCost);
                pq.add(new int[]{row+1, col,currCost, currMin});
            }

            if(valid(row, col+1, n, m)){
                int currCost = cost + mat[row][col+1];
                int currMin = Math.min(min, currCost);
                pq.add(new int[]{row, col+1, currCost, currMin});
            }
        }

        return res>0 ? 1 : -1 * res +1;

    }
    boolean valid(int row, int col, int n, int m) {
        return row >=0 && row <n && col >=0 && col < m;
    }

    //------------------------------- OR------------------------
    public int calculateMinimumHP1(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int[][] dp = new int[n][m];

        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0 ;j--){

                if(i == n-1 && j == m-1){
                    dp[i][j] = Math.min(0, mat[i][j]);
                }else if( i == n-1){
                    dp[i][j] = Math.min(0, dp[i][j+1] + mat[i][j]);
                }else if( j == m-1){
                    dp[i][j] = Math.min(0, dp[i+1][j] + mat[i][j]);
                }else{
                    dp[i][j] = Math.min(0, Math.max(dp[i][j+1], dp[i+1][j]) + mat[i][j]);
                }
            }
        }
        return dp[0][0] * -1 + 1;
    }
    //37. 2035. Partition Array Into Two Arrays to Minimize Sum Difference || meet in the middle algorithm-> https://leetcode.com/problems/partition-array-into-two-arrays-to-minimize-sum-difference/description/
    public int minimumDifference(int[] nums) {

        int totalSum = Arrays.stream(nums).sum();
        int N = nums.length;
        int n = N/2;

        List<List<Integer>> left = new ArrayList<>();
        List<List<Integer>> right = new ArrayList<>();

        for(int i=0;i<=n;i++){
            left.add(new ArrayList<>());
            right.add(new ArrayList<>());
        }

        int powOfN = 1 <<n;
        for(int i=0;i<powOfN;i++){
            int sum1=0, sum2 = 0;
            int cnt = 0;
            for(int j=0;j<n;j++){

                if((i & (1 << j)) >0){
                    sum1 += nums[j];
                    sum2 += nums[n+j];
                    cnt++;
                }
            }
            left.get(cnt).add(sum1);
            right.get(cnt).add(sum2);
        }

        for(int i=0;i<=n;i++){
            Collections.sort(right.get(i));
        }

        int res = (int) 1e9;

        for(int i=0;i<=n;i++){
            for(int leftSum1 : left.get(i)){
                int leftSum2 = lb(right.get(n-i), totalSum/2 - leftSum1);

                int totalLeftSum = Math.abs(totalSum - 2*(leftSum1 + leftSum2));
                res = Math.min(res, totalLeftSum);
            }
        }

        return res;
    }
    int lb(List<Integer> arr, int target){
        int low = 0, high = arr.size()-1;
        int res = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (Math.abs(arr.get(mid) - target) < Math.abs(res - target)) {
                res = arr.get(mid);
            }
            if (arr.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return res;
    }

    //38. given a lattice kinda graph where each node is either a torch node that has power 16 or wire node where value is 0,
    //if power node is connected to wire node, it will transmit power to wire and value would become 15 from 0
    // (1 value would be lost during transmission), again if this wire node is connected to another wire node then
    // value would become 14 of that node.
    //for eg 16 -> 0 -> 0 would become 15 -> 14 -> 13, unless there is one more torch node ahead, then
    //16 -> 0 -> 0 -> 16
    //16 -> 15 -> 14 -> 16
    //16 -> 15 -> 15 <- 16

    static void flowCurrent(int[][] edges, int V, int[] volts){
       List<List<Integer>> adj = new ArrayList<>();

       for(int i=0;i<V;i++)
           adj.add(new ArrayList<>());

       for (int[] edge : edges){
           int u = edge[0];
           int v = edge[1];
           adj.get(u).add(v);
       }
       Queue<int[]> q = new LinkedList<>();
       for(int i=0;i<V;i++){
           if(volts[i] > 0)
               q.add(new int[]{i,volts[i]});
       }

        while(!q.isEmpty()){

            int node = q.peek()[0];
            int volt = q.remove()[1];

            for(int adjNode : adj.get(node)){

                if(volts[adjNode] < volt -1){
                    q.add(new int[]{adjNode, volt-1});
                    volts[adjNode] = volt-1;
                }
            }

        }

        for(int i =0;i<V;i++){
            System.out.println(i + ": "+ volts[i]);
        }
    }

    //39. 1820. Maximum Number of Accepted Invitations -> https://algo.monster/liteproblems/1820
    // how many persons can be matched for prome dance like that.
    static int findTotalPersons(int[][] grid){
        int boysCount = grid.length;
        int girlsCount = grid[0].length;

        int[] matched = new int[girlsCount];
        Arrays.fill(matched, -1);

        int res = 0;
        for(int i=0;i<boysCount;i++){
            boolean[] vis = new boolean[girlsCount];
            if(solve(i, grid, matched, vis))
                res ++;
        }
        return res;
    }
    static boolean solve(int boy, int[][] grid, int[] matched, boolean[] vis){

        for(int girl =0;girl < matched.length;girl++){

            if(grid[boy][girl] == 0 || vis[girl])
                continue;
            vis[girl] = true;
            if(matched[girl] == -1 || solve(matched[girl], grid, matched, vis)){
                matched[girl] = boy;
                return true;
            }
            vis[girl] = false;
        }
        return false;
    }

    //40.   This question is similar to above question
    // questions[
    //{id:1, tags: ["MAC", "VSCODE"]},
    //{id:2, tags: ["PY", "AI"]}
    //{id:3, tags: ["JAVA", "OS"]}
    //{id:4, tags: ["PY", "NW"]}
    //]
    //
    //Volunteer[
    //{id: "1", tags:["PY",""NW], name: "A"},
    //{id: "2", tags:["AI"], name: "B"},
    //{id: "3", tags:["JAVA","NW], name: "C"},
    //{id: "4", tags:["JAVA","NW"], name: "D"}
    //]
    //
    //Assign question to volunteers such that each question is assigned to at most one volunteer based on tags match.
    //One volunteer can take at most one question and maximise the question assigned to volunteer.
    //
    //for this example
    //A can take question 4(PY match)
    //B can take question 2(AI match)
    //C can take question 3(Java match)
    //Question one no one can take as not match.
//    public static void main(String[] args) {
//        List<String> questions = new ArrayList<>(List.of("MAC VSCODE", "PY AI", "JAVA OS", "PY NW"));
//        List<String> volunteers = new ArrayList<>(List.of("PY NW", "AI", "JAVA NW", "JAVA, NW"));
//        System.out.println(totalAssignedQuestions(questions, volunteers));
//    }

    static int totalAssignedQuestions(List<String> questions, List<String> volunteers){

        int quesCount = questions.size();
        int volCount = volunteers.size();

        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<quesCount;i++){
            adj.add(new ArrayList<>());
        }

        for(int i=0;i<quesCount;i++){
            for(int j=0;j<volCount;j++){

                if(isCommonTags(questions.get(i), volunteers.get(j))){
                    adj.get(i).add(j);
                }
            }
        }

        int[] matched = new int[volCount];
        Arrays.fill(matched, -1);
        int totalCount = 0;

        for(int ques= 0;ques<quesCount;ques++){
            boolean[] vis = new boolean[volCount];
            if(dfs(ques, adj, matched,vis)){
                totalCount++;
            }
        }
        for(int i=0;i<matched.length;i++){

            if(matched[i] == -1)
                System.out.println("Question " + ((char) (i + 'A')) + " not assigned");
            else
                System.out.println("Question " + ((char) (i + 'A')) + ": volunteer " + (matched[i]+1));
        }
        return totalCount;
    }

    static boolean isCommonTags(String str1, String str2){
        Set<String> set = new HashSet<>(Arrays.asList(str1.split(" ")));

        for(String s : str2.split(" ")){
            if(set.contains(s))
                return true;
        }
        return false;
    }

    static boolean dfs(int ques, List<List<Integer>> adj, int[] matched, boolean[] vis){

        for(int vol : adj.get(ques)){

            if(vis[vol])
                continue;
            vis[vol] = true;
            if(matched[vol] == -1 || dfs(matched[vol], adj, matched, vis)){
                matched[vol] = ques;
                return true;
            }
            vis[vol] = false;
        }
        return false;
    }

    //41.
    //Basically I needed to implement cli.
    //
    //    Replace files with directories if all files in directory are
    //            specified
    //    Example input & output:
    //    allFiles = [
    //            "a/b/c/d.txt",
    //            "a/b/c/e.txt",
    //            "a/b/b.txt",
    //            "a/b/e.txt",
    //            "b/c/d.txt"
    //            ]
    //    subsetFiles = [
    //            "a/b/c/d.txt",
    //            "a/b/c/e.txt",
    //            "a/b/b.txt",
    //            "b/c/d.txt"
    //            ]
    //    output=[
    //            "a/b/c",
    //            "a/b/b.txt",
    //            "b"
    //            ]

    class Solution{
//        public static void main(String[] args) {
//            List<String> dir = new ArrayList<>(List.of("a/b/c/d.txt",
//                    "a/b/c/e.txt",
//                    "a/b/b.txt",
//                    "a/b/e.txt",
//                    "b/c/d.txt"));
//            List<String> selectedDir = new ArrayList<>(List.of("a/b/c/d.txt",
//                    "a/b/c/e.txt",
//                    "a/b/b.txt",
//                    "b/c/d.txt"));
//            func(dir, selectedDir);
//        }
        void func(List<String> directories, List<String> selectedDir){
            Trie trie = new Trie();

            for(String s : directories){
                trie.insert(s);
            }

            for (String s : selectedDir){
                trie.visit(s);
            }

            Set<String> set = new LinkedHashSet<>();
            for(String s : selectedDir){
                set.addAll(trie.getResults(s));
            }

            for(String res : set)
                System.out.println(res);
        }

        class TrieNode{
            TrieNode[] links = new TrieNode[26];
            Map<String, Boolean> fileVisMap = new HashMap<>();
            int pathVisCount = 0;
            void addFile(String fileName){
                fileVisMap.put(fileName, false);
            }
            void markFileAsVis(String fileName){
                if(!fileVisMap.containsKey(fileName))
                    return;
                fileVisMap.put(fileName, true);
            }
        }

        class Trie{

            TrieNode root;
            Trie(){
                root = new TrieNode();
            }

            void insert(String s){
                String[] str = s.split("/");

                TrieNode node = root;
                for (String dir : str) {

                    if (dir.trim().isEmpty())
                        continue;
                    if (dir.contains("txt")) {
                        node.addFile(dir);
                        break;
                    }
                    char ch = dir.charAt(0);
                    if (node.links[ch - 'a'] == null)
                        node.links[ch - 'a'] = new TrieNode();
                    node = node.links[ch - 'a'];
                    node.pathVisCount++;
                }
            }

            void visit(String s){

                String[] str = s.split("/");
                int n = str.length;
                TrieNode node = root;

                for (String dir : str) {
                    if (dir.trim().isEmpty())
                        continue;
                    if (dir.contains("txt")) {
                        node.markFileAsVis(dir);
                        break;
                    }
                    char ch = dir.charAt(0);

                    if (node.links[ch - 'a'] == null)
                        return;
                    node = node.links[ch - 'a'];
                    node.pathVisCount--;
                }
            }

            List<String> getResults(String s){

                StringBuilder sb = new StringBuilder();
                String[] str = s.split("/");
                TrieNode node = root;

                for (String dir : str) {
                    if(dir.contains("txt"))
                        break;
                    if (dir.trim().isEmpty())
                        continue;
                    char ch = dir.charAt(0);

                    node = node.links[ch - 'a'];
                    sb.append(ch).append("/");
                    if(node.pathVisCount == 0)
                        break;
                }

                String base = sb.toString();
                List<String> res = new ArrayList<>();
                if(node.pathVisCount == 0) {
                    res.add(base.substring(0, base.length() - 1));
                } else {
                    for(var entry : node.fileVisMap.entrySet()){
                        String fileName = entry.getKey();
                        boolean isVis = entry.getValue();
                        if(isVis){
                            res.add(base + fileName);
                        }
                    }
                }
                return res;
            }
        }

    }

    //42. assume that "byte" contains only "a" to "f"
    //input: "abcdefacbeddefd"
    //
    //"a" is present
    //"b" is present
    //..
    //"f" is present
    //"aa" is not present
    //"ab" is present
    //"ac" is present
    //
    //you can return "aa" or "ad" or "ae"... "ff"
    //but not "ab" "ac" "bc"
    //
    //testcases
    //input - aabcdf
    //output - e
    //Check for single byte shortest byte sequence a,b,c, .... f

//    public static void main(String[] args) {
//        System.out.println(findMissing("abcdefacbeddefd"));
//    }

    static String findMissing(String input){

        char[] allowedChar = "abcdef".toCharArray();

        Set<Character> seenChar = new HashSet<>(); // 1 size

        for(char ch : input.toCharArray()){
            seenChar.add(ch);
        }

        for(char ch : allowedChar){
            if(!seenChar.contains(ch) )
                return String.valueOf(ch);
        }

        Set<String> seenWords = new HashSet<>();
        for(int i=0;i<input.length()-1;i++){
            seenWords.add(input.substring(i, i+2));
        }

        for(char ch1 : allowedChar){
            for(char ch2 : allowedChar){
                if(!seenWords.contains(""+ch1 + ch2))
                    return ""+ch1 + ch2;
            }
        }

        return "";
    }

    //43. There is a testRunner function that takes multiple unit test and returns if those uts are
    // executed together & then there is some error or not. If no error then return true other wise false.
    // You have been given N unit tests, and you know that when you run all test cases at a time then it fails.
    // Now you need to find at least one pair of UTs, which fails when executed at the same time using the test runner.
    // Its easy when we assume that the testRunner takes O(1) to execute any number of test cases.
    // But for the case when the test runner takes O(n) time to execute n test cases at a time then
    // find the optimal way to find one pair of failed UTs.
    //Note there may be multiple pairs that fail with each other, but need to report only one.
    // Also all the UTs are running ok when executed individually.

//    public static void main(String[] args) {
//        List<Integer> testCases = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
//        TestRunner testRunner = new TestRunner();
//        int[] failingPair = testRunner.findFailingPairs(testCases);
//        if (failingPair != null) {
//            System.out.println("Failing Pair: " + failingPair[0] + ", " + failingPair[1]);
//        } else {
//            System.out.println("No failing pair found");
//        }
//    }

    static class TestRunner{
        boolean testRunner(Set<Integer> testSet){

            Set<Set<Integer>> failingPairs = new HashSet<>();
            failingPairs.add(new HashSet<>(List.of(2,5)));
            //failingPairs.add(new HashSet<>(List.of(3, 7)));

            for(Set<Integer> pair : failingPairs){
                if(testSet.containsAll(pair))
                    return true;
            }
            return false;
        }

        int[] findFailingPairs(List<Integer> tests){

            if(tests.size() <= 1)
                return new int[]{-1, -1};

            int mid = tests.size() / 2;

            List<Integer> left = tests.subList(0, mid);
            List<Integer> right = tests.subList(mid, tests.size());

            if (testRunner(new HashSet<>(left)))
                return findFailingPairs(left);

            if (testRunner(new HashSet<>(right)))
                return findFailingPairs(right);

            return findCross(left, right);

        }

        int[] findCross(List<Integer> left, List<Integer> right){

            for(int l : left ){
                for(int r : right){
                    if(testRunner(new HashSet<>(List.of(l, r))))
                        return new int[]{l, r};
                }
            }
            return new int[]{-1, -1};
        }
    }

    //44. Variation of coin change.
    //we are given dp array that we created while finding number of ways to make the target sum.
    // We need to find the coins array, using which this dp array is created.
    //Example:
    //target = 10
    //number of ways to make 10: 3
    //Input: [1, 0, 1, 0, 1, 1, 2, 1, 2, 1, 3]
    //Output: [2, 5, 6]

//    public static void main(String[] args){
//        int[] dp = new int[]{1,1,2,3,4,6};
//        int target = 4;
//        for(int val : constructOriginalArray(dp, target)){
//            System.out.print(val+" ");
//        }
//    }
    static List<Integer> constructOriginalArray(int[] dp, int target) {
        List<Integer> coins = new ArrayList<>();
        int n = dp.length; // dp array should be equal to target+1
        if(target > n)
            return coins;
        for(int i=1;i<=target;i++) {
            if (dp[i] == 1) {
                for(int j=target;j>=i;j--) {
                    dp[j] = dp[j] - dp[j-i];
                }
                coins.add(i);
            }
        }
        return coins;
    }
    //45. Parallel course II -> https://leetcode.com/problems/parallel-courses-ii/description/
    class Solution45 {
        int n;
        int k;
        Map<String, Integer> memo;
        public int minNumberOfSemesters(int n, int[][] relations, int k) {
            this.n = n;
            this.k = k;

            memo = new HashMap<>();

            List<List<Integer>> adj = new ArrayList<>();
            for(int i=0;i<n;i++)
                adj.add(new ArrayList<>());

            int[] indegree = new int[n];

            for(int[] edge : relations){
                int u = edge[0]-1;
                int v = edge[1]-1;
                adj.get(u).add(v);

                indegree[v] ++;
            }

            int mask = 0;
            for(int i= 0;i<n;i++){
                if(indegree[i] == 0)
                    mask = mask | 1 <<i;
            }

            return solve(adj, indegree, mask);
        }

        int solve(List<List<Integer>> adj, int[] indegree, int mask){
            if(mask == 0)
                return 0;

            String key = mask +","+ Arrays.toString(indegree);
            if(memo.containsKey(key)) return memo.get(key);

            List<Integer> avl = new ArrayList<>();
            for(int i=0;i<n;i++){
                if(indegree[i] > 0 || (mask & 1 << i) == 0)
                    continue;
                avl.add(i);
            }

            List<List<Integer>> allCombi = getCombi(avl, k);
            int res = (int) 1e9;
            for(List<Integer> combi : allCombi){

                int newMask = mask;
                int[] newIndegree = Arrays.copyOf(indegree, n);
                for(int node : combi){

                    for(int adjNode : adj.get(node)){
                        newIndegree[adjNode] --;
                        if(newIndegree[adjNode] == 0){
                            newMask = newMask | (1 << adjNode);
                        }
                    }
                    newMask = newMask ^ ( 1 << node);

                }
                res = Math.min(res, 1 + solve(adj, newIndegree, newMask));
            }
            memo.put(key, res);
            return res;
        }
        List<List<Integer>> getCombi(List<Integer> avl, int k){

            List<List<Integer>> res = new ArrayList<>();
            if(avl.size() <= k){
                res.add(avl);
                return res;
            }
            List<Integer> ds = new ArrayList<>();
            dfs(avl, 0, k, ds, res);
            return res;
        }
        void dfs(List<Integer> avl, int i, int k, List<Integer> ds, List<List<Integer>> res){
            if( k == 0){
                res.add(new ArrayList<>(ds));
                return ;
            }
            if(i == avl.size())
                return;

            dfs(avl, i+1, k, ds, res);
            ds.add(avl.get(i));
            dfs(avl, i+1, k-1, ds, res);
            ds.remove(ds.size()-1);
        }
    }
    //46. 2127. Maximum Employees to Be Invited to a Meeting -> https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/description/
    public int maximumInvitations(int[] fav) {
        int n = fav.length;
        int[] indegree = new int[n];

        for(int i=0;i<n;i++){
            indegree[fav[i]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(indegree[i] == 0)
                q.add(i);
        }

        int[] len = new int[n];
        Arrays.fill(len, 1);

        while(!q.isEmpty()){

            int node = q.remove();
            int adjNode = fav[node];

            len[adjNode] = Math.max(len[adjNode], len[node]+1);

            indegree[adjNode]--;
            if(indegree[adjNode] == 0){
                q.add(adjNode);
            }

        }

        int[] vis = new int[n];
        int chainSum = 0, maxCircle = 0;
        for(int i=0;i<n;i++){

            if(indegree[i] <=0 || vis[i] == 1)
                continue;

            int cnt = 0, curr = i;
            while(vis[curr] == 0){
                vis[curr] = 1;
                cnt ++;
                curr = fav[curr];
            }

            if(cnt == 2 ){
                curr = i;
                int next = fav[i];
                chainSum += len[curr] + len[next];
            }else
                maxCircle = Math.max(maxCircle, cnt);
        }
        return Math.max(maxCircle, chainSum);
    }
    //47.Validate if the equation is syntactically correct.
    //
    //Valid operators: +, -, a-z, (, )
    //Test cases:
    //Valid - a + x = b + (c + a)
    //Invalid - a + x = (ending with =; doesn't have RHS)
    //Invalid - a + -x = a + b (- in -x is a unary operator)

    static boolean isValid(String s){
        String[] arr = s.split("=");
        if(arr.length != 2 || arr[0].trim().isEmpty() || arr[1].trim().isEmpty())
            return false;

        return check(arr[0]) && check(arr[1]);
    }

    static boolean check(String s){
        Stack<Character> st = new Stack<>();
        boolean expectOperand = true;

        for(int i=0;i<s.length();i++){

            char ch = s.charAt(i);
            if(ch == ' ')
                continue;
            if(ch == '('){
                st.add(ch);
                expectOperand = true;
            }else if(ch == ')'){
                if(st.isEmpty() || st.pop() != '(') return false;

                expectOperand = false;

            }else if(Character.isLetter(ch)){
                if(!expectOperand) return false;
                expectOperand = false;
            }else{
                if(expectOperand) return false;
                expectOperand = true;
            }
        }


        return st.isEmpty() && !expectOperand;
    }

    //48. Basic Calculator II -> https://leetcode.com/problems/basic-calculator-ii/description/

    //49. Expression Add Operators -> https://leetcode.com/problems/expression-add-operators/description/
    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        solve(0, num, 0, 0, target, "", res);
        return res;
    }

    void solve(int i, String num, long sum, long prev, int target, String path, List<String> res){
        if(i == num.length()){
            if(sum == target){
                res.add(path);
            }
            return;
        }

        for(int j=i;j<num.length();j++){

            if( j > i && num.charAt(i) == '0')
                return;
            String currValStr = num.substring(i, j+1);
            long currVal = Long.parseLong(currValStr);

            if( i == 0){
                solve(j+1, num, currVal, currVal, target, path + currValStr, res);
            }else{

                long revertPrevOp = sum - prev;

                solve(j+1, num, sum + currVal, currVal, target, path +"+"+ currValStr, res);
                solve(j+1, num, sum - currVal, -currVal, target, path +"-"+ currValStr, res);
                solve(j+1, num, revertPrevOp + prev * currVal, prev * currVal, target, path +"*"+ currValStr, res);

//                if(currVal > 0){
//                    solve(j+1, num, sum / currVal, currVal, target, "(" + path +")"+"/"+ currValStr, res);
//                    solve(j+1, num, revertPrevOp + prev/currVal, prev/currVal, target, path +"/"+ currValStr, res);
//                }
//                solve(j+1, num, sum * currVal, currVal, target, "(" + path +")" +"*"+ currValStr, res);

            }
        }
    }
    //50. Different Ways to Add Parentheses and solve expression-> https://leetcode.com/problems/different-ways-to-add-parentheses/description/

    class Solution1 {
        class ExprResult {
            int val;
            String expr;
            ExprResult(int val, String expr) {
                this.val = val;
                this.expr = expr;
            }
        }
        public List<Integer> diffWaysToCompute(String exp) {
            List<ExprResult> res = solve(exp);

            for(ExprResult e : res){
                System.out.println(e.expr + " = " + e.val);
            }

            return res.stream().map(e -> e.val).collect(Collectors.toList());

        }
        public List<ExprResult> solve(String exp) {

            List<ExprResult> res = new ArrayList<>();

            for(int i=0;i<exp.length();i++){
                char op = exp.charAt(i);
                if(op == '+' || op == '-' || op == '*'){
                    List<ExprResult> left = solve(exp.substring(0, i));
                    List<ExprResult> right = solve(exp.substring(i+1));
                    for(ExprResult l : left){
                        for(ExprResult r : right){
                            int val = operations(l.val, r.val, op);
                            res.add(new ExprResult(val, "(" + l.expr + op + r.expr + ")"));
                        }
                    }
                }
            }
            if(res.size() == 0){
                int val = Integer.parseInt(exp);
                res.add(new ExprResult(val, exp));
            }
            return res;
        }
        int operations(int x, int y, char op){
            switch(op){
                case '+' :
                    return x + y;
                case '-' :
                    return x - y;
                case '*':
                    return x * y;
            }
            return 0;
        }
    }
    // 51. You're given a list of elements. Each element has a unique id and 3 properties. Two elements are
    // considered as duplicates if they share any of the 3 properties. Please write a function that
    // takes the input and returns all the duplicates.
    //
    //Input:
    //E1: id1, p1, p2, p3
    //E2: id2, p1, p4, p5
    //E3: id3, p6, p7, p8
    //
    //Output: {{id1, id2}, {id3}}

//    public static void main(String[] args) {
//        List<String> input = List.of(
//                "E1: id1, p1, p2, p3",
//                "E2: id2, p1, p4, p5",
//                "E3: id3, p6, p7, p8",
//                "E4: id4, p4, p9",
//                "E5: id5, p10",
//                "E6: id6, p8, p11"
//        );
//        List<List<String  >> res = solve(input);
//        for(List<String > a : res){
//            for(String s : a){
//                System.out.print(s);
//            }
//            System.out.println();
//        }
//    }

    static List<List<String  >> solve(List<String> input){
        int n = input.size();
        DisjointSet set = new DisjointSet(n);
        Map<String , Integer> propertyIndexMap = new HashMap<>();

        for(int i=0;i<n;i++){

            String[] arr = input.get(i).split(",");

            for(int j=1;j<arr.length;j++){

                if(propertyIndexMap.containsKey(arr[j])){
                    int prevIndex = propertyIndexMap.get(arr[j]);
                    set.unionBySize(prevIndex, i);
                }else
                    propertyIndexMap.put(arr[j], i);
            }
        }

        Map<Integer, Set<Integer>> indexPropertyMap = new HashMap<>();


        for(var entry : propertyIndexMap.entrySet()){
            int index = entry.getValue();
            int upi = set.findUParent(index);

            if(indexPropertyMap.containsKey(upi)){
                indexPropertyMap.get(upi).add(index);
            }else
                indexPropertyMap.put(upi, new HashSet<>(List.of(index)));
        }

        List<List<String>> res = new ArrayList<>();
        for(var entry : indexPropertyMap.entrySet()){

            List<String> list = new ArrayList<>();
            for(int index : entry.getValue()){
                String s = input.get(index).split(",")[0].split(":")[1];
                list.add(s);
            }
            res.add(list);
        }

        return res;
    }
    //52. You are assigned the task of determining how many gifts an uncle can purchase
    // for his nephew, given that he saves money daily for this purpose. The nephew has
    // prepared a list of gifts, each with a designated day for purchase and a specified cost.
    // The uncle begins with no savings but accumulates $1 each day Your objective is to calculate
    // the maximum number of gifts the uncle can afford from the list, adhering to the financial
    // constraints and timing.

    static int maxGifts(int[][] gifts, int k) {
        // Sort gifts by the deadline (day the gift must be bought by)
        Arrays.sort(gifts, (a,b) -> a[1]-b[1]);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b-a);
        int totalMoneySaved = 0;
        int prevDay = 0;
        int totalSpent = 0;
        for (int[] gift : gifts) {
            int cost = gift[0];
            int giftDay = gift[1];
            int currDay = giftDay;

            // The gift must be bought before or on day k
            if (giftDay > k) break;


            totalMoneySaved += currDay-prevDay;
            totalSpent += cost;

            maxHeap.add(cost);

            // Can the uncle afford this gift on time?
            if (totalSpent > totalMoneySaved) {
                totalSpent -= maxHeap.poll();
            }
        }

        return maxHeap.size(); // Number of gifts bought
    }
    //53. I was given a binary matrix and asked to find the upper-left corner
    // of the largest square of 1's.
    // The follow-up question was: What if we are allowed to switch at most k zeros to 1's?
    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            dp[i][0] = matrix[i][0];
        }
        for (int j = 0; j < m; j++) {
            dp[0][j] = matrix[0][j];
        }

        int x = -1, y = -1;
        int maxSize = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    dp[i][j] = 0;
                } else {
                    int size = Math.min(dp[i - 1][j], Math.min(dp[i - 1][j - 1], dp[i][j - 1]));
                    dp[i][j] = 1 + size;
                }

                if (maxSize < dp[i][j]) {
                    maxSize = dp[i][j];
                    x = i - maxSize + 1;
                    y = j - maxSize + 1;
                }
            }
        }

        System.out.println(maxSize); // max size of the square
        System.out.println("row: " + x + " col: " + y); // starting of maxSqare
        return maxSize;
    }
    // follow ups when upto k zeros can be modified to k ones
    public int countSquares(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[][] prefix = new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int zero = matrix[i-1][j-1] == 0 ? 1 : 0;
                int val = prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1] + zero;

                prefix[i][j] = val;
            }
        }


        int min = Math.min(n, m);

        boolean flag = false;
        int maxSize = 0;

        for(int size = min;size >0;size--){
            for(int i = 0;i <= n-size;i++){
                for(int j = 0;j <= m-size;j++){

                    int firstRow = i+1, firstCol = j+1;
                    int lastRow = i + size, lastCol = j + size;
                    int cnt = prefix[lastRow][lastCol]
                            - prefix[lastRow][firstCol-1]
                            - prefix[firstRow-1][lastCol]
                            + prefix[firstRow-1][firstCol-1];
                    if(cnt <= k){
                        maxSize = size;
                        flag = true;
                        break;
                    }
                }

                if(flag)
                    break;
            }
            if(flag)
                break;
        }
        System.out.println(maxSize);
        return maxSize;

    }
    //54. Let's say you have given a list of neighborhood, each neighborhood has different houses in it,
    // e.g {{8,2,9}, {4,6,4}, {4,5,1}}.
    // Respective house color is also given {{'x','r','b'}, {'z', 'r', 'q'}, {'c', 'x', 'a'}}.
    //We need to return the sorted house order with color attached to it but one condition is that
    // one neighborhood can't have same house number twice in it. Result of above example is:
    //{{'1a','2r','4z'}, {'4q','5x','6r'}, {'4c','8x','9b'}}.

    static class House{
        int num;
        List<Character> colors = new ArrayList<>();
        int index = 0;
    }
    static List<List<String>> findHouse(List<List<Integer>> houses, List<List<Character>> colors){
        int n = houses.size();
        int m = houses.get(0).size();
        Map<Integer, List<Character>> map = new HashMap<>();

        for(int i=0;i<houses.size();i++) {
            for (int j = 0; j < houses.get(i).size(); j++) {
                int house = houses.get(i).get(j);
                char color = colors.get(i).get(j);
                map.computeIfAbsent(house, e -> new ArrayList<>()).add(color);
            }
        }

        Queue<House> pq = new PriorityQueue<>((a, b) -> b.colors.size() - a.colors.size());
        for(var entry : map.entrySet()){
            House house = new House();
            house.num = entry.getKey();
            house.colors = entry.getValue();
            pq.add(house);
        }
        List<List<String>> res = new ArrayList<>();
        for(int i=0;i<n;i++){
            Queue<House> q = new LinkedList<>();
            List<String> neighbour = new ArrayList<>();
            for(int j=0;j<m;j++){

                House h = pq.remove();
                neighbour.add(""+h.num + h.colors.get(h.index));
                h.index++;
                if(h.index < h.colors.size())
                    q.add(h);
            }
            Collections.sort(neighbour);
            res.add(neighbour);
            pq.addAll(q);
        }
        return res;
    }
    //55. Let's say you have given a string and list of words. We need to find words that we can make from
    // the given string by deleting chars from string from any position.
    // Follow up : if we have listOfWords given in the starting and we can preprocess it and then find out the same result.
    //Eg:
    //string: "ozweitosgshg", listOfWords: ["egg", "zoo"]

    class Word{
        int index;
        int len;
        String word;
        Word(String word){
            this.word =word;
            this.index = 0;
            this.len = word.length();
        }

    }
    List<String> findWord(String text, List<String> words){

        Map<Character, List<Word>> mp = new HashMap<>();
        for(String word : words){
            char ch = word.charAt(0);
            mp.computeIfAbsent(ch, e -> new ArrayList<>()).add(new Word(word));
        }

        List<String> res = new ArrayList<>();

        for(char ch : text.toCharArray()){

            List<Word> list = mp.getOrDefault(ch, new ArrayList<>());
            mp.put(ch, new ArrayList<>());// clear this char from map

            for(Word word : list){
                word.index++;
                if(word.index == word.len){
                    res.add(word.word);
                }else{
                    char curr = word.word.charAt(word.index);
                    mp.computeIfAbsent(curr, e -> new ArrayList<>()).add(word);
                }
            }
        }
        return res;
    }
    //56. Given an array of start time for N processes that have the same duration of completion.
    // Given M CPUs, determine the minimum time when all processes will be completed.
//    public static void main(String[] args) {
//        List<Integer> start = new ArrayList<>(List.of(1, 2, 3, 4, 5));
//        int m = 2, t = 3;
//        System.out.println(solve(start, m, t));
//    }

    static int solve(List<Integer> startTimes, int m, int t){

        Collections.sort(startTimes);
        PriorityQueue<Integer> cpu = new PriorityQueue<>((a, b) -> a - b);
        for(int i=0;i<m;i++)
            cpu.add(0);

        int lastFinished = 0;
        for(int startTime : startTimes){
            int free = cpu.remove();
            int start = Math.max(startTime, free);
            int endTime = start + t;
            cpu.add(endTime);
            lastFinished = Math.max(lastFinished, endTime);
        }
        return lastFinished;
    }

    //57 Given two source A, B and one destination D in a unweighted graph.
    // Determine the number of minimum distinct edges that both A and B will cover to reach D
    public int minDistinctEdges(List<List<Integer>> graph, int A, int B, int D) {
        int n = graph.size();
        int[] distA = bfs(graph, A, n);
        int[] distB = bfs(graph, B, n);
        int[] distD = bfs(graph, D, n);

        int minEdges = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (distA[i] != -1 && distB[i] != -1 && distD[i] != -1) {
                minEdges = Math.min(minEdges, distA[i] + distB[i] + distD[i]);
            }
        }

        return minEdges;
    }

    private int[] bfs(List<List<Integer>> graph, int start, int n) {
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        dist[start] = 0;

        while (!q.isEmpty()) {
            int node = q.poll();
            for (int nei : graph.get(node)) {
                if (dist[nei] == -1) {
                    dist[nei] = dist[node] + 1;
                    q.add(nei);
                }
            }
        }

        return dist;
    }
    //58.  Longest Repeating Subsequence -> https://www.naukri.com/code360/problems/longest-repeating-subsequence_1118110?topList=top-google-coding-interview-questions&problemListRedirection=true&company%5B%5D=Google&leftPanelTabValue=PROBLEM
    public static int longestRepeatingSubsequence(String s, int n)
    {
        int[][] dp = new int[n+1][n+1];
        for(int[] cols : dp){
            Arrays.fill(cols, -1);
        }
        return solve(s, n, n, dp);
    }
    static int solve(String s, int i, int j, int[][] dp){
        if( i == 0 || j == 0)
            return 0;
        if(dp[i][j] != -1) return dp[i][j];
        int res = 0;
        if(s.charAt(i-1) == s.charAt(j-1) && i != j){
            res = 1 + solve(s, i-1, j-1, dp);
        }else
            res = Math.max(solve(s,i-1, j, dp), solve(s, i, j-1, dp));
        return dp[i][j] = res;
    }
    //59. 1074. Number of Submatrices That Sum to Target -> https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/description/
    public int numSubmatrixSumTarget(int[][] matrix, int target) { // we do
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] preSum = new int[n][m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int top = i > 0 ? preSum[i-1][j] : 0;
                int left = j>0 ? preSum[i][j-1] : 0;
                int topLeft = i>0 && j>0 ? preSum[i-1][j-1] : 0;
                preSum[i][j] = matrix[i][j] + left + top - topLeft;
            }
        }
        int count = 0;
        for(int r1 =0;r1 <n;r1++){
            for(int r2=r1;r2<n;r2++){

                Map<Integer, Integer> mp = new HashMap<>();
                mp.put(0, 1);

                for(int c=0;c<m;c++){
                    int prev = r1 > 0 ? preSum[r1-1][c] : 0;
                    int currSum = preSum[r2][c] - prev;
                    if(mp.containsKey(currSum - target)){
                        count += mp.get(currSum-target);
                    }
                    mp.put(currSum, mp.getOrDefault(currSum, 0) +1);
                }
            }
        }
        return count;
    }
    //60.  BST queries -> https://www.naukri.com/code360/problems/bst-queries_1095658?topList=top-google-coding-interview-questions&problemListRedirection=true&leftPanelTabValue=PROBLEM
    public static ArrayList<Integer> bstQueries(TreeNode root, int q, int[][] queries) {
        // Write your code here
        ArrayList<Integer> arr = new ArrayList<>();
        dfs(root, arr);
        ArrayList<Integer> res = new ArrayList<>();
        for(int[] query : queries){
            int l = query[0];
            int r = query[1];

            int index2 = findFloor(arr, r);
            int index1 = findCeil(arr, l);
            if(index1 <= index2)
                res.add(index2 - index1+1);
            else
                res.add(0);
        }
        return res;
    }

    static int findCeil(ArrayList<Integer> arr, int target){
        int low = 0, high = arr.size()-1;
        int res = high+1;

        while(low <= high){
            int mid = (low + high)/2;

            if(target <= arr.get(mid)){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return res;
    }

    static int findFloor(ArrayList<Integer> arr, int target){
        int low = 0;
        int high = arr.size()-1;
        int res = -1;
        while(low <= high){
            int mid = (low + high)/2;

            if(target >= arr.get(mid)){
                res = mid;
                low = mid+1;
            }else
                high = mid-1;
        }
        return res;
    }
    static void dfs(TreeNode root, ArrayList<Integer> arr){
        if(root == null){
            return;
        }

        dfs(root.left, arr);
        arr.add(root.data);
        dfs(root.right, arr);
    }
    //61. 1129. Shortest Path with Alternating Colors -> https://leetcode.com/problems/shortest-path-with-alternating-colors/description/
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] greenEdges) {
        List<List<Integer>> red = new ArrayList<>();
        List<List<Integer>> green = new ArrayList<>();
        for(int i=0;i<n;i++){
            red.add(new ArrayList<>());
        }
        for(int i=0;i<n;i++){
            green.add(new ArrayList<>());
        }
        for(int[] edge : redEdges){
            int u = edge[0];
            int v = edge[1];
            red.get(u).add(v);
        }
        for(int[] edge : greenEdges){
            int u = edge[0];
            int v = edge[1];
            green.get(u).add(v);
        }
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        int[][] vis = new int[n][2];
        Queue<int[]> q = new LinkedList<>();//node, steps, color

        q.add(new int[]{0, 0, -1});
        vis[0][0] = 1;
        vis[0][1] = 1;

        while(!q.isEmpty()){
            //red -> 1
            //green -> 0
            int node = q.peek()[0];
            int step = q.peek()[1];
            int color = q.remove()[2];

            if(dist[node] == -1)
                dist[node] = step;

            if(color != 1){
                for(int adj : red.get(node)){
                    if(vis[adj][1] == 0){
                        vis[adj][1] = 1;
                        q.add(new int[]{adj, step+1, 1});
                    }
                }
            }

            if(color != 0){
                for(int adj : green.get(node)){
                    if(vis[adj][0] == 0){
                        vis[adj][0] = 1;
                        q.add(new int[]{adj, step+1, 0});
                    }
                }
            }

        }
        return dist;

    }
    //62. All Unique Permutations -> https://www.naukri.com/code360/problems/all-unique-permutations_1094902?topList=top-google-coding-interview-questions&problemListRedirection=true&leftPanelTabValue=PROBLEM
    public static ArrayList<ArrayList< Integer >> uniquePermutations(ArrayList<Integer> arr, int n) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        solve(arr, 0, arr.size(), res);
        return new ArrayList<>(res);
    }
    static void solve(List<Integer> arr, int i, int n, ArrayList<ArrayList<Integer>> res){

        if(i == n){
            res.add(new ArrayList<>(arr));
            return;
        }
        Set<Integer> seen = new HashSet<>();
        for(int k=i;k<n;k++){
            if(seen.contains(arr.get(k)))
                continue;
            seen.add(arr.get(k));
            swap(arr, i, k);
            solve(arr, i+1, n, res);
            swap(arr, i, k);
        }
    }
    static void swap(List<Integer> arr, int i, int j){
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
    //63. Shortest Unique Prefix -> https://www.naukri.com/code360/problems/shortest-unique-prefix_1094887?topList=top-google-coding-interview-questions&problemListRedirection=true&leftPanelTabValue=PROBLEM
    public String[] shortestUniquePrefix(String[] s, int n) {
        // Write your code here
        String[] res = new String[n];

        Trie trie = new Trie();
        for(String str : s){
            trie.insert(str);
        }
        int i= 0;
        for(String str : s){
            res[i++] = trie.getPrefix(str);
        }
        return res;
    }
    //64. Maximum Points On Straight Line -> https://www.naukri.com/code360/problems/maximum-points-on-straight-line_1092972?topList=top-google-coding-interview-questions&problemListRedirection=true&leftPanelTabValue=PROBLEM
    public static int maxPointsOnLine(int[][] points, int n) {

        if (n <= 2) return n;

        int result = 0;

        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopes = new HashMap<>();
            int samePointCount = 0;
            int localMax = 0;


            for (int j = i + 1; j < n; j++) {

                if (points[i][0] == points[j][0] && points[i][1] == points[j][1]) {
                    samePointCount++;
                    continue;
                }

                int deltaY = points[j][1] - points[i][1];
                int deltaX = points[j][0] - points[i][0];
                int gcd = findGCD(deltaY, deltaX);

                deltaY /= gcd;
                deltaX /= gcd;


                String slope = deltaY + "/" + deltaX;
                slopes.put(slope, slopes.getOrDefault(slope, 0) + 1);
                localMax = Math.max(localMax, slopes.get(slope));
            }

            result = Math.max(result, localMax + samePointCount + 1);
        }

        return result;
    }
    private static int findGCD(int a, int b) {
        if (b == 0) return a;
        return findGCD(b, a % b);
    }

    //65. Single Number II -> All number 3 time one number 1 time
    public static int elementThatAppearsOnce(int[] arr) {
        // one can have a value ,it it is not in two
        //two can have a value , if it is not in one
        //three can have a value , if it not in one and two
        int one = 0, two = 0, three = 0;
        for(int num : arr){

            one = (one ^ num) & ~two;
            two = (two ^ num) & ~one;
            three = ((three ^ num) & ~two) & ~one;
        }
        return one;
    }
    //66. Four Keys Keyboard -> https://www.naukri.com/code360/problems/four-keys-keyboard_1092346?topList=top-google-coding-interview-questions&problemListRedirection=true&company%5B%5D=Google&leftPanelTabValue=PROBLEM
    static Map<Integer, Long> mp = new HashMap<>(); //need to understand
    static long findMaxAs(int n) {
        if(n <=6)
            return n;
        if(mp.containsKey(n))
            return mp.get(n);
        long res = 0;

        for(int i = n-3;i>=1;i--){
            long val = findMaxAs(i) * (n-i-1);
            res = Math.max(res, val);
        }
        mp.put(n, res);
        return res;
    }
    //67. two keys keyboard -> https://leetcode.com/problems/2-keys-keyboard/description/
    int size;
    Map<String, Integer> mp1;
    public int minSteps(int n) {
        if(n==1)
            return 0;
        size = n;
        mp = new HashMap<>();
        return 1 + solve1(1, 1);
    }

    int solve1(int cnt, int copy){
        if( cnt > size)
            return (int) 1e9;
        if(cnt == size){
            return 0;
        }
        String key = cnt + "," + copy;
        if(mp1.containsKey(key))
            return mp1.get(key);

        int left = 1 + solve1(cnt + copy, copy);
        int right = 2 + solve1(cnt * 2, cnt);
        mp1.put(key, Math.min(left, right));
        return mp1.get(key);
    }
    //68. 847. Shortest Path Visiting All Nodes -> https://leetcode.com/problems/shortest-path-visiting-all-nodes/description/
    public int shortestPathLength(int[][] graph) {
        List<List<Integer>> adj = new ArrayList<>();
        int V = graph.length;
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }


        for(int i=0;i<graph.length;i++){

            for(int j=0;j<graph[i].length;j++){
                adj.get(i).add(graph[i][j]);

            }
        }
        Queue<int[]> q = new LinkedList<>();// node, mask, count
        Set<String> vis = new HashSet<>();
        for(int i=0;i<V;i++){
            q.add(new int[]{i, 1 << i, 0});
            vis.add(i + "," +( 1 << i));
        }

        while(!q.isEmpty()){

            int[] curr = q.remove();
            int node = curr[0];
            int mask = curr[1];
            int count = curr[2];

            if(mask == (1 << V) -1)
                return count;

            for(int adjNode : adj.get(node)){

                int currMask = mask | ( 1 << adjNode);
                if(!vis.contains(adjNode +"," + currMask)){
                    q.add(new int[]{adjNode, currMask, count+1});
                    vis.add(adjNode +"," + currMask);
                }
            }
        }
        return -1;
    }
    //69. Journey to the Moon -> https://www.hackerrank.com/challenges/journey-to-the-moon/problem
    public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)
            adj.add(new ArrayList<>());
        for(var list: astronaut){
            int u = list.get(0);
            int v = list.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] vis = new int[n];

        long cnt = 0, res = 0;
        for(int i=0;i<n;i++){
            if(vis[i] == 1)
                continue;
            long val = dfs(i, adj, vis);
            res += cnt * val;
            cnt += val;
        }
        return  res;

    }
    static long dfs(int node, List<List<Integer>> adj, int[] vis){
        vis[node] = 1;
        long cnt = 0;
        for(int adjNode : adj.get(node)){
            if(vis[adjNode] == 0){
                cnt += dfs(adjNode, adj, vis);
            }
        }
        return cnt+1;
    }
    //70. Given an array of non-negative integers, the goal is to travel from the first
    // index to the last index with maximum possible score with as many jumps allowed.
    // Score of a jump is defined as the number of index jumped multiplied by the value on the jumped index.
    //e.g. [3,7,9,10]
    //
    //if the jump is from index0 to index2, the score is (2-0)*9 = 18
    //
    //Sample input: [3,12,9,10]
    //Sample output: 32
    static int solve(int[] arr, int i, int n, int[] dp){
        if( i == n -1)
            return 0;
        if(dp[i] != -1)
            return dp[i];
        int res = 0;
        for(int k=i+1;k<n;k++){
            int val = (k-i) * arr[k] + solve(arr, k, n, dp);
            res = Math.max(res, val);
        }
        return dp[i] = res;
    }
    //---------------------OR-----------
    public static int getMaxScore1(int[] nums) {
        int result = 0;
        int maxSeenSoFar = Integer.MIN_VALUE;
        // First value doesn't matter hence condition > 0
        for (int i = nums.length - 1; i > 0; i--) {
            maxSeenSoFar = Math.max(maxSeenSoFar, nums[i]);
            result += maxSeenSoFar;
        }
        return result;
    }
    //71.The tournament is knockout format. Which means if we have 8 players [a b c d e f g h] with their
    // ranks [1 2 3 4 5 6 7 8], the tournament will look like this:
    //1st round: [a b] [c d] [e f] [g h]
    //2nd round: [a c] [e g]
    //3rd round: [a e]
    //champion : [a]
    //
    //We are calling [a b c d e f g h] or [1 2 3 4 5 6 7 8] a "draw" where in the 1st round:
    // first two players meet in the first match, the next two players meet in the second match and so on.
    // Problem 1:
    //Given a draw, find out whether it is a valid draw.
    //
    //Round 1: [1,8,6,2,7,3,4,5] -> valid
    //Round 2: [1,2,3,4] -> invalid
    //
    //Round 1: [1,8,4,5,3,6,2,7] -> valid
    //Round 2: [1,4,3,2] -> valid
    //Round 3: [1,2] -> valid
    private static boolean solve(int[] arr, int n) {
        if(arr.length % 2 != 0)
            return false;
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<n;i++)
            q.add(arr[i]);
        int cnt = 1;
        while(q.size() > 1){
            int size = q.size();
            cnt++;
            for(int i=0;i<size;i+=2){

                int first = q.poll();
                int second = q.poll();
                if (first + second != n + 1) {
                    System.out.println("Failed at " + cnt + " level");
                    return false;
                }
                q.add(Math.min(first, second));
            }
            n/=2;
        }
        System.out.println("winner is " + q.peek());
        return true;
    }

    //72.
    //Given below pattern of license plates (Pattern only, not the actual list of license plates),
    // Find the nth license plate
    //All license plates no are of size 5 chars
    //Eg, if n is 3, ans is - 00002
    //
    //    00000
    //    00001
    //    99999
    //    0000A
    //    0001A
    //........
    //9999Z
    //000AA
    //001AA
    //........
    //ZZZZZ
//    public static void main(String[] args) {
//        System.out.println(makeNumberPlate(11_881_376));//zzzzz
//    }
    public static void main(String[] args) {
        System.out.println(getNthID(26*26*26*26*26-1, 5));
    }
    private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String getNthID(long n, int maxLength) {
        long index = n;

        for (int letterCount = 0; letterCount <= maxLength; letterCount++) {
            int digitCount = maxLength - letterCount;

            long blockSize = (long) Math.pow(10, digitCount) * (long) Math.pow(26, letterCount);

            if (index < blockSize) {
                // Found the block where the index belongs
                int digits = (int) index / (int) Math.pow(26, letterCount);
                int letters = (int) index % (int) Math.pow(26, letterCount);

                String digitPart = getDigitPart(digitCount, digits);
                String letterPart = getLetterPart(letterCount, letters);

                return digitPart + letterPart;
            }
        }

        return "OUT_OF_RANGE";
    }
    private static String getDigitPart(int length, int val){
        if(length == 0)
            return "";
        String s = String.valueOf(val);
        length = length - s.length();

        return "0".repeat(length) + s;
    }

    private static String getLetterPart(int length, int val) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.insert(0, LETTERS[val % 26]);
            val /= 26;
        }
        return sb.toString();
    }

}
