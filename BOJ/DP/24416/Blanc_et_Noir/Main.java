/* https://www.acmicpc.net/problem/24416 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());

        /* DP 배열 초기화 */
        int[] DP = new int[41];

        /* DP 초기화 */
        DP[0] = 0;
        DP[1] = 1;
        DP[2] = 1;

        /* DP 계산 */
        for(int i=3; i<DP.length; i++){
            DP[i] = DP[i-1] + DP[i-2];
        }

        /* 재귀형태, 반복형태의 코드 호출 수 계산 */
        int v1 = DP[N];
        int v2 = N==1||N==2?0:N-2;

        bw.write(v1+" "+v2+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}