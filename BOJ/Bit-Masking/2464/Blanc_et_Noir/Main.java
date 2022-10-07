//https://www.acmicpc.net/problem/2464

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	//정수 N에 대하여 b번째 비트의 값을 얻는 메소드
	public static long getBit(long N, long b) {
		return ((N&(1L<<b))>>>b);
	}
	
	//정수 N에 대하여 b번째 비트를 v로 변경하는 메소드
	public static long setBit(long N, long b, long v) {
		if(v==1) {
			return (N|(1L<<b));
		}else {
			return (N&(~(1L<<b)));
		}
	}
	
	//정수 N에 대하여 b ~ 0번째 비트의 모든 1을 left 또는 right 방향으로 한곳에 치우는 메소드
	public static long putAside(long N, long b, int cnt, boolean isLeft) {
		//만약 왼쪽으로 1비트를 치워두어야 한다면
		if(isLeft) {
			//b번째 비트부터
			for(long i=b; i>=0; i--) {
				//1의 개수만큼
				if(cnt>0) {
					//해당 비트를 1로 설정함
					N = setBit(N,i,1);
					
					//1의 개수를 감소시킴
					cnt--;
				
				//만약 1을 모두 왼쪽 치웠다면
				}else {
					//나머지 비트는 전부 0으로 채움
					N = setBit(N,i,0);
				}
			}
		}else {
			//0번째 비트부터
			for(long i=0; i<=b; i++) {
				//1의 개수만큼
				if(cnt>0) {
					//해당 비트를 1로 설정함
					N = setBit(N,i,1);
					
					//1의 개수를 감소시킴
					cnt--;
					
				//만약 1을 모두 왼쪽 치웠다면
				}else {
					//나머지 비트는 전부 0으로 채움
					N = setBit(N,i,0);
				}
			}
		}
		
		//b ~ 0번째 비트에 있는 모든 1을 한쪽으로 치운 결과를 리턴함
		return N;
	}
	
	public static void main(String[] args) throws Exception {
		long N = Long.parseLong(br.readLine());
		long B = N;
		long S = N;
		
		//입력받은 정수 N을 이진변환함
		String bs = "0"+Long.toBinaryString(N);
		
		//해당 정수의 이진 비트에서 한쪽으로 치워야할 비트의 수를 저장하는 변수
		int cnt = 0;
		
		for(int i=0; i<bs.length()-1; i++) {
			//만약 현재 탐색중인 비트가 1이고 그 앞의 비트가 0이라면
			if(getBit(B,i)==1&&getBit(B,i+1)==0) {
				//두 비트를 서로 교환함
				B = setBit(B,i,0);
				B = setBit(B,i+1,1);
				
				//현재 바로 이전 비트 ~ 0번째 비트까지 존재하는 모든 1비트를 오른쪽으로 치워둠
				B = putAside(B,i-1,cnt,false);
				
				//더이상 탐색할 필요가 없음
				break;
			}
			
			//만약 비트가 1이라면
			if(getBit(B,i)==1) {
				//치워야할 비트의 수를 1증가시킴
				cnt++;
			}
		}
		
		//해당 정수의 이진 비트에서 한쪽으로 치워야할 비트의 수를 저장하는 변수
		cnt = 0;
		
		for(int i=0; i<bs.length()-1; i++) {
			//만약 현재 탐색중인 비트가 0이고 그 앞의 비트가 1이라면
			if(getBit(S,i)==0&&getBit(S,i+1)==1) {
				//두 비트를 서로 교환함
				S = setBit(S,i,1);
				S = setBit(S,i+1,0);
				
				//현재 바로 이전 비트 ~ 0번째 비트까지 존재하는 모든 1비트를 왼쪽으로 치워둠
				S = putAside(S,i-1,cnt,true);
				
				//더이상 탐색할 필요가 없음
				break;
			}
			
			//만약 비트가 1이라면
			if(getBit(S,i)==1) {
				//치워야할 비트의 수를 1증가시킴
				cnt++;
			}
		}
		
		//만약 N보다 작아야할 값인 S가 N과 동일하다면
		if(S==N) {
			//N보다 작으면서 N과 1비트의 개수가 동일한 수는 없으므로 0으로 설정함
			S = 0;
		}
		
		//N보다 작으면서 1비트의 개수가 동일한 수와 N보다 크면서 1비트의 개수가 동일한 수를 모두 출력함
		bw.write(S+" "+B+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}