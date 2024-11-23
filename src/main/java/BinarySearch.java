public class BinarySearch {
    public static void main(String[] args) {

    }

    //Binary Search --------------------------------------------------------------------------------------------------

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
