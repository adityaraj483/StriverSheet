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

            count += Math.ceil(((double)arr[i]/(double)div));
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
    //21.
}
