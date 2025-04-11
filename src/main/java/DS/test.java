package DS;

import javax.swing.event.ListDataListener;
import java.util.*;
import java.util.stream.Collectors;


//35. Question: Given n routers placed on a Cartesian plane and provided with a source and
// destination vertex, the task was to determine whether it was possible to reach the
// destination. Only adjacent vertices could be explored, and a vertex was considered
// adjacent if it had the minimum distance from the current vertex while remaining within
// a given threshold. Additionally, once a vertex was visited, the previously visited node became
// inactive (i.e., could no longer be used). The goal was to determine if a path existed from the
// source to the destination under these constraints.

public class test {

    public static void main(String[] args) {
        int a = 10;
        print(a);
        print(~a);
    }
    static void print(int n ){
        System.out.println(n);
        if( n < 0)
            n *=-1;
        StringBuilder sb = new StringBuilder();
        while(n != 0){
            sb.append(n % 2);
            n /= 2;
        }
        System.out.println(sb.reverse());
    }

}






