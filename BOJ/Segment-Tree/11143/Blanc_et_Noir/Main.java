/* https://www.acmicpc.net/problem/11143 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int B, P, Q, T;
    public static int[] tree;

    /* idx 위치의 값을 val만큼 변화시키는 업데이트 메소드 */
    public static void update(int idx, int val){
        update(1, 0, B-1, idx, val);
    }

    /* idx 위치의 값을 val만큼 변화시키는 업데이트 메소드 */
    public static void update(int n, int s, int e, int idx, int val){
        if(idx<s||idx>e){
            return;
        }else if(s==e){
            tree[n] = tree[n] + val;
        }else{
            update(n*2, s, (s+e)/2, idx, val);
            update(n*2+1, (s+e)/2+1, e, idx, val);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* l ~ r 구간의 구슬의 합을 구하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, B-1, l, r);
    }

    /* l ~ r 구간의 구슬의 합을 구하는 쿼리 메소드 */
    public static int query(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return query(n*2, s, (s+e)/2, l, r) + query(n*2+1, (s+e)/2+1, e, l, r);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input;

        T = Integer.parseInt(br.readLine());

        for(int i=0;i<T;i++){
            input = br.readLine().split(" ");

            B = Integer.parseInt(input[0]);
            P = Integer.parseInt(input[1]);
            Q = Integer.parseInt(input[2]);

            tree = new int[4*B];

            for(int j=0;j<P+Q;j++){
                input = br.readLine().split(" ");

                if(input[0].equals("P")){
                    int idx = Integer.parseInt(input[1])-1;
                    int val = Integer.parseInt(input[2]);

                    update(idx, val);
                }else if(input[0].equals("Q")){
                    int l = Integer.parseInt(input[1])-1;
                    int r = Integer.parseInt(input[2])-1;

                    bw.write(query(l, r)+"\n");
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}