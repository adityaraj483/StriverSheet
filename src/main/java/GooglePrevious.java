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
    //
}
