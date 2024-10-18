/* https://www.acmicpc.net/problem/5817 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;

    /* index[v] = 숫자 v가 배열상에 존재하는 위치 */
    public static int[] index;

    /* number[i] = 인덱스 i에 위치하는 숫자 */
    public static int[] number;

    /* tree[n][0] = 구간의 최소값, tree[n][1] = 구간의 최대값 */
    public static int[][] tree;

    /* 세그트리를 초기화하는 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            /* 세그트리에는 난쟁이의 키 v가 아니라, 난쟁이의 인덱스를 저장함 */
            tree[n][0] = index[s];
            tree[n][1] = index[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(
                    tree[n*2], tree[n*2+1]
            );
        }
    }

    /* 세그트리의 자식을 병합하는 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        return new int[]{
                Math.min(arr1[0],arr2[0]),
                Math.max(arr1[1],arr2[1])
        };
    }

    /* l, r 번째 값의 위치를 서로 변경하는 스왑 메소드 */
    public static void swap(int l, int r){
        /* l, r 위치에 존재하는 값 v1, v2 */
        int v1 = number[l];
        int v2 = number[r];

        /* v1, v2가 배열상에 존재하는 위치 idx1, idx2 */
        int idx1 = index[v1];
        int idx2 = index[v2];

        /* v1, v2에 위치한 값을 각각 idx2, idx1으로 변경함 */
        update(v1, idx2);
        update(v2, idx1);

        /* l, r 번째에 위치한 값을 서로 변경함 */
        int t = number[l];
        number[l] = number[r];
        number[r] = t;

        /* v1, v2 숫자의 위치를 서로 변경함 */
        t = index[v1];
        index[v1] = index[v2];
        index[v2] = t;
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, N-1, i, v);
    }

    /* i번째 값을 v로 변경하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n][0] = v;
            tree[n][1] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(
                    tree[n*2], tree[n*2+1]
            );
        }
    }

    /* 주어진 구간 l, r에 대하여 l ~ r 사이에 있는 값이 모두 연속해있는지 판단하는 쿼리 메소드 */
    public static String query(int l, int r){
        int[] result = query(1, 0, N-1, l, r);

        /* l ~ r 구간의 크기와, 쿼리의 결과로 얻어진 인덱스의 최소, 최대값의 차이와 동일하다면 l, l+1, l+2, ... r 값은 모두 연속함 */
        if(r-l==result[1]-result[0]){
            return "YES";
        }

        return "NO";
    }

    /* 주어진 구간 l, r에 대하여 l ~ r 사이에 있는 값이 모두 연속해있는지 판단하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new int[]{
                    Integer.MAX_VALUE, Integer.MIN_VALUE
            };
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
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        index = new int[N];
        number = new int[N];
        tree = new int[4*N][2];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            int v = Integer.parseInt(input[i])-1;

            index[v] = i;
            number[i] = v;
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int q = Integer.parseInt(input[0]);
            int l = Integer.parseInt(input[1])-1;
            int r = Integer.parseInt(input[2])-1;

            if(q==1){
                swap(l, r);
            }else if(q==2){
                bw.write(query(l, r)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}