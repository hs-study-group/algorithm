/* https://www.acmicpc.net/problem/2243 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static int[] tree;

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, M-1, i, v);
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
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

    /* i번째 값을 리턴하는 쿼리 메소드 */
    public static int query(int i){
        return query(1, 0, M-1, i);
    }

    /* i번째 값을 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int i){
        /* 리프노드의 인덱스가 곧 찾고자 하는 값임 */
        if(s==e){
            return s;
        /* 왼쪽 자식노드의 값이 i보다 크거나 같으면, 왼쪽노드에 i번째 값이 존재함 */
        }else if(tree[n*2]>=i){
            return query(n*2, s, (s+e)/2, i);
        /* 왼쪽 자식노드의 값이 i보다 작으면, 오른쪽 노드에 i번째 값이 존재함 */
        }else{
            return query(n*2+1, (s+e)/2+1, e, i-tree[n*2]);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] input;

        N = Integer.parseInt(br.readLine());
        M = 1000001;

        tree = new int[4*M];

        for(int i=0; i<N; i++){
            input = br.readLine().split(" ");

            int A = Integer.parseInt(input[0]);

            if(A==1){
                int B = Integer.parseInt(input[1]);
                int q = query(B);

                bw.write(q+"\n");
                update(q, -1);
            }else if(A==2){
                int B = Integer.parseInt(input[1]);
                int C = Integer.parseInt(input[2]);

                update(B, C);
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}