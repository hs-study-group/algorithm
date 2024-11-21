/* https://www.acmicpc.net/problem/3653 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[] tree, arr, index;
    public static int N, M, K, T;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, K-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 초기 범위를 벗어나지 않는 노드의 값은 1로 설정함 */
            if(s<N){
                tree[n] = 1;
            }
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, K-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
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

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, K-1, i, v);
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; i++){
            input = br.readLine().split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);
            K = 200001;

            arr = new int[M];
            index = new int[N];
            tree = new int[4*K];

            input = br.readLine().split(" ");

            for(int j=0; j<N; j++){
                index[j] = N-1-j;
            }

            for(int j=0; j<M; j++){
                arr[j] = Integer.parseInt(input[j])-1;
            }

            /* 세그트리 초기화 */
            init();

            for(int j=0; j<M; j++){
                /* 원하는 DVD의 현재 인덱스를 얻음 */
                int idx = index[arr[j]];
                
                /* 그보다 더 위에 있는 DVD의 개수를 구함 */
                bw.write(query(idx+1, K)+" ");

                /* 현재 위치의 DVD 개수를 0으로 설정함 */
                update(index[arr[j]], 0);
                
                /* 원하는 DVD의 인덱스를 새로 계산함 */
                index[arr[j]] = N+j;
                
                /* 새로 계산된 인덱스의 DVD 개수를 1로 설정함 */
                update(N+j, 1);
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}