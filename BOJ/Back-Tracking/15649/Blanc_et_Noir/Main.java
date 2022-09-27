//https://www.acmicpc.net/problem/15649

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//중복없는 순열을 계산하는 메소드
	public static void permutate(int[] arr, boolean[] v, int idx, int c, String str) throws IOException {
		//c개의 숫자를 모두 선택했다면
		if(c==0) {
			//해당 순열을 출력함
			bw.write(str.trim()+"\n");
		//아직 c개의 숫자를 모두 선택하지 못했다면
		}else {
			for(int i=0; i<arr.length; i++) {
				//아직 해당 숫자를 선택한 적이 없으면
				if(!v[i]) {
					//해당 숫자를 선택함
					v[i]=true;
					
					//재귀적으로 순열을 구함
					permutate(arr,v,i,c-1,str+" "+arr[i]);
					
					//해당 숫자를 선택하지 않은 것으로 처리함
					v[i]=false;
				}
				
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//배열을 선언함
		int[] arr = new int[N];
		
		//배열을 초기화함
		for(int i=0; i<N;i++) {
			arr[i] = i+1;
		}
		
		//사전순으로 출력하기 위해 정렬함
		Arrays.sort(arr);
		
		//순열을 구해서 출력함
		permutate(arr,new boolean[arr.length],0,M,"");
		
		bw.flush();
		bw.close();
		br.close();
	}
}