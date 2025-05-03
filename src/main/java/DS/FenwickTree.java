package DS;

public class FenwickTree { // we can only add something or subtract something but did not update something.
    int[] arr;
    int n;
    public FenwickTree(int n){
        this.arr = new int[n+1];
        this.n = n+1;
    }
    public int query(int id){
        int res = 0;
        while(id > 0){
            res += arr[id];
            id -= (id & -id);
        }
        return res;
    }
    public void update(int id, int val){
        while(id < n){
            arr[id] += val;
            id += (id & - id);
        }
    }
}
