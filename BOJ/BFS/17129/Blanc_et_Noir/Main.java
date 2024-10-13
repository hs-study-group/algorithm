/* https://www.acmicpc.net/problem/17129 */

import java.io.*;
import java.util.*;

/* y, x 좌표, 소요 시간 */
class Node{
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
    public static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        int[][] m = new int[N][M];

        boolean[][] v = new boolean[N][M];
        Queue<Node> q = new LinkedList<Node>();

        for(int i=0; i<N; i++){
            input = br.readLine().split("");
            for(int j=0; j<M; j++){
                m[i][j] = Integer.parseInt(input[j]);
                if(m[i][j]==2){
                    q.add(new Node(i,j,0));
                    v[i][j] = true;
                }
            }
        }

        boolean flag = false;
        int result = 0;

        while(!q.isEmpty()){
            Node n = q.poll();

            /* 어떤 음식에 도달한 적이 있다면 그때의 소요 시간을 갱신함 */
            if(m[n.y][n.x]==3||m[n.y][n.x]==4||m[n.y][n.x]==5){
                flag = true;
                result = n.t;
                break;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<N&&x>=0&&x<M)){
                    continue;
                }

                if(m[y][x]==1){
                    continue;
                }

                if(v[y][x]){
                    continue;
                }

                q.add(new Node(y,x,n.t+1));
                v[y][x] = true;
            }
        }

        if(flag){
            bw.write("TAK\n"+result+"\n");
        }else{
            bw.write("NIE\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}