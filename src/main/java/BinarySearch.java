import java.util.Arrays;

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

    //8.

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
