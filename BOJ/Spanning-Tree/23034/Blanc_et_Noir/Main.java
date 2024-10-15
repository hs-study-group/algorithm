/* https://www.acmicpc.net/problem/23034 */

import java.io.*;
import java.util.*;

class Node{
    /* 정점 v, 거리 d */
    int v, d;

    Node(int v, int d){
        this.v = v;
        this.d = d;
    }
}

class Edge{
    /* 정점 v1, v2, 거리 d */
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
    public static int N, M, T;
    
    /* 각 정점들의 최상위 부모를 기록할 배열 */
    public static int[] parents;
    
    /* 특정 번호의 정점이 갖는 인접한 간선정보 리스트를 저장할 해시맵 */
    public static HashMap<Integer, ArrayList<Edge>> hm = new HashMap<Integer, ArrayList<Edge>>();
    
    /* 더 적은 비용을 갖는 간선 정보를 우선적으로 리턴하는 우선순위 큐 선언 */
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

    /* 정점 v의 최상위 부모를 찾는 메소드 */
    public static int find(int v){
        if(parents[v] == v){
            return v;
        }else{
            return find(parents[v]);
        }
    }

    /* 해당 간선을 선택했을때 사이클을 형성하는지 아닌지를 판단하는 메소드 */
    public static boolean union(Edge e){
        int p1 = find(e.v1);
        int p2 = find(e.v2);

        /* 두 정점의 최상위 부모가 같다면 사이클을 형성하게되므로 해당 간선을 선택하지 않음 */
        if(p1==p2){
            return false;
        }

        /* 두 정점의 최상위 부모중에서 더 작은 부모가 더 큰 부모의 최상위 부모가 되도록 설정 */
        if(p1<p2){
            parents[p2] = p1;
        }else{
            parents[p1] = p2;
        }

        /* 해당 간선을 선택해도 사이클이 형성되지 않으므로 선택함 */
        return true;
    }

    /* 최소 스패닝 트리에서 v1에서 v2로 BFS탐색을 수행하며, 가장 비용이 큰 간선을 제외한 비용을 리턴하는 메소드 */
    public static int BFS(int v1, int v2){
        boolean[] v = new boolean[N];
        v[v1] = true;

        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(v1, Integer.MIN_VALUE));
        int val = 0;

        while(!q.isEmpty()){
            Node n = q.poll();

            /* 목적지에 도달시, 지나온 간선이 갖는 비용중 가장 큰 비용을 val에 저장 */
            if(n.v==v2){
                val = n.d;
                break;
            }

            /* 인접한 간선정보 리스트 */
            ArrayList<Edge> list = hm.get(n.v);

            for(int i=0; i<list.size(); i++){
                Edge e = list.get(i);

                /* 이미 방문한 정점이면 pass */
                if(v[e.v2]){
                    continue;
                }

                /* 해당 정점을 방문하며 최대 간선 비용도 갱신 */
                q.add(new Node(e.v2, Math.max(n.d, e.d)));
                
                /* 정점 방문처리 */
                v[e.v2] = true;
            }
        }

        /* 최소 스패닝 트리의 비용이 T라면, v1에서 v2로 이동하며 만난 간선들이 갖는 비용중 가장 큰 비용을 빼면 문제의 정답 */
        return T - val;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        parents = new int[N];

        for(int i=0; i<N; i++){
            parents[i] = i;
        }

        for(int i=0;i<M;i++){
            input = br.readLine().split(" ");

            int v1 = Integer.parseInt(input[0])-1;
            int v2 = Integer.parseInt(input[1])-1;
            int d = Integer.parseInt(input[2]);

            pq.add(new Edge(v1, v2, d));
        }

        /* 최소 스패닝 트리 생성 및 그때의 비용 계산 */
        while(!pq.isEmpty()){
            Edge e = pq.poll();

            if(union(e)){
                T += e.d;

                if(!hm.containsKey(e.v1)){
                    hm.put(e.v1, new ArrayList<Edge>());
                }

                if(!hm.containsKey(e.v2)){
                    hm.put(e.v2, new ArrayList<Edge>());
                }

                /* 정점 v1의 인접한 간선정보 리스트에 추가 */
                hm.get(e.v1).add(new Edge(e.v1, e.v2, e.d));

                /* 정점 v2의 인접한 간선정보 리스트에 추가 */
                hm.get(e.v2).add(new Edge(e.v2, e.v1, e.d));
            }
        }

        int Q = Integer.parseInt(br.readLine());

        for(int i=0; i<Q;i++){
            input = br.readLine().split(" ");

            int v1 = Integer.parseInt(input[0])-1;
            int v2 = Integer.parseInt(input[1])-1;
            
            bw.write(BFS(v1,v2)+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}