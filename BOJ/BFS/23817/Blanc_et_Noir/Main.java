/* https://www.acmicpc.net/problem/23817 */

import java.io.*;
import java.util.*;

/* y, x 좌표 및 소요 시간 v */
class Node{
    int y, x, v;

    Node(int y, int x, int v){
        this.y = y;
        this.x = x;
        this.v = v;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
    public static int[][] index;
    public static char[][] m;
    public static int N, M, K=1;
    public static int[][] distances;
    public static int result = Integer.MAX_VALUE;
    public static boolean[] ck;

    /* K 또는 S의 인덱스를 리턴하는 메소드 */
    public static int getIndex(int y, int x){
        if(m[y][x]=='K'||m[y][x]=='S'){
            return index[y][x];
        }

        return -1;
    }

    /* S 또는 K에서 S 또는 K로 이동하는데 필요한 거리를 계산하는 메소드 */
    public static void BFS(int sy, int sx){
        boolean[][] v = new boolean[N][M];
        int idx = getIndex(sy,sx);
        distances[idx] = new int[K];

        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));
        v[sy][sx] = true;

        while(!q.isEmpty()){
            Node n = q.poll();

            for(int i=0;i<dist.length;i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(y>=0&&y<N&&x>=0&&x<M&&!v[y][x]&&m[y][x]!='X'){
                    if(m[y][x]!='.'){
                        distances[idx][getIndex(y,x)] = n.v + 1;
                    }
                    q.add(new Node(y,x,n.v+1));
                    v[y][x] = true;
                }
            }
        }
    }

    /* DFS를 통해 순열을 구하는 메소드 */
    public static void DFS(int s, int d, int v){
        if(d==5){
            result = Math.min(result, v);
        }else{
            for(int e=0; e<K; e++){
                if(!ck[e]&&distances[s][e]!=0){
                    ck[e] = true;
                    DFS(e,d+1, v+distances[s][e]);
                    ck[e] = false;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        m = new char[N][M];
        index = new int[N][M];
        
        /* 인덱스 배열 초기화 */
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                index[i][j]=-1;
            }
        }

        /* S, K의 좌표를 기록할 리스트 */
        ArrayList<Node> list = new ArrayList<Node>();

        for(int i=0;i<N;i++){
            m[i] = br.readLine().toCharArray();
            for(int j=0;j<M;j++){
                /* S는 무조건 인덱스 0로 고정 */
                if(m[i][j]=='S'){
                    index[i][j]=0;
                    list.add(new Node(i,j,0));
                }else if(m[i][j]=='K'){
                    index[i][j]=K++;
                    list.add(new Node(i,j,0));
                }
            }
        }

        distances = new int[K][K];
        ck = new boolean[K];

        for(Node n : list){
            BFS(n.y,n.x);
        }

        /* S는 이미 선택했다고 가정 */
        ck[0] = true;
        
        /* 순열을 구함 */
        DFS(0,0,0);

        if(result==Integer.MAX_VALUE){
            result=-1;
        }

        bw.write(result+"\n");
        bw.flush();
        br.close();
        bw.close();
    }
}