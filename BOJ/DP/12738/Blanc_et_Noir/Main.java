//https://www.acmicpc.net/problem/12738

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//lowerbound 값의 인덱스를 반환하는 메소드
	//lowerbound란 자신보다 큰 값들중 가장 작은값을 의미
	public static int getLowerBound(int[] arr,int val, int s, int e) {
		int m = (s+e)/2;
		if(s==e) {
			return m;
		}
		//자기자신도 lowerbound가 될 수 있으므로 자기자신도 포함하여 탐색
		if(arr[m]>=val) {
			return getLowerBound(arr,val,s,m);
		//자기자신보다 작은 값들은 lowerbound가 될 수 없음
		}else {
			return getLowerBound(arr,val,m+1,e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int[] result = new int[N];
		int cnt = 0;
		
		String[] temp = br.readLine().split(" ");
		
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		result[0] = arr[0];
		
		for(int i=0; i<arr.length; i++) {
			//입력값 <= 임시배열의 마지막 값 이면
			//더 길이가 길어질 가능성이 있도록 값을 교체
			if(arr[i]<=result[cnt]) {
				result[getLowerBound(result,arr[i],0,cnt)] = arr[i];
			//더 길어질 가능성이 없으므로 그냥 추가만 함
			}else{
				result[++cnt] = arr[i];
			}
		}
		
		bw.write((cnt+1)+"\n");
		bw.flush();
	}
}
