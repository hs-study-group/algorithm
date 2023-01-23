//https://www.acmicpc.net/problem/2470

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//절대값이 0에 가장 가까울때의 두 용액을 저장할 변수
		int r1 = 0, r2 = 0;
		
		String[] temp = br.readLine().split(" ");
		
		//용액들의 정보를 입력받을 배열
		int[] arr = new int[N];
		
		//용액의 정보를 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//용액을 오름차순으로 정렬함
		Arrays.sort(arr);
		
		//투 포인터 알고리즘을 사용하기 위해 시작, 끝 포인터를 지정함
		int s = 0, e = arr.length-1;
		
		//용액의 합의 최소값을 저장할 변수
		int min = Integer.MAX_VALUE;
		
		while(s<e) {
			//현재 포인터가 가리키는 용액의 합을 구함
			int sum = arr[s] + arr[e];
			
			//현재 포인터가 가리키는 용액의 합의 절대값을 구함
			int abs = Math.abs(sum);
			
			//만약 절대값이 기존에 최소라고 생각했던 절대값보다 작다면
			if(abs<min) {
				//그것을 최소값으로 갱신함
				min = abs;
				
				//s, e가 가리키는 두 용액을 결과 변수에 저장함
				r1 = arr[s];
				r2 = arr[e];
			}
			
			//만약 두 용액의 합이 양수라면
			if(sum>0) {
				//오른쪽 포인터를 왼쪽으로 이동시켜 0에 가까워질 수 있는지 탐색함
				e--;
			//만약 두 용액의 합이 음수라면
			}else if(sum<0) {
				//왼쪽 포인터를 오른쪽으로 이동시켜 0에 가까워질 수 있는지 탐색함
				s++;
			//만약 두 용액의 합이 0이라면
			}else {
				//더이상 탐색할 필요도 없이 바로 두 용액을 정답으로 갱신함
				r1 = arr[s];
				r2 = arr[e];
				break;
			}
		}
		
		bw.write(r1+" "+r2);
		bw.flush();
		bw.close();
		br.close();
	}
}