import java.util.*;
import java.lang.String;
public class Recursion {
    public static void main(String[] args) {

    }
    //1. Recursive Implementation of atoi()
    long res = 0;
    public int myAtoi(String s) {
        s = s.trim();

        if(s.isEmpty())
            return 0;
        int sign = 1;
        int index = 0;
        if(s.charAt(index) == '+' || s.charAt(index) == '-') {
            if(s.charAt(index) == '-')
                sign = -1;
            index++;
        }
        solve(s, index);
        res = res * sign;
        if(res > Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if(res < Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int)res;
    }
    void solve(String s, int index){
        if(s.length() == index || res > Integer.MAX_VALUE)
            return ;

        if(s.charAt(index) <= '9' && s.charAt(index) >= '0'){
            res = res * 10 + s.charAt(index) - '0';
            solve(s, index+1);
        }
    }
    //2. Implement pow(x, n), which calculates x raised to the power n (i.e., xn).
    public double myPow(double x, int n) {
        double ans = 1.0;
        long n1 = n;
        if (n1 < 0) n1 = -1 * n1;
        while (n1 > 0) {
            if (n1 % 2 == 1) {
                ans = ans * x;
                n1 = n1 - 1;
            } else {
                x = x * x;
                n1 = n1 / 2;
            }
        }
        if (n < 0) ans = (double)(1.0) / (double)(ans);
        return ans;
    }
    //3. Count Good Numbers
    int mod = (int) 1e9 + 7;
    public int countGoodNumbers(long n) {
        if(n == 1)
            return 5;
        long count4 = n/2;
        long count5 = n - count4;

        long res = (power(4, count4) * power(5, count5)) % mod;
        return  (int)res;
    }
    long power(long x, long n){
        if( n == 0 || x == 0)
            return 1;
        if(n == 1)
            return x;
        long res = power(x, n/2);

        if(n % 2 == 0)
            return (res * res) % mod;
        else
            return (res*res*x) % mod;
    }
    //4.
}
