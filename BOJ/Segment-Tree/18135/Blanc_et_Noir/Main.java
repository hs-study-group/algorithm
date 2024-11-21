/* https://www.acmicpc.net/problem/18135 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static long[] tree, lazy, arr;
    public static int[] index;
    public static int N, M, K;

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 처리해야핳 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] += lazy[n];
            lazy[n*2+1] += lazy[n];
        }

        /* 레이지 값 * 구간의 개수만큼 구간합을 증가시킴 */
        tree[n] += (lazy[n]*(e-s+1));

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static long merge(long v1, long v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, M-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = arr[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, M-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    /* l ~ r 구간을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, long v){
        update(1, 0, M-1, l, r, v);
    }

    /* l ~ r 구간을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, long v){
        /* 레이지 값 처리 */
        process(n, s, e);

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
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        index = new int[N];
        arr = new long[N];
        tree = new long[4*M];
        lazy = new long[4*M];

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int L = Integer.parseInt(input[0])-1;
            int R = Integer.parseInt(input[1])-1;
            int V = Integer.parseInt(input[2]);

            /* L ~ R 구간을 같은 집합으로 묶음 */
            for(int j=L; j<=R; j++){
                index[j] = K;
            }

            /* K번째 집합에 V만큼 값을 추가함 */
            arr[K++] = V;
        }

        /* 세그트리 초기화 */
        init();

        while(true){
            input = br.readLine().split(" ");

            if(input[0].equals("0")){
                break;
            }

            int Q = Integer.parseInt(input[0]);
            int L = Integer.parseInt(input[1])-1;
            int R = Integer.parseInt(input[2])-1;
            long V;
            
            if(Q==1){
                /* L ~ R 구간이 Wrap Around 되지 않는다면 그대로 구간합을 계산 */
                if(L<=R){
                    bw.write(query(index[L], index[R])+"\n");
                /* L ~ R 구간이 Wrap Around 된다면 구간합을 나누어 계산 */
                }else{
                    bw.write((query(index[L], M-1)+query(0, index[R]))+"\n");
                }
            }else if(Q==2){
                V = Long.parseLong(input[3]);
                /* L ~ R 구간이 Wrap Around 되지 않는다면 그대로 구간합을 업데이트 */
                if(L<=R){
                    update(index[L], index[R], V);
                /* L ~ R 구간이 Wrap Around 된다면 구간합을 나누어 업데이트 */
                }else{
                    update(index[L], M-1, V);
                    update(0, index[R], V);
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}