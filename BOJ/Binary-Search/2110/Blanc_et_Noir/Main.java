//https://www.acmicpc.net/problem/2110

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
		
		//모든 집의 개수
		int N = Integer.parseInt(temp[0]);
		
		//공유기를 설치해야할 집의 개수
		int C = Integer.parseInt(temp[1]);
		
		//공유기를 설치할 수 있는 후보들의 위치 정보를 저장할 배열
		int[] arr = new int[N];
		
		//배열 초기화
		for(int i=0; i<arr.length; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		//이분 탐색을 위해 오름차순 정렬
		Arrays.sort(arr);
		
		//두 집간의 최소 거리는 1임
		int min = 1;
		
		//두 집간의 최대 거리는 1000000000임
		int max = 1000000000;
		int answer = 0;
		
		while(min<=max) {
			//두 집간의 최소 거리를 mid라고 설정함
			int mid = (min+max)/2;
			
			//
			int cnt = 0;
			int current = 0;
			
			for(int i=1; i<arr.length; i++) {
				//만약 두 집간의 거리가 최소거리인 mid보다 크거나 같다면 해당 위치에 공유기를 설치할 수 있음
				if(arr[i]-arr[current]>=mid) {
					//공유기를 설치한 횟수를 1증가시킴 (정확히는 공유기를 설치한 한 쌍의 집의 수를 증가시킴)
					cnt++;
					//기준이 될 집의 인덱스를 갱신함
					current = i;
				}
			}
			
			//만약 C개 미만으로 공유기를 설치했다면
			if(cnt+1<C) {
				//최소 거리를 너무 길게 잡은 것이므로 줄여야함
				max = mid - 1;
			//만약 C개 초과로 공유기를 설치했다면
			}else if(cnt+1>C) {
				//최소 거리를 너무 짧게 잡은 것이므로 늘려야함
				min = mid + 1;
				
				//기준으로 삼았던 최소 거리를 정답으로 갱신함
				answer = mid;
			//만약 C개만큼 공유기를 설치했다면
			}else {
				//동일하게 C개를 설치하더라도, 최소 거리가 커지는 경우가 있을 수 있음
				//따라서 최소 거리를 늘려보고 다시 탐색함
				min = mid + 1;
				
				//기준으로 삼았던 최소 거리를 정답으로 갱신함
				answer = mid;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}