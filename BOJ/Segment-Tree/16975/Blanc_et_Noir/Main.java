/* https://www.acmicpc.net/problem/16975 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /* 세그트리 초기화 메소드 */
    public static void init(long[] arr, long[] tree, int node, int start, int end) {
        if(start==end) {
            tree[node] = arr[start];
            return;
        }else {
            int mid = (start+end)/2;

            init(arr,tree,node*2,start,mid);
            init(arr,tree,node*2+1,mid+1,end);

            return;
        }
    }

    public static long query(long[] tree, long[] lazy, int node, int start, int end, int left, int right) {
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

    /* 특정 구간의 값들에 val을 더하는 갱신 메소드 */
    public static void update(long[] tree, long[] lazy, int node, int start, int end, int left, int right, long val) {
        /* 레이지 값 처리 */
        process(tree,lazy,node,start,end);
        
        if(start>right||end<left) {
            return;
        }else if(left<=start&&end<=right) {
            /* 해당 구간의 크기 * val값을 해당 노드에 누적하여 더함 */
            tree[node] += val*(end-start+1);

            /* 리프노드가 아니라면 자식 노드에 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] += val;
                lazy[node*2+1] += val;
            }

            return;
        }else {
            update(tree,lazy,node*2,start,(start+end)/2,left,right,val);
            update(tree,lazy,node*2+1,(start+end)/2+1,end,left,right,val);
            
            return;
        }
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(long[] tree, long[] lazy, int node, int start, int end) {
        /* 해당 노드의 레이지 값이 있다면 */
        if(lazy[node]!=0) {
            /* 레이지 값 처리 */
            tree[node] += lazy[node]*(end-start+1);

            /* 리프노드가 아니라면 레이지 값 전파 */
            if(start!=end) {
                lazy[node*2] += lazy[node];
                lazy[node*2+1] += lazy[node];
            }

            /* 레이지 값 초기화 */
            lazy[node] = 0;
        }
    }

    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());
        final int TREE_SIZE = 1<<((int) Math.ceil(Math.log(N)/Math.log(2))+1);

        long[] arr = new long[N];
        long[] tree = new long[TREE_SIZE];
        long[] lazy = new long[TREE_SIZE];

        String[] temp = br.readLine().split(" ");

        for(int i=0; i<temp.length; i++) {
            arr[i] = Long.parseLong(temp[i]);
        }

        /* 세그트리 초기화 */
        init(arr,tree,1,0,N-1);

        int M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++) {
            temp = br.readLine().split(" ");

            int A = Integer.parseInt(temp[0]);
            int B = Integer.parseInt(temp[1])-1;

            if(A==1) {
                int C = Integer.parseInt(temp[2])-1;
                long D = Long.parseLong(temp[3]);

                update(tree,lazy,1,0,N-1,B,C,D);
            }else {
                bw.write(query(tree,lazy,1,0,N-1,B,B)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}