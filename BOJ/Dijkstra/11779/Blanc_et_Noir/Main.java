//https://www.acmicpc.net/problem/11779

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

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
	
	//주어진 간선 정보에 대하여 S노드에서 시작했을때 나머지 노드로의 최소비용 및 경로를 반환하는 메소드
	public static HashMap<String, Object> dijkstra(ArrayList<ArrayList<Node>> graph, int S){
		int[] dist = new int[graph.size()];
		int[] path = new int[graph.size()];
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		//해당 위치로 오는데 사용된 비용이 더 적은 노드가 먼저 반환되는 우선순위 큐 선언
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
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
		
		//거리 및 경로를 저장할 배열을 초기화함
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(path, Integer.MAX_VALUE);
		
		//시작위치에서 시작위치로 이동하는 비용은 0이며, 이를 우선순위 큐에 추가함
		dist[S] = 0;
		pq.add(new Node(S,0));
		
		//우선순위 큐가 비어있지 않으면
		while(!pq.isEmpty()) {
			//노드 정보를 가져옴
			Node cur = pq.poll();
			
			//여태까지 사용한 비용이 해당 위치에 도달하는데 필요한 최소 비용보다 많다면
			if(cur.cost>dist[cur.idx]) {
				//더이상 탐색할 필요가 없으므로 continue
				continue;
			}
			
			//인접한 위치에 대하여
			for(int i=0; i<graph.get(cur.idx).size(); i++) {
				//인접한 위치에 대한 정보를 얻음
				Node next = graph.get(cur.idx).get(i);
				
				//지금까지 소모한 비용 + 다음 인접한 위치로 이동하는데 필요한 비용을 계산함
				int cost = cur.cost + next.cost;
				
				//위에서 계산한 비용이 인접한 위치로 이동하는데 필요한 최소 비용보다 적다면
				if(cost<dist[next.idx]) {
					//그것을 최소비용으로 갱신하고
					dist[next.idx] = cost;
					
					//우선순위 큐에 추가하여 추가적으로 탐색할 수 있도록 함
					pq.add(new Node(next.idx,cost));
					
					//경로를 저장함
					path[next.idx] = cur.idx;
				}
			}
		}
		
		//최소 비용 및 경로를 반환함
		result.put("dist", dist);
		result.put("path", path);
		
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp;
		
		//도시의 개수
		int N = Integer.parseInt(br.readLine());
		
		//버스의 개수
		int B = Integer.parseInt(br.readLine());
		
		//간선정보를 저장할 2차원 어레이리스트 선언
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();

		//어레이리스트를 초기화함
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//간선정보를 입력받음
		for(int i=0; i<B; i++) {
			temp = br.readLine().split(" ");
			int U = Integer.parseInt(temp[0])-1;
			int V = Integer.parseInt(temp[1])-1;
			int W = Integer.parseInt(temp[2]);
			
			graph.get(U).add(new Node(V,W));
		}
		
		temp = br.readLine().split(" ");
		
		//시작 노드
		int S = Integer.parseInt(temp[0])-1;
		
		//종료 노드
		int E = Integer.parseInt(temp[1])-1;
		
		//다익스트라를 수행한 결과를 받음
		HashMap<String, Object> result = dijkstra(graph,S);
		
		int[] dist = (int[]) result.get("dist");
		int[] path = (int[]) result.get("path");
		
		//경로 배열을 이용하여 역순으로 경로를 추적하기 위해 종료 노드부터 탐색함
		int I = E;
		
		//도시의 개수를 카운트할 변수
		int C = 1;
		
		bw.write(dist[E]+"\n");
		
		StringBuilder sb = new StringBuilder();
		
		//경로를 역순으로 정렬하기 위한 Stack 선언
		Stack<Integer> s = new Stack<Integer>();
		
		
		//시작노드를 찾을때 까지 역순으로 경로를 추적함
		while(I!=S) {
			s.push(I+1);
			I = path[I];
			C++;
		}
		sb.append((I+1)+" ");	
		
		bw.write(C+"\n");
		
		//Stack에 저장된 노드들을 꺼내서 문자열로 덧붙임
		while(!s.isEmpty()) {
			sb.append(s.pop()+" ");
		}
		
		bw.write(sb.toString().trim()+"\n");
		bw.flush();
		bw.close();
		
	}
}