//https://www.acmicpc.net/problem/12844

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int[] arr;
    static int[] tree;
    static int[] lazy;

    /* 세그트리 초기화 메소드 */
    public static void init(int node, int start, int end) {
        if(start==end) {
            tree[node] = arr[start];

            return;
        }else {
            int mid = (start+end)/2;

            init(node*2,start,mid);
            init(node*2+1,mid+1,end);

            tree[node] = tree[node*2]^tree[node*2+1];

            return;
        }
    }

    /* 특정한 구간의 XOR 값을 리턴하는 메소드 */
    public static int query(int node, int start, int end, int left, int right) {
        /* 레이지 값 처리 */
        process(node,start,end);

        if(start>right||end<left) {
            return 0;
        }else if(left<=start&&end<=right) {
            return tree[node];
        }else {
            return query(node*2,start,(start+end)/2,left,right)^query(node*2+1,(start+end)/2+1,end,left,right);
        }
    }

    public static void update(int node, int start, int end, int left, int right, int val) {
        /* 레이지 값 처리 */
        process(node,start,end);

        if(start>right||end<left) {
            return;
        }else if(left<=start&&end<=right) {
            /* 짝수번의 XOR은 그대로, 홀수번의 XOR은 반전 */
            tree[node] ^= val*((end-start+1)%2);

            /* 리프노드가 아니라면 레이지 전파 */
            if(start!=end) {
                lazy[node*2] ^= val;
                lazy[node*2+1] ^= val;
            }

            return;
        }else {
            update(node*2,start,(start+end)/2,left,right,val);
            update(node*2+1,(start+end)/2+1,end,left,right,val);

            tree[node] = tree[node*2]^tree[node*2+1];

            return;
        }
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int node, int start, int end) {
        /* 레이지 값이 남아있다면 */
        if(lazy[node]!=0) {
            /* 짝수번의 XOR은 그대로, 홀수번의 XOR은 반전 */
            tree[node] ^= lazy[node]*((end-start+1)%2);

            /* 리프노드가 아니라면 레이지 전파 */
            if(start!=end) {
                lazy[node*2] ^= lazy[node];
                lazy[node*2+1] ^= lazy[node];
            }

            /* 레이지 값 초기화 */
            lazy[node]=0;
        }
    }

    public static void main(String[] args) throws Exception {
        int N = Integer.parseInt(br.readLine());

        final int TREE_SIZE = 1<<((int) Math.ceil(Math.log(N)/Math.log(2))+1);
        arr = new int[N];
        tree = new int[TREE_SIZE];
        lazy = new int[TREE_SIZE];

        String[] temp = br.readLine().split(" ");

        for(int i=0; i<temp.length; i++) {
            arr[i] = Integer.parseInt(temp[i]);
        }

        /* 세그트리 초기화 */
        init(1,0,N-1);

        int M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++) {
            temp = br.readLine().split(" ");

            int A = Integer.parseInt(temp[0]);
            int B = Integer.parseInt(temp[1]);
            int C = Integer.parseInt(temp[2]);

            if(A==1) {
                int D = Integer.parseInt(temp[3]);

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