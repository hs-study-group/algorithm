//https://www.acmicpc.net/problem/1766

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//위상정렬후 정렬 순서를 출력하는 메소드
	public static String topologicalSort(ArrayList<ArrayList<Integer>> graph, int[] indegree) {
		StringBuilder sb = new StringBuilder();
		
		//해당 우선순위 큐는 난이도가 적은 문제집이 더 먼저 반환되도록 함
		//진입차수가 동일하게 0인 문제집이 여러개 있을 때, 그 중에서도 어떤 문제집을 먼저 풀 지를 우선순위 큐를 활용하여 정함
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				if(n1<n2) {
					return -1;
				}else if(n1>n2) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//진입차수가 0인 문제집들은 지금 당장 풀 수 있는 문제집이므로 우선순위 큐에 추가함.
		for(int i=0; i<indegree.length; i++) {
			if(indegree[i]==0) {
				pq.add(i);
			}
		}
		
		while(!pq.isEmpty()) {
			int A = pq.poll();
			
			//진입차수가 0인 문제집을 품
			sb.append((A+1)+" ");
			
			//해당 문제집을 풀어야만 풀 수 있는 다음 문제집들에 대하여
			for(int i=0; i<graph.get(A).size(); i++) {
				int B = graph.get(A).get(i);
				
				//해당 문제집의 진입차수를 1감소시킴
				indegree[B]--;
				
				//그 문제집의 진입차수가 0이라면
				//드디어 그 문제를 풀 사전 준비가 완료된 것이므로 우선순위 큐에 추가함
				if(indegree[B]==0) {
					pq.add(B);
				}
			}
		}
		
		//위성정렬 순서를 출력함
		return sb.toString().trim();
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//진입 차수 생성
		int[] indegree = new int[N];
		
		//간선정보를 입력받을 이중 리스트 선언
		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		
		//이중 리스트 초기화
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		
		//위상정렬을 수행하기 전에 각 노드의 진입차수를 계산하고 간선정보를 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			
			graph.get(A).add(B);
			
			//B로 진입할 수 있는 노드의 수를 1증가시킴
			//즉 A -> B로 이동할 수 있기 때문에, A를 풀어야만 B를 풀 수 있기 때문임
			indegree[B]++;
		}
		
		//위상정렬 결과를 출력함
		bw.write(topologicalSort(graph,indegree)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}