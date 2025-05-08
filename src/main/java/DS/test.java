package DS;


import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
       int[] arr = {1,2,3,4};
       int n = arr.length;
       for(int i=0;i<n;i++){
           List<Integer> curr = new ArrayList<>();
           for(int j=i;j<n;j++){
               curr.add(arr[j]);
               print(curr);
           }
       }
    }
    static void print(List<Integer> curr){
        for(int val : curr)
            System.out.print(val);
        System.out.println();
    }


}



