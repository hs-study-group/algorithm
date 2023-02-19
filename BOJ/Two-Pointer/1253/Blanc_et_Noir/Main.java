//https://www.acmicpc.net/problem/1253

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//arr배열에서 idx에 위치한 수를 제외한 나머지 두 수를 활용하여
	//v를 만들 수 있다면 v는 좋은 수임을 반환하는 메소드
	public static boolean find(int[] arr, int v, int idx) {
		int l=0, r=arr.length-1, cnt=0;
		
		while(l<r) {
			//왼쪽 포인터가 v값의 인덱스를 가리키면
			if(l==idx) {
				//그 수는 계산하지 않고 그대로 넘어감
				l++;
				continue;
			//오른쪽 포인터가 v값의 인덱스를 가리키면
			}else if(r==idx) {
				//그 수는 계산하지 않고 그대로 넘어감
				r--;
				continue;
			}
			
			//두 포인터가 가리키는 숫자를 더함
			int sum = arr[l]+arr[r];
			
			//그 합이 v와 같으면 v는 좋은 수임
			if(sum==v) {
				return true;
			//그 합이 v보다 크다면
			}else if(sum>v) {
				//오른쪽 포인터를 감소시킴
				r--;
			//그 합이 v보다 작다면
			}else {
				//왼쪽 포인터를 증가시킴
				l++;
			}
		}
		
		//중간에 true가 반환되지 않으면 그것은 좋은 수가 아님
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//좋은 수의 개수를 저장할 변수
		int answer = 0;
		
		String[] temp = br.readLine().split(" ");
		int[] arr = new int[N];
		
		//정수를 입력 받음
		for(int i=0; i<N;i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//투 포인터 알고리즘 적용을 위해 오름차순 정렬
		Arrays.sort(arr);
		
		for(int i=0; i<N;i++) {
			//해당 숫자가 자신을 제외한 나머지 두 개의 수의 합으로 나타낼 수 있는 좋은 수라면
			if(find(arr,arr[i],i)) {
				//좋은 수의 개수를 1 증가시킴
				answer++;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}