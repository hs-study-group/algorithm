//https://level.goorm.io/exam/177463/rgb-%EC%A3%BC%EC%B0%A8%EC%9E%A5/quiz/1

import java.io.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		final long N = Long.parseLong(br.readLine());
		final long VAL = 100000007;
		
		long result = 3;
		
		//3*2^0 -> 3*2^1 -> 3*2^2 -> ... 3*2^(N-1)과 같은 등비수열을 이룸
		for(int i=0;i<N-1;i++){
			//매 결과를 VAL로 나눈 나머지를 result로 사용함
			result = (result*2)%VAL;
		}
		
		bw.write(result+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}