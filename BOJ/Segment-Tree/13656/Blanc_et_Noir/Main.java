/* https://www.acmicpc.net/problem/13656 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static final int MOD = 3;
    public static int N, M;
    public static int[][] tree;
    public static int[] lazy;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        int[] result = new int[MOD];

        /* 개미, 사람, 코끼리의 수를 각각 더함 */
        for(int i=0; i<MOD; i++){
            result[i] = arr1[i] + arr2[i];
        }

        return result;
    }

    /* 레이지 값을 처리하는 프로세스 메소드 */
    public static void process(int n, int s, int e){
        /* 처리해야할 레이지 값이 없다면 종료 */
        if(lazy[n]==0){
            return;
        }

        /* 리프노드가 아니라면 레이지 값 전파 */
        if(s!=e){
            lazy[n*2] = (lazy[n*2] + lazy[n])%MOD;
            lazy[n*2+1] = (lazy[n*2+1] + lazy[n])%MOD;
        }

        /* 레이지 값 처리, 레이지 값 만큼을 시프팅 */
        tree[n] = shift(tree[n], lazy[n]);

        /* 레이지 값 초기화 */
        lazy[n] = 0;
    }

    /* 배열을 시프팅 하는 시프트 메소드 */
    public static int[] shift(int[] arr, int v){
        int[] result = new int[MOD];

        /* v만큼을 시프팅 */
        for(int i=0; i<MOD; i++){
            result[(i+v)%MOD] = arr[i];
        }

        return result;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 개미, 사람, 코끼리 중 하나로 초기값 설정 */
            tree[n][0] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l, r 구간에 대해 v값을 적용하는 업데이트 메소드 */
    public static void update(int l, int r, int v){
        update(1, 0, N-1, l, r, v);
    }

    /* l, r 구간에 대해 v값을 적용하는 업데이트 메소드 */
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

    /* l, r 구간에 대해 개미, 사람, 코끼리의 수를 리턴하는 쿼리 메소드 */
    public static String query(int l, int r){
        int[] result = query(1, 0, N-1, l, r);

        return result[0]+" "+result[1]+" "+result[2];
    }

    /* l, r 구간에 대해 개미, 사람, 코끼리의 수를 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        /* 레이지 값 처리 */
        process(n, s, e);

        if(s>r||e<l){
            return new int[]{0, 0, 0};
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
        String temp;
        String[] input;

        /* 테스트 케이스별 문단 띄어쓰기를 하기 위한 플래그 */
        boolean flag = false;

        while((temp=br.readLine())!=null){
            /* 2번째 케이스부터 문단 띄어쓰기 적용 */
            if(flag){
                bw.write("\n");
            }

            input = temp.split(" ");

            N = Integer.parseInt(input[0]);
            M = Integer.parseInt(input[1]);

            tree = new int[4*N][MOD];
            lazy = new int[4*N];

            /* 세그트리 초기화 */
            init();

            for(int i=0; i<M; i++){
                input = br.readLine().split(" ");

                char Q = input[0].charAt(0);
                int L = Integer.parseInt(input[1])-1;
                int R = Integer.parseInt(input[2])-1;

                if(Q=='C'){
                    bw.write(query(L, R)+"\n");
                }else if(Q=='M'){
                    update(L, R, 1);
                }
            }

            /* 첫 번째 케이스 이후부터 true로 설정 */
            flag = true;
        }

        bw.flush();
        bw.close();
        br.close();
    }
}