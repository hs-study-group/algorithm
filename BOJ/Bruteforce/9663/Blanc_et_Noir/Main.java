//https://www.acmicpc.net/problem/9663

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class boj_9663 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//가능한 배치의 개수를 카운트할 변수
	static int cnt = 0;
	
	public static void DFS(int[] map, int idx) {
		//idx 인덱스 값이 열의 개수 만큼이 된 경우
		//모든 퀸을 배치하는 것에 성공한 것임
		if(idx == map.length) {
			cnt++;
			return;
		}
		
		//모든 열에 대하여
		for(int i=0; i<map.length; i++) {
			//현재 열 이전의 모든 퀸들을 조사해서 현재 열의 특정 행에 퀸을 배치할 수 있다면
			if(check(map, idx, i)) {
				//해당 위치에 퀸을 배치함
				map[idx] = i;
				
				//다음 열로 이동함
				DFS(map, idx+1);
			}
		}
	}
	
	//해당 위치에 퀸을 놓을 수 있는지 없는지 판단하는 메소드
	public static boolean check(int[] map, int idx, int row) {
		//자신보다 이전의 열에 존재하는 퀸들에 대해서
		for(int i=0; i<idx; i++) {
			//자신과 다른 열, 같은 행에 존재하는 퀸이 있으면
			if(map[i]==row) {
				//해당 위치에 퀸을 놓을 수 없음
				return false;
			//자신과 다른 열, 다른 행에 존재하는 퀸 중에서 대각선 위치에 존재하는 퀸이 있으면
			}else if(Math.abs(map[i]-row)==Math.abs(i-idx)) {
				//해당 위치에 퀸을 놓을 수 없음
				return false;
			}
		}
		//해당 위치에 퀸을 배치할 수 있음
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(br.readLine());
		DFS(new int[N],0);
		bw.write(cnt+"\n");
		bw.flush();
		bw.close();
	}
}