//https://www.acmicpc.net/problem/1753

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node{
	int idx, cost;
	Node(int idx, int cost){
		this.idx = idx;
		this.cost = cost;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//어떤 특정 간선정보를 가지고 특정 노드에서 다른 노드로 이동하는데 필요한 최소 비용을 구하는 다익스트라 메소드
	public static int[] dijkstra(ArrayList<ArrayList<Node>> graph, int start) {
		int[] dist = new int[graph.size()];
		
		//다익스트라에 사용할 우선순위 큐 선언
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			//소모한 비용이 더 적은 노드가 먼저 반환되도록 우선순위를 정함
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.cost < n2.cost) {
					return -1;
				}else if(n1.cost > n2.cost) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//최소 비용정보를 저장할 배열을 모두 최대치로 초기화함
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		//시작 위치에서 시작 위치로 이동하는 최소 비용을 0으로 초기화함
		dist[start] = 0;
		
		//우선순위 큐에 해당 시작 노드 정보를 추가함
		pq.add(new Node(start,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//만약 여태까지 탐색한 비용이 최소비용을 넘어서면 더이상 탐색을 진행할 필요가 없음
			if(n.cost > dist[n.idx]) {
				continue;
			}
			
			//현재 위치에서 이동할 수 있는 인접위치로의 비용을 탐색함
			for(int i=0; i<graph.get(n.idx).size(); i++) {
				//인접한 위치의 정점
				Node next = graph.get(n.idx).get(i);
				
				//여태까지 사용한 비용 + 인접한 위치로 이동하기 위한 비용
				int cost = n.cost + next.cost;
				
				//위에서 계산한 비용이 인접한 위치로 이동하는데 필요한 최소 비용보다 저렴하다면
				if(cost<dist[next.idx]) {
					//최소 비용을 해당 비용으로 갱신함
					dist[next.idx] = cost;
					
					//우선순위 큐에 해당 노드 정보를 추가함
					pq.add(new Node(next.idx, cost));
				}
			}
		}
		
		//최소 비용이 저장된 배열을 반환함
		return dist;
	}
	
	public static void main(String[] args) throws IOException {
		String[] temp = br.readLine().split(" ");
		
		//정점의 수와 간선의 수를 입력 받음
		int V = Integer.parseInt(temp[0]);
		int E = Integer.parseInt(temp[1]);
		
		//간선정보를 저장할 그래프를 초기화 함
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		for(int i=0; i<V; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//시작 위치를 입력받음
		int S = Integer.parseInt(br.readLine())-1;
		
		//간선 정보를 입력 받음
		for(int i=0; i<E; i++) {
			temp = br.readLine().split(" ");
			
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			graph.get(A).add(new Node(B,C));
		}
		
		//다익스트라 메소드를 수행하여 최소 비용이 저장된 배열을 얻음
		int[] dist = dijkstra(graph,S);
		
		//최소 비용들을 모두 출력함
		for(int i=0; i<dist.length; i++) {
			if(dist[i] != Integer.MAX_VALUE) {
				bw.write(dist[i]+"\n");
			}else {
				bw.write("INF\n");
			}
		}
		
		bw.flush();
		bw.close();
	}
}