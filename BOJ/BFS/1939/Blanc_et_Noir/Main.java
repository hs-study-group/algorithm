//https://www.acmicpc.net/problem/1939

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//정점과, 해당 정점에 도달하는데 사용한 비용을 저장할 클래스
class Node{
	int v, c;
	Node(int v, int c){
		this.v = v;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 간선정보를 바탕으로 s정점에서 e정점으로 이동할때 최소 mid이상의 가중치로만으로 이동할 수 있는지 없는지 확인하는 메소드
	public static boolean BFS(ArrayList<ArrayList<Node>> g, int s, int e, int mid) {
		Queue<Node> q = new LinkedList<Node>();
		q.add(new Node(s,0));
		
		//s정점은 이미 방문한 것으로 처리함
		//단순히 해당 정점에 도달할 수 있는지 없는지 정보만 저장하면 되므로 boolean으로 선언
		boolean[] v = new boolean[g.size()];
		v[s] = true;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//도착 정점에 도달할 수 있다면
			if(n.v == e) {
				//true를 리턴함
				return true;
			}
			
			//인접한 정점들을 탐색함
			for(int i=0; i<g.get(n.v).size(); i++) {
				//인접한 정점의 정보를 얻음
				Node node = g.get(n.v).get(i);
				
				//만약 인접한 정점을 아직 방문하지 않았고, 인접한 정점으로 이동하는데의 다리의 무게가 mid이상이라면
				if(!v[node.v] && node.c >= mid) {
					//이동할 수 있는 것으로 처리하고 노드 정보를 추가함
					q.add(new Node(node.v,0));
					
					//인접한 정점에 방문한 것으로 처리
					v[node.v] = true;
				}
			}
		}
		
		//중간에 true를 리턴하지 않았다면 도달하지 못함을 의미하므로 false를 리턴함
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//간선 정보를 저장할 2차원 어레이리스트
		ArrayList<ArrayList<Node>> g = new ArrayList<ArrayList<Node>>();
		
		//2차원 어레이리스트를 초기화함
		for(int i=0; i<N; i++) {
			g.add(new ArrayList<Node>());
		}
		
		//간선 정보를 양방향으로 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			g.get(A).add(new Node(B,C));
			g.get(B).add(new Node(A,C));
		}
		
		temp = br.readLine().split(" ");
		
		//시작 및 종료 정점의 정보를 얻음
		int S = Integer.parseInt(temp[0])-1;
		int E = Integer.parseInt(temp[1])-1;
		
		//매개변수 탐색에 사용할 min, max값을 정의
		int min = 1;
		int max = 1000000000;
		int answer = Integer.MIN_VALUE;
		
		//
		while(min<=max) {
			//mid값이란 해당 정점으로 이동하는데 여러 경로를 거치면서 종료 정점에 도달할 때 거쳐가는 다리가 버틸 수 있는 무게들의 최소값임
			int mid = (min+max)/2;
			
			//만약 종료 정점에 도달하는데, 거쳐가는 다리들이 버틸 수 있는 무게의 최소값이 mid라고 했을때
			//그러한 경로가 존재한다면
			if(BFS(g,S,E, mid)) {
				//mid값을 절반 증가시켜봄
				min = mid + 1;
				//그것을 정답으로 처리함
				answer = mid;
			//그러한 경로가 없다면
			}else {
				//기준치를 절반 낮춰봄
				max = mid - 1;
			}
			
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}