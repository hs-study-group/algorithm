/* https://www.acmicpc.net/problem/19663 */

import java.io.*;
import java.util.*;

class Node{
    /* 산의 위치, 산의 높이 */
    int idx;
    long height;

    Node(int idx, long height){
        this.idx = idx;
        this.height = height;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N;
    public static long[] tree;
    
    /* 산의 위치, 산의 높이를 저장할 배열 */
    public static Node[] moutains;

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1,0,N-1,l,r);
    }

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
        if(l>e||r<s){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            int m = (s+e)/2;
            return query(n*2,s,m,l,r) + query(n*2+1,m+1,e,l,r);
        }
    }

    /* idx 위치의 값을 val로 변경하는 업데이트 메소드 */
    public static void update(int idx, int val){
        update(1,0,N-1,idx,val);
    }

    /* idx 위치의 값을 val로 변경하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int idx, long val){
        if(!(s<=idx&&idx<=e)){
            return;
        }else if(s==e){
            tree[n] = val;
        }else{
            update(n*2,s,(s+e)/2,idx,val);
            update(n*2+1,(s+e)/2+1,e,idx,val);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        input = br.readLine().split(" ");

        moutains = new Node[N];
        tree = new long[4*N];

        for(int i=0;i<N;i++){
            moutains[i] = new Node(i,Long.parseLong(input[i]));
        }
        
        /* 산의 높이를 기준으로 오름차순 정렬 */
        Arrays.sort(moutains, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if(n1.height<n2.height){
                    return -1;
                }else if(n1.height>n2.height){
                    return 1;
                }else{
                    return 0;
                }
            }
        });

        long max = Long.MIN_VALUE;
        long result = 0L;

        /* max보다 높이가 낮은 산들을 임시로 저장할 큐 */
        Queue<Node> q = new LinkedList<Node>();

        for(int i=0;i<N;i++){
            /* 높이가 낮은 산을 탐색 */
            Node n = moutains[i];

            /* 그 산이 기존 산의 최대 높이보다 크다면, 그보다 작은 산들을 세그트리에 추가함 */
            if(n.height>max){
                
                while(!q.isEmpty()){
                    update(q.poll().idx,1);
                }

                /* 산의 최대 높이 갱신 */
                max = n.height;
            }

            /* 현재 탐색중인 산을 큐에 추가함 */
            q.add(new Node(n.idx, n.height));
            
            /* 해당 산을 기준으로, 왼쪽구간과 오른쪽 구간의 산의 개수를 곱한 결과를 누적하여 더함 */
            result += query(0,n.idx-1)*query(n.idx+1,N-1);
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}