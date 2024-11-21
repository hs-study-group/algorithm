/* https://www.acmicpc.net/problem/1629 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static long A, B, C;

    /* A ^ B % C를 재귀 분할정복으로 계산하는 메소드 */
    public static long recursive(long A, long B, long C){
        if(B==1){;
            return A%C;
        }

        /* 불필요한 중복 재귀를 방지하기 위해 변수에 값을 저장 및 재활용 */
        long temp = recursive(A, B/2, C)%C;
        
        /* 지수가 짝수라면 재귀 결과를 서로 곱함 */
        if(B%2==0){
            return (temp * temp)%C;
        /* 지수가 홀수라면 재귀 결과를 서로 곱하고 A를 이어서 곱함 */
        }else{
            return ((temp * temp)%C * A)%C;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        A = Long.parseLong(input[0]);
        B = Long.parseLong(input[1]);
        C = Long.parseLong(input[2]);

        bw.write(recursive(A, B, C)+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}