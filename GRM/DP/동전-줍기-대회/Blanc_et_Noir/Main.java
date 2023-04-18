//https://level.goorm.io/exam/49078/%EB%8F%99%EC%A0%84-%EC%A4%8D%EA%B8%B0-%EB%8C%80%ED%9A%8C/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		final int N = Integer.parseInt(br.readLine());
		
		long[] arr = new long[N];
		
		//배열에 저장할 숫자를 입력 받고 배열에 대입함
		String[] temp = br.readLine().split(" ");
		
		for(int i=0;i<N;i++){
			arr[i]=Long.parseLong(temp[i]);
		}
		
		//dp[ i ]는 i번째 요소를 연속 수열의 마지막 요소로 삼을 때의
		//연속수열의 최대값을 저장하는 dp배열
		long[] dp = new long[N];
		
		//0번째 요소는 음수일 경우 선택하면 당연히 최대값이 될 수 없으므로
		//양수일때만 해당 숫자를 선택함
		dp[0] = Math.max(0,arr[0]);
		
		//0번째 요소가 음수일 경우 선택하면 선택하지 않았을때보다 당연히
		//값이 작아지므로 음수일때는 선택하지 않아야 최대값이 됨
		long max = Math.max(0,arr[0]);
		
		for(int i=1;i<N;i++){
			//기존에 i-1번째 요소를 연속수열의 가장 마지막 값으로 할 때의 최대값에서 i번째 요소를 선택했을때와
			//기존의 연속 수열을 포기하고 i번째 요소만을 선택했을때의 값중 더 큰값을 dp[ i ] 값으로 설정함
			dp[i] = Math.max(dp[i-1]+arr[i],arr[i]);
			
			//만약 해당 dp배열의 값이 기존 max보다 크다면 그것을 max로 갱신함
			max = Math.max(max,dp[i]);
		}

		bw.write(max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}