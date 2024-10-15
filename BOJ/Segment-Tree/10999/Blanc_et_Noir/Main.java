/* https://www.acmicpc.net/problem/10999 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    static long[] arr;
    static long[] tree;
    static long[] lazy;

    /* 세그먼트 트리를 초기화하는 메소드 */
    public static void init(int node, int start, int end) {
        if(start==end) {
            tree[node] = arr[start];

            return;
        }else {
            int mid = (start+end)/2;

            init(node*2,start,mid);
            init(node*2+1,mid+1,end);

            tree[node] = tree[node*2] + tree[node*2+1];

            return;
        }
    }

    /* 세그먼트 트리를 갱신하는 메소드 */
    public static void update(int node, int start, int end, int left, int right, long val) {
        /* 현재 노드의 레이지값 처리 */
        process(node,start,end);

        if(start>right||end<left) {
            return;
        }else if(left<=start&&end<=right) {
            /* 레이지값 처리, 해당 구간에 포함되는 N개의 값을 모두 val만큼 증가시킨 결과를 현재 노드에도 반영 */
            tree[node] += (end-start+1)*val;

            /* 리프노드가 아니라면 자식 노드에게 레이지 전파 */
            if(start!=end) {
                lazy[node*2] += val;
                lazy[node*2+1] += val;
            }

            return;
        }else {
            int mid = (start+end)/2;

            update(node*2,start,mid,left,right,val);
            update(node*2+1,mid+1,end,left,right,val);

            tree[node] = tree[node*2] + tree[node*2+1];
            return;
        }
    }

    /* 주어진 구간의 합을 구하는 쿼리 메소드 */
    public static long query(int node, int start, int end, int left, int right) {
        /* 현재 노드의 레이지값 처리 */
        process(node,start,end);

        if(start>right||end<left) {
            return 0;
        }else if(left<=start&&end<=right) {
            return tree[node];
        }else {
            int mid = (start+end)/2;

            return query(node*2,start,mid,left,right) + query(node*2+1,mid+1,end,left,right);
        }
    }

    /* 레이지 값이 남아 있다면 이를 처리하는 프로세스 메소드 */
    public static void process(int node, int start, int end) {
        /* 현재 노드에 레이지 값이 존재한다면 */
        if(lazy[node]!=0) {
            /* 레이지 값 처리 */
            tree[node] += lazy[node]*(end-start+1);

            /* 리프노드가 아니라면 레이지 전파 */
            if(start!=end) {
                lazy[node*2] += lazy[node];
                lazy[node*2+1] += lazy[node];
            }

            /* 레이지 값 초기화 */
            lazy[node] = 0;
        }
    }

    public static void main(String[] args) throws Exception {
        String[] temp = br.readLine().split(" ");

        int N = Integer.parseInt(temp[0]);
        int M = Integer.parseInt(temp[1]);
        int K = Integer.parseInt(temp[2]);

        arr = new long[N];
        tree = new long[N*4];
        lazy = new long[N*4];

        for(int i=0; i<N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        /* 세그트리 초기화 */
        init(1,0,N-1);

        for(int i=0; i<M+K; i++) {
            temp = br.readLine().split(" ");

            int A = Integer.parseInt(temp[0]);
            int B = Integer.parseInt(temp[1])-1;
            int C = Integer.parseInt(temp[2])-1;

            if(A==1) {
                long D = Long.parseLong(temp[3]);
                update(1,0,N-1,B,C,D);
            }else {
                bw.write(query(1,0,N-1,B,C)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}