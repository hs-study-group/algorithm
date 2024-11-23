/* https://www.acmicpc.net/problem/1273 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    
    /* DP[i] = i번째 층의 점수합 */
    public static long[] DP, POINT;
    public static int[] tree, index;
    public static int H, N, M;

    /* 두 노드를 병합하는 병합 메소드 */
    public static int merge(int v1, int v2){
        return v1 + v2;
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, H-1);
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

    /* i번째 층이 실제로는 몇 번째 층인지를 리턴하는 쿼리 메소드 */
    public static int query(int i){
        return query(1, 0, H-1, i);
    }

    /* i번째 층이 실제로는 몇 번째 층인지를 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int i){
        if(s==e){
            return s;
        }else{
            if(tree[n*2]>=i){
                return query(n*2, s, (s+e)/2, i);
            }else{
                return query(n*2+1, (s+e)/2+1, e, i - tree[n*2]);
            }
        }
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, H-1, i, v);
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
        H = 3000001;
        DP = new long[H];
        POINT = new long[]{1, 2, 5};
        index = new int[H];

        /* 값 입력 및 누적합 배열 세팅 */
        for(int i=0; i<3; i++){
            input = br.readLine().split(" ");

            for(int j=0; j<N; j++){
                int v = Integer.parseInt(input[j]);

                DP[index[j]] += POINT[i];
                DP[index[j] + v] -= POINT[i];
                index[j] += v;
            }
        }

        /* 누적합 계산 */
        for(int i=1; i<H; i++){
            DP[i] += DP[i-1];
        }

        M = Integer.parseInt(br.readLine());
        tree = new int[4*H];

        /* 세그트리 초기화 */
        init();

        input = br.readLine().split(" ");

        for(int i=0; i<M; i++){
            int I = Integer.parseInt(input[i]);

            /* I번째 층이 초기 상태에서의 몇 번째 층인지 계산 */
            int idx = query(I);

            /* 그 층의 합을 출력함 */
            bw.write(DP[idx]+"\n");

            /* 층을 제거함 */
            update(idx, -1);
        }

        bw.flush();
        bw.close();
        br.close();
    }
}