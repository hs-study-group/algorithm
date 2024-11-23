/* https://www.acmicpc.net/problem/13372 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, T;
    public static long K;
    public static long[] tree;
    public static int[] arr;

    /* 두 노드를 병합하는 병합 메소드 */
    public static long merge(long v1, long v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = 1L;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, long v){
        update(1, 0, N-1, i, v);
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, long v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] += v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
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

    public static void main(String[] args) throws IOException {
        String[] input;

        T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; i++){
            K = 0;
            N = Integer.parseInt(br.readLine());

            arr = new int[N];
            tree = new long[4*N];

            /* 세그트리 초기화 */
            init();

            input = br.readLine().split(" ");

            for(int j=0; j<N; j++){
                arr[j] = Integer.parseInt(input[j])-1;
            }

            for(int j=0; j<N; j++){
                /* 시작점은 자신보다 오른쪽에 있으나, 종료점이 자신보다 왼쪽에 있는 정점의 개수를 누적하여 더함 */
                K += query(0, arr[j]-1);

                /* 탐색중인 현재 정점을 없앰 */
                update(arr[j], -1);
            }

            bw.write(K+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}