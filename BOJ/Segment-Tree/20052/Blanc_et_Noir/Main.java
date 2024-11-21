/* https://www.acmicpc.net/problem/20052 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static char[] arr;

    /* DP[N] = 0 ~ N-1 번째 괄호까지의 누적합 */
    public static int[] tree, DP;
    public static int N, M, K;

    /* 여는 괄호를 1, 닫는 괄호를 -1로 계산 */
    public static int parse(char c){
        return c=='('?1:-1;
    }

    /* 괄호의 방향을 반전시키는 메소드 */
    public static char convert(char c){
        return c=='('?')':'(';
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return Math.min(v1, v2);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = DP[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간에서의 최소값을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간에서의 최소값을 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return Integer.MAX_VALUE;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        arr = br.readLine().toCharArray();

        N = arr.length;

        tree = new int[4*N];
        DP = new int[N];

        /* 0 ~ 0 번째 괄호까지의 누적합 계산 */
        DP[0] = parse(arr[0]);

        /* 0 ~ 1, 0 ~ 2, ... , 0 ~ N-1 번째 괄호까지의 누적합을 계산 */
        for(int i=1; i<N; i++){
            DP[i] = DP[i-1] + parse(arr[i]);
        }

        /* 세그트리 초기화 */
        init();

        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int L = Integer.parseInt(input[0])-1;
            int R = Integer.parseInt(input[1])-1;

            /* L ~ R 구간의 괄호 문자열의 최소값을 구함 */
            int min = query(L, R)-(L-1>=0?DP[L-1]:0);
            
            /* L ~ R 구간의 괄호 문자열 합을 구함 */
            int sum = DP[R]-(L-1>=0?DP[L-1]:0);

            /* 누적합의 최소값이 0이고, 누적합이 0인 경우 올바른 괄호 문자열임 */
            if(min>=0&&sum==0){
                K++;
            }
        }

        bw.write(K+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}