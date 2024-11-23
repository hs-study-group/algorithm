/* https://www.acmicpc.net/problem/8441 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[] tree;

    /* DP[i] = 0번째 ~ i번째 괄호까지의 괄호 문자열 총합 */
    public static int[] DP, lazy;
    public static int N, M, S;
    public static char[] arr;

    /* 여는 괄호는 1, 닫는 괄호는 -1로 계산하는 메소드 */
    public static int parse(char c){
        return c=='('?1:-1;
    }

    /* 괄호의 방향을 반전시키는 메소드 */
    public static char convert(char c){
        return c=='('?')':'(';
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 처리해야할 레이지 값이 없다면 종료 */
        if(lazy[n] == 0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] += lazy[n];
            lazy[n*2+1] += lazy[n];
        }

        /* 레이지 값 처리 */
        tree[n] += lazy[n];

        /* 레이지 값 초기화 */
        lazy[n] = 0;
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

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, N-1, l, r, v);
    }

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 레이지 값 전파 */
            lazy[n] = v;

            /* 레이지 값 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v);
            update(n*2+1, (s+e)/2+1, e, l, r, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 최소값을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 최소값을 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

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

        N = Integer.parseInt(br.readLine());

        tree = new int[4*N];
        lazy = new int[4*N];
        DP = new int[N];

        arr = br.readLine().replaceAll("\\s","").toCharArray();

        /* 누적합 배열 첫번째 인덱스 설정 */
        DP[0] = parse(arr[0]);
        
        /* 전체 문자열의 총합 설정 */
        S += parse(arr[0]);

        for(int i=1; i<N; i++){
            /* 누적합 계산 */
            DP[i] = DP[i-1] + parse(arr[i]);

            /* 전체 문자열의 총합 증감 */
            S += parse(arr[i]);
        }

        /* 세그트리 초기화 */
        init();

        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            int I = Integer.parseInt(br.readLine())-1;

            /* 괄호를 반전시키는 요청처리 */
            if(I!=-1){
                arr[I] = convert(arr[I]);

                /* DP[I] ~ DP[N-1]을 변화시킴, 여는괄호를 닫는 괄호로 반전시키면 -2, 그 반대면 2를 증가시킴 */
                update(I, N-1, parse(arr[I])*2);

                /* 전체 문자열의 총합 증감 */
                S += (parse(arr[I]) * 2);
            }else{
                /* 전체 문자열의 총합이 0이고, 괄호문자열을 구성하는 과정에서 단 한번도 음수가 나온적이 없다면 올바른 괄호 문자열 */
                if(S==0&&query(0, N-1)>=0){
                    bw.write("TAK\n");
                }else{
                    bw.write("NIE\n");
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}