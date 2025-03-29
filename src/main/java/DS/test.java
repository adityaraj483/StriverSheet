package DS;
import java.util.*;


class test {
    public static void main(String[] args) {
        int[][] mat = new int[][]{
                {
                    -1,-2,-4,0,-1
                },
                {
                    -2,-4, 0, 0, 0
                }
        };
        int res = findHealth(mat);
        System.out.println(res);
    }

    static int findHealth(int[][] mat){
        int n = mat.length;
        int m = mat[0].length;

        Queue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);

        pq.add(new int[]{0, 0, mat[0][0]});

        while(!pq.isEmpty()){
            int row = pq.peek()[0];
            int col = pq.peek()[1];
            int cost = pq.remove()[2];
            if(row == n-1 && col == m-1)
                return Math.abs(cost)+1;

            if(valid(row+1, col, n, m)){
                pq.add(new int[]{row+1, col, cost + mat[row+1][col]});
            }

            if(valid(row, col+1, n, m)){
                pq.add(new int[]{row, col+1, cost + mat[row][col+1]});
            }
        }
        return -1;
    }

    private static  boolean valid(int row, int col, int n, int m) {
        return row >=0 && row <n && col >=0 && col < m;
    }

}