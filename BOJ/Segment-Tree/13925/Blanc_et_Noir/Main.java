/* https://www.acmicpc.net/problem/13925 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static long[] arr, tree;
    public static long[][] lazy;
    public static final long MOD = 1000000007;

    /* 세그트리 초기화 메소드 */
    public static void init(){
        /* 곱셈에 대한 lazy값을 모두 1로 초기화 */
        for(int i=0; i<lazy.length; i++){
            lazy[i][0] = 1;
        }

        init(1, 0, N-1);
    }

    /* 세그트리 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = arr[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = (tree[n*2] + tree[n*2+1])%MOD;
        }
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 레이지 값이 없다면 종료 */
        if(lazy[n][0]==1&&lazy[n][1]==0){
            return;
        }

        /* 리프노드가 아니라면 자식 노드에 레이지 전파 */
        if(s!=e){
            /* 곱셈에 대한 레이지 값은 곱셈에 대해서만 전파 */
            lazy[n*2][0] = (lazy[n*2][0] * lazy[n][0])%MOD;
            lazy[n*2+1][0] = (lazy[n*2+1][0] * lazy[n][0])%MOD;

            /* 덧셈에 대한 레이지 값은 곱셈에 대한 레이지 값 전파후,  덧셈에 대한 레이지 값을 전파 */
            lazy[n*2][1] = ((lazy[n*2][1]*lazy[n][0])%MOD + lazy[n][1])%MOD;
            lazy[n*2+1][1] = ((lazy[n*2+1][1]*lazy[n][0])%MOD + lazy[n][1])%MOD;
        }

        /* 곱셈에 대한 레이지 값 처리후, 덧셈에 대한 레이지 값을 이어서 처리 */
        tree[n] = ((tree[n] * lazy[n][0])%MOD + (lazy[n][1]*(e-s+1))%MOD)%MOD;

        /* 레이지 값 초기화 */
        lazy[n][0] = 1;
        lazy[n][1] = 0;
    }

    /* l ~ r 구간에 대하여 v1을 곱하고, 이어서 v2를 더하는 업데이트 메소드 */
    public static void update(int l, int r, long v1, long v2){
        update(1, 0, N-1, l, r, v1, v2);
    }

    /* l ~ r 구간에 대하여 v1을 곱하고, 이어서 v2를 더하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, long v1, long v2){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 곱셈에 대한 레이지 값은 곱셈에 대해서만 계산 */
            lazy[n][0] = (lazy[n][0] * v1)%MOD;
            
            /* 덧셈에 대한 레이지 값은 곱셈에 대한 레이지 값 계산후, 덧셈에 대한 레이지 값을 이어서 처리 */
            lazy[n][1] = ((lazy[n][1] * v1)%MOD+v2)%MOD;

            /* 레이지 값 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v1, v2);
            update(n*2+1, (s+e)/2+1, e, l, r, v1, v2);

            tree[n] = (tree[n*2] + tree[n*2+1])%MOD;
        }
    }

    /* 특정 구간 l ~ r의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r)%MOD;
    }

    /* 특정 구간 l ~ r의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n]%MOD;
        }else{
            return (query(n*2, s, (s+e)/2, l, r) + query(n*2+1, (s+e)/2+1, e, l, r))%MOD;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N];
        lazy = new long[4*N][2];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Long.parseLong(input[i]);
        }

        /* 세그트리 초기화 */
        init();

        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int Q = Integer.parseInt(input[0]);
            int L = Integer.parseInt(input[1])-1;
            int R = Integer.parseInt(input[2])-1;
            long V;

            if(Q==1){
                V = Long.parseLong(input[3]);

                /* 구간에 대한 덧셈은, 곱셈에 대한 레이지 값은 1, 덧셈에 대한 레이지 값은 v로 설정 */
                update(L, R, 1, V);
            }else if(Q==2){
                V = Long.parseLong(input[3]);

                /* 구간에 대한 곱셈은, 곱셈에 대한 레이지 값은 v, 덧셈에 대한 레이지 값은 0으로 설정 */
                update(L, R, V, 0);
            }else if(Q==3){
                V = Long.parseLong(input[3]);

                /* 구간에 대한 대입은, 곱셈에 대한 레이지 값은 0, 덧셈에 대한 레이지 값은 v로 설정 */
                update(L, R, 0, V);
            }else if(Q==4){
                bw.write(query(L,R)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}