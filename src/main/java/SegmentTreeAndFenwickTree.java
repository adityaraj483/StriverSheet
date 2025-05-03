import DS.SegmentTree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentTreeAndFenwickTree {

    //1. 307 Range Sum Query - Mutable -> Segment Tree
    class NumArray {
       SegmentTree tree;
        public NumArray(int[] nums) {
            tree = new SegmentTree(nums);
            tree.buildTreeForSum();
        }

        public void update(int index, int val) {
            tree.updateSumAtIndex(index, val);
        }
        public int sumRange(int left, int right) {
            return tree.findSumInRange(left, right);
        }
    }

    //2. 315 Count of Smaller Numbers After Self
    public List<Integer> countSmaller(int[] nums) {

        int n =  nums.length;
        int[][] arr = new int[n][2];
        for(int i=0;i<n;i++){
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        SegmentTree tree = new SegmentTree(nums);
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);
        Integer[] res = new Integer[n];
        for(int i=0;i<n;i++){

            int index = arr[i][1];

            int cnt = index < n-1 ? tree.findSumInRange(index+1, n-1) : 0;
            res[index] = cnt;
            tree.updateSumAtIndex(index, 1);
        }
        return Arrays.asList(res);
    }
    //3. count of greater number after self
    public static int[] countGreater(int []arr, int []query) {
        // Write your code here.
        int n = arr.length;
        int[][] nums = new int[n][2];
        for(int i=0;i<n;i++){
            nums[i][0] = arr[i];
            nums[i][1] = i;
        }

        SegmentTree tree = new SegmentTree(n);

        Arrays.sort(nums, (a, b) -> b[0] - a[0]);
        Map<Integer, Integer> mp = new HashMap<>();
        int cnt = 1;

        for(int i=0;i<n;i++){
            int index = nums[i][1];
            int c = index < n-1 ? tree.findSumInRange(index +1, n-1) : 0;

            mp.put(index, c);
            tree.updateSumAtIndex(index, 1);
        }
        int[] res = new int[query.length];
        for(int i=0;i<query.length;i++){
            res[i] = mp.get(query[i]);
        }
        return res;
    }
}
