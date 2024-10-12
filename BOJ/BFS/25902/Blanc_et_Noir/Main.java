/* https://www.acmicpc.net/problem/25902 */

import java.io.*;
import java.util.*;

class Node{
    /* y, x좌표, 소요시간 */
    int y, x, t;

    Node(int y, int x, int t){
        this.y = y;
        this.x = x;
        this.t = t;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, R, C;
    public static int[][] m;
    public static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};

    /* sy, sx 좌표에서 ey, ex 좌표로 이동하는데 최소 높이가 val 이상으로 하여 방문할수 있는지 확인하는 메소드 */
    public static boolean BFS(int sy, int sx, int ey, int ex, int val){
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));

        /* 방문배열 */
        boolean[][] v = new boolean[R][C];

        /* 초기위치 방문처리 */
        v[sy][sx] = true;

        while(!q.isEmpty()){
            Node n = q.poll();

            if(n.y==ey&&n.x==ex){
                return true;
            }

            for(int i=0;i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<R&&x>=0&&x<C)){
                    continue;
                }

                if(v[y][x]){
                    continue;
                }

                /* 해당 위치 방문시 높이가 val 미만이라면 방문할 수 없음 */
                if(m[y][x] - (n.t+1) < val){
                    continue;
                }

                q.add(new Node(y,x,n.t+1));
                v[y][x] = true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        for(int i=0; i<N;i++){
            input = br.readLine().split(" ");

            R = Integer.parseInt(input[0]);
            C = Integer.parseInt(input[1]);

            m = new int[R][C];

            for(int j=0; j<R; j++){
                input = br.readLine().split(" ");

                for(int k=0; k<C; k++){
                    m[j][k] = Integer.parseInt(input[k]);
                }
            }

            int s = 1;
            int e = m[0][0];
            int r = Integer.MIN_VALUE;

            while(s<=e){
                int val = (s+e)/2;

                /* 최소높이가 val이 되도록 목적지에 도달할 수 있다면 */
                if(BFS(0,0, R-1, C-1, val)){
                    /* val을 증가시키고, 결과를 업데이트 함 */
                    s = val + 1;
                    r = Math.max(r, val);
                /* 최소높이가 val이 되도록 목적지에 도달할 수 없다면 */
                }else{
                    /* val을 감소시킴 */
                    e = val - 1;
                }
            }

            if(r!=Integer.MIN_VALUE){
                bw.write(r+"\n");
            }else{
                bw.write("impossible\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}