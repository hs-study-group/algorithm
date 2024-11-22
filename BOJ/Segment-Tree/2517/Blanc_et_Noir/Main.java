/* https://www.acmicpc.net/problem/2517 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N;

    /* sorted = 좌표압축을 위해 정렬에 사용할 배열 */
    public static int[] arr, sorted, tree;
    
    /* 압축된 좌표를 저장할 해시맵 */
    public static HashMap<Integer, Integer> hm;

    /* v값을 i에 좌표압축하는 메소드 */
    public static void set(int v, int i){
        hm.put(v, i);
    }

    /* v값에 매핑된 압축좌표를 찾는 메소드 */
    public static int get(int v){
        return hm.get(v);
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return v1 + v2;
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

    /* i 번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, N-1, i, v);
    }

    /* i 번째 값을 v로 변경하는 업데이트 메소드 */
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
        N = Integer.parseInt(br.readLine());

        arr = new int[N];
        sorted = new int[N];
        tree = new int[4*N];
        hm = new HashMap<Integer, Integer>();

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(br.readLine())-1;
            sorted[i] = arr[i];
        }

        /* 오름차순 정렬 */
        Arrays.sort(sorted);

        /* 높은 값부터 0, 1, 2 ... 로 좌표압축 수행 */
        for(int i=0; i<N; i++){
            set(sorted[i], N-1-i);
        }

        for(int i=0; i<N; i++){
            /* 자신의 값을 업데이트 */
            update(get(arr[i]), 1);

            /* 자신보다 실력이 좋은 선수의 수 + 1을 구함 */
            bw.write(query(0, get(arr[i]))+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}