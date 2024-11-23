/*https://www.acmicpc.net/problem/12741 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static int[] arr;
    public static int[][] tree;
    public static String S = "CS204", F="HSS090";

    /* 두 노드를 병합하는 병합 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        /* 왼쪽 노드가 유효하지 않으면 오른쪽 노드를 리턴 */
        if(arr1[2]==0){
            return arr2;
        /* 오른쪽 노드가 유효하지 않으면 왼쪽 노드를 리턴 */
        }else if(arr2[2]==0){
            return arr1;
        }else{
            /* 왼쪽 노드의 맨 오른쪽 값이 오른쪽 노드의 맨 왼쪽 값보다 작거나 같으면 */
            if(arr1[1] <= arr2[0]){
                /* 왼쪽 노드의 맨 왼쪽 값, 오른쪽 노드의 맨 오른쪽 값, 왼쪽노드와 오른쪽 노드의 크기의 합 */
                return new int[]{arr1[0], arr2[1], arr1[2] + arr2[2]};
            /* 아니라면 무효화 처리 */
            }else{
                return new int[]{0, 0, 0};
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
            /* 구간의 맨 왼쪽 값, 구간의 맨 오른쪽 값, 구간의 크기 */
            tree[n][0] = arr[s];
            tree[n][1] = arr[s];
            tree[n][2] = 1;
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l 번째 값과 r 번째 값을 스왑하는 스왑 메소드 */
    public static void swap(int l, int r){
        int t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;

        update(l, arr[l]);
        update(r, arr[r]);
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
            tree[n][2] = 1;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = merge(tree[n*2], tree[n*2+1]);
        }
    }

    /* l ~ r 맨 왼쪽값, 맨 오른쪽 값, 구간의 크기를 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, N-1, l, r)[2];
    }

    /* l ~ r 맨 왼쪽값, 맨 오른쪽 값, 구간의 크기를 리턴하는 쿼리 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
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
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        input = br.readLine().split(" ");

        arr = new int[N];
        tree = new int[4*N][3];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        /* 세그트리 초기화 */
        init();

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int Q = Integer.parseInt(input[0]);
            int L = Integer.parseInt(input[1])-1;
            int R = Integer.parseInt(input[2])-1;

            if(Q==1){
                /* 리턴받은 구간이 L ~ R 구간이라면 성공 */
                if(query(L, R)==(R-L+1)){
                    bw.write(S+"\n");
                }else{
                    bw.write(F+"\n");
                }
            }else if(Q==2){
                swap(L, R);
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}