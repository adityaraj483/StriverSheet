package DS;
import java.util.*;
import java.util.stream.Collectors;

public class test{
    public static void main(String[] args){


        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        List<Integer> list = new ArrayList<>(t);
        while(t-- > 0){
            int n = sc.nextInt();
            list.add(n);
        }
        sc.close();

        for (Integer integer : list) {
            int[] arr = solve(integer);
            for (int val : arr) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    static int[] solve(int n) {

        int[] res = new int[n];
        for(int i=1;i<=n;i++){
            res[i-1] = i;
        }
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(8);
        list.add(288);
        list.add(9800);
        list.add(57121);
        list.add(332928);

        if(list.contains(n))
            return new int[]{-1};
        int val1 = res[0], val2 = res[n-1];
        res[0] = val2;
        res[n-1] = val1;
        return res;
    }
}