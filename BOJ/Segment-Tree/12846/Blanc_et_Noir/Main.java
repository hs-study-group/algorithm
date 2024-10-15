//https://www.acmicpc.net/problem/12846
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static long[] arr;
    static long[][] tree;
    static long max = Long.MIN_VALUE;

    /* 두 배열중 더 작은값을 가진 배열을 리턴하는 병합 메소드 */
    public static long[] merge(long[] r1, long[] r2) {
        if(r1[0]<=r2[0]) {
            return r1;
        }else {
            return r2;
        }
    }

    /* 세그트리를 초기화하는 메소드 */
    public static void init(int n, int s, int e) {
        if(s==e) {
            tree[n][0] = arr[s];
            tree[n][1] = s;
            
            return;
        }else {
            init(n*2,s,(s+e)/2);
            init(n*2+1,(s+e)/2+1,e);

            tree[n] = merge(tree[n*2],tree[n*2+1]);

            return;
        }
    }

    /* 특정 구간의 최소값과 그때의 인덱스를 리턴하는 쿼리 메소드 */
    public static long[] query(int n, int s, int e, int l, int r) {
        if(s>r||e<l) {
            return new long[] {Long.MAX_VALUE, -1};
        }else if(l<=s&&e<=r) {
            return tree[n];
        }else {
            return merge(query(n*2,s,(s+e)/2,l,r), query(n*2+1,(s+e)/2+1,e,l,r));
        }
    }

    /* 이분탐색을 시도하는 재귀 메소드 */
    public static void recursive(int l, int r) {
        if(l>r) {
            return;
        }else {
            long[] q = query(1,0,arr.length-1,l,r);

            /* 구간의 크기 * 최소값의 결과가 기존보다 크다면 갱신 */
            max = Math.max(max, q[0]*(r-l+1));

            /* 좌우 구간을 재귀 탐색 */
            recursive(l,(int)q[1]-1);
            recursive((int)q[1]+1,r);

            return;
        }
    }

    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N][2];

        String[] temp = br.readLine().split(" ");

        for(int i=0; i<temp.length; i++) {
            arr[i] = Long.parseLong(temp[i]);
        }

        /* 세그트리 초기화 */
        init(1,0,N-1);

        /* 이분탐색 시작 */
        recursive(0,N-1);

        bw.write(max+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}