
/*
    Striver A to Z sheet
 */


import java.util.*;

public class Arrays {
    public static void main(String[] args) {


    }

    // Sorting algorithms ------------------------------------------------------------

    // 1. Merge Sort----------
    void mergeSort(int arr[], int l, int r) {
        // code here
        if(l< r){
            int m = (l+r)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m+1, r);
            merge(arr, l, m, r);
        }
    }

    void merge(int[] arr, int l, int m, int r){
        int[] temp = new int[r-l+1];
        int l1 =l, m1 = m+1, k=0;

        while(l1 <= m && m1 <=r){
            if(arr[l1] <= arr[m1]){
                temp[k++] = arr[l1++];
            }else{
                temp[k++] = arr[m1++];
            }
        }
        while(l1 <= m){
            temp[k++] = arr[l1++];
        }
        while(m1 <=r){
            temp[k++] = arr[m1++];
        }

        k=0;
        for(int i=l;i<=r;i++){
            arr[i] = temp[k++];
        }
    }

    // 2. Quick sort-------
    static void quickSort(int arr[], int low, int high) {
        // code here
        if(low < high){
            int index = partition(arr, low, high);
            quickSort(arr, low, index-1);
            quickSort(arr, index+1, high);
        }
    }

    static int partition(int arr[], int low, int high) {
        // your code here

        int pivot = arr[low];

        int i = low, j = high;

        while(i < j){

            while(arr[i] <= pivot && i <= high-1)
                i++;

            while(arr[j] >= pivot && j >= low+1)
                j--;

            if(i<j){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        arr[low] = arr[j];
        arr[j] = pivot;
        return j;
    }

    //Arrays -----------------------------------------------------------------------------------------------------------
    //1. second largest element in an array
    public int getSecondLargest(int[] arr) {
        int firstLargest = -1;
        int secondLargest = -1;

        for(int i=0;i<arr.length;i++){
            if(arr[i] > firstLargest){
                secondLargest = firstLargest;
                firstLargest = arr[i];
            }else if(arr[i] > secondLargest && arr[i] < firstLargest){
                secondLargest = arr[i];
            }
        }
        return secondLargest;
    }

    //2. check if array is sorted and rotated
    public boolean check(int[] nums) {
        int val = nums[0], count = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i] < val)
                count++;
            val = nums[i];
        }
        if(nums[0] < nums[nums.length-1]) count ++;
        return count <= 1;
    }
    //3. Remove Duplicates from Sorted Array II
    public int removeDuplicates(int[] nums) {

        int n =nums.length;
        if(n<=2)
            return n;
        int i=2;
        for(int j=2;j<n;j++){
            if(nums[j] != nums[i-2])
                nums[i++] = nums[j];
        }
        return i;
    }
    //4.Left rotate an array by D places
    public void rotate(int[] nums, int k) {

        int n = nums.length;
        k = k%n;
        reverse(nums, 0, n-k-1);
        reverse(nums, n-k, n-1);
        reverse(nums, 0, n-1);
    }
    void reverse(int[] nums, int i, int j){
        while(i<j){
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    //5. move zeros to the end
    public void moveZeroes(int[] nums) {
        int i=0;
        for(int j=0;j<nums.length;j++){
            if(nums[j] != 0){
                nums[i++] = nums[j];
            }

            if(i -1 != j)
                nums[j] = 0;
        }
    }
    //6. union of two arrays
    public ArrayList<Integer> findUnion(int a[], int b[]) {
        // add your code here
        ArrayList<Integer> arr = new ArrayList<>();

        int i=0, j=0, n = a.length, m = b.length;

        while(i<n && j<m){

            int val;

            if(a[i] == b[j]){
                val = a[i];
                i++;
                j++;
            }else if(a[i] < b[j]){
                val = a[i];
                i++;
            }else{
                val = b[j];
                j++;
            }

            if(arr.isEmpty() || arr.get(arr.size()-1) != val)
                arr.add(val);
        }

        while(i<n){
            if(arr.isEmpty() || arr.get(arr.size()-1) != a[i])
                arr.add(a[i]);
            i++;
        }

        while(j<m){
            if(arr.isEmpty() || arr.get(arr.size()-1) != b[j])
                arr.add(b[j]);
            j++;
        }
        return arr;
    }
    //7. missing number form 1 to n numbers
    public int missingNumber(int[] nums) {
        int n= nums.length;
        int xor = 0 ;
        for(int i=0;i<n;i++){
            xor ^= (i+1);
            xor ^= nums[i];
        }
        return xor;
    }
    //8. max consecutive ones III
    public int longestOnes(int[] nums, int k) {
        int zeroCount = 0, i = 0;
        int maxCount = 0;

        for(int j=0;j<nums.length;j++){
            if(nums[j] == 0)
                zeroCount++;

            while(zeroCount > k){
                if(nums[i++] == 0)
                    zeroCount--;
            }
            maxCount = Math.max(j-i+1, maxCount);
        }
        return maxCount;
    }

    //9. Longest subarray with given sum
    public int lenOfLongestSubarr(int[] arr, int k) {
        // code here
        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(0, -1);
        int n = arr.length, sum = 0, res = -1;

        for(int i=0;i<n;i++){
            sum += arr[i];

            if(mp.containsKey(sum-k)){
                res = Math.max(res, i-mp.get(sum-k));
            }
            if(!mp.containsKey(sum))
                mp.put(sum, i);
        }
        return res;
    }

    //10. 2 sum problem return indices of them
    public int[] twoSum(int[] nums, int target) {

        Map<Integer,Integer> mp = new HashMap<>();

        for(int i=0;i<nums.length;i++){

            if(mp.containsKey(target - nums[i])){
                return new int[]{mp.get(target-nums[i]), i};
            }
            mp.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
    //11. Sort an array of 0's 1's and 2's
    public void sortColors(int[] nums) {

        int i=0, j=0, k=nums.length-1;

        while(j<=k){

            if(nums[j] == 0){
                swap(nums, i, j);
                i++;j++;
            }else if(nums[j] == 1)
                j++;
            else{
                swap(nums, j, k);
                k--;
            }
        }

    }
    //12. Majority Element (>n/2 times)
    public int majorityElement(int[] nums) {
        int count = 1, ele = nums[0];
        int n = nums.length;

        for(int i=1;i<n;i++){
            if(count == 0){
                ele = nums[i];
                count = 1;
            }else if(nums[i] == ele){
                count++;
            }else{
                count--;
            }
        }
        return ele;
    }
    //13. Kadane's Algorithm, maximum subarray sum
    public int maxSubArray(int[] nums) {
        int sum = 0, maxSum = Integer.MIN_VALUE;

        for(int i=0;i<nums.length;i++){
            sum += nums[i];
            if(sum < nums[i]){
                sum = nums[i];
            }
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }
    //14. buy and sell stock II (can buy any number of times)
    public int maxProfit(int[] prices) {
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
    //15. buy and sell stock III (can buy only 2)
    public int maxProfit3i(int[] prices) {
        int n = prices.length;
        int buy1= prices[0], buy2 = prices[n-1];
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = 0;right[n-1] = 0;

        for(int i=1, j = n-2;i<n && j>=0;i++, j--){
            buy1 = Math.min(buy1, prices[i]);
            left[i] = Math.max(left[i-1], prices[i] -  buy1);

            buy2 = Math.max(buy2, prices[j]);
            right[j] = Math.max(right[j+1], buy2 - prices[j]);
        }
        int res = 0;
        for(int i=0;i<n;i++){
            res = Math.max(left[i]+right[i], res);
        }
        return res;
    }
  //---------------  OR ----------------
    public int maxProfit3(int[] prices) {
        int[][] dp = new int[prices.length][4];
        for(int[] arr : dp)
            java.util.Arrays.fill(arr, -1);
        return solve(0, 0, prices.length, prices, dp);
    }
    int solve(int index, int tran, int n, int[] prices, int[][] dp){
        if(index == n || tran == 4)
            return 0;

        if(dp[index][tran] != -1)
            return dp[index][tran];

        if(tran % 2 == 0){
            int buy = - prices[index] + solve(index+1, tran+1, n, prices, dp);
            int notBuy = 0 + solve(index+1, tran, n, prices, dp);
            return dp[index][tran] = Math.max(buy, notBuy);
        }else{
            int sell = prices[index] + solve(index+1, tran+1, n, prices, dp);
            int notSell = 0 + solve(index+1, tran, n, prices, dp);
            return dp[index][tran] = Math.max(sell, notSell);
        }
    }
    //16. Rearrange the array in alternating positive and negative items
    public int[] rearrangeArray(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n];

        int posIndex =0, negIndex = 1;

        for(int i=0;i<n;i++){

            if(nums[i] >0){
                arr[posIndex] = nums[i];
                posIndex +=2;
            }else{
                arr[negIndex] = nums[i];
                negIndex +=2;
            }
        }
        return arr;
    }
    //17. Next Permutation
    public void nextPermutation(int[] nums) {
        int index1 = -1, index2 = -1;
        int n = nums.length;

        for(int i= n-2;i>=0;i--){
            if(nums[i] < nums[i+1]){
                index1 = i;
                break;
            }
        }

        if(index1 == -1){
            reverse(nums, 0, n-1);
            return;
        }

        for(int i=n-1;i>=0;i--){
            if(nums[i] > nums[index1]){
                index2 = i;
                break;
            }
        }
        swap(nums, index1, index2);
        reverse(nums, index1+1, n-1);
    }
    //18. Array Leaders (Next greater element from right)
    static ArrayList<Integer> leaders(int arr[]) {
        // code here
        Stack<Integer> st = new Stack<>();
        ArrayList<Integer> res = new ArrayList<>();
        for(int i=arr.length-1;i>=0;i--){

            while(!st.isEmpty() && st.peek() <= arr[i])
                st.pop();

            if(st.isEmpty())
                res.add(arr[i]);
            st.add(arr[i]);
        }

        Collections.reverse(res);
        return res;
    }

    //19. Longest Consecutive Sequence in an Array
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> hs = new HashSet<>();
        int res  =0 ;

        for(int i=0;i<nums.length;i++){
            hs.add(nums[i]);
        }
        for(int i=0;i<nums.length;i++){

            if(!hs.contains(nums[i]+1)){
                int val = nums[i];
                int count = 0;
                while(hs.contains(val--))
                    count++;
                res = Math.max(res, count);
            }
        }
        return res;
    }
    //20. set matrix zero
    public void setZeroes(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int col = 1;

        for(int i=0;i<n;i++){
            if(mat[i][0] == 0)
                col = 0;
            for(int j=1;j<m;j++){
                if(mat[i][j] == 0){
                    mat[i][0] = 0;
                    mat[0][j] = 0;
                }
            }
        }
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=1;j--){
                if(mat[i][0] == 0 || mat[0][j] == 0)
                    mat[i][j] = 0;
            }

            if(col == 0)
                mat[i][0] = 0;
        }
    }
    //21. rotate matrix 90 degree
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for(int i=0;i<n;i++){
            for(int j=0;j<=i;j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for (int[] arr : matrix) {
            reverse(arr, 0, n - 1);
        }
    }
    //22. Print spiral matrix
    public List<Integer> spiralOrder(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int down = 0, right = m-1;
        int up = n-1, left = 0;
        List<Integer> res = new ArrayList<>();

        while(down <= up && left <= right){

            for(int i=left;i<=right;i++){
                res.add(mat[down][i]);
            }
            down++;

            for(int i=down; i<= up;i++){
                res.add(mat[i][right]);
            }
            right--;

            if(up < down)
                break;

            for(int i=right;i>= left;i--){
                res.add(mat[up][i]);
            }
            up--;

            if(right < left)
                break;

            for(int i=up;i>=down;i--){
                res.add(mat[i][left]);
            }
            left++;
        }
        return res;
    }
    //23. count number of sun arrays with given sum
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> mp = new HashMap<>();
        int sum =0, n = nums.length;
        mp.put(0, 1);
        int count =0;

        for (int num : nums) {
            sum += num;
            if (mp.containsKey(sum - k))
                count += mp.get(sum - k);
            mp.put(sum, mp.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    //4 Binary Search --------------------------------------------------------------------------------------------------
    
    //Koko eating banana-------
    public int minEatingSpeed(int[] piles, int h) {

        long low =1, high = java.util.Arrays.stream(piles)
                .asLongStream().reduce(0, Long::sum);
        long res = -1;

        while(low <=high){
            long mid = (low + high)/2;

            if(isPossible(piles, mid, h)){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return (int)res;
    }

    boolean isPossible(int[] piles, long mid, int h){
        long count =0;
        for (int pile : piles) {

            count = count + (long) pile / mid;
            if (pile % mid != 0)
                count++;
        }
        return count<=h;
    }

    //minimum number of days to make m banquets---------
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if((long) m * k > (long)n)
            return -1;

        int low = 1, high = java.util.Arrays.stream(bloomDay).reduce(0, Integer::max);

        int res =-1;
        while(low <= high){
            int mid = (low+high)/2;

            if(isPossible(bloomDay, m,k,mid)){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return res;
    }

    boolean isPossible(int[] arr, int m, int k, int mid){
        int count =0, totalCount =0;
        int n = arr.length;

        for(int j=0;j<n;j++){

            if(arr[j] <= mid)
                count++;
            else
                count = 0;

            if(count == k){
                count = 0;
                totalCount++;
            }
        }
        return totalCount >= m;
    }

    //Capacity to ship package withing D days------

    public int shipWithinDays(int[] weights, int days) {
        long low =1, high = java.util.Arrays.stream(weights)
                .asLongStream().sum();
        long res =-1;
        while(low <=high){
            long mid = (low+high)/2;

            if(isPossible(weights, days, mid)){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return (int)res;
    }

    boolean isPossible(int[] arr, int days, long limit){
        int count =1;
        long sum =0;

        for(int i=0;i<arr.length;i++){

            sum += arr[i];
            if(arr[i] > limit)
                return false;
            if(sum > limit){
                count++;
                sum = arr[i];
            }
        }
        return count <= days;
    }

    

}


