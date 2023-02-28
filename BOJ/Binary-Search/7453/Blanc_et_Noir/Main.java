//https://www.acmicpc.net/problem/7453

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;


public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//오름차순으로 정렬된 배열에서 v보다 큰 값이 처음 나타나는 위치를 반환하는 메소드
	public static int upperbound(int[] arr, int v) {
		int s = 0, e = arr.length, m=0;
		
		while(s<e) {
			m = (s+e)/2;
			if(arr[m]>v) {
				e=m;
			}else {
				s=m+1;
			}
		}
		
		return e;
	}
	
	//오름차순으로 정렬된 배열에서 v보다 크거나 같은 값이 처음 나타나는 위치를 반환하는 메소드
	public static int lowerbound(int[] arr, int v) {
		int s = 0, e = arr.length, m = 0;
		
		while(s<e) {
			m = (s+e)/2;
			if(arr[m]>=v) {
				e=m;
			}else {
				s=m+1;
			}
		}
		
		return e;
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int[][] arr = new int[4][N];
		int[][] newArr = new int[2][N*N];
		
		String[] temp = null;
		
		//4개의 배열에 대한 정보를 입력 받음
		for(int i=0;i<N;i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<4;j++) {
				arr[j][i] = Integer.parseInt(temp[j]);
			}
		}
		
		//1, 2번 배열과 3, 4번 배열을 이용해서 만들 수 있는 숫자의 합의 조합을 모두 구함
		for(int i=0; i<N;i++) {
			for(int j=0;j<N;j++) {
				newArr[0][i*N+j] = arr[0][i] + arr[1][j];
				newArr[1][i*N+j] = arr[2][i] + arr[3][j];
			}
		}
		
		//새로 만들어진 두 번째 배열은 오름차순으로 정렬함
		Arrays.sort(newArr[1]);
		
		long cnt = 0;
		int v = 0;
		
		for(int i=0; i<newArr[0].length;i++) {
			//새로 만들어진 첫 번째 배열의 값이 value라면
			//새로 만들어진 두 번째 배열에서는 값이 -value여야만
			//4개의 배열에서 각각 하나의 숫자를 골랐을때의 그 합이 0가 될 수 있음
			v = -newArr[0][i];
			
			//따라서 새로 만들어진 첫 번째 배열에서 선택한 값과 상쇄되도록 하는 값의 개수를
			//upperbound - lowerbound를 활용하여 새로 만들어진 두 번째 배열에서 구함
			cnt += upperbound(newArr[1],v)-lowerbound(newArr[1],v);
		}
		
		bw.write(cnt+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}