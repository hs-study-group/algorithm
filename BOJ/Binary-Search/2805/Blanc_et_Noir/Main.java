//https://www.acmicpc.net/problem/2805

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		long[] arr = new long[N];
		
		temp = br.readLine().split(" ");
		
		for(int i=0; i<N; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//나무를 자르는 최소 높이는 1로 설정함
		long min = 1;
		
		//나무를 자르는 최대 높이는 1000000000로 설정함
		long max = 1000000000;
		long result = 0;
		
		while(min<=max) {
			//mid 높이로 나무를 자르고자 함
			long mid = (min+max)/2;
			
			//mid 높이로 자르고 남은 나무의 길이 합을 구할 변수
			long sum = 0;
			
			//mid 이상의 길이를 갖는 나무를 잘라 남은 부분을 sum에 더함
			for(int i=0; i<arr.length; i++) {
				sum += arr[i]>=mid ? arr[i]-mid : 0;
			}
			
			//만약 잘라낸 나무의 길이 총합이 M이상이라면
			if(sum>=M) {
				//나무를 자르는 높이를 조금 더 높게 설정해도 됨
				min = mid + 1;
				
				//현재 높이를 정답으로 갱신함
				result = mid;
			//만약 잘라낸 나무의 길이 총합이 M미만이라면
			}else {
				//나무를 자르는 높이를 조금 더 낮게 설정해도 됨
				max = mid - 1;
			}
		}
		
		bw.write(result+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}