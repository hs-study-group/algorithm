//https://www.acmicpc.net/problem/18114

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
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		int[] arr = new int[N];
		int answer = 0;
		
		//무게 정보들을 입력 받음
		temp = br.readLine().split(" ");
		
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//무게를 오름차순 정렬함
		Arrays.sort(arr);
		
		//투 포인터 알고리즘에 사용할 s, e 포인터를 선언함
		int s, e;
		
		//3개의 무게를 선택하여 M을 만들 수 있는지 확인함
		for(int i=0;i<N-2; i++) {
			//그 무게를 제외한 구간에서 사용할 투 포인터를 선언함
			s = i+1;
			e = N-1;
			
			while(s<e) {
				//i, s, e 번째 무게를 선택했때의 무게 합을 구함
				int sum = arr[i]+arr[s]+arr[e];
				
				//그 무게 합이 M보다 작다면
				if(sum<M) {
					//s포인터를 증가시킴
					s++;
				//그 무게 합이 M보다 크다면
				}else if(sum>M) {
					//e포인터를 감소시킴
					e--;
				//그 무게 합이 M과 같다면
				}else {
					//조건을 만족하는 조합이 있으므로 정답을 1로 갱신하고 반복을 종료함
					answer = 1;
					break;
				}
			}
		}
		
		//포인터를 초기화 함
		s = 0;
		e = N-1;
		
		//2개의 무게를 선택하여 무게 합이 M이 되도록 하는 조합을 구함
		while(s<e) {
			//s, e포인터가 가리키는 무게의 합을 구함
			int sum = arr[s]+arr[e];
			
			//그 무게 합이 M과 같다면
			if(sum==M) {
				//정답을 1로 갱신하고 반복을 종료함
				answer = 1;
				break;
			//만약 그 무게 합이 M보다 작다면
			}else if(sum<M){
				//s포인터를 증가시킴
				s++;
			//만약 그 무게 합이 M보다 크다면
			}else {
				//e포인터를 감소시킴
				e--;
			}
		}
		
		//1개의 무게를 선택하여 그 무게가 M이 되는 경우를 찾음
		for(int i=0;i<arr.length;i++) {
			//만약 무게가 M인 상품이 있다면
			if(arr[i]==M) {
				//정답을 1로 갱신하고 반복을 종료함
				answer=1;
				break;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}