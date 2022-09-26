//https://www.acmicpc.net/problem/15650

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void combinate(int[] arr, boolean[] v, int idx, int c, String str) throws IOException {
		//c개 만큼의 숫자를 모두 선택했으면
		if(c==0) {
			//수열을 출력함
			bw.write((str).trim()+"\n");
		//아직 선택 횟수가 남아있으면
		}else {
			//조합이기 때문에 0이 아닌 idx부터 탐색함
			for(int i=idx; i<arr.length; i++) {
				//중복하여 선택한 인덱스가 아니라면
				if(!v[i]) {
					//해당 인덱스를 선택한 것으로 처리
					v[i] = true;
					
					//재귀적으로 조합을 구함
					combinate(arr,v,i,c-1,str+" "+arr[i]);
					
					//해당 인덱스를 선택하지 않은 것으로 처리
					v[i] = false;
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		
		//N과 M을 입력 받음
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[] arr = new int[N];
		
		//배열을 초기화 함
		for(int i=0; i<N;i++) {
			arr[i] = i+1;
		}
		
		//조합을 구함
		combinate(arr,new boolean[arr.length],0,M,"");
		
		bw.flush();
		bw.close();
		br.close();
	}
}