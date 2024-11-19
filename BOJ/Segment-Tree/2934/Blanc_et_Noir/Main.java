/* https://www.acmicpc.net/problem/2934 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static int[] tree, lazy;

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] += lazy[n];
            lazy[n*2+1] += lazy[n];
        }

        /* 구간에 포함되는 값들을 레이지 값만큼 증가시킴 */
        tree[n] += lazy[n] * (e-s+1);

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 구간 l ~ r 을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, M-1, l, r, v);
    }

    /* 구간 l ~ r 을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 레이지 값 전파 */
            lazy[n] += v;

            /* 레이지 값 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v);
            update(n*2+1, (s+e)/2+1, e, l, r, v);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* 구간 l ~ r 에 대하여 꽃이 핀 식물의 개수를 구하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, M-1, l, r);
    }

    /* 구간 l ~ r 에 대하여 꽃이 핀 식물의 개수를 구하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return query(n*2, s, (s+e)/2, l, r) + query(n*2+1, (s+e)/2+1, e, l, r);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());
        M = 100001;

        tree = new int[4*M];
        lazy = new int[4*M];

        for(int i=0; i<N; i++){
            input = br.readLine().split(" ");

            int L = Integer.parseInt(input[0])-1;
            int R = Integer.parseInt(input[1])-1;

            /* 새로운 구간의 시작과 끝 부분에만 꽃이 핌 */
            bw.write((query(L,L)+query(R,R))+"\n");

            /* 구간의 값을 1씩 증가시킴 */
            update(L, R, 1);
            
            /* 구간의 시작과 끝을 0으로 변경함 */
            update(L, L, -query(L,L));
            update(R, R, -query(R,R));
        }

        bw.flush();
        bw.close();
        br.close();
    }
}