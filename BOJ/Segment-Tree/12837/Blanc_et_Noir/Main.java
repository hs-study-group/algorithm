/* https://www.acmicpc.net/problem/12837 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, Q;
    public static long[] tree;

    /* i위치의 값을 v만큼 변화시키는 업데이트 메소드 */
    public static void update(int i, long v){
        update(1, 0, N-1, i, v);
    }

    /* i위치의 값을 v만큼 변화시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, long v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] = tree[n] + v;
            return;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = tree[n*2] + tree[n*2+1];
            return;
        }
    }

    /* l ~ r 구간의 변화량을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 변화량을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return query(n*2,s,(s+e)/2,l,r) + query(n*2+1,(s+e)/2+1, e, l, r);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        Q = Integer.parseInt(input[1]);

        tree = new long[4*N];

        for(int i=0; i<Q; i++){
            input = br.readLine().split(" ");

            if(input[0].equals("1")){
                int idx = Integer.parseInt(input[1])-1;
                long val = Long.parseLong(input[2]);

                update(idx, val);
            }else if(input[0].equals("2")){
                int l = Integer.parseInt(input[1])-1;
                int r = Integer.parseInt(input[2])-1;
                bw.write(query(l,r)+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}