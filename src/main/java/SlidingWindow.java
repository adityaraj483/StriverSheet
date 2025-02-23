import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SlidingWindow {
    public static void main(String[] args) {    }
    //1. Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if(n <= 1)
            return n;

        int i = 0, j = 0;
        int res = 1;
        Map<Character, Integer> mp = new HashMap<>();
        while(j < n){
            char ch = s.charAt(j);

            while(mp.containsKey(ch)){
                char ch1 = s.charAt(i);
                mp.put(ch1, mp.getOrDefault(ch1, 1)-1);
                if(mp.get(ch1) == 0){
                    mp.remove(ch1);
                }
                i++;
            }
            mp.put(ch, mp.getOrDefault(ch, 0)+1);
            j++;
            res = Math.max(res, j-i);
        }
        return res;
    }
    //2. Max Consecutive Ones III
    public int longestOnes(int[] nums, int k) {
        int i=0, j=0;
        int n = nums.length;
        int one =0, zero = 0, res = 0;
        while(j < n){
            if(nums[j] == 1)
                one++;
            else
                zero++;

            while(zero > k){
                if(nums[i] == 0)
                    zero--;
                else
                    one--;
                i++;
            }
            j++;
            res = Math.max(res, one+zero);
        }
        return res;
    }
    //3. Fruit Into Baskets || Find length of the longest subarray containing atmost two distinct integers
    public int totalElements(Integer[] arr) {
        int n = arr.length;
        int i=0, j=0;
        int res = 1;
        Map<Integer, Integer> mp = new HashMap<>();

        while(j<n){

            mp.put(arr[j], mp.getOrDefault(arr[j], 0)+1);

            while(mp.size() > 2){
                mp.put(arr[i], mp.getOrDefault(arr[i], 1)-1);
                if(mp.get(arr[i]) == 0)
                    mp.remove(arr[i]);
                i++;
            }
            j++;
            res = Math.max(res, j-i);
        }
        return res;
    }
    //4. longest repeating character replacement
    public int characterReplacement1(String s, int k) {
        int i=0, j=0;
        int n = s.length();
        int res = 0;
        int max = 0;
        Map<Character, Integer> mp = new HashMap<>();
        while(j<n){
            char ch = s.charAt(j);
            mp.put(ch, mp.getOrDefault(ch, 0)+1);
            max = Math.max(max, mp.get(ch));

            while((j-i+1) - max > k){

                char ch1 = s.charAt(i);
                mp.put(ch1, mp.getOrDefault(ch1, 1)-1);
                if(mp.get(ch1) == 0)
                    mp.remove(ch1);
                i++;
                max = getMaxFre(mp);
            }
            j++;
            res = Math.max(res, j-i);
        }
        return res;
    }
    int getMaxFre(Map<Character, Integer> mp){
        int max = 0;
        for(Map.Entry<Character, Integer> entry : mp.entrySet())
            max = Math.max(max, entry.getValue());
        return max;
    }
    // ----------------------OR----------------------
    public int characterReplacement2(String s, int k) {
        int i=0, j=0;
        int n = s.length();
        int res = 0;
        int max = 0;
        Map<Character, Integer> mp = new HashMap<>();
        while(j<n){
            char ch = s.charAt(j);
            mp.put(ch, mp.getOrDefault(ch, 0)+1);
            max = Math.max(max, mp.get(ch));

            if((j-i+1) - max > k){

                char ch1 = s.charAt(i);
                mp.put(ch1, mp.getOrDefault(ch1, 1)-1);
                if(mp.get(ch1) == 0)
                    mp.remove(ch1);
                i++;
            }
            j++;
            res = Math.max(res, j-i);
        }
        return res;
    }
    //5. Binary subarray with sum
    public int numSubarraysWithSum(int[] nums, int goal) {
        int n = nums.length;
        Map<Integer, Integer> mp = new HashMap<>();
        int sum = 0, cnt = 0;
        //mp.put(sum, 1); // if they include empty subarray the this conditions adds
        for(int i=0;i<n;i++){
            sum += nums[i];
            if(sum == goal)
                cnt++;
            if(mp.containsKey(sum - goal)){
                cnt += mp.get(sum - goal);
            }

            mp.put(sum, mp.getOrDefault(sum, 0)+1);
        }
        return cnt;
    }
    //6. Count Number of Nice Subarrays
    public int numberOfSubarrays(int[] nums, int k) {
        nums = Arrays.stream(nums).map(a -> a % 2).toArray();
        Map<Integer, Integer> mp = new HashMap<>();
        int sum = 0, cnt = 0;
        for(int i=0;i<nums.length;i++){
            sum += nums[i];
            if(sum == k)
                cnt ++;
            if(mp.containsKey(sum-k)){
                cnt += mp.get(sum-k);
            }
            mp.put(sum, mp.getOrDefault(sum, 0)+1);
        }
        return cnt;
    }
    //7. Number of Substrings Containing All Three Characters
    public int numberOfSubstrings(String s) {
        Map<Character, Integer> mp = new HashMap<>();
        int i=0, j=0;
        int n = s.length();
        int cnt = 0;
        while(j<n){
            char ch = s.charAt(j);
            mp.put(ch, mp.getOrDefault(ch, 0)+1);

            while(mp.size() ==3){
                cnt += n-j;
                char ch1 = s.charAt(i);
                mp.put(ch1, mp.get(ch1)-1);
                if(mp.get(ch1) == 0)
                    mp.remove(ch1);
                i++;
            }
            j++;
        }
        return cnt;
    }
    //8. Maximum Points You Can Obtain from Cards
    public int maxScore(int[] cardPoints, int k) {
        int total = Arrays.stream(cardPoints).sum();
        int n = cardPoints.length;
        if(k == n)
            return total;

        int minSum = (int) 1e9;
        int sum = 0;
        int k1 = n-k;

        for(int i=0;i<n;i++){
            sum += cardPoints[i];

            if(i >= k1-1){
                minSum = Math.min(minSum, sum);
                sum -= cardPoints[i-k1+1];
            }
        }
        return total - minSum;
    }
    //9. Longest Substring with K Distinct Characters
    public int longestkSubstr(String s, int k) {
        int i=0, j=0,n = s.length();
        Map<Character, Integer> mp = new HashMap<>();
        int res = 0;

        while(j<n){
            char ch = s.charAt(j);
            mp.put(ch, mp.getOrDefault(ch, 0)+1);

            if(mp.size() > k){
                mp.put(s.charAt(i), mp.get(s.charAt(i))-1);
                if(mp.get(s.charAt(i)) == 0)
                    mp.remove(s.charAt(i));
                i++;
            }
            j++;
            if(mp.size() == k)
                res = Math.max(res, j-i);
        }

        return res == 0 ? -1 : res;
    }
    //10. Subarrays with K Different Integers
    public int subarraysWithKDistinct(int[] nums, int k) {
        return solve(nums, k) - solve(nums, k-1);
    }
    int solve(int[] nums, int k){
        int i=0, j=0;
        int res = 0;
        int n = nums.length;
        Map<Integer, Integer> mp = new HashMap<>();
        while(j<n){
            mp.put(nums[j], mp.getOrDefault(nums[j], 0)+1);

            while(mp.size() > k){
                mp.put(nums[i], mp.get(nums[i]) -1);
                if(mp.get(nums[i]) == 0)
                    mp.remove(nums[i]);
                i++;
            }
            j++;
            res += j-i;
        }
        return res;
    }
    //11. Minimum Window Substring
    public String minWindow(String s, String t) {
        int start =-1, len = (int) 1e9;
        int i=0, j=0;
        int n = s.length();
        int m = t.length();

        Map<Character, Integer> mp = new HashMap<>();
        for(char ch : t.toCharArray())
            mp.put(ch, mp.getOrDefault(ch,0)+1);

        int cnt = 0;
        while(j<n){
            char ch = s.charAt(j);
            if(mp.containsKey(ch) && mp.get(ch) > 0)
                cnt++;
            mp.put(ch, mp.getOrDefault(ch, 0)-1);

            while(cnt == m){

                if(len > j-i+1){
                    len = j-i+1;
                    start = i;
                }
                mp.put(s.charAt(i), mp.get(s.charAt(i))+1);
                if(mp.get(s.charAt(i)) > 0)
                    cnt--;
                i++;
            }
            j++;
        }
        if(start >=0)
            return s.substring(start, start+len);
        return "";
    }
    //12. minimum window subsequences


}
