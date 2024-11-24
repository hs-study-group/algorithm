/* https://www.acmicpc.net/problem/15678 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, D;
    public static int[] arr;

    /* DP[i] = i번째 위치까지 도달했을때 얻을 수 있는 최대 점수 */
    public static long[] DP, tree;

    /* 두 노드를 병합하는 병합 메소드 */
    public static long merge(long v1, long v2){
        return Math.max(v1, v2);
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
            tree[n] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 최댓값을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 최댓값을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return Long.MIN_VALUE;
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
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        D = Integer.parseInt(input[1]);

        arr = new int[N];
        DP = new long[N];
        tree = new long[4*N];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        for(int i=0; i<N; i++){
            DP[i] = arr[i];

            /* i번째까지 왔을때 얻을 수 있는 최대 점수는 */
            /* 1: i번째 위치에서 게임을 시작하거나 */
            /* 2: i-D ~ i-1 위치에 도달할때의 최대 점수 + i번째 위치를 밟았을 때의 점수 */
            /* 1, 2 선택지중 더 큰값을 DP[i]의 값으로 설정 */
            DP[i] = Math.max(DP[i], query(Math.max(i-D,0), Math.max(i-1,0)) + arr[i]);

            /* i번째 위치에 도달했을때 얻을 수 있는 최대 점수인 DP[i]를 세그트리에 저장 */
            update(i, DP[i]);
        }

        bw.write(query(0, N-1)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}