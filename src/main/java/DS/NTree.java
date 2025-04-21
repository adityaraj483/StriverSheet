package DS;

import java.util.ArrayList;
import java.util.List;

public class NTree {
    public List<NTree> children;
    public int val;
    public NTree(int val){
        this.val = val;
        children = new ArrayList<>();
    }
}
