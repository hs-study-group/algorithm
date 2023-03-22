//https://level.goorm.io/exam/47881/%EA%B7%BC%EB%AC%B5%EC%9E%90%ED%9D%91/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//idx위치를 기준으로 왼쪽으로 최소 몇번의 변환을 수행해야 하는지를 계산하는 메소드
	public static int getLeft(int idx, int N, int K){
		return (int)Math.ceil(idx*1.0/(K-1));
	}
	
	//idx위치를 기준으로 오른쪽으로 최소 몇번의 변환을 수행해야 하는지를 계산하는 메소드
	public static int getRight(int idx,int N, int K){
		return (int)Math.ceil((N-idx-1)*1.0/(K-1));
	}
	
	//left, right 구간내에서 K개의 연속된 초기 위치를 고르는 메소드 
	public static int select(int left, int right, int N, int K){
		//모든 숫자를 동일하게 1로 변경하는데 필요한 횟수를 저장할 변수 선언
		int min = Integer.MAX_VALUE;
		
		//left, right 구간내에서 K개의 연속된 초기 위치를 고름
		for(int i=left;i+K-1<=right;i++){
			int l = i;
			int r = i + K - 1;
			
			//그 초기 위치를 기준으로 왼쪽, 오른쪽 방향으로 변환하는 횟수를 계산함
			//초기 위치를 1로 변환하는 횟수도 포함시켜야 하므로 +1을 해야함
			//변환 횟수가 기존 최소값보다 작다면 그것을 최소값으로 갱신함
			min = Math.min(min,getLeft(l,N,K)+getRight(r,N,K)+1);
		}
		return min;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int K = Integer.parseInt(temp[1]);
		int[] arr = new int[N];
		int idx = -1;
		
		//배열을 입력받음
		temp = br.readLine().split(" ");
		
		for(int i=0;i<N;i++){
			arr[i] = Integer.parseInt(temp[i]);
			//1의 위치를 기록해둠
			if(arr[i]==1){
				idx = i;
			}
		}
		
		//1을 기준으로 좌우로 -K+1, +K-1 구간의 시작점과 끝점을 각각 left, right로 설정함 
		int left = idx-K+1>=0?idx-K+1:0;
		int right = idx+K-1<N?idx+K-1:N-1;

		//left, right구간에서 근묵자흑 알고리즘을 처음 적용할 K개의 연속된 위치를 고름
		bw.write(select(left,right,N,K)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}