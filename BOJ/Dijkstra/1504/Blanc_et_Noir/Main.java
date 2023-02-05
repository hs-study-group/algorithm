//https://www.acmicpc.net/problem/1504

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

//어떤 정점까지 얼마의 비용을 사용하여 도착했는지 정보를 저장할 노드 클래스
class Node implements Comparable<Node>{
	int n, c;
	Node(int n, int c){
		this.n=n;
		this.c=c;
	}
	
	//사용한 비용이 더 적은 노드 정보가 먼저 반환되도록 함
	@Override
	public int compareTo(Node n) {
		if(this.c < n.c) {
			return -1;
		}else if(this.c > n.c) {
			return 1;
		}else {
			return 0;
		}
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//어떤 간선정보를 바탕으로 정점 s에서 정점 e로 이동하기 위해 필요한 최소 비용을 리턴하는 다익스트라 메소드
	public static int dijkstra(ArrayList<ArrayList<Node>> graph, int s, int e) {
		//최소 비용 정보를 저장할 배열
		int[] dist = new int[graph.size()];
		
		//최소 비용 정보를 모두 MAX_VALUE로 초기화 함
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		//시작 정점에 대해서는 0으로 초기화 함
		dist[s] = 0;
		
		//시작 정점에 대학 정보를 추가함
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(s,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//여태까지 사용한 비용이 현재 위치에 도달하기 위한 최소비용보다 많이 필요하다면
			if(n.c > dist[n.n]) {
				//탐색할 필요 없음
				continue;
			}
			
			//인접한 모든 노드에 대하여 탐색함
			for(int i=0; i<graph.get(n.n).size(); i++) {
				//현재 노드와 인접한 노드
				Node next = graph.get(n.n).get(i);
				
				//여태까지 사용한 비용 + 인접한 노드로 이동하기 위한 비용
				int cost = n.c + next.c;
				
				//그 비용이 인접한 노드로 이동하기 위한 최소 비용보다 적다면
				if(cost<dist[next.n]) {
					//그것을 최소 비용으로 갱신하고 인접한 노드부터 탐색할 수 있도록 노드 정보를 추가함
					dist[next.n] = cost;
					pq.add(new Node(next.n,cost));
				}
			}
		}
		
		//최소 거리를 리턴함
		return dist[e];
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int E = Integer.parseInt(temp[1]);
		
		//간선 정보를 저장할 2차원 어레이리스트
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		
		//2차원 어레이리스트를 초기화함
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//간선정보를 입력받음
		for(int i=0; i<E; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			//양방향 간선이므로 A->B, B->A 두 경로 모두 그래프에 추가함
			graph.get(A).add(new Node(B,C));
			graph.get(B).add(new Node(A,C));
		}
		
		//중간에 경유해야할 정점 번호를 입력 받음
		temp = br.readLine().split(" ");
		int V1 = Integer.parseInt(temp[0])-1;
		int V2 = Integer.parseInt(temp[1])-1;
		
		int answer1, answer2;
		
		//1 -> V1 -> V2 -> N으로 이동할 때의 최소 비용과
		//1 -> V2 -> V1 -> N으로 이동할 때의 최소 비용을 구함
		int[][] dist = new int[][] {
			{dijkstra(graph,0,V1),dijkstra(graph,V1,V2),dijkstra(graph,V2,N-1)},
			{dijkstra(graph,0,V2),dijkstra(graph,V2,V1),dijkstra(graph,V1,N-1)}
		};

		//만약 1 -> V1 -> V2 -> N 경로에서 중간에 한 번이라도 이동 불가능한 경우가 존재한다면
		if(dist[0][0]==Integer.MAX_VALUE||dist[0][1]==Integer.MAX_VALUE||dist[0][2]==Integer.MAX_VALUE) {
			//이동이 불가하다고 판단하고 MAX_VALUE로 설정함
			answer1 = Integer.MAX_VALUE;
		}else {
			//그때의 비용을 최소 비용으로 설정함
			answer1 = dist[0][0]+dist[0][1]+dist[0][2];
		}
		
		//만약 1 -> V2 -> V1 -> N 경로에서 중간에 한 번이라도 이동 불가능한 경우가 존재한다면
		if(dist[1][0]==Integer.MAX_VALUE||dist[1][1]==Integer.MAX_VALUE||dist[1][2]==Integer.MAX_VALUE) {
			//이동이 불가하다고 판단하고 MAX_VALUE로 설정함
			answer2 = Integer.MAX_VALUE;
		}else {
			//그때의 비용을 최소 비용으로 설정함
			answer2 = dist[1][0]+dist[1][1]+dist[1][2];
		}
		
		//두 경로의 최소비용을 비교해서 어느 하나라도 정상적인 경로가 존재한다면
		if(answer1!=Integer.MAX_VALUE||answer2!=Integer.MAX_VALUE) {
			//둘 중 더 작은 비용이 정답임
			bw.write(Math.min(answer1, answer2)+"\n");
		//둘 다 비 정상적인 경로라면
		}else {
			//-1을 출력함
			bw.write("-1\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}