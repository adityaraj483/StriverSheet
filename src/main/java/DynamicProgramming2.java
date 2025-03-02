import java.util.*;
import java.lang.String;

public class DynamicProgramming2 {
    public static void main(String[] args) {

    }
    //34. Best Time to Buy and Sell Stock |(DP-35)
    public int maxProfit(int[] prices) {
        int maxProfit =0;
        int curr = prices[0];
        for(int i=1;i<prices.length;i++){
            curr = Math.min(curr, prices[i]);
            maxProfit = Math.max(prices[i] - curr, maxProfit);
        }
        return maxProfit;
    }
    //35. Best Time to Buy and Sell Stock II |(DP-36)
    public int maxProfit21(int[] prices) {
        int maxProfit = 0, minPrice = Integer.MAX_VALUE;

        for(int i=0;i<prices.length;i++){
            minPrice = Math.min(minPrice, prices[i]);

            if(minPrice < prices[i]){
                maxProfit += prices[i] - minPrice;
                minPrice = prices[i];
            }
        }
        return maxProfit;
    }
    //36. Best Time to Buy and Sell Stock III |(DP-37)
    public int maxProfit31(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][4];
         for(int[]a : dp)
             Arrays.fill(a, -1);
         return solve(prices, 0, n, 0, dp);
    }
    int solve(int[] prices, int index, int n, int count, int[][] dp){
        if(index == n || count == 4)
            return 0;
        if(dp[index][count] != -1) return dp[index][count];
        if(count % 2 == 0){
            int buy = -prices[index] + solve(prices, index+1, n, count+1, dp);
            int notBuy = 0 + solve(prices, index+1, n, count, dp);
            return dp[index][count] = Math.max(buy, notBuy);
        }else{
            int sell = prices[index] + solve(prices, index+1, n, count+1, dp);
            int notSell = 0 + solve(prices, index+1, n, count, dp);
            return dp[index][count] = Math.max(sell, notSell);
        }
    }
    //-------------------OR-------------------
    public int maxProfit32(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][5];

        for(int i=n-1;i>=0;i--){
            for(int j = 3;j>=0;j--){
                int res = 0;
                if(j % 2 == 0){
                    int buy = -prices[i] + dp[i+1][j+1];
                    int notBuy = dp[i+1][j];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] + dp[i+1][j+1];
                    int notSell = 0 + dp[i+1][j];
                    res = Math.max(sell,notSell);
                }
                dp[i][j] = res;
            }
        }
        return dp[0][0];
    }
    //-------------------OR-------------------
    public int maxProfit33(int[] prices) {
        int n = prices.length;
        int[] next = new int[5];

        for(int i=n-1;i>=0;i--){
            for(int j = 3;j>=0;j--){
                int res = 0;
                if(j % 2 == 0){
                    int buy = -prices[i] + next[j+1];
                    int notBuy = next[j];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] + next[j+1];
                    int notSell = 0 + next[j];
                    res = Math.max(sell,notSell);
                }
                next[j] = res;
            }
        }
        return next[0];
    }
    //37. Buy and Stock Sell IV |(DP-38)
    public int maxProfit41(int k, int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2*k];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return findMax(prices, 0, 0, 2*k, dp);
    }
    int findMax(int[] prices, int index, int cnt, int k, int[][] dp){
        if(index == prices.length || cnt == k){
            return 0;
        }
        if(dp[index][cnt] != -1) return dp[index][cnt];
        int res = 0;
        if(cnt % 2 == 0){
            int buy = -prices[index] + findMax(prices, index+1, cnt+1, k, dp);
            int notBuy = 0 + findMax(prices, index+1, cnt, k, dp);
            res = Math.max(buy, notBuy);
        }else{
            int sell = prices[index] + findMax(prices, index+1, cnt+1, k, dp);
            int notSell = 0 + findMax(prices, index+1, cnt, k, dp);
            res = Math.max(sell, notSell);
        }
        return dp[index][cnt] = res;
    }
    //-------------------OR-------------------
    public int maxProfit42(int k, int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2*k+1];

        for(int i= n-1;i>=0;i--){
            for(int j=2*k-1;j>=0;j--){

                int res = 0;
                if(j % 2 ==0){
                    int buy = -prices[i] + dp[i+1][j+1];
                    int notBuy = 0 + dp[i+1][j];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] + dp[i+1][j+1];
                    int notSell = dp[i+1][j];
                    res = Math.max(sell, notSell);
                }
                dp[i][j] = res;
            }
        }
        return dp[0][0];
    }
    //-------------------OR-------------------
    public int maxProfit43(int k, int[] prices) {
        int n = prices.length;
        int[] next = new int[2*k+1];

        for(int i= n-1;i>=0;i--){
            for(int j=2*k-1;j>=0;j--){

                int res = 0;
                if(j % 2 ==0){
                    int buy = -prices[i] + next[j+1];
                    int notBuy = 0 + next[j];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] + next[j+1];
                    int notSell = next[j];
                    res = Math.max(sell, notSell);
                }
                next[j] = res;
            }
        }
        return next[0];
    }
    //38. Buy and Sell Stocks With Cooldown|(DP-39)
    public int maxProfit51(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+1][2+1];

        for(int[] b : dp)
            Arrays.fill(b, -1);

        return solve(prices, 0, 1, dp);
    }
    int solve(int[] prices, int index, int canBuy, int[][] dp){
        if(index >= prices.length)
            return 0;

        if(dp[index][canBuy] != -1)
            return dp[index][canBuy];

        int res = 0;
        if(canBuy == 1){
            int buy = -prices[index] + solve(prices, index+1, 0, dp);
            int notBuy = 0 + solve(prices, index+1, 1, dp);
            res = Math.max(buy, notBuy);
        }else{
            int sell = prices[index] + solve(prices, index+2, 1, dp);
            int notSell = 0 + solve(prices, index+1, 0, dp);
            res = Math.max(sell, notSell);
        }
        return dp[index][canBuy] = res;
    }
    //-------------------OR-------------------
    public int maxProfit52(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n+2][2+1];

        for(int i=n-1;i>=0;i--){
            for(int j=1;j>=0;j--){
                int res = 0;
                if(j == 0){
                    int buy = -prices[i] + dp[i+1][1];
                    int notBuy = 0 + dp[i+1][0];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] + dp[i+2][0];
                    int notSell = 0 + dp[i+1][1];
                    res = Math.max(sell, notSell);
                }
                dp[i][j] = res;
            }
        }
        return dp[0][0];
    }
    //39. Buy and Sell Stocks With Transaction Fee|(DP-40)
    public int maxProfit61(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
         for(int[] a: dp)
             Arrays.fill(a, -1);
         return solve6(prices, 0, fee, 1, dp);

    }
    int solve6(int[] prices, int index, int fee, int canBuy, int[][] dp){
        if( index == prices.length)
            return 0;
        int res = 0;
        if(dp[index][canBuy] != -1) return dp[index][canBuy];
        if(canBuy == 1){
            int buy = -prices[index] + solve6(prices, index+1, fee, 0, dp);
            int notBuy = 0 + solve6(prices, index+1, fee, 1,dp);
            res = Math.max(buy, notBuy);
        }else{
            int sell = prices[index] - fee + solve6(prices, index+1, fee, 1, dp);
            int notSell = 0 + solve6(prices, index+1, fee, 0, dp);
            res = Math.max(sell, notSell);
        }
        return dp[index][canBuy] = res;
    }
    //-------------------OR-------------------
    public int maxProfit62(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n+1][2];
        for(int i=n-1;i>=0;i--){
            for(int j=0;j<2;j++){
                int res = 0;
                if(j == 1){
                    int buy = -prices[i] + dp[i+1][0];
                    int notBuy = 0 + dp[i+1][1];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] - fee + dp[i+1][1];
                    int notSell = 0 + dp[i+1][0];
                    res = Math.max(sell, notSell);
                }
                dp[i][j] = res;
            }
        }
        return dp[0][1];
    }
    //-------------------OR------------------------------------
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[] next = new int[2];
        int[] curr = new int[2];
        for(int i=n-1;i>=0;i--){
            for(int j=0;j<2;j++){
                int res = 0;
                if(j == 1){
                    int buy = -prices[i] + next[0];
                    int notBuy = 0 + next[1];
                    res = Math.max(buy, notBuy);
                }else{
                    int sell = prices[i] - fee + next[1];
                    int notSell = 0 + next[0];
                    res = Math.max(sell, notSell);
                }
                curr[j] = res;
            }
            next = Arrays.copyOf(curr,2);
        }
        return next[1];
    }
    //40. Longest Increasing Subsequence |(DP-41)
    public int lengthOfLIS1(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve6(nums, 0, -1, dp);
    }
    int solve6(int[] nums, int index, int prevIndex, int[][] dp){
        if(nums.length == index)
            return 0;
        if(dp[index][prevIndex+1] != -1) return dp[index][prevIndex+1];

        int notTake = 0 + solve6(nums, index+1, prevIndex, dp);
        int take = 0;
        if(prevIndex == -1 || nums[prevIndex] < nums[index]){
            take = 1 + solve6(nums, index+1, index, dp);
        }
        return dp[index][prevIndex+1] = Math.max(take,notTake);
    }
    //-------------------OR-------------------
    public int lengthOfLIS2(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];

        for(int i=n-1;i>=0;i--){
            for(int j = i-1;j>=-1;j--){
                int notTake = dp[i+1][j+1];
                int take = 0;
                if(j == -1 || nums[j] < nums[i])
                    take = 1 + dp[i+1][i+1];
                dp[i][j+1] = Math.max(take, notTake);
            }
        }
        return dp[0][-1 + 1];
    }
    //-------------------OR-------------------
    public int lengthOfLIS3(int[] nums) {
        int n = nums.length;
        int[] next= new int[n+1];
        int[] curr = new int[n+1];

        for(int i=n-1;i>=0;i--){
            for(int j = i-1;j>=-1;j--){
                int notTake = next[j+1];
                int take = 0;
                if(j == -1 || nums[j] < nums[i])
                    take = 1 + next[i+1];
                curr[j+1] = Math.max(take, notTake);
            }
            next = Arrays.copyOf(curr, n+1);
        }
        return next[-1 + 1];
    }
    //-------------------OR-------------------
    public int lengthOfLIS4(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;
        for(int i=0;i<n;i++){
            for(int prev = 0; prev <i;prev++){
                if(nums[prev] < nums[i])
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
    //41. print Longest Increasing Subsequence |(DP-42)
    public ArrayList<Integer> longestIncreasingSubsequence1(int n, int arr[]) {
        // Code here
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList<Integer> ds = new ArrayList<>();
        solve(arr, 0, n, ds, res, -1);
        return res;
    }
    void solve(int[] arr, int i, int n , ArrayList<Integer> ds, ArrayList<Integer> res, int prev){
        if(i == n){
            if(ds.size() > res.size()){
                res.clear();
                res.addAll(ds);
            }
            return;
        }
        if(prev == -1 || arr[prev] < arr[i]){
            ds.add(arr[i]);
            solve(arr, i+1, n, ds, res, i);
            ds.remove(ds.size()-1);
        }
        solve(arr, i+1, n, ds, res, prev);
    }
    //-------------------OR-------------------
    public ArrayList<Integer> longestIncreasingSubsequence2(int n, int arr[]) {
        int[] dp = new int[n];
        int[] hs = new int[n];
        Arrays.fill(dp, 1);
        Arrays.fill(hs, -1);
        int index = 0;
        int max = 0;

        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(arr[j] < arr[i] && dp[i] < 1 + dp[j]){
                    dp[i] = 1 + dp[j];
                    hs[i] = j;
                }
            }
            if(dp[i] > max){
                max = dp[i];
                index = i;
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        while(index >= 0){
            res.add(arr[index]);
            index = hs[index];
        }
        Collections.reverse(res);
        return res;
    }
    //42. Longest Increasing Subsequence |(DP-43)
    int lis(int arr[]) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int max = 1;
        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(arr[j] < arr[i]){
                    dp[i] = Math.max(dp[i], 1 + dp[j]);

                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
    //43. Largest Divisible Subset|(DP-44)
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] hash = new int[n];

        Arrays.sort(nums);
        Arrays.fill(dp, 1);
        Arrays.fill(hash, -1);
        int maxLen = 0, lastIndex = -1;
        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[i] % nums[j] == 0  && dp[i] < 1 + dp[j]){
                    dp[i] = 1 + dp[j];
                    hash[i] = j;
                }
            }
            if(maxLen < dp[i]){
                maxLen = dp[i];
                lastIndex = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while(lastIndex >= 0){
            res.add(nums[lastIndex]);
            lastIndex = hash[lastIndex];
        }
        Collections.reverse(res);
        return res;
    }
    //44. Longest String Chain|(DP-45)
    public int longestStrChain(String[] words) {
        int n = words.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int res = 1;
        Arrays.sort(words, (a, b) -> a.length() - b.length());

        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){

                if(isValid(words[j], words[i])){
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }
    boolean isValid(String a, String b){
        if(a.length() + 1 != b.length())
            return false;

        int first = 0, second = 0;
        while(second < b.length()){
            if(first < a.length() && a.charAt(first) == b.charAt(second)){
                first++;
                second++;
            }else{
                second++;
            }
        }
        return first == a.length() && second == b.length();
    }
    //45. Longest Bitonic Subsequence |(DP-46)
    public static int LongestBitonicSequence(int n, int[] nums) {
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, 1);
        Arrays.fill(dp2, 1);

        for(int i=0;i<n;i++){
            for(int prev=0;prev<i;prev++){
                if(nums[prev]< nums[i]){
                    dp1[i] = Math.max(dp1[i], 1 + dp1[prev]);
                }
            }
        }

        for(int i=n-1;i>=0;i--){
            for(int prev=n-1;prev>i;prev--){
                if(nums[prev] < nums[i]){
                    dp2[i] = Math.max(dp2[i], 1 + dp2[prev]);
                }
            }
        }

        int maxi = 0;
        for(int i=1;i<n-1;i++){
            if(dp1[i] == 1 || dp2[i] == 1) // so that single element is not considered
                continue;

            maxi = Math.max(maxi, dp1[i]+dp2[i]-1);
        }
        return maxi;
    }
    //46. Number of Longest Increasing Subsequences|(DP-47)
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];

        Arrays.fill(count, 1);
        Arrays.fill(dp, 1);
        int maxLen =1;

        for(int i=0;i<n;i++){
            for(int j=0;j<i;j++){
                if(nums[j] < nums[i]){

                    if(dp[j] + 1 > dp[i]){
                        dp[i] = 1 + dp[j];
                        count[i] = count[j];
                    }else if(dp[j]+1 == dp[i]){
                        count[i] += count[j];
                    }
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        int res = 0;
        for(int i=0;i<n;i++){
            if(dp[i] == maxLen)
                res += count[i];
        }
        return res;
    }
    //47. Matrix Chain Multiplication|(DP-48)
    int matrixMultiplication1(int arr[]) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        for(int[] a: dp)
            Arrays.fill(a, -1);

        return solve4(arr, 1, n-1, dp);
    }
    int solve4(int[] arr, int i, int j, int[][] dp){
        if( i == j) return 0;

        if(dp[i][j] != -1) return dp[i][j];
        int res = (int) 1e9;
        for(int k=i;k<j;k++){

            int mm = arr[i-1] * arr[k] * arr[j] + solve4(arr, i, k, dp) + solve4(arr, k+1, j, dp);

            res = Math.min(mm, res);
        }
        return dp[i][j] = res;
    }
    //49. Matrix Chain Multiplication|(DP-49)
    int matrixMultiplication2(int arr[]) {
        int n = arr.length;
        int[][] dp = new int[n][n];

        for(int i=n-1;i>=1;i--){
            for(int j=i+1;j<n;j++){

                int res = (int) 1e9;

                for(int k =i;k<j;k++){

                    int mm = arr[i-1] * arr[k] * arr[j] + dp[i][k] + dp[k+1][j];
                    res = Math.min(mm, res);
                }
                dp[i][j] = res;
            }
        }
        return dp[1][n-1];
    }
    //50. Minimum Cost to Cut the Stick|(DP-50)
    public int minCost1(int n, int[] cuts) {
        List<Integer> cut = new ArrayList<>();
        int c = cuts.length;
        for(int i=0;i<c;i++){
            cut.add(cuts[i]);
        }
        cut.add(0);
        cut.add(n);
        Collections.sort(cut);

        int[][] dp = new int[c+1][c+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);

        return solve(cut, 1, c, dp);
    }
    int solve(List<Integer> cut, int i, int j, int[][] dp){
        if(j < i)
            return 0;
        if(dp[i][j] != -1)
            return dp[i][j];
        int res = (int) 1e9;
        for(int k =i;k<=j;k++){
            int mm = cut.get(j+1) - cut.get(i-1) + solve(cut, i, k-1, dp) + solve(cut, k+1, j, dp);
            res = Math.min(res, mm);
        }
        return dp[i][j] = res;
    }
    //-------------------OR-------------------
    public int minCost2(int n, int[] cuts) {
        List<Integer> cut = new ArrayList<>();
        int c = cuts.length;
        for(int i=0;i<c;i++){
            cut.add(cuts[i]);
        }
        cut.add(0);
        cut.add(n);
        Collections.sort(cut);

        int[][] dp = new int[c+2][c+2];

        for(int i=c;i>=1;i--){
            for(int j = 1;j<=c;j++){
                if(j<i)
                    continue;

                int res = (int) 1e9;
                for(int k=i;k<=j;k++){
                    int mm = cut.get(j+1) - cut.get(i-1) + dp[i][k-1] + dp[k+1][j];
                    res = Math.min(res, mm);
                }
                dp[i][j] = res;
            }
        }
        return dp[1][c];
    }
    //50. Burst Balloons|(DP-51)
    public int maxCoins1(int[] nums) {
        int n = nums.length;
        List<Integer> num = new ArrayList<>();

        num.add(1);
        for(int i=0;i<n;i++)
            num.add(nums[i]);
        num.add(1);

        int[][] dp = new int[n+1][n+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);

        return solve2(num, 1, n, dp);
    }
    int solve2(List<Integer> num, int i, int j, int[][] dp){
        if(j < i)
            return 0;
        int res = 0;
        if(dp[i][j] != -1) return dp[i][j];
        for(int k =i;k<=j;k++){
            int mm = num.get(i-1)*num.get(k)*num.get(j+1) + solve2(num, i, k-1, dp) + solve2(num, k+1, j, dp);
            res = Math.max(mm, res);
        }
        return dp[i][j] = res;
    }
    //-------------------OR-------------------
    public int maxCoins2(int[] nums) {
        Stack<Integer> st = new Stack<>();
        st.pop();
        int n = nums.length;
        List<Integer> num = new ArrayList<>();

        num.add(1);
        for (int value : nums) num.add(value);
        num.add(1);

        int[][] dp = new int[n+2][n+2];

        for(int i= n;i>=1;i--){
            for(int j=1;j<=n;j++){
                if(j < i)
                    continue;
                int res = 0;
                for(int k =i;k<=j;k++){
                    int mm = num.get(i-1)*num.get(k)*num.get(j+1) + dp[i][k-1] + dp[k+1][j];
                    res = Math.max(mm, res);
                }
                dp[i][j] = res;
            }
        }
        return dp[1][n];
    }
    // extra. Egg Dropping Puzzle
    static int eggDrop1(int n, int k) {
        int[][] dp = new int[n+1][k+1];
        for(int[] row : dp){
            Arrays.fill(row, -1);
        }
        return solve(n, k, dp);
    }
    static int solve(int n , int k, int[][] dp){
        if( k <= 1)
            return k;
        if( n == 1)
            return k;
        if(dp[n][k] != -1) return dp[n][k];
        int res = (int) 1e9;
        for(int j=1;j<=k;j++){
            int val = 1 + Math.max(solve(n-1, j-1, dp), solve(n, k-j, dp));
            res = Math.min(res, val);
        }
        return dp[n][k] = res;
    }
    //-------------------OR-------------------
    static int eggDrop(int n, int k) {
        int[][] dp = new int[n+1][k+1];
        for(int i=0;i<=n;i++){
            dp[i][1] = 1;
        }
        for(int i=0;i<=k;i++){
            dp[1][i] = i;
        }

        for(int i=2;i<=n;i++){
            for(int j=2;j<=k;j++){
                // if(j <= 1 || i == 1){ // either include this or prev base case
                //     dp[i][j] = j;
                //     continue;
                // }
                int res = (int) 1e9;
                for(int fr = 1; fr<=j;fr++){
                    int val = 1 + Math.max(dp[i-1][fr-1], dp[i][j-fr]);
                    res = Math.min(val, res);
                }
                dp[i][j] = res;
            }
        }
        return dp[n][k];
    }

    //51. Evaluate Boolean Expression to True|(DP-52)
    public boolean parseBoolExpr(String e) {
        Stack<Character> st = new Stack<>();
        for(int i=0;i<e.length();i++){
            char ch = e.charAt(i);
            if(ch == ',')
                continue;
            else if( ch == ')'){
                List<Character> list = new ArrayList<>();
                while(st.peek() != '('){
                    list.add(st.pop());
                }
                st.pop();
                char op = st.pop();
                st.push(solve(list, op));
            }else
                st.push(ch);
        }

        return st.peek() == 't';
    }
    char solve(List<Character> list, char op){

        if(op == '!')
            return list.get(0) == 't' ? 'f' : 't';

        if(op == '&'){
            for(int i=0;i<list.size();i++){
                if(list.get(i) == 'f')
                    return 'f';
            }
            return 't';
        }

        if(op == '|'){
            for(int i=0;i<list.size();i++){
                if(list.get(i) == 't')
                    return 't';
            }
            return 'f';
        }
        return 't';
    }

    //52. Palindrome Partitioning - II|(DP-53)
    public int minCut1(String s) {
        int n = s.length();
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(s, 0, dp)-1;
    }
    int solve(String s, int i, int[] dp){
        if(i == s.length())
            return 0;

        if(dp[i] != -1) return dp[i];
        int res = (int) 1e9;

        for(int j=i;j<s.length();j++){
            if(isPalindrome(s, i, j)){
                int val = 1 + solve(s, j+1, dp);
                res = Math.min(res, val);
            }
        }
        return dp[i] = res;
    }
    boolean isPalindrome(String s, int i, int j){
        while(i<j){
            if(s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }
    //-------------------OR-------------------
    public int minCut2(String s) {
        int n = s.length();
        int[] dp = new int[n+1];

        for(int i=n-1;i>=0;i--){
            int res = (int) 1e9;

            for(int j =i;j<n;j++){
                if(isPalindrome(s, i, j)){
                    int val = 1 + dp[j+1];
                    res = Math.min(res, val);
                }
            }
            dp[i] = res;
        }
        return dp[0] -1;
    }
    //53. Partition Array for Maximum Sum|(DP-54)
    public int maxSumAfterPartitioning1(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(arr, 0, n, k, dp);
    }
    int solve(int[] arr, int i, int n, int k, int[] dp){

        if(i == n)
            return 0;
        if(dp[i] != -1) return dp[i];
        int maxVal =0;
        int res = 0;
        for(int j=i;j< Math.min(i+k, n);j++){
            maxVal = Math.max(maxVal, arr[j]);

            int mm = maxVal * (j-i+1) + solve(arr, j+1, n, k, dp);
            res = Math.max(mm, res);
        }
        return dp[i] = res;
    }
    //-------------------OR-------------------
    public int maxSumAfterPartitioning2(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n+1];

        for(int i=n-1;i>=0;i--){
            int maxVal = 0, res = 0;
            for(int j=i;j<Math.min(n, i+k);j++){
                maxVal = Math.max(maxVal, arr[j]);
                int mm = maxVal * (j-i+1) + dp[j+1];
                res = Math.max(res, mm);
            }
            dp[i] = res;
        }
        return dp[0];
    }
    //54. Maximum Rectangle Area with all 1's|(DP-55)
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
    //55. Count Square Submatrices with All Ones|(DP-56)
    public int countSquares(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        for(int i=0;i<n;i++){
            dp[i][0] = matrix[i][0];
        }
        for(int j=0;j<m;j++){
            dp[0][j] = matrix[0][j];
        }

        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                if(matrix[i][j] == 0) {
                    dp[i][j] = 0;
                }else {
                    int size = Math.min(dp[i-1][j], Math.min(dp[i-1][j-1], dp[i][j-1]));
                    dp[i][j] = 1 + size;
                }
            }
        }

        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                res += dp[i][j];
            }
        }
        return res;
    }
}
