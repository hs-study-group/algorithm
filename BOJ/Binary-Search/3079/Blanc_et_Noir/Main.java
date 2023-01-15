//https://www.acmicpc.net/problem/3079

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
		long M = Long.parseLong(temp[1]);
		
		long[] arr = new long[(int) N];
		
		for(int i=0; i<N; i++) {
			arr[i] = Long.parseLong(br.readLine());
		}
		
		//편의상 1을 최소값으로 사용함
		long min = 1;
		
		//10^6 * 10^10 = 10^18을 최소값으로 사용함
		long max = 1000000000000000000L;
		long answer = 0;
		
		//아직 min <= max라면
		while(min<=max) {
			//중간값을 구함
			long mid = (min+max)/2L;
			
			//min시간내에 얼마나 많은 사람들이 입국심사를 통과할 수 있을지를 저장하는 변수
			long cnt = 0;
			
			//모든 입국심사관들이 min시간동안 응대할 수 있는 사람들의 수를 누적하여 구함 
			for(int i=0; i<arr.length; i++) {
				//가령 어떤 입국심사관 A가 한 사람을 검사할때 T만큼의 시간이 필요하다면
				//K시간 동안에는 K/T 만큼의 사람을 검사할 수 있음
				cnt+=mid/arr[i];
				
				//오버플로우 방지를 위해 M만큼의 사람을 모두 충분히 검사하고도 남는다면 
				if(cnt>=M) {
					//더이상 탐색하지 않고 반복문을 탈출함
					break;
				}
			}
			
			//만약 mid 시간내에 M명 미만을 검사할 수 있다면
			if(cnt<M) {
				//시간이 부족한 것이므로, 시간을 더 넉넉하게 고려하여 탐색함
				min = mid+1;
			//만약 mid시간내에 M명 이상을 검사할 수 있다면
			}else {
				//시간에 여유가 있는 것이므로 시간을 더 줄여서 탐색함
				max = mid-1;
				answer = mid;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}