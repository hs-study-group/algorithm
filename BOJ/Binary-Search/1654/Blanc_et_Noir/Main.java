//https://www.acmicpc.net/problem/1654

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int K = Integer.parseInt(temp[0]);
		int N = Integer.parseInt(temp[1]);
		
		//랜선의 길이를 입력받음
		int[] arr = new int[K];
		
		for(int i=0; i<K; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		//랜선의 최소 길이
		long min = 1;
		
		//랜선의 최대 길이
		long max = Integer.MAX_VALUE;
		
		long answer = 0;
		
		//탐색의 최소범위가 최대 범위보다 작거나 같다면 반복을 수행함
		while(min<=max) {
			long mid = (min+max)/2L;
			int cnt = 0;
			
			//랜선을 우선 mid크기씩 잘라봄
			for(int i=0; i<arr.length; i++) {
				cnt += arr[i]/mid;
			}

			//만약 N개 미만의 랜선을 얻을 수 있다면
			if(cnt<N) {
				//너무 큼직하게 랜선을 자른 것이므로 랜선을 자르는 크기를 좀 줄여봄
				max = mid - 1;
			//만약 N개 이상의 랜선을 얻을 수 있다면
			}else {
				//너무 잘게 랜선을 잘랐거나, 알맞게 자른 것이므로 크기를 좀 늘려봄
				min = mid + 1;
				answer = mid;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}