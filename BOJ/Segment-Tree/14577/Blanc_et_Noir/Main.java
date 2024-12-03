/* https://www.acmicpc.net/problem/14577 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    /* 오프라인으로 처리할 쿼리를 저장할 배열 */
    public static long[][] queries;

    /* i번째 지역의 현재 적설량을 저장할 배열 */
    public static long[] snow;

    /* 적설량에 대한 압축된 좌표를 내림차순으로 저장할 배열 */
    public static ArrayList<Long> idx = new ArrayList<Long>();

    public static int[] tree;
    public static int N, M;

    /* 초기 적설량, 오프라인 쿼리를 바탕으로 좌표압축을 수행하는 메소드 */
    public static void compress(long[] snow, long[][] queries){
        /* 중복방지를 위한 set 선언 */
        HashSet<Long> set = new HashSet<Long>();

        /* 누적 적설량을 임시로 기록할 배열 */
        long[] arr = new long[N];

        /* 초기 적설량 정보를 set에 모두 추가 */
        for (int i = 0; i < N; i++) {
            set.add(arr[i] = snow[i]);
        }

        for (int i = 0; i < M; i++) {
            /* 적설량 증가 쿼리라면 적설량을 쿼리만큼 증가시키고 set에 추가 */
            if(queries[i][0] == 1) {
                set.add(arr[(int) queries[i][1]] += queries[i][2]);
                /* 적설량 증가 쿼리라면 적설량을 쿼리만큼 감소시키고 set에 추가 */
            }else if(queries[i][0] == 2){
                set.add(arr[(int) queries[i][1]] -= queries[i][2]);
                /* L이상 R이하의 적설량에 대한 좌표압축도 수행하기 위해 set에 추가 */
            }else if(queries[i][0] == 3){
                set.add(queries[i][1]);
                set.add(queries[i][2]);
            }
        }

        /* set에 담긴 값을 모두 ArrayList에 추가 */
        for(long v : set){
            idx.add(v);
        }

        /* 좌표압축 결과를 내림차순 정렬 */
        Collections.sort(idx, Collections.reverseOrder());
    }

    /* 적설량 V에 대해 압축된 좌표를 이분탐색으로 리턴하는 메소드 */
    public static int get(long v){
        int s = 0;
        int e = idx.size()-1;

        while(s<=e){
            int m = (s+e)/2;

            if(idx.get(m) < v){
                e = m - 1;
            }else if(idx.get(m) > v){
                s = m + 1;
            }else{
                return m;
            }
        }

        return -1;
    }

    /* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
    public static void update(int i, int v){
        update(1, 0, idx.size()-1, i, v);
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

            tree[n] = tree[n*2] + tree[n*2+1];
        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query1(int l, int r){
        return query1(1, 0, idx.size()-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query1(int n, int s, int e, int l, int r){
        if(s>r||e<l){
            return 0;
        }else if(l<=s&&e<=r){
            return tree[n];
        }else{
            return query1(n*2, s, (s+e)/2, l, r) + query1(n*2+1, (s+e)/2+1, e, l, r);
        }
    }

    /* 내림차순으로 v번째 값을 리턴하는 쿼리 메소드 */
    public static int query2(int v){
        return query2(1, 0, idx.size()-1, v);
    }

    /* 내림차순으로 v번째 값을 리턴하는 쿼리 메소드 */
    public static int query2(int n, int s, int e, int v){
        if(s==e){
            return s;
        }else if(tree[n*2] >= v){
            return query2(n*2, s, (s+e)/2, v);
        }else{
            return query2(n*2+1, (s+e)/2+1, e, v-tree[n*2]);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        snow = new long[N];

        input = br.readLine().split(" ");

        /* 초기 적설량 정보를 입력받음 */
        for(int i=0; i<N; i++){
            snow[i] = Long.parseLong(input[i]);
        }

        queries = new long[M][3];

        /* 쿼리를 입력받되, 처리를 나중으로 미룸 */
        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            long Q = Long.parseLong(input[0]);

            queries[i][0] = Q;

            if(Q == 1){
                long I = Long.parseLong(input[1])-1;
                long V = Long.parseLong(input[2]);

                queries[i][1] = I;
                queries[i][2] = V;
            }else if(Q == 2){
                long I = Long.parseLong(input[1])-1;
                long V = Long.parseLong(input[2]);

                queries[i][1] = I;
                queries[i][2] = V;
            }else if(Q == 3){
                long L = Long.parseLong(input[1]);
                long R = Long.parseLong(input[2]);

                queries[i][1] = L;
                queries[i][2] = R;
            }else if(Q == 4){
                long T = Long.parseLong(input[1]);

                queries[i][1] = T;
            }
        }

        /* 초기 적설량 정보 및 쿼리를 바탕으로 적설량에 대한 좌표압축 수행 */
        compress(snow, queries);

        tree = new int[idx.size()*4];

        /* 초기 적설량 정보에 따라, 각 적설량에 해당하는 압축된 좌표에 1을 증가시킴 */
        for(int i=0; i<N; i++){
            update(get(snow[i]), 1);
        }

        /* 오프라인 쿼리를 처리함 */
        for(int i=0; i<M; i++){
            long Q = queries[i][0];

            /* 적설량 증가 쿼리라면 */
            if(Q == 1){
                int I = (int) queries[i][1];
                long V = queries[i][2];

                /* 기존에 I위치에 쌓인 적설량의 압축된 좌표에 대한 값을 1 감소시킴 */
                update(get(snow[I]), -1);
                /* I위치의 현재 적설량을 증가시킴 */
                snow[I] += V;
                /* 새로운 I위치에 쌓인 적설량의 압축된 좌표에 대한 값을 1 증가시킴 */
                update(get(snow[I]), 1);
            }else if(Q == 2){
                int I = (int) queries[i][1];
                long V = queries[i][2];

                /* 기존에 I위치에 쌓인 적설량의 압축된 좌표에 대한 값을 1 감소시킴 */
                update(get(snow[I]), -1);
                /* I위치의 현재 적설량을 감소시킴 */
                snow[I] -= V;
                /* 새로운 I위치에 쌓인 적설량의 압축된 좌표에 대한 값을 1 증가시킴 */
                update(get(snow[I]), 1);
            }else if(Q==3){
                /* 쿼리의 시작, 종료 적설량에 대한 압축된 좌표를 얻음, 오름차순 정렬 되어있으므로, 두 값이 바뀐 상태로 들어가야함에 유의 */
                int L = get(queries[i][2]);
                int R = get(queries[i][1]);

                bw.write(query1(L, R)+"\n");
            }else if(Q==4){
                int T = (int) queries[i][1];

                /* T번째 적설량에 대한 압축된 좌표를 얻고, 그것으로 실제 적설량을 구함 */
                bw.write(idx.get(query2(T))+"\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}