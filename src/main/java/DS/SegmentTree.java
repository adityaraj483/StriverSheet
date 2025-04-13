package DS;

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
