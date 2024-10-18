/* https://www.acmicpc.net/problem/12899 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static int[] tree;

    /* i번째 수를 v만큼 추가하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, M-1, i, v);
    }

    /* i번째 수를 v만큼 추가하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] += v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* v번째 수를 리턴하는 쿼리 메소드 */
    public static int query(int v){
        return query(1, 0, M-1, v);
    }

    /* v번째 수를 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int v){
        if(s==e){
            return s;
        /* 왼쪽 노드에 존재하는 수의 개수가 v보다 크거나 같으면, v번째 값은 왼쪽 노드에 존재함 */
        }else if(tree[n*2]>=v){
            return query(n*2, s, (s+e)/2, v);
        /* 왼쪽 노드에 존재하는 수의 개수가 v보다 작으면, v번째 값은 오른쪽 노드에 존재함 */
        }else{
            return query(n*2+1, (s+e)/2+1, e, v-tree[n*2]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());
        M = 2000001;

        tree = new int[4*M];

        for(int i=0; i<N; i++){
            input = br.readLine().split(" ");

            int T = Integer.parseInt(input[0]);
            int X = Integer.parseInt(input[1]);

            if(T==1){
                update(X, 1);
            }else if(T==2){
                int q = query(X);

                bw.write(q+"\n");

                update(q,-1);
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}