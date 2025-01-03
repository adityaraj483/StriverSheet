package DS;

public class DlLLRandom {
    public int val;
    public DlLLRandom next;
    public DlLLRandom random;

    public DlLLRandom(int x) {
        val = x;
        next = null;
        random = null;
    }
    public DlLLRandom(int x, DlLLRandom next) {
        val = x;
        this.next = next;
    }
}
