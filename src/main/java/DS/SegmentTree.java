package DS;

import java.util.Arrays;

public class SegmentTree {
    int[] tree;
    int[] arr;
    int size;
    public SegmentTree(int[] arr){
        this.arr = arr;
        this.size = arr.length;

        this.tree = new int[4 * size];
    }
    public SegmentTree(int n){
        tree = new int[4 * n];
        size = n;
    }
    public void updateSumAtIndex(int index, int val){
        updateSumAtIndex(0, size-1,index, val, 0);
    }
    public int findSumInRange(int ql, int qr){
        return findSumInRange(0, size-1, ql, qr, 0);
    }
    public void buildTreeForSum(){
        buildTreeForSum(0, size-1, 0);
    }
    private void buildTreeForSum(int l, int r, int node) {
        if(l == r){
            tree[node] = arr[l];
            return;
        }

        int mid = (l + r)/2;

        buildTreeForSum( l, mid, 2*node+1);
        buildTreeForSum( mid+1, r, 2*node+2);
        tree[node] = tree[2*node+1] + tree[2*node+2];
    }

    private int findSumInRange(int l, int r, int ql, int qr, int node){
        if(r < ql || qr < l)
            return 0;
        if(ql <= l && r <= qr)
            return tree[node];

        int mid = (l + r)/2;
        int left = findSumInRange(l, mid, ql, qr, 2*node+1);
        int right = findSumInRange(mid+1, r, ql, qr, 2*node+2);
        return left+right;
    }

    private void updateSumAtIndex(int l, int r, int index, int val, int node){
        if(l == r){
            tree[node] = val;
            return;
        }

        int mid = (l + r)/2;
        if(index <= mid)
            updateSumAtIndex(l, mid, index, val, 2 * node+1);
        else
            updateSumAtIndex(mid+1, r, index, val, 2 * node+2);

        tree[node] = tree[2*node+1] + tree[2*node+2];
    }

}
class solun{// next greater elements to the right
    public static void main(String[] args) {
        int[] arr = {1,5,4,6,2};
        int n = arr.length;
        int[][] brr = new int[n][2];
        for(int i=0;i<n;i++){
            brr[i][0] = arr[i];
            brr[i][1] = i;
        }
        Arrays.sort(brr, (a, b) -> b[0] - a[0]);
        SegmentTree tree = new SegmentTree(n);
        int[] res = new int[n];
        for(int i=0;i<n;i++){
            int index = brr[i][1];
            int val = brr[i][0];
            int totalEle = tree.findSumInRange(index, n-1);
            res[index] = totalEle;
            tree.updateSumAtIndex(index, 1);
        }
        for(int val : res)
            System.out.print(val +", ");
    }
}

