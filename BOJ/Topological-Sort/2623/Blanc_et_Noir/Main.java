//https://www.acmicpc.net/problem/2623

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//간선정보와 진입차수를 바탕으로 위상정렬을 수행 결과를 리턴하는 메소드
	public static String topologicalSort(ArrayList<ArrayList<Integer>> graph, int[] indegree) throws IOException {
		StringBuilder sb = new StringBuilder();
		Queue<Integer> q = new LinkedList<Integer>();
		int cnt=0;
		
		//진입차수가 0인 노드들을 큐에 추가함.
		for(int i=0; i<indegree.length; i++) {
			//진입차수가 0인 노드를 큐에 추가함.
			if(indegree[i]==0) {
				//큐에 추가되었다는 의미는, 자신으로 올 수 있는 다른 노드가 없다는 의미임.
				q.add(i);
				
				//즉, 더이상 자신보다 먼저 선택되어야 할 노드가 없다는 뜻임
				//따라서 위상정렬 순서에 맞게 배치된 노드의 개수를 1증가시킴
				cnt++;
			}
		}

		while(!q.isEmpty()) {
			int A = q.poll();
			
			//해당 노드는 진입차수가 0이므로 자신보다 먼저 탐색되어야할 노드가 더이상 존재하지 않음.
			//따라서, 해당 노드는 정렬 순서에 맞게 배치된 것임.
			sb.append((A+1)+"\n");
			
			//진입차수가 0인 노드와 인접한 노드에 대해
			//즉, 진입차수가 0인 노드가 먼저 선택되어야만 그 후에 선택될 수 있는 노드에 대하여
			for(int i=0; i<graph.get(A).size(); i++) {
				int B = graph.get(A).get(i);
				
				//해당 노드의 진입차수를 감소시킴
				indegree[B]--;
				
				//그 진입차수가 0이된다면
				if(indegree[B]==0) {
					//해당 노드를 큐에 추가함.
					q.add(B);
					
					//진입차수가 0이므로 이 노드는 지금 당장 선택되어도 되므로 정렬이 완료된 노드의 개수를 1증가시킴.
					cnt++;
				}
			}
		}
		
		//만약 위상정렬이 종료되었는데, 정렬이 완료된 노드의 수가 N이면
		if(cnt==graph.size()) {
			//모든 노드를 정렬할 수 있는 것임
			return sb.toString();
		//아니라면
		}else {
			//해당 간선정보로는 모든 노드를 정렬할 수 없음
			//해당 간선정보에 사이클이 존재한다는 의미임
			return "0\n";
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//진입차수를 저장할 배열 선언
		int[] indegree = new int[N];
		
		//간선정보를 저장할 이중 리스트를 선언 및 초기화 함
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		
		//간선정보를 입력받고 진입차수를 계산함
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			for(int j=1; j<temp.length-1; j++) {
				int A = Integer.parseInt(temp[j])-1;
				int B = Integer.parseInt(temp[j+1])-1;
				
				indegree[B]++;
				graph.get(A).add(B);
			}
		}
		
		bw.write(topologicalSort(graph,indegree));
		bw.flush();
		bw.close();
		br.close();
	}
}