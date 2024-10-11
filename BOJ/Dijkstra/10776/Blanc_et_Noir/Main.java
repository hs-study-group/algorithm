/* https://www.acmicpc.net/problem/10776 */

import java.io.*;
import java.util.*;

class Node{
    /* 노드, 남은 두께, 소요 시간 */
    int n, k, t;

    Node(int n, int k, int t){
        this.n = n;
        this.k = k;
        this.t = t;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int K, N, M;

    /* 인접 리스트 간선정보 */
    public static ArrayList<ArrayList<Node>> g = new ArrayList<ArrayList<Node>>();

    /* s에서 e로 안전하게 이동할 수 있을때의 최단 시간을 리턴하는 메소드 */
    public static int dijkstra(int s, int e){
        /* v[N][K] = 노드 N에 K만큼의 두께 여유를 남기고 도달할 수 있는 최단 시간 */
        int[][] v = new int[N][K+1];

        /* 더 적은 시간, 더 많은 두께를 갖는 노드를 먼저 리턴하는 우선순위 큐 */
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.t<n2.t){
                    return -1;
                }else if(n1.t>n2.t){
                    return 1;
                }else{
                    if(n1.k>n2.k){
                        return -1;
                    }else if(n1.k<n2.k){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        });

        pq.add(new Node(s,K,0));

        /* 방문배열 초기화 */
        for(int i=0; i<N; i++){
            for(int j=0; j<K+1; j++){
                v[i][j] = Integer.MAX_VALUE;
            }
        }

        /* 시작위치 방문처리 */
        v[s][K] = 0;

        /* 다익스트라 수행 */
        while(!pq.isEmpty()){
            Node n = pq.poll();

            for(Node next : g.get(n.n)){
                int t = n.t + next.t;
                int k = n.k - next.k;

                /* 두께 여유가 있고, 더 적은 시간으로 방문 가능한지 판단 */
                if(k > 0 && t < v[next.n][k]){
                    pq.add(new Node(next.n, k, t));
                    v[next.n][k] = t;
                }
            }
        }

        /* e에 도달하기 위해 필요한 최단시간 계산 */
        int result = Integer.MAX_VALUE;

        for(int val : v[e]){
            result = Math.min(result, val);
        }

        if(result!=Integer.MAX_VALUE){
            return result;
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        K = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);
        M = Integer.parseInt(input[2]);

        /* 간선정보 인접 리스트 초기화 */
        for(int i=0;i<N;i++){
            g.add(new ArrayList<Node>());
        }

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int A = Integer.parseInt(input[0])-1;
            int B = Integer.parseInt(input[1])-1;
            int T = Integer.parseInt(input[2]);
            int H = Integer.parseInt(input[3]);

            /* 양방향으로 간선 등록 */
            g.get(A).add(new Node(B,H,T));
            g.get(B).add(new Node(A,H,T));
        }

        input = br.readLine().split(" ");

        int s = Integer.parseInt(input[0])-1;
        int e = Integer.parseInt(input[1])-1;

        bw.write(dijkstra(s,e)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}