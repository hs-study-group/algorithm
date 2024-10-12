/* https://www.acmicpc.net/problem/1944 */

import java.io.*;
import java.util.*;

class Node{
    /* y 좌표, x 좌표, 거리 */
    int y, x, t;

    Node(int y, int x, int t){
        this.y = y;
        this.x = x;
        this.t = t;
    }
}


/* 간선 정보를 저장할 클래스 */
class Edge{
    /* 정점 v1, v2, 거리 */
    int v1, v2, d;

    Edge(int v1, int v2, int d){
        this.v1 = v1;
        this.v2 = v2;
        this.d = d;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
    public static int[][] index;
    public static char[][] m;
    public static int N, M, K, L;
    
    /* 각 정점들의 최상위 부모를 저장할 배열 */
    public static int[] parents;
    
    /* 비용이 더 적게드는 간선 정보를 우선적으로 리턴하는 우선순위 큐 선언 */
    public static PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
        @Override
        public int compare(Edge e1, Edge e2) {
            if(e1.d<e2.d){
                return -1;
            }else if(e1.d>e2.d){
                return 1;
            }else{
                return 0;
            }
        }
    });

    /* y, x 좌표에 위치한 좌표의 인덱스를 리턴하는 메소드 */
    public static int getIndex(int y, int x){
        return index[y][x];
    }

    /* sy, sx 좌표에서 각각의 K로 가기위한 최단 거리를 구해 간선 정보로 등록하는 BFS 메소드 */
    public static void BFS(int sy, int sx){
        boolean[][] v = new boolean[N][N];
        v[sy][sx] = true;

        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));

        int v1 = getIndex(sy,sx);

        while(!q.isEmpty()){
            Node n = q.poll();

            for(int i=0; i<dist.length;i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<N&&x>=0&&x<N)){
                    continue;
                }

                if(v[y][x]){
                    continue;
                }

                if(m[y][x]=='1'){
                    continue;
                }

                if(m[y][x]=='0'){
                    q.add(new Node(y,x,n.t+1));
                /* 해당 위치가 열쇠라면 간선 정보를 등록함 */
                }else if(m[y][x]=='K'||m[y][x]=='S'){
                    pq.add(new Edge(v1,getIndex(y,x),n.t+1));
                }

                v[y][x] = true;
            }
        }
    }

    /* 정점 v의 최상위 부모를 리턴하는 메소드 */
    public static int find(int v){
        if(parents[v] == v){
            return v;
        }else{
            return find(parents[v]);
        }
    }

    /* 해당 간선을 선택했을때, 기존 간선들과 사이클을 이루는지 아닌지를 판단하는 메소드 */
    public static boolean union(Edge e){
        /* 두 정점의 최상위 부모를 구함 */
        int p1 = find(e.v1);
        int p2 = find(e.v2);

        /* 두 정점의 최상위 부모가 같다면 사이클을 형성하는 것이므로 false */
        if(p1==p2){
            return false;
        }

        /* 두 정점의 최상위 부모중, 더 작은 최상위 부모가, 다른 최상위 부모의 부모가 되도록 설정함 */
        if(p1<p2){
            parents[p2] = p1;
        }else{
            parents[p1] = p2;
        }

        /* 해당 간선을 선택해도 사이클을 이루지 않음 */
        return true;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        m = new char[N][N];
        index = new int[N][N];

        /* 인덱스 배열 초기화 */
        for(int i=0; i<N;i++){
            for(int j=0;j<N;j++){
                index[i][j] = -1;
            }
        }

        /* 맵을 입력 받으면서, K, S위치에 인덱스를 할당함 */
        for(int i=0; i<N;i++){
            m[i] = br.readLine().toCharArray();
            for(int j=0;j<N;j++){
                if(m[i][j]=='K'||m[i][j]=='S'){
                    index[i][j]=K++;
                }
            }
        }

        /* 최상위 부모 배열 선언 및 초기화 */
        parents = new int[K];

        for(int i=0; i<K;i++){
            parents[i] = i;
        }

        /* S 또는 K 위치에서 BFS 시작 */
        for(int i=0;i<N;i++){
            for(int j=0; j<N;j++){
                if(m[i][j]=='S'||m[i][j]=='K'){
                    BFS(i,j);
                }
            }
        }

        int result = 0;

        /* 비용이 적은 간선을 우선적으로 선택하되, 사이클을 이루는 간선은 선택하지 않음 */
        while(!pq.isEmpty()){
            Edge e = pq.poll();

            if(union(e)){
                result += e.d;
                L++;
            }

            /* 정점이 K개라면, K-1개의 간선을 선택한 즉시 최소 스패닝 트리가 완성됨 */
            if(L==K-1){
                break;
            }
        }

        /* 만약 K-1개의 간선을 선택하지 못했다면, 최소 스패닝 트리를 완성하지 못한 것임 */
        if(L!=K-1){
            result = -1;
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}