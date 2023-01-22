//https://www.acmicpc.net/problem/6209

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int D = Integer.parseInt(temp[0]);
		int N = Integer.parseInt(temp[1]);
		int M = Integer.parseInt(temp[2]);
		
		int[] arr = new int[N+2];
		
		//맨 앞과 맨 뒤의 돌섬의 정보를 추가함
		arr[0] = 0;
		arr[arr.length-1] = D;
		
		//작은 돌섬의 정보를 입력 받음
		for(int i=1; i<arr.length-1; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		//돌섬을 오름차순으로 정렬함
		Arrays.sort(arr);
		
		//돌섬의 최소 거리는 1, 최대 거리는 1000000000임
		int min = 1;
		int max = 1000000000;
		int answer = 0;
		
		while(min<=max) {
			//인접한 돌섬간의 최소 거리를 mid라고 설정함
			int mid = (min+max)/2;

			int cnt = 0;
			int current = 0;
			
			for(int i=1; i<arr.length; i++) {
				//현재 돌섬과 다음에 탐색할 돌섬의 거리를 비교함
				int distance = arr[i] - arr[current];
				
				//거리가 mid보다 작다면, 해당 돌섬을 제거해야만 최소거리가 mid가 되도록 할 수 있음
				if(distance<mid) {
					//돌섬을 제거한 횟수를 1 증가시킴
					cnt++;
				//거리가 mid보다 크거나, 같다면 해당 돌섬을 제거할 필요가 없으므로 탐색할 기준이 될 돌섬을 갱신함
				}else {
					current = i;
				}
			}
			
			//만약 M개 초과의 돌섬을 제거했다면
			if(cnt>M){
				//너무 많은 돌섬을 제거한 것이므로, 기준이 될 최소거리를 감소시켜 돌섬을 덜 제거하도록 함
				max = mid - 1;
			//만약 M개 미만의 돌섬을 제거했다면
			}else if(cnt<M){
				//너무 적은 돌섬을 제거한 것이므로, 기준이 될 최소거리를 증가시켜 돌섬을 더 제거하도록 함
				min = mid + 1;
				//현재 기준으로 잡은 최소 거리를 정답으로 갱신함
				answer = mid;
			//만약 M개의 돌섬을 제거했다면
			}else{
				//동일하게 M개를 제거하더라도, 최소거리가 더 큰 경우가 있을 수 있으므로 최소 거리를 늘려 탐색함
				min = mid + 1;
				//현재 기준으로 잡은 최소 거리를 정답으로 갱신함
				answer = mid;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}