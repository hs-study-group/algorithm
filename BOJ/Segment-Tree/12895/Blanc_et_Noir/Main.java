/* https://www.acmicpc.net/problem/12895 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static int[] tree;
    static int[] lazy;

    /* 세그트리 초기화 메소드 */
    public static void init(int node, int start, int end) {
        if(start==end) {
            tree[node] = 1;
            
            return;
        }else {
            int mid = (start+end)/2;

            init(node*2,start,mid);
            init(node*2+1,mid+1,end);

            /* 두 노드의 OR값을 활용함 */
            tree[node] = tree[node*2]|tree[node*2+1];

            return;
        }
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int node, int start, int end) {
        /* 레이지 값이 남아있다면 */
        if(lazy[node]!=0) {
            /* 레이지 값 처리 */
            tree[node] = lazy[node];

            /* 리프노드가 아니라면 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] = lazy[node];
                lazy[node*2+1] = lazy[node];
            }

            /* 레이지 값 초기화 */
            lazy[node]=0;
        }
    }

    /* 세그트리의 값을 변경하는 업데이트 메소드 */
    public static void update(int node, int start, int end, int left, int right, int v) {
        /* 레이지 값 처리 */
        process(node,start,end);
        
        if(start>right||end<left) {
            return;
        }else if(left<=start&&end<=right) {
            /* 세그트리의 값을 v로 변경 */
            tree[node] = v;

            /* 리프노드가 아니라면 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] = v;
                lazy[node*2+1] = v;
            }

            return;
        }else {
            int mid = (start+end)/2;

            update(node*2,start,mid,left,right,v);
            update(node*2+1,mid+1,end,left,right,v);

            tree[node] = tree[node*2]|tree[node*2+1];
            
            return;
        }
    }

    /* 특정 구간의 색의 종류를 리턴하는 쿼리 메소드 */
    public static int query(int node, int start, int end, int left, int right) {
        /* 레이지 값 처리 */
        process(node,start,end);
        
        if(start>right||end<left) {
            return 0;
        }else if(left<=start&&end<=right) {
            return tree[node];
        }else {
            int mid = (start+end)/2;

            return query(node*2,start,mid,left,right)|query(node*2+1,mid+1,end,left,right);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] temp = br.readLine().split(" ");

        int N = Integer.parseInt(temp[0]);
        int T = Integer.parseInt(temp[1]);
        int Q = Integer.parseInt(temp[2]);

        tree = new int[4*N];
        lazy = new int[4*N];

        /* 세그트리 초기화 */
        init(1,0,N-1);

        for(int i=0; i<Q; i++) {
            temp = br.readLine().split(" ");
            
            int x = Integer.parseInt(temp[1])-1;
            int y = Integer.parseInt(temp[2])-1;

            /* 입력값 교환 */
            if(x>y) {
                int p = x;
                x = y;
                y = p;
            }

            if(temp[0].equals("C")) {
                int z = 1<<(Integer.parseInt(temp[3])-1);

                update(1,0,N-1,x,y,z);
            }else {
                bw.write(Integer.bitCount(query(1,0,N-1,x,y))+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}