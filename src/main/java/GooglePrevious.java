import java.util.Arrays;
public class GooglePrevious {
    public static void main(String[] args) {

    }
    //1. Sum of bit differences
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
    //2. Travelling Salesman Problem
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
    //3. Matrix diagonally traversal
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
    //4. Josephus problem
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

    //--------------------List of All DSA questions that are asked in Google interview--------------------
    //3. Kth Largest Element in an Array
    //4. Kth Smallest Element in an array
    //5. Median of Stream of Running Integers using Heaps
    //6. Sum of bit differences
    //7. Travelling Salesman Problem
    //8. Find the element that appears once in an array where every other element appears twice
    //9. Find the two non-repeating elements in an array of repeating elements
    //10. Find the Missing Number
    //11. Find the maximum and minimum element in an array
    //12. Find the "Kth" max and min element of an array
    //13. Count Inversions in an array
    //14. Find the maximum product subarray
    //15. Find the Longest Consecutive Subsequence
    //16. Given an array of size n and a number k, find all elements that appear more than n/k times
    //17. Maximum profit by buying and selling a share at most twice
    //18. Find whether an array is a subset of another array
    //19. Find the triplet that sum to a given value
    //20. Trapping Rain Water problem
    //21. Chocolate Distribution problem
    //22. Smallest Subarray with sum greater than a given value
    //23. Three way partitioning of an array around a given value
    //24. Minimum swaps required bring elements less equal K together
    //25. Minimum no. of operations required to make an array palindrome
    //26. Median of 2 sorted arrays of equal size
    //27. Median of 2 sorted arrays of different size
    //28. Merge Without Extra Space
    //29. Count triplets with sum smaller than a given value
    //30. Count of pairs with the given sum
    //31. Merge Overlapping Subintervals
}
