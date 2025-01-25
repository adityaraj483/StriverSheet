package DS;

import java.util.ArrayList;
import java.util.List;

public class DisjointSet{
    public List<Integer> parent;
    public List<Integer> size;
    public DisjointSet(int n){
        parent = new ArrayList<>();
        size = new ArrayList<>();
        for(int i=0;i<=n;i++){
            parent.add(i);
            size.add(1);
        }
    }

    public int findUParent(int node){
        if(node == parent.get(node))
            return node;
        int p = findUParent(parent.get(node));
        parent.set(node, p);
        return p;
    }
    public void unionBySize(int x, int y){
        int upx = findUParent(x);
        int upy = findUParent(y);
        if(upx == upy)
            return;

        if(size.get(upx) < size.get(upy)){
            parent.set(upx, upy);
            size.set(upy, size.get(upx) + size.get(upy));
        }else{
            parent.set(upy, upx);
            size.set(upx, size.get(upx) + size.get(upy));
        }
    }
}
