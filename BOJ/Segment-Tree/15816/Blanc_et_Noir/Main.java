/* https://www.acmicpc.net/problem/15816 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    public static int[][] queries;

    /* 초기 퀘스트 달성상황, 구간합 세그트리 */
    public static int[] initialQuests, tree;

    public static int[] idx;

    /* 값 v에 매핑된 인덱스를 리턴하는 메소드 */
    public static int get(int v){
        int s = 0;
        int e = idx.length-1;

        while(s<=e){
            int m = (s+e)/2;

            if(v==idx[m]){
                return m;
            }else if(v<idx[m]){
                e = m - 1;
            }else{
                s = m + 1;
            }
        }

        return -1;
    }

    /* 초기 배열과 쿼리를 입력받아 좌표압축을 수행하는 메소드 */
    public static void compress(int[] initialQuests, int[][] queries){
        /* 중복되지 않게 값을 저장할 임시 set */
        HashSet<Integer> set = new HashSet<Integer>();

        /* 초기 퀘스트 달성 상활을 set에 추가 */
        for(int i=0; i<N; i++){
            set.add(initialQuests[i]);
        }

        /* 쿼리에 담긴 모든 값을 set에 추가 */
        for(int i=0; i<M; i++){
            if(queries[i][0] == 1){
                set.add(queries[i][1]);
            }else if(queries[i][0] == 2){
                set.add(queries[i][1]);
                set.add(queries[i][2]);
            }
        }

        idx = new int[set.size()];

        int k = 0;

        for(int v : set){
            idx[k++] = v;
        }

        Arrays.sort(idx);
    }

    /* i번째 값을 v로 업데이트하는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, idx.length-1, i, v);
    }

    /* i번째 값을 v로 업데이트하는 업데이트 메소드 */
    public static void update(int n, int s, int e, int i, int v){
        if(i<s||i>e){
            return;
        }else if(s==e){
            tree[n] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* l ~ r 구간의 구간합을 구하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, idx.length-1, l, r);
    }

    /* l ~ r 구간의 구간합을 구하는 쿼리 메소드 */
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

        /* N개의 초기 퀘스트 달성 정보를 입력받음 */
        N = Integer.parseInt(br.readLine());
        initialQuests = new int[N];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            initialQuests[i] = Integer.parseInt(input[i]);
        }

        /* M개의 쿼리를 입력 받음 */
        M = Integer.parseInt(br.readLine());

        /*
            queries[i][0] = 쿼리타입
            queries[i][1] = X 또는 L
            queries[i][2] = R
        */
        queries = new int[M][3];

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            queries[i][0] = Integer.parseInt(input[0]);
            queries[i][1] = Integer.parseInt(input[1]);

            if(queries[i][0]==2){
                queries[i][2] = Integer.parseInt(input[2]);
            }
        }

        /* 초기에 완료한 퀘스트 정보, 쿼리 정보를 바탕으로 좌표압축 */
        compress(initialQuests, queries);

        /* 세그트리 초기화 */
        tree = new int[idx.length*4];

        /* 초기에 완료한 퀘스트 정보를 업데이트 */
        for(int i=0; i<N; i++){
            update(get(initialQuests[i]), 1);
        }

        /* 추후에 완료한 퀘스트 정보를 업데이트 OR 달성하지 못한 퀘스트 개수 출력 */
        for(int i=0; i<M; i++){
            int Q = queries[i][0];

            if(Q == 1){
                update(get(queries[i][1]), 1);
            }else if(Q == 2){
                int L = get(queries[i][1]);
                int R = get(queries[i][2]);

                bw.write(((queries[i][2] - queries[i][1] + 1) - query(L, R))+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}