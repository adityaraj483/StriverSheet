package DS;

import java.util.*;
import java.util.stream.Collectors;


public class test {

    public static void main(String[] args) {
        FenwickTree tree = new FenwickTree(15);
        tree.update(2, 1);
        tree.update(5, -1);

        tree.update(4, 1);
        tree.update(7, -1);

        System.out.println(tree.query(7));

//        for(int val : tree.arr){
//            System.out.print(val +", ");
//        }

    }


}