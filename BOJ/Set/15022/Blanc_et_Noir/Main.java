/* https://www.acmicpc.net/problem/15022 */

import java.io.*;
import java.util.*;

class Node implements Comparable<Node>{
    /* 해결한 문제 수, 패널티 점수 */
    int v, p;

    Node(int v, int p){
        this.v = v;
        this.p = p;
    }

    /* 문제를 해결하는 메소드 */
    public void solve(int p){
        this.v += 1;
        this.p += p;
    }

    /* 문제를 더 많이 풀었거나, 같은 문제수를 해결했다면 더 적은 패널티를 가진 팀이 등수가 높음 */
    @Override
    public int compareTo(Node n) {
        if(this.v==n.v){
            return n.p - this.p;
        }

        return this.v - n.v;
    }
}

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int N, M;
    
    /* 1번팀보다 높은 등수를 갖는 팀을 저장하는 셋 */
    public static HashSet<Integer> high = new HashSet<Integer>();
    
    /* 각 팀의 정보를 저장할 배열 */
    public static Node[] teams;

    public static void main(String[] args) throws IOException {
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);

        teams = new Node[N];

        for(int i=0;i<N;i++){
            teams[i] = new Node(0,0);
        }

        for(int i=0; i<M; i++){
            input = br.readLine().split(" ");

            int idx = Integer.parseInt(input[0])-1;
            int val = Integer.parseInt(input[1]);

            /* 해당 팀이 문제를 해결함 */
            teams[idx].solve(val);

            /* 1번팀이 문제를 해결했다면 */
            if(idx==0){
                /* 1번팀보다 더 낮은 등수를 갖는 팀의 정보를 저장하는 해시 셋 */
                HashSet<Integer> low = new HashSet<Integer>();

                /* 1번팀보다 더 높은 등수를 갖는 팀 중에서, 이제는 1번팀보다 더 낮은 등수를 갖게 되는 팀을 찾음 */
                for(Integer n : high){
                    if(teams[0].compareTo(teams[n])>=0){
                        low.add(n);
                    }
                }

                /* 1번팀보다 낮은 등수를 갖는 팀들을 높은 등수를 갖는 팀에서 제거함 */
                for(Integer n : low){
                    high.remove(n);
                }
            }

            /* 이번에 문제를 해결한 팀이 1번팀보다 등수가 높아진다면 high 셋에 추가함 */
            if(teams[idx].compareTo(teams[0]) > 0){
                high.add(idx);
            }

            bw.write((high.size()+1)+"\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }
}