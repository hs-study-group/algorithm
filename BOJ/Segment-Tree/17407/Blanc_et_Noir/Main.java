/* https://www.acmicpc.net/problem/17407 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static char[] arr;
    /* S = 여는 괄호를 1, 닫는 괄호를 -1로 가정했을때 전체 문자열의 합을 저장 */
    public static int N, M, K, S;
    
    /* DP[N] = 0번째 부터 N-1번째 괄호의 누적합을 저장하는 배열 */
    public static int[] tree, lazy, DP;

    /* 여는 괄호는 1, 닫는 괄호는 -1로 계산 */
    public static int parse(char c) {
        return c == '(' ? 1 : -1;
    }

    /* 여는 괄호는 닫는 괄호로, 닫는 괄호는 여는 괄호로 변환 */
    public static char convert(char c) {
        return c == '(' ? ')' : '(';
    }

    /* 레이지 값을 처리하느 프로세스 메소드 */
    public static void process(int n, int s, int e) {
        /* 레이지 값이 없다면 종료 */
        if (lazy[n] == 0) {
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if (s != e) {
            lazy[n * 2] += lazy[n];
            lazy[n * 2 + 1] += lazy[n];
        }

        /* 레이지값을 더함 */
        tree[n] += lazy[n];

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2) {
        return Math.min(v1, v2);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init() {
        init(1, 0, N - 1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e) {
        if (s == e) {
            tree[n] = DP[s];
        } else {
            init(n * 2, s, (s + e) / 2);
            init(n * 2 + 1, (s + e) / 2 + 1, e);

            tree[n] = merge(tree[n * 2], tree[n * 2 + 1]);
        }
    }

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int l, int r, int v) {
        update(1, 0, N - 1, l, r, v);
    }

    /* l ~ r 구간의 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v) {
        /* 레이지 값 처리 */
        process(n, s, e);

        if (s > r || e < l) {
            return;
        } else if (l <= s && e <= r) {
            /* 레이지 값 전파 */
            lazy[n] = v;

            /* 레이지 값 처리 */
            process(n, s, e);
        } else {
            update(n * 2, s, (s + e) / 2, l, r, v);
            update(n * 2 + 1, (s + e) / 2 + 1, e, l, r, v);

            tree[n] = merge(tree[n * 2], tree[n * 2 + 1]);
        }
    }

    public static void main(String[] args) throws IOException {
        arr = br.readLine().toCharArray();

        N = arr.length;

        DP = new int[N];
        
        /* 0번 괄호 ~ 0번 괄호의 누적합은 1 또는 -1 */
        DP[0] = parse(arr[0]);
        
        /* 여는 괄호, 닫는 괄호의 총 합 갱신 */
        S += parse(arr[0]);

        for (int i = 1; i < N; i++) {
            DP[i] = DP[i - 1] + parse(arr[i]);
            S += parse(arr[i]);
        }

        tree = new int[4 * N];
        lazy = new int[4 * N];

        /* 세그트리 초기화 */
        init();

        M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            int idx = Integer.parseInt(br.readLine()) - 1;
            
            /* idx 괄호를 반전시킴 */
            arr[idx] = convert(arr[idx]);

            /* 0 ~ idx, 0 ~ idx + 1, ... , 0 ~ N-1 까지의 누적합도 변화시킴 */
            /* 괄호를 반전시키면 누적합은 2 또는 -2 만큼 변화함 */
            update(idx, N-1, parse(arr[idx]) * 2);

            /* 전체 괄호 문자열의 합을 변경시킴 */
            S += (parse(arr[idx]) * 2);

            /* 만약 모든 누적합 결과들의 최솟값이 0이상이고, 전체 문자열 합이 0이라면 올바른 괄호 문자열임 */
            if (tree[1] >= 0 && S == 0) {
                K++;
            }
        }

        bw.write(K + "\n");
        bw.flush();
        bw.close();
        br.close();
    }
}