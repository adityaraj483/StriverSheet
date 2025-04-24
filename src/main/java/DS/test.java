package DS;

import java.util.*;
import java.util.stream.Collectors;

public class test{
    public static void main(String[] args) {
        int amount = 10;
        int[] coins = {2, 5, 6};
        System.out.println(change(amount, coins));

    }
    static public int change(int amount, int[] coins) {
        int n = coins.length;
        int[] prev = new int[amount+1];
        int[] curr = new int[amount+1];

        for(int i=0;i<=amount;i++){
            if(i % coins[0] == 0)
                prev[i] = 1;
        }

        for(int i=1;i<n;i++){
            for(int j=0;j<=amount;j++){
                int notTake = prev[j];
                int take = 0;
                if(j >= coins[i])
                    take = curr[j-coins[i]];
                curr[j] = take + notTake;
            }
            prev = curr;
        }
        for(int val : prev){
            System.out.print(val+", ");
        }
        return prev[amount];
    }
    static int solve(int i, int[] arr, int target, Integer[][] dp){
       if( i == 0 ){
           if( target == 0 && arr[i] ==0)
               return 2;
           if(target == 0 || target % arr[i] == 0)
               return 1;
           return 0;
       }

       if(dp[i][target] != null) return dp[i][target];

       int notTake = solve(i-1, arr, target, dp);
       int take = 0;
       if(arr[i] <= target){
           take = solve(i, arr, target - arr[i], dp);
       }
       return dp[i][target] = take + notTake;
    }

}

