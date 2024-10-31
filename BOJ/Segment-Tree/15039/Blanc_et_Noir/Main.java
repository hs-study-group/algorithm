/* https://www.acmicpc.net/problem/15039 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static final int MOD = 9;
    public static int N, Q;
    public static int[] arr, lazy;

    /* tree[n][m] = n구간에 m값이 몇 개가 존재하는지를 저장하는 세그트리 */
    public static int[][] tree;

    /* 레이지 값을 처리하는 메소드 */
    public static void process(int n, int s, int e){
        /* 처리할 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] = (lazy[n*2] + lazy[n])%MOD;
            lazy[n*2+1] = (lazy[n*2+1] + lazy[n])%MOD;
        }

        /* 레이지 값 처리 */
        tree[n] = shift(
                tree[n],
                lazy[n]
        );

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 배열의 값을 v만큼씩 시프트하는 메소드 */
    public static int[] shift(int[] arr, int v){
        int[] result = new int[MOD];

        for(int i=0; i<MOD; i++){
            result[(i+v)%MOD] = arr[i];
        }

        return result;
    }

    /* 두 쿼리의 결과를 하나로 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        int[] result = new int[MOD];

        for(int i=0; i<MOD; i++){
            result[i] = arr1[i] + arr2[i];
        }

        return result;
    }

    /* 세그트리를 초기화 하는 메소드 */
    public static void init(){
        /* 배열의 값을 1로 초기화 */
        for(int i=0; i<N; i++){
            arr[i] = 1;
        }

        init(1, 0, N-1);
    }

    /* 세그트리를 초기화 하는 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 초기에는 n구간에 1이 e-s+1개 존재함 */
            tree[n][1] = (e-s+1);
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            /* merge 메소드로 병합하는 것도 가능함 */
            tree[n][1] = tree[n*2][1] + tree[n*2+1][1];
        }
    }

    /* 특정 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, N-1, l, r, v);
    }

    /* 특정 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v){
        /* 레이지 값 처리 */
        process(n, s, e);
        
        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 레이지 값 전파 */
            lazy[n] = (lazy[n] + v)%MOD;

            /* 레이지 값 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v);
            update(n*2+1, (s+e)/2+1, e, l, r, v);

            tree[n] = merge(
                    tree[n*2],
                    tree[n*2+1]
            );
        }
    }

    /* 특정 구간에서 가장 많은 숫자가 무엇인지 찾는 쿼리 메소드 */
    public static int query(int l, int r){
        int[] arr = query(1, 0, N-1, l, r);
        int MAX = 0, result = 0;

        for(int i=0; i<MOD; i++){
            /* i값이 MAX개 이상으로 많다면 그것을 정답으로 갱신함 */
            if(arr[i]>=MAX){
                MAX = arr[i];
                result = i;
            }
        }

        return result;
    }

    /* 특정 구간에서 가장 많은 숫자가 무엇인지 찾는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return new int[MOD];
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);

        arr = new int[N];
        lazy = new int[4*N];
        tree = new int[4*N][MOD];

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            int L = Integer.parseInt(input[0]);
            int R = Integer.parseInt(input[1]);
            int V = query(L, R);

            update(L, R, V);
        }

        for(int i=0; i<N; i++){
            bw.write(query(i,i)+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}