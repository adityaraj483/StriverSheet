package DS;


import java.util.*;


    public class test {

        public static int findLongestIncreasingSubarray(int[] a, int n) {
            if (n == 0) return 0;

            int[] l = new int[n]; // Stores length of increasing sequence ending at index i
            int[] r = new int[n]; // Stores length of increasing sequence starting at index i
            int ans = 1;

            // Compute l[] (lengths of increasing sequences ending at i)
            l[0] = 1;
            for (int i = 1; i < n; i++) {
                if (a[i] > a[i - 1]) {
                    l[i] = l[i - 1] + 1;
                    ans = Math.max(ans, l[i]);
                } else {
                    l[i] = 1;
                }
            }

            // Compute r[] (lengths of increasing sequences starting at i)
            r[n - 1] = 1;
            for (int i = n - 2; i >= 0; i--) {
                if (a[i] < a[i + 1]) {
                    r[i] = r[i + 1] + 1;
                } else {
                    r[i] = 1;
                }
            }

            // Check if modifying one element extends the sequence
            for (int i = 1; i < n - 1; i++) {
                if (a[i + 1] - a[i - 1] > 1) {
                    ans = Math.max(ans, l[i - 1] + r[i + 1] + 1);
                }
            }

            return ans;
        }

        public static void main(String[] args) {
            int[] a = {9, 4, 5, 1, 7};
            int n = a.length;
            System.out.println(findLongestIncreasingSubarray(a, n));
        }
    }




