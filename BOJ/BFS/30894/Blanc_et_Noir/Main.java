/* https://www.acmicpc.net/problem/30894 */

import java.io.*;
import java.util.*;

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
    public static int[][] dist = {{0,-1},{-1,0},{0,1},{1,0}};

    /* d방향으로 탐색하여 그 방향에 자신을 보고있는 유령이 있는지 없는지 판단함 */
    public static boolean search(char[][] m, Node node, int d){
        Queue<Node> q = new LinkedList<Node>();
        q.add(node);

        while(!q.isEmpty()){
            Node n = q.poll();

            int y = n.y + dist[d][0];
            int x = n.x + dist[d][1];

            if(!(y>=0&&y<m.length&&x>=0&&x<m[0].length)){
                break;
            }

            if(m[y][x] == '#'){
                break;
            }else if(m[y][x]=='.'){
                q.add(new Node(y,x,n.v));
            }else{
                /* 마주보고 있는 유령이 있음 */
                if(d == (m[y][x]-'0'+n.v)%4){
                    return false;
                }

                break;
            }
        }

        /* 마주보고 있는 유령이 없음 */
        return true;
    }

    /* 4방향으로 마주보고 있는 유령이 있다면 false, 없으면 true */
    public static boolean check(char[][] m, Node node){
        for(int i=0; i<4; i++){
            if(!search(m, node, i)){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");
        String result = "GG";
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        char[][] m = new char[N][M];

        input = br.readLine().split(" ");

        /* 시작 x, y, 종료 x, y 좌표 */
        int sx = Integer.parseInt(input[1])-1;
        int sy = Integer.parseInt(input[0])-1;
        int ex = Integer.parseInt(input[3])-1;
        int ey = Integer.parseInt(input[2])-1;

        for(int i=0; i<N; i++){
            m[i] = br.readLine().toCharArray();
        }

        boolean[][][] v = new boolean[N][M][4];
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy, sx, 0));
        v[sy][sx][0] = true;

        while(!q.isEmpty()){
            Node n = q.poll();

            /* 자신을 마주보고 있는 유령이 있다면 pass */
            if(!check(m, new Node(n.y, n.x, n.v))){
                continue;
            }

            /* 도착지에 도달한다면 소요 시간을 리턴 */
            if(n.y==ey&&n.x==ex){
                result = n.v+"";
                break;
            }

            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];

                if(!(y>=0&&y<N&&x>=0&&x<M)){
                    continue;
                }

                if(v[y][x][(n.v+1)%4]){
                    continue;
                }

                if(m[y][x]!='.'){
                    continue;
                }

                /* 이동했을때 자신을 보고 있는 유령이 없다면 방문처리 */
                if(check(m, new Node(y, x, n.v+1))){
                    q.add(new Node(y,x,n.v+1));
                    v[y][x][(n.v+1)%4] = true;
                /* 이동했을때 자신을 보고 있는 유령이 있고, 그 상태에서 그 자리에서 기다려 본 적이 없다면 제자리 방문처리 */
                }else if(!v[n.y][n.x][(n.v+1)%4]){
                    q.add(new Node(n.y, n.x,n.v+1));
                    v[n.y][n.x][(n.v+1)%4] = true;
                }
            }
        }

        bw.write(result+"\n");
        bw.flush();
        br.close();
        bw.close();
    }
}