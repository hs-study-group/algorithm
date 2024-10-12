/* https://www.acmicpc.net/problem/1422 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int K, N;
    public static String[] arr;

    /* 가장 큰 수를 찾는 메소드 */
    public static int max(String[] arr){
        int max = Integer.MIN_VALUE;

        for(String val : arr){
            max = Math.max(max,Integer.parseInt(val));
        }

        return max;
    }

    /* v1, v2를 순서를 바꿔 덧붙인 결과를 비교하여, 우선순위를 결정하는 메소드 */
    public static int select(String v1, String v2){
        char[] arr1 = (v1+v2).toCharArray();
        char[] arr2 = (v2+v1).toCharArray();

        /* -1이면 v1이 높은 우선순위를, 1이면 v2이 높은 우선순위를, 0이면 v1과 v2가 같은 우선순위를 가짐 */
        for(int i=0;i<arr1.length;i++){
            if(arr1[i]-'0'>arr2[i]-'0'){
                return -1;
            }else if(arr1[i]-'0'<arr2[i]-'0'){
                return 1;
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        K = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);

        arr = new String[K];

        for(int i=0;i<K;i++){
            arr[i] = br.readLine();
        }

        /* 우선순위가 더 큰 숫자를 먼저 리턴하는 우선순위 큐 선언 */
        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String v1, String v2) {
                return select(v1,v2);
            }
        });

        /* 입력받은 수 중에서 가장 큰 수를 찾음 */
        String max = max(arr)+"";

        /* 가장 큰 수를 N-K번 우선순위 큐에 추가함 */
        for(int i=0; i<N-K;i++){
            pq.add(max);
        }

        /* 각각의 수를 큐에 한 번씩 추가함 */
        for(int i=0;i<K;i++){
            pq.add(arr[i]);
        }

        /* 큐에 담긴 모든 수를 덧붙여서 출력함 */
        while(!pq.isEmpty()){
            bw.write(pq.poll()+"");
        }

        bw.write("\n");
        bw.flush();
        bw.close();
        br.close();
    }
}