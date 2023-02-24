//https://www.acmicpc.net/problem/9024

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(br.readLine());
		
		//T개의 테스트 케이스를 수행
		while(T-->0) {
			String[] temp = br.readLine().split(" ");
			
			//배열의 크기를 입력받음
			int N = Integer.parseInt(temp[0]);
			
			//기준이 될 값
			int K = Integer.parseInt(temp[1]);
			
			//배열 선언 및 초기화
			int[] arr = new int[N];
			
			//배열에 숫자를 입력 받음
			temp = br.readLine().split(" ");
			
			for(int i=0; i<N; i++) {
				arr[i] = Integer.parseInt(temp[i]);
			}
			
			//배열을 오름차순 정렬
			Arrays.sort(arr);
			
			//K와의 차이가 가장 적은 조합의 개수를 저장할 변수
			int cnt = 1;
			
			//가장 왼쪽 및 오른쪽 포인터
			int s = 0;
			int e = N-1;
			
			//s와 e포인터가 가리키는 숫자의 합과 K의 차이를 diff로 정의함
			int diff = Integer.MAX_VALUE;
			
			//왼쪽 포인터가 오른쪽 포인터보다 왼쪽에 존재할 경우
			while(s<e) {
				//두 포인터가 가리키는 숫자의 합
				int sum = (arr[e]+arr[s]);
				
				//두 포인터가 가리키는 숫자의 합과 K의 차이
				int abs = Math.abs(K-sum);
				
				//만약 기존에 기록했던 최소 차이값과 동일한 차이를 보인다면
				if(abs==diff) {
					//결과 값을 1개 카운트함
					cnt++;
				//만약 기존에 기록했던 최소 차이값보다 작은 차이를 보인다면
				}else if(abs<diff) {
					//그때의 최소 차이를 기록하고, 결과 값을 1부터 시작하도록 초기화
					diff = abs;
					cnt=1;
				}
				
				//만약 지금 합이 K보다 크다면
				if(sum>K) {
					//오른쪽 포인터를 한칸 왼쪽으로 이동시켜 그 합을 감소시킴
					e--;
				//만약 지금 합이 K보다 작다면
				}else if(sum<K) {
					//왼쪽 포인터를 한칸 오른쪽으로 이동시켜 그 합을 증가시킴
					s++;
				//만약 지급 합이 K와 동일하다면 s 또는 e 또는 두 포인터를 이동시킴
				}else {
					s++;
					e--;
				}
			}
			
			bw.write(cnt+"\n");
		}		
		
		bw.flush();
		bw.close();
		br.close();
	}
}