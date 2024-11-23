/* https://www.acmicpc.net/problem/25639 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static long[][] tree;
    public static long[] arr;

    /* v1, v2, v3 중 가장 큰 값을 리턴하는 메소드 */
    public static long max(long v1, long v2, long v3){
        return Math.max(v1, Math.max(v2, v3));
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static long[] merge(long[] arr1, long[] arr2){
        return new long[]{
                /* 두 노드의 최솟값중 더 작은 값을 최종 결과의 최솟값으로 설정 */
                Math.min(arr1[0], arr2[0]),
                /* 두 노드의 최댓값중 더 큰 값을 최종 결과의 최댓값으로 설정 */
                Math.max(arr1[1], arr2[1]),
                /* 왼쪽노드의 최댓값-최솟값, 오른쪽 노드의 최댓값-최솟값, 최종 결과의 최댓값-최솟값 중 더 큰 값을 최종 결과로 설정 */
                max(
                        arr1[2],
                        arr2[2],
                        arr2[1]-arr1[0]
                )
        };
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 구간의 최솟값 */
            tree[n][0] = arr[s];

            /* 구간의 최댓값 */
            tree[n][1] = arr[s];

            /* 최댓값과 최솟값의 차이 */
            tree[n][2] = 0;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, N-1, i, v);
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n][0] = v;
            tree[n][1] = v;
            tree[n][2] = 0;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 가장 큰 최댓값 - 최솟값 결과를 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r)[2];
    }

    /* l ~ r 구간의 가장 큰 최댓값 - 최솟값 결과를 리턴하는 쿼리 메소드 */
    public static long[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new long[]{
                    Integer.MAX_VALUE,
                    Integer.MIN_VALUE,
                    0
            };
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

        N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N][3];

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

            if(Q==1){
                update(L, R+1);
            }else if(Q==2){
                bw.write(query(L, R)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}