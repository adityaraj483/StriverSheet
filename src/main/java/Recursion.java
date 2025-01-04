import java.util.*;
import java.lang.String;
public class Recursion {
    public static void main(String[] args) {

    }
    //1. Recursive Implementation of atoi()
    long res = 0;
    public int myAtoi(String s) {
        s = s.trim();

        if(s.isEmpty())
            return 0;
        int sign = 1;
        int index = 0;
        if(s.charAt(index) == '+' || s.charAt(index) == '-') {
            if(s.charAt(index) == '-')
                sign = -1;
            index++;
        }
        solve(s, index);
        res = res * sign;
        if(res > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if(res < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int)res;
    }
    void solve(String s, int index){
        if(s.length() == index || res > Integer.MAX_VALUE)
            return ;

        if(s.charAt(index) <= '9' && s.charAt(index) >= '0'){
            res = res * 10 + s.charAt(index) - '0';
            solve(s, index+1);
        }
    }
    //2. Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
    public double myPow(double x, int n) {
        double ans = 1.0;
        long n1 = n;
        if (n1 < 0) n1 = -1 * n1;
        while (n1 > 0) {
            if (n1 % 2 == 1) {
                ans = ans * x;
                n1 = n1 - 1;
            } else {
                x = x * x;
                n1 = n1 / 2;
            }
        }
        if (n < 0) ans = (double)(1.0) / (double)(ans);
        return ans;
    }
    //3. Count Good Numbers
    int mod = (int) 1e9 + 7;
    public int countGoodNumbers(long n) {
        if(n == 1)
            return 5;
        long count4 = n/2;
        long count5 = n - count4;

        long res = (power(4, count4) * power(5, count5)) % mod;
        return  (int)res;
    }
    long power(long x, long n){
        if( n == 0 || x == 0)
            return 1;
        if(n == 1)
            return x;
        long res = power(x, n/2);

        if(n % 2 == 0)
            return (res * res) % mod;
        else
            return (res*res*x) % mod;
    }
    //4. sort stack
    public Stack<Integer> sort(Stack<Integer> s) {
        if(s.isEmpty())
            return s;
        int ele = s.pop();
        sort(s);
        insert(s, ele);
        return s;
    }
    public void insert(Stack<Integer> s, int ele){
        if(s.isEmpty() || s.peek() <= ele)
            s.add(ele);
        else{
            int val = s.pop();
            insert(s, ele);
            insert(s, val);
        }
    }
    //5. Reverse a stack using recursion
    static void reverse(Stack<Integer> s) {
        if(s.isEmpty())
            return;
        int val = s.pop();
        reverse(s);
        insertAtBottom(s, val);
    }
    static void insertAtBottom(Stack<Integer> s, int val) {
        if(s.isEmpty())
            s.add(val);
        else{
            int ele = s.pop();
            insertAtBottom(s, val);
            s.add(ele);
        }
    }
    //6. Generate all binary strings
    public static List<String> generateBinaryStrings(int n) {
        List<String> res = new ArrayList<>();
        generate(n, "", true, res);
        return res;
    }
    static void generate(int n, String ds, boolean flag, List<String> res){
        if(n == 0){
            res.add(ds);
            return;
        }
        generate(n-1, ds + '0', true, res);
        if(flag){
            generate(n-1, ds + '1', false, res);
        }
    }
    //7. Generate Parenthesis
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generate(n, n, "", res);
        return res;
    }
    void generate(int open, int close, String ds, List<String> res){
        if(open < 0 || close < 0 || open > close)
            return;
        if(open == 0 && close == 0){
            res.add(ds);
            return;
        }

        generate(open -1, close, ds + '(', res);
        generate(open, close -1, ds + ')', res);
    }
    //8. Print all subsequences/Power Set
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        generate(nums, 0, ds, res);
        return res;
    }
    void generate(int[] nums, int index, List<Integer> ds, List<List<Integer>> res){
        if(index == nums.length){
            res.add(new ArrayList<>(ds));
            return;
        }
        generate(nums, index+1, ds, res);
        ds.add(nums[index]);
        generate(nums, index+1, ds, res);
        ds.remove(ds.size()-1);
    }
    //9. Better String
    public String betterString(String str1, String str2) {
        // Code here\
        Set<String> hs1 = new HashSet<>();
        Set<String> hs2 = new HashSet<>();
        generate(str1, hs1);
        generate(str2, hs2);
        if(hs1.size() >= hs2.size())
            return str1;
        return str2;
    }
    void generate(String s, Set<String> hs){
        long n = power(2, s.length());

        for(long i=0;i<n;i++){
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<s.length();j++){
                if((i & (1<<j)) > 0){
                    sb.append(s.charAt(j)+"");
                }
            }
            hs.add(sb.toString());
        }
    }
    //----------------------------------------------------------------
    public static String betterString1(String str1, String str2) {
        // Code here
        long count1 = countTotalUniqueSubsequence(str1);
        long count2 = countTotalUniqueSubsequence(str2);
        if(count1 >= count2)
            return str1;
        return str2;
    }
    static long countTotalUniqueSubsequence(String s){
        Map<Character, Long> mp = new HashMap<>();
        long count = 1;
        for(char ch : s.toCharArray()){

            long newCount = count * 2;
            if(mp.containsKey(ch)){
                newCount -= mp.get(ch);
            }

            mp.put(ch, count);
            count = newCount;
        }
        return count;
    }
    //10. Count all subsequences with sum K
    public int perfectSum(int[] nums, int target) {
        Arrays.sort(nums);
        int[][] dp = new int[target+1][nums.length];
        for(int[] arr : dp)
            Arrays.fill(arr, -1);
        return solve(nums, 0, target, dp);
    }
    int solve(int[] nums, int i, int target, int[][] dp){
        if(i == nums.length || target < 0){
            if(target == 0)
                return 1;
            return 0;
        }
        if(dp[target][i] != -1)
            return dp[target][i];

        int count = solve(nums, i+1, target - nums[i], dp) + solve(nums, i+1, target, dp);

        return dp[target][i]=count;
    }
    //11. Check if there exists a subsequence with sum K
    public static boolean isSubsetPresent(int n, int k,int []a) {
        return solve(a, 0, n, k);
    }
    static boolean solve(int[] a, int i, int n, int k){
        if( k == 0)
            return true;
        if(i == n || k < 0)
            return false;
        return solve(a, i+1, n, k-a[i]) || solve(a, i+1, n, k);
    }
    //12. Combination sum , given distinct integers, the same number may be chosen from candidates an unlimited number of times
    public List<List<Integer>> combinationSum(int[] can, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        Arrays.sort(can);
        solve(can, 0, ds, target, res);
        return res;
    }
    void solve(int[] can, int i, List<Integer> ds, int target, List<List<Integer>> res){
        if(i == can.length || target == 0){
            if(target == 0)
                res.add(new ArrayList<>(ds));
            return;
        }

        for(int j = i;j <can.length;j++){
            if(can[j] > target)
                break;
            ds.add(can[j]);
            solve(can, j, ds, target - can[j], res);
            ds.remove(ds.size()-1);
        }
    }
    //13. Combination Sum II
    public List<List<Integer>> combinationSum2(int[] can, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        Arrays.sort(can);
        solve(can, 0, target, ds, res);
        return res;
    }
    void solve(int[] can, int i, int target, List<Integer> ds, List<List<Integer>> res){
        if(i == can.length || target == 0){
            if(target == 0){
                res.add(new ArrayList<>(ds));
            }
            return ;
        }

        for(int j=i;j<can.length;j++){
            if(i != j && can[j] == can[j-1])
                continue;
            if(can[j] > target)
                break;
            ds.add(can[j]);
            solve(can, j+1, target - can[j], ds, res);
            ds.remove(ds.size()-1);
        }
    }
    //14. Subset Sum-I
    public ArrayList<Integer> subsetSums(int[] arr) {
        // code here
        ArrayList<Integer> res = new ArrayList<>();
        solve(arr, 0, 0, res);
        return res;
    }
    void solve(int[] arr, int i, int sum, ArrayList<Integer> res){
        if( i == arr.length){
            res.add(sum);
            return;
        }
        solve(arr, i+1, sum + arr[i], res);
        solve(arr, i+1, sum, res);
    }
    //15. Subsets II
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        Arrays.sort(nums);
        solve(nums, 0, ds, res);
        return res;
    }
    void solve(int[] nums, int i, List<Integer> ds, List<List<Integer>> res){
        res.add(new ArrayList<>(ds));

        for(int j=i;j<nums.length;j++){
            if(i != j && nums[j] == nums[j-1])
                continue;
            ds.add(nums[j]);
            solve(nums, j+1, ds, res);
            ds.remove(ds.size()-1);
        }
    }
    //16. combination sum III
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        solve(k, n, 1, ds, res);
        return res;
    }
    void solve(int n, int target, int val, List<Integer> ds, List<List<Integer>> res){
        if(n == 0 || target <=0 || val == 10){
            if( n == 0 && target == 0){
                res.add(new ArrayList<>(ds));
            }
            return;
        }

        solve(n, target, val+1, ds, res);
        ds.add(val);
        solve(n-1, target - val, val+1, ds, res);
        ds.remove(ds.size()-1);
    }
    //17. Letter Combinations of a Phone number
    public List<String> letterCombinations(String num) {
        if(num.isEmpty())
            return Collections.emptyList();

        String[] digits = new String[]{"", "", "abc",
                "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> res = new ArrayList<>();
        solve(digits, num, 0, "", res);
        return res;
    }
    void solve(String[] digits, String num, int i, String ds, List<String> res){
        if(i == num.length()){
            res.add(ds);
            return;
        }

        String letters = digits[num.charAt(i) - '0'];
        for(char ch : letters.toCharArray()){
            solve(digits, num, i+1, ds + ch, res);
        }
    }
    //18.

}
