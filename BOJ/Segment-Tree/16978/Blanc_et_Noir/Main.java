/* https://www.acmicpc.net/problem/16978 */
import java.io.*;
import java.util.*;

/* 1번 쿼리를 관리할 클래스 */
class Query1{
    /* 문제의 k, i, v 값 */
    int k, i, v;

    Query1(int k, int i, int v){
        this.k = k;
        this.i = i;
        this.v = v;
    }
}

/* 2번 쿼리를 관리할 클래스 */
class Query2{
    /* 쿼리의 원래 순서, 문제의 k, l, r 값 */
    int q, k, l, r;

    Query2(int q, int k, int l, int r){
        this.q = q;
        this.k = k;
        this.l = l;
        this.r = r;
    }
}

/* 쿼리 결과를 저장할 클래스 */
class Result{
    /* 쿼리의 원래 순서, 쿼리 결과 */
    int q;
    long v;

    Result(int q, long v){
        this.q = q;
        this.v = v;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M, K;
    public static long[] tree, arr;
    public static Queue<Query1> queries1 = new LinkedList<Query1>();
    public static ArrayList<Query2> queries2 = new ArrayList<Query2>();
    public static ArrayList<Result> result = new ArrayList<Result>();

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(){
        init(1, 0, N-1);
    }

    /* 세그트리를 초기화하는 초기화 메소드 */
    public static void init(int n, int s, int e){
        if(s==e){
            tree[n] = arr[s];
        }else{
            init(n*2, s, (s+e)/2);
            init(n*2+1, (s+e)/2+1, e);

            tree[n] = tree[n*2] + tree[n*2+1];
        }
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
            tree[n] = v;
        }else{
            update(n*2, s, (s+e)/2, i, v);
            update(n*2+1, (s+e)/2+1, e, i, v);

            tree[n] = tree[n*2] + tree[n*2+1];

        }
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int l, int r){
        return query(1, 0, N-1, l, r);
    }

    /* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
    public static long query(int n, int s, int e, int l, int r){
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

        N = Integer.parseInt(br.readLine());

        arr = new long[N];
        tree = new long[4*N];

        input = br.readLine().split(" ");

        for(int i=0; i<N; i++){
            arr[i] = Long.parseLong(input[i]);
        }

        /* 세그트리 초기화 */
        init();

        /* 쿼리를 입력 받음 */
        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int Q = Integer.parseInt(input[0]);

            if(Q==1){
                int I = Integer.parseInt(input[1])-1;
                int V = Integer.parseInt(input[2]);

                queries1.add(new Query1(++K, I, V));
            }else if(Q==2){
                int K = Integer.parseInt(input[1]);
                int L = Integer.parseInt(input[2])-1;
                int R = Integer.parseInt(input[3])-1;

                queries2.add(new Query2(i, K, L, R));
            }
        }

        /* 2번 쿼리들을 k를 기준으로 오름차순 정렬 */
        Collections.sort(queries2, new Comparator<Query2>() {
            @Override
            public int compare(Query2 q1, Query2 q2) {
                return q1.k - q2.k;
            }
        });

        for(int i=0; i<queries2.size(); i++){
            Query2 q2 = queries2.get(i);

            /* 2번 쿼리가 갖는 k값 이하의 k를 갖는 1번 쿼리를 모두 수행 */
            while(!queries1.isEmpty()&&queries1.peek().k<=q2.k){
                Query1 q1 = queries1.poll();

                update(q1.i, q1.v);
            }
            
            /* 결과값을 저장 */
            result.add(new Result(q2.q, query(q2.l, q2.r)));
        }

        /* 결과값을 원래의 순서대로 정렬 */
        Collections.sort(result, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                return r1.q - r2.q;
            }
        });

        /* 결과값 출력 */
        for(int i=0; i<result.size(); i++){
            bw.write(result.get(i).v+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}