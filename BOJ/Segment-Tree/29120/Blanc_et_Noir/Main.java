//https://www.acmicpc.net/problem/29120

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, Q;
    public static int[] arr;
    public static int[][] tree;
    public static final int MOD = 1000000007;
    public static ArrayList<Integer> primes = new ArrayList<Integer>();

    /* 세그트리 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리 초기화 메소드 */
    public static void init(int n, int s, int e){
        /* 리프노드에서는 소인수분해 결과를 세그트리에 저장 */
        if(s==e){
            tree[n] = divide(arr[s]);

            return;

            /* 그외 노드에서는 자식 노드의 결과를 병합 */
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = merge(
                    tree[n*2],
                    tree[n*2+1]
            );

            return;
        }
    }

    /* 두 세그트리의 노드를 병합하는 메소드 */
    public static int[] merge(int[] arr1, int[] arr2){
        int[] result = new int[arr1.length];

        for(int i=0;i<arr1.length; i++){
            //result[i] = ((arr1[i]%MOD) + (arr2[i]%MOD))%MOD;
            result[i] = arr1[i] + arr2[i];
        }

        return result;
    }

    /* 어떤 수를 소인수분해한 결과를 저장하는 메소드 */
    public static int[] divide(int v){
        int[] result = new int[primes.size()];

        for(int i=0; i<primes.size(); i++){
            int prime = primes.get(i);

            while(v%prime==0){
                v = v/prime;
                result[i]++;
            }
        }

        return result;
    }

    /* l ~ r 구간에서의 소인수 분해 결과를 리턴하는 메소드 */
    public static int[] query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간에서의 소인수 분해 결과를 리턴하는 메소드 */
    public static int[] query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return new int[primes.size()];
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return merge(
                    query(n*2, s, (s+e)/2, l, r),
                    query(n*2+1, (s+e)/2+1, e, l, r)
            );
        }
    }

    /* idx번째 수를 val로 업데이트 하는 메소드 */
    public static void update(int idx, int val){
        update(1, 0, N-1, idx, val);
    }

    /* idx번째 수를 val로 업데이트 하는 메소드 */
    public static void update(int n, int s, int e, int idx, int val){
        if(idx<s||idx>e){
            return;
        }else if(s==e){
            arr[idx] = val;
            tree[n] = divide(val);

            return;
        }else{
            update(n*2, s, (s+e)/2, idx, val);
            update(n*2+1, (s+e)/2+1, e, idx, val);

            tree[n] = merge(
                    tree[n*2],
                    tree[n*2+1]
            );

            return;
        }
    }

    /* 소인수분해 결과를 종합하여 약수의 개수를 계산하는 메소드 */
    public static long calculate(int[] arr){
        long result = 1;

        for(int i=0; i<arr.length; i++){
            /* arr[i]라는 소수로 분해한 적이 있다면 그때의 지수+1를 누적하여 곱함 */
            if(arr[i]>0){
                result = ((result%MOD)*((arr[i]+1)%MOD))%MOD;
            }
        }

        return result%MOD;
    }

    /* 소수인지 아닌지 판별하는 메소드 */
    public static boolean isPrime(int v) {
        if (v == 1) {
            return true;
        }

        for (int i = 2; i * i <= v; i++) {
            if (v % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        /* 2 ~ 10000 범위의 소수를 찾는 메소드 */
        for(int i=2; i<=10000; i++){
            if(isPrime(i)){
                primes.add(i);
            }
        }

        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        tree = new int[4*N][primes.size()];

        input = br.readLine().split(" ");
        Q = Integer.parseInt(br.readLine());

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(input[i]);
        }

        init();

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            int A = Integer.parseInt(input[0]);
            int B = Integer.parseInt(input[1]);
            int C = Integer.parseInt(input[2]);

            if(A==0){
                update(B-1, C);
            }else if(A==1){
                bw.write(calculate(query(B-1,C-1))+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}