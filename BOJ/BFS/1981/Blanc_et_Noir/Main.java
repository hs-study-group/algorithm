/* https://www.acmicpc.net/problem/1981 */

import java.io.*;
import java.util.*;

/* y, x 좌표를 기록하는 클래스 */
class Node{
    int y, x;

    Node(int y, int x){
        this.y = y;
        this.x = x;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] dist = {{0,-1},{-1,0},{0,1},{1,0}};

    /* 최소값이 min, 최대값이 max가 되도록 도착지에 도달할 수 있는지 판별하는 BFS 메소드 */
    public static boolean BFS(int[][] m, int min, int max){
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(0,0));
        boolean[][] v = new boolean[m.length][m[0].length];
        v[0][0] = true;

        /* 시작부터 max보다 크거나, min보다 작다면 false */
        if(m[0][0] > max || m[0][0] < min){
            return false;
        }

        while(!q.isEmpty()){
            Node n = q.poll();

            /* min, max 규칙을 지키며 도달했다면 true를 리턴함 */
            if(n.y==m.length-1&&n.x==m[0].length-1){
                return true;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<m.length&&x>=0&&x<m[0].length)){
                    continue;
                }

                if(v[y][x]){
                    continue;
                }

                /* 만약 max보다 크거나, min보다 작다면 그 방향으로는 진행하지 않음 */
                if(m[y][x] > max || m[y][x] < min){
                    continue;
                }

                q.add(new Node(y,x));
                v[y][x] = true;
            }
        }

        return false;
    }

    /* i를 최소값, i+mid를 최대값으로 하여 BFS탐색이 가능한지 체크하는 메소드 */
    public static boolean check(int[][] m, int mid){
        for(int i=0; i+mid<=200; i++){
            if(BFS(m,i,i+mid)){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());
        int[][] m = new int[N][N];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for(int i=0; i<N; i++){
            String[] input = br.readLine().split(" ");
            for(int j=0; j<N; j++){
                m[i][j] = Integer.parseInt(input[j]);
                min = Math.min(min, m[i][j]);
                max = Math.max(max, m[i][j]);
            }
        }

        /* 최대값 - 최소값의 최소는 0, 최대갑 - 최소값의 최대는 max - min */
        int start = 0;
        int end = max-min;
        int result = Integer.MAX_VALUE;

        while(start<=end){
            int mid = (start+end)/2;

            /* 만약 최대, 최소값의 차이가 mid이하가 되도록 도착지에 도달할 수 있다면 mid값을 더 줄여봄 */
            if(check(m, mid)){
                end = mid - 1;
                result = Math.min(result, mid);
            }else{
                start = mid + 1;
            }
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}