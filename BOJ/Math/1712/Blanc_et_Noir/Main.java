/* https://www.acmicpc.net/problem/1712 */

import java.io.*;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int A, B, C, R;

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        A = Integer.parseInt(input[0]);
        B = Integer.parseInt(input[1]);
        C = Integer.parseInt(input[2]);
        R = -1;

        /* 판매가격이 생산비용보다 높다면 */
        if(B<C){
            /* 갚아야 할 금액 / 1대당 순이익 + 1 이 정답 */
            R = A / (C-B) + 1;
        }

        bw.write(R+"\n");
        bw.flush();
        bw.close();
        br.close();
    }
}