//https://www.acmicpc.net/problem/12920

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 정수 K를 2의 거듭제곱 합으로 나누고 그만큼 무게와 만족도를 곱한 값을 W, S 리스트에 추가하는 메소드
	//V만큼의 무게, S만큼의 만족도를 갖는 물건이 K개 있을때,
	//이를 마치 V, S 만큼의 무게, 만족도를 갖는 물건 1개
	//V*2, S*2 만큼의 무게, 만족도를 갖는 물건 1개
	//V*4, S*4 만큼의 무게, 만족도를 갖는 물건 1개... 와 같이 분할함
	//DP 배열을 채울때의 그 반복 횟수를 줄일 수 있음
	public static void divide(ArrayList<Integer> W, ArrayList<Integer> S, int V, int C, int K) {
		int digit = 1;
		
		//K가 digit보다 크거나 같다면
		while(K>=digit) {
			//digit만큼을 곱한 V, C값을 무게, 만족도를 저장하는 리스트에 추가함
			W.add(V*digit);
			S.add(C*digit);
			
			//K를 digit만큼을 감소시킴
			K-=digit;
			
			//digit을 2배로 증가시킴
			digit*=2;
		}
		
		//K가 나누어 떨이지지 않았다면
		if(K>0) {
			//그 만큼의 가중치를 고려하여 V, C값을 증가시키고 W, S 리스트에 추가함
			W.add(V*K);
			S.add(C*K);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//각 상품의 무게, 만족도를 저장할 리스트 선언
		//DP 처리를 원활하게 할 수 있도록 아무 의미없는 값인 0을 맨 앞에 추가함
		ArrayList<Integer> W = new ArrayList<Integer>();
		W.add(0);
		ArrayList<Integer> S = new ArrayList<Integer>();
		S.add(0);
		
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			
			//V, C, K값을 입력받음
			int V = Integer.parseInt(temp[0]);
			int C = Integer.parseInt(temp[1]);
			int K = Integer.parseInt(temp[2]);
			
			//K를 2의 거듭제곱의 합으로 분할할 필요가 있는 경우
			if(K>1) {
				//K를 분할하고 그 만큼의 적절한 가중치들을 곱하여 W, S리스트에 추가함
				divide(W,S,V,C,K);
			//분할할 필요가 없는 경우
			}else {
				//W, S에 V, C값을 추가함
				W.add(V);
				S.add(C);
			}
		}
		
		//DP[i][j]는 i번째까지의 물건을 j용량을 갖는 가방에 넣었을때 얻을 수 있는 가장 큰 만족도를 의미
		int[][] DP = new int[W.size()+1][M+1];
		
		for(int i=1; i<W.size(); i++) {
			for(int j=1; j<=M; j++) {
				//만약 현재 넣고자 하는 물건의 무게가 가방의 용량보다 작거나 같은 경우
				if(W.get(i)<=j) {
					//i번째까지의 물건을 j용량을 갖는 가방에 넣었을 때 얻을 수 있는 가장 큰 만족도는
					//i-1번째 까지의 물건을 동일한 j용량의 가방에 넣었을 때 얻을 수 있는 가장 큰 만족도와
					//i-1번째 까지의 물건을 j-현재 탐색중인 물건의 무게 만큼의 용량을 갖는 가방에 넣었을 때 얻을 수 있는 가장 큰 만족도 중
					//더 큰값임
					DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j - W.get(i)] + S.get(i));
				//현재 탐색중인 물건의 무게가 가방의 용량보다 크므로 절대로 해당 물건은 선택할 수 없음
				//따라서 i번째 물건을 선택하지 않고 i-1번째 물건까지를 고려했을때의 최대값을 i번째까지를 고려했을때의 최대값으로 설정함
				}else {
					DP[i][j] = DP[i-1][j];
				}
			}
		}
		
		//결과를 출력함
		bw.write(DP[W.size()-1][M]+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}