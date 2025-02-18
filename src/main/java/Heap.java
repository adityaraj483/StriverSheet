import java.util.*;

public class Heap {
    public static void main(String[] args) {

    }
    //1.
    //2.
    //3.
    //4.
    //5. Kth Largest Element in an Array
    public int findKthLargest(int[] nums, int k) {
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> a-b);
        for (int num : nums) {
            pq.add(num);
            if (pq.size() > k)
                pq.remove();
        }
        return pq.remove();
    }
    //6. Kth Smallest Element in an array
    public static int kthSmallest(int[] arr, int k) {
        // Your code here
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> b-a);
        for(int val : arr){
            pq.add(val);
            if(pq.size() > k)
                pq.remove();
        }
        return pq.remove();
    }
    //7.
    //8.
    //9.
    //10. Task Scheduler
    public int leastInterval(char[] tasks, int n) {
        int[] fre = new int[26];
        for (char task : tasks) {
            fre[task - 'A']++;
        }
        Queue<Integer> pq = new PriorityQueue<>((a, b) -> b-a);

        for(int i=0;i<26;i++){
            if(fre[i] > 0)
                pq.add(fre[i]);
        }

        Queue<int[]> q = new LinkedList<>();

        int time = 0;
        while(!q.isEmpty() || !pq.isEmpty()){
            time ++;
            if(!pq.isEmpty()){

                int val = pq.remove();
                val--;
                if(val > 0)
                    q.add(new int[]{val, time + n});
            }

            if(!q.isEmpty()){

                if(q.peek()[1] == time){
                    pq.add(q.remove()[0]);
                }
            }
        }
        return time;
    }
    //11. Hands of Straights
    public boolean isNStraightHand(int[] hand, int groupSize) {
        int n = hand.length;
        if(n < groupSize || n % groupSize != 0)
            return false;

        Map<Integer, Integer> mp = new HashMap<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[0]-b[0]);

        for(int i=0;i<n;i++)
            mp.put(hand[i], mp.getOrDefault(hand[i], 0)+1);

        for(Map.Entry<Integer, Integer> entry : mp.entrySet()){
            pq.add(new int[]{entry.getKey(), entry.getValue()});
        }
        int m = n/groupSize;

        for(int i=0;i<m;i++){

            int[][] arr = new int[groupSize][2];

            for(int j=0;j<groupSize;j++){
                arr[j] = pq.remove();
                arr[j][1]--;
                if(j >0 && Math.abs(arr[j][0] - arr[j-1][0]) != 1)
                    return false;
            }

            for(int j= 0;j<groupSize;j++){
                if(arr[j][1] > 0)
                    pq.add(arr[j]);
            }
        }
        return true;
    }
    //12. Design Twitter

    //13. Connect `n` ropes with minimal cost -> https://practice.geeksforgeeks.org/problems/minimum-cost-of-ropes-1587115620/1
    public int minCost(int[] arr) {
        int n = arr.length;
        Queue<Integer> pq = new PriorityQueue<>();
        for (int j : arr) {
            pq.add(j);
        }

        int res  = 0;
        while(pq.size() > 1){
            int first = pq.remove();
            int sec = pq.remove();
            pq.add(first+sec);
            res += first + sec;
        }
        return res;
    }
    //14. Kth Largest Element in a Stream
    class KthLargest {
        Queue<Integer> pq;
        int K;
        public KthLargest(int k, int[] nums) {
            pq = new PriorityQueue<>((a, b)-> a-b);
            for (int num : nums) {
                pq.add(num);
                if (pq.size() > k)
                    pq.remove();
            }
            K = k;
        }
        public int add(int val) {
            pq.add(val);
            if(pq.size() > K)
                pq.remove();
            return pq.peek();
        }
    }
    //15. Maximum Sum Combination
    public int[] solve(int[] A, int[] B, int C) {

        Arrays.sort(A);
        Arrays.sort(B);
        int n = A.length;
        Set<String> set = new HashSet<>();

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        pq.add(new int[]{A[n-1]+B[n-1], n-1, n-1});
        set.add((n-1) +", "+ (n-1));
        int[] res = new int[C];
        int k= 0;
        while(C> 0){
            C--;
            int[] curr = pq.remove();
            int val = curr[0];
            int i = curr[1];
            int j = curr[2];
            res[k++] = val;

            if(!set.contains((i-1) +", "+ j) && i-1 >= 0){
                pq.add(new int[]{A[i-1]+ B[j] , i-1, j});
                set.add((i-1) +", "+ (j));
            }
            if(!set.contains(i +", "+ (j-1)) && j-1 >=0){
                pq.add(new int[]{A[i] + B[j-1] , i, j-1});
                set.add(i +", "+ (j-1));
            }

        }
        return res;
    }

    //16. Find Median from Data Stream
    class MedianFinder {
        Queue<Integer> maxPQ, minPQ;
        public MedianFinder() {
            maxPQ = new PriorityQueue<>((a, b) -> b-a);
            minPQ = new PriorityQueue<>((a, b) -> a-b);
        }

        public void addNum(int num) {
            if(maxPQ.isEmpty())
                maxPQ.add(num);
            else{
                if(maxPQ.size() == minPQ.size()){

                    if(minPQ.peek() >= num){
                        maxPQ.add(num);
                    }else{
                        maxPQ.add(minPQ.remove());
                        minPQ.add(num);
                    }
                }else{
                    if(maxPQ.peek() > num){
                        minPQ.add(maxPQ.remove());
                        maxPQ.add(num);
                    }else{
                        minPQ.add(num);
                    }
                }
            }
        }
        public double findMedian() {
            int n = maxPQ.size(), m = minPQ.size();
            if((n+m)%2 ==0){
                double val = (minPQ.peek() + maxPQ.peek())/2.0;
                return val;
            } else {
                return maxPQ.peek();
            }
        }
    }
    //17. Top k frequent elements
    public int[] topKFrequent(int[] nums, int k) {
        Queue<int[]> pq = new PriorityQueue<>((a,b) -> a[1]-b[1]);
        int n = nums.length;
        Map<Integer, Integer> mp = new HashMap<>();
        for(int i=0;i<n;i++){
            mp.put(nums[i], mp.getOrDefault(nums[i], 0)+1);
        }

        for(Map.Entry<Integer, Integer> entry : mp.entrySet()){
            pq.add(new int[]{entry.getKey(), entry.getValue()});
            if(pq.size() > k)
                pq.remove();
        }
        int[] ans = new int[k];
        int i = 0;
        while(!pq.isEmpty()){
            ans[i++] = pq.remove()[0];
        }
        return ans;
    }
}
