/* https://www.acmicpc.net/problem/5465 */

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
    public static int[][] dist = {{0,-1},{-1,0},{0,1},{1,0}};

    /* 2차원 배열을 MAX_VALUE로 초기화 하는 메소드 */
    public static void initializeArray(int[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                arr[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    /* 벌들을 이동시켜 매시각마다의 좌표를 기록하는 메소드, 벌은 한 번의 이동마다 S초 소요한다고 처리 */
    public static int[][] goHive(char[][] m, Queue<Node> hq, int S){
        int[][] hv = new int[m.length][m[0].length];

        initializeArray(hv);

        while(!hq.isEmpty()){
            Node n = hq.poll();

            if(n.t==0){
                hv[n.y][n.x] = 0;
            }

            for(int i=0; i< dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<m.length&&x>=0&&x<m[0].length)){
                    continue;
                }

                if(m[y][x]!='G'&&m[y][x]!='M'){
                    continue;
                }

                if(hv[y][x]!=Integer.MAX_VALUE){
                    continue;
                }

                /* 벌은 한 번의 이동으로 1초가 아닌, S초를 소요한다고 가정 */
                hq.add(new Node(y,x,n.t+1));
                hv[y][x] = (n.t+1)*S;
            }
        }

        return hv;
    }

    /* 매시각마다의 벌의 좌표를 체크하며, v만큼 시작 위치에서 기다렸다가 도착지에 도달하는 것이 가능한지 체크하는 메소드 */
    public static boolean goBear(char[][] m, int[][] hv, int sy, int sx, int s, int v){
        /* 벌이 1번 행동할때 s초가 소요된다면, 곰은 1번 이동할때 1초가 소요된다고 가정, 대신 꿀을 먹는 것은 s초를 소요함 */
        Node b = new Node(sy, sx, s*v);

        Queue<Node> bq = new LinkedList<Node>();
        bq.add(b);
        boolean[][] bv = new boolean[m.length][m[0].length];
        bv[b.y][b.x] = true;

        /* 곰이 꿀을 먹는 도중에 벌한테 잡힌다면 false */
        if(b.t>=hv[b.y][b.x]){
            return false;
        }

        while(!bq.isEmpty()){
            Node n = bq.poll();

            /* v초만큼 꿀을 먹고, 매초마다 S칸을 움직여 목적지에 도달 가능함 */
            if(m[n.y][n.x]=='D'){
                return true;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<m.length&&x>=0&&x<m[0].length)){
                    continue;
                }

                if(m[y][x]=='T'){
                    continue;
                }

                if(bv[y][x]){
                    continue;
                }
                
                /* 벌보다 더 빠르게 해당 위치에 도달할 수 있다면 탐색을 이어나감 */
                if(n.t+1<hv[y][x]){
                    bv[y][x] = true;
                    bq.add(new Node(y,x,n.t+1));
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int S = Integer.parseInt(input[1]);

        char[][] m = new char[N][N];

        Queue<Node> hq = new LinkedList<Node>();

        int sy = 0, sx = 0;

        /* 맵과 시작 위치, 벌의 좌표를 저장함 */
        for(int i=0;i<N; i++){
            m[i] = br.readLine().toCharArray();
            for(int j=0; j<N; j++){
                if(m[i][j]=='H'){
                    hq.add(new Node(i,j,0));
                }else if(m[i][j]=='M'){
                    sy = i;
                    sx = j;
                }
            }
        }

        /* 벌이 1초 대신, S초마다 1칸 이동한다고 가정하고 BFS 탐색 */
        int[][] hv = goHive(m, hq, S);

        /* 곰이 꿀을 0초 ~ N*N초 먹었을때의 최대값을 찾기 위한 low, high 설정 */
        int low = 0, high = N*N, result=-1;

        while(low<=high){
            int mid = (low+high)/2;

            /* 만약 곰이 mid초만큼 꿀을 먹고, 매초 S칸을 이동해서 벌을 피해 도착지에 도달할 수 있다면 true */
            boolean flag = goBear(m, hv, sy, sx, S, mid);

            if(flag){
                result = mid;
                /* 꿀을 더 먹어보기 위해 다음의 mid값을 증가시킴 */
                low = mid + 1;
            }else{
                /* 꿀을 덜 먹어보기 위해 다음의 mid값을 감소시킴 */
                high = mid - 1;
            }
        }

        bw.write(result+"\n");
        bw.flush();
        br.close();
        bw.close();
    }
}