/* https://www.acmicpc.net/problem/10277 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] tree;
    public static int[] lazy;
    public static int C, N, O;

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 처리해야할 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] += lazy[n];
            lazy[n*2+1] += lazy[n];
        }

        /* 레이지 값 처리 */
        tree[n][0] += lazy[n];
        tree[n][1] += lazy[n];

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        return new int[]{
                Math.min(arr1[0], arr2[0]),
                Math.max(arr1[1], arr2[1])
        };
    }

    /* l ~ r 구간의 최대, 최소값을 리턴하는 쿼리 메소드 */
    public static int[] query(int l, int r){
        return query(1, 0, C-1, l, r);
    }

    /* l ~ r 구간의 최대, 최소값을 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return new int[]{N+1, -N-1};
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    /* l ~ r 구간에 v값을 적용하는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, C-1, l, r, v);
    }

    /* l ~ r 구간에 v값을 적용하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v){
        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 레이지 값 전파 */
            lazy[n] = v;

            /* 레이지 값 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v);
            update(n*2+1, (s+e)/2+1, e, l, r, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        input = br.readLine().split(" ");

        C = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);
        O = Integer.parseInt(input[2]);

        tree = new int[4*C][2];
        lazy = new int[4*C];

        for(int i=0; i<O; i++){
            input = br.readLine().split(" ");

            int L, R, S, V = 0;

            if(input[0].equals("state")){
                L = Integer.parseInt(input[1]);
                R = L;

                bw.write(query(L, R)[0]+"\n");
            }else{
                if(input[0].equals("change")){
                    L = Integer.parseInt(input[1]);
                    R = L;
                    S = Integer.parseInt(input[2]);
                }else{
                    L = Integer.parseInt(input[1]);
                    R = Integer.parseInt(input[2]);
                    S = Integer.parseInt(input[3]);
                }

                int[] result = query(L, R);

                /* S만큼 증가시켜야한다면, 상한선까지만 증가하도록 함 */
                if(S>0){
                    V = result[1]+S<=N?S:N-result[1];
                /* S만큼 감소시켜야한다면, 하한선까지만 감소하도록 함 */
                }else if(S<0){
                    V = result[0]+S>=0?S:-result[0];
                }

                /* L ~  R 구간의 최소값, 최대값을 V만큼 증가시킴 */
                update(L, R, V);

                bw.write(V+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}