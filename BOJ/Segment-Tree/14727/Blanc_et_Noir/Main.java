/* https://www.acmicpc.net/problem/14727 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static long[] arr;
    static long[][] tree;

    /* 두 값중 더 작은 값과 그때의 인덱스를 하나의 배열로 병합하는 메소드 */
    public static long[] merge(long[] q1, long[] q2) {
        return q1[0]<=q2[0]?q1:q2;
    }

    /* 세그트리를 초기화 하는 메소드 */
    public static void init(int n, int s, int e) {
        if(s==e) {
            tree[n][0] = arr[s];
            tree[n][1] = s;
            return;
        }else {
            int m = (s+e)/2;

            init(n*2,s,m);
            init(n*2+1,m+1,e);

            tree[n] = merge(tree[n*2],tree[n*2+1]);
            return;
        }
    }

    /* l ~ r 구간에서의 최소값과 그때의 인덱스를 리턴하는 쿼리 메소드 */
    public static long[] query(int n, int s, int e, int l, int r) {
        if(s>r||e<l) {
            return new long[] {Long.MAX_VALUE,-1};
        }else if(l<=s&&e<=r) {
            return tree[n];
        }else {
            int m = (s+e)/2;
            return merge(query(n*2,s,m,l,r),query(n*2+1,m+1,e,l,r));
        }
    }

    /* 재귀 탐색을 통해 답을 구하는 메소드 */
    public static long recursive(int l, int r) {
        if(l>r) {
            return Long.MIN_VALUE;
        }else {
            /* 쿼리를 통해 l ~ r 구간에서의 최소값, 그때의 인덱스를 구함 */
            long[] t = query(1,0,arr.length-1,l,r);
            int idx = (int) t[1];

            /* 기본 답보다 새로운 답이 더 크다면 그것을 정답으로 갱신함 */
            return Math.max((r-l+1)*t[0], Math.max(recursive(l,idx-1), recursive(idx+1,r)));
        }
    }

    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N][2];

        for(int i=0; i<N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        /* 세그트리 초기화 */
        init(1,0,N-1);

        bw.write(recursive(0,N-1)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}