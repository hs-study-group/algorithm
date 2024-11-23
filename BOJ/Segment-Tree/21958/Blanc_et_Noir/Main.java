/* https://www.acmicpc.net/problem/21958 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, Q;
    
    /* tree[n][0] : 홀수의 최솟값, tree[n][1] : 홀수의 최댓값, tree[n][2] : 짝수의 최솟값, tree[n][3] : 짝수의 최댓값 */
    public static long[][] tree;
    public static long[] arr, lazy;

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

        /* 레이지 값 만큼 최솟값, 최댓값을 증가시킴 */
        for(int i=0; i<4; i++){
            /* 유효하지 않은 노드라면 continue */
            if(tree[n][i]==-1){
                continue;
            }
            
            tree[n][i] += lazy[n];
        }

        /* 레이지 값이 홀수라면 홀, 짝이 바뀜 */
        if(lazy[n]%2!=0){
            /* 홀의 최솟값과 짝의 최솟값을 스왑하고, 홀의 최댓값과 짝의 최댓값을 스왑함 */
            for(int i=0; i<2; i++){
                long t = tree[n][0+i];
                tree[n][0+i] = tree[n][2+i];
                tree[n][2+i] = t;
            }
        }

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static long[] merge(long[] arr1, long[] arr2){
        long[] result = new long[4];

        /* 왼쪽 노드의 홀수 최솟값, 오른쪽 노드의 홀수 최솟값을 최종 홀수의 최솟값으로 설정 */
        result[0] = arr1[0]!=-1&&arr2[0]!=-1?Math.min(arr1[0], arr2[0]):Math.max(arr1[0], arr2[0]);
        
        /* 왼쪽 노드의 홀수 최댓값, 오른쪽 노드의 홀수 최댓값을 최종 홀수의 최댓값으로 설정 */
        result[1] = Math.max(arr1[1], arr2[1]);

        /* 왼쪽 노드의 짝수 최솟값, 오른쪽 노드의 짝수 최솟값을 최종 짝수의 최솟값으로 설정 */
        result[2] = arr1[2]!=-1&&arr2[2]!=-1?Math.min(arr1[2], arr2[2]):Math.max(arr1[2], arr2[2]);

        /* 왼쪽 노드의 짝수 최댓값, 오른쪽 노드의 짝수 최댓값을 최종 짝수의 최댓값으로 설정 */
        result[3] = Math.max(arr1[3], arr2[3]);

        return result;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* s번째 숫자가 홀수라면, 홀수의 최소, 최댓값으로 설정 */
            if(arr[s]%2!=0){
                tree[n][0] = arr[s];
                tree[n][1] = arr[s];
            /* s번째 숫자가 짝수라면, 짝수의 최소, 최댓값으로 설정 */
            }else{
                tree[n][2] = arr[s];
                tree[n][3] = arr[s];
            }
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 홀수의 최소, 최대, 짝수의 최소, 최댓값을 구하는 쿼리 메소드 */
    public static long[] query(int l, int r){
        long[] result = query(1, 0, N-1, l, r);

        return new long[]{
                result[2],  result[1]
        };
    }

    /* l ~ r 구간의 홀수의 최소, 최대, 짝수의 최소, 최댓값을 구하는 쿼리 메소드 */
    public static long[] query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return new long[]{-1, -1, -1, -1};
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, long v){
        update(1, 0, N-1, l, r, v);
    }

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
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
        String[] input;

        N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N][4];
        lazy = new long[4*N];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        for(int i=0; i<4*N; i++){
            for(int j=0; j<4; j++){
                tree[i][j] = -1;
            }
        }

        /* 세그트리 초기화 */
        init();

        Q = Integer.parseInt(br.readLine());

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            int M = Integer.parseInt(input[0]);
            int L = Integer.parseInt(input[1])-1;
            int R = Integer.parseInt(input[2])-1;

            if(M==0){
                long V = Long.parseLong(input[3]);

                update(L, R, V);
            }else if(M==1){
                long[] result = query(L, R);

                bw.write(result[0]+" "+result[1]+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}