import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {

    }

    //Binary Search --------------------------------------------------------------------------------------------------
    //1. Binary search
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length-1;
        while(low <= high){
            int mid = (low + high)/2;
            if(nums[mid] == target)
                return mid;
            else if(nums[mid] < target)
                low = mid+1;
            else
                high = mid-1;
        }
        return -1;
    }
    //2. lower bound
    static int findFloor(int[] arr, int k) {
        int low = 0, high = arr.length-1;

        while(low <= high){
            int mid = (low + high)/2;
            if(arr[mid] <= k){
                low = mid+1;
            }else{
                high = mid-1;
            }
        }
        return high;
    }
    //3. upper bound
    int upperBound(int[] arr, int low, int high, int x){
        while(low <= high){
            int mid = (low +high)/2;
            if(arr[mid] >= x)
                high = mid-1;
            else
                low = mid+1;
        }
        return low;
    }
    //4. Search Insert Position ( upper bound)
    public int searchInsert(int[] nums, int target) {
        int low = 0, high = nums.length-1;
        while(low <= high){
            int mid = (low +high)/2;
            if(nums[mid] >= target){
                high = mid -1;
            }else
                low = mid +1;
        }
        return low;
    }
    //5.floor and ceil in a sorted array
    public int[] getFloorAndCeil(int x, int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;

        int lbI = lowerBound(arr, 0, n-1, x);
        int ubI = upperBound1(arr, 0, n-1, x);

        int lbV = (lbI >= 0 && lbI < n ? arr[lbI] : -1);
        int upV = (ubI >= 0 && ubI < n ? arr[ubI] : -1);

        return new int[]{lbV, upV};
    }
    int lowerBound(int[] arr, int low, int high, int x){
        while(low <= high){
            int mid = (low +high)/2;
            if(arr[mid] <= x){
                low = mid+1;
            }else
                high = mid-1;
        }
        return high;
    }

    int upperBound1(int[] arr, int low, int high, int x){
        while(low <= high){
            int mid = (low +high)/2;
            if(arr[mid] >= x)
                high = mid-1;
            else
                low = mid+1;
        }
        return low;
    }
    //6. Find First and Last Position of Element in Sorted Array
    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int firstIndex = firstOccurance(nums, 0, n-1, target);
        int lastIndex = lastOccurance(nums, 0, n-1, target);

        if(firstIndex < 0 || firstIndex >= n || nums[firstIndex] != target)
            firstIndex = -1;
        if(lastIndex < 0 || lastIndex >= n || nums[lastIndex] != target)
            lastIndex = -1;
        return new int[]{firstIndex, lastIndex};

    }
    int firstOccurance(int[] nums, int low, int high, int target){
        while(low <= high){
            int mid = (low + high)/2;
            if(nums[mid] >= target){
                high = mid - 1;
            }else
                low = mid+1;
        }
        return low;
    }
    int lastOccurance(int[] nums, int low, int high, int target){
        while(low <= high){
            int mid = (low + high)/2;
            if(nums[mid] <= target){
                low = mid +1;
            }else
                high = mid -1;
        }
        return high;
    }
    //7. Count occurrences of a number in a sorted array with duplicates
    int countFreq(int[] arr, int target) {
        // code here
        int n = arr.length;
        int firstIndex = firstOccurance(arr, 0, n-1, target);
        int lastIndex = lastOccurance(arr, 0, n-1, target);
        if(lastIndex < firstIndex)
            return 0;
        return lastIndex - firstIndex+1;
    }

    //8. Search in Rotated Sorted Array
    public int searchInRotatedSorted(int[] nums, int target) {
        int low = 0, high = nums.length -1;
        while(low <= high){
            int mid = (low + high)/2;
            if(nums[mid] == target)
                return mid;
            else if(nums[low] <= nums[mid]){
                if(nums[low] <= target && target <= nums[mid])
                    high = mid-1;
                else
                    low = mid+1;
            }else{
                if(nums[mid] <= target && target <= nums[high])
                    low = mid+1;
                else
                    high = mid-1;
            }
        }
        return -1;
    }
    //9. Search in Rotated Sorted Array II ( with duplicate)
    public Boolean search2(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high = n-1;

        while(low <=high){
            int mid = (low+high)/2;

            if(nums[mid] == target)
                return true;

            if(nums[low] == nums[mid] && nums[mid] == nums[high]){
                low++;
                high--;
                continue;
            }

            if(nums[low] <= nums[mid]){
                if(nums[low] <= target && target <= nums[mid]){
                    high = mid-1;
                }else
                    low = mid+1;
            }else{
                if(nums[mid] <= target && target <= nums[high]){
                    low = mid+1;
                }else
                    high = mid-1;
            }
        }
        return false;
    }
    //10. Find Minimum in Rotated Sorted Array
    public int findMin(int[] nums) {
        int low = 0, high = nums.length-1;

        while(low < high){
            int mid = (low + high)/2;

            if(nums[low] <= nums[mid]){
                if(nums[mid] < nums[high])
                    high = mid;
                else
                    low = mid+1;
            }else{
                if(nums[high] < nums[mid])
                    low = mid+1;
                else
                    high = mid;
            }
        }
        return nums[low];
    }
    //11. How many times sorted array is rotated
    public int findKRotation(List<Integer> arr) {
        int low = 0, high = arr.size()-1;

        while(low < high){
            int mid = (low + high)/2;

            if(arr.get(low) <= arr.get(mid)){
                if(arr.get(mid) <= arr.get(high))
                    high = mid;
                else
                    low = mid+1;
            }else{
                if(arr.get(high) <= arr.get(mid))
                    low = mid+1;
                else
                    high = mid;
            }
        }
        return low;
    }
    //12. Single Element in a Sorted Array . eg [3,3,7,7,10,11,11] -> 10
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length -1 ;
        int n = nums.length;

        while(low <= high){
            int mid = (low + high)/2;
            if(mid % 2 == 0){
                if(mid+1 <n && nums[mid] == nums[mid+1])
                    low = mid+1;
                else
                    high = mid-1;
            }else{
                if(mid-1 >= 0 && nums[mid] == nums[mid-1])
                    low = mid+1;
                else
                    high = mid-1;
            }
        }
        return nums[low];
    }

    //13. Find Peak Element
    public int findPeakElement(int[] nums) {
        int low = 0, high = nums.length -1;

        while(low < high){
            int mid = (low + high)/2;
            if(nums[mid] > nums[mid+1])
                high = mid;
            else
                low = mid+1;
        }
        return low;
    }

    //14. Find square root of a number in log n
    int floorSqrt(int n) {
        // Your code here
        int low = 1, high = n;
        while(low <= high){
            int mid = (low + high)/2;
            if(mid * mid == n)
                return mid;
            else if(mid * mid > n)
                high = mid -1;
            else
                low = mid + 1;
        }
        return high;
    }
    //15.Find nth root of m
    public int nthRoot(int n, int m) {
        int low = 1, high = m;
        while(low <= high){
            int mid = (low +high)/2;
            long val = nTimesMid(mid, n);
            if(val == (long) m)
                return mid;
            else if(val > (long) m)
                high = mid -1;
            else
                low = mid +1;
        }
        return -1;
    }
    long nTimesMid(int mid, int n){
        long res = 1;
        for(int i=1;i<=n;i++){
            res *= mid;
        }
        return res;
    }
    //16. Koko eating bananas
    public int minEatingSpeed(int[] piles, int h) {
        long low = 1, high = Arrays.stream(piles).
                asLongStream().reduce(0, Long::sum);
        long res = -1;
        while(low <= high){

            long mid = (low + high)/2;
            if(isPossible1(piles, mid, h)){
                res = mid;
                high = mid - 1;
            }else
                low = mid+1;
        }
        return (int)res;
    }
    boolean isPossible1(int[] piles, long mid, int h){
        int count = 0;
        for(int val : piles){
            count += (int) Math.ceil((double)val/mid);
        }
        return count <= h;
    }
    //17. Minimum Number of Days to Make m Bouquets
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if((long) m * k > (long)n)
            return -1;
        int low = 1, high = java.util.Arrays.stream(bloomDay).reduce(0, Integer::max);

        int res =-1;
        while(low <= high){
            int mid = (low+high)/2;

            if(isPossible2(bloomDay, m,k,mid)){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return res;
    }

    boolean isPossible2(int[] arr, int m, int k, int mid){
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
    //18. Find the Smallest Divisor Given a Threshold
    public int smallestDivisor(int[] nums, int threshold) {
        int low =1, high = Arrays.stream(nums).reduce(0, Integer::max);
        int res =-1;

        while(low <= high){

            int mid = (low+high)/2;

            if(sum(nums, mid) <= threshold){
                res = mid;
                high = mid-1;
            }else
                low = mid+1;
        }
        return res;
    }
    int sum(int[] arr, int div){

        int count =0;
        for(int i=0;i<arr.length;i++){

            count += (int) Math.ceil(((double)arr[i]/(double)div));
        }
        return count;
    }
    //19. Capacity to ship package withing D days
    public int shipWithinDays(int[] weights, int days) {
        int low = 0, high = Arrays.stream(weights).sum();
        int res = -1;
        while(low <= high){
            int mid = (low +high)/2;
            if(isPossible3(weights, days, mid)){
                res = mid;
                high = mid -1;
            }else
                low = mid+1;
        }
        return res;
    }
    boolean isPossible3(int[] weights, int days, int weight){
        int count = 0;
        int sum = 0;
        for(int val : weights){
            if(val > weight)
                return false;

            sum += val ;
            if(sum > weight){
                count ++;
                sum = val;
            }
        }
        if(sum >0)
            count +=1;
        return count <= days;
    }
    //20. Kth Missing Positive Number
    public int findKthPositive(int[] arr, int k) {
        int low = 0, high = arr.length -1;
        int index = -1;
        while(low <= high){
            int mid = (low +high)/2;
            if(arr[mid] - mid -1 < k){
                index = mid;
                low  = mid +1;
            }else
                high = mid -1;
        }
        return k+index+1;
    }
    //21. Aggressive Cows
    public int aggressiveCows(int []stalls, int k) {
        long low = 0, high = Arrays.stream(stalls).asLongStream().sum();
        long res = -1;
        Arrays.sort(stalls);
        while(low <= high){
            long mid = (low + high)/2;
            if(isPossible4(stalls, k, mid)){
                low = mid +1;
                res = mid;
            }else
                high = mid -1;
        }
        return (int)res;
    }
    boolean isPossible4(int[] stalls, int k, long dis){
        int count = 1;
        int prevIndex = 0;
        for(int i=1;i<stalls.length;i++){
            if((long)(stalls[i]- stalls[prevIndex]) >= dis){
                count ++;
                prevIndex = i;
            }
        }
        return count >=k;
    }
    //22.  Allocate Books
    public int findPages(ArrayList<Integer> arr, int n, int m) {
        // Write your code here.
        if(m > n)
            return -1;
        long low = 1, high = arr.stream().reduce(0, Integer::sum);
        long res = -1;
        while(low <= high){
            long mid = (low + high)/2;
            if(isPossible5(arr, m, mid)){
                res = mid;
                high = mid -1;
            }else
                low = mid + 1;
        }
        return (int)res;
    }
    boolean isPossible5(ArrayList<Integer> arr, int m, long mid){
        int count =0;
        long sum = 0;
        for(int val : arr){
            if(val > mid)
                return false;
            sum += val;
            if(sum > mid){
                count ++;
                sum = val;
            }
        }

        count += sum > 0 ? 1: 0;
        return count <= m;
    }
    //23. Split Array Largest Sum
    public int splitArray(int[] nums, int k) {
        int low = 0, high = Arrays.stream(nums).sum();
        int res = -1;
        while(low <= high){

            int mid = (low + high)/2;
            if(isPossible6(nums, k, mid)){
                res = mid;
                high = mid -1;
            }else
                low = mid +1;
        }
        return res;
    }
    boolean isPossible6(int[] nums, int k, int mid){
        int count = 0;
        int sum = 0;
        for(int val : nums){
            if(val > mid)
                return false;

            sum += val;
            if(sum >mid){
                count ++;
                sum = val;
            }
        }
        count += sum > 0 ? 1: 0;
        return count <=k;
    }
    //24. Painter's Partition Problem
    public int findLargestMinDistance(ArrayList<Integer> boards, int k) {
        long low = 1, high = boards.stream().mapToLong(Integer::longValue).sum();
        long res = -1;
        while(low <= high){
            long mid = (low + high)/2;
            if(isPossible7(boards, k, mid)){
                res = mid;
                high = mid -1;
            }else
                low = mid +1;
        }
        return (int)res;
    }
    static boolean isPossible7(ArrayList<Integer> boards, int k, long mid){
        int count = 0, sum =0;
        for(int val : boards){
            if(val > mid)
                return false;
            sum += val;
            if(sum > mid){
                sum = val;
                count ++;
            }
        }
        count+= sum > 0 ? 1: 0;
        return count <=k;
    }
    //25. Minimize Max Distance to Gas Station
    public double MinimiseMaxDistance(int []arr, int K){
        double low =0, high = 0;

        for(int i=1;i<arr.length;i++){
            high = Math.max(high, (double)arr[i]-arr[i-1]);
        }
        double res = high;
        double diff = 1e-6;
        while((high-low)> diff){
            double mid = (low + high)/2.0;
            if(isPossible8(arr, K, mid)){
                high = mid;
                res = mid;
            }else
                low = mid;
        }
        return res;
    }
    boolean isPossible8(int[] arr, int k, double mid){
        int count = 0;
        for(int i=1;i<arr.length;i++){
            int val = (int) ((arr[i]- arr[i-1])/mid);
            count += val;
            if( (val * mid) == (arr[i] - arr[i-1]))
                count--;
        }
        return count <=k;
    }
    //26. Median of Two Sorted Arrays
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        if(n>m)
            return findMedianSortedArrays(nums2, nums1);

        int low = 0, high = n;

        while(low <= high) {
            int cut1 = (low + high) / 2;
            int cut2 = (n + m + 1) / 2 - cut1;

            int l1 = cut1 > 0 ? nums1[cut1-1] : Integer.MIN_VALUE;
            int r1 = cut1 < n ? nums1[cut1] : Integer.MAX_VALUE;

            int l2 = cut2 > 0 ? nums2[cut2-1] : Integer.MIN_VALUE;
            int r2 = cut2 < m ? nums2[cut2] : Integer.MAX_VALUE;

            if(l1 <= r2 && l2 <= r1) {
                if((n + m) % 2 == 0) {
                    return (Math.max(l1,l2) + Math.min(r1,r2)) / 2.0;
                }else {
                    return Math.max(l1, l2);
                }
            }else if(l1 > r2) {
                high = cut1 - 1;
            }else {
                low = cut1 + 1;
            }
        }
        return 0.0;
    }
    //27. Kth element of 2 sorted arrays
    public int kthElement(int a[], int b[], int k) {
        // code here
        int n = a.length, m = b.length;
        if(n>m)
            return kthElement(b, a, k);

        int low = Math.max(0, k-m), high = Math.min(n, k);
        int left = k;
        while(low <= high){
            int cut1 = (low +high)/2;
            int cut2 = left-cut1;

            int l1 = cut1 == 0 ? Integer.MIN_VALUE: a[cut1-1];
            int l2 = cut2 ==0 ? Integer.MIN_VALUE: b[cut2-1];

            int r1 = cut1 == n ? Integer.MAX_VALUE: a[cut1];
            int r2 = cut2 == m ? Integer.MAX_VALUE: b[cut2];

            if(l1 <= r2 && l2<= r1){
                return Math.max(l1, l2);
            }else if(l1 > r2)
                high = cut1-1;
            else
                low = cut1+1;
        }
        return -1;
    }
    //28. Row with max 1s
    public int rowWithMax1s(int arr[][]) {
        int n = arr.length, m = arr[0].length;
        int i = 0, j = m-1;
        int res = -1;
        while(i<n && j >= 0){
            if(arr[i][j] == 1){
                res = i;
                j--;
            }else
                i++;
        }
        return res;
    }
    //29. Search in a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int low = 0, high = n * m - 1;
        while(low <= high){
            int mid = (low + high)/2;
            int val = matrix[mid/m][mid%m];
            if(val == target)
                return true;
            else if(val > target)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return false;
    }
    //30. Search in a 2D Matrix II
    public boolean searchMatrix2(int[][] matrix, int target) {
        int n = matrix.length, m = matrix[0].length;
        int i = 0, j = m-1;

        while(i < n && j >= 0){
            if(matrix[i][j] == target)
                return true;
            else if(matrix[i][j] > target)
                j--;
            else
                i++;
        }
        return false;
    }
    //31. Find Peak Element (2D Matrix)
    public int[] findPeakGrid(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        int low = 0, high = m-1;

        while(low <= high){
            int col = (low+high)/2;
            int row = findMaxEleRow(mat, col, n);
            if(col+1 < m && mat[row][col]< mat[row][col+1])
                low = col +1;
            else if(col-1 >=0 && mat[row][col-1]> mat[row][col])
                high = col -1;
            else
                return new int[]{row, col};

        }
        return new int[]{-1, -1};
    }
    int findMaxEleRow(int[][] mat, int col, int n){
        int index = 0;
        int maxValue =0;
        for(int i=0;i<n;i++){
            if(mat[i][col] > maxValue){
                maxValue = mat[i][col];
                index = i;
            }
        }
        return index;
    }
    //32. Matrix Median
    int median(int mat[][]) {
        int n = mat.length, m = mat[0].length;
        int low = 1, high = 2000;
        while(low <= high){
            int mid = (low + high)/2;
            int count = 0;
            for(int i=0;i<n;i++){
                count += upperBound(mat[i], m, mid);
            }
            if(count <= (n*m)/2)
                low = mid +1;
            else
                high = mid -1;
        }
        return low;
    }
    int upperBound(int[] arr, int n, int target){
        int low = 0, high = n-1;
        int res = 0;
        while(low <= high){
            int mid = (low + high)/2;
            if(arr[mid] <= target){
                low = mid + 1;
                res = low;
            }else
                high = mid -1;
        }
        return res;
    }
    //33. Seperate squares by its area using a horizontal line
    public double separateSquares(int[][] squares) {
        double low = 0, high = 0;
        for (int i = 0; i < squares.length; i++) {
            high = Math.max(high, (double)(squares[i][1] + squares[i][2]));
        }

        double res = 0;
        while(high-low > (double)1e-5){
            double mid = low +(high-low)/2.0;

            if(isPossible(mid, squares)){
                res = mid;
                low = mid;
            }else{
                high= mid;
            }
        }
        return res;
    }
    boolean isPossible(double mid, int[][] sq){
        double areaAbove =0.0, areaBelow = 0.0;
        for(int i=0;i<sq.length;i++){

            int x = sq[i][0], y = sq[i][1], l = sq[i][2];

            if(y+l <= mid){
                areaBelow += (double)l*(double)l;
            }else if( y >= mid){
                areaAbove += (double)l*(double)l;
            }else{
                areaAbove += (y+l - mid)*l;
                areaBelow += (mid - y) * l;
            }
        }
        return areaBelow < areaAbove;
    }
}
