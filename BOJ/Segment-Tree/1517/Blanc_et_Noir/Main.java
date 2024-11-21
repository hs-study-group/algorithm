/* https://www.acmicpc.net/problem/1517 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static long[] tree, arr, sorted;
    public static int N;
    public static long K;
    /* 좌표 압축용 해시맵 */
    public static HashMap<Long, Integer> hm = new HashMap<Long, Integer>();

    /* 정수 v가 몇 번째 수인지를 리턴하는 메소드 */
    public static int get(long v){
        return hm.get(v);
    }

    /* 점수 v가 i번째 숫자임을 설정하는 메소드 */
    public static void set(long v, int i){
        hm.put(v, i);
    }

    /* 두 노드를 병합하는 메소드 */
    public static long merge(long v1, long v2){
        return v1 + v2;
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

    /* i번째 값을 v만큼 증가시키는 메소드 */
    public static void update(int i, long v){
        update(1, 0, N-1, i, v);
    }

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

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        arr = new long[N];
        sorted = new long[N];
        tree = new long[4*N];

        input = br.readLine().split(" ");

        /* 정수를 입력 받음 */
        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
            sorted[i] = Integer.parseInt(input[i]);
        }

        /* 입력받은 정수를 오름차순 정렬 */
        Arrays.sort(sorted);

        /* 각각의 정수를 내림차순으로 등수를 부여 */
        for(int i=N-1; i>=0; i--){
            set(sorted[i], (N-1)-i);
        }

        for(int i=0; i<N; i++){
            /* arr[i]이 N번째 숫자라면, 그보다 큰 0부터 N-1번째 숫자의 개수를 누적하여 더함 */
            K += query(0, get(arr[i])-1);

            /* arr[i]가 N번째 숫자라면, N번째 숫자의 개수를 1 증가시킴 */
            update(get(arr[i]), 1);
        }

        bw.write(K+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}