//https://level.goorm.io/exam/49057/k-%EA%B1%B0%EB%A6%AC-%EC%88%98/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//두 정점 v1, v2의 거리차이를 구하는 메소드
	public static int getDistance(int v1, int v2){
		return Math.abs(v2-v1);
	}
	
	public static void main(String[] args) throws Exception {
		final int N = Integer.parseInt(br.readLine());
		String[] temp = br.readLine().split(" ");
		
		//배열의 정보를 입력 받음
		int[] arr = new int[N];
		
		for(int i=0;i<N;i++){
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		final int LENGTH = 100001;
		
		//각 숫자들을 최근 발견했을때의 위치를 저장할 배열
		int[] idx = new int[LENGTH];
		
		//각 숫자들에 대하여, 같은 숫자들간의 거리가 최대가 될 때의 값을 저장할 배열
		int[] distances = new int[LENGTH];
		
		//위의 두 배열을 -1로 초기화함
		Arrays.fill(idx,-1);
		Arrays.fill(distances,-1);
		
		//각 숫자들에 대하여 거리를 계속 구해나감
		for(int i=0;i<N;i++){
			int v = arr[i];
			
			//기존에 찾았던 위치
			int s = idx[v];
			
			//이번에 찾은 위치
			int e = i;
			
			//두 위치간의 거리 차이를 구함
			int d = getDistance(s,e);

			//그렇게 구한 거리가 기존보다 큰 값을 가지면 그것을 최대값으로 갱신함
			distances[v] = Math.max(distances[v],d);
			
			//최근 탐색한 위치를 s에서 e로 갱신함
			idx[v]=e;
		}
		
		for(int i=0;i<LENGTH;i++){
			//최근 탐색한 위치가 -1이라면 그러한 숫자는 배열에 없었다는 의미이므로 반복을 건너뜀
			if(idx[i]==-1){
				continue;
			}
			
			//해당 숫자가 가장 마지막으로 발견된 위치
			int s = idx[i];
			
			//배열을 범위를 처음 벗어날때의 위치
			int e = N;
			
			//두 정점간의 거리를 구함
			int d = getDistance(s,e);
			
			//그 거리가 기존에 기록해둔 거리보다 크다면 그것을 최대값으로 갱신함
			distances[i] = Math.max(distances[i],d);
		}
		
		//모든 숫자에 대하여 K거리가 최소가 될 때의 거리값
		int MIN_DISTANCE = Integer.MAX_VALUE;
		
		//모든 숫자에 대하여 K거리가 최소가 되는 수가 여러개라면, 그중 더 큰값을 저장할 변수
		int MAX_VALUE = Integer.MIN_VALUE;
		
		for(int i=0;i<distances.length;i++){
			//가장 최근에 탐색한 위치가 -1이라면 그 숫자는 배열에 없었던 것이므로 반복을 건너뜀
			if(idx[i]==-1){
				continue;
			}
			
			//만약 해당 숫자의 K거리가 기존에 기록해둔 것보다 작다면
			if(distances[i]<MIN_DISTANCE){
				//그것을 최소값으로 갱신함
				MIN_DISTANCE = distances[i];
				//그때의 숫자를 기록해둠
				MAX_VALUE = i;
			//만약 해당 숫자의 K거리가 기존의 최소값과 동일하면서, 그때의 숫자보다는 더 큰 숫자라면
			}else if(distances[i]==MIN_DISTANCE&&i>MAX_VALUE){
				//그 숫자를 최대값으로 갱신함
				MAX_VALUE = i;
			}
		}
		
		bw.write(MAX_VALUE+" "+MIN_DISTANCE+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}