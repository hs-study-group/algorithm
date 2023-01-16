//https://www.acmicpc.net/problem/16564

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		long N = Long.parseLong(temp[0]);
		long K = Long.parseLong(temp[1]);
		
		//최소로 올리고자 하는 팀 레벨
		long min = 1;
		
		//최대로 올리고자 하는 팀 레벨
		long max = 2000000000;
		
		long[] arr = new long[(int) N];
		
		//현재 캐릭터들의 레벨을 입력받음
		for(int i=0; i<N; i++) {
			arr[i] = Long.parseLong(br.readLine());
		}
		
		//팀 레벨을 최소 몇까지 올릴 수 있는지 저장할 변수
		long answer = 0;
		
		while(min<=max) {
			//최소와 최대로 올리고자 하는 팀 레벨의 중간값을 구함
			long mid = (min+max)/2L;
			
			//중간값만큼 팀 레벨을 올리기위해서 캐릭터의 레벨을 얼마나 올렸는지 저장할 변수
			long sum = 0;
			
			//중간값만큼 팀 레벨을 올리려고 할 때 K이하로 캐릭터 레벨을 올릴 수 있는지 확인할 변수
			boolean flag = true;
			
			for(int i=0; i<arr.length; i++) {
				//만약 해당 캐릭터의 레벨이 기준에 못 미친다면
				if(arr[i]<mid) {
					//부족한 만큼을 레벨업시킴
					sum += (mid-arr[i]);
				}
				
				//만약 K를 초과하여 캐릭터를 레벨업 시켰다면
				if(sum>K) {
					//불가능한 것임
					flag=false;
					break;
				}
			}
			
			//팀 레벨을 mid까지 올리는 것이 가능하다면, 아직 여유가 있는 것임
			if(flag) {
				//그 기준이 될 팀 레벨을 증가시키고자 min값을 mid+1로 갱신함
				min = mid+1;
				//mid가 아직까진 최대로 올릴 수 있는 팀 레벨임
				answer = mid;
			//팀 레벨을 mid까지 올리는 것이 불가능하다면 여유가 없는 것임
			}else {
				//그 기준이 될 팀 레벨을 낮추고자 max값을 mid-1로 갱신함
				max = mid-1;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}