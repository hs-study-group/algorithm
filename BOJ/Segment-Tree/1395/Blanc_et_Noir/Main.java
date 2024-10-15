/* https://www.acmicpc.net/problem/1395 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int[] tree, int[] lazy, int node, int start, int end) {
        /* 레이지 값이 남아있다면 */
        if(lazy[node]!=0) {
            /* 해당 구간에 불이 K개 켜져있다면, 반전시키면 N-K개가 남아있음 */
            tree[node] = (end-start+1)-tree[node];

            /* 리프노드가 아니라면 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] = (lazy[node*2]+1)%2;
                lazy[node*2+1] = (lazy[node*2+1]+1)%2;
            }

            /* 레이지 값 초기화 */
            lazy[node] = 0;
        }
    }

    /* 주어진 구간의 합을 리턴하는 쿼리 메소드 */
    public static int query(int[] tree, int[] lazy, int node, int start, int end, int left, int right) {
        /* 레이지 값 처리 */
        process(tree,lazy,node,start,end);

        if(start>right||end<left) {
            return 0;
        }else if(left<=start&&end<=right) {
            return tree[node];
        }else {
            return query(tree,lazy,node*2,start,(start+end)/2,left,right)+query(tree,lazy,node*2+1,(start+end)/2+1,end,left,right);
        }
    }

    /* 특정한 구간을 반전시키는 갱신 메소드 */
    public static void update(int[] tree, int[] lazy, int node, int start, int end, int left, int right) {
        /* 레이지 값 처리 */
        process(tree,lazy,node,start,end);

        if(start>right||end<left) {
            return;
        }else if(left<=start&&end<=right) {
            /* 해당 구간에 불이 K개 켜져있다면, 반전시키면 N-K개가 남아있음 */
            tree[node] = (end-start+1)-tree[node];

            /* 리프노드가 아니라면 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] = (lazy[node*2]+1)%2;
                lazy[node*2+1] = (lazy[node*2+1]+1)%2;
            }

            return;
        }else {
            update(tree,lazy,node*2,start,(start+end)/2,left,right);
            update(tree,lazy,node*2+1,(start+end)/2+1,end,left,right);

            tree[node] = tree[node*2]+tree[node*2+1];

            return;
        }
    }

    public static void main(String[] args) throws Exception {
        String[] temp = br.readLine().split(" ");

        int N = Integer.parseInt(temp[0]);
        int M = Integer.parseInt(temp[1]);

        final int TREE_SIZE = 1<<((int) Math.ceil(Math.log(N)/Math.log(2))+1);

        int[] arr = new int[N];
        int[] tree = new int[TREE_SIZE];
        int[] lazy = new int[TREE_SIZE];

        for(int i=0; i<M; i++) {
            temp = br.readLine().split(" ");

            int A = Integer.parseInt(temp[0]);
            int B = Integer.parseInt(temp[1])-1;
            int C = Integer.parseInt(temp[2])-1;

            if(A==0) {
                update(tree,lazy,1,0,N-1,B,C);
            }else {
                bw.write(query(tree,lazy,1,0,N-1,B,C)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}