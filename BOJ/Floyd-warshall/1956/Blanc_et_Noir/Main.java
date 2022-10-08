//https://www.acmicpc.net/problem/1956

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//플로이드 워셜 알고리즘을 통해 각 정점에서 다른 정점으로 이동하는데 필요한 최소 비용을 계산하는 메소드
	public static void floydWarshall(int[][] graph) {
		for(int k=0; k<graph.length; k++) {
			for(int i=0; i<graph.length; i++) {
				for(int j=0; j<graph.length; j++) {
					graph[i][j] = Math.min(graph[i][j], graph[i][k]+graph[k][j]);
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//Integer.MAX_VALUE로 초기화 하면 오버플로우가 발생하여
		//음수가 발생할 수 있으므로 임의로 INF값을 정함
		final int INF = Integer.MAX_VALUE/2;
		
		int V = Integer.parseInt(temp[0]);
		int E = Integer.parseInt(temp[1]);
		
		//간선정보를 저장할 배열
		int[][] graph = new int[V][V];
		
		//간선정보를 초기화 함
		for(int i=0; i<graph.length; i++) {
			for(int j=0; j<graph.length; j++) {
				//단, 자기 자신으로의 비용은 0이며, 그외에는 무한대로 큰 값으로 초기화 함
				if(i!=j) {
					graph[i][j] = INF;
				}
			}
		}
		
		//간선정보를 입력 받음
		for(int i=0; i<E; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			graph[A][B] = C;
		}
		
		//각 정점에서 다른 정점으로 이동하기 위한 최소비용을 계산함
		floydWarshall(graph);
		
		int min = INF;
		
		for(int i=0; i<graph.length; i++) {
			for(int j=0; j<graph.length; j++) {
				if(i!=j) {
					//기존의 최소비용보다 i -> j -> i로 이동하는 비용이 더 작으면 그값으로 최소값을 갱신함
					min = Math.min(min, graph[i][j]+graph[j][i]);
				}
			}
		}
		
		//만약 최소값이 갱신된 적이 있었다면
		if(min<INF) {
			//최소값을 출력함
			bw.write(min+"\n");
		//최소값이 갱신된 적이 없다면
		}else {
			//최소비용의 운동경로를 결정할 수 없으므로 -1을 출력함
			bw.write("-1\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}