/* https://www.acmicpc.net/problem/17244 */

import java.io.*;
import java.util.*;

/* y, x 좌표 및 챙긴 물품의 상태, 소요 시간 */
class Node{
    int y, x, k, t;

    Node(int y, int x, int k, int t){
        this.y = y;
        this.x = x;
        this.k = k;
        this.t = t;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
    public static char[][] m;

    /* 물품마다 비트마스킹용 인덱스를 할당하는 배열 */
    public static int[][] index;
    public static int N, M, K;

    /* 특정 좌표에 위치한 물품의 인덱스를 리턴하는 메소드 */
    public static int getIndex(int y, int x){
        if(m[y][x]=='X'){
            return index[y][x];
        }

        return -1;
    }

    /* y, x 좌표에 존재하는 물품을 챙겼는지 아닌지 판단하는 메소드 */
    public static boolean hasKey(int y, int x, int k){
        return (k&(1<<getIndex(y,x)))>0;
    }

    /* 특정 인덱스의 물품을 챙겼다고 체크하는 메소드 */
    public static int addKey(int k, int idx){
        return k|(1<<idx);
    }

    /* sy, sx 좌표에서 모든 물품을 챙기고 나갈 수 있다면 그때의 최소 시간을 리턴하는 메소드 */
    public static int BFS(int sy, int sx){
        int result = -1;
        
        /* v[y][x][k] = y, x 좌표에 k라는 상태로 도달한 적이 있는지 체크하는 배열 */
        boolean[][][] v = new boolean[N][M][31+1];
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy, sx, 0, 0));

        while(!q.isEmpty()){
            Node n = q.poll();

            /* 모든 물품을 챙기고 도착지에 도달한경우 그때까지 사용한 시간을 리턴함 */
            if(m[n.y][n.x]=='E'&&n.k==(1<<K)-1){
                result = n.t;
                break;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                int k = n.k;

                if(!(y>=0&&y<N&&x>=0&&x<M)){
                    continue;
                }

                if(m[y][x]=='#'){
                    continue;
                }

                /* 해당 위치가 물품이고, 아직 챙기지 않았다면 물품을 챙김 */
                if(m[y][x]=='X'){
                    if(!hasKey(y,x,k)){
                        k = addKey(k, getIndex(y,x));
                    }
                }

                if(v[y][x][k]){
                    continue;
                }

                q.add(new Node(y,x,k,n.t+1));
                v[y][x][k] = true;
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[1]);
        M = Integer.parseInt(input[0]);
        m = new char[N][M];
        index = new int[N][M];

        int sy=0, sx=0;

        for(int i=0;i<N;i++){
            m[i] = br.readLine().toCharArray();
            for(int j=0; j<M; j++){
                if(m[i][j]=='S'){
                    sy = i;
                    sx = j;
                }else if(m[i][j]=='X'){
                    /* 물품마다 인덱스를 할당 */
                    index[i][j] = K++;
                }
            }
        }

        int result = BFS(sy, sx);

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}