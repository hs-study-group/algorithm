/* https://www.acmicpc.net/problem/10090 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M=1000001;
    public static long[] tree;
    public static long K;

    /* 두 노드를 병합하는 병합 메소드 */
    public static long merge(long v1, long v2){
        return v1 + v2;
    }

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, M-1, l, r);
    }

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
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

    /* i번째 값을 v 만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, M-1, i, v);
    }

    /* i번째 값을 v 만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
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

        tree = new long[4*M];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            int I = Integer.parseInt(input[i]);

            /* I ~ M-1 구간의 합을 구함 */
            K += query(I, M-1);

            /* 현재 위치의 값을 1만큼 증가시킴 */
            update(I, 1);
        }

        bw.write(K+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}