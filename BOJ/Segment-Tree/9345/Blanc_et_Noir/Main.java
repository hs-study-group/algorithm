/* https://www.acmicpc.net/problem/9345 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int T, N, M;
    public static int[][] tree;
    public static int[] arr;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        return new int[]{
                /* 두 노드의 최솟값중 더 작은 값 */
                Math.min(arr1[0], arr2[0]),

                /* 두 노드의 최댓값중 더 큰 값 */
                Math.max(arr1[1], arr2[1]),

                /* 두 구간의 크기를 합함 */
                arr1[2] + arr2[2]
        };
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 구간의 최솟값, 최댓값, 구간의 크기 */
            tree[n][0] = arr[s];
            tree[n][1] = arr[s];
            tree[n][2] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l 번째 값과 r 번째 값을 스왑하는 스왑 메소드 */
    public static void swap(int l, int r){
        int t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;

        update(l, arr[l]);
        update(r, arr[r]);
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
            tree[n][2] = 1;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 최솟값, 최댓값, 구간의 크기를 리턴하는 쿼리 메소드 */
    public static boolean query(int l, int r){
        int[] result = query(1, 0, N-1, l, r);

        /* 최솟값이 l, 최댓값이 r, 구간의 크기가 l ~ r의 크기와 동일하다면 성공 */
        if(result[0]==l&&result[1]==r&&result[2]==r-l+1){
            return true;
        }

        return false;
    }

    /* l ~ r 구간의 최솟값, 최댓값, 구간의 크기를 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
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
            input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            arr = new int[N];
            tree = new int[4*N][3];

            for(int j=0; j<N; j++){
                arr[j] = j;
            }

            /* 세그트리 초기화 */
            init();

            for(int j=0; j<M; j++){
                input = br.readLine().split(" ");

                int Q = Integer.parseInt(input[0]);
                int L = Integer.parseInt(input[1]);
                int R = Integer.parseInt(input[2]);

                if(Q==0){
                    swap(L, R);
                }else if(Q==1){
                    bw.write((query(L, R)?"YES":"NO")+"\n");
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}