package DS;

import java.util.*;

//Given an array of non-negative integers, the goal is to travel from the first index to the last index with maximum
// possible score with as many jumps allowed. Score of a jump is defined as the number of index jumped
// multiplied by the value on the jumped index.
//e.g. [3,7,9,10]
//
//if the jump is from index0 to index2, the score is (2-0)*9 = 18
//
//Sample input: [3,12,9,10]
//Sample output: 32
public class test{
    static int count = 0;
    public static void main(String[] args) {
        int[][] arr = {{1,1,1},
        {1,0,1},
        {0,0,1}};
        System.out.println(solve(arr));
    }

    private static int solve(int[][] arr) {
        int boys = arr.length;
        int girls = arr[0].length;

        int[] assigned = new int[girls];
        Arrays.fill(assigned, -1);

        int count = 0;
        for(int i=0;i<boys;i++){

            if(helper(i, arr, assigned, new HashSet<>()))
                count++;
        }
        return count;
    }

    private static boolean helper(int boy, int[][] arr, int[] assigned, Set<Integer> seen) {
        for(int girl =0;girl < assigned.length;girl ++){
            if(arr[boy][girl] == 0 || seen.contains(girl))
                continue;
            seen.add(girl);

            if(assigned[girl] == -1 || helper(assigned[girl], arr, assigned, seen)) {
                assigned[girl] = boy;
                return true;
            }
        }
        return false;
    }


}