import java.util.*;
import java.lang.String;

public class Greedy {
    public static void main(String[] args) {

    }
    //1. Assign Cookies
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(s);
        Arrays.sort(g);
        int n = g.length;
        int m = s.length;
        int i=0, j=0, cnt = 0;
        while(i<n && j <m){
            if(g[i] <= s[j]){
                i++;
                j++;
                cnt++;
            }else
                j++;
        }
        return cnt;
    }
    //2. Fractional Knapsack Problem
    double fractionalKnapsack(List<Integer> val, List<Integer> wt, int capacity) {
        int n = val.size();
        List<int[]> valWt = new ArrayList<>();
        for(int i=0;i<n;i++){
            int[] currVal = new int[]{val.get(i), wt.get(i)};
            valWt.add(currVal);
        }

        valWt.sort((a, b) -> {
            double d1 = 1D * a[0] / a[1];
            double d2 = 1D * b[0] / b[1];
            return Double.compare(d2, d1);
        });
        double res = 0;
        for(int i=0;i<n;i++){

            if(capacity >= valWt.get(i)[1]){
                res += valWt.get(i)[0];
                capacity -= valWt.get(i)[1];
            }else{
                res +=  1D*capacity * valWt.get(i)[0]/valWt.get(i)[1];
                capacity = 0;
            }
        }
        return res;
    }
    //3. Greedy algorithm to find minimum number of coins -> coins should be properly distributed (means previous coin should be half or less than half of next coin)
    static List<Integer> minPartition(int N){
        List<Integer> coins = List.of(2000,500,200,100,50,20,10,5,2,1);
        List<Integer> res= new ArrayList<>();

        for(int i=0;i<coins.size();i++){
            if(N >= coins.get(i)){
                int val = coins.get(i);
                int cnt = N/val;
                N %= val;
                while(cnt > 0){
                    res.add(val);
                    cnt--;
                }
            }
        }

        return res;
    }
    //4. Lemonade Change
    public boolean lemonadeChange(int[] bills) {
        int coin5 = 0, coin10 = 0, coin20 = 0;

        for (int currCoin : bills) {
            if (currCoin == 5) {
                coin5++;
            } else if (currCoin == 10) {
                if (coin5 > 0) {
                    coin5--;
                } else
                    return false;
                coin10++;
            } else {
                if (coin10 >= 1 && coin5 >= 1) {
                    coin10--;
                    coin5--;
                } else if (coin5 >= 3) {
                    coin5 -= 3;
                } else
                    return false;
                coin20++;
            }
        }
        return true;
    }
    //5. Valid Paranthesis Checker
    public boolean checkValidString1(String s) {
        int n = s.length();
        int[][] dp = new int[n][n+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve(s, 0, 0, dp);
    }
    boolean solve(String s, int i, int cnt, int[][] dp){
        if(i == s.length() || cnt < 0){
            return cnt == 0;
        }
        if(dp[i][cnt] != -1) return dp[i][cnt] == 1;
        boolean res = false;
        if(s.charAt(i) == '('){
            res = solve(s, i+1, cnt+1, dp);
        }else if(s.charAt(i) == ')'){
            res = solve(s, i+1, cnt-1, dp);
        }else{
            res = solve(s, i+1, cnt+1, dp) || solve(s, i+1, cnt-1, dp) || solve(s, i+1, cnt, dp);
        }
        dp[i][cnt] = res ? 1: 0;
        return res;
    }
    //---------------------OR------------------------------
    public boolean checkValidString2(String s) {
        int n = s.length();
        int min = 0, max = 0;
        for(int i=0;i<n;i++){
            if(s.charAt(i) == '('){
                min++;
                max++;
            }else if(s.charAt(i) == ')'){
                min--;
                max--;
            }else{
                min--;
                max++;
            }

            if(min < 0 && max < 0)
                return false;
            if(min < 0)
                min = 0;
        }

        return min == 0 || max == 0;
    }
    //6. N meetings in one room
    public int maxMeetings(int start[], int end[]) {
        // add your code here
        int n = start.length;
        List<int[]> list = new ArrayList<>();
        for(int i=0;i<n;i++){
            list.add(new int[]{start[i], end[i]});
        }

        list.sort((a, b) -> {
            if(a[1] != b[1])
                return a[1] - b[1];
            return a[0] - b[0];
        });

        int count = 0;
        int prev = -1;
        for(int i=0;i<n;i++){
            if(list.get(i)[0] > prev){
                count++;
                prev = list.get(i)[1];
            }
        }
        return count;
    }
    //7. Jump Game
    public boolean canJump(int[] nums) {
        int maxIndex =0;
        for(int i=0;i<nums.length;i++){

            if(maxIndex < i)
                return false;

            maxIndex = Math.max(maxIndex, i + nums[i]);
        }
        return true;
    }
    //8. Jump Game 2
    public int jump1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(nums, 0, n, dp);
    }
    int solve(int[] nums, int i, int n, int[] dp){
        if(i >= n-1)
            return 0;
        if(dp[i] != -1) return dp[i];
        int res = (int) 1e9;
        for(int j=i;j<=Math.min(n, i+nums[i])-1;j++){
            int val = 1 + solve(nums, j+1, n, dp);
            res = Math.min(res, val);
        }
        return dp[i] = res;
    }
    //---------------------OR------------------------------
    public int jump2(int[] nums) {
        int n = nums.length;
        int min = 0, max = 0;
        int cnt = 0;
        while(max < n-1){
            int fartest = 0;
            for(int i = min;i<=max;i++){
                fartest = Math.max(fartest, min + nums[i]);
            }
            min = max+1;
            max = fartest;
            cnt ++;
        }
        return cnt;
    }
    //9. Minimum number of platforms required for a railway
    int findPlatform(int arr[], int dep[]) {
        int n = arr.length;
        Arrays.sort(arr);
        Arrays.sort(dep);
        int i=0, j=0, cnt=0, res =0;
        while(i < n){
            if(arr[i] <= dep[j]){
                cnt++;
                i++;
            }else{
                cnt--;
                j++;
            }
            res = Math.max(res, cnt);
        }
        return res;
    }
    //10. Job Sequencing Problem
    public ArrayList<Integer> JobSequencing(int[] id, int[] deadline, int[] profit) {
        int n = id.length;
        Set<Integer> hs = new HashSet<>();
        List<int[]> job = new ArrayList<>();

        for(int i=0;i<n;i++){
            job.add(new int[]{id[i], deadline[i], profit[i]});
        }

        job.sort((a, b) -> b[2] - a[2]);

        int cnt = 0 , totalProfit = 0;

        for(int i=0;i<n;i++){

            int currDeadline = job.get(i)[1];
            int currProfit = job.get(i)[2];

            while(hs.contains(currDeadline) && currDeadline > 0){
                currDeadline--;
            }
            if(currDeadline > 0){
                hs.add(currDeadline);
                totalProfit +=currProfit;
                cnt++;
            }
        }

        return new ArrayList<>(List.of(cnt, totalProfit));
    }
    //11. candy -> https://leetcode.com/problems/candy/
    public int candy1(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        left[0] = 1;
        for(int i=1;i<n;i++){
            if(ratings[i-1] < ratings[i]){
                left[i] = left[i-1]+1;
            }else{
                left[i] = 1;
            }
        }

        int[] right = new int[n];
        right[n-1] = 1;
        for(int i=n-2;i>=0;i--){
            if(ratings[i] > ratings[i+1]){
                right[i] = right[i+1]+1;
            }else
                right[i] = 1;
        }
        int res = 0;
        for(int i=0;i<n;i++){
            res += Math.max(left[i], right[i]);
        }
        return res;
    }
    //---------------------OR------------------------------
    public int candy2(int[] ratings) {
        int n = ratings.length;
        int sum = 1;
        int i=1;

        while(i<n){

            if(ratings[i-1] == ratings[i]){
                sum += 1;
                i +=1;
                continue;
            }
            int peak = 1;
            while(i<n && ratings[i-1] < ratings[i]){
                peak +=1;
                sum +=peak;
                i += 1;
            }
            int down = 1;
            while(i < n && ratings[i-1] > ratings[i]){
                sum +=down;
                down +=1;
                i += 1;
            }

            if(down > peak){
                sum += down-peak;
            }
        }
        return sum;
    }
    //12. Program for Shortest Job First (or SJF) CPU Scheduling
    static int solve(int bt[] ) {
        Arrays.sort(bt);
        int n = bt.length;
        int sum = 0;
        int res = 0;
        for(int i=0;i<n-1;i++){
            sum += bt[i];
            res += sum;
        }
        res = (int) Math.floor(1D*res/n);
        return res;
    }
    //13. LRU Cache
    class LRUCache {
        class DLL{
            int val, key;
            DLL prev, next;
            DLL(int val){
                this.val = val;
            }
        }
        int size;
        int currSize;
        DLL start, end;
        Map<Integer, DLL> mp;
        public LRUCache(int capacity) {
            size = capacity;
            start= new DLL(-1);
            end =new DLL(-1);
            start.next = end;
            end.prev = start;
            mp = new HashMap<>();
            currSize = 0;
        }

        public int get(int key) {
            if(mp.containsKey(key)){
                put(key, mp.get(key).val);
                return mp.get(key).val;
            }else
                return -1;
        }

        public void put(int key, int value) {
            DLL node;
            if(mp.containsKey(key)){
                node = mp.get(key);
                //removing from prev location
                DLL prev = node.prev;
                DLL next = node.next;
                prev.next = next;
                next.prev = prev;
                //adding node to the front
                node.next = start.next;
                start.next = node;
                node.prev = start;
                node.next.prev = node;
                //updating value;
                node.val = value;
            }else{
                node = new DLL(value);
                node.key = key;
                mp.put(key, node);

                //puttin it on front
                node.next = start.next;
                start.next = node;
                node.prev = start;
                node.next.prev = node;
                currSize ++;
            }

            if(currSize > size){
                node = end.prev;
                DLL prev = node.prev;
                prev.next = end;
                end.prev = prev;
                mp.remove(node.key);
                currSize--;
            }
        }
    }
    //14. Insert Interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        List<int[]> list = new ArrayList<>();

        int i=0;
        while(i < n && intervals[i][1] < newInterval[0]){
            list.add(new int[]{intervals[i][0], intervals[i][1]});
            i++;
        }

        while(i<n && intervals[i][0] <= newInterval[1]){
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }
        list.add(new int[]{newInterval[0], newInterval[1]});

        while(i<n){
            list.add(new int[]{intervals[i][0], intervals[i][1]});
            i++;
        }
        int[][] ans = new int[list.size()][2];
        for(i=0;i<list.size();i++){
            ans[i] = list.get(i);
        }
        return ans;
    }
    //15. Merge Intervals
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int start = intervals[0][0];
        int end = intervals[0][1];
        List<int[]> res = new ArrayList<>();

        for(int i=0;i<intervals.length;i++){

            if(intervals[i][0] <= end){
                end = Math.max(end, intervals[i][1]);
            }else{
                res.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        res.add(new int[]{start, end});
        int[][] ans = new int[res.size()][2];
        for(int i=0;i<res.size();i++){
            ans[i] = res.get(i);
        }
        return ans;
    }
    //16. Non-overlapping Intervals
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b)-> a[1] - b[1]);

        int cnt = 0;
        int end = intervals[0][1];
        for(int i=1;i<intervals.length;i++){
            if(intervals[i][0] < end)
                cnt++;
            else
                end = Math.max(end, intervals[i][1]);
        }
        return cnt;
    }
}
