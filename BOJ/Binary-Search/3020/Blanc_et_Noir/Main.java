//https://www.acmicpc.net/problem/3020

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//arr배열에서 v와 같거나, 그보다 큰 수의 개수를 리턴하는 메소드
	public static int getTheNumberOfLowerBound(int[] arr, int v) {
		int min = 0;
		int max = arr.length;
		
		while(min<max) {
			int mid = (min+max)/2;
			if(arr[mid]>=v) {
				max = mid;
			}else {
				min = mid+1;
			}
		}
		
		return arr.length-max;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int H = Integer.parseInt(temp[1]);
		
		//석순의 높이를 저장할 배열
		int[] bottom = new int[N/2+N%2];
		
		//종유석의 높이를 저장할 배열
		int[] top = new int[N/2];
		
		int bottomIndex = 0;
		int topIndex = 0;
		
		//홀수번째는 석순, 짝수 번째는 종유석 배열에 해당 높이를 저장함
		for(int i=0; i<N; i++) {
			int V = Integer.parseInt(br.readLine());
			
			if(i%2==0) {
				bottom[bottomIndex++] = V;
			}else {
				top[topIndex++] = V;
			}
		}
		
		//두 배열을 모두 오름차순 정렬함
		Arrays.sort(bottom);
		Arrays.sort(top);
		
		int cnt = 0;
		int min = Integer.MAX_VALUE;
		
		//개똥벌레가 높이 1 ~ H까지 날아갈때의 상황을 탐색함
		for(int i=1; i<=H; i++) {
			//개똥벌레가 i높이로 날아갈때 부딫히는 석순과 종유석의 개수를 구함
			//석순의 경우는 단순히 i높이보다 크거나 같은 석순의 개수를 구하면 됨
			//종유석의 경우는 H-i+1높이보다 크거나 같은 종유석의 개수를 구하도록 해야함
			int sum = getTheNumberOfLowerBound(bottom,i)+getTheNumberOfLowerBound(top,H-i+1);
			
			//만약 부딫히는 횟수가 min보다 작다면
			if(sum<min) {
				//그것을 min으로 갱신하고 cnt값을 1부터 다시 시작함
				min = sum;
				cnt=1;
			//만약 부딫히는 횟수가 min과 동일하다면
			}else if(min==sum) {
				//cnt값을 1증가시킴
				cnt++;
			}
		}
		
		bw.write(min+" "+cnt+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}