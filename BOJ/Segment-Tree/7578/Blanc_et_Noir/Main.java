/* https://www.acmicpc.net/problem/7578 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[] tree, arr, index;
    public static int N, M = 1000001;
    public static long K;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, N-1, l, r);
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

    /* idx번째 값을 v로 업데이트하는 업데이트 메소드 */
    public static void update(int idx, int v){
        update(1, 0, N-1, idx, v);
    }

    /* idx번째 값을 v로 업데이트하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int idx, int v){
        if(idx<s||idx>e){
            return;
        }else if(s==e){
            tree[n] = v;
        }else{
            update(n*2, s, (s+e)/2, idx, v);
            update(n*2+1, (s+e)/2+1, e, idx, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        arr = new int[N];
        index = new int[M];
        tree = new int[4*N];

        /* index[N] = 위쪽의 N값이 아래쪽의 몇 번째에 연결되는지를 저장할 배열 */
        for(int i=0; i<M; i++){
            index[i] = -1;
        }

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            index[Integer.parseInt(input[i])] = i;
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<N; i++){
            /* 반대편의 연결 지점의 값을 0으로 변경 */
            update(index[arr[i]], 0);

            /* 교차점의 개수를 구함 */
            K += query(0, index[arr[i]]);
        }

        bw.write(K+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}