/* https://www.acmicpc.net/problem/32322 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, T;
    public static int[] tree;

    /* l ~ r 구간에서의 합을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return r-l+1-query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간에서의 합을 리턴하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return query(n*2, s, (s+e)/2,l,r) + query(n*2+1,(s+e)/2+1,e,l,r);
        }
    }

    /* idx위치의 값을 val로 변경하는 메소드 */
    public static void update(int idx, int val){
        update(1, 0, N-1, idx, val);
    }

    /* idx위치의 값을 val로 변경하는 메소드 */
    public static void update(int n, int s, int e, int idx, int val){
        if(idx<s||idx>e){
            return;
        }else if(s==e){
            tree[n] = val;
        }else{
            update(n*2, s,(s+e)/2, idx, val);
            update(n*2+1,(s+e)/2+1,e,idx,val);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        T = Integer.parseInt(input[1]);

        tree = new int[4*N];

        for(int i=0;i<T;i++){
            input = br.readLine().split(" ");

            if(input[0].equals("A")){
                int l = Integer.parseInt(input[1])-1;
                int r = Integer.parseInt(input[2])-1;

                bw.write(query(l,r)+"\n");
            }else if(input[0].equals("R")){
                int idx = Integer.parseInt(input[1])-1;

                update(idx,1);
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}