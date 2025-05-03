package DS;

import java.util.*;
import java.util.stream.Collectors;


public class test {
    public static void main(String[] args) {
        int[] arr = {1,2,3,3};
        int res = findSpecialInteger(arr);
        System.out.println(res);
    }

    static public int findSpecialInteger(int[] arr) {
        int n = arr.length;
        int k = n/4;
        int n1 = n-1;

        List<Integer> pos = new ArrayList<>();
        while(n1 >= k){
            if(pos.isEmpty() || pos.get(pos.size()-1) != arr[n1])
                pos.add(arr[n1]);
            n1 -= k;
        }

        for(int i=0;i<pos.size();i++){
            int val = pos.get(i);
            int a = lowerBound(arr, val, n);
            int b = upperBound(arr, val, n);
            if(b - a + 1 > k){
                return val;
            }
        }
        return -1;
    }
    static int lowerBound(int[] arr, int target, int n){
        int low = 0, high = n-1;
        int res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) {
                res = mid;
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }
    static int upperBound(int[] arr, int target, int n){
        int low = 0, high = n-1;
        int res = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) {
                res = mid;
                low = mid + 1; // keep searching on the right
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return res;
    }

}