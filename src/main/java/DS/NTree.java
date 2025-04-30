package DS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NTree {
    public Set<NTree> children;
    public int val;
    public NTree(int val){
        this.val = val;
        children = new HashSet<>();
    }
}
