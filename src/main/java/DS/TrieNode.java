package DS;

public class TrieNode{
    public TrieNode[] links = new TrieNode[26];
    public int prefixCount = 0;

    public TrieNode get(char ch){
        return links[ch - 'a'];
    }
    public void put(char ch){
        links[ch - 'a'] = new TrieNode();
    }
}
