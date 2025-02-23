
/*
    Striver A to Z sheet
 */


import java.util.*;

public class Array {
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

    static int partition(int[] arr, int low, int high) {
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
        int count = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i] < nums[i-1])
                count++;
        }
        if(nums[0] < nums[nums.length-1]) count ++;
        return count <= 1;
    }
    //3. Remove Duplicates from Sorted Array II
    public int removeDuplicates(int[] nums) {

        int n = nums.length;
        if(n <= 2)
            return n;
        int i = 2;
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
    // follow up -> all num appears 3 time just one num appears 1 time
    public int singleNumber(int[] nums) {
        int first = 0, second = 0;

        for (int num : nums) {
            first = (first ^ num) & ~second;
            second = (second ^ num) & ~first;
        }
        return first;
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
        int sum =0, n = nums.length, count = 0;
        mp.put(sum, 1);

        for (int num : nums) {
            sum += num;
            if (mp.containsKey(sum - k))
                count += mp.get(sum - k);
            mp.put(sum, mp.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
    //24. pascals triangle
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> temp = new ArrayList<>();
            for(int j=0;j<=i;j++){

                if(j == 0 || j == i){
                    temp.add(1);
                }else{
                    int val = res.get(i-1).get(j-1)+ res.get(i-1).get(j);
                    temp.add(val);
                }
            }
            res.add(temp);
        }
        return res;
    }
    //25. Majority element (n/3)
    public List<Integer> majorityElement2(int[] nums) {
        int ele1 = Integer.MIN_VALUE, ele2 = Integer.MIN_VALUE;
        int count1 = 0, count2 = 0, n = nums.length;
        for(int i=0;i<n;i++){

            if(ele1 == nums[i]){
                count1++;
            }else if(ele2 == nums[i]){
                count2++;
            }else if(count1 == 0){
                ele1 = nums[i];
                count1++;
            }else if(count2 == 0){
                ele2 = nums[i];
                count2++;
            }else{
                count1 --;
                count2 --;
            }
        }

        List<Integer> res = new ArrayList<>();
        count1 = 0;
        count2 = 0;
        for(int i=0;i<n;i++){
            if(nums[i] == ele1)
                count1++;
            if(nums[i] == ele2)
                count2++;
        }

        if(count1 > n/3)
            res.add(ele1);
        if(count2 > n/3)
            res.add(ele2);
        return res;
    }
    //26. 3 sum (three sum)
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for(int i=0;i<n;i++){
            if( i != 0 && nums[i] == nums[i-1])
                continue;

            int j = i+1, k = n-1;
            while(j<k){
                if(j != i+1 && nums[j] == nums[j-1]){
                    j++;
                    continue;
                }

                int sum = nums[i] + nums[j] + nums[k];
                if(sum == 0){
                    res.add(List.of(nums[i], nums[j], nums[k]));
                    j++;
                }else if(sum > 0){
                    k--;
                }else
                    j++;
            }
        }
        return res;
    }
    //27. 4 sum ( four sum)
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for(int i=0;i<n;i++){
            if(i != 0 && nums[i] == nums[i-1])
                continue;

            for(int j=i+1;j<n;j++){
                if(j != i+1 && nums[j] == nums[j-1])
                    continue;

                int l =j+1, h = n-1;

                while(l < h){
                    if(l != j+1 && nums[l] == nums[l-1]){
                        l++;
                        continue;
                    }
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[h];

                    if(sum == target){
                        res.add(List.of(nums[i], nums[j], nums[l], nums[h]));

                        l++;
                    }else if(sum > target){
                        h --;
                    }else
                        l++;
                }
            }
        }
        return res;
    }
    //28. Largest Subarray with 0 Sum
    int maxLen(int arr[]) {
        Map<Integer,Integer> mp = new HashMap<>();
        mp.put(0,-1);
        int res = 0, sum = 0;
        for(int i=0;i<arr.length;i++){
            sum += arr[i];
            if(mp.containsKey(sum)){
                res = Math.max(i- mp.get(sum), res);
            }else
                mp.put(sum, i);
        }
        return res;
    }
    //29. Count number of subarrays with given xor equals to K
    public int solve(int[] A, int B) {
        Map<Integer,Integer> mp = new HashMap<>();
        int xor = 0, count = 0;
        mp.put(xor, 1);
        for(int i=0;i<A.length;i++){
            xor ^= A[i];

            if(mp.containsKey(xor ^ B)){
                count += mp.get(xor ^ B);
            }

            mp.put(xor, mp.getOrDefault(xor, 0)+1);
        }
        return count;
    }
    //30. Merge Intervals
    public List<List<Integer>> merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int start = intervals[0][0];
        int end = intervals[0][1];
        List<List<Integer>> res = new ArrayList<>();

        for(int i=0;i<intervals.length;i++){

            if(intervals[i][0] <= end){
                end = Math.max(end, intervals[i][1]);
            }else{
                res.add(List.of(start, end));
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        res.add(List.of(start, end));
        return res;
    }
    //31. Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int k = m + n - 1;
        int i = m-1, j = n-1;
        while(i>=0 && j>=0){
            if(nums1[i]>= nums2[j]){
                nums1[k--] = nums1[i--];
            }else{
                nums1[k--] = nums2[j--];
            }
        }

        while(i>=0){
            nums1[k--] = nums1[i--];
        }

        while(j>=0){
            nums1[k--] = nums2[j--];
        }
    }
    // 32. Missing and Repeating number
    ArrayList<Integer> findTwoElement(int arr[]) {
        int n = arr.length;
        int xor = 0;

        for(int i=0;i<n;i++){
            xor = xor ^ arr[i];
            xor = xor ^ (i+1);
        }

        int bit = xor & ~(xor-1);
        int val1 =0, val2 =0;

        for(int i=0;i<n;i++){
            if((bit & arr[i]) > 0)
                val1 = val1 ^ arr[i];
            else
                val2 = val2 ^ arr[i];

            if((bit & (i+1)) > 0)
                val1 = val1 ^ (i+1);
            else
                val2 = val2 ^ (i+1);
        }

        int val1Count =0;
        for(int i=0;i<n;i++){
            if(val1 == arr[i])
                val1Count++;
        }
        ArrayList<Integer> res = new ArrayList<>();
        if(val1Count >0){
            res.add(val1);
            res.add(val2);
        }else{
            res.add(val2);
            res.add(val1);
        }
        return res;
    }
    //33. Inversion count in array
    int inversionCount(int arr[]) {

        return mergeSort1(arr, 0, arr.length-1);
    }
    int mergeSort1(int[] arr, int l, int h){
        if(l >= h)
            return 0;
        int mid = (l+h)/2;
        int count = 0;
        count += mergeSort1(arr, l, mid);
        count += mergeSort1(arr, mid+1, h);
        count += merge1(arr, l, mid, h);
        return count;
    }
    int merge1(int[] arr, int l, int m, int h){

        int[] temp = new int[h-l+1];
        int l1 = l, m1 = m+1;
        int k =0;
        int count = 0;
        while(l1 <= m && m1 <= h){
            if(arr[l1] <= arr[m1]){
                temp[k++] = arr[l1++];
            }else{
                count += m - l1+1;
                temp[k++] = arr[m1++];
            }
        }

        while(l1 <= m){
            temp[k++] = arr[l1++];
        }
        while(m1 <= h){
            temp[k++] = arr[m1++];
        }

        for(int i=l;i<=h;i++){
            arr[i] = temp[k++];
        }
        return count;
    }
    //34. reverse pairs
    public int reversePairs(int[] nums) {
        return mergeSort2(nums, 0, nums.length - 1);
    }
    private int mergeSort2(int[] nums, int left, int right) {
        if (left >= right)
            return 0;
        int mid = (left + right) / 2;
        int count = 0;
        count += mergeSort2(nums, left, mid);
        count += mergeSort2(nums, mid + 1, right);
        count += merge2(nums, left, mid, right);
        return count;
    }
    private int merge2(int[] nums, int left, int mid, int right) {
        List<Integer> list = new ArrayList<>();

        int l = left, r = mid + 1;
        int count = 0;

        while (l <= mid && r <= right) {
            if (nums[l] > (long) 2 * nums[r]) {
                count += (mid - l + 1);
                r++;
            } else
                l++;
        }

        l = left;
        r = mid + 1;
        while (l <= mid && r <= right) {

            if (nums[l] <= nums[r]){
                list.add(nums[l++]);
            }else {
                list.add(nums[r++]);
            }
        }

        while (l <= mid)
            list.add(nums[l++]);
        while (r <= right)
            list.add(nums[r++]);

        for (int i = left; i <= right; i++) {
            nums[i] = list.get(i - left);
        }
        return count;
    }
    //35. Maximum Product Subarray
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int res = Integer.MIN_VALUE;
        int pre = 1;

        for(int i=0;i<n;i++){
            pre *= nums[i];
            res = Math.max(res, pre);
            if(pre == 0)
                pre = 1;
        }

        int suf = 1;
        for(int i= n-1;i>=0;i--){
            suf *= nums[i];
            res = Math.max(res, suf);
            if(suf == 0)
                suf =1;
        }
        return res;
    }
}


