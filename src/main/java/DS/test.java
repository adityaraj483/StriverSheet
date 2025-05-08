package DS;


public class test {
    public static void main(String[] args) {
        String s = "011";
        int k = 2;
        System.out.println(solve(s, k));
    }
    static int solve(String s, int k){
        int n = s.length();
        String target1 = makeTarget('0', n);
        String target2 = makeTarget('1', n);
        int count1 = minFlips(s, target1, k, n);
        int count2 = minFlips(s, target2, k, n);
        return Math.min(count1, count2);
    }
    static int minFlips(String s, String target, int k, int n){
        int[] diff = new int[n+1];
        int res = 0;
        int flip = 0;

        for(int i=0;i<n;i++){
            flip += diff[i];

            int actual = s.charAt(i) - '0';
            int expected = flip % 2 == 0 ? target.charAt(i) - '0' : '1' - target.charAt(i);

            if(actual != expected){
                if(i + k > n) return (int) 1e9;
                flip ++;
                res++;
                diff[i+k] = -1;
            }
        }
        return res;
    }
    static String makeTarget(char ch , int n){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            if(i %2 == 0){
                sb.append(ch);
            }else
                sb.append('1' - ch);
        }
        return sb.toString();
    }





}



