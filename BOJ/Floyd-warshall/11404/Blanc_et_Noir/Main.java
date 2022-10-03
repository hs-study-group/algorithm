//https://www.acmicpc.net/problem/11404

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int INF = 100000*1000;
		int[][] arr = new int[N][N];
		
		//자기 자신을 제외한 나머지 정점으로의 가중치를 INF로 초기화 함
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(i==j) {
					arr[i][j] = 0;
				}else {
					arr[i][j] = INF;
				}
			}
		}
		
		int M = Integer.parseInt(br.readLine());
		
		String[] temp;
		
		//가중치를 입력 받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			//단, A에서 B로 이동하는 버스는 하나가 아니라 여럿 존재할 수 있으므로
			//A에서 B로 이동할 수 있는 버스의 비용중 가장 적은 비용이 저장되도록 함
			if(arr[A][B]>C) {
				arr[A][B] = C;
			}			
		}
		
		//플로이드 워셜 알고리즘을 수행함
		for(int k=0; k<N; k++) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					arr[i][j] = Math.min(arr[i][j], arr[i][k]+arr[k][j]);
				}
			}
		}
		
		//최소 비용정보를 출력함
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(arr[i][j]!=INF) {
					bw.write(arr[i][j]+" ");
				}else {
					bw.write(0+" ");
				}
			}
			bw.write("\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}