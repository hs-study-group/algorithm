/* https://www.acmicpc.net/problem/5676 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /* 세그트리를 초기화 하는 메소드 */
    public static void init(int[][] tree, int[] arr){
        init(tree, arr, 1, 0, arr.length -1);
    }

    /* 세그트리를 초기화 하는 메소드 */
    public static void init(int[][] tree, int[] arr, int n, int s, int e){
        if(s==e){
            if(arr[s]<0){
                tree[n][0] = 1;
            }else if(arr[s]==0){
                tree[n][1] = 1;
            }else{
                tree[n][2] = 1;
            }
        }else{
            int m = (s+e)/2;

            init(tree, arr, n*2, s, m);
            init(tree, arr, n*2+1, m+1, e);

            for(int i=0; i<tree[0].length; i++){
                tree[n][i] = tree[n*2][i] + tree[n*2+1][i];
            }
        }
    }

    /* idx번째 숫자를  v로 변경하는 메소드 */
    public static void change(int[][] tree, int[] arr, int idx, int v){
        change(tree, arr, 1, 0, arr.length-1, idx, v);
    }

    /* idx번째 숫자를  v로 변경하는 메소드 */
    public static void change(int[][] tree, int[] arr, int n, int s, int e, int idx, int v){
        if(idx < s || idx > e){
            return;
        }else if(s!=e){
            int m = (s+e)/2;

            change(tree, arr, n*2, s, m, idx, v);
            change(tree, arr, n*2+1, m+1, e, idx, v);

            for(int i=0; i<tree[0].length; i++) {
                tree[n][i] = tree[n * 2][i] + tree[n * 2 + 1][i];
            }
        }else {
            arr[idx] = v;

            if(arr[idx]<0){
                tree[n][0] = 1;
                tree[n][1] = 0;
                tree[n][2] = 0;
            }else if(arr[idx]==0){
                tree[n][0] = 0;
                tree[n][1] = 1;
                tree[n][2] = 0;
            }else{
                tree[n][0] = 0;
                tree[n][1] = 0;
                tree[n][2] = 1;
            }
        }
    }

    /* l ~ r 구간의 숫자들의 각 부호 개수를 구하는 쿼리 메소드 */
    public static int[] multiply(int[][] tree, int[] arr, int n, int s, int e, int l, int r){
        if(l>e||r<s){
            return new int[]{0,0,0};
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            int m = (s+e)/2;

            int[] result1 = multiply(tree, arr, n*2, s, m, l, r);
            int[] result2 = multiply(tree, arr, n*2+1, m+1, e, l, r);

            return new int[]{
                    result1[0]+result2[0],
                    result1[1]+result2[1],
                    result1[2]+result2[2]
            };
        }
    }

    /* l ~ r 구간의 숫자들의 각 부호 개수를 구하는 쿼리 메소드 */
    public static String multiply(int[][] tree, int[] arr, int l, int r){
        int[] result = multiply(tree, arr, 1, 0, arr.length-1, l, r);

        /* 0이 1개라도 있으면 곱의 최종 결과는 0 */
        if(result[1]>0){
            return "0";
        /* 음수가 홀수개 있으면 곱의 최종 결과는 음수 */
        }else if(result[0]%2!=0){
            return "-";
        /* 0도 없고, 음수가 짝수개 있다면 곱의 최종 결과는 양수 */
        }else{
            return "+";
        }
    }

    public static void main(String[] args) throws IOException {
        String temp = null;

        while((temp = br.readLine())!=null){
            String[] input = temp.split(" ");

            int N = Integer.parseInt(input[0]);
            int K = Integer.parseInt(input[1]);

            int[][] tree = new int[4*N][3];
            int[] arr = new int[N];

            input = br.readLine().split(" ");

            for(int i=0; i<N; i++){
                arr[i] = Integer.parseInt(input[i]);
            }

            /* 세그트리 초기화 */
            init(tree, arr);

            for(int i=0; i<K; i++){
                input = br.readLine().split(" ");

                int A = Integer.parseInt(input[1]);
                int B = Integer.parseInt(input[2]);

                if(input[0].equals("C")){
                    change(tree, arr, A - 1, B);
                }else if(input[0].equals("P")) {
                    bw.write(multiply(tree, arr, A - 1, B - 1));
                }
            }

            bw.write("\n");
            bw.flush();
        }

        bw.flush();
        br.close();
        bw.close();
    }
}