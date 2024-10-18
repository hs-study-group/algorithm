/* https://www.acmicpc.net/problem/1321 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M, MAX = 500001;
    public static long[] arr, tree;

    /* 세그먼트 트리를 초기화하는 메소드 */
    public static void init(){
        init(1, 0, MAX-1);
    }

    /* 세그먼트 트리를 초기화하는 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = arr[s];
        }else{
            init(n*2,s,(s+e)/2);
            init(n*2+1,(s+e)/2+1, e);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* i값의 개수를 v만큼 증가시키는 업데이트 메소드 */
    public static void update(long i, long v){
        update(1, 0, MAX-1, i, v);
    }

    /* i값의 개수를 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, long i, long v){
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

    /* v번째 값을 리턴하는 쿼리 메소드 */
    public static long query(long v){
        return query(1, 0, MAX-1, v)+1;
    }

    /* v번째 값을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, long v){
        if(s==e){
            return s;
        /* 왼쪽 노드의 숫자의 개수가 v보다 크거나 같으면, v번째 값은 왼쪽 노드에 존재함 */
        }else if(tree[n*2]>=v){
            return query(n*2, s, (s+e)/2, v);
        /* 왼쪽 노드의 숫자의 개수가 v작으면, v번째 값은 오른쪽 노드에 존재함 */
        }else{
            return query(n*2+1, (s+e)/2+1, e, v-tree[n*2]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        N = Integer.parseInt(br.readLine());
        arr = new long[MAX];
        tree = new long[4*MAX];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        /* 세그먼트 트리 초기화 */
        init();

        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            long A = Long.parseLong(input[0]);
            long B = Long.parseLong(input[1]);

            if(A==1){
                long C = Long.parseLong(input[2]);

                update(B-1, C);
            }else if(A==2){
                bw.write(query(B)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}