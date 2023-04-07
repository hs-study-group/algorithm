//https://www.acmicpc.net/problem/1561

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		Long N = Long.parseLong(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		temp = br.readLine().split(" ");
		
		//배열에 놀이기구 운행 시간들을 입력받음
		int[] arr = new int[M];
		
		for(int i=0; i<arr.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//
		long min = 1;
		long max =  2000000000L*30L;
		long time = 0;
		
		//만약 사람의 수 N이 놀이기구의 수 M보다 적거나 같다면
		//마지막 사람은 N번째 놀이기구를 탑승하게 됨
		//여러개의 놀이기구가 비어있다면 가장 앞에서부터 탑승하기 때문
		if(N<=M) {
			bw.write(N+"\n");
		//사람수 N이 놀이기구의 수 M보다 크다면
		}else {
			while(min<=max) {
				//mid라는 시간 내에 탑승가능한 최대 인원수를 구하기위한 기준 시간
				long mid = (min+max)/2;
				
				//mid시간내에 탑승가능한 최대 인원수
				long cnt = 0;
				
				//어떤 놀이기구의 운행시간이 t라면, mid시간내에 탈 수 있는 사람의 수는 mid/t임
				//모든 놀이기구에 대하며 t시간내에 탑승가능한 모든 인원수를 구함
				for(int i=0; i<arr.length; i++) {
					cnt += mid/arr[i];
				}
				
				//만약 mid시간내에 탑승가능한 최대 인원수가 0초대에 탑승했던 인원들을 제외한
				//나머지 인원들을 모두 수용할 수는 없다면
				if(cnt<N-M) {
					//기준 시간인 mid를 증가시켜봄
					min = mid + 1;
				//만약 mid시간내에 탑승가능한 최대 인원수가 0초대에 탑승했던 인원들을 제외한
				//나머지 인원들을 모두 수용할 수는 있다면
				}else if(cnt>N-M) {
					//mid보다 더 적은 시간으로도 모든 인원을 수용할 수 있는지 확인하기 위해
					//기준 시간인 mid를 감소시켜봄
					max = mid - 1;
					//그때의 시각을 time으로 기록해둠
					time = mid;
				//만약 mid시간내에 탑승가능한 최대 인원수가 0초대에 탑승했던 인원들을 제외한
				//나머지 인원들을 모두 수용할 수는 있다면
				}else {
					//mid보다 더 적은 시간으로도 모든 인원을 수용할 수 있는지 확인하기 위해
					//기준 시간인 mid를 감소시켜봄
					max = mid - 1;
					//그때의 시각을 time으로 기록해둠
					time = mid;
				}
			}
			
			//
			int sum = M;
			
			//N명을 모두 탑승시킬 수 있는 최소한의 시간인 time에 대하여
			//time-1만큼의 시간으로 탑승시킬 수 있는 인원의 수를 sum이라고 설정함
			for(int i=0; i<arr.length; i++) {
				sum += (time-1)/arr[i];
			}
			
			//time-1시간동안 사람들을 탑승시키고, time시각에(시간이 아님)
			//탑승중인 사람이 없어 탑승시킨 사람의 수
			int cnt = 0;
			
			for(int i=0; i<arr.length; i++) {
				//time을 운행시각으로 나누었을때 나누어 떨어진다면
				if(time%arr[i]==0) {
					//time시각에는 해당 놀이기구는 새로운 사람을 탑승시킬 준비가 되어있는 것이므로 카운트함
					cnt++;
				}
				
				//time-1시간동안 사람들을 최대한 탑승시키고도 남은 사람들을 모두 탑승시켰다면
				if(cnt==N-sum) {
					//그때의 놀이기구 번호를 출력함
					bw.write((i+1)+"\n");
					break;
				}
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}