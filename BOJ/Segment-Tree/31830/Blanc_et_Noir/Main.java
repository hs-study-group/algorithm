/* https://www.acmicpc.net/problem/31830 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, Q, MOD = 26;
    public static char[] temp;
    public static int[][] tree;
    public static int[] arr, lazy;

    /* v값을 i만큼 시프팅하는 시프트 메소드 */
    public static int shift(int v, int i){
        return (v + i) % MOD;
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 처리해야할 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 */
        if(s!=e){
            /* 부모 노드의 레이지 값만큼 자식 노드도 시프팅하도록 레이지 값을 전파 */
            lazy[n*2] = shift(lazy[n*2], lazy[n]);
            lazy[n*2+1] = shift(lazy[n*2+1], lazy[n]);
        }

        /* 구간의 맨 왼쪽, 맨 오른쪽 값을 레이지 값만큼 시프팅 */
        tree[n][0] = shift(tree[n][0], lazy[n]);
        tree[n][1] = shift(tree[n][1], lazy[n]);

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 두 배열을 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        /* 둘 다 유효하지 않은 노드인 경우, 병합 결과도 유효하지 않음 */
        if(arr1[1]==-1&&arr2[0]==-1){
            return new int[]{-1, -1, 0};
        /* 왼쪽 노드가 유효하지 않으면 오른쪽 노드를 리턴함 */
        }else if(arr1[1]==-1){
            return arr2;
        /* 오른쪽 노드가 유효하지 않으면 왼쪽 노드를 리턴함 */
        }else if(arr2[0]==-1){
            return arr1;
        /* 둘 다 유효한 노드인 경우 */
        }else{
            /* 왼쪽 노드의 맨 오른쪽 값과, 오른쪽 노드의 맨 왼쪽 값이 동일하다면 */
            if(arr1[1]!=arr2[0]){
                /* 병합된 구간의 맨 왼쪽, 맨 오른쪽 값, 두 노드의 알파벳 묶음의 개수 합을 리턴함 */
                return new int[]{
                        arr1[0], arr2[1], arr1[2] + arr2[2]
                };
            }else{
                /* 병합된 구간의 맨 왼쪽, 맨 오른쪽 값, 두 노드의 알파벳 묶음의 개수 합 -1을 리턴함 */
                return new int[]{
                        arr1[0], arr2[1], arr1[2] + arr2[2] - 1
                };
            }
        }
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n][0] = arr[s];
            tree[n][1] = arr[s];
            tree[n][2] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 구간의 알파벳 묶음의 개수를 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, N-1, l, r)[2];
    }

    /* l ~ r 구간의 알파벳 묶음의 개수를 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        /* 처리해야할 레이지 값이 있다면 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return new int[]{-1, -1, 0};
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    /* l ~ r 구간의 값을 v만큼 시프팅하는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, N-1, l, r, v);
    }

    /* l ~ r 구간의 값을 v만큼 시프팅하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int l, int r, int v){
        /* 처리해야할 레이지 값이 있다면 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return;
        }else if(l<=s&&e<=r){
            /* 시프팅해야할 레이지 값을 전파 */
            lazy[n] = shift(lazy[n], v);

            /* 처리해야할 레이지 값이 있다면 처리 */
            process(n, s, e);
        }else{
            update(n*2, s, (s+e)/2, l, r, v);
            update(n*2+1, (s+e)/2+1, e, l, r, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);

        temp = br.readLine().toCharArray();
        arr = new int[N];
        tree = new int[4*N][3];
        lazy = new int[4*N];

        /* A : 0, B : 1, ..., Z : 25 */
        for(int i=0; i<N; i++){
            arr[i] = temp[i]-'A';
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            int O = Integer.parseInt(input[0]);
            int L = Integer.parseInt(input[1])-1;
            int R = Integer.parseInt(input[2])-1;

            if(O==1){
                bw.write(query(L, R)+"\n");
            }else if(O==2){
                update(L, R, 1);
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}