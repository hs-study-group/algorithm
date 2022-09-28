//https://www.acmicpc.net/problem/16566

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//val보다 큰 값이 처음 나타나는 인덱스를 반환하는 upperbound( ) 메소드
	//만약 그러한 값이 없으면 arr.length를 반환함
	static int upperbound(int[] arr, int val) {
		int start = 0, end = arr.length;
		
		while(start<end) {
			int mid = (start+end)/2;
			if(arr[mid]<=val) {
				start = mid+1;
			}else {
				end = mid;
			}
		}
		
		return end;
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		int K = Integer.parseInt(temp[2]);
		
		//해당 카드를 사용했는지 아닌지 여부를 저장할 배열
		boolean[] v = new boolean[M];
		
		temp = br.readLine().split(" ");
		
		//카드 배열을 초기화 함
		int[] arr = new int[M];
		
		//카드들의 정보를 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//카드를 오름차순 정렬함
		Arrays.sort(arr);
		
		temp = br.readLine().split(" ");
		
		for(int i=0; i<temp.length; i++) {
			//상대가 val값을 가진 카드를 내려고 할때
			int val = Integer.parseInt(temp[i]);
			
			//그 val값보다 더 크지만, 그 중에서는 가장 작은 값의 인덱스를 반환함
			int idx = upperbound(arr,val);
			
			//해당 인덱스부터 선형탐색하여 사용하지 않은 카드의 인덱스를 반환함
			while(idx<arr.length&&v[idx]) {
				idx++;
			}
			
			//해당 카드를 출력함
			bw.write(arr[idx]+"\n");
			
			//해당 카드를 사용한 것으로 처리함
			v[idx]=true;
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}