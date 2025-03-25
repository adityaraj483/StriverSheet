package DS;

import java.util.ArrayList;
import java.util.List;

public class Node1{
    public String name, value;
    public List<Node1> children;
    public Node1(String name, String val){
        this.name = name;
        this.value = val;
        this.children = new ArrayList<>();
    }
}