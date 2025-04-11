package DS;

public class Trie{
    public TrieNode root;
    public Trie(){
        root = new TrieNode();
    }

    public void insert(String s){
        TrieNode node = root;
        for(char ch : s.toCharArray()){
            if(node.get(ch) == null){
                node.put(ch);
            }
            node = node.get(ch);
            node.prefixCount++;
        }
    }
    public String getPrefix(String s){
        TrieNode node = root;
        for(int i = 0;i<s.length();i++){
            char ch = s.charAt(i);
            node = node.get(ch);
            if(node.prefixCount == 1){
                return s.substring(0, i+1);
            }
        }
        return s;
    }
}