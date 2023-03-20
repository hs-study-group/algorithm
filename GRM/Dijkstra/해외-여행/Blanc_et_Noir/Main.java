//https://level.goorm.io/exam/49091/%ED%95%B4%EC%99%B8-%EC%97%AC%ED%96%89/quiz/1

import java.io.*;
import java.util.*;

//정점 번호 v, 해당 정점까지 이동하기위해 필요한 비용 c를 저장할 노드 클래스 선언
class Node implements Comparable<Node>{
	int v, c;
	Node(int v, int c){
		this.v=v;
		this.c=c;
	}
	
	//더 적은 비용을 소모하는 노드 정보가 먼저 반환되도록 함
	public int compareTo(Node n){
		if(this.c<n.c){
			return -1;
		}else if(this.c>n.c){
			return 1;
		}else{
			return 0;
		}
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 간선정보를 바탕으로 n-1번 나라에 도착하기위한 최소 비용을 구하는 메소드
	public static int dijkstra(ArrayList<ArrayList<Node>> graph){
		int[] dist = new int[graph.size()];
		
		//최소비용 배열을 MAX_VALUE로 초기화함
		Arrays.fill(dist,Integer.MAX_VALUE);
		
		//여행을 시작할 위치는 최소비용을 0으로 초기화함
		dist[0] = 0;
		
		//다익스트라에 사용할 우선순위 큐 선언
		PriorityQueue<Node> pq = new PriorityQueue();
		
		//0번나라에서 0의 비용을 사용한 상태로 다익스트라 수행
		pq.add(new Node(0,0));
		
		while(!pq.isEmpty()){
			//현재 정보를 얻음
			Node node = pq.poll();
			
			//현재 탐색중인 위치와 인접한 정점에 대하여
			for(int i=0; i<graph.get(node.v).size(); i++){
				//인접한 정점의 정보를 얻음
				Node next = graph.get(node.v).get(i);
				
				//여태까지 사용한 비용 + 인접한 정점으로 이동하기위한 비용을 구함
				int cost = node.c + next.c;
				
				//그 비용이 기존에 기록한 최소 비용보다 작다면
				if(cost<dist[next.v]){
					//그것을 최소비용으로 갱신하고
					//해당 위치부터 다시 탐색할 수 있도록 정보를 큐에 추가함
					dist[next.v] = cost;
					pq.add(new Node(next.v,cost));
				}
			}
		}
		
		//n-1번 나라에 도착하기위한 최소 비용을 리턴함
		return dist[graph.size()-1];
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int n = Integer.parseInt(temp[0]);
		int m = Integer.parseInt(temp[1]);
		
		//간선정보를 저장할 2차원 어레이리스트 선언
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		
		//2차원 어레이리스트 초기화
		for(int i=0; i<n; i++){
			graph.add(new ArrayList<Node>());
		}
		
		//간선 정보를 입력 받음
		for(int i=0; i<m; i++){
			temp = br.readLine().split(" ");
			
			//편의를 위해 정점 번호는 0번부터 시작하도록 -1을 함
			int a = Integer.parseInt(temp[0])-1;
			int b = Integer.parseInt(temp[1])-1;
			int c = Integer.parseInt(temp[2]);
			
			//간선 정보를 추가함
			graph.get(a).add(new Node(b,c));
		}
		
		//다익스트라 알고리즘의 결과로 얻은 최소비용을 저장함
		int answer = dijkstra(graph);
		
		//만약 최소비용이 MAX_VALUE가 아니라면
		if(answer!=Integer.MAX_VALUE){
			//그것을 출력함
			bw.write(answer+"\n");
		//최소비용이 MAX_VALUE라면
		}else{
			//go home을 출력함
			bw.write("go home\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
}