package DS;


//35. Question: Given n routers placed on a Cartesian plane and provided with a source and
// destination vertex, the task was to determine whether it was possible to reach the
// destination. Only adjacent vertices could be explored, and a vertex was considered
// adjacent if it had the minimum distance from the current vertex while remaining within
// a given threshold. Additionally, once a vertex was visited, the previously visited node became
// inactive (i.e., could no longer be used). The goal was to determine if a path existed from the
// source to the destination under these constraints.

public class test {

    public static void main(String[] args) {
        int[] arr = new int[]{1,6,9,3,4,76,9,9};
        FenwickTree tree = new FenwickTree(8);
        for(int i=0;i<arr.length;i++){
            tree.update(i+1, arr[i]);
        }

//        for(int i=0;i<=arr.length;i++){
//            System.out.print(tree.arr[i]+", ");
//        }

        System.out.println(tree.query(3) - tree.query(2));
    }

}






