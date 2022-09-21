//https://www.acmicpc.net/problem/14428

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node{
	int idx ,cost;
	Node(int idx, int cost){
		this.idx = idx;
		this.cost = cost;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int dijkstra(ArrayList<ArrayList<Node>> graph, int S, int E) {
		int[] dist = new int[graph.size()];
		
		//다익스트라 알고리즘을 효율적으로 구현하기 위한 우선순위큐 선언
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			//여태까지 탐색에 소모한 비용이 적은 노드가 먼저 반환되도록 함
			public int compare(Node n1, Node n2) {
				if(n1.cost<n2.cost) {
					return -1;
				}else if(n1.cost>n2.cost) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//최소 비용을 저장할 배열을 최대치로 초기화함
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		//단, 시작지점 자기 자신에 대해서는 비용이 0인 것으로 처리해야함
		dist[S] = 0;
		pq.add(new Node(S,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//만약 여태까지 탐색하며 사용한 비용이 해당 지점에 도착하는데 필요한 최소 비용보다 많이 필요하다면
			if(n.cost>dist[n.idx]) {
				//더이상 탐색할 필요가 없음, 비용이 음수인 경우는 없기 때문
				continue;
			}
			
			//현재 위치에서 인접한 위치로 이동할지 말지 여부를 반복하여 탐색함
			for(int i=0; i<graph.get(n.idx).size(); i++) {
				//현재 위치와 인접한 위치의 정보를 얻음
				Node next = graph.get(n.idx).get(i);
				
				//여태까지 사용한 비용 + 인접한 위치로 이동하는데 필요한 비용을 계산함
				int cost = n.cost + next.cost;
				
				//위에서 계산한 그 비용이 기존에 기록했었던 최소비용보다 작다면
				if(cost<dist[next.idx]) {
					//그것을 최소비용으로 갱신함
					dist[next.idx] = cost;
					
					//해당 위치부터 다시 탐색할 수 있도록 우선순위 큐에 추가함
					pq.add(new Node(next.idx,cost));
				}
			}
		}
		
		//S -> E로 이동하기 위해 필요한 최소 비용을 반환함
		return dist[E];
	}
	
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		//2차원 어레이리스트 선언, 간선정보를 저장할 그래프
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		
		for(int i=0; i<N;i++) {
			graph.add(new ArrayList<Node>());
		}
		
		String[] temp;
		
		//간선 정보를 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			
			//문제에서는 도시 번호가 1부터 시작하지만, 다익스트라 알고리즘을 실제로 수행할 때엔
			//0번 인덱스부터 사용하므로 -1을 해야함
			int S = Integer.parseInt(temp[0])-1;
			int E = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			//간선정보를 그래프에 추가함
			graph.get(S).add(new Node(E,C));
		}
		
		temp = br.readLine().split(" ");
		
		//탐색을 시작할 도시와 종료할 도시 번호를 입력받음
		int S = Integer.parseInt(temp[0])-1;
		int E = Integer.parseInt(temp[1])-1;
		
		//다익스트라 알고리즘을 수행하여 S -> E로 이동하는데 필요한 최소 비용을 계산함
		bw.write(dijkstra(graph, S, E)+"\n");
		
		bw.flush();
		bw.close();
		br.close();
	}
}