/* https://www.acmicpc.net/problem/18128 */

import java.io.*;
import java.util.*;


/* y, x 좌표 및 소요시간 t */
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
    public static int[][] dist = {{0,-1},{-1,0},{0,1},{1,0},{-1,-1},{-1,1},{1,-1},{1,1}};

    /* BFS탐색을 통해 각 좌표에 물이 어느 시점에 도달하는지 계산하는 메소드 */
    public static void waterBFS(int[][] wv, Queue<Node> q){

        for(int i=0; i<wv.length; i++){
            for(int j=0; j<wv.length; j++){
                wv[i][j] = Integer.MAX_VALUE;
            }
        }

        wv[0][0] = 0;
        wv[wv.length-1][wv[0].length-1] = 0;

        while(!q.isEmpty()){
            Node n = q.poll();

            if(n.t==0){
                wv[n.y][n.x] = 0;
            }

            for(int i=0; i<dist.length/2; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<wv.length&&x>=0&&x<wv.length)){
                    continue;
                }

                if(wv[y][x] != Integer.MAX_VALUE){
                    continue;
                }

                q.add(new Node(y,x,n.t+1));
                wv[y][x] = n.t+1;
            }
        }
    }

    /* 치삼이가 도착지에 어느 시점에 도달할 수 있는지 리턴하는 메소드 */
    public static int chisamBFS(int[][] m, int[][] wv){
        int[][] cv = new int[m.length][m[0].length];

        /* 소요시간이 더 적은 노드를 리턴하는 우선순위 큐 */
        PriorityQueue<Node> cq = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.t<n2.t){
                    return -1;
                }else if(n1.t>n2.t){
                    return  1;
                }else{
                    return 0;
                }
            }
        });

        /* 방문배열 초기화 */
        for(int i=0; i<cv.length; i++){
            for(int j=0; j<cv[0].length; j++){
                cv[i][j] = Integer.MAX_VALUE;
            }
        }

        cq.add(new Node(0,0,0));
        cv[0][0] = 0;

        while (!cq.isEmpty()){
            Node n = cq.poll();

            if(n.y==m.length-1&&n.x==m[0].length-1){
                return n.t;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<m.length&&x>=0&&x<m[0].length)||m[y][x]!=1){
                    continue;
                }

                /* 치삼이가 여태 사용한 비용과 물이 도달하는데 걸리는 시간중 더 큰 값이 도달하는데 필요한 시간 */
                int cost = Math.max(n.t, wv[y][x]);

                /* 기존보다 더 빠르게 도달할 수 있었다면 방문처리 */
                if(cost < cv[y][x]){
                    cq.add(new Node(y,x,cost));
                    cv[y][x] = cost;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        String result = "-1";
        String[] input = br.readLine().split(" ");

        int N = Integer.parseInt(input[0]);
        int W = Integer.parseInt(input[1]);

        int[][] m = new int[N][N];
        int[][] wv = new int[N][N];

        /* 물의 좌표를 기록할 큐 */
        Queue<Node> wq = new LinkedList<Node>();

        for(int i=0; i<W; i++){
            input = br.readLine().split(" ");

            int y = Integer.parseInt(input[0])-1;
            int x = Integer.parseInt(input[1])-1;

            wq.add(new Node(y,x,0));
        }

        /* 각 좌표에 물이 어느 시점에 도달하는지 계산 */
        waterBFS(wv, wq);

        for(int i=0; i<N; i++){
            input = br.readLine().split("");

            for(int j=0; j<N; j++){
                m[i][j] = Integer.parseInt(input[j]);
            }
        }

        /* 치삼이를 이동시킴 */
        result = chisamBFS(m, wv)+"";

        bw.write(result+"\n");
        bw.flush();
        br.close();
        bw.close();
    }
}