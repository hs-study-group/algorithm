/* https://www.acmicpc.net/problem/16933 */

import java.io.*;
import java.util.*;

class Node{
    /* y, x 좌표, 밤낮여부, 벽을 부술 기회, 소모 시간 */
    int y, x, d, k, c;

    Node(int y, int x, int d, int k, int c){
        this.y = y;
        this.x = x;
        this.d = d;
        this.k = k;
        this.c = c;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        String result = "-1";

        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        int K = Integer.parseInt(input[2]);
        int[][] m = new int[N][M];
        boolean[][][][] v = new boolean[N][M][2][K+1];

        int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};

        for(int i=0; i<N; i++){
            input = br.readLine().split("");
            for(int j=0; j<M; j++){
                m[i][j] = Integer.parseInt(input[j]);
            }
        }

        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(0,0,0,K,1));

        /* 시작위치 방문처리 */
        v[0][0][0][K] = true;
        v[0][0][1][K] = true;

        while(!q.isEmpty()){
            Node n = q.poll();

            if(n.y==N-1&&n.x==M-1){
                result = n.c+"";
                break;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<N&&x>=0&&x<M)) {
                    continue;
                }

                /* 벽이 아니라면 */
                if(m[y][x] == 0){
                    /* 밤낮이 바뀌어 해당 위치에 방문한 적이 없다면 방문처리 */
                    if(!v[y][x][(n.d+1)%2][n.k]){
                        q.add(new Node(y,x,(n.d+1)%2, n.k, n.c+1));
                        v[y][x][(n.d+1)%2][n.k] = true;
                    }
                /* 벽이라면 */
                }else{
                    /* 밤이라면 */
                    if(n.d%2==0){
                        /* 벽을 부술 기회가있고, 벽을 부숴서 밤에 그곳에 도착한 적이 없다면 제자리에서 대기 */
                        if(n.k > 0 && !v[y][x][(n.d+1)%2][n.k-1]){
                            q.add(new Node(y, x, (n.d+1)%2, n.k-1, n.c+1));
                            v[y][x][(n.d+1)%2][n.k-1] = true;
                        }
                    /* 낮이라면 */
                    }else{
                        /* 벽을 부술 기회가있고, 벽을 부숴서 밤에 그곳에 도착한 적이 없다면 방문처리 */
                        if(n.k > 0 && !v[y][x][(n.d+1)%2][n.k-1]){
                            q.add(new Node(n.y, n.x, (n.d+1)%2, n.k, n.c+1));
                            v[n.y][n.x][(n.d+1)%2][n.k] = true;
                        }
                    }
                }
            }
        }

        bw.write(result+"\n");
        bw.flush();
        br.close();
        bw.close();
    }
}