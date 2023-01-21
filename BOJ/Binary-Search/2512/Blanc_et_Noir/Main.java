//https://www.acmicpc.net/problem/2512

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		String[] temp = br.readLine().split(" ");
		long[] arr = new long[N];

		for(int i=0; i<temp.length; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		int M = Integer.parseInt(br.readLine());
		
		//예산의 상한액의 최소치
		long min = 1;
		
		//예산의 상한액의 최대치
		long max = M;
		
		//모든 지방에 적절한 예산을 할당했을때, 가장 많이 할당된 예산액을 저장할 변수
		long answer = 0;
		
		while(min<=max) {
			//예산의 상한액을 정함
			long mid = (min+max)/2;
			
			//지방에 예산을 할당하고 남은 총 예산액
			long remain = M;
			
			//예산을 할당받은 지방의 수
			int cnt = 0;
			
			//할당한 예산액중에서 가장 큰 예산액
			long result = 0;
			
			for(int i=0; i<arr.length; i++) {
				//만약 지방 정부가 요청한 예산액이 상한액을 넘어서면 상한액까지만 지급하고
				//아니라면 지방 정부가 요청한만큼만 제공함
				long val = arr[i]>=mid?mid:arr[i];
				
				//그만큼을 남은 예산에서 제외함
				remain -= val;
				
				//만약 예산이 초과된다면
				if(remain<0) {
					//더이상 탐색할 필요 없음
					break;
				}
				
				//예산 할당에 성공했다면, 그 예산액과 누적하여 구한 최대 예산액을 비교하여 최대치를 적절하게 갱신함
				result = Math.max(result, val);
				
				//예산을 할당받은 지방 정부의 수를 1 증가시킴
				cnt++;
			}
			
			//모든 지방에 예산을 할당했다면 예산에 여유가 있는 것임
			if(cnt==N) {
				//예산의 상한액을 좀 더 늘려봄
				min = mid + 1;
				
				//mid를 예산의 상한액으로 정했을때 가장 많이 할당받은 지방 정부의 예산액을 answer로 설정함
				answer = result;
			//모든 지방에 예산을 할당하지 못하고, 예산이 부족하다면
			}else {
				//상한액을 좀 더 줄여봄
				max = mid - 1;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}