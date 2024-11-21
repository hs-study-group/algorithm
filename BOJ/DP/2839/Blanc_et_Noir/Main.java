/* https://www.acmicpc.net/problem/2839 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        int result = 0;

        while(N > 0){
            /* N이 5의 배수라면 5kg 봉지로 배달 */
            if(N % 5 == 0){
                result += N / 5;
                break;
            }

            /* N이 3kg보다 적으면 배달할 수 없음 */
            if(N < 3){
                result = -1;
                break;
            }

            /* 3kg을 감소시킴 */
            N -= 3 ;

            /* 사용한 봉지의 수 증가 */
            result++;
        }

        bw.write(result+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}