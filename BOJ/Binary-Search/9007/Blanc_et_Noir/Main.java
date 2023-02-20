//https://www.acmicpc.net/problem/9007

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//특정한 배열에서 v값보다 크거나 같은값중 가장 작은 값의 인덱스를 리턴하는 메소드
	public static int lowerbound(int[] arr, int v){
		int l = 0, r = arr.length;
		
		while(l<r) {
			int m = (l+r)/2;
			
			if(v<=arr[m]) {
				r = m;
			}else{
				l = m + 1;
			}
		}
		
		//만약 arr에 있는 모든 값들이 v보다 작다면
		if(r==arr.length) {
			//배열의 마지막 인덱스를 리턴함
			r=arr.length-1;
		}
		
		return r;
	}
	
	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(br.readLine());

		while(T-->0) {			
			String[] temp = br.readLine().split(" ");
			
			//여태까지 구해낸 조합의 합의 입력값 K와의 차이를 저장할 변수
			int diff = Integer.MAX_VALUE;
			
			//K와의 차이가 가장 작을때의 조합의 총합을 저장할 변수
			int answer = Integer.MAX_VALUE;
			
			int K = Integer.parseInt(temp[0]);
			int N = Integer.parseInt(temp[1]);
			
			//각 반의 선수 정보를 입력 받을 배열
			int[][] arr = new int[4][N];
			
			//각 반의 선수 정보를 입력 받음
			for(int i=0; i<arr.length; i++) {
				temp = br.readLine().split(" ");
				for(int j=0;j<N;j++) {
					arr[i][j] = Integer.parseInt(temp[j]);
				}
			}
			
			//1,2반에서 얻을 수 있는 선수들 몸무게 총합과
			//3,4반에서 얻을 수 있는 선수들 몸무게 총합을 저장할 배열
			int[][] list = new int[2][N*N];
			
			//1, 2반과 3, 4반을 짝지어 만들 수 있는 몸무게 총합의 조합을 구함
			for(int i=0; i<arr.length/2; i++) {
				int idx = 0;
				for(int j=0; j<N; j++) {
					for(int k=0; k<N; k++) {
						list[i][idx++] = arr[i][j] + arr[i+2][k];
					}
				}
			}
			
			//3, 4반의 선수를 골라 얻을 수 있는 몸무게 총합의 조합이 담긴 배열은
			//lowerbound 알고리즘 활용을 위해 오름차순 정렬
			Arrays.sort(list[1]);
			
			for(int i=0; i<list[0].length; i++) {
				//입력값 K에서 1,2반에서 한명씩 선택하여 만들 수 있는 몸무게의 총합을 빼고
				//3, 4반에서 한명씩 선택하여 K에 가깝게 만들 수 있는 몸무게의 총합을 구함
				int idx1 = list[0][i] + list[1][lowerbound(list[1],K-list[0][i])];
				int idx2 = list[0][i] + list[1][Math.max(0, lowerbound(list[1],K-list[0][i])-1)];

				//만약 1,2반에서 선택된 선수의 몸무게와 3, 4반에서 선택된 선수의 몸무게가 diff보다 작다면
				if(Math.abs(K-idx2)<diff) {
					//그때의 K와의 차이를 diff로 갱신함
					diff = Math.abs(K-idx2);
					//그때의 몸무게 총합을 answer로 갱신함
					answer = idx2;
				//만약 1,2반에서 선택된 선수의 몸무게와 3, 4반에서 선택된 선수의 몸무게가 diff와 같고, answer보다 총합이 작다면
				}else if(Math.abs(K-idx2)==diff&&idx2<answer) {
					//그때의 몸무게 총합을 answer로 갱신함
					answer = idx2;
				}
				
				//만약 1,2반에서 선택된 선수의 몸무게와 3, 4반에서 선택된 선수의 몸무게가 diff보다 작다면
				if(Math.abs(K-idx1)<diff) {
					//그때의 K와의 차이를 diff로 갱신함
					diff = Math.abs(K-idx1);
					//그때의 몸무게 총합을 answer로 갱신함
					answer = idx1;
				//만약 1,2반에서 선택된 선수의 몸무게와 3, 4반에서 선택된 선수의 몸무게가 diff와 같고, answer보다 총합이 작다면
				}else if(Math.abs(K-idx1)==diff&&idx1<answer) {
					//그때의 몸무게 총합을 answer로 갱신함
					answer = idx1;
				}

			}			
			bw.write(answer+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}