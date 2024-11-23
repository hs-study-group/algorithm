/* https://www.acmicpc.net/problem/1280 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M = 200000;
    public static long K = 1, MOD = 1000000007;
    
    /* 나무들의 거리합을 저장하는 세그트리 */
    public static long[] tree;
    
    /* 나무들의 개수합을 저장하는 세그트리 */
    public static int[] tree2;

    /* 두 노드를 병합하는 병합 메소드 */
    public static long merge(long v1, long v2){
        return v1 + v2;
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge2(int v1, int v2){
        return v1 + v2;
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, M-1, i, v);
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update2(int i, int v){
        update2(1, 0, M-1, i, v);
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
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

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update2(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree2[n] += v;
        }else{
            update2(n*2, s, (s+e)/2, i, v);
            update2(n*2+1, (s+e)/2+1, e, i, v);

            tree2[n] = merge2(tree2[n*2], tree2[n*2+1]);
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, M-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query2(int l, int r){
        return query2(1, 0, M-1, l, r);
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

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static int query2(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree2[n];
        }else{
            return merge2(
                    query2(n*2, s, (s+e)/2, l, r),
                    query2(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        tree = new long[4*M];
        tree2 = new int[4*M];

        /* 단 한번이라도 비용이 달라진 적 있는지 체크하는 변수 */
        boolean F = false;

        for(int i=0; i<N; i++){
            int V = Integer.parseInt(br.readLine());

            /* V위치에 나무가 심어지므로 거리도 V인 나무가 심어짐 */
            update(V, V);

            /* V위치에 나무가 1개 추가로 심어짐 */
            update2(V, 1);

            /* 첫번째 입력은 이하의 계산 생략 */
            if(i==0){
                continue;
            }

            long S = 0;
            /* 자신보다 왼쪽에 있는 나무들과 비교하여 비용을 계산 */
            long L = V * query2(0, V-1) - query(0, V-1);
            
            /* 자신보다 오른쪽에 있는 나무들과 비교하여 비용을 계산 */
            long R = query(V+1, M-1) - V * query2(V+1, M-1);

            /* 최종 비용 */
            S = L + R;

            /* 비용이 0이 아니라면 누적함 */
            if(S!=0){
                K = (K * (S % MOD)) % MOD;
                F = true;
            }
        }

        /* 한 번이라도 비용이 0이상이 된 적 있으면 K를, 아니라면 0을 출력 */
        bw.write((F?K:0)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}