import DS.MountainArray;

import java.util.*;
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

    //5. Find All Possible Recipes from Given Supplies
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
    //1`. Partition to K Equal Sum Subsets
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
    //12.
}
