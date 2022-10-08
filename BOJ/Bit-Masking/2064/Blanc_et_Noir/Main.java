//https://www.acmicpc.net/problem/2064

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static String getString(long num) {
		String result = "";
		
		for(int i=3; i>=0; i--) {
			int digit = 1;
			long sum = 0;
			for(int j=0; j<8; j++) {
				long bit = getBit(num,8*i+j);
				sum += digit*bit;
				digit*=2;
			}
			
			if(i>0) {
				result+=sum+".";
			}else {
				result+=sum;
			}
		}
		return result;
	}
	
	public static long getBit(long num, int b) {
		return ((num&(1<<b))>>>b);
	}
	
	public static long setBit(long num, int b, int v) {
		if(v==0) {
			return (num&(~(1<<b)));
		}else {
			return (num|(1L<<b));
		}
	}
	
	public static boolean checkBit(long[] arr, int b) {
		for(int i=0; i<arr.length-1; i++) {
			if(getBit(arr[i],b)!=getBit(arr[i+1],b)) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		long[] arr = new long[N];
		
		for(int i=0; i<N; i++) {
			String[] temp = br.readLine().split("\\.");
			for(int j=0; j<temp.length; j++) {
				arr[i] += Long.parseLong(temp[j])<<(8*(3-j));
			}
		}
		
		int len = -1;
		
		
		
		for(int i=31; i>=0; i--) {
			if(!checkBit(arr,i)) {
				len = i;
				break;
			}
		}
		
		long address = arr[0];
		long mask = arr[0];

		for(int i=31; i>len; i--) {
			mask = setBit(mask,i,1);
		}
		
		for(int i=len; i>=0; i--) {
			address = setBit(address,i,0);
			mask = setBit(mask,i,0);
		}

		bw.write(getString(address)+"\n");
		bw.write(getString(mask)+"\n");
		
		bw.flush();
		bw.close();
		br.close();
	}
}