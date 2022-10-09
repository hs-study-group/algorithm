//https://www.acmicpc.net/problem/1806


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
		int S = Integer.parseInt(temp[1]);
		int C = Integer.MAX_VALUE;
		
		int[] arr = new int[N];
		
		//N개의 정수를 입력 받음
		temp = br.readLine().split(" ");
		
		//입력받은 정수를 배열에 저장함
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//L과 R은 포인터, T는 현재까지 계산된 부분합을 저장할 변수
		int L=0, R=0, T=0;
		
		while(true) {
			//현재까지 계산된 부분합이 S보다 크거나, S와 같은 경우
			if(T>=S) {
				//부분합의 길이가 기존의 최소 길이보다도 작다면
				if(R-L<C) {
					//그것을 최소 길이로 갱신함
					C = R - L;
				}
				
				//L포인터가 가리키는 만큼의 값을 부분합에서 제외하고, L포인터를 우측으로 1칸 이동시킴
				T -= arr[L++];
			//만약 R포인터가 범위를 벗어났으면
			}else if(R==N) {
				//탐색을 종료함
				break;
			//현재까지 계산된 부분합이 S보다 작다면
			}else {
				//R포인터가 가리키는 만큼의 값을 부분합에 포함시키고, R포인터를 우측으로 1칸 이동시킴
				T += arr[R++];
			}
		}
		
		//만약 최소길이가 갱신된 적이 없다면
		if(C==Integer.MAX_VALUE) {
			//부분합이 S가 되는 연속되는 수열이 없으므로 그 길이를 0으로 출력함
			bw.write(0+"\n");
		//만약 최소 길이가 갱신된 적이 있다면
		}else {
			//부분합이 S가 되는 연속되는 수열의 최소 길이를 출력함.
			bw.write(C+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}