import java.util.*;
class Tries {


//1. Implement Trie (Prefix Tree)
class TrieNode{
    TrieNode[] links = new TrieNode[26];
    boolean isEnd = false;
    boolean containsKey(char ch){
        return links[ch - 'a'] != null;
    }
    void put(char ch){
        links[ch - 'a'] = new TrieNode();
    }

    TrieNode get(char ch){
        return links[ch - 'a'];
    }
    int wordsEndWith;
    int totalPrefix;
}
class Trie1 {
    TrieNode root;

    public Trie1() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.links[ch - 'a'] != null)
                node = node.links[ch - 'a'];
            else {
                node.links[ch - 'a'] = new TrieNode();
                node = node.links[ch - 'a'];
            }
        }
        node.isEnd = true;
    }
    int insert(String s, int start){
        int count = 0;
        Trie1 t  = new Trie1();
        TrieNode node = t.root;
        for(int i=start;i<s.length();i++){
            char ch = s.charAt(i);
            if(!node.containsKey(ch)){
                count ++;
                node.put(ch);
            }
            node = node.get(ch);
        }
        return count;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (node.links[ch - 'a'] != null)
                node = node.links[ch - 'a'];
            else
                return false;
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (node.links[ch - 'a'] != null)
                node = node.links[ch - 'a'];
            else
                return false;
        }
        return true;
    }
    public boolean checkAllPrefix(String word){
        TrieNode node = root;
        for(char ch : word.toCharArray()){
            if(node.containsKey(ch) == false)
                return false;
            else {
                node = node.get(ch);

                if(node.isEnd == false)
                    return false;
            }
        }
        return true;
    }
}

    //2. Implement Trie - 2 (Prefix Tree)
class Trie2 {
    TrieNode root;
    public Trie2() {
        // Write your code here.
        root = new TrieNode();
    }

    public void insert(String word) {
        // Write your code here.
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            node.totalPrefix++;
            if(node.links[ch - 'a'] != null){
                node = node.links[ch - 'a'];
            }else {
                node.links[ch- 'a'] = new TrieNode();
                node = node.links[ch - 'a'];
            }
        }
        node.totalPrefix++;
        node.wordsEndWith++;
    }

    public int countWordsEqualTo(String word) {
        // Write your code here.
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            if(node.links[ch - 'a'] != null){
                node = node.links[ch - 'a'];
            }else {
                return 0;
            }
        }
        return node.wordsEndWith;
    }

    public int countWordsStartingWith(String word) {
        // Write your code here.
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            if(node.links[ch - 'a'] != null){
                node = node.links[ch - 'a'];
            }else {
                return 0;
            }
        }
        return node.totalPrefix;
    }

    public void erase(String word) {
        // Write your code here.
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char ch = word.charAt(i);
            node.totalPrefix--;
            node = node.links[ch - 'a'];
        }
        node.totalPrefix--;
        node.wordsEndWith--;
    }
}
    //3. Longest String with All Prefixes
    public boolean checkAllPrefix1(String word){
        Trie1 t = new Trie1();
        TrieNode node = t.root;
        for(char ch : word.toCharArray()){
            if(node.containsKey(ch) == false)
                return false;
            else {
                node = node.get(ch);

                if(node.isEnd == false)
                    return false;
            }
        }
        return true;
    }
    public String completeString(int n, String[] a) {
        Trie1 trie = new Trie1();
        for(int i=0;i<n;i++){
            trie.insert(a[i]);
        }

        String res = "";

        for(String word : a){
            if(trie.checkAllPrefix(word)){
                if (word.length() > res.length() ||
                        (word.length() == res.length() && word.compareTo(res) < 0)) {
                    res = word;
                }
            }
        }
        return res.isEmpty() ? "None" : res;
    }
    //4. Number of Distinct Substrings in a String
    int insert(String s, int start){
        int count = 0;
        Trie1 t  = new Trie1();
        TrieNode node = t.root;
        for(int i=start;i<s.length();i++){
            char ch = s.charAt(i);
            if(!node.containsKey(ch)){
                count ++;
                node.put(ch);
            }
            node = node.get(ch);
        }
        return count;
    }
    public int countDistinctSubstrings(String s) {
        //	  Write your code here.
        Trie1 trie = new Trie1();
        int cnt = 0;
        int n = s.length();
        for(int i=0;i<n;i++){
            for(int j=i;j<n;j++){
                cnt += trie.insert(s, j);
            }
        }
        return cnt+1;
    }
    //5. Bit PreRequisites for TRIE Problems
    public int XOR(int n, int m)
    {
        // Code here
        return n ^ m;
    }
    public int check(int a, int b) {
        // Code here
        a--;
        if((b & (1<<a)) > 0)
            return 1;
        return 0;
    }
    public int setBit(int c, int d) {
        // Code here
        return (d | (1<<c));
    }
    //6. Maximum XOR of two numbers in an array
    class XorSol1 {
        class TrieNode{
            TrieNode[] links = new TrieNode[2];
            void put(int i){
                links[i] = new TrieNode();
            }
            TrieNode get(int i){
                return links[i];
            }
            boolean containsKey(int i){
                return links[i] != null;
            }
        }
        class Trie{
            TrieNode root = new TrieNode();
            void insert(int n){

                TrieNode node = root;

                for(int i=31;i>=0;i--){
                    int val = (n &(1<< i)) > 0? 1: 0;
                    if(node.containsKey(val) == false){
                        node.put(val);
                    }
                    node = node.get(val);
                }
            }

            int findXor(int n){
                int res = 0;
                TrieNode node = root;

                for(int i=31;i>=0;i--){
                    int val = (n &(1<< i)) > 0? 0: 1;
                    if(node.containsKey(val) == false){
                        node = node.get((val+1)%2);
                    }else{
                        node = node.get(val);
                        res += (1 << i);
                    }
                }
                return res;
            }
        }
        public int findMaximumXOR(int[] nums) {
            Trie t = new Trie();
            for(int i=0;i<nums.length;i++){
                t.insert(nums[i]);
            }
            int res = 0;
            for(int i=0;i<nums.length;i++){
                int xor = t.findXor(nums[i]);
                res = Math.max(res, xor);
            }
            return res;
        }
        //7. Maximum XOR With an Element From Array for all queries

        class State {
            int index;
            int[] query;
            State(int i, int[] q) {
                index = i;
                query = q;
            }
        }
        public int[] maximizeXor(int[] nums, int[][] queries) {

            List<State> q = new ArrayList<>();
            for(int i=0;i<queries.length;i++) {
                q.add(new State(i, queries[i]));
            }
            q.sort((a, b) -> a.query[1] - b.query[1]);

            Arrays.sort(nums);
            Arrays.sort(queries, (a, b) -> a[1] - b[1]);

            int n = nums.length;
            int m = queries.length;

            int i=0;
            Trie t = new Trie();
            int[] res = new int[m];

            for(int j=0;j<m;j++){
                int x = q.get(j).query[0];
                int mi = q.get(j).query[1];

                int currIndex = q.get(j).index;

                while(i < n && nums[i] <= mi){
                    t.insert(nums[i++]);
                }
                if(i == 0){
                    res[currIndex] = -1;
                    continue;
                }

                int curr = t.findXor(x);
                res[currIndex] = curr;
            }
            return res;
        }
    }
}
