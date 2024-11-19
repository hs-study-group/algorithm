/* https://www.acmicpc.net/problem/3002 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static final int MOD = 10;
    public static int N, M;
    public static int[] arr, lazy;
    public static int[][] tree;

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 레이지 값이 없으면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] = (lazy[n*2] + lazy[n])%MOD;
            lazy[n*2+1] = (lazy[n*2+1] + lazy[n])%MOD;
        }

        /* 레이지 값만큼 시프팅 */
        tree[n] = shift(tree[n], lazy[n]);

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 레이지 값만큼 배열을 시프팅 하는 시프트 메소드 */
    public static int[] shift(int[] arr, int v){
        int[] result = new int[MOD];

        for(int i=0; i<MOD; i++){
            result[(i+v)%MOD] = arr[i];
        }

        return result;
    }

    /* 두 배열을 병합하는 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        int[] result = new int[MOD];

        for(int i=0; i<MOD; i++){
            result[i] = arr1[i] + arr2[i];
        }

        return result;
    }

    /* 세그트리를 초기화하는 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 해당하는 값의 개수를 1 증가시킴 */
            tree[n][arr[s]] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간에 대해 각 숫자의 개수를 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        int[] result = query(1, 0, N-1, l, r);
        int sum = 0;

        for(int i=0; i<MOD; i++){
            sum += i * result[i];
        }

        return sum;
    }

    /* l ~ r 구간에 대해 각 숫자의 개수를 리턴하는 쿼리 메소드 */
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

    /* 주어진 구간에 대해 v만큼 업데이트하는 갱신 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, N-1, l, r, v);
    }

    /* 주어진 구간에 대해 v만큼 업데이트하는 갱신 메소드 */
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

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        arr = new int[N];
        tree = new int[4*N][MOD];
        lazy = new int[4*N];

        input = br.readLine().split("");

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int L = Integer.parseInt(input[0])-1;
            int R = Integer.parseInt(input[1])-1;

            bw.write(query(L, R)+"\n");

            /* 주어진 구간의 숫자를 1씩 증가시킴 */
            update(L, R, 1);
        }

        bw.flush();
        bw.close();
        br.close();
    }
}