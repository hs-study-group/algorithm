//https://www.acmicpc.net/problem/12738

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int getLowerBound(int[] arr,int val, int s, int e) {
		int m = (s+e)/2;
		if(s==e) {
			return m;
		}
		if(arr[m]>=val) {
			return getLowerBound(arr,val,s,m);
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
			if(arr[i]<=result[cnt]) {
				result[getLowerBound(result,arr[i],0,cnt)] = arr[i];
			}else{
				result[++cnt] = arr[i];
			}
		}
		
		bw.write((cnt+1)+"\n");
		bw.flush();
	}
}