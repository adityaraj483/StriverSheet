package DS;

public class Pair <T, Y>{
    public T first;
    public Y second;
    public Pair(T first, Y second){
        this.first = first;
        this.second = second;
    }
    public Pair(){
    }
    public T getFirst(){
        return first;
    }
    public Y getSecond(){
        return second;
    }
    public void setFirst(T first){
        this.first = first;
    }
    public void setSecond(Y second){
        this.second = second;
    }
}
