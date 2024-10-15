/* https://www.acmicpc.net/problem/1572 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, K, M;
    public static int[] tree;
    public static int[] arr;

    /* 특정 인덱스의 값을 v 증가시키는 업데이트 메소드 */
    public static void update(int i, long v){
        update(1, 0, M-1, i, v);
    }
    
    /* 특정 인덱스의 값을 v 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, long v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] += v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* v번째 수를 리턴하는 메소드 */
    public static long query(long v){
        return query(1, 0, M-1, v);
    }
    
    /* v번째 수를 리턴하는 메소드 */
    public static long query(int n, int s, int e, long v){
        /* 리프노드의 인덱스가 찾고자하는 값임 */
        if(s==e){
            return s;
        }

        /* 왼쪽 자식 노드보다 v의 값이 크거나 같다면, 찾는 값은 왼쪽 노드에 존재함 */
        if(tree[n*2] >= v){
            return query(n*2, s, (s+e)/2, v);
        /* 왼쪽 자식 노드보다 v의 값이 작다면, 찾는 값은 오른쪽 노드에 존재함 */
        }else{
            return query(n*2+1, (s+e)/2+1, e, v - tree[n*2]);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        /* 숫자는 최대 250000까지임 */
        M = 250001;

        arr = new int[M];
        tree = new int[4*M];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        /* 앞서 K-1개는 세그트리에서의 개수만 1씩 증가시킴 */
        for(int i=0; i<K-1; i++){
            update(arr[i], 1L);
        }

        long result = 0;

        for(int i=K-1; i<N; i++){
            /* 현재의 값의 개수를 1 증가시킴 */
            update(arr[i], 1L);
            /* 중앙값을 구함, 중앙값은 (K+1)/2번째 숫자임 */
            result += query((K+1)/2);
            /* 현재 구간의 맨 앞 값을 1 감소시킴 */
            update(arr[i-(K-1)], -1L);
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}