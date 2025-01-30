import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {
    public static void main(String[] args) {

    }
    //1. Climbing Stars
    public int climbStairs(int n) {
        if(n <=2)
            return n;
        int prev = 1, curr = 2;
        for(int i=3;i<n+1;i++){
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
    //2. Frog Jump(DP-3)
    int minCost1(int[] height) {
        int n = height.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(height, 0, n, dp);
    }

    int solve(int[] arr, int index, int n, int[] dp){
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        if(dp[index] != -1)
            return dp[index];
        if(index + 1 < n)
            left = Math.abs(arr[index] - arr[index+1]) + solve(arr, index+1, n, dp);
        if(index + 2 < n)
            right = Math.abs(arr[index] - arr[index+2]) + solve(arr, index+2, n, dp);
        if(left == right && left == Integer.MAX_VALUE)
            return dp[index]= 0;
        return dp[index] = Math.min(left, right);
    }
    //---------------------------or--------------------------------
    int minCost2(int[] height) {
        int n = height.length;
        int[] dp = new int[n];
        dp[0] = 0;
        for(int i=1;i<n;i++){
            int a = dp[i-1] + Math.abs(height[i] - height[i-1]);
            int b = (int) 1e9;
            if(i-2 >= 0)
                b = dp[i-2] + Math.abs(height[i] - height[i-2]);
            dp[i] = Math.min(a,b);
        }
        return dp[n-1];
    }
    //---------------------------or--------------------------------
    int minCost3(int[] height) {
        int n = height.length;

        int first =0, second = 0;
        for(int i=1;i<n;i++){
            int a = first + Math.abs(height[i] - height[i-1]);
            int b = (int) 1e9;
            if(i-2 >= 0)
                b = second + Math.abs(height[i] - height[i-2]);
            int res = Math.min(a,b);
            second = first;
            first = res;
        }
        return first;
    }
    //3. Frog Jump with k distances(DP-4)
    public int minimizeCost1(int k, int arr[]) {
        // code here

        int n = arr.length;
        if( n == 1)
            return 0;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return solve(arr, k, 0, n, dp);
    }
    int solve(int[] arr, int k, int i, int n, int[] dp){
        if(dp[i] != -1)
            return dp[i];
        int res = Integer.MAX_VALUE;
        for(int j=i+1;j<Math.min(i+k+1, n);j++){
            int s = solve(arr, k, j, n, dp);

            if(s == Integer.MAX_VALUE)
                s = 0;

            int val = Math.abs(arr[i] - arr[j]);
            res = Math.min(res, val + s);
        }
        return dp[i]=res;
    }
    //---------------------------or--------------------------------
    public int minimizeCost2(int k, int arr[]) {
        int n = arr.length;
        int[] dp = new int[n];

        for(int i=1;i<n;i++){
            int res = Integer.MAX_VALUE;
            for(int j=1;j<=k;j++){
                if(i-j < 0)
                    break;

                int val = Math.abs(arr[i] - arr[i-j]);
                res = Math.min(dp[i-j] + val, res);

            }
            dp[i] = res;
        }
        return dp[n-1];
    }
    //---------------------------or--------------------------------
    public int minimizeCost3(int k, int arr[]) {
        int n = arr.length;
        int[] dp = new int[k];

        for(int i=1;i<n;i++){
            int res = Integer.MAX_VALUE;
            for(int j=1;j<=k;j++){
                if(i-j < 0)
                    break;

                int val = Math.abs(arr[i] - arr[i-j]);
                res = Math.min(dp[k-j] + val, res);

            }
            for(int j=1;j<k;j++){
                dp[j-1] = dp[j];
            }
            dp[k-1] = res;
        }
        return dp[k-1];
    }
    //4. House robber 1 (DP 5)
    public int rob1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        int pick = nums[n-1] + solve(nums, n-3, dp);
        int notPick = 0 + solve(nums, n-2, dp);
        return Math.max(pick, notPick);
    }
    int solve(int[] nums, int index, int[] dp){
        if(index < 0)
            return 0;
        if(dp[index] != -1)
            return dp[index];

        int pick = nums[index] + solve(nums, index-2, dp);
        int notPick = 0 + solve(nums, index-1, dp);
        return dp[index] = Math.max(pick, notPick);
    }
    //---------------------------or--------------------------------
    public int rob2(int[] nums) {
        int n = nums.length;
        if( n == 1)
            return nums[0];
        int[] dp = new int[n];
        int take = 0;
        int notTake = 0;
        dp[0] = nums[0];
        for(int i=1;i<n;i++){
            take = nums[i];
            if(i >1) take += dp[i-2];
            notTake = 0 + dp[i-1];
            dp[i] = Math.max(take, notTake);
        }
        return Math.max(dp[n-1], dp[n-2]);
    }
    //---------------------------or--------------------------------
    public int rob3(int[] nums) {
        int n = nums.length;
        int prev = nums[0];
        int prev2 = 0;

        for(int i=1;i<n;i++){
            int take = nums[i];
            if(i >1) take += prev2;

            int notTake = 0 + prev;

            int currMax = Math.max(take, notTake);
            prev2 = prev;
            prev = currMax;
        }
        return prev;
    }
    //5. House Robber II (DP 6)
    public int rob(int[] nums) {
        int n = nums.length;
        if(n == 1)
            return nums[0];
        int pick = solve(nums, 0, n-1);
        int notPick = solve(nums, 1, n);
        return Math.max(pick, notPick);
    }
    int solve(int[] nums, int start, int end){

        int prev = nums[start];
        int prev2 = 0;
        for(int i=start+1;i<end;i++){
            int pick = nums[i];
            if(i - 1 > start)
                pick += prev2;
            int notPick = 0 + prev;
            int curr = Math.max(pick, notPick);
            prev2 = prev;
            prev = curr;
        }
        return prev;
    }
    //6. Ninja's Training (DP 7)
    public int maximumPoints1(int arr[][]) {
        int n =arr.length;
        int m = arr[0].length;
        int[][] dp = new int[n][m+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);

        return solve(arr, 0, -1, n, m, dp);
    }
    int solve(int[][] arr, int row, int prevCol, int n, int m, int[][] dp){
        if(row == n)
            return 0;
        if(dp[row][prevCol+1] != -1) return dp[row][prevCol+1];
        int res = 0;
        for(int col =0;col<m;col++){
            if(col == prevCol)
                continue;
            int val = arr[row][col] + solve(arr, row+1, col, n, m, dp);
            res = Math.max(res, val);
        }
        return dp[row][prevCol+1]=res;
    }
    //---------------------------or--------------------------------
    public int maximumPoints2(int arr[][]) {
        int n = arr.length;
        int m = arr[0].length;
        int[][] dp = new int[n][m];
        for(int i=0;i<m;i++)
            dp[0][i] = arr[0][i];

        for(int day=1;day<n;day++){

            for(int last=0;last<m;last++){
                int maxi = 0;
                for(int curr=0;curr<m;curr++){
                    if(last == curr)
                        continue;
                    int val = arr[day][last] + dp[day-1][curr];

                    maxi = Math.max(val, maxi);
                }
                dp[day][last] = maxi;
            }
        }

        int res = 0;
        for(int i=0;i<m;i++)
            res = Math.max(res, dp[n-1][i]);
        return res;
    }
    //---------------------------or--------------------------------
    public int maximumPoints3(int arr[][]) {
        int n = arr.length;
        int m = arr[0].length;
        int[] prev = new int[3];
        for(int i=0;i<m;i++)
            prev[i] = arr[0][i];

        for(int day=1;day<n;day++){
            int[] temp = new int[3];
            for(int last=0;last<m;last++){
                int maxi = 0;

                for(int curr=0;curr<m;curr++){
                    if(last == curr)
                        continue;
                    int val = arr[day][last] + prev[curr];

                    maxi = Math.max(val, maxi);
                }
                temp[last] = maxi;
            }
            prev = temp;
        }
        int res = 0;
        for(int i=0;i<m;i++)
            res = Math.max(res, prev[i]);
        return res;
    }
    //7. Grid Unique Paths : DP on Grids (DP8)
    public int uniquePaths1(int m, int n) {
        int[][] dp = new int[m+1][n+1];
        for(int i=0;i<=m;i++)
            Arrays.fill(dp[i], -1);
        return solve(m, n, dp);
    }
    int solve(int m, int n, int[][] dp){
        if( m == 1 && n == 1)
            return 1;
        if( m <1 || n < 1)
            return 0;
        if(dp[m][n] != -1) return dp[m][n];
        return dp[m][n] = solve(m, n-1, dp) + solve(m-1, n, dp);
    }
    //---------------------------or--------------------------------
    public int uniquePaths2(int m, int n) {
        int[][] dp = new int[m+1][n+1];
        dp[1][1] = 1;
        for(int i=1;i<=m;i++){
            for(int j =1;j<=n;j++){
                if(i == j && i == 1)
                    continue;

                dp[i][j] = dp[i][j-1] + dp[i-1][j];
            }
        }
        return dp[m][n];
    }
    //---------------------------or--------------------------------
    public int uniquePaths3(int m, int n) {
        int[] prevCol = new int[n+1];
        prevCol[1] = 1;
        for(int i=1;i<=m;i++){
            int[] currCol = new int[n+1];
            for(int j =1;j<=n;j++){
                currCol[j] = currCol[j-1] + prevCol[j];
            }
            prevCol = currCol;
            for(int j=0;j<=n;j++){
                prevCol[j] = currCol[j];
            }
        }
        return prevCol[n];
    }
    //---------------------------or--------------------------------
    public int uniquePaths4(int m, int n) {
        int N = (n+m -2);
        int R = Math.min(n, m)-1;

        int res = 1;
        for(int i=1;i<=R;i++){
            res = (int) (1D * res * (N-i+1)/i);
        }
        return res;
    }
    //8. Grid Unique Paths with Obstacles : DP on Grids (DP9)
    public int uniquePathsWithObstacles1(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        if(arr[0][0] == 1 || arr[n-1][m-1] == 1)
            return 0;

        int[][] dp = new int[n][m];
        for(int[] row : dp)
            Arrays.fill(row,-1);
        return solve1(arr, 0, 0, n, m, dp);
    }

    int solve1(int[][] arr, int i, int j, int n, int m, int[][] dp){

        if(i >= n || j >= m || arr[i][j] == 1)
            return 0;
        if(i == n-1 && j == m-1)
            return 1;
        if(dp[i][j] != -1)
            return dp[i][j];

        dp[i][j] = solve(arr, i+1, j, n, m, dp) + solve(arr, i, j+1, n, m, dp);
        return dp[i][j];
    }
    //---------------------------or--------------------------------
    public int uniquePathsWithObstacles2(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        if(arr[0][0] == 1 || arr[n-1][m-1] == 1)
            return 0;

        int[][] dp = new int[n][m];


        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i == 0 && j == 0){
                    dp[i][j] = 1;
                    continue;
                }
                if(arr[i][j] == 1){
                    dp[i][j] = 0;
                    continue;
                }

                int up = 0;
                int left = 0;
                if(i > 0)
                    up = dp[i-1][j];
                if(j > 0)
                    left = dp[i][j-1];
                dp[i][j] = up + left;
            }
        }
        return dp[n-1][m-1];
    }
    //---------------------------or--------------------------------
    public int uniquePathsWithObstacles(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        if(arr[0][0] == 1 || arr[n-1][m-1] == 1)
            return 0;

        int[] prevCol = new int[m];


        for(int i=0;i<n;i++){
            int[] currCol = new int[m];
            for(int j=0;j<m;j++){
                if(i == 0 && j == 0){
                    currCol[j] = 1;
                    continue;
                }
                if(arr[i][j] == 1){
                    currCol[j] = 0;
                    continue;
                }

                int up = 0;
                int left = 0;
                if(i > 0)
                    up = prevCol[j];
                if(j > 0)
                    left = currCol[j-1];
                currCol[j] = up + left;
            }
            prevCol = currCol;
        }
        return prevCol[m-1];
    }
    //9. Minimum Path Sum : DP on Grids (DP10)
    public int minPathSum1(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve2(grid, 0, 0, n, m, dp);
    }
    int solve2(int[][] grid, int i, int j, int n, int m, int[][] dp){

        if(i >= n || j >= m)
            return 50000;
        if(i == n-1 && j == m-1)
            return grid[i][j];
        if(dp[i][j] != -1) return dp[i][j];
        int right = solve(grid, i, j+1, n, m, dp);
        int down = solve(grid, i+1, j, n, m, dp);
        return dp[i][j] = grid[i][j] + Math.min(right, down);
    }
    //---------------------------or--------------------------------
    public int minPathSum2(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i == 0 && j ==0){
                    dp[i][j] = grid[i][j];
                    continue;
                }

                int up = 50000;
                if(i > 0)
                    up = dp[i-1][j];

                int left = 50000;
                if(j > 0)
                    left = dp[i][j-1];

                dp[i][j] = grid[i][j] + Math.min(up, left);
            }
        }
        return dp[n-1][m-1];
    }
    //---------------------------or--------------------------------
    public int minPathSum3(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[] prevCol = new int[m];

        for(int i=0;i<n;i++){
            int[] currCol = new int[m];
            for(int j=0;j<m;j++){
                if(i == 0 && j ==0){
                    currCol[j] = grid[i][j];
                    continue;
                }

                int up = 50000;
                if(i > 0)
                    up = prevCol[j];

                int left = 50000;
                if(j > 0)
                    left = currCol[j-1];

                currCol[j] = grid[i][j] + Math.min(up, left);
            }
            prevCol = currCol;
        }
        return prevCol[m-1];
    }
    //10. Minimum path sum in Triangular Grid (DP 11)
    public int minimumTotal1(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve(triangle, 0, 0, dp);
    }
    int solve(List<List<Integer>> arr, int row, int prevCol, int[][] dp){
        if(arr.size() == row)
            return 0;
        if(dp[row][prevCol] != -1) return dp[row][prevCol];
        int same = arr.get(row).get(prevCol) + solve(arr, row+1, prevCol, dp);
        int next = arr.get(row).get(prevCol) + solve(arr, row+1, prevCol+1, dp);
        return dp[row][prevCol] = Math.min(same, next);
    }
    //---------------------------or--------------------------------
    public int minimumTotal2(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        for(int i=0;i<n;i++){
            dp[n-1][i] = triangle.get(n-1).get(i);
        }

        for(int i=n-2;i>=0;i--){
            for(int j=i; j>=0;j--){

                int down = triangle.get(i).get(j) + dp[i+1][j];
                int downRight = triangle.get(i).get(j) + dp[i+1][j+1];

                dp[i][j] =  Math.min(down, downRight);
            }
        }
        return dp[0][0];
    }
    //---------------------------or--------------------------------
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] prevCol = new int[n];
        for(int i=0;i<n;i++){
            prevCol[i] = triangle.get(n-1).get(i);
        }

        for(int i=n-2;i>=0;i--){
            int[] currCol = new int[n];
            for(int j=i; j>=0;j--){

                int down = triangle.get(i).get(j) + prevCol[j];
                int downRight = triangle.get(i).get(j) + prevCol[j+1];

                currCol[j] =  Math.min(down, downRight);
            }
            prevCol = currCol;
        }
        return prevCol[0];
    }
    //11. Minimum Falling Path Sum (DP-12)
    public int minFallingPathSum1(int[][] matrix) {
        int res = Integer.MAX_VALUE;
        int n = matrix.length;
        int[][] dp = new int[n][n];
        for(int[] a: dp)
            Arrays.fill(a, 100000);

        for(int i=0;i<matrix.length;i++){
            res = Math.min(res, matrix[0][i] + solve(matrix, 1, i, n, dp));
        }
        return res;
    }
    int solve(int[][] mat, int row, int prevCol, int n, int[][] dp){
        if(row == n)
            return 0;
        if(dp[row][prevCol] != 100000)
            return dp[row][prevCol];

        int left = Integer.MAX_VALUE;
        int center = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;

        if(prevCol > 0){
            left = mat[row][prevCol-1] + solve(mat, row+1,prevCol-1, n, dp);
        }
        center = mat[row][prevCol] + solve(mat, row+1, prevCol, n, dp);
        if(prevCol < n-1){
            right = mat[row][prevCol+1] + solve(mat, row+1, prevCol+1, n, dp);
        }
        return dp[row][prevCol] = Math.min(left, Math.min(center, right));
    }
    //---------------------------or--------------------------------
    public int minFallingPathSum2(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if( i == 0){
                    dp[i][j] = matrix[i][j];
                }else{

                    int leftDiag = Integer.MAX_VALUE;
                    int down = matrix[i][j] + dp[i-1][j];
                    int rightDiag = Integer.MAX_VALUE;

                    if(j > 0)
                        leftDiag = matrix[i][j] + dp[i-1][j-1];
                    if( j< n-1)
                        rightDiag = matrix[i][j] + dp[i-1][j+1];

                    dp[i][j] = Math.min(leftDiag, Math.min(down, rightDiag));
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            res = Math.min(res, dp[n-1][i]);
        }
        return res;
    }
    //---------------------------or--------------------------------
    public int minFallingPathSum3(int[][] matrix) {
        int n = matrix.length;
        int[] prevCol = new int[n];

        for(int i=0;i<n;i++){
            int[] currCol = new int[n];
            for(int j=0;j<n;j++){
                if( i == 0){
                    currCol[j] = matrix[i][j];
                }else{
                    int leftDiag = Integer.MAX_VALUE;
                    int down = matrix[i][j] + prevCol[j];
                    int rightDiag = Integer.MAX_VALUE;

                    if(j > 0)
                        leftDiag = matrix[i][j] + prevCol[j-1];
                    if( j< n-1)
                        rightDiag = matrix[i][j] + prevCol[j+1];

                    currCol[j] = Math.min(leftDiag, Math.min(down, rightDiag));
                }
            }
            prevCol = currCol;
        }
        int res = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            res = Math.min(res, prevCol[i]);
        }
        return res;
    }
    //12. 3-d DP : Ninja and his friends (DP-13)


}
