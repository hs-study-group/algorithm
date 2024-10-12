/* https://www.acmicpc.net/problem/6213 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, Q;
    public static int[] arr;
    
    /* 특정 연속 구간에서의 최소, 최대값을 기록할 세그트리 */
    public static int[][] tree;

    /* 세그트리를 초기화하는 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n][0] = arr[s];
            tree[n][1] = arr[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n][0] = Math.min(tree[n*2][0],tree[n*2+1][0]);
            tree[n][1] = Math.max(tree[n*2][1],tree[n*2+1][1]);
        }
    }

    /* 특정 구간에서의 최대, 최소값의 차이를 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        int[] result = query(1, 0, N-1, l, r);

        return result[1]-result[0];
    }

    /* 특정 구간에서의 최대, 최소값의 차이를 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE};
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            int[] result1 = query(n*2, s, (s+e)/2, l, r);
            int[] result2 = query(n*2+1, (s+e)/2+1, e, l, r);

            return merge(result1, result2);
        }
    }

    /* 두 자식 노드를 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        return new int[]{
                Math.min(arr1[0],arr2[0]),
                Math.max(arr1[1],arr2[1])
        };
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);

        arr = new int[N];
        tree = new int[4*N][2];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            int l = Integer.parseInt(input[0])-1;
            int r = Integer.parseInt(input[1])-1;

            bw.write(query(l,r)+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}