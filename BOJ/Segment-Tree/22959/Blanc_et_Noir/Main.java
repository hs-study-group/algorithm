/* https://www.acmicpc.net/problem/22959 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static long[][] tree;
    public static long[] arr;

    /* 두 노드를 병합하는 병합 메소드 */
    public static long[] merge(long[] arr1, long[] arr2){
        return new long[]{
                /* 왼쪽 노드와 오른쪽 노드의 크기를 합함 */
                arr1[0] + arr2[0],
                
                /* 왼쪽 노드와 오른쪽 노드의 최솟값중 더 작은값 */
                Math.min(arr1[1], arr2[1])
        };
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n][0] = arr[s];
            tree[n][1] = arr[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int i, long v){
        update(1, 0, N-1, i, v);
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, long v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n][0] = v;
            tree[n][1] = v;
            arr[s] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합, 최솟값을 리턴하는 쿼리 메소드 */
    public static long[] query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 구간합, 최솟값을 리턴하는 쿼리 메소드 */
    public static long[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new long[]{0, Long.MAX_VALUE};
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
        tree = new long[4*N][2];

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
            int I = Integer.parseInt(input[1])-1;
            long V = Long.parseLong(input[2]);

            if(Q==1){
                update(I, V);
            }else if(Q==2){
                /* I를 기준으로 연속된 왼쪽 구간중 최소값이 V이상이 되도록 하는 지점을 이분탐색 */
                int S = 0;
                int E = I-1;
                long MAX = 0;
                long K = 0;

                while(S<=E){
                    int L = (S+E)/2;
                    int R = I-1;

                    long[] result = query(L, R);

                    if(result[1]>=V){
                        MAX = Math.max(MAX, result[0]);
                        E = L - 1;
                    }else{
                        S = L + 1;
                    }
                }

                /* 구간의 합이 최대가 될 때의 값을 누적하여 더함 */
                K += MAX;

                /* I를 기준으로 연속된 오른쪽 구간중 최소값이 V이상이 되도록 하는 지점을 이분탐색 */
                S = I+1;
                E = N-1;
                MAX = 0;

                while(S<=E){
                    int L = I+1;
                    int R = (S+E)/2;

                    long[] result = query(L, R);

                    if(result[1]>=V){
                        MAX = Math.max(MAX, result[0]);
                        S = R + 1;
                    }else{
                        E = R - 1;
                    }
                }

                /* 구간의 합이 최대가 될 때의 값을 누적하여 더함 */
                K += MAX;

                /* I번째 값, 왼쪽 구간의 구간합, 오른쪽 구간의 구간합을 모두 더한 결과를 출력 */
                bw.write((K+arr[I])+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}