/* https://www.acmicpc.net/problem/31492 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N;
    public static int[] tree;
    public static int[][] arr;
    public static int[][] result;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* v번째 수를 리턴하는 쿼리 메소드 */
    public static int query(int v){
        return query(1, 0, N-1, v);
    }

    /* v번째 수를 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int v){
        if(s==e){
            return s;
        /* 왼쪽 노드에 v개 이상의 숫자가 있을 경우, 왼쪽 노드에 대해서 재귀 탐색 */
        }else if(tree[n*2]>=v){
            return query(n*2, s, (s+e)/2, v);
        /* 왼쪽 노드에 v개 미만의 숫자가 있을 경우, 찾고자 하는 값은 오른쪽 노드에 있으므로 오른쪽 노드 재귀 탐색 */
        }else {
            return query(n*2+1, (s+e)/2+1, e, v-tree[n*2]);
        }
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, N-1, i, v);
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] += v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());

        tree = new int[4*N];
        arr = new int[2][N/2];
        result = new int[2][N/2];

        for(int i=0; i<2; i++){
            input = br.readLine().split(" ");

            for(int j=0; j<N/2; j++){
                arr[i][j] = Integer.parseInt(input[j]);
            }
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<N/2; i++){
            for(int j=0; j<2; j++){
                /* arr[i][j] 번째 수를 찾아서 결과 배열에 저장 */
                result[j][i] = query(arr[j][i]);
                
                /* 이미 사용한 값은 삭제처리 */
                update(result[j][i], -1);
            }
        }

        for(int i=0; i<2; i++){
            for(int j=0; j<N/2; j++){
                bw.write((result[i][j]+1)+" ");
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}