import java.lang.String;
import java.util.*;
import java.lang.*;

public class DynamicProgramming1 {
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
        return solve(nums, n-1, dp);
    }
    int solve(int[] nums, int index, int[] dp){
        if(index == 0)
            return nums[0];
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
        List<Integer> list = new ArrayList<>();
        list.sort((a, b) -> a-b);
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
    public int ninjaAndFrnds(int n, int m, int grid[][]) {
        // Code here
        int[][][] dp = new int[n][m][m];
        for(int[][] a: dp){
            for(int[] b : a)
                Arrays.fill(b, -1);
        }

        return dfs(0, 0, m-1, n, m, grid, dp);
    }
    int dfs(int row, int col1, int col2, int n, int m, int[][] grid, int[][][] dp){
        if(col1 < 0 || col1 >= m || col2 < 0 || col2 >= m)
            return -1;

        if(row == n-1){
            if(col1 ==col2)
                return grid[row][col1];
            else
                return grid[row][col1] + grid[row][col2];
        }

        if(dp[row][col1][col2] != -1) return dp[row][col1][col2];

        int maxi = 0;
        for(int i=-1;i<2;i++){
            for(int j=-1; j<2;j++){

                int res = 0;
                if(col1 == col2){
                    res = grid[row][col2] + dfs(row+1, col1+i, col2+j, n, m, grid, dp);
                }else{
                    res = grid[row][col1] + grid[row][col2];
                    res += dfs(row+1, col1+i, col2+j, n, m, grid, dp);
                }
                maxi = Math.max(res, maxi);
            }
        }
        return dp[row][col1][col2] = maxi;
    }
    //---------------------------or--------------------------------
    public int solve2(int n, int m, int grid[][]) {
        int[][][] dp = new int[n][m][m];
        for(int i=n-1;i>=0;i--){
            for(int j1 =0;j1<m;j1++){
                for(int j2 =0;j2<m;j2++){

                    if(i == n-1){
                        if(j1 == j2){
                            dp[i][j1][j2] = grid[i][j1];
                        }else{
                            dp[i][j1][j2] = grid[i][j1] + grid[i][j2];
                        }
                    }else{
                        int maxi = 0;
                        for(int r = -1; r<=1;r++){
                            for(int c=-1;c<=1;c++){
                                int res ;
                                if(j1 == j2){
                                    res =  grid[i][j1];
                                    if(j1+r >=0 && j1 +r <m && j2 + c>=0 && j2+c < m)
                                        res+= dp[i+1][j1+r][j2+c];
                                }else{
                                    res = grid[i][j1]+ grid[i][j2];
                                    if(j1+r >=0 && j1 +r <m && j2 + c>=0 && j2+c < m)
                                        res += dp[i+1][j1+r][j2+c];
                                }
                                maxi = Math.max(res, maxi);
                            }
                        }
                        dp[i][j1][j2] = maxi;
                    }

                }
            }
        }
        return dp[0][0][m-1];
    }
    //---------------------------or--------------------------------
    public int solve3(int n, int m, int grid[][]) {
        int[][] prev = new int[m][m];
        for(int i=n-1;i>=0;i--){

            int[][] curr = new int[m][m];
            for(int j1 =0;j1<m;j1++){
                for(int j2 =0;j2<m;j2++){

                    if(i == n-1){
                        if(j1 == j2){
                            curr[j1][j2] = grid[i][j1];
                        }else{
                            curr[j1][j2] = grid[i][j1] + grid[i][j2];
                        }
                    }else{
                        int maxi = 0;
                        for(int r = -1; r<=1;r++){
                            for(int c=-1;c<=1;c++){
                                int res ;
                                if(j1 == j2){
                                    res =  grid[i][j1];
                                    if(j1+r >=0 && j1 +r <m && j2 + c>=0 && j2+c < m)
                                        res+= prev[j1+r][j2+c];
                                }else{
                                    res = grid[i][j1]+ grid[i][j2];
                                    if(j1+r >=0 && j1 +r <m && j2 + c>=0 && j2+c < m)
                                        res += prev[j1+r][j2+c];
                                }
                                maxi = Math.max(res, maxi);
                            }
                        }
                        curr[j1][j2] = maxi;
                    }
                }
            }
            prev = curr;
        }
        return prev[0][m-1];
    }
    //13. Subset sum equal to target (DP- 14)
    static Boolean isSubsetSum1(int arr[], int target) {
        int n = arr.length;
        int[][] dp = new int[n][target+1];

        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve(arr, n-1, target, dp);
    }
    static boolean solve(int[] arr, int i, int target, int[][] dp){
        if(target == 0)
            return true;
        if(i == 0)
            return arr[0] == target;
        if(dp[i][target] != -1) return dp[i][target] == 1;

        boolean notTake = solve(arr, i-1, target, dp);

        boolean take = false;
        if(target >= arr[i])
            take = solve(arr, i-1, target - arr[i], dp);

        dp[i][target] = take || notTake ? 1 : 0;
        return take || notTake;
    }
    //---------------------------or--------------------------------
    static Boolean isSubsetSum2(int arr[], int target) {
        int n = arr.length;
        boolean[][] dp = new boolean[n][target+1];

        for(int i=0;i<n;i++)
            dp[i][0] = true;

        if(arr[0] <= target)
            dp[0][arr[0]] = true;

        for(int i=1;i<n;i++){
            for(int j=1;j<= target;j++){

                boolean notTake = dp[i-1][j];
                boolean take = false;

                if(j >= arr[i])
                    take = dp[i-1][j - arr[i]];

                dp[i][j] = take || notTake;
            }
        }
        return dp[n-1][target];
    }
    //---------------------------or--------------------------------
    static Boolean isSubsetSum3(int arr[], int target) {
        int n = arr.length;
        boolean[] prev = new boolean[target+1];

        prev[0] = true;
        if(arr[0] <= target)
            prev[arr[0]] = true;

        for(int i=1;i<n;i++){
            boolean[] curr = new boolean[target+1];
            curr[0] = true;
            for(int j=1;j<= target;j++){
                boolean notTake = prev[j];
                boolean take = false;
                if(j >= arr[i])
                    take = prev[j - arr[i]];
                curr[j] = take || notTake;
            }
            prev = curr;
        }
        return prev[target];
    }
    //14. Partition Equal Subset Sum (DP- 15)
    public boolean canPartition(int[] nums) {

        int sum = Arrays.stream(nums).reduce(0, Integer::sum);
        int n = nums.length;
        if(sum %2 == 1)
            return false;
        int target = sum/2;
        return isSubsetSum3(nums, target); // Just above same logic.
    }
    //15. Partition Set Into 2 Subsets With Min Absolute Sum Diff (DP- 16)

    //16. Count Subsets with Sum K (DP - 17)
    public int perfectSum1(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target+1];
        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve1(nums, n-1, target, dp);
    }
    int solve1(int[] nums, int index, int target, int[][] dp){

        if(index == 0){
            if(nums[0] == 0 && target == 0)
                return 2;
            if(nums[0] == target || target == 0)
                return 1;
            return 0;
        }
        if(dp[index][target] != -1) return dp[index][target];

        int notTake = solve1(nums, index-1, target, dp);
        int take = 0;
        if(target >= nums[index])
            take = solve1(nums, index-1, target - nums[index], dp);
        return dp[index][target] = take + notTake;
    }
    //---------------------------or--------------------------------
    public int perfectSum2(int[] nums, int target) {
        int n = nums.length;
        int[][] dp = new int[n][target+1];

        if(nums[0] <= target)
            dp[0][nums[0]] = 1;

        if(nums[0] == 0)
            dp[0][0] = 2;
        else
            dp[0][0] = 1;

        for(int i=1;i<n;i++){
            for(int j=0;j<=target;j++){

                int notTake = dp[i-1][j];
                int take = 0;
                if(j >= nums[i])
                    take = dp[i-1][j - nums[i]];
                dp[i][j] = take + notTake;
            }
        }
        return dp[n-1][target];
    }
    //---------------------------or--------------------------------
    public int perfectSum(int[] nums, int target) {
        int n = nums.length;
        int[] prev = new int[target+1];

        if(nums[0] <= target)
            prev[nums[0]] = 1;  // this is the crucial point;

        if(nums[0] == 0)
            prev[0] = 2;
        else
            prev[0] = 1;

        for(int i=1;i<n;i++){
            int[] curr = new int[target+1];
            for(int j=0;j<=target;j++){

                int notTake = prev[j];
                int take = 0;
                if(j >= nums[i])
                    take = prev[j - nums[i]];
                curr[j] = take + notTake;
            }
            prev = curr;
        }
        return prev[target];
    }
    //17. Count Partitions with Given Difference (DP - 18)
    int countPartitions(int[] arr, int d) {
        int n = arr.length;
        int totalSum = Arrays.stream(arr).reduce(0, Integer::sum);
        if((totalSum-d) % 2 == 1 || (totalSum-d) < 0)
            return 0;

        int target = (totalSum - d)/2;
        int[] prev = new int[target+1];

        if(arr[0] <= target)
            prev[arr[0]] = 1;

        if(arr[0] == 0)
            prev[0] = 2;
        else
            prev[0] = 1;

        for(int i=1;i<n;i++){
            int[] curr = new int[target+1];
            for(int j=0;j<=target;j++){
                int notTake = prev[j];
                int take = 0;
                if(j >= arr[i])
                    take = prev[j-arr[i]];
                curr[j] = take + notTake;
            }
            prev = curr;
        }
        return prev[target];
    }
    //18. Assign Cookies
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
    //19. Minimum Coins (DP - 20)
    public int coinChange1(int[] coins, int amount) {
        if(amount == 0)
            return 0;
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        int res = solve2(coins, n-1, amount, dp);

        if(res >= 1e9)
            return -1;
        return res;
    }
    int solve2(int[] coins, int index, int amount, int[][] dp){
        if(amount == 0)
            return 0;
        if(index == 0){
            if(amount % coins[0] == 0)
                return amount/coins[0];
            return (int) 1e9;
        }

        if(coins[index]  == amount)
            return 1;
        if(dp[index][amount] != -1) return dp[index][amount];
        int notTake = 0 + solve2(coins, index-1, amount, dp);
        int take = (int) 1e9;
        if(amount >= coins[index])
            take = 1 + solve2(coins, index, amount-coins[index], dp);

        return dp[index][amount] = Math.min(take, notTake);
    }
    //---------------------------or--------------------------------
    public int coinChange2(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        for (int target = 0; target <= amount; target++) {
            if (target % coins[0] == 0) dp[0][target] = target / coins[0];
            else dp[0][target] = (int) 1e9;
        }

        for (int i = 1; i < n; i++) {
            for (int target = 0; target <= amount; target++) {
                int take = (int) 1e9, notTake = (int) 1e9;
                if (target - coins[i] >= 0) {
                    take = 1 + dp[i][target - coins[i]];
                }
                notTake = 0 + dp[i - 1][target];
                dp[i][target] = Math.min(take, notTake);
            }
        }
        int ans = dp[n - 1][amount];
        if (ans >= 1e9) return -1;
        return ans;
    }
    //---------------------------or--------------------------------
    public int coinChange3(int[] coins, int amount) {
        int n = coins.length;
        int[] prev = new int[amount+1];

        for(int i=0;i<=amount;i++){
            if(i % coins[0] == 0)
                prev[i] = i/coins[0];
            else
                prev[i] = (int) 1e9;
        }

        for(int i=1;i<n;i++){
            int[] curr = new int[amount+1];
            for(int j=0;j<=amount;j++){
                int notTake = prev[j];
                int take = (int) 1e9;
                if(j >= coins[i])
                    take = 1+ curr[j- coins[i]];
                curr[j] = Math.min(take, notTake);
            }
            prev = curr;
        }
        int res = prev[amount];
        if(res >= 1e9)
            return -1;
        return res;
    }

    //20. Target Sum (DP - 21)
    public int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).reduce(0, Integer::sum);
        if( (totalSum - target) < 0 || (totalSum-target) %2 == 1)
            return 0;
        int sum = (totalSum -target)/2;

        int[][] dp = new int[n][sum+1];

        for(int[] a: dp)
            Arrays.fill(a, -1);

        return solve3(nums, n-1, sum, dp);
    }
    int solve3(int[] nums, int index, int target, int[][] dp){
        if(index == 0){
            if(nums[0] == 0 && target == 0)
                return 2;
            if(target == 0 || target == nums[0])
                return 1;
            return 0;
        }
        if(dp[index][target] != -1) return dp[index][target];
        int notTake = 0 + solve3(nums, index-1, target, dp);
        int take = 0;
        if(nums[index]<= target)
            take = solve3(nums, index-1, target - nums[index], dp);
        return dp[index][target] = take + notTake;
    }
    //---------------------------or--------------------------------
    public int findTargetSumWays2(int[] nums, int target) {
        int n = nums.length;

        int totalSum = Arrays.stream(nums).reduce(0, Integer::sum);
        if((totalSum - target) < 0 || (totalSum-target) % 2 == 1)
            return 0;
        int sum = (totalSum -target)/2;

        int[] prev = new int[sum+1];
        if(nums[0] <= sum)
            prev[nums[0]] = 1;
        if(nums[0] == 0)
            prev[0] = 2;
        else
            prev[0] = 1;

        for(int i=1;i<n;i++){
            int[] curr = new int[sum+1];
            for(int j =0;j<=sum;j++){

                int notTake = prev[j];
                int take = 0;
                if(j-nums[i] >=0)
                    take = prev[j-nums[i]];
                curr[j] = take + notTake;

            }
            prev = curr;
        }
        return prev[sum];
    }
    //21. Coin Change 2 (DP - 22)
    public int change(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount+1];
        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve4(coins, n-1, amount, dp);
    }
    int solve4(int[] coins, int index, int amount, int[][] dp){
        if(amount == 0)
            return 1;
        if(index < 0)
            return 0;
        if(dp[index][amount] != -1) return dp[index][amount];

        int notTake = solve4(coins, index-1, amount, dp);
        int take = 0;
        if(amount >= coins[index])
            take = solve4(coins, index, amount-coins[index], dp);
        return dp[index][amount] = take + notTake;
    }
    //---------------------------or--------------------------------
    public int change2(int amount, int[] coins) {
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
        return prev[amount];
    }
    //22. KnapSack 1 (DP - 23) --- Knapsack with Duplicate Items
    static int knapSack(int val[], int wt[], int capacity) {
        int n = val.length;
        int[][] dp = new int[n][capacity+1];
        for(int[] a: dp)
            Arrays.fill(a, -1);

        return solve(val, wt, n-1, capacity, dp);
    }
    static int solve(int[] val, int[] wt, int index, int cap, int[][] dp){
        if(index == 0){
            return cap/wt[0] * val[0];  // not necessary to fill whole bucket, sometimes cap can not be zero;
        }
        if(dp[index][cap] != -1) return dp[index][cap];
        int notTake = solve(val, wt, index-1, cap, dp);
        int take = 0;
        if(cap >= wt[index]){
            take = val[index] + solve(val, wt, index, cap - wt[index], dp);
        }
        return dp[index][cap] = Math.max(notTake, take);
    }
    //---------------------------or--------------------------------
    static int knapSack1(int val[], int wt[], int capacity) {
        int n = val.length;
        int[] prev = new int[capacity+1];
        int[] curr = new int[capacity+1];

        for(int i=0;i<= capacity;i++){
            prev[i] = i/wt[0] * val[0];
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<=capacity;j++){
                int notTake = prev[j];
                int take = 0;
                if(j >= wt[i])
                    take = val[i] + curr[j - wt[i]];
                curr[j] = Math.max(take, notTake);
            }
            prev = curr;
        }
        return prev[capacity];
    }
    //23. Rod Cutting Problem | (DP - 24)
    public int cutRod(int[] price) {
        int n = price.length;
        int[][] dp = new int[n][n+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve5(price, n-1, n, dp);
    }
    int solve5(int[] price, int index, int len, int[][] dp){

        if(index == 0){
            return price[index] *len;
        }

        if(dp[index][len] != -1) return dp[index][len];

        int notTake = 0 + solve5(price, index - 1, len, dp);
        int rodLen = index + 1;
        int take = 0;
        if(rodLen <= len)
            take = price[index]  + solve5(price, index, len - rodLen, dp);

        return dp[index][len] = Math.max(take, notTake);
    }
    //---------------------------or--------------------------------
    public int cutRod2(int[] price) {
        int n = price.length;
        int[][] dp = new int[n][n+1];

        for(int i=1;i<=n;i++){
            dp[0][i] = price[0]*i;
        }
        for(int i=1;i<n;i++){
            for(int j=1;j<=n;j++){

                int notTake = 0 + dp[i-1][j];
                int take = 0;
                int rodLen = i+1;
                if(rodLen <= j)
                    take = price[i] + dp[i][j-rodLen];
                dp[i][j] = Math.max(take, notTake);
            }
        }
        return dp[n-1][n];
    }
    //24. Longest Common Subsequence | (DP - 25)
    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] a: dp)
            Arrays.fill(a, -1);
        return solve(text1, text2, n, m, dp);
    }
    int solve(String s1, String s2, int index1, int index2, int[][] dp){
        if(index1 == 0 || index2 == 0){
            return 0;
        }
        if(dp[index1][index2] != -1) return dp[index1][index2];
        int ans = 0;
        if(s1.charAt(index1-1) == s2.charAt(index2-1)){
            ans = 1 + solve(s1, s2, index1-1, index2-1, dp);
        }else{
            int a = solve(s1, s2, index1-1, index2, dp);
            int b = solve(s1, s2, index1, index2-1, dp);
            ans = Math.max(a, b);
        }
        return dp[index1][index2] = ans;
    }
    //---------------------------or--------------------------------

    public int longestCommonSubsequence2(String text1, String text2) {
        int n = text1.length(), m = text2.length();
        int[] prev = new int[m+1];
        int[] curr = new int[m+1];

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int ans = 0;
                if(text1.charAt(i-1) == text2.charAt(j-1))
                    ans = 1 + prev[j-1];
                else
                    ans = Math.max(prev[j], curr[j-1]);
                curr[j] = ans;
            }
            prev = Arrays.copyOf(curr, curr.length);
        }
        return prev[m];
    }
    //25. Print ALL Longest Common Subsequence | (DP - 26)
    class Pair{
        int len;
        String path;
        int row, col;
        Pair(int row, int col, String path, int len){
            this.row = row;
            this.col = col;
            this.path = path;
            this.len = len;
        }
    }
    public List<String> all_longest_common_subsequences(String s, String t) {
        // Code here
        int n = s.length(), m = t.length();

        //creating dp array to store the length of longest common subsequence
        int[][] dp = new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int ans = 0;
                if(s.charAt(i-1) == t.charAt(j-1))
                    ans = 1 + dp[i-1][j-1];
                else
                    ans = Math.max(dp[i-1][j], dp[i][j-1]);
                dp[i][j] = ans;
            }
        }

        int maxLen = dp[n][m];
        Set<String> res = new TreeSet<>();
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(n, m, "", 0));
        Set<String> memo = new HashSet<>(); // to avoid duplicate paths
        // doing bfs to get all the longest common subsequences
        while(!q.isEmpty()){
            Pair p = q.remove();

            int row = p.row;
            int col = p.col;
            int len = p.len;
            String path = p.path;

            if(len == maxLen){
                res.add(new StringBuilder(path).reverse().toString());
                continue;
            }
            if(row == 0 || col == 0)
                continue;

            String key = row +", "+col+", "+path;
            if(memo.contains(key))
                continue;
            else
                memo.add(key);


            if(s.charAt(row-1) == t.charAt(col-1)){
                Pair p1 = new Pair(row-1, col-1, path + s.charAt(row-1), len+1);
                q.add(p1);
            }else{
                if(dp[row-1][col] > dp[row][col-1]){
                    Pair p1 = new Pair(row-1, col, path, len);
                    q.add(p1);
                }else if(dp[row][col-1]> dp[row-1][col]){
                    Pair p1 = new Pair(row, col-1, path, len);
                    q.add(p1);
                }else{
                    Pair p1 = new Pair(row, col-1, path, len);
                    Pair p2 = new Pair(row-1, col, path, len);
                    q.add(p1);
                    q.add(p2);
                }
            }
        }
        return new ArrayList<>(res);
    }
    //26. Longest Common Substring | (DP - 27)
    public int longestCommonSubstr(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[] prev = new int[m+1];
        int[] curr = new int[m+1];
        int res = 0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){

                if(s1.charAt(i-1) == s2.charAt(j-1))
                    curr[j] = 1 + prev[j-1];
                else
                    curr[j] = 0;
                res = Math.max(res, curr[j]);
            }
            prev = Arrays.copyOf(curr, m+1);
        }
        return res;
    }
    //27. Longest Palindromic Subsequence | (DP - 28)
    public int longestPalindromeSubseq(String s1) {
        String s2 = new StringBuilder(s1).reverse().toString();
        int n = s1.length();
        int[] prev = new int[n+1];
        int[] curr = new int[n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    curr[j] = 1 + prev[j-1];
                }else{
                    curr[j] = Math.max(prev[j], curr[j-1]);
                }
            }
            prev = Arrays.copyOf(curr, n+1);
        }
        return prev[n];
    }
    //28. Minimum insertions to make string palindrome | DP-29
    public int minInsertions(String s1) {
        String s2 = new StringBuilder(s1).reverse().toString();
        int n = s1.length();
        int[] prev = new int[n+1];
        int[] curr = new int[n+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=n;j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    curr[j] = 1 + prev[j-1];
                }else{
                    curr[j] = Math.max(prev[j], curr[j-1]);
                }
            }
            prev = Arrays.copyOf(curr, n+1);
        }
        return n - prev[n];
    }
    //29. Minimum Insertions/Deletions to Convert two String identical | (DP- 30)
    public int minDistance(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {

                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    curr[j] = 1 + prev[j - 1];
                else
                    curr[j] = Math.max(curr[j - 1], prev[j]);
                res = Math.max(res, curr[j]);
            }
            prev = Arrays.copyOf(curr, m + 1);
        }

        return (n -res) + (m - res);
    }
    //30. Shortest Common Super sequence | (DP - 31)
    public String shortestCommonSupersequence(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        int[][] dp = new int[n+1][m+1];

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int res = 0;
                if(str1.charAt(i-1) == str2.charAt(j-1)){
                    res = 1 + dp[i-1][j-1];
                }else{
                    res = Math.max(dp[i-1][j], dp[i][j-1]);
                }
                dp[i][j] = res;
            }
        }

        StringBuilder sb = new StringBuilder();
        int i=n, j = m;
        while(i> 0 && j > 0){

            if(str1.charAt(i-1) == str2.charAt(j-1)){
                sb.append(str1.charAt(i-1));
                i--;
                j--;
            }else{

                int up = dp[i-1][j];
                int down = dp[i][j-1];
                if(up == dp[i][j]){
                    sb.append(str1.charAt(i-1));
                    i--;
                }else{
                    sb.append(str2.charAt(j-1));
                    j--;
                }
            }
        }
        while(i > 0){
            sb.append(str1.charAt(--i));
        }
        while(j > 0)
            sb.append(str2.charAt(--j));

        return sb.reverse().toString();
    }
    //31. Distinct Subsequences| (DP-32)
    public int numDistinct(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve5(s, t, n, m, dp);
    }
    int solve5(String s, String t, int ind1, int ind2, int[][] dp){
        if(ind2 <= 0)
            return 1;
        if(ind1 <= 0)
            return 0;
        if(dp[ind1][ind2] != -1) return dp[ind1][ind2];
        if(s.charAt(ind1-1) == t.charAt(ind2-1)){
            return dp[ind1][ind2] = solve(s, t, ind1-1, ind2-1, dp) + solve(s, t, ind1-1, ind2, dp);
        }else
            return dp[ind1][ind2] = solve(s, t, ind1-1, ind2, dp);
    }
    //---------------------------or--------------------------------
    public int numDistinct2(String s, String t) {
        int n = s.length(), m = t.length();
        int[] prev = new int[m+1];
        int[] curr = new int[m+1];

        for(int i=1;i<=n;i++){
            prev[0] = 1;
            for(int j=1;j<=m;j++){
                int ans = 0;
                if(s.charAt(i-1) == t.charAt(j-1)){
                    ans = prev[j-1] + prev[j];
                }else
                    ans = prev[j];
                curr[j] = ans;
            }
            prev = Arrays.copyOf(curr, m+1);
        }
        return prev[m];
    }
    //32. Edit Distance | (DP-33)
    public int minDistance1(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve2(word1, word2, n, m, dp);
    }
    int solve2(String word1, String word2, int n , int m, int[][] dp){
        if(n == 0 && m == 0)
            return 0;
        if(m == 0)
            return n;
        if( n == 0)
            return m;
        if(dp[n][m] != -1)
            return dp[n][m];
        if(word1.charAt(n-1) == word2.charAt(m-1))
            return dp[n][m] = solve(word1, word2, n-1, m-1, dp);
        else{
            int a = 1 + solve(word1, word2, n, m-1, dp); // do insert
            int b = 1 + solve(word1, word2, n-1, m, dp); // do delete
            int c = 1 + solve(word1, word2, n-1, m-1, dp); // do replace
            int res = Math.min(a, Math.min(b, c));
            return dp[n][m] = res;
        }
    }
    //---------------------------or--------------------------------
    public int minDistance2(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[][] dp = new int[n+1][m+1];

        for(int i=0;i<=n;i++){
            dp[i][0] = i;
        }
        for(int i=0;i<=m;i++){
            dp[0][i] = i;
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                int res = 0;
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    res = dp[i-1][j-1];
                }else{
                    int a = 1 + dp[i][j-1]; // do insert
                    int b = 1 + dp[i-1][j]; // do delete
                    int c = 1 + dp[i-1][j-1]; // do replace
                    res = Math.min(a, Math.min(b, c));
                }
                dp[i][j] = res;
            }
        }
        return dp[n][m];
    }
    //---------------------------or--------------------------------
    public int minDistance3(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        int[] prev = new int[m+1];
        int[] curr = new int[m+1];

        for(int i=0;i<=m;i++){
            prev[i] = i;
        }

        for(int i=1;i<=n;i++){
            curr[0] = i;
            for(int j=1;j<=m;j++){

                int res = 0;
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    res = prev[j-1];
                }else{
                    int a = 1 + curr[j-1]; // do insert
                    int b = 1 + prev[j]; // do delete
                    int c = 1 + prev[j-1]; // do replace
                    res = Math.min(a, Math.min(b, c));
                }
                curr[j] = res;
            }
            prev = Arrays.copyOf(curr, m+1);
        }
        return prev[m];
    }
    //33. Wildcard Matching | (DP-34)
    public boolean isMatch1(String s, String p) {
        int n = s.length(), m = p.length();
        int[][] dp = new int[n+1][m+1];
        for(int[] a : dp)
            Arrays.fill(a, -1);
        return solve1(s, p, n, m, dp);
    }
    boolean solve1(String s, String p, int n, int m, int[][] dp){
        if( n == 0 && m ==0)
            return true;
        if( m == 0 && n > 0)
            return false;

        if(n == 0 && m > 0){
            return isAllStar(p, m);
        }

        if(dp[n][m] != -1) return dp[n][m] == 1;
        boolean res = false;

        if(p.charAt(m-1) == '?' || p.charAt(m-1) == s.charAt(n-1)){
            res = solve1(s, p, n-1, m-1, dp);
        }else if (p.charAt(m-1) == '*'){
            res = solve1(s, p, n-1, m, dp) || solve1(s, p, n, m-1, dp);
        }

        dp[n][m] = res ? 1: 0;
        return res;
    }
    boolean isAllStar(String s, int i){
        while(--i>=0){
            if(s.charAt(i) != '*')
                return false;
        }
        return true;
    }
    //---------------------------or--------------------------------
    public boolean isMatch2(String s, String p) {
        int n = s.length(), m = p.length();
        boolean[][] dp = new boolean[n+1][m+1];

        dp[0][0] = true;
        for(int i=1;i<=n;i++){
            dp[i][0] = false;
        }
        for(int i=1;i<=m;i++){
            if(p.charAt(i-1) == '*')
                dp[0][i] = true;
            else
                break;
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                boolean res = false;
                if(p.charAt(j-1) == '?' || p.charAt(j-1) == s.charAt(i-1))
                    res = dp[i-1][j-1];
                else if(p.charAt(j-1) == '*')
                    res = dp[i-1][j] || dp[i][j-1];
                dp[i][j] = res;
            }
        }
        return dp[n][m];
    }
    //---------------------------or--------------------------------
    public boolean isMatch3(String s, String p) {
        int n = s.length(), m = p.length();
        boolean[] prev = new boolean[m+1];
        boolean[] curr = new boolean[m+1];

        prev[0] = true;
        for(int i=1;i<=m;i++){
            if(p.charAt(i-1) == '*')
                prev[i] = true;
            else
                break;
        }

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                boolean res = false;
                if(p.charAt(j-1) == '?' || p.charAt(j-1) == s.charAt(i-1))
                    res = prev[j-1];
                else if(p.charAt(j-1) == '*')
                    res = prev[j] || curr[j-1];
                curr[j] = res;
            }
            prev = Arrays.copyOf(curr, m+1);
        }
        return prev[m];
    }
}
