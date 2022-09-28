//https://www.acmicpc.net/problem/1644

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//에라토스테네스 알고리즘 구현 메소드
	public static ArrayList<Integer> getPrimes(boolean[] isPrime) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i=2; i<isPrime.length; i++) {
			//해당 숫자를 아직 체크하지 않았다면
			if(!isPrime[i]) {
				//가장 첫번째 숫자는 소수이므로 리스트에 추가함
				list.add(i);
				
				//소수와 그 배수를 모두 방문처리함
				for(int j=i; j<isPrime.length; j=j+i) {
					isPrime[j] = true;
				}
			}
		}
		
		//소수 리스트를 반환함
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine()), cnt=0, start=0, end=0, sum=0;
		boolean[] isPrime = new boolean[N+1];

		ArrayList<Integer> list = getPrimes(isPrime);
		
		while(true) {
			//합이 N보다 크거나 N과 같다면
			if(sum>=N) {
				//합이 N과 같다면
				if(sum==N) {
					//카운트함
					cnt++;
				}
				//가장 맨 왼쪽의 값을 합에서 제외하고 왼쪽 시작지점을 한칸 우측 이동시킴
				sum -= list.get(start++);
			
			//만약 end가 범위를 벗어났으면
			}else if(end==list.size()){
				//반복문을 탈출함
				break;
			//만약 합이 N보다 작다면
			}else{
				//오른쪽 값을 합에 더하고 오른쪽 종료지점을 한칸 우측 이동시킴
				sum += list.get(end++);
			}
		}
		
		bw.write(cnt+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}