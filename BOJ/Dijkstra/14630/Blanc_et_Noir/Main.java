//https://www.acmicpc.net/problem/14630

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//어떤 정점 n에 도달하기 위해 필요한 비용 c를 저장하는 노드 클래스 선언
class Node{
	int n;
	//c값이 int범위를 벗어날 수 있으므로 long으로 처리함
	long c;
	Node(int n, long c){
		this.n=n;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//상태를 s1에서 s2로 변신시키기 위한 비용을 계산하는 메소드
	public static long getCost(String s1, String s2) {
		long cost = 0;
		
		//편의를 위해 문자 배열로 변환
		char[] arr1 = s1.toCharArray();
		char[] arr2 = s2.toCharArray();
		
		//비용을 계산함
		for(int i=0; i<arr1.length; i++) {
			cost += (arr1[i]-arr2[i])*(arr1[i]-arr2[i]);
		}
		
		//총 비용을 리턴함
		return cost;
	}
	
	//str.length개 중에서 2개를 중복없이 조합을 구하는 메소드
	public static void combinate(ArrayList<ArrayList<Node>> graph, String[] str, int[] result, int idx, int cnt) {
		//모두 선택했다면
		if(cnt==0) {
			int idx1 = result[0];
			int idx2 = result[1];
			
			//2개의 상태를 순서, 중복없이 고름
			String s1 = str[idx1];
			String s2 = str[idx2];
			
			//각 상태끼리의 변신에 필요한 비용을 계산함
			long distance = getCost(s1,s2);
			
			//해당 비용 정보를 양방햔 간선 정보로 취급하여 그래프에 저장함
			graph.get(idx1).add(new Node(idx2,distance));
			graph.get(idx2).add(new Node(idx1,distance));
			
		//아직 선택할 것이 cnt개를 남았다면
		}else {
			//중복, 순서없이 재귀적으로 선택함
			for(int i=idx; i<graph.size(); i++) {
				result[result.length-cnt] = i;
				combinate(graph, str, result, i+1,cnt-1);
			}
		}
	}
	
	//어떤 상태s에서 다른상태 e로 변신시키기 위한 최소 비용을 구하는 다익스트라 메소드
	public static long dijkstra(ArrayList<ArrayList<Node>> graph,String[] str, int s, int e) {
		//최소 비용 정보를 저장할 배열
		long[] dist = new long[graph.size()];
		
		//모두 MAX_VALUE로 초기화 함
		Arrays.fill(dist, Long.MAX_VALUE);
		
		//시작 정점은 0으로 초기화 함
		dist[s] = 0;
		
		//적은 비용을 소모한 노드 정보가 먼저 반환되도록하는 우선순위큐
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.c<n2.c) {
					return -1;
				}else if(n1.c>n2.c) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//우선순위큐에 시작 노드 정보를 추가함
		pq.add(new Node(s,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//여태까지 소모한 비용이 해당 정점에 도달하기위해 필요한 최소비용보다 크다면
			if(n.c > dist[n.n]) {
				//더이상 탐색할 필요 없음
				continue;
			}
			
			//현재 정점과 인접한 정점들에 대하여
			for(int i=0; i<graph.get(n.n).size();i++) {
				//인접한 정점의 정보를 얻음
				Node next = graph.get(n.n).get(i);
				
				//여태까지 소모한 비용 + 인접한 정점으로 이동하기 위해 필요한 비용
				long cost = n.c + next.c;
				
				//그 비용이 다음 정점으로 이동하는데 필요한 최소비용보다 작다면
				if(cost<dist[next.n]) {
					//그것을 최소비용으로 갱신하고, 계속 탐색하도록 노드 정보를 추가함
					dist[next.n] = cost;
					pq.add(new Node(next.n,cost));
				}
			}
		}
		
		//최소 비용을 리턴함
		return dist[e];
	}
	
	public static void main(String[] args) throws Exception {
		//간선 정보를 저장할 2차원 어레이리스트 선언
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		
		//변신로봇이 가질 수 있는 상태의 개수
		int N = Integer.parseInt(br.readLine());
		
		//변신로봇의 상태를 저장할 배열
		String[] str = new String[N];
		
		//변신로봇의 상태를 입력 받음
		for(int i=0; i<N; i++) {
			str[i] = br.readLine();
			//동시에 어레이리스트도 초기화함
			graph.add(new ArrayList<Node>());
		}
		
		//시작 상태와 종료 상태를 입력받음
		String[] temp = br.readLine().split(" ");
		int s = Integer.parseInt(temp[0])-1;
		int e = Integer.parseInt(temp[1])-1;
		
		//모든 경우의 수에 대하여 간선 정보를 만들어 냄
		combinate(graph,str,new int[2],0,2);
		
		//다익스트라 알고리즘 수행 결과를 출력함
		bw.write(dijkstra(graph,str,s,e)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}