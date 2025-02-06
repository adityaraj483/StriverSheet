import java.util.Arrays;

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
    //40. -------------------OR-------------------
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

}
