import java.lang.String;
import java.util.*;

public class string {
    //1. Remove outermost Paranthesis
    public String removeOuterParentheses1(String s) {
        return solve(s, 0);
    }
    String solve(String s, int i){
        if(i >= s.length())
            return "";
        int cnt = 0, j = i;
        while(j<s.length()){
            if(s.charAt(j) == '(')
                cnt++;
            else
                cnt--;
            if(cnt == 0)
                break;
            j++;
        }
        return s.substring(i+1, j) + solve(s, j+1);
    }
    //------------OR-------------------------------
    public String removeOuterParentheses2(String s) {
        Stack<Character> st = new Stack<>();
        String res = "";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == ')')
                st.pop();
            if(!st.isEmpty())
                res += s.charAt(i);

            if(s.charAt(i) == '(')
                st.push('(');
        }
        return res;
    }
    //2. Reverse Words in a String
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        int i = n-1, j= n-1;
        while(j>=0){

            if(s.charAt(j) != ' '){
                i= j;
                while(i >=0 && s.charAt(i) != ' ')
                    i--;
                sb.append(s.substring(i+1, j+1) + " ");
                j=i;
            }else{
                j--;
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    //3. Largest Odd Number in String
    public String largestOddNumber(String num) {
        int i = num.length()-1;

        while(i>=0){
            if(Integer.parseInt(num.charAt(i) +"") % 2 !=0)
                break;
            i--;
        }
        return num.substring(0,i+1);
    }
    //4. Longest Common Prefix
    public String longestCommonPrefix1(String[] strs) {
        Arrays.sort(strs);
        int a=0, b = strs.length-1;
        int n = strs[a].length();
        int m = strs[b].length();

        int count =0;
        int i=0, j=0;

        while(i<n && j<m){
            if(strs[a].charAt(i) == strs[b].charAt(j))
                count++;
            else
                break;
            i++;
            j++;
        }
        return strs[a].substring(0, count);
    }
    //----------------OR-------------------------
    public String longestCommonPrefix2(String[] strs) {

        int n = strs.length;
        String first = strs[0];
        int resLen = first.length();
        for(int i=1;i<n;i++){
            String second = strs[i];
            int currLen = second.length();
            while(resLen > currLen || first.equals(second.substring(0, resLen)) == false){
                resLen --;
                if(resLen == 0)
                    return "";
                first = first.substring(0, resLen);
            }
        }
        return first;
    }
    //5. Isomorphic String
    public boolean isIsomorphic(String s, String t) {
        int n = s.length();
        if(n != t.length())
            return false;

        Map<Character,Character> mp = new HashMap<>();
        Set<Character> valSet = new HashSet<>();

        for(int i=0;i<n;i++){
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);
            if(!mp.containsKey(ch1)){
                if(valSet.contains(ch2))
                    return false;
                mp.put(ch1, ch2);
                valSet.add(ch2);

            }else if(mp.containsKey(ch1) && mp.get(ch1) == ch2)
                continue;
            else
                return false;
        }
        return true;
    }
    //6. check whether one string is a rotation of another
    public boolean rotateString1(String s, String goal) {
        int n = s.length();
        if(n != goal.length())
            return false;

        String main = s+s;
        return main.contains(goal);
    }
    //----------------OR-------------------------
    public boolean rotateString2(String s, String goal) {
        int n = s.length();
        if(n != goal.length())
            return false;

        String main = s+s;
        return lcssubstr(main, goal);
    }
    boolean lcssubstr(String main, String goal){
        int n = main.length();
        int m = goal.length();

        int[] prev = new int[m+1];
        int[] curr = new int[m+1];
        int maxLen = 0;

        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){

                if(main.charAt(i-1) == goal.charAt(j-1)){
                    curr[j] = 1 + prev[j-1];
                }else
                    curr[j] = 0;
                maxLen = Math.max(curr[j], maxLen);
            }
            prev = Arrays.copyOf(curr, m+1);
        }
        return maxLen == goal.length();
    }
    //7. Check if two strings are anagram of each other
    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length())
            return false;

        int[] fre = new int[26];
        for(int i=0;i<s.length();i++){
            fre[s.charAt(i)- 'a'] ++;
            fre[t.charAt(i)- 'a'] --;
        }
        for(int i=0;i<26;i++){
            if(fre[i] != 0)
                return false;
        }
        return true;
    }
    //8. sort characters by frequency
    public String frequencySort(String s) {
        Map<Character, Integer> mp = new HashMap<>();

        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            mp.put(ch, mp.getOrDefault(ch, 0)+1);
        }

        List<Character> list = new ArrayList<>(mp.keySet());
        list.sort((a, b) -> Integer.compare(mp.get(b), mp.get(a)));

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++){

            char ch = list.get(i);
            int m = mp.get(ch);
            sb.append(String.valueOf(ch).repeat(m));
        }
        return sb.toString();
    }
    //9. Maximum Nesting Depth of the Parentheses
    public int maxDepth(String s) {

        int res = 0;
        int count =0;
        for(char ch : s.toCharArray()){
            if(ch == '(')
                count++;
            if(ch == ')')
                count--;
            res = Math.max(res, count);
        }
        return res;
    }
    //10. Roman to Integer
    public int romanToInt(String s) {
        int n = s.length();
        Map<Character, Integer> mp = new HashMap<>();
        mp.put('I', 1);
        mp.put('V', 5);
        mp.put('X', 10);
        mp.put('L', 50);
        mp.put('C', 100);
        mp.put('D', 500);
        mp.put('M', 1000);

        int res = 0;
        for(int i=0;i<n;i++){
            if(i < n-1 && mp.get(s.charAt(i)) < mp.get(s.charAt(i+1)))
                res -= mp.get(s.charAt(i));
            else
                res += mp.get(s.charAt(i));
        }
        return res;
    }
    // 10.1  Integer to Roman
    public String intToRoman(int num) {
        int[] values = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbol = new String[]{"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(i < values.length){

            if(num>= values[i]){
                sb.append(symbol[i]);
                num -= values[i];
            }else{
                i++;
            }
            if(num == 0)
                break;
        }
        return sb.toString();
    }
    //11. Implement Atoi
    long res;
    public int myAtoi(String s) {
        s = s.trim();
        if(s.length() == 0)
            return 0;
        int index = 0;
        int sign = 1;
        if(s.charAt(0) == '+' || s.charAt(0) == '-'){
            if(s.charAt(0) == '-')
                sign = -1;
            index++;
        }

        res = 0;
        solve1(s, index);
        res = res * sign;

        if(res < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        if(res > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        return (int)res;
    }
    void solve1(String s, int i){
        if(i == s.length())
            return;
        if(res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            return;

        if(s.charAt(i) <= '9' && s.charAt(i) >= '0'){
            res = res *10 + s.charAt(i) - '0';
            solve(s, i+1);
        }
    }
    //12. Count Number of Substrings with exactly k distinct characters.
    int countSubstr(String s, int k) {
        return solve2(s, k) - solve2(s, k-1);
    }
    int solve2(String s, int k){
        Map<Character, Integer> mp = new HashMap<>();
        int i=0, j=0;
        int n = s.length();
        int cnt = 0;
        while(j<n){

            mp.put(s.charAt(j), mp.getOrDefault(s.charAt(j), 0)+1);

            while(mp.size() > k){
                char ch = s.charAt(i);
                mp.put(ch, mp.getOrDefault(ch, 1)-1);
                if(mp.get(ch) == 0)
                    mp.remove(ch);
                i++;
            }
            j++;
            cnt += j-i;
        }
        return cnt;
    }
    //13. Longest Palindromic Substring
    public String longestPalindrome(String s) {
        int n = s.length();
        int start = 0, size =0;

        for(int i=0;i<n;i++){

            int l=i, r= i;
            while(l>=0 && r<n && s.charAt(l) == s.charAt(r)){
                l--;
                r++;
            }
            if(r-l -1 > size){
                start = l+1;
                size = r-l-1;
            }

            l = i;r= i+1;
            while(l>=0 && r<n && s.charAt(l) == s.charAt(r)){
                l--;
                r++;
            }
            if(r-l-1 > size){
                start = l+1;
                size = r-l-1;
            }
        }
        return s.substring(start, start+size);
    }
    //14. Sum of Beauty of All Substrings
    public int beautySum(String s) {
        int n = s.length();
        Map<Character, Integer> mp;
        int cnt = 0;
        for(int i=0;i<n;i++){
            mp = new HashMap<>();
            for(int j=i;j<n;j++){

                mp.put(s.charAt(j), mp.getOrDefault(s.charAt(j), 0)+1);
                if(mp.size() >= 2)
                    cnt += func(mp);
            }
        }
        return cnt;
    }
    int func(Map<Character, Integer> mp){
        int min = (int) 1e9;
        int max = 0;

        for(Map.Entry<Character, Integer> entry : mp.entrySet()){
            min = Math.min(min, entry.getValue());
            max = Math.max(max, entry.getValue());
        }
        return max - min;
    }

    //15. Longest valid parenthesis
    static int maxLength(String s) {
        int open = 0, close = 0;
        int cnt = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '(')
                open++;
            else{
                close++;
            }

            if(close > open){
                open =0;
                close = 0;
            }

            if(open == close)
                cnt = Math.max(cnt, open+close);
        }

        close = 0;
        open = 0;
        for(int i=s.length()-1;i>=0;i--){
            if(s.charAt(i) == ')')
                close++;
            else{
                open++;
            }

            if(open > close){
                open = 0;
                close = 0;
            }
            if(open == close)
                cnt = Math.max(cnt, open+close);
        }
        return cnt;
    }

    //16. Minimum Number of Swaps to Make the String Balanced -> https://leetcode.com/problems/minimum-number-of-swaps-to-make-the-string-balanced/
    public int minSwaps(String s) {
        int open =0, close = 0;
        for(char ch : s.toCharArray()){
            if(ch == '[')
                open++;
            else if(open > 0)
                open--;
            else
                close++;
        }

        return (close +1)/2;
    }
    //17. count and say
    public String countAndSay(int n) {
        if(n == 1)
            return "1";
        String ans = countAndSay(n-1);
        int len = ans.length();
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        for(int i=1;i<len;i++){
            if(ans.charAt(i-1) == ans.charAt(i))
                cnt++;
            else{
                sb.append(""+cnt).append(ans.charAt(i-1));
                cnt = 1;
            }
        }
        sb.append(""+cnt).append(ans.charAt(len-1));
        return sb.toString();
    }
    //18. Rabin Karp algorithm
    public static List< Integer > stringMatch(String text, String pat){
        // Write your code here.
        long mod = (int) 1e9 + 7;
        long base = 27;
        int n = text.length();
        int m = pat.length();
        long hashP = 0;
        long hashT = 0;

        long lastIndexHash = 1;
        //creating hash of pat
        for(int i=0;i<m;i++){
            long p = pat.charAt(i) - 'a';
            long t = text.charAt(i) - 'a';
            hashP = (hashP * base + p) % mod;
            hashT = (hashT * base + t) % mod;

            if(i < m-1)
                lastIndexHash = (lastIndexHash * base) % mod;
        }

        List<Integer> res = new ArrayList<>();

        if(hashP == hashT)
            res.add(1);

        for(int i=m;i<n;i++){

            //removing old value
            hashT = hashT - (lastIndexHash * (text.charAt(i-m) - 'a') % mod);
            if(hashT < 0)
                hashT += mod;
            hashT = (hashT * base + (text.charAt(i) - 'a')) % mod;


            if(hashT == hashP)
                res.add(i-m+2);

        }
        return res;
    }
    //19. 686. Repeated String Match using rabin carp
    public int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder(a);

        int cnt = 1;
        while(sb.length() < b.length()){
            sb.append(a);
            cnt++;
        }

        if(find(sb.toString(), b)){
            return cnt;
        }
        sb.append(a);
        cnt++;
        if(find(sb.toString(), b))
            return cnt;
        return -1;
    }

    boolean find(String text, String pat){
        int n = text.length();
        int m = pat.length();
        long base = 27;
        long mod = (long) 1e9 + 7;

        long hashP = 0;
        long hashT = 0;
        long lastIndexHash = 1;

        for(int i=0;i<m;i++){
            hashP = (hashP * base + (pat.charAt(i)-'a')) % mod;
            hashT = (hashT * base + (text.charAt(i) - 'a')) % mod;

            if(i < m-1)
                lastIndexHash = (lastIndexHash * base) %mod;
        }

        if(hashT == hashP) //For suriety, we can add extra check here.
            return true;

        for(int i=m;i<n;i++){
            long chOut = text.charAt(i-m) - 'a';
            hashT = (hashT - (chOut * lastIndexHash) % mod);
            if(hashT < 0)
                hashT += mod;

            long chIn = text.charAt(i) - 'a';
            hashT = (hashT * base + chIn) % mod;
            if(hashT == hashP) // For suriety, we can add extra check here.
                return true;
        }
        return false;
    }
    //20. z-algorithm

    //21. KMP algorithm
    public int findMatching(String text, String pat) {
        int n = text.length();
        int m = pat.length();

        if(m > n)
            return -1;

        int[] lps = getLps(pat);

        int i = 0, j = 0;

        while(i < n && j < m){
            if(text.charAt(i) == pat.charAt(j)){
                i++;
                j++;
            }else{
                if(j == 0)
                    i++;
                else
                    j = lps[j-1];
            }
        }
        return j == m ? i-j : -1;
    }

    int[] getLps(String pat){

        int m = pat.length();
        int[] lps = new int[m];

        int i= 1, prevLps = 0;

        while(i < m){
            if(pat.charAt(i) == pat.charAt(prevLps)){
                lps[i] = prevLps + 1;
                i++;
                prevLps++;
            }else if( prevLps == 0){
                lps[i] = 0;
                i++;
            }else
                prevLps = lps[prevLps -1];
        }
        return lps;
    }

    //22. Shortest Palindrome using rabin karp
    public String shortestPalindrome(String s) {
        int mod = (int) 1e9 + 7;
        int n = s.length();
        long prefix = 0, suffix = 0;
        int lastIndex = -1;
        int base = 29;
        long power = 1;

        String rev = new StringBuilder(s).reverse().toString();

        for(int i=0;i<n;i++){
            int ch = s.charAt(i) - 'a' +1;
            prefix = (prefix * base) % mod;
            prefix = (prefix + ch) % mod;

            suffix = (suffix + ch * power) % mod;
            power = (power * base)%mod;

            if(prefix == suffix)
                lastIndex = i;
        }
        return new StringBuilder(s.substring(lastIndex+1)).reverse().toString() + s;
    }
    //---------------------------------------- USing KMP Algorithm ----------------
    public String shortestPalindromee(String s) {
        String str = s + "#" + new StringBuilder(s).reverse();
        int n = str.length();
        int[] lps = getLps(str, n);

        return new StringBuilder(s.substring(lps[n-1])).reverse() + s;
    }
    int[] getLps(String s, int n){
        int[] lps = new int[n];

        int i = 1, prevLps = 0;

        while(i < n){
            if(s.charAt(i) == s.charAt(prevLps)){
                lps[i] = prevLps + 1;
                prevLps += 1;
                i++;
            }else if(prevLps == 0){
                lps[i] = 0;
                i++;
            }else{
                prevLps = lps[prevLps-1];
            }
        }
        return lps;
    }

    //23. Longest Happy prefix
    public String longestPrefix(String s) {
        int n = s.length();
        int[] lps = getLpss(s, n);
        int size = lps[n-1];
        return s.substring(0, size);
    }
    int[] getLpss(String s, int n){
        int[] lps = new int[n];
        int i = 1, prevLps = 0;

        while(i < n){
            if(s.charAt(i) == s.charAt(prevLps)){
                lps[i] = prevLps + 1;
                prevLps += 1;
                i++;
            }else if(prevLps == 0){
                lps[i] = 0;
                i++;
            }else{
                prevLps = lps[prevLps-1];
            }
        }
        return lps;
    }
    //24. Count Palindromic Subsequences
    int countPS(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int[] cols: dp)
            Arrays.fill(cols, -1);
        return solve(s, 0, n-1, dp);

    }
    int solve(String s, int i, int j, int[][] dp){
        if(i == j)
            return 1;
        if(i > j)
            return 0;
        if(dp[i][j] != -1) return dp[i][j];

        if(s.charAt(i) == s.charAt(j)){
            return dp[i][j] = 1 + solve(s, i+1, j, dp) + solve(s, i, j-1, dp);
        }else
            return dp[i][j] = solve(s, i+1, j, dp) + solve(s, i, j-1, dp) - solve(s, i+1, j-1, dp);

    }

}