/* https://www.acmicpc.net/problem/9034 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /* 압축된 좌표를 저장할 해시맵 */
    public static HashMap<Integer,Integer> idx;

    /* 쿼리를 저장할 배열 */
    public static int[][] q;

    /* 각 사람의 현재 점수를 저장할 배열 */
    public static int[] s;
    public static int[] tree;

    public static int T, N, M;

    /* v점수의 압축된 좌표를 얻는 메소드 */
    public static int get(int v){
        return idx.get(v);
    }

    /* v점수를 좌표압축시켜 i 값으로 인덱스를 설정하는 메소드 */
    public static void set(int v, int i){
        idx.put(v, i);
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

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
    public static int query(int l, int r){
        return query(1, 0, idx.size()-1, l, r)+1;
    }

    /* l ~ r 구간의 합을 리턴하는 쿼리 메소드 */
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

        for(int i=0; i<T; i++){
            /* 메모리 초과 방지를 위한 GC */
            System.gc();

            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            s = new int[N];
            q = new int[M][3];

            /* 좌표압축 시작 */

            idx = new HashMap<Integer, Integer>();
            HashSet<Integer> set = new HashSet<Integer>();

            set.add(0);

            for(int j=0; j<M; j++){
                input = br.readLine().split(" ");

                if(input[0].equals("Q")){
                    q[j][0] = 0;
                    q[j][1] = Integer.parseInt(input[1])-1;
                }else if(input[0].equals("R")){
                    q[j][0] = 1;
                    q[j][1] = Integer.parseInt(input[1])-1;
                    q[j][2] = Integer.parseInt(input[2]);
                    s[q[j][1]] += q[j][2];

                    set.add(s[q[j][1]]);
                }
            }

            ArrayList<Integer> list = new ArrayList<Integer>();

            for(Integer v : set){
                list.add(v);
            }

            /* 오름차순 정렬 */
            Collections.sort(list, Collections.reverseOrder());

            int temp = -1;

            for(int j=0; j<list.size(); j++){
                if(list.get(j)!=temp){
                    temp = list.get(j);

                    set(list.get(j), idx.size());
                }
            }

            list = null;
            set = null;

            /* 좌표압축 종료 */

            tree = new int[idx.size()*4];
            s = new int[N];

            /* 0점이 N명 존재하도록 초기화 */
            update(get(0), N);

            for(int j=0; j<M; j++){
                if(q[j][0]==0){
                    /* 자신의 순위를 출력 */
                    bw.write(query(0, get(s[q[j][1]])-1)+"\n");
                }else if(q[j][0]==1){
                    /* 기존 점수의 사람 숫자를 1명 감소 */
                    update(get(s[q[j][1]]),-1);

                    /* 점수 증가 */
                    s[q[j][1]] += q[j][2];

                    /* 새로운 점수의 사람 숫자를 1명 증가 */
                    update(get(s[q[j][1]]),1);
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}