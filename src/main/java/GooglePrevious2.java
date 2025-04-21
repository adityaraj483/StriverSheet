import DS.DisjointSet;
import DS.NTree;
import com.sun.source.tree.Tree;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GooglePrevious2 {
    //1. Your organization has hired interns who need to relocate for the summer.
    // You are in charge of assigning apartments to them. Each intern will get their own room.
    // They can choose whether they prefer to share a 2+ room apartment or get a one-bedroom to themselves.
    //Note that they may not get what they want because the apartments vary in the number of rooms that they have.

    class solution {
    //        public static void main(String[] args) {
    //            List<Apartment> apartments = new ArrayList<>();
    //            apartments.add(new Apartment(101, 1));
    //            apartments.add(new Apartment(102, 2));
    //            apartments.add(new Apartment(103, 1));
    //            apartments.add(new Apartment(104, 3));
    //
    //
    //            List<Person> people = new ArrayList<>();
    //            people.add(new Person("Jean", true)); // Wants housemates
    //            people.add(new Person("Maria", false)); // Doesn't want housemates
    //            people.add(new Person("Alex", true));
    //            people.add(new Person("Xinyi", true));
    //            people.add(new Person("Filippo", true));
    //            people.add(new Person("Cameron", false));
    //            people.add(new Person("Vikki", false)); // Wants housemates
    //            for (var entry : assignApartmentsToPeople(apartments, people).entrySet()) {
    //                var key = entry.getKey();
    //                System.out.print(key + " : ");
    //                for (String s : entry.getValue()) {
    //                    System.out.print(s + ", ");
    //                }
    //                System.out.println();
    //            }
    //        }


        class Apartment {
            int apartmentNo;
            int bedsCount;

            Apartment(int apartmentNo, int bedsCount) {
                this.apartmentNo = apartmentNo;
                this.bedsCount = bedsCount;
            }
        }

        class Person {
            String name;
            boolean wantRoommate;

            Person(String name, boolean wantRoommate) {
                this.name = name;
                this.wantRoommate = wantRoommate;
            }

        }

        Map<Integer, List<String>> apartmentPersonMap;

        public Map<Integer, List<String>> assignApartmentsToPeople(List<Apartment> apartments, List<Person> persons) {
            List<Apartment> singleRoomApartment = new ArrayList<>();
            List<Apartment> multiRoomApartment = new ArrayList<>();
            for (Apartment apartment : apartments) {
                if (apartment.bedsCount == 1)
                    singleRoomApartment.add(apartment);
                else
                    multiRoomApartment.add(apartment);
            }

            List<Person> wantSingleRoomPerson = new ArrayList<>();
            List<Person> wantMultiRoomPerson = new ArrayList<>();
            for (Person person : persons) {
                if (person.wantRoommate) {
                    wantMultiRoomPerson.add(person);
                } else
                    wantSingleRoomPerson.add(person);
            }

            apartmentPersonMap = new HashMap<>();

            AtomicInteger singleRoomPersonIndex = new AtomicInteger(0);
            AtomicInteger singleRoomApartmentIndex = new AtomicInteger(0);
            assign(singleRoomApartment, singleRoomApartmentIndex, wantSingleRoomPerson, singleRoomPersonIndex);


            AtomicInteger multiRoomPersonIndex = new AtomicInteger(0);
            assign(singleRoomApartment, singleRoomApartmentIndex, wantMultiRoomPerson, multiRoomPersonIndex);

            AtomicInteger multiRoomApartmentIndex = new AtomicInteger(0);
            assign(multiRoomApartment, multiRoomApartmentIndex, wantSingleRoomPerson, singleRoomPersonIndex);

            assign(multiRoomApartment, multiRoomApartmentIndex, wantMultiRoomPerson, multiRoomPersonIndex);


            return apartmentPersonMap;

        }

        void assign(List<Apartment> apartments, AtomicInteger apartmentIndex, List<Person> persons, AtomicInteger personIndex) {
            if (apartments.size() == apartmentIndex.get() || persons.size() == personIndex.get())
                return;


            while (apartmentIndex.get() < apartments.size() && personIndex.get() < persons.size()) {
                Apartment apartment = apartments.get(apartmentIndex.get());
                int cnt = apartment.bedsCount - apartmentPersonMap.getOrDefault(apartment.apartmentNo, new ArrayList<>()).size();
                while (cnt > 0 && personIndex.get() < persons.size()) {
                    Person person = persons.get(personIndex.get());
                    apartmentPersonMap.computeIfAbsent(apartment.apartmentNo, e -> new ArrayList<>()).add(person.name);
                    personIndex.set(personIndex.get() + 1);
                    cnt--;
                }
                if (cnt > 0)
                    break;
                apartmentIndex.set(apartmentIndex.get() + 1);
            }

        }
    }
    //2.: There is stream of float values (-inf, inf) which is coming as input and an integer D.
    //We need to find a set of 3 values which satisfy condition -
    // |a - b| <= D, |b - c| <= D, |a - c| <= D, assuming a,b,c are 3 float values.
    // Print these 3 values and remove them and continue ....
    //Constraints -
    //All values in stream will be unique.
    //D -> [0, inf)
    //Eg:
    //Input stream - [1,10,7,-2,8,....], d = 5
    //Output - (when 8 comes, then print "7 8 10" and remove them and continue)
    //    public static void main(String[] args) {
    //        TreeSet<Double> set = new TreeSet<>();
    //        set.add(1.0);
    //        set.add(10.0);
    //        set.add(7.0);
    //        set.add(-2.0);
    //        set.add(8.0);
    //        checkTriplets(set, 8, 5);
    //    }
    private static void checkTriplets(TreeSet<Double> set, double x, double D) {
        NavigableSet<Double> inRange = set.subSet(x - D, true, x + D, true);

        if (inRange.size() >= 3) {
            Iterator<Double> it = inRange.iterator();
            double a = it.next();
            double b = it.next();
            double c = it.next();

            double min = Math.min(a, Math.min(b, c));
            double max = Math.max(a, Math.max(b, c));

            if (max - min <= D) {
                System.out.println("Triplet: " + a + ", " + b + ", " + c);
                set.remove(a);
                set.remove(b);
                set.remove(c);
            }
        }
    }
    //3. In a matrix of 0s and 1s you need to find the largest right-angled triangle
    //    public static void main(String[] args) {
    //        int[][] arr = {
    //                {1,1,0,0},
    //                {1,1,1,0},
    //                {1,1,1,1}
    //        };
    //        System.out.println(maxSizeRightTriangle(arr));
    //    }
    static int maxSizeRightTriangle(int[][] mat){
        int n = mat.length;
        int m = mat[0].length;
        int[][] dp = new int[n+1][m+1];
        int max = 0;

        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(mat[i][j] == 0)
                    continue;
                int val =  Math.min(dp[i+1][j], dp[i+1][j+1]);
                dp[i][j] = val + 1;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }

    //4. A turtle can move forward and backward.
    //A steps forward and B steps backward will the turtle ever be able to reach a target T.
    //I might be missing some parameters here I don’t remember it clearly.
//    public static void main(String[] args) {
//        System.out.println(turtleJump2(4, 1, -8));
//    }
    static boolean turtleJump1(int A, int B, int T){
        Set<Integer> seen = new HashSet<>();
        return func(A, B, T, 0, seen);
    }

    static private boolean func(int a, int b, int t, int curr,  Set<Integer> seen) {
        if(seen.contains(curr))
            return false;
        if(curr == t)
            return true;
        seen.add(curr);

        if(curr >= t + b)
            return func(a, b, t, curr-b, seen);
        else
            return func(a, b, t, curr + a,seen);
    }
    //---------------------------------------------------
    static boolean turtleJump2(int A, int B, int T){
        if(A == 0 && B == 0) return T == 0;
        if(A == 0) return T <= 0 && T % B == 0;
        if(B == 0) return T >=0 && T % A == 0;

        int gcd = findGCD(A, B);
        return T % gcd == 0;
    }

    private static int findGCD(int a, int b) {
        if(b == 0)
            return a;
        return findGCD(b, a % b);
    }
    //5. I was asked paint fences type of question
    //Basically an array of fence heights was given and it could have 0s in it to
    // represent gaps in the fence. We have to paint the fence with horizontal and
    // vertical strokes what is the min number of strokes required to paint the fence

    //    public static void main(String[] args) {
    //        Scanner scn = new Scanner(System.in);
    //        int n = scn.nextInt();
    //        int[] arr = new int[n];
    //        for(int i=0;i<n;i++){
    //            arr[i] = scn.nextInt();
    //        }
    //        System.out.println(solve(arr, 0, n-1));
    //    }
    static int solve(int[] arr, int l, int r){
        if( l > r)
            return 0;

        while(l <=r && arr[l] == 0)
            l++;
        while(r>= l && arr[r] == 0)
            r--;

        if( l > r)
            return 0;

        int min = (int ) 1e9;
        for(int i=l;i<=r;i++){
            if(arr[i] == 0)
                continue;
            min = Math.min(min, arr[i]);
        }


        int i=l;

        int[] nums = arr.clone();
        int cnt =1;
        for(int k=l;k<=r;k++){
            if(nums[k] == 0)
                cnt++;
            else
                nums[k] -= min;
        }

        int Hstrokes = cnt * min;

        while(i<=r){
            if(nums[i] == 0) {
                i++;
                continue;
            }
            int j = i;

            while(j<=r && nums[j] != 0){
                j++;
            }

            Hstrokes += solve(nums, i, j-1);
            i=j;
        }
        int Vstrokes = 0;
        for(i=l;i<=r;i++){
            if(arr[i] == 0)
                continue;
            Vstrokes++;
        }
        return Math.min(Vstrokes, Hstrokes);
    }
    //6.You have a backend system that stores all versions of a JSON object. You need to reduce the
    //amount of data stored, how would you design the API.
    //I assumed they wanted to write a function to do a JSON diff of current state vs new state so
    //we only store the diff. Had no feedback whatsoever from the interviewer while working on it,
    //so I have no idea what they expected.

    class Solution{
        class DS{
            JSONObject object;
            int id;
            List<JSONObject> updates;
            DS(int id, JSONObject object){
                this.id = id;
                this.object = object;
                this.updates = new ArrayList<>();
            }
        }
         Map<Integer, DS> history = new HashMap<>();
    //        public  void main(String[] args) {
    //            JSONObject a = new JSONObject();
    //            JSONObject b = new JSONObject();
    //            a.put("name", "Aditya");
    //            a.put("id", 1);
    //
    //            add(1, a);
    //
    //            b.put("name", "Aditya");
    //            b.put("id", 2);
    //
    //            modify(1, b);
    //            System.out.println(getLatestVersion(1));
    //
    //        }
         void add(int id, JSONObject object){
            history.put(id, new DS(id, object));
        }
         void modify(int id, JSONObject object){
            DS ds = history.get(id);
            JSONObject diff = findDiff(ds.object, object);
            ds.updates.add(diff);
        }
         JSONObject getLatestVersion(int id){
            JSONObject object = history.get(id).object;
            List<JSONObject> changes = history.get(id).updates;
            JSONObject latest = new JSONObject(object.toString());

            for(JSONObject curr : changes){
                for(Iterator<String> it = curr.keys();it.hasNext();){
                    String key = it.next();
                    latest.put(key, curr.get(key));
                }
            }
            return latest;
        }
        JSONObject findDiff(JSONObject a, JSONObject b){
            JSONObject curr = new JSONObject();
            for (Iterator<String> it = a.keys(); it.hasNext(); ) {
                String key = it.next();

                if (b.has(key) && !a.get(key).equals(b.get(key))) {

                    curr.put(key, b.get(key));
                }

            }

            for (Iterator<String> it = b.keys(); it.hasNext(); ) {
                String key = it.next();
                if(a.get(key) == null)
                    curr.put(key, b.get(key));
            }

            return curr;
        }
    }
    //7. 24 game with expr-> https://leetcode.com/problems/24-game/
    class Solution1 {
        class Expr{
            double val;
            String exp;
            Expr(double val, String exp){
                this.val = val;
                this.exp = exp;
            }
        }
        boolean res;
        double epi = 1e-6;
        public boolean judgePoint24(int[] cards) {
            List<Expr> list = new ArrayList<>();
            for(int i=0;i<cards.length;i++){
                list.add(new Expr(cards[i], ""+cards[i]));
            }
            solve(list);
            return res;
        }
        void solve(List<Expr> cards){
            int n = cards.size();
            if(n == 1){
                if(Math.abs(cards.get(0).val - 24) < epi){
                    System.out.println(cards.get(0).exp);
                    res = true;
                }
            }

            for(int fn =0;fn<n;fn++){
                for(int sn =0;sn<n;sn++){
                    if(fn == sn)
                        continue;

                    Expr a = cards.get(fn);
                    Expr b = cards.get(sn);
                    for(Expr curr : getAllExprCombinations(a, b)){
                        List<Expr> next = new ArrayList<>();
                        next.add(curr);

                        for(int i=0;i<n;i++){
                            if( i== fn || i == sn)
                                continue;

                            next.add(cards.get(i));
                        }

                        solve(next);
                    }
                }
            }

        }
        List<Expr> getAllExprCombinations(Expr a, Expr b) {
            List<Expr> results = new ArrayList<>();
            double x = a.val, y = b.val;

            results.add(new Expr(x + y, "(" + a.exp + "+" + b.exp + ")"));
            results.add(new Expr(x - y, "(" + a.exp + "-" + b.exp + ")"));
            results.add(new Expr(y - x, "(" + b.exp + "-" + a.exp + ")"));
            results.add(new Expr(x * y, "(" + a.exp + "*" + b.exp + ")"));

            if (Math.abs(y) > 1e-6)
                results.add(new Expr(x / y, "(" + a.exp + "/" + b.exp + ")"));
            if (Math.abs(x) > 1e-6)
                results.add(new Expr(y / x, "(" + b.exp + "/" + a.exp + ")"));

            return results;
        }
    }
    //8.
    // you are given an array of houses in a neighbourhood in a city.
    //you have to rearrange houses in such a way that in a single neighbourhood the houses are sorted by number in ascending order and no 2 houses with same number are in same neighbourhood.
    //you can only rearrange house based on the capacity of each neighbourhood . If neighbourhood "1" in input has 2 houses then at output also it can only have 2 houses.
    //
    //For example-
    //{
    //{1,2},
    //{4,4,7,8},
    //{4,9,9,9}
    //}
    //
    //becomes
    //{
    //{4,9},
    //{1,2,4,9},
    //{4,7,8,9}
    //}
    List<int[]> solve(List<int[]> neighbours) throws Exception{
        int n = neighbours.size();
        Map<Integer, Integer> mp = new HashMap<>();
        for(int[] houses : neighbours){
            for(int house : houses){
                mp.put(house, mp.getOrDefault(house, 0)+1);
            }
        }

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if(b[1] != a[1])
                return b[1] - a[1];
            return a[0] - b[0];

        });

        for(var entry : mp.entrySet()){
            int key = entry.getKey();
            int cnt = entry.getValue();
            pq.add(new int[]{key, cnt});
            if(cnt > n)
                throw new Exception("Not possible");
        }

        for(int[] houses : neighbours){
            int size = houses.length;
            Queue<int[] > q = new LinkedList<>();
            if(pq.size() < size)
                throw new Exception("Not Possible");
            int index = 0;
            while(index < size){
                int[] curr = pq.remove();
                houses[index] = curr[0];
                curr[1] --;
                q.add(curr);
                index++;
            }
            while(!q.isEmpty()){
                int[] curr = q.remove();
                if(curr[1] >0)
                    pq.add(curr);
            }
            Arrays.sort(houses);
        }
        return neighbours;
    }
    //9.
    // Given a string, your task is to generate a list of substrings such that while appending all of the substrings in the list should give back the original string. If the resulting substring is not already present in the list, it should be added to the list.
    //Examples:
    //Input: "GOOOOOOGLE"
    //Output: ["G", "O", "OO", "OOO", "GL", "E"]
    //Input: "GOOOOOOGLEG"
    //Output: ["G", "O", "OO", "OOO", "GL", "E", "G"]
    //handling edge case
//    public static void main(String[] args) {
//        solve("GOOOOOOGLEG");
//    }
    void solve(String s){
        List<List<String>> res = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        solve(0, s, new ArrayList<>(), res, seen);
        for(List<String> curr : res){
            for (String str : curr){
                System.out.print(str +", ");
            }
            System.out.println();
        }
    }
    boolean solve(int i,  String s, List<String> ds, List<List<String>> res, Set<String> seen){

        if(i == s.length()){
            res.add(new ArrayList<>(ds));
            return true;
        }

        for(int j=i;j<s.length();j++){
            String str = s.substring(i, j+1);
            if(seen.contains(str))
                continue;
            ds.add(str);
            seen.add(str);
            if(solve(j+1, s, ds, res, seen))
                return true;
            ds.remove(ds.size()-1);
            seen.remove(str);
        }
        return false;
    }
    //10.
    // Given an array of integers, find a subarray with maximum sum?
    //Solved using Kadane's Algorithm
    //Follow up:
    //Given an array of integers nums, find indexes [i, j] such that the
    // subarray sum nums[i] + nums[i+1] ... nums[j-1] + nums[j] is maximum and nums[i] is equal to nums[j]

//    public static void main(String[] args) {
//        solve(new int[]{5, -100, -200, 5, 10});
//    }
    int solve(int[] arr){
        int n = arr.length;
        Map<Integer, int[]> mp = new HashMap<>();//num, prefixsum, index;
        int[] res = new int[]{-1, -1};

        int sum = 0, maxsum = (int) -1e9;
        for(int i=0;i<n;i++){
           sum += arr[i];
           if(mp.containsKey(arr[i])){
               int[] curr = mp.get(arr[i]);
               int ss = sum - curr[0] + arr[i];//removing prev sums
               if(maxsum < ss){
                   maxsum = ss;
                   res[0] = curr[1];
                   res[1] = i;
               }
           }else
               mp.put(arr[i], new int[]{sum, i});
        }
        System.out.println(maxsum);
        System.out.println(res[0]+" "+res[1]);
        return maxsum;
    }
    //11.
    // Consider a bank with some initial amount of money. Consider an array which represents
    // list of transactions which are going to come through customers. + means deposit -
    // means withdrawal. Bank can choose from which customer they want to start serving the customers and
    // they can refuse any number of customers. But once they start they have to serve till the time its
    // impossible to serve the customers. Maximize the total customers bank can serve.
    //Example :
    //Bank has 1 unit of money initially.
    //Customer transactions : [1, -3, 5, -2, 1]
    //answer = 3
    //Bank starts with customer with deposit of 5
    //1+ 5 = 6
    //6 - 2 = 4
    //4 + 1 =5
    //If bank starts at in index 0 can only serve 1 customer
    //1+1 =2
    //2-3 = -1 not possible
//    public static void main(String[] args) {
//        int[] arr = new int[]{1, -3, 5, -2, 1};
//        System.out.println(solve(arr, 1));
//    }
    int solve(int[] amount, int balance){
        int n = amount.length;
        int start = 0;
        int sum = balance;
        int max = 0;
        for(int i=0;i<n;i++){
            sum += amount[i];

            if(sum < 0){
                sum = balance;
                max = Math.max(max, i-start);
                start = i+1;
            }
        }
        max = Math.max(max, n-start);
        return max;
    }
    //12.
    // Given an array with N integers, starting index S and a value X.
    // You are playing game in which you start from S and first move is always odd.
    // If the move is odd you can jump to first index on the left which has value A[idx]+1.
    // If the move is even you the same on right side. Whenever we make a jump update the previous
    // position by X. Output the end position when you can not make any jump. If the game is
    // going to be infinite return -1 or return the end index.
    //From example
    //A = [3,4,2,2,7]
    //X = 4
    //S=2
    //we are at index 2, A[2] = 2; move is odd 2+1=3 exists at index 0 Array will become this
    //3,4,6,2,7
    //Now we are at index 0, make a jump because 4 exists on right (even move)
    //7,4,6,2,7
    //Now at index 1 4+1 5 doesnt exist on the right of 5
    //Answer is 1 final index.
    //A = [2,1]
    //X = 2
    //S = 1
    //This is an infinite case. Aditya ->no infinite case exist;
//    public static void main(String[] args) {
//        int[] arr ={2, 4, 3, 6, 1} ;
//        int s = 4;
//        int x = 4;
//        System.out.println(solve1(arr, s, x));
//
//    }
    int solve1(int[] arr, int s, int x){
        int n = arr.length;

        Set<String> vis = new HashSet<>();
        String key = "";
        char direction = 'l';
        while(!vis.contains(key)){
            boolean found = false;
            vis.add(s + ","+ arr[s]);
            if(direction == 'l'){
                for(int i=s-1;i>=0;i--){
                    if(arr[i] == arr[s]+1){
                        found = true;
                        arr[s] = x;
                        s = i;
                        direction = 'r';
                        break;
                    }
                }
            }else{
                for(int i=s+1;i<n;i++){
                    if(arr[i] == arr[s]+1){
                        found = true;
                        arr[s] = x;
                        s = i;
                        direction = 'l';
                        break;
                    }
                }
            }

            if(!found)
                return s;

        }
        return -1;
    }
    //13. A circle is define by x-axis position, y-axis position, and a
    //radius. A circle group is a collection of circles that overlap. Given a
    //list of circles, figure out if they belong to a single circle group
    //    public static void main(String[] args) {
    //        List<int[]> circles = Arrays.asList(
    //                new int[]{0, 0, 2},
    //                new int[]{2, 2, 2},
    //                new int[]{4, 0, 2},
    //                new int[]{6, 2, 2},
    //                new int[]{8, 0, 2}
    //        );
    //        // Expected: true (chained connection across all)
    //        System.out.println(isGroupFormed(circles));
    //    }
    boolean isGroupFormed(List<int[]> circles){
        int n = circles.size();
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<n;i++){
            for(int j = i+1;j<n;j++){
                if(isOverlap(circles.get(i), circles.get(j))){
                    adj.get(i).add(j);
                    adj.get(j).add(i);
                }
            }
        }

        int[] vis = new int[n];
        dfs(0,adj, vis);

        for(int i=0;i<n;i++){
            if(vis[i] == 0)
                return false;
        }
        return true;
    }
    private void dfs(int node, List<List<Integer>> adj, int[] vis) {
        vis[node] = 1;

        for(int adjNode : adj.get(node)){
            if(vis[adjNode] == 0){
                dfs(adjNode, adj, vis);
            }
        }
    }
     private boolean isOverlap(int[] a, int[] b) {
        double x1 = a[0];
        double y1 = a[1];
        double r1 = a[2];

        double x2 = b[0];
        double y2 = b[1];
        double r2 = b[2];

        double dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1-y2, 2));
        return dist <= r1 + r2;
    }
    //14.
    // For a rooted tree with any arbitary number of children for each node,
    //not necessarily n-ary tree.
    //Remove all the leaf nodes, and store them in a list, this would create
    //new leaf nodes. Repeat until all the nodes are removed
    //Conditions : Freshly created leaf nodes(node whose children are removed)
    //should not be removed just after its children are removed, unless
    //there's no other option for us, then we can remove it
//    public static void main(String[] args) {
//        NTree root = new NTree(1);
//        NTree node2 = new NTree(2);
//        NTree node3 = new NTree(3);
//        NTree node4 = new NTree(4);
//        NTree node5 = new NTree(5);
//        NTree node6 = new NTree(6);
//        NTree node7 = new NTree(7);
//        NTree node8 = new NTree(8);
//
//        root.children.addAll(List.of(node2, node3));
//        node2.children.add(node4);
//        node3.children.addAll(List.of(node5, node6));
//        node6.children.add(node7);
//        node7.children.add(node8);
//        for(int val : solve2(root)){
//            System.out.print(val +", ");
//        }
//    }
    List<Integer> solve2(NTree root){
        List<Integer> res = new ArrayList<>();
        Map<NTree, NTree> childParentMap = new HashMap<>();
        Map<NTree, Integer> parentCount = new HashMap<>();
        Queue<NTree> leafs = new LinkedList<>();
        buildMapAndFindLeaf(root, null, childParentMap, leafs, parentCount);
        while(!leafs.isEmpty()){

            NTree leaf = leafs.poll();
            NTree parent = childParentMap.get(leaf);

            res.add(leaf.val);

            if(parent != null){
                parentCount.put(parent, parentCount.getOrDefault(parent, 1)-1);
                if(parentCount.get(parent) == 0)
                    leafs.add(parent);
            }
        }
        return res;
    }
    private void buildMapAndFindLeaf(NTree root, NTree parent, Map<NTree, NTree> childParentMap, Queue<NTree> leafs, Map<NTree, Integer> parentCount) {
        if(root == null)
            return;

        childParentMap.put(root, parent);
        if(parent != null)
            parentCount.put(parent, parentCount.getOrDefault(parent, 0)+1);
        if(isLeaf(root)){
            leafs.add(root);
            return;
        }
        for(NTree child : root.children){
            buildMapAndFindLeaf(child, root, childParentMap, leafs, parentCount);
        }
    }
    private boolean isLeaf(NTree root) {
        return root.children.isEmpty();
    }

    //15. The Earliest Moment When Everyone Become Friends
    //Follow up is , according to query a, b can became frnd , or ab can unfrnd as well.
//    public static void main(String[] args) {
//        int[][] logs = {
//                {1, 0, 1, 1},  // 0-1
//                {2, 1, 2, 1},  // 1-2
//                {3, 2, 3, 1},  // 2-3
//                {4, 3, 4, 1},  // 3-4
//                {5, 2, 3, 0},  // break 2-3
//                {6, 3, 0, 1},  // now trying to connect 3 and 0 again
//        };
//        int V = 5;
//        System.out.println(minTime(logs, V));
//    }
    public int minTime(int[][] logs, int V) {
       DisjointSet set = new DisjointSet(V);

        int res = 0;
        Arrays.sort(logs, (a, b) -> a[0] - b[0]);
        List<int[]> activeEdges = new ArrayList<>();

        for (int[] log : logs) {
            int cost = log[0];
            int u = log[1];
            int v = log[2];
            int op = log[3];
            if(op == 1) {//add friend

                if (set.findUParent(u) == set.findUParent(v))
                    continue;

                res = Math.max(res, cost);
                set.unionBySize(u, v);
                activeEdges.add(new int[]{Math.max(u, v), Math.min(u, v)});
            }else{ // we need to break connection
                activeEdges.removeIf(edge -> edge[0] == Math.max(u, v) && edge[1] == Math.min(u, v));
                set.rebuild(V, activeEdges);
            }
        }
        int cnt = 0;
        for(int i=0;i<V;i++){
            if(set.parent.get(i) == i)
                cnt++;
        }

        return cnt == 1 ? res : -1;
    }
    //16.
    // Design a search data structure to store and display recent searches. If
    //a user just clicks the search bar without typing anything, it should
    //return the N most recent searches. Given a search string it should save
    //the search and also return the N most recent searches
//    public static void main(String[] args) {
//        Searcher searcher = new Searcher(5);
//        searcher.search("adb");
//        searcher.search("abz");
//        searcher.search("aby");
//        searcher.search("abd");
//        searcher.search("abe");
//        searcher.search("aby");
//
//        for(String s : searcher.search())
//            System.out.print(s +", ");
//    }
    static class Searcher{
        class DLLNode{
            String data;
            DLLNode prev, next;
            DLLNode(String data){
                this.data = data;
                prev = null;
                next = null;
            }
        }
        class LRU{
            DLLNode start, end;
            int totalSize;
            int currSize;
            Map<String, DLLNode> cache;
            public LRU(int n){
                this.totalSize = n;
                start = new DLLNode("-1");
                end = new DLLNode("-1");
                start.next = end;
                end.prev = start;
                currSize = 0;
                cache = new HashMap<>();
            }

            public void addNode(DLLNode node){
                if(cache.containsKey(node.data)){
                    DLLNode curr = cache.get(node.data);
                    removeNode(curr);
                    addNode(curr);
                }else {
                    currSize++;
                    if (currSize > totalSize) {
                        removeNode();
                    }
                    node.next = start.next;
                    node.prev = start;
                    start.next = node;
                    node.next.prev = node;
                    cache.put(node.data, node);
                }
            }

            public void removeNode(){

                if(start.next == end)
                    return;
                currSize--;
                DLLNode  curr = end.prev;
                end.prev = curr.prev;
                end.prev.next = end;
                cache.remove(curr.data);
            }
            public void removeNode(DLLNode node){

                if(start.next == end)
                    return;
                currSize--;
                DLLNode next = node.next;

                next.prev = next.prev.prev;
                next.prev.next = next;
                cache.remove(node.data);
            }

            public List<String> getAllUrls() {
                List<String > res = new ArrayList<>();
                DLLNode node = start.next;
                while(node != end){
                    res.add(node.data);
                    node = node.next;
                }
                return res;
            }
        }
        LRU lru;
        Searcher(int n){
            lru = new LRU(n);
        }
        public void search(String s){
            DLLNode node = new DLLNode(s);
            lru.addNode(node);
        }
        public List<String > search(){
            return lru.getAllUrls();
        }
    }
    //17. Given map {X=>123, Y=456}
    //Input: %X%_%Y%
    //Output: 123_456
    //Given map {USER=>admin, HOME=>/%USER%/home} Input: I am %USER% My home is %HOME% Output: I am admin My home is /admin/home
    //USER= bob
    //HOME= /home/%USER% should be substituted as : /home/bob ex2:
    //home/ %USER% -> /home/bob
    //Hello %USER% -> Hello bob!
    //ex3:
    //The user %USER% is at 50%% -> The user bob is at 50%
//    public  void main(String[] args) throws Exception {
//        Map<String, String> mp = new HashMap<>();
//        mp.put("user", "admin");
//        mp.put("home", "/%user%/home");
//        String str = "I am %user% My home is %home% 50%%";
//        Set<String> seen = new HashSet<>();
//        System.out.println(solve(mp, str, seen));
//    }

     String solve(Map<String, String> mp, String s, Set<String> seen) throws Exception {
        if(s == null || s.isEmpty())
            return "";
        if(seen.contains(s))
            throw new Exception("Circle exist");
        s = s.replace("%%", "%");
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        int i=0;
        while(i<n){
            if(i+1 < n && s.charAt(i) == '%' && s.charAt(i+1) != '%'){
                i+=1;
                int j = i;
                while(j < n && s.charAt(j) != '%')
                    j++;
                String str = solve(mp, mp.get(s.substring(i, j)), seen);
                sb.append(str);
                i = j;
            }else
                sb.append(s.charAt(i));
            i++;
        }
        return sb.toString();
    }
    //18.
    // You are given a list of user sessions where each user session has start and end times
    // both inclusive. Now, given a value N, find the count of all users at each point in time
    // from [0,N) i.e include 0 but exclude N.
    // Example:
    //Input:
    //[(0,3), (1,4) ] N=7
    //Output:
    //0->1
    //1->2
    //2->2
    //3->2
    //4->1
//    public static void main(String[] args) {
//        List<int[]> timestamps = List.of(
//                new int[]{0, 1},
//                new int[]{3, 4},
//                new int[]{6, 6}
//        );
//        int n = 8;
//
//
//
//        for(String s : findAllUsers2(timestamps, n)){
//            System.out.println(s);
//        }
//    }
    List<String> findAllUsers1(List<int[]> timestamp, int n){
        int[] diff = new int[n+1];
        for(int[] time : timestamp){
            int start = time[0];
            int end = time[1];
            diff[start] +=1;
            diff[end+1] =-1;
        }

        for(int i=1;i<=n;i++){
            diff[i] += diff[i-1];
        }
        List<String> res = new ArrayList<>();
        for(int i=0;i<=n;i++){
            if(diff[i] > 0)
                res.add(i+" -> "+ diff[i]);
        }
        return res;
    }
    //-----------------------------
    List<String> findAllUsers2(List<int[]> timestamp, int n){
        List<int[]> list = new ArrayList<>();

        for(int[] time : timestamp){
            int start = time[0];
            int end = time[1];
            list.add(new int[]{start,1});
            if(end +1  < n){
                list.add(new int[]{end+1, -1});
            }
        }
        list.sort((a, b) -> a[0] - b[0]);
        List<String> res = new ArrayList<>();
        int prevTime = 0, prevCount = 0;

        for(int[] entry : list){
            int time = entry[0];
            for(int i= prevTime; i< time && prevCount> 0;i++){
                res.add(i+" -> "+ prevCount);
            }
            prevCount += entry[1];
            prevTime = time;
        }
        for(int i = prevTime ;i<n && prevCount > 0;i++){
            res.add(i+" -> "+ prevCount);
        }
        return res;
    }

    //19. For example, [1,2,3,1,2,2]
    //Student 0 is taking exam 1
    //Student 1 is taking exam 2
    //Student 2 is taking exam 3
    //Student 3 is taking exam 1
    //Student 4 is taking exam 2
    //Student 5 is taking exam 2
    //Output would be a list with the students re-arranged.
    // An acceptable output for the above case would be [1,2,3,2,1,2].

    public static void main(String[] args) throws Exception {
        List<Integer> arr = new ArrayList<>(List.of(1,2,3,2,2,2));
        for(int val : seperateStudents(arr))
            System.out.print(val +", ");
    }

    static int[] seperateStudents(List<Integer> arr) throws Exception {
        int n = arr.size();
        Map<Integer, Integer> mp = new HashMap<>();
        for(int val : arr){
            mp.put(val, mp.getOrDefault(val, 0)+1);
        }

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);// val, count

        for(var entry : mp.entrySet()){
            int val = entry.getKey();
            int cnt = entry.getValue();

            pq.add(new int[]{val, cnt});
        }
        if(pq.isEmpty())
            throw new Exception("Not possible");

        int[] res = new int[n];
        res[0] = pq.peek()[0];
        pq.peek()[1]--;

        for(int i=1;i<n && !pq.isEmpty();i++){
            int[] first = pq.poll();
            int[] second = pq.poll();
            if(res[i-1] == first[0]){
                if(second == null)
                    throw new Exception("Not possible");
                res[i] = second[0];
                second[1] --;
            }else{
                res[i] = first[0];
                first[1] --;
            }
            if(first[1] > 0)
                pq.add(first);
            if(second != null && second[1] > 0)
                pq.add(second);
        }
        return res;
    }



}
