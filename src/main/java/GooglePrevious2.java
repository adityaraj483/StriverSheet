import DS.*;

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
    // Constraints -
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
//    public static void main(String[] args) {
//        System.out.println("start");
//        TripletFinder tripletFinder = new TripletFinder(5);
//        Scanner sc = new Scanner(System.in);
//        float val = 0;
//        while((val = sc.nextFloat()) != -1){
//            tripletFinder.addNumber(val);
//        }
//    }
    class TripletFinder{
        float distance;
        TreeSet<Float> stream;
        TripletFinder(float distance){
            this.distance = distance;
            this.stream = new TreeSet<>();
        }

        void addNumber(float num){
            if(checkTripletWithDistance(num)==false)
                stream.add(num);
        }

        private boolean checkTripletWithDistance(Float num) {
            float from = num - distance;
            float to = num +  distance;
            NavigableSet<Float> list = stream.subSet(from, true, to, true);
            if(list.size() >=2){
                Iterator<Float> it = list.iterator();
                float first = it.next();

                while(it.hasNext()){

                    float last = it.next();

                    if(num - distance <= first && last <= num + distance) {

                        System.out.println(first + ", " + num + ", " + last);

                        stream.remove(first);
                        stream.remove(last);
                    } else {
                        first = last;
                    }
                }
            }
            return false;
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
//        System.out.println(turtleJump1(4, 1, -8));
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
//        System.out.println(paintFence(arr, 0, n-1));
//    }
    static int paintFence(int[] arr, int l, int r){
        if( l > r)
            return 0;

        while(l <=r && arr[l] == 0)
            l++;
        while(r >= l && arr[r] == 0)
            r--;

        if( l > r)
            return 0;

        int Vstrokes = 0;
        for(int i=l;i<=r;i++){
            if(arr[i] == 0)
                continue;
            Vstrokes++;
        }

        int min = (int ) 1e9;
        for(int i=l;i<=r;i++){
            if(arr[i] == 0)
                continue;
            min = Math.min(min, arr[i]);
        }

        int cnt = 1;
        int prev = -1;
        for(int k = l;k <= r;k ++){
            if(arr[k] != 0)
                arr[k] -= min;

            if(prev != 0 && arr[k] == 0){
                cnt++;
            }

            prev = arr[k];
        }

        int Hstrokes = cnt * min;
        Hstrokes += paintFence(arr, l, r);

        return Math.min(Vstrokes, Hstrokes);
    }
    //6.You have a backend system that stores all versions of a JSON object. You need to reduce the
    //amount of data stored, how would you design the API.
    //I assumed they wanted to write a function to do a JSON diff of current state vs new state so
    //we only store the diff.

//    public static void main(String[] args) {
//        Map<String, String> curr = new HashMap<>();
//
//        curr.put("Aditya", "Raj");
//        Solution sln = new Solution(curr, 10);
//
//        Map<String, String> curr1 = new HashMap<>();
//        curr1.put("Aditya", "Kishan");
//        curr1.put("age", "20");
//
//        sln.pushChanges(curr1);
//        System.out.println(sln.getLatestVersion());
//    }

    static class Solution{
       List<Map<String, String>> data;
       int fullVersion;
       Solution(Map<String, String> currState, int fullVersion){
           this.data = new ArrayList<>();
           this.fullVersion = fullVersion;
           this.data.add(currState);
       }

       void pushChanges(Map<String, String> changes){
           this.data.add(changes);

           if(data.size() > 1 && data.size() % fullVersion == 0){
               buildFullVersion((data.size()/ fullVersion -1) * fullVersion);
           }
       }

        private void buildFullVersion(int i) {
           Map<String, String> fullVersionMap = new HashMap<>();
           while(i < data.size()){
               fullVersionMap.putAll(data.get(i++));
           }
           data.remove(data.size() -1);
           data.add(fullVersionMap);
        }

        Map<String, String> getLatestVersion(){
           if(data.isEmpty())
               return Collections.emptyMap();

           int index = (data.size() / fullVersion) * 10;

           Map<String, String> currState = new HashMap<>();
           for(int i=index;i<data.size();i++){
               currState.putAll(data.get(i));
           }
           return currState;
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
    // you have to rearrange houses in such a way that in a single neighbourhood the houses are sorted by number in
    // ascending order and no 2 houses with same number are in same neighbourhood.
    // you can only rearrange house based on the capacity of each neighbourhood . If neighbourhood "1" in input has 2
    // houses then at output also it can only have 2 houses.
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
        });//house, count

        for(var entry : mp.entrySet()){
            int key = entry.getKey();
            int cnt = entry.getValue();
            pq.add(new int[]{key, cnt});
            if(cnt > n)
                throw new Exception("Not possible");
        }

        List<int[]> res = new ArrayList<>();
        for(int[] houses : neighbours){
            int size = houses.length;
            Queue<int[] > q = new LinkedList<>();

            if(pq.size() < size)
                throw new Exception("Not Possible");

            int index = 0;
            int[] newHouse = new int[size];

            while(index < size){
                int[] curr = pq.remove();
                newHouse[index] = curr[0];
                curr[1] --;
                if(curr[1] > 0)
                    q.add(curr);
                index++;
            }
            pq.addAll(q);
            Arrays.sort(newHouse);
            res.add(newHouse);
        }
        return res;
    }
    //9.
    // Given a string, your task is to generate a list of substrings such that while appending all of the
    // substrings in the list should give back the original string. If the resulting substring is not already present
    // in the list, it should be added to the list.
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
    // Solved using Kadane's Algorithm
    // Follow up:
    // Given an array of integers nums, find indexes [i, j] such that the
    // subarray sum nums[i] + nums[i+1] ... nums[j-1] + nums[j] is maximum and nums[i] is equal to nums[j]

//    public static void main(String[] args) {
//        solve(new int[]{5, -100, -200, 5, 10});
//    }
    static int solve(int[] arr){
        Map<Integer, List<Integer>> valueIndexPrefixMap = new HashMap<>();//val, preSum

        int sum = 0;
        int res = 0;

        for(int i=0;i<arr.length;i++){
            sum += arr[i];
            if(valueIndexPrefixMap.containsKey(arr[i])){

                for(int prevSum : valueIndexPrefixMap.get(arr[i])){
                    int currSum = sum - prevSum + arr[i];
                    res = Math.max(currSum, res);
                }
            }
            valueIndexPrefixMap.computeIfAbsent(arr[i] , e -> new ArrayList<>()).add(sum);
        }

        return res;
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
    //1 + 5 = 6
    //6 - 2 = 4
    //4 + 1 = 5
    //If bank starts at in index 0 can only serve 1 customer
    //1 + 1 = 2
    //2 - 3 = -1 not possible
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
    static int solve1(int[] arr, int s, int x){
        Map<Integer, TreeSet<Integer>> map = new HashMap<>();
        for(int i=0;i<arr.length;i++)
            map.computeIfAbsent(arr[i], e -> new TreeSet<>()).add(i);

        Set<String> vis = new HashSet<>();
        String key = "";

        boolean left  = true;
        while(!vis.contains(key)){


            vis.add(key);
            key = left +", "+ s;


            if(left){
                if(map.containsKey(arr[s]+1)){
                    Integer newS = map.get(arr[s]+1).floor(s);
                    if(newS == null || newS.equals(s))
                        return s;

                    map.get(arr[s]).remove(s);
                    map.computeIfAbsent(x, e -> new TreeSet<>()).add(s);
                    arr[s] =  x;
                    s = newS;
                }else
                    return s;

                left = false;
            }else{

                if(map.containsKey(arr[s]+1)){
                    Integer newS = map.get(arr[s]+1).ceiling(s);
                    if(newS == null || newS.equals(s))
                        return s;

                    map.get(arr[s]).remove(s);
                    map.computeIfAbsent(x, e -> new TreeSet<>()).add(s);
                    arr[s] = x;
                    s = newS;

                }else
                    return s;
                left = true;
            }

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
    static List<Integer> solve2(NTree root){
        List<Integer> res = new ArrayList<>();
        Map<NTree, NTree> childParentMap = new HashMap<>();
        Queue<NTree> leafs = new LinkedList<>();
        buildMapAndFindLeaf(root, null, childParentMap, leafs);

        while(!leafs.isEmpty()){

            NTree leaf = leafs.poll();
            NTree parent = childParentMap.get(leaf);

            res.add(leaf.val);

            if(parent != null){
                parent.children.remove(leaf);
                if(parent.children.isEmpty())
                    leafs.add(parent);
            }
        }
        return res;
    }
    static private void buildMapAndFindLeaf(NTree root, NTree parent, Map<NTree, NTree> childParentMap, Queue<NTree> leafs) {
        if(root == null)
            return;

        childParentMap.put(root, parent);

        if(isLeaf(root)){
            leafs.add(root);
            return;
        }
        for(NTree child : root.children){
            buildMapAndFindLeaf(child, root, childParentMap, leafs);
        }
    }
    static private boolean isLeaf(NTree root) {
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

            List<String> addNode(DLLNode node){
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
                return getAllUrls();
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
        public void clickSearchBar(){
            lru.getAllUrls();
        }
        public List<String > search(){
            return lru.getAllUrls();
        }
    }
    //17. Given map {X=>123, Y=456}
    //Input: %X%_%Y%
    //Output: 123_456
    //Given map {USER=>admin, HOME=>/%USER%/home} Input: I am %USER% My home is %HOME% Output:
    // I am admin My home is /admin/home
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
            return "Not Found";
        if(seen.contains(s))
            throw new Exception("Circle exist");

        s = s.replace("%%", "#");
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        int i=0;
        while(i<n){
            if( s.charAt(i) == '%'){
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
        return sb.toString().replace("#", "%%");
    }
    //18.
    // You are given a list of user sessions where each user session has start and end times
    // both inclusive. Now, given a value N, find the count of all users at each point in time
    // from [0,N) i.e include 0 but exclude N.
    // Example:
    //Input:
    //[(0,3), (1,4) ] N=7
    //Output:
    //0 -> 1
    //1 -> 2
    //2 -> 2
    //3 -> 2
    //4 -> 1
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
        int[] diff = new int[n];
        for(int[] time : timestamp){
            int start = time[0];
            int end = time[1];
            diff[start] +=1;
            if(end < n)
                diff[end+1] =-1;
        }

        for(int i=1;i<n;i++){
            diff[i] += diff[i-1];
        }
        List<String> res = new ArrayList<>();
        for(int i=0;i<n;i++){
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

        for(int[] entry : list) {
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

//    public static void main(String[] args) throws Exception {
//        List<Integer> arr = new ArrayList<>(List.of(1,2,3,2,2,2));
//        for(int val : seprateStudents(arr))
//            System.out.print(val +", ");
//    }

    static int[] seperateStudents(List<Integer> arr) throws Exception {
        int n = arr.size();
        if(n == 0)
            return new int[0];
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

        int[] res = new int[n];
        int prev = -1;

        for(int i=0;i<n && !pq.isEmpty();i++){

            int[] first = pq.poll();
            int[] second = pq.poll();

            if(prev == first[0]){
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
            prev = res[i];
        }
        return res;
    }

    //20.
    // You work as a consultant and have clients in cityA and cityB. On a given day,
    ////    say i, you can either
    ////    work in cityA and make Ai dollars or you can work in cityB and make Bi dollars. You can also spend
    ////    the day traveling between cityA and cityB in which case your earnings that day are 0.
    ////    Given Al,A2, ....An and B1, B2,....., Bn, return a schedule S of N days which maximizes your earnings,
    ////    where S is a string of length N, and Si = A/B/T where A means work in cityA, B means work in cityB
    ////    T means travel on day i. You can start either in cityA or cityB.
    ////    Example1: A = [23, 4,5 ,10] B = [21,1,10, 100] The optimal schedule S here would be ->"ATBB"

//    public static void main(String[] args) {
//        int[] arr1 = {23, 4, 5, 100};
//        int[] arr2 = {21, 1, 10, 100};
//        int n = arr1.length;
//
//        dp = new Integer[n+1][3];
//        pathdp = new String[n+1][3];
//        int a = solve(0, arr1, arr2, n, 1);
//        int b = solve(0, arr1, arr2, n, 2);
//        if(a >= b){
//            System.out.println(a + " : "+ pathdp[0][1]);
//        }else
//            System.out.println(b + " : "+ pathdp[0][2]);
//    }
    Integer[][] dp;
    String[][] pathdp;

    int solve(int index, int[] arr1, int[] arr2, int n, int currArray){

        if(index >=n)
            return 0;

        if(dp[index][currArray] != null) return dp[index][currArray];

        int currVal = currArray == 1 ? arr1[index] : arr2[index];
        int option1 = currVal + solve(index+1, arr1, arr2, n, currArray);// We go to same array

        int otherArray = currArray ==1 ? 2 : 1;
        int option2 = currVal;// we try to go to next array
        if(index+2 <= n) {
            option2 += solve(index+2, arr1, arr2, n, otherArray);
        }

        if(option1 >= option2){
            dp[index][currArray] = option1;
            String path1 = (currArray == 1 ? "A": "B") + (pathdp[index+1][currArray] == null ? "" : pathdp[index+1][currArray]);
            pathdp[index][currArray] = path1;
        }else {
            dp[index][currArray] = option2;
            String path2 = (currArray == 1 ? "A" : "B") + "T" + (pathdp[index+2][otherArray] == null ? "" : pathdp[index+2][otherArray]);
            pathdp[index][currArray] = path2;
        }
        return dp[index][currArray];
    }

    //21.Give a list of string, where every string in the list is of size 5.
    // Return the list of 5 string such that all the characters in each of the strings are unique
    //i.e if we combine all the strings(not necessary) we will have 25 unique characters)
    //eg
    //Input explanation
    //List of string with length of 5 each
    //intput = ["abcde", "fghij", "klmno",
    // "pqrst", "uvwxy", "zabcd", "apple", "zebra", "ocean",
    // "quick", "world", "jumps", "foxes", "liver"]
//    public static void main(String[] args) {
//        String[] arr = {"abcde", "fghij", "klmno", "pqrst", "uvwxy", "zabcd", "apple", "zebra", "ocean", "quick", "world", "jumps", "foxes", "liver"};
//        List<String> ds = new ArrayList<>();
//        System.out.println(solve(arr, 5, ds, new HashSet<>()));
//        for(String s: ds){
//            System.out.print(s +", ");
//        }
//    }
    static boolean solve(String[] arr, int count, List<String> ds, Set<Character> seen){
        if( count == 0)
            return true;

        for(String s : arr){

            if(isValid(s, seen)){
                ds.add(s);
                addToSeen(seen, s);
                if(solve(arr, count-1, ds, seen))
                    return true;
                ds.remove(ds.size()-1);
                removeFromSeen(seen, s);
            }
        }
        return false;
    }
    static void removeFromSeen(Set<Character> seen, String s){
        for(char ch : s.toCharArray()){
            seen.remove(ch);
        }
    }
    static void addToSeen(Set<Character> seen, String s){
        for(char ch : s.toCharArray()){
            seen.add(ch);
        }
    }
    static boolean isValid(String s, Set<Character> set){
        for(char ch : s.toCharArray()){
            if(set.contains(ch))
                return false;
        }
        return true;
    }

    //22. sort all odd elements and leave even elements as it is at their original position
    // we can use selection sort here.
//    public static void main(String[] args) {
//        int[] arr = {5, 8, 6, 3, 4, 1, 7};
//        sortOddInPlace(arr);
//    }
    public void sortOddInPlace(int[] arr) {
        int n = arr.length;

        // Use selection sort-like logic but only for odd numbers
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] % 2 == 0) continue; // skip even numbers

            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] % 2 != 0 && arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            // Swap only if different index
            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
            }
        }
        for (int val : arr)
            System.out.print(val +", ");
    }
    //23.Give the count of managers who has salary less than average salary of direct and indirect employees
    //Example:
    //A->B, A->C, A->D, B->E
    //Salaries
    //A = 50000
    //B = 20000
    //C = 10000
    //D = 10000
    //E = 25000
    //Answer: 1
    //Explanation: A is the manager of direct employees B, C, D and indirect
    // employee E so avg. is 16,250 and B = 20000 < E = 25000 so answer is B

//    public static void main(String[] args) {
//        int[][] edges = {{0,1}, {0,2}, {1,3}, {1,4}, {0, 4}};
//        int[] salary = {20, 15, 30, 50, 40};
//
//        findManagers(edges, salary);
//    }

    private static void findManagers(int[][] edges, int[] salary) {
        int V = salary.length;
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }

        for(int[] edge : edges){
            adj.get(edge[0]).add(edge[1]);
        }
        int[] emp = new int[V];
        Arrays.fill(emp, -1);

        for(int i=0;i<V;i++){
            if(emp[i] == -1){
                dfs(i, adj, salary, emp);
            }
        }

        for(int i=0;i<V;i++){
            if(emp[i] == 1) {
                char employee = (char) ('A' + i);
                System.out.print(employee +", ");
            }
        }
    }

    static Set<Integer> dfs(int node, List<List<Integer>> adj, int[] salary, int[] emp){
        Set<Integer> employeesList = new HashSet<>();
        employeesList.add(node);
        for(int adjNode : adj.get(node)){
            Set<Integer> list = dfs(adjNode, adj, salary, emp);
            employeesList.addAll(list);
        }

        int sum = employeesList.stream().map(ele -> salary[ele]).reduce(0, Integer::sum);

        int cnt = employeesList.size();

        if(salary[node] < 1D*sum / cnt){
            emp[node] = 1;
        }

        return employeesList;
    }
    //24. There is a robot at location (0, 0) of a 10x10 grid of tiles.
    // Each tile can be one of 8 different colors: (0, 1, ... 7). There is a star at a known location
    // (marked with the color -1) on the grid. You can program the robot by giving it a lookup
    // table of color to direction. The robot will sense the color of the tile it is currently on,
    // and move in the direction (up, down, left, or right) specified by the lookup table you provided.
    // Output a lookup table that guides the robot to the star, if such a table is possible.
    //Small example grid: [[(0), 1, 0, 0], [3, 2,-1, 3], [0, 0, 0, 2], [0, 0, 0, 4]]

//    public static void main(String[] args) throws Exception {
//        int[][] mat = {
//                {0, 1, 0, 0},
//                {3,2,-1,3},
//                {0,0,0,2},
//                {0,0,0,4}
//        };
//        Map<Integer, String> dir = new HashMap<>();
//        int[][] vis = new int[4][4];
//        boolean res = solve(mat, 0,0, 4, dir, vis);
//
//        if(res) {
//            for (var entry : dir.entrySet()) {
//                System.out.println(entry.getKey() + " -> " + entry.getValue());
//            }
//        }
//    }

    static boolean solve(int[][] mat, int row, int col, int n, Map<Integer, String> mp, int[][] vis) throws Exception {
        if(row >= n || row < 0 || col >= n || col < 0 || vis[row][col] == 1)
            return false;
        int currVal = mat[row][col];
        if(currVal == -1)
            return true;
        vis[row][col] = 1;

        if(mp.containsKey(currVal)){
            String dir = mp.get(currVal);
            switch (dir) {
                case "U":
                    return solve(mat, row - 1, col, n, mp, vis);
                case "D":
                    return solve(mat, row + 1, col, n, mp, vis);
                case "L":
                    return solve(mat, row, col - 1, n, mp, vis);
                case "R":
                    return solve(mat, row, col + 1, n, mp, vis);
            }
            throw new Exception("Invalid");
        }else{

            mp.put(currVal, "R");
            if(solve(mat, row, col+1, n, mp, vis))
                return true;
            mp.remove(currVal);

            mp.put(currVal, "D");
            if(solve(mat, row+1, col, n, mp, vis))
                return true;
            mp.remove(currVal);

            mp.put(currVal, "U");
            if(solve(mat, row-1, col, n, mp, vis))
                return true;
            mp.remove(currVal);

            mp.put(currVal, "L");
            if(solve(mat, row, col-1, n, mp, vis))
                return true;
            mp.remove(currVal);

        }
        vis[row][col] = 0;
        return false;
    }

    //25. There is a stream of integers. Every time you see a new element in the stream,
    // return the mean value of the last N elements, excluding the largest K elements.
    //Example:
    //N=5
    //K=2
    //elements so far = [20, 2, -2, 0, 10, 1, 5, -2, 0]
    //last N elements: [10, 1, 5, -2, 0] largest K elements: [10, 5]
    //result = (1+(-2)+0)/3 = -0.3333333
//    public static void main(String[] args) {
//        int N = 5, K = 2;
//        int[] arr = {1,2,3};
//        Solution11 sln = new Solution11(N, K);
//        for(int val : arr){
//            sln.add(val);
//        }
//        System.out.println(sln.findMean());
//    }
    class Solution11{
        int totalSize;
        int k;
        int leftCount;
        int rightCount;
        int leftSum ;
        TreeMap<Integer, Integer> leftMap;// large elements on top
        TreeMap<Integer, Integer> rightMap;// small elements on top
        Queue<Integer> stream;
        Solution11(int n, int k){

            this.k = k;
            this.totalSize = n;
            leftMap = new TreeMap<>((a, b) -> b-a);
            rightMap = new TreeMap<>((a, b) -> a- b);
            leftCount =0;
            rightCount = 0;
            leftSum = 0;
            stream = new LinkedList<>();
        }

        Double add(int num){

            stream.add(num);
            rightMap.put(num, rightMap.getOrDefault(num, 0)+1);
            rightCount++;

            if(stream.size() > totalSize){
                int lastVal = stream.remove();
                remove(lastVal);
            }
            balance();
            return findMean();
        }

        void balance() {

            while(rightCount < k && leftCount > 0){
                int val = leftMap.firstKey();
                leftMap.put(val, leftMap.get(val)-1);
                if(leftMap.get(val) ==0)
                    leftMap.remove(val);

                leftCount--;
                leftSum -= val;

                rightMap.put(val, rightMap.getOrDefault(val, 0)+1);
                rightCount++;
            }

            while(rightCount > k){
                int val = rightMap.firstKey();
                rightMap.put(val, rightMap.get(val)-1);
                if(rightMap.get(val) == 0)
                    rightMap.remove(val);
                rightCount--;

                leftMap.put(val, leftMap.getOrDefault(val, 0)+1);
                leftSum += val;
                leftCount++;
            }

        }

        double findMean() {
            if(stream.size() <= k)
                return -1;
            return 1D*leftSum/(leftCount);
        }
        void remove(int num) {
            if(leftMap.containsKey(num)){
                leftMap.put(num, leftMap.get(num) -1);
                if(leftMap.get(num) == 0)
                    leftMap.remove(num);

                leftSum -= num;
                leftCount--;
            }else{
                rightMap.put(num, rightMap.get(num) -1);
                if(rightMap.get(num) == 0){
                    rightMap.remove(num);
                }
                rightCount--;
            }
            balance();
        }
    }

    //26. Find the length of longest increasing subsequence such that the difference between
    // consecutive elements in LIS is an increasing sequence
    //Example :
    //nums -> 1 2 3 4 5 6 ans -> 3
    //Explanation : the best LIS can be gotten if we take 1 , 2 , 4 ( in this way )
//    public static void main(String[] args) {
//        int[] arr= {1, 3, 6, 10, 15};
//        System.out.println(findLIS(arr));
//    }

    static int findLIS(int[] arr){
        int n = arr.length;
        int[] dp = new int[n];
        int[] lastDiff = new int[n];
        Arrays.fill(lastDiff, -1);
        Arrays.fill(dp, 1);
        int res = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = arr[i] - arr[j];
                if (arr[i] > arr[j] && (lastDiff[j] == -1 || diff > lastDiff[j])) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        lastDiff[i] = diff;
                    }
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    //27. find sum when we can use bracket as well for 1234-> (1+2) * (3+4) = 21
//    public static void main(String[] args) {
//        for(int val : solve1("22", 21))
//            System.out.print(val+", ");
//    }
    static Map<String, List<Integer>> memo = new HashMap<>();
    static public List<Integer> solve1(String exp, int target) {
        if(memo.containsKey(exp)) return memo.get(exp);
        List<Integer> res = new ArrayList<>();

        for(int i=1;i<exp.length();i++){
            List<Integer> left = solve1(exp.substring(0, i), target);
            List<Integer> right = solve1(exp.substring(i), target);
            for(int k=0;k<3;k++) {

                char op ;
                if( k == 0)
                    op ='+';
                else if(k== 1)
                    op = '-';
                else
                    op = '*';

                for (int l : left) {
                    for (int r : right) {
                        int val = operations(l, r, op);
                        res.add(val);
//                        if(val == target)
//                            res = true;
                    }
                }

            }
        }
        if(res.isEmpty() && !exp.isEmpty()){
            int val = Integer.parseInt(exp);
            res.add(val);
        }
        memo.put(exp, res);
        return res;
    }
    static int operations(int x, int y, char op){
        switch(op){
            case '+' :
                return x + y;
            case '-' :
                return x - y;
            case '*':
                return x * y;
        }
        return 0;
    }

    //28.
    // You have a stream of rpc requests coming in. Each log is of the
    //form {id, timestamp, type(start/end)}. Given a timeout T, you need to figure out at
    // the earliest possible time if a request
    //has timed out.
    //Eg :
    //id - time - type
    //0 - 0 - Start
    //1 - 1 - Start
    //0 - 2 - End
    //2 - 6 - Start
    //1 - 7 - End
    //Timeout = 3
    //Ans : {1, 6} ( figured out id 1 had timed out at time 6 )
//    public static void main(String[] args) {
//        List<Log> logs = new ArrayList<>();
//        logs.add(new Log(0, 0, "start"));
//        logs.add(new Log(1, 1, "start"));
//        logs.add(new Log(0, 2, "end"));
//        logs.add(new Log(2, 6, "start"));
//        logs.add(new Log(1, 7, "end"));
//
//        int[] res = findFirstIdTimeOut(logs, 3);
//        System.out.println(res[0] +" , "+res[1]);
//    }

    static int[] findFirstIdTimeOut(List<Log> logs, int timeout){

        Queue<Log> inProcess = new LinkedList<>();
        Set<Integer> processed = new HashSet<>();
        int i = 0;
        while(i < logs.size()){
            Log log = logs.get(i);
            while(!inProcess.isEmpty() && inProcess.peek().timestamp + timeout < log.timestamp){

                if(!processed.contains(inProcess.peek().id))
                    return new int[]{inProcess.peek().id, log.timestamp};

                inProcess.remove();
            }

            if(log.type.equals( "end"))
                processed.add(log.id);
            else
                inProcess.add(log);
            i++;
        }

        return new int[]{-1, -1};
    }

    //28.
    // You're given a 2D matrix (or grid), where:
    //"X" represents land
    //"." represents water
    //Water cells (".") can be either:
    //Ocean → if it touches the border of the matrix or can reach the border through other water cells.
    //Lake → if it’s completely surrounded by land on all sides (i.e., not connected to the border through any path of .)
    //Now, a cell is a Coast if:
    //It’s a land cell ("X")
    //And it is adjacent (4-directionally) to an ocean cell ("." that is unbounded)
//    public static void main(String[] args) {
//        char[][] grid = {
//                {'.', 'X', '.'},
//                {'X', 'X', 'X'},
//                {'.', 'X', '.'}
//        };
//        solution2 sln = new solution2(grid);
//        System.out.println(sln.isCost(1,1));
//    }

    class solution2{
        char[][] mat;
        int rows, cols;
        int[] delRow = {-1, 0, 1,0};
        int[] delCol = {0, 1, 0, -1};
        solution2(char[][] mat){
            //X -land
            //L- lake
            //O - ocean
            this.mat = mat;
            rows = mat.length;
            cols = mat[0].length;
            buildOceanAndLake(mat);
        }

        boolean isCost(int row , int col){
            if(row == 0 || row == rows-1 || col == 0 || col == cols-1)// if X is at the edge of matrix
                return true;
            for(int i=0;i<4;i++){
                int r = delRow[i] + row;
                int c = delCol[i] + col;
                if(r >=0 && r < rows && c >=0 && c < cols && mat[r][c] == 'O'){
                    return true;
                }
            }
            return false;
        }

        private void buildOceanAndLake(char[][] mat) {

            for(int row=0;row<rows;row++){
                if(mat[row][0] == '.')
                    dfs(row, 0);

                if(mat[row][cols-1] == '.')
                    dfs(row, cols-1);
            }

            for(int col=0;col<cols;col++){
                if(mat[0][col] == '.')
                    dfs(0, col);

                if(mat[rows-1][col] == '.')
                    dfs(rows-1, col);
            }


            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(mat[i][j] == '.'){
                        mat[i][j] = 'L';
                    }
                }
            }
        }

        void dfs(int row, int col){
            mat[row][col] = 'O';


            for(int i=0;i<4;i++){

                int r = row + delRow[i];
                int c = col + delCol[i];

                if(r >=0 && r < rows && c >=0 && c < cols && mat[r][c] == '.'){
                    dfs(r, c);
                }
            }
        }
    }
    //29.Given is a 2D array that describes the height of a landscape and the
    // location of 2 cities within this 2D array. I am now looking for the highest
    // position to place a water tower there so that both cities can be supplied with water.
    //Rules:
    //The pipes of the tower are not allowed to run diagonally
    //The pipes must always slope downwards (i.e. be lower than the previous cell) or be at the same height, otherwise the water would run upwards
//    public static void main(String[] args) {
//        int[][] mat = {
//                {5, 4, 3},
//                {6, 3, 2},
//                {7, 4, 1}
//        };
//        int[] town1 = {2, 0}; // bottom-left
//        int[] town2 = {2, 2}; // bottom-right
//        System.out.println(findHeight(mat, town1, town2));
//    }
    static int findHeight(int[][] mat, int[] town1, int[] town2){
        int n = mat.length;
        int m = mat[0].length;
        int[][] vis1 = new int[n][m];
        int[][] vis2 = new int[n][m];
        dfs(mat, town1[0], town1[1], vis1);
        dfs(mat, town2[0], town2[1], vis2);
        int res = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(vis1[i][j] + vis2[i][j] == 2){
                    res = Math.max(res, mat[i][j]);
                }
            }
        }
        return res;
    }
    static void dfs(int[][] mat, int row, int col, int[][] vis){

        int n = mat.length;
        int m = mat[0].length;
        int[] delRow = {-1,0,1,0};
        int[] delCol ={0,1,0,-1};

        vis[row][col] = 1;
        for(int i=0;i<4;i++){
            int r = row + delRow[i];
            int c = col + delCol[i];

            if( r >=0 && r < n && c>=0 && c < m && vis[r][c] == 0 && mat[r][c] >= mat[row][col]){
                dfs(mat, r, c, vis);
            }
        }
    }
    //30.
    // Given a string, you have to return the first word in the string having the most number of
    // repeating characters.
    //Example:
    //"Today is the greatest day ever!"
    //Answer:
    //"greatest."
//    public static void main(String[] args) {
//        String s = "Today is the greatest day ever!";
//        System.out.println(findWord(s));
//    }
    static String findWord(String s){
        String res = "";
        int maxTill = 0;
        String[] arr = s.split(" ");
        Map<Character, Integer> mp = new HashMap<>();
        for(String word : arr){

            for(char ch : word.toCharArray()){
                mp.put(ch, mp.getOrDefault(ch, 0)+1);
                if(mp.get(ch) > maxTill){
                    res = word;
                    maxTill = mp.get(ch);
                }
            }
            mp.clear();

        }
        return res;
    }
    //31.
    // You are given a struct Block in C++ that represents the time during which a
    // person is busy, with attributes: personId, startTime, and endTime. You are also
    // given an integer totalTime which represents the total duration. The task is to
    // find the time intervals during which all the persons are free.
//    public static void main(String[] args) {
//        List<Person> list = new ArrayList<>();
//        list.add(new Person(1, 0, 2));
//        list.add(new Person(2, 0, 4));
//        list.add(new Person(3, 0, 1));
//        list.add(new Person(4, 8, 10));
//        for(int[] arr : findFreeTime(list, 12)){
//            System.out.println(arr[0] +" -> " + arr[1]);
//        }
//    }
    static class Person{
        int personId, startTime, endTime;
        Person(int personId, int startTime, int endTime){
            this.personId = personId;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    static List<int[]> findFreeTime(List<Person> list, int totalTime){

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);// time, +-1

        for(Person person : list){
            int startTime = person.startTime;
            int endTime = person.endTime;
            pq.add(new int[]{startTime, 1});

            if(endTime+1 <= totalTime){
                pq.add(new int[]{endTime+1, -1});
            }
        }

        List<int[]> res = new ArrayList<>();

        int prevCnt = 0, prevTime = 0;

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            int currTime = curr[0];

            if(prevTime+1 <= currTime-1 && prevCnt == 0){
                res.add(new int[]{prevTime+1,currTime-1});
            }

            prevTime = currTime;
            prevCnt += curr[1];
        }

        if(prevTime < totalTime-1 && prevCnt == 0){
            res.add(new int[]{prevTime+1,totalTime-1});
        }
        return res;
    }
    // 32. Given a set of jobs array and max number of cpus, where each
    // job object contains 3 props {starttime,duration,numberofCpusNeeded},
    // write a function which returns true if the jobs can be executed with the
    // given max cpus else return false even if one job can't be executed?
//    public static void main(String[] args) {
//        List<int[]> process = new ArrayList<>();
//        process.add(new int[]{1,5,2});
//        process.add(new int[]{2,1,1});
//        process.add(new int[]{4,1,2});
//        System.out.println(isPossibleToProcess(process, 4));
//    }
    static boolean isPossibleToProcess(List<int[]> processs, int totalCpu){
        //startTime, duration, cpu Needed
        processs.sort((a, b) -> {
            if(a[0] != b[0])
                return a[0] - b[0];
            else
                return a[1] - b[1];
        });

        int cpuNeeded = 0;
        Queue<int[]> inprocess = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        for (int[] curr : processs) {
            int currStartTime = curr[0];

            while (!inprocess.isEmpty() && inprocess.peek()[1] < currStartTime) {
                cpuNeeded -= inprocess.remove()[2];

            }
            curr[1] = curr[0] + curr[1];//endTime
            inprocess.add(curr);
            cpuNeeded += curr[2];
            if (cpuNeeded > totalCpu)
                return false;
        }
        return true;
    }

    //33. Given a N-array-tree, calculate the maximum ancestor
    // for all the leaf nodes. Maximum ancestor of a leaf node
    // is the maximum of it's ancestors and the leaf itself.
    // It means maximum value in its path from root to leaf
    Map<Integer, Integer> leafMaxAncester = new HashMap<>();
    void findMaxLeafAncester(NTree root, int max){
        if( root == null)
            return ;
        if(root.children.size() == 0){
            leafMaxAncester.put(root.val, max);
            return;
        }

        for(NTree child : root.children){
            findMaxLeafAncester(child, Math.max(max, child.val));
        }
    }

    //34. Given an encoded string in form of "ab[cd]{2}def"
    //You have to return decoded string "abcdcddef"
//    Example 1:
//    Input: "ab[cd]{2}"
//    Output: "abcdcd"
//    Example 2:
//    Input: "def[ab[cd]{2}]{3}ghi"
//    Output: "defabcdcdabcdcdabcdcdghi"
//    public static void main(String[] args) {
//        System.out.println(decodeString("def[ab[z]{3}]{2}xx"));
//    }
    static String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder cur = new StringBuilder();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            } else if (Character.isLetter(ch)) {
                cur.append(ch);
            } else if (ch == '[') {
                stack.push(cur.toString());
                cur.setLength(0);
            } else if (ch == '}') {
                String prev = stack.pop();
                cur = new StringBuilder(prev + cur.toString().repeat(num));
                num = 0;
            }
        }

        // If anything remains in the stack
        while (!stack.isEmpty()) {
            cur.insert(0, stack.pop());
        }

        return cur.toString();
    }
    //35. Given a playlist of songs, you have to design a song shuffler.
    //This song shuffler is not like the normal song shuffler that shuffles
    // the complete playlist at the start and returns a shuffled list, but instead
    // when asked for a next song to be played, returns a random song from the list of songs.
    //The next random song to be played should satisfy a condition that the song
    // was not played in the last 'k' turns.
    //You have to make sure, that at each call, all the eligible
    // (not played during last k turns) songs have equal probability of being played next.
//    public static void main(String[] args) {
//        MusicPlayer player = new MusicPlayer(List.of(1,2,3,4,5), 4);
//        for(int i=0;i<10;i++){
//            System.out.print(player.findRandom1()+", ");
//        }
//    }

    static class MusicPlayer{
        List<Integer> songs;
        Queue<Integer> q;
        int k;
        int start;

        MusicPlayer(List<Integer> songs, int k){
            this.k = k;
            this.songs = new ArrayList<>(songs);
            this.q = new LinkedList<>();
            start = 0;
        }

        int findRandom(){
            int n = songs.size();
            int rand = (int) (Math.random() * songs.size());

            int currSong = songs.get(rand);
            songs.set(rand, songs.get(n-1));
            songs.remove(n-1);
            q.add(currSong);
            if(q.size() > k){
                songs.add(q.remove());
            }
            return currSong;
        }
        int findRandom1(){
            int n = songs.size();
            int random = k + (int) (Math.random() * (n-k));
            int currSong = songs.get(random);
            Collections.swap(songs, random, start++);
            if(start >= k)
                start = 0;
            return currSong;
        }
    }
    //36.I was asked about reservoir sampling—given a comments link to a
    // YouTube live stream, find a random user with uniform probability.
    public class ReservoirSampling {
        class Comment {
            String userId;
            int timestamp;

            public Comment(String userId, int timestamp) {
                this.userId = userId;
                this.timestamp = timestamp;
            }
        }

        class RandomUserPicker {
            Random random = new Random();
            String result = null;
            int count = 0;


            public void process(Comment comment) {
                count++;
                // With 1/count probability, replace the current result
                if (random.nextInt(count) == 0) {
                    result = comment.userId;
                }
            }

            public String getRandomUser() {
                return result;
            }
        }
    }
    //37. 952. Largest Component Size by Common Factor
    public int largestComponentSize(int[] nums) {
        int n = nums.length;

        DisjointSet dsu = new DisjointSet(n);
        Map<Integer, Integer> factorIndexMap = new HashMap<>();

        for(int i=0;i<nums.length;i++){
            int val = nums[i];

            for(int factor : findFactors(val)){
                if(factorIndexMap.containsKey(factor)){
                    dsu.unionBySize(i, factorIndexMap.get(factor));
                }else
                    factorIndexMap.put(factor, i);
            }

        }

        int res = 0;
        for(int i=0;i<n;i++){
            int upi = dsu.findUParent(i);
            res = Math.max(res, dsu.size.get(upi));
        }
        return res;
    }
    List<Integer> findFactors(int val){
        List<Integer> factors = new ArrayList<>();
        for(int i=2 ;i*i<=val;i++){
            if( val % i == 0){
                factors.add(i);
                while(val % i == 0){
                    val = val/i;
                }
            }
        }
        if( val > 1)
            factors.add(val);
        return factors;
    }
    //38. Read 4n buffer
    public class ReadBuffer{
        char[] myBuf = new char[4];
        int bufCount = 0;
        int bufIndex = 0;
        int solve(char[] buf, int n, Reader4 obj){
            int idx = 0;

            while(idx < n){

                if(bufIndex == 0)
                    bufCount = obj.read(myBuf);

                if(bufCount == 0)
                    break;

                while(idx < n && bufIndex < bufCount){
                    buf[idx++] = myBuf[bufIndex++];
                }

                if(bufIndex >= bufCount)
                    bufIndex = 0;
            }

            return idx;
        }

    }
    //39.  Given set of intervals, find out, if all the intervals have anything in common.
    int[] findCommonn(List<int[]> intervals){

        int maxStart= 0;
        int minEnd = (int) 1e9;

        for (int[] interval : intervals) {
            maxStart = Math.max(maxStart, intervals.get(0)[0]);
            minEnd = Math.min(minEnd, interval[1]);
        }
        if(maxStart <= minEnd)
            return new int[]{maxStart, minEnd};
        return new int[]{-1, -1};
    }
    //39 -> follow up -> Given intervals, findout minimum set of points S, such that
    // each interval has at least one point in the S.

    int findMinPoint(List<int[]> intervals) {
        intervals.sort((a, b) -> Integer.compare(a[1], b[1])); // sort by end

        int count = 0;
        int lastPoint = -1;

        for (int[] interval : intervals) {
            if (lastPoint < interval[0]) {
                lastPoint = interval[1];  // pick the end of this interval
                count++;
            }
        }

        return count;
    }
    //40. Given a number N, the task is to count minimum steps to minimize it to
    // 1 according to the following criteria:
    //If N is divisible by 2 then you may reduce N to N/2.
    //If N is divisible by 3 then you may reduce N to N/3.
    //Otherwise, Decrement N by 1.
    //
    //Input: N = 10
    //Output: 3
    //Explanation: 10 - 1 = 9 / 3 = 3 / 3 = 1
    void make1(int n) {
        int[] dp = new int[n+1];
        //solve(n, dp);
        dp[1] = 0;
        dp[2] = 1;
        for(int i=3;i<=n;i++){
            int res = dp[i-1];
            if( i % 2 == 0)
                res = Math.min(res, dp[i/2]);
            if(i %3 == 0)
                res = Math.min(res, dp[i/3]);
            dp[i] = 1 + res;
        }
        System.out.println(dp[n]);
    }

    static int solve(int n, int[] dp){
        if(n == 1)
            return 0;
        if(dp[n] != -1)
            return dp[n];
        int res = 1 + solve(n-1, dp);
        if(n % 2 == 0) {
            res = Math.min(res, 1 + solve(n / 2, dp));
        }

        if(n % 3 == 0){
            res = Math.min(res, 1 + solve(n/3, dp));
        }
        return dp[n] = res;
    }
    //41. You and your friends discuss a pattern of number your teacher has written on blackboard
    // after class. Return false if there is any contradiction between you and your friends, in
    // sequencing of numbers, else return true.
    //For e.g Suppose 4 friends write a sequence of numbers
    //Friend 1: 1, 3, 4, 2
    //Friend 2: 3, 4, 9, 10
    //Friend 3: 11, 49, 13, 3
    //Friend 4: 19, 3, 13, 4
    //Ans. False, there is contradiction
//    public static void main(String[] args) {
//        List<List<Integer>> seq = new ArrayList<>();
//        seq.add(List.of( 1, 3, 4, 2));
//        seq.add(List.of( 4, 9, 10));
//        seq.add(List.of( 11, 49, 13, 3));
//        seq.add(List.of( 19, 3,  4));
//        System.out.println(checkIfValid(seq));
//
//    }
    static boolean checkIfValid(List<List<Integer>> seq){
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> numbers = new HashSet<>();
        for(List<Integer> curr : seq){
            for(int i=1;i<curr.size();i++){
                graph.computeIfAbsent(curr.get(i-1), ele -> new ArrayList<>()).add(curr.get(i));
                numbers.add(curr.get(i-1));
                numbers.add(curr.get(i));
            }
        }

        Set<Integer> vis = new HashSet<>();

        for(int val : numbers){
            if(vis.contains(val))
                continue;
            if(isCyclic(val, graph, vis, new HashSet<>()))
                return false;
        }
        return true;
    }
    static boolean isCyclic(int node, Map<Integer, List<Integer>> graph, Set<Integer> vis, Set<Integer> pathVis){
        vis.add(node);
        pathVis.add(node);

        for(int adjNode : graph.getOrDefault(node, new ArrayList<>())){
            if(!vis.contains(adjNode)){
                if(isCyclic(adjNode, graph, vis, pathVis))
                    return true;
            }else if(pathVis.contains(adjNode))
                return true;
        }
        pathVis.remove(node);
        return false;
    }

    //42. Suppose you have given a task to schedule meetings in your office.
    // You have to merge any meeting if they coincides, apart there is a DND
    // interval in which you can't schedule any meeting. If any meeting has time
    // overlapping with DND you have to break it.
    //You have to do it in
    //Time Complexity: O(n*log(n))
    //Space Complexity: O(1)
    //For e.g
    //Meeting : [[1, 4], [3, 5], [9, 12], [7, 10]]
    //DND: [3, 8]
    //Ans. [[1, 3], [8, 12]]
//    public static void main(String[] args) {
//        List<int[]> meetings = new ArrayList<>();
//        meetings.add(new int[]{1,2});
//        meetings.add(new int[]{4,6});
//
//        meetings.add(new int[]{7,10});
//        int[] dnd = {3,8};
//        for(int[] curr : merge(meetings, dnd)){
//            System.out.println(curr[0]+" "+curr[1]);
//        }
//    }

    static List<int[]> merge(List<int[]> meetings, int[] dnd){
        meetings.sort((a, b) -> a[0] - b[0]);

        List<int[]> merged = new ArrayList<>();
        int start = meetings.get(0)[0];
        int end = meetings.get(0)[1];
        for(int i=1;i<meetings.size();i++){

            if(meetings.get(i)[0] <= end ){
                end = Math.max(end, meetings.get(i)[1]);
            }else{
                merged.add(new int[]{start, end});
                start = meetings.get(i)[0];
                end = meetings.get(i)[1];
            }

        }
        merged.add(new int[]{start, end});
        int n = merged.size();

        List<int[]> res = new ArrayList<>();
        int i=0;
        while(i < n && merged.get(i)[1] < dnd[0]){
            res.add(merged.get(i));
            i++;
        }

        if( i < n && merged.get(i)[0] < dnd[0])
            res.add(new int[]{merged.get(i)[0], dnd[0]});

        while(i < n && merged.get(i)[1] <= dnd[1])
            i++;
        if( i < n)
            res.add(new int[]{Math.max(dnd[1],merged.get(i)[0]), merged.get(i++)[1]});

        while(i < n)
            res.add(merged.get(i++));
        return res;
    }
    //43. There is a long and thin painting that can be represented by a number line. You are given a
    // 0-indexed 2D integer array paint of length n, where paint[i] = [start_i, end_i].
    // This means that on the ith day you need to paint the area between start_i and end_i.
    // Painting the same area multiple times will create an uneven painting so
    // you only want to paint each area of the painting at most once.
    //Return an integer array worklog of length n, where worklog[i] is the amount
//    public static void main(String[] args) {
//
//        List<int[]> paint = new ArrayList<>();
//        paint.add(new int[]{4,10});//6
//        paint.add(new int[]{7,13});//3
//        paint.add(new int[]{6,20});//7
//        paint.add(new int[]{1,40});//23
//        for(int val : findSolu1(paint)){
//            System.out.println(val);
//        }
//
//    }
    static List<Integer> findSolu(List<int[]> ranges){
        int max = ranges.stream().map(ele -> ele[1]).reduce(0, Integer::max);
        int[] painted = new int[max+1];

        List<Integer> res = new ArrayList<>();
        for(int i=0;i<ranges.size();i++){
            int start = ranges.get(i)[0];
            int end = ranges.get(i)[1];

            int cnt = 0;
            for(int k = start;k<end;k++){
                if(painted[k] == 0){
                    cnt ++;
                    painted[k]=1;
                }
            }
            res.add(cnt);
        }
        return res;
    }
    static List<Integer> findSolu1(List<int[]> ranges){
       Map<Integer, Integer> mp = new HashMap<>();
       List<Integer> res = new ArrayList<>();
       for(int[] curr : ranges){
           int start = curr[0];
           int end = curr[1];

           int work = 0;
           while(start < end){
               if(mp.containsKey(start)){
                   start = mp.get(start);
               }else{
                   work++;
                   mp.put(start, end);
                   start ++;
               }
           }
           res.add(work);
       }
       return res;
    }

    //44. A good arithmetic sequence is an arithmetic sequence with a common
    // difference of either 1 or -1. For example, [4, 5, 6] is a good arithmetic
    // sequence. So is [6, 5, 4], [10, 9], or [-3, -2, -1]. But, [1, 2, 1]
    // (no common difference) or [3, 7] (common difference is 4) is NOT.
    //    Given nums = [7, 4, 5, 6, 5]. Each of the following subarrays is a good arithmetic sequence:
    //
    //            [7], [4], [5], [6], [5],
    //            [4, 5], [5, 6], [6, 5],
    //            [4, 5, 6]
    //    The sums of these subarrays are:
    //            7, 4, 5, 6, 5,
    //            4 + 5 = 9, 5 + 6 = 11, 6 + 5 = 11,
    //            4 + 5 + 6 = 15
    //    Thus, the answer is the sum of all the sums above, which is:
    //            7 + 4 + 5 + 6 + 5 + 9 + 11 + 11 + 15 = 73.
//    public static void main(String[] args) {
//        int[] arr = new int[]{7, 4, 5,1,2,1};
//        System.out.println(sumOfGoodSequences(arr));
//        System.out.println(sumOfGoodSequencesReadable(arr));
//
//    }

    public static int sumOfGoodSequences(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += nums[i]; // single element is always good
            int diff = 0;
            if (i + 1 < n)
                diff = nums[i + 1] - nums[i];

            if (diff != 1 && diff != -1)
                continue; // if no valid common difference, continue

            int sum = nums[i];
            for (int j = i + 1; j < n; j++) {
                if (nums[j] - nums[j - 1] != diff) break;
                sum += nums[j];
                ans += sum;
            }
        }
        return ans;
    }

    public static int sumOfGoodSequencesReadable(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }

        long totalSum = 0;
        // Stores the sum of good sequences ending at the current index, keyed by their common difference.
        Map<Integer, Long> goodSequenceSumsEndingHere = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int currentNum = nums[i];

            // 1. Every single element is a good sequence.
            totalSum += currentNum;

            // 2. Initialize a map for the good sequences ending at the current index.
            Map<Integer, Long> nextSequenceSums = new HashMap<>();
            nextSequenceSums.put(0, (long) currentNum); // For the single element sequence.

            // 3. Extend good sequences ending at the previous index.
            if (i > 0) {
                int previousNum = nums[i - 1];
                int currentDifference = currentNum - previousNum;

                if (currentDifference == 1 || currentDifference == -1) {
                    // a) Form a new good sequence of length 2.
                    totalSum += (long) previousNum + currentNum;
                    nextSequenceSums.put(currentDifference, nextSequenceSums.getOrDefault(currentDifference, 0L) + previousNum + currentNum);

                    // b) Extend existing good sequences with the same common difference.
                    for (Map.Entry<Integer, Long> entry : goodSequenceSumsEndingHere.entrySet()) {
                        if (entry.getKey() == currentDifference) {
                            long previousSequenceSum = entry.getValue();
                            totalSum += previousSequenceSum + currentNum;
                            nextSequenceSums.put(currentDifference, nextSequenceSums.getOrDefault(currentDifference, 0L) + previousSequenceSum + currentNum);
                        }
                    }
                }
            }

            // Update the map for the next iteration.
            goodSequenceSumsEndingHere = nextSequenceSums;
        }

        return (int) totalSum;
    }

    //45. same as ramp but opposite , here we need to check previous element
    // which is larger and return max distance between them
    //Given an array of stock prices, find the size of
    // the widest interval over which the stock has lost value. Lost value is defined as the
    // initial value of the interval is larger than the final value of the interval.
    // Intermediate values within the interval may rise above the initial value, but the
    // interval would still be considered as lost value.
    //Example :
    //Time: 00, 01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12, 13, 14, 15, 16
    //Price: 50, 52, 58, 54, 57, 51, 55, 60, 62, 65, 68, 72, 62, 61, 59, 63, 72
    //Ans - 7
    //Explanation - the widest interval with a net loss in value is from the indices 07 to 14 and has length 14 - 7 = 7
    //Although a peak value of 72 at time index 11 is present in this interval, it is still considered an interval with loss value.
    void rampSolve(int[] arr, int n){
        Stack<Integer> st = new Stack<>();
        for(int i=0;i<n;i++){
            if(st.isEmpty() || arr[st.peek()] < arr[i])
                st.add(i);

        }
        int res = 0;
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && arr[st.peek()] > arr[i]){
                res = Math.max(res, i-st.pop());
            }
        }
        System.out.println(res);
    }
    //46. Number of island in a tree

        static Set<TreeNode> vis;  // Set to track visited nodes
        static int count;  // Counter for the islands

        // Main function to count islands
        static int numberOfIslands(TreeNode root) {
            vis = new HashSet<>();  // Initialize the visited set
            count = 0;  // Initialize the island count
            iterate(root);  // Start the iteration
            return count;  // Return the final count
        }

        // Iterate function to traverse the tree and detect isolated islands
        static void iterate(TreeNode root) {
            if (root == null) return;  // Base case to end recursion

            // Masked part: If the node is unvisited and land (1), we start DFS
            if (!vis.contains(root) && root.val == 1) {
                dfs(root);  // Call DFS to mark the entire island as visited
                count++;  // Increment the island count
            }

            // Continue recursion to left and right children
            iterate(root.left);  // Left subtree recursion
            iterate(root.right);  // Right subtree recursion
        }

        // DFS function to explore all connected land nodes
        static void dfs(TreeNode root) {
            if (root == null || root.val == 0 || vis.contains(root)) return;  // Base case

            vis.add(root);  // Mark the current node as visited

            // Masked part: Recurse through all neighboring connected nodes
            dfs(root.left);  // Explore the left child
            dfs(root.right);  // Explore the right child
        }

        // Method to create a test case for the tree
        public static TreeNode testCase() {
            TreeNode root = new TreeNode(1);  // Create root
            root.left = new TreeNode(1);  // Left child
            root.right = new TreeNode(0);  // Right child (water)
            root.left.left = new TreeNode(1);  // Left child's left child (land)
            root.left.right = new TreeNode(1);  // Left child's right child (land)
            return root;  // Return the constructed tree
        }

    //        public static void main(String[] args) {
    //            TreeNode root = testCase();  // Create a test case
    //            int result = numberOfIslands(root);  // Get the number of islands
    //            System.out.println("Number of Islands: " + result);  // Output the result
    //        }

    //47. Number of ways to partition an array into segments s.t each segment has atleast 2 negative numbers
//    public static void main(String[] args) {
//        int[] arr ={1,2,3,-1, -3, 1,2,3, -1, -2};
//        System.out.println(countValidPartitions(arr));
//    }

    public static int countValidPartitions(int[] arr) {
        int n = arr.length;
        int[] prefixNeg = new int[n + 1]; // prefixNeg[i] = #negatives in arr[0...i-1]

        for (int i = 0; i < n; i++) {
            prefixNeg[i + 1] = prefixNeg[i] + (arr[i] < 0 ? 1 : 0);
        }

        int totalNeg = prefixNeg[n];
        if(totalNeg < 2)
            return 0;
        if (totalNeg < 4) return 1; // need at least 2 negatives in both parts

        int count = 1;
        for (int cut = 1; cut < n; cut++) {
            int leftNeg = prefixNeg[cut];
            int rightNeg = totalNeg - leftNeg;

            if (leftNeg >= 2 && rightNeg >= 2) {
                count++;
            }
        }

        return count;
    }
    //48. https://leetcode.com/problems/my-calendar-i/description/
    class MyCalendar {
        TreeMap<Integer, Integer> book;
        public MyCalendar() {
            book = new TreeMap<>();
        }

        public boolean book(int startTime, int endTime) {
            Integer left = book.floorKey(startTime);
            Integer right = book.ceilingKey(startTime);

            if(left != null && book.get(left) > startTime)
                return false;
            if(right != null && right < endTime)
                return false;

            book.put(startTime, endTime);
            return true;
        }
    }

    //49. Minimum Area Rectangle -> https://leetcode.com/problems/minimum-area-rectangle/description/
    public int minAreaRect(int[][] points) {
        int n = points.length;

        Set<String> seen = new HashSet<>();
        for(int i=0;i<n;i++){
            seen.add(points[i][0] +","+ points[i][1]);
        }

        int res = (int) 1e9;
        for(int i=0;i<n;i++){
            int x1 = points[i][0];
            int y1 = points[i][1];
            for(int j=i+1;j<n;j++){
                int x2 = points[j][0];
                int y2 = points[j][1];
                if(x1 != x2 && y1 != y2){

                    if(seen.contains(x1+","+y2) && seen.contains(x2+","+y1)){
                        int area = Math.abs(x1-x2) * Math.abs(y1-y2);
                        res = Math.min(res, area);
                    }
                }
            }
        }
        return res == 1e9 ? 0 : res;
    }
    //50 Minimum Area Rectangle II -> https://leetcode.com/problems/minimum-area-rectangle-ii/description/
    public double minAreaFreeRect(int[][] points) {
        int n = points.length;
        Set<String> seen = new HashSet<>();
        for(int i=0;i<n;i++){
            seen.add(points[i][0] +","+points[i][1]);
        }
        double res = Double.MAX_VALUE;

        for(int i=0;i<n;i++){
            int[] p1 = points[i];
            for(int j=0;j<n;j++){
                if(i==j)
                    continue;

                int[] p2 = points[j];

                for(int k=0;k<n;k++){
                    if( k == i || k == j)
                        continue;

                    int[] p3  = points[k];

                    if(isRightAngled(p1, p2, p3)){

                        int p4x = p2[0] + p3[0] - p1[0];
                        int p4y = p2[1] + p3[1] - p1[1];

                        if(seen.contains(p4x+","+p4y)){
                            double area = distance(p1, p2) * distance(p1, p3);
                            if(area > 0 && area < res)
                                res = area;
                        }
                    }
                }
            }
        }

        return res == Double.MAX_VALUE ? 0 : res;
    }
    double distance(int[] p1, int[] p2){
        return Math.sqrt(Math.pow(p1[0]-p2[0],2) + Math.pow(p1[1] -p2[1], 2));
    }
    boolean isRightAngled(int[] p1, int[] p2, int[] p3){
        int val = (p2[0] - p1[0])*(p3[0] - p1[0]) + (p2[1] - p1[1] )*(p3[1] -p1[1]);
        return val == 0;
    }

    //51. We have a vector of strings. Each index has exactly 3 strings which are in a group.
    //We can group the common strings at different indexes and print the number of groups.
    //
    //example
    //[
    //["sam","man","peach"],
    //["man","qwer","wsx"],
    //["jko","pcd","qwe"]
    //]
    //since "man" is common for index 0 and index 1 we can say all strings
    // in index 0 and index 1 are in same group .
    //
    //therefore we will have 2 groups
    //["sam","man","peach","qwer","wsx"] and ["jko","pcd","qwe"]
//    public static void main(String[] args) {
//        List<List<String>> arr = new ArrayList<>();
//        arr.add(List.of("sam","man","peach"));
//        arr.add(List.of("man","qwer","wsx"));
//        arr.add(List.of("jko","pcd","qwe"));
//        findCommon(arr);
//
//    }

    static void findCommon(List<List<String>> arr){
        int n = arr.size();
        DisjointSet set = new DisjointSet(n);
        Map<String, Integer> stringIndexMap = new HashMap<>();
        for(int i=0;i<n;i++){
            for(String s : arr.get(i)){

                if(stringIndexMap.containsKey(s)) {
                    set.unionBySize(i, stringIndexMap.get(s));
                }
                stringIndexMap.put(s, i);
            }
        }


        Map<Integer, Set<String>> indexStringMap = new HashMap<>();
        for(var entry : stringIndexMap.entrySet()){
            String s = entry.getKey();
            int index = entry.getValue();
            int upIndex = set.findUParent(index);
            if(!indexStringMap.containsKey(upIndex))
                indexStringMap.put(upIndex, new HashSet<>());
            indexStringMap.get(upIndex).add(s);

        }
        List<List<String>> res = new ArrayList<>();
        for(var entry : indexStringMap.entrySet()){
            Set<String> strs = entry.getValue();
            res.add(new ArrayList<>(strs));
        }

        for(List<String> list : res){
            for (String s : list){
                System.out.print(s +", ");
            }
            System.out.println();
        }
    }

    //52. we have a square table and we have n numbers of cakes on the table.
    // we have to divide the table horizontally in such a way that the area of cake in
    // top part of table is exactly equal to area of cake in lower part.
    //Separate Squares I -> https://leetcode.com/problems/separate-squares-i/description/
    public double separateSquares(int[][] squares) {
        double epi = 1e-6;
        double low = 0.0, high = 1e10;

        double res = 0.0;
        while((high-low) > epi){
            double mid = (low + high)/2;

            if(valid(squares, mid)){
                res = mid;
                high = mid;
            }else
                low = mid;
        }
        return res;
    }
    boolean valid(int[][] squares, double mid){
        double down = 0, up = 0;
        for(int[] curr : squares){
            double x = curr[0];
            double y = curr[1];
            double l = curr[2];

            if(y + l <= mid){
                down += l * l;
            }else if(y >= mid){
                up += l *l;
            }else{
                up += (y+l-mid)*(y+l-mid);
                down += (mid-y)*(mid-y);
            }
        }
        return down>= up;
    }
    //53. decode string
//    public static void main(String[] args) {
//        decodeString2("3[a2[b]]a");
//    }
    static void decodeString2(String s){

        Stack<Integer> countStack = new Stack<>();
        Stack<String> stringStack = new Stack<>();
        int num = 0;
        StringBuilder curr = new StringBuilder();
        for(char ch : s.toCharArray()){

            if(Character.isDigit(ch)){
                num = num * 10 + (ch - '0');
            }else if(ch == '['){
                countStack.add(num);
                stringStack.add(curr.toString());
                num = 0;
                curr = new StringBuilder();
            }else if(ch == ']'){
                int count = countStack.pop();
                String str = curr.toString();
                curr = new StringBuilder(stringStack.pop() + str.repeat(count));
            }else if(Character.isLetter(ch))
                curr.append(ch);
        }
        while(!stringStack.isEmpty()){
            curr.insert(0, stringStack.pop());
        }
        System.out.println(curr);
    }
    //54. You are given an array ‘ARR’ of integers. Your task is to find the
    // length of the longest alternating subsequence.
    //Note:
    //A sequence a1, a2, .... an is called an alternating
    // sequence if its elements satisfy one of the following relations :
    // a1 < a2 > a3 < a4 > a5..... or  a1 > a2 < a3 > a4 < a5.
    //For Example:
    //'ARR' = {3, 10, 1, 2, 30}, the longest alternating
    // subsequence for this array can be {3, 10, 1, 30} or {3, 10, 2, 30}.
    // Therefore, the answer will be 4.
//    public static void main(String[] args) {
//        int[] arr = {3, 10, 1, 29, 30};
//        longestAlternatingSubsequence(arr);
//    }

    public static void longestAlternatingSubsequence(int[] arr) {
        if (arr.length == 0) return;

        int n = arr.length;
        int up = 1, down = 1; // min length is 1 (each element is a subsequence)

        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                up = down + 1;
            } else if (arr[i] < arr[i - 1]) {
                down = up + 1;
            }
            // if equal, do nothing
        }

        System.out.println( Math.max(up, down));
    }

    //55. 1007. Minimum Domino Rotations For Equal Row
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int count1 = solve(tops, bottoms, tops[0]);
        if(count1 != -1)
            return count1;
        return solve(tops, bottoms, bottoms[0]);

    }
    int solve(int[] tops, int[] bottoms, int target){
        int flipTop = 0;
        int flipBottom = 0;
        int n = tops.length;

        for(int i=0;i<n;i++){
            if(tops[i] != target && bottoms[i] != target)
                return -1;
            else if(tops[i] != target){
                flipTop++;
            }else if(bottoms[i] != target){
                flipBottom++;
            }
        }
        return  Math.min(flipTop, flipBottom);
    }
    //56. You have n dice.
    //	Each dice can roll any number between 1 to 6 (inclusive).
    //	Find the number of ways to achieve a target sum.
//    public static void main(String[] args) {
//        findSumUsingDice(17, 3);
//    }
    static void findSumUsingDice(int target, int n){
        int[][] dp = new int[n+1][target+1];
        dp[0][0] = 1;

        for(int dice =1;dice<=n;dice++){
            for(int tar = 0;tar<=target;tar++){
                for(int face =1;face<=6;face++){
                    if(tar-face >= 0)
                        dp[dice][tar] += dp[dice-1][tar-face];
                }
            }
        }
        System.out.println(dp[n][target]);
    }

    //57 Question 2: Minimum Flips to Alternate Binary String (With K Flip Window)
    //You are given a binary string s. In one operation, you can flip a
    // subarray of length exactly k (flip all bits in that subarray).
    // Return the minimum number of such operations needed to make the string
    // alternating (i.e., no two adjacent bits are the same). If it's not possible, return -1.
    //Test Case: s = "00010111"
    //k = 3
    //Expected Output : 2

//    public static void main(String[] args) {
//        System.out.println(minFlips("00010111", 3, "10101010101")); // Expected output: 3
//    }


    public static int minFlips(String s, int k) {
        int n = s.length();
        String target1 = generateTarget(n, "0");//01010101
        String target2 = generateTarget(n, "1");//10101010
        int res = Math.min(helper(s, k, target1), helper(s, k, target2));
        if(res == 1e9)
            return -1;
        return res;
    }
    private static int helper(String s, int k, String target) {
        int n = s.length();
        int res = 0;
        int flip = 0;
        int[] diff = new int[n + 1];

        for (int i = 0; i < n; i++) {
            flip += diff[i]; // update current flip status
            int expected = target.charAt(i) - '0';
            int current = s.charAt(i) - '0';
            int actual = (flip % 2 == 0) ? current : 1 - current;//(1-current) we will flip from 0 to 1 or from 1 to 0

            // If after applying flip, current does not match expected, we must flip here
            if (actual != expected) {
                if (i + k > n) return (int)1e9; // can't flip outside

                res++;
                flip ++; // adding here
                diff[i + k] --; // removing here
            }
        }
        return res;
    }
    private static String generateTarget(int n, String ch1) {
        String ch2 = ch1.equals("1") ? "0": "1";
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            if(i % 2 == 0)
                sb.append(ch1);
            else
                sb.append(ch2);
        }
        return sb.toString();
    }
    //58. A number is called lucky if:
    //Reading from left to right, each digit is less than or equal to the next digit.
    // Examples of Lucky Numbers:
    //1234 → 1 ≤ 2 ≤ 3 ≤ 4 →
    //1225 → 1 ≤ 2 ≤ 2 ≤ 5 →
    //1268 → 1 ≤ 2 ≤ 6 ≤ 8 →
    //Examples of Unlucky Numbers:
    //2134 → 2 > 1 →
    //2331 → 3 > 1 →
    //Problem has 3 questions:
    //1. Check if a number is lucky
    //Input: num = 1233
    //Output: True (because 1 ≤ 2 ≤ 3 ≤ 3)
    //2. Find the next lucky number greater than the given number
    //Input: num = 1233
    //Output: 1234
    //(The smallest number greater than 1233 that satisfies the lucky condition.)
    //
    //3. List all lucky numbers in a given range
    //Input: low = 1200, high = 1250
    //Output: [1222, 1223, 1224, 1225, ..., 1233, 1234, ...]
    //(All numbers between 1200 and 1250 that are lucky.)
//    public static void main(String[] args) {
//        List<String> res = new ArrayList<>();
//        generateRange("", "2200", "2230", res);
//        for(String s : res){
//            System.out.print(s +", ");
//        }
//        StringBuilder ans = new StringBuilder();
//        generateNext("", "99", ans);
//        System.out.println();
//        System.out.println(ans);
//    }
    static boolean generateNext(String curr, String num, StringBuilder sb){
        if(!curr.isEmpty()){
            if((curr.length() > num.length()) || (curr.length() == num.length() && curr.compareTo(num) >0)) {
                sb.append(curr);
                return true;
            }
        }

        char startDigit = curr.isEmpty() ? '1': curr.charAt(curr.length()-1);
        for(char d = startDigit;d<='9';d++){
            if(generateNext(curr + d, num, sb))
                return true;
        }
        return false;
    }

    static void generateRange(String curr, String low, String high, List<String> res) {
        if (!curr.isEmpty()) {
            if (curr.length() > high.length() ||
                    (curr.length() == high.length() && curr.compareTo(high) > 0)) return;
            if ((curr.length() > low.length() ||
                    (curr.length() == low.length() && curr.compareTo(low) >= 0)))
                res.add(curr);
        }

        char startDigit = curr.isEmpty() ? '1' : curr.charAt(curr.length() - 1);
        for (char d = startDigit; d <= '9'; d++) {
            generateRange(curr + d, low, high, res);
        }
    }


    //59.//Write a class that does the following:
    //    //insert range
    //    //query for a point
//    public static void main(String[] args) {
//        RangeModule rm = new RangeModule();
//        rm.addRange(5, 10);
//        rm.addRange(15, 20);
//        rm.addRange(8, 18); // Merges overlapping ranges
//
//        rm.printRanges(); // Should show [5, 20)
//
//        System.out.println(rm.query(7));  // true
//        System.out.println(rm.query(21)); // false
//    }

    static public class RangeModule {
        private final TreeMap<Integer, Integer> rangeMap;

        public RangeModule() {
            rangeMap = new TreeMap<>();
        }

        // Add a range [left, right)
        public void addRange(int left, int right) {
            if (left >= right) return;

            // Find overlapping or adjacent ranges
            Integer start = rangeMap.floorKey(left);
            Integer end = rangeMap.floorKey(right);

            if (start != null && rangeMap.get(start) >= left) {
                left = Math.min(left, start);
                right = Math.max(right, rangeMap.get(start));
                rangeMap.remove(start);
            }

            while (end != null && end >= left) {
                right = Math.max(right, rangeMap.get(end));
                rangeMap.remove(end);
                end = rangeMap.lowerKey(end);
            }

            rangeMap.put(left, right);
        }

        // Query if x is present in any range
        public boolean query(int x) {
            Integer start = rangeMap.floorKey(x);
            return start != null && rangeMap.get(start) >= x;
        }

        // Optional: print current ranges (for debugging)
        public void printRanges() {
            for (Map.Entry<Integer, Integer> entry : rangeMap.entrySet()) {
                System.out.println("[" + entry.getKey() + ", " + entry.getValue() + ")");
            }
        }
    }

    //60. Consider an infinite binary tree with the following structure:
    //At each odd level, each node has two children.
    //At each even level, each node has only one child.
    //The tree nodes have consecutive values starting from 1.
    //                     1
    //                /        \
    //                2           3
    //                /                \
    //                4                   5
    //            /      \            /        \
    //          6           7       8           9
    //         |            |        |            |
    //Write a function find_path(n: int) -> List[int] that takes a node value
    // n as input and returns the path from the given node to the root of the tree.
    // The path should be a list of node values, starting from the given node and
    // going up to the root.

//    public static void main(String[] args) {
//        for(long val : findPath(11))
//            System.out.print(val +", ");
//    }

    public static List<Integer> findPath(int n) {
        List<Integer> path = new ArrayList<>();

        int totalNodes = 0;
        int level = 0;

        //we will first find the level of the node
        while (totalNodes < n) {
            level++;
            totalNodes += Math.pow(2, level/2);
        }
        System.out.println(level);

        // we will process this by observation
        while(n > 0){
            path.add(n);

            if(level % 2 == 1){
                n -= (int) Math.pow(2, level/2);
            }else{
                n = (int) Math.ceil((n+1)/2.0);
            }
            if( n == 2) {
                path.add(2);
                break;
            }else if(n == 3) {
                path.add(3);
                break;
            }
            level--;
        }
        path.add(1);
        return path;
    }

    //61. There are n men , ith mean can create 1 chair, in arr[i] days,
    // what is the minimum number of days required to complete c chairs
//    public static void main(String[] args) {
//
//        int[] arr = {2,2};
//        int n = arr.length;
//        int c = 3;
//        int res = solve(arr, 0, c);
//        System.out.println(res);
//    }
    static int solve(int[] arr, int i,  int c){
       int max = Arrays.stream(arr).reduce(0, Integer::max);

       int low = 0, high = max * c;

       while(low <= high){
           int mid = (low + high)/2;

           if(isPossible(arr, c, mid)){
               high = mid-1;
           }else
               low = mid+1;
       }
       return low;
    }
    static boolean isPossible(int[] arr, int c, int mid){
        int count = 0;
        for (int selfId : arr) {
            count += mid / selfId;
        }
        return count >= c;
    }
    //62. 2013. Detect Squares


    //64. Find the number of partitions of an array such that each contiguous
    // partition consists of atleast one negative number.
    //eg. [-1,-2,-3,-4] has these possible partitions :
    //[-1],[-2],[-3],[-4];
    //[-1,-2],[-3,-4];
    //[-1,-2,-3] ,[-4];
    //[-1],[-2,-3,-4];
    //[-1][-2,-3],[-4];

//    public static void main(String[] args) {
//        int[] arr = {1,-2,-3,-4, -6};
//
//        System.out.println(findAllPartisions1(arr));
//        System.out.println(findAllPartisions(0, arr));
//    }
    static int findAllPartisions(int i, int[] arr){
        if(i == arr.length)
            return 1;

        int res = 0;
        int neg = 0;
        for(int j=i;j<arr.length;j++){
            if(arr[j] < 0)
                neg++;
            if(neg >= 1)
                res += findAllPartisions(j+1, arr);
        }
        return res;
    }

    static int findAllPartisions1(int[] nums){
        int n = nums.length;
        int[] dp = new int[n];
        int currsum = 0;
        for(int i = 0; i<n; i++) {
            if(nums[i] < 0)
                dp[i] = currsum + 1;
            else {
                dp[i] = i > 0 ? dp[i - 1] : 0;
            }

            currsum += dp[i];
        }
        return dp[n-1];
    }

    //65. 1834. Single-Threaded CPU -> https://leetcode.com/problems/single-threaded-cpu/description/
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        List<int[]> newTask = new ArrayList<>();
        int index = 0;
        for(int[] curr : tasks){
            newTask.add(new int[]{curr[0], curr[1], index++});
        }

        newTask.sort((a, b) -> a[0] - b[0]);

        Queue<int[]> pq = new PriorityQueue<>((a, b) ->{
            if(a[1] != b[1])
                return a[1] - b[1];
            else
                return a[2] - b[2];
        });

        int j = 0, i = 0, time = 0;
        int[] res = new int[n];

        while( j < n){

            while(i < n && newTask.get(i)[0] <= time){
                pq.add(newTask.get(i++));

            }
            if(pq.isEmpty()){
                time = newTask.get(i)[0];
                continue;
            }

            res[j++] = pq.peek()[2];
            time += pq.peek()[1];

            pq.remove();
        }
        return res;
    }
    //66. 983. Minimum Cost For Tickets
    public int minCostTickets(int[] days, int[] costs) {
        Arrays.sort(days);
        int n = days.length;
        Integer[] dp = new Integer[n];
        return solve(days, costs, 0, dp);
    }
    int solve(int[] days, int[] costs, int i, Integer[] dp){
        if( i >= days.length)
            return 0;
        if(dp[i] != null) return dp[i];
        int i1 = upperBound(days, days[i]);
        int i2 = upperBound(days, days[i] + 7-1);
        int i3 = upperBound(days, days[i] + 30-1);

        int cost1 = costs[0] + solve(days, costs, i1, dp);
        int cost2 = costs[1] + solve(days, costs, i2, dp);
        int cost3 = costs[2] + solve(days, costs, i3, dp);
        return dp[i] = Math.min(cost1, Math.min(cost2, cost3));
    }
    int upperBound(int[] arr, int target){
        int low = 0, high = arr.length-1;

        while(low <= high){
            int mid = (low + high)/2;
            if(arr[mid] <= target){
                low = mid+1;
            }else
                high = mid-1;
        }
        return low;
    }

    //77.  DATE maker
    public void FindDates() {
        String s = "?4:0?";
        List<String> res = new ArrayList<>();

        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m++) {
                String time = String.format("%02d:%02d", h, m);
                if (matchesPattern(time, s)) {
                    res.add(time);
                }
            }
        }

        for (String ss : res) {
            System.out.println(ss);
        }
        System.out.println("Total valid times: " + res.size());
    }

    private static boolean matchesPattern(String time, String pattern) {
        for (int i = 0; i < 5; i++) {
            if (pattern.charAt(i) == '?' || pattern.charAt(i) == time.charAt(i)) {
                continue;
            }else
                return false;
        }
        return true;
    }
    //78. How many employee working at a time

//    public static void main(String[] args) {
//        Abby 1 10
//        Ben 5 7
//        Carla 6 12
//        David 15 17
//        Abby 8 13
//        List<String> list = new ArrayList<>();
//        list.add("Abby 1 10");
//        list.add("Ben 5 7");
//        list.add("Carla 6 12");
//        list.add("David 15 17");
//        list.add("Abby 8 13");
//        findAllEmployees(list);
//    }
    static void findAllEmployees(List<String> list){
        List<Set<String>> line = new ArrayList<>();
        for(int i=0;i<20;i++)
            line.add(new HashSet<>());

        for(String s : list){
            String[] curr = s.split(" ");
            int start = Integer.parseInt(curr[1]);
            int end = Integer.parseInt(curr[2]);

            for(int i=start;i<=end;i++){
                line.get(i).add(curr[0]);
            }
        }

        System.out.println(line.get(9));
    }

}

