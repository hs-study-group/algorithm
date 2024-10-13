/* https://www.acmicpc.net/problem/17472 */

import java.io.*;
import java.util.*;

class Node{
    /* y, x 좌표, 거리 */
    int y, x, t;

    Node(int y, int x, int t){
        this.y = y;
        this.x = x;
        this.t = t;
    }
}

/* 간선정보 클래스 */
class Edge{
    /* v1, v2 정점, 거리 */
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
    public static int[][] m, index;
    public static int N, M, K, L;
    public static int[] parents;

    /* 특정 구역을 val로 마킹하는 메소드 */
    public static void mark(int sy, int sx, int val){
        boolean[][] v = new boolean[N][M];
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));
        v[sy][sx] = true;
        index[sy][sx] = val;

        while(!q.isEmpty()){
            Node n = q.poll();

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<N&&x>=0&&x<M)){
                    continue;
                }

                if(v[y][x]){
                    continue;
                }

                if(m[y][x]==0){
                    continue;
                }

                q.add(new Node(y,x,0));
                v[y][x] = true;
                index[y][x] = val;
            }
        }
    }

    /* sy, sx 좌표에서 d방향으로 다리를 건설하는 메소드 */
    public static Edge construct(int sy, int sx, int d){
        Edge e = null;

        if(index[sy][sx]==-1){
            return e;
        }

        int v1 = index[sy][sx];
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));

        while(!q.isEmpty()){
            Node n = q.poll();

            int y = n.y + dist[d][0];
            int x = n.x + dist[d][1];

            if(!(y>=0&&y<N&&x>=0&&x<M)){
                continue;
            }

            if(index[y][x]==v1){
                continue;
            }

            if(index[y][x]!=-1){
                if(n.t>1){
                    e = new Edge(v1, index[y][x], n.t);
                }
                break;
            }

            q.add(new Node(y,x,n.t+1));
        }

        return e;
    }

    /* v 정점의 최상위 부모를 찾는 메소드 */
    public static int getAncestor(int v){
        if(parents[v]==v){
            return v;
        }else{
            return getAncestor(parents[v]);
        }
    }

    /* 다리를 선택했을때 사이클이 형성되는지 판단하는 메소드 */
    public static boolean selectBridge(Edge e){
        /* 두 정점 v1, v2의 최상위 부모를 확인함 */
        int p1 = getAncestor(e.v1);
        int p2 = getAncestor(e.v2);
        
        /* 두 정점의 최상위 부모가 같다면 사이클을 형성하게되므로 false */
        if(p1==p2){
            return false;
        }
        
        /* 두 정점의 최상위 부모중 더 작은 부모가 더 큰 부모의 최상위 부모가 되도록 함 */
        if(p1<p2){
            parents[p2] = p1;
        }else{
            parents[p1] = p2;
        }

        /* 해당 간선을 선택해도 사이클이 형성되지 않음 */
        return true;
    }

    public static void main(String[] args) throws IOException {
        /* 더 적은 거리를 갖는 간선 정보를 우선적으로 리턴하는 우선순위 큐 선언 */
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
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

        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        m = new int[N][M];
        index = new int[N][M];

        for(int i=0;i<N;i++){
            input = br.readLine().split(" ");
            for(int j=0;j<M;j++){
                m[i][j] = Integer.parseInt(input[j]);

                /* y, x좌표가 몇 번째 구역인지 체크하기전 -1로 초기화 */
                index[i][j] = -1;
            }
        }

        for(int i=0; i<N;i++){
            for(int j=0;j<M;j++){
                /* 맵상에서 1로 표시되어있고 아직 마킹되지 않은 곳이면 마킹함 */
                if(m[i][j]==1&&index[i][j]==-1){
                    mark(i,j,K++);
                }
            }
        }

        for(int i=0; i<N;i++){
            for(int j=0;j<M;j++){
                /* i, j 좌표에서 4방향으로 다리를 건설을 시도함 */
                for(int k=0;k<dist.length;k++){
                    Edge e = construct(i,j,k);

                    if(e!=null){
                        pq.add(e);
                    }
                }
            }
        }

        /* 사이클 형성 확인을 위해 최상위 부모 배열 초기화 */
        parents = new int[K];

        for(int i=0;i<K;i++){
            parents[i] = i;
        }

        int result = 0;

        while(!pq.isEmpty()){
            Edge e = pq.poll();

            /* 해당 간선을 선택했을때 사이클을 형성하지 않으면 최종 결과값에 거리를 누적하여 더함 */
            if(selectBridge(e)){
                L++;
                result += e.d;
            }
        }

        /* 간선이 K-1개 선택되지 않았다면 최소 스패닝 트리를 완성하지 못한 것임 */
        if(L!=K-1){
            result=-1;
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}