//https://www.acmicpc.net/problem/1072

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		long X = Long.parseLong(temp[0]);
		long Y = Long.parseLong(temp[1]);

		//만약 현재 승률이 99% 이상이라면, 아무리 여러번 게임을 이겨도 승률이 오르지 않음
		if(Y*100/X>=99) {
			//따라서 -1을 출력함
			bw.write("-1\n");
		//승률이 오를 여지가 남이 있다면
		}else {
			//앞으로 형택이가 
			long min = 1;
			long max = Long.MAX_VALUE/100;
			
			//초기의 승률
			long rate = Y*100/X;
			long answer = 0;

			while(min<=max) {
				//앞으로 형택이가 더 플레이할 횟수를 정함
				long mid=(min+max)/2L;
				
				//형택이는 무조건 게임에서 이기므로 mid만큼 이긴다고 가정했을때의 새로운 승률을 구함
				long newRate = ((Y+mid)*100)/(X+mid);

				//새로운 승률이 기존보다 낮거나, 같다면
				if(newRate<=rate) {
					//형택이의 승률에 개선이 없는 것이므로 플레이 횟수를 증가시킴
					min = mid + 1;
				//형택이의 승률에 개선이 있다면
				}else {
					//형택이가 플레이할 횟수를 감소시킴
					max = mid - 1;
					//그때의 횟수를 정답으로 일단 기록함
					answer = mid;
				}
			}
			bw.write(answer+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}