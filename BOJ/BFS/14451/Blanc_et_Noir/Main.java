/* https://www.acmicpc.net/problem/14451 */

import java.io.*;
import java.util.*;

class Node{
    /* 1번 방향의 y, x 좌표 및 방향, 2번 방향의 y, x 좌표 및 방향, 소요시간 */
    int y1, x1, d1, y2, x2, d2, t;

    Node(int y1, int x1, int d1, int y2, int x2, int d2, int t){
        /* 위쪽 방향으로 시작하는 시작점의 좌표 */
        this.y1 = y1;
        this.x1 = x1;
        this.d1 = d1;

        /* 오른쪽 방향으로 시작하는 시작점의 좌표 */
        this.y2 = y2;
        this.x2 = x2;
        this.d2 = d2;

        /* 소요 시간 */
        this.t = t;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N;

    public static int BFS(char[][] m, int sy, int sx, int ey, int ex){
        /* 상, 우, 하, 좌 */
        int[][] dist = {{-1,0},{0,1},{1,0},{0,-1}};

        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0, sy,sx,1,0));

        /* y1, x1, d1, y2, x2, d2 에 대한 방문배열 */
        boolean[][][][][][] v = new boolean[N][N][4][N][N][4];
        v[sy][sx][0][sy][sx][1] = true;

        int result = -1;

        while(!q.isEmpty()){
            Node n = q.poll();

            if(n.y1==ey&&n.x1==ex&&n.y2==ey&&n.x2==ex){
                result = n.t;
                break;
            }

            int y1 = n.y1+dist[n.d1][0];
            int x1 = n.x1+dist[n.d1][1];
            int y2 = n.y2+dist[n.d2][0];
            int x2 = n.x2+dist[n.d2][1];

            /* y1, x1 좌표가 맵을 벗어나거나, 벽이거나, 도착지점이라면 그대로 정지 */
            if(!(y1>=0&&y1<N&&x1>=0&&x1<N)||m[y1][x1]=='H'||(n.y1==0&&n.x1==N-1)){
                y1 = n.y1;
                x1 = n.x1;
            }

            /* y2, x2 좌표가 맵을 벗어나거나, 벽이거나, 도착지점이라면 그대로 정지 */
            if(!(y2>=0&&y2<N&&x2>=0&&x2<N)||m[y2][x2]=='H'||(n.y2==0&&n.x2==N-1)){
                y2 = n.y2;
                x2 = n.x2;
            }

            /* 이동하고자 하는 y1, x1, y2, x2 위치에 각각의 방향으로 방문한 적이 없다면 방문처리 */
            if(!v[y1][x1][n.d1][y2][x2][n.d2]){
                q.add(new Node(y1,x1,n.d1,y2,x2,n.d2,n.t+1));
                v[y1][x1][n.d1][y2][x2][n.d2] = true;
            }

            /* 두 노드의 방향을 좌회전함 */
            int d1 = (n.d1+3)%4;
            int d2 = (n.d2+3)%4;

            /* y1, x1 좌표가 도착지점이라면 방향을 원래대로 둠 */
            if(n.y1==0&&n.x1==N-1){
                d1 = n.d1;
            }

            /* y2, x2 좌표가 도착지점이라면 방향을 원래대로 둠 */
            if(n.y2==0&&n.x2==N-1){
                d2 = n.d2;
            }

            /* y1, x1, y2, x2 좌표에서 좌회전으로 도착한 적이 없다면 방문처리 */
            if(!v[n.y1][n.x1][d1][n.y2][n.x2][d2]){
                q.add(new Node(n.y1,n.x1,d1,n.y2,n.x2, d2, n.t+1));
                v[n.y1][n.x1][d1][n.y2][n.x2][d2] = true;
            }

            /* 두 노드의 방향을 우회전함 */
            d1 = (n.d1+1)%4;
            d2 = (n.d2+1)%4;

            /* y1, x1 좌표가 도착지점이라면 방향을 원래대로 둠 */
            if(n.y1==0&&n.x1==N-1){
                d1 = n.d1;
            }

            /* y2, x2 좌표가 도착지점이라면 방향을 원래대로 둠 */
            if(n.y2==0&&n.x2==N-1){
                d2 = n.d2;
            }

            /* y1, x1, y2, x2 좌표에서 우회전으로 도착한 적이 없다면 방문처리 */
            if(!v[n.y1][n.x1][d1][n.y2][n.x2][d2]){
                q.add(new Node(n.y1,n.x1,d1,n.y2,n.x2, d2, n.t+1));
                v[n.y1][n.x1][d1][n.y2][n.x2][d2] = true;
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        char[][] m = new char[N][N];

        for(int i=0;i<N;i++){
            m[i] = br.readLine().toCharArray();
        }

        bw.write(BFS(m,N-1,0,0,N-1)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}