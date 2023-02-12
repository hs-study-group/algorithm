//https://www.acmicpc.net/problem/2644

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//정점 번호와 촌수를 저장한 노드 클래스 선언
class Node{
	int v, c;
	Node(int v, int c){
		this.v=v;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		//전체 사람 수를 입력 받음
		int N = Integer.parseInt(br.readLine());
		String[] temp = br.readLine().split(" ");
		
		//탐색을 시작할 정점과 종료할 정점을 입력 받음
		int S = Integer.parseInt(temp[0])-1;
		int E = Integer.parseInt(temp[1])-1;
		
		//간선의 개수를 입력 받음
		int M = Integer.parseInt(br.readLine());
		
		//간선 정보를 저장할 2차원 어레이리스트 선언
		ArrayList<ArrayList<Node>> g = new ArrayList<ArrayList<Node>>();
		
		//2차원 어레이리스트 초기화
		for(int i=0; i<N; i++) {
			g.add(new ArrayList<Node>());
		}
		
		//간선정보를 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			
			//간선 정보를 양방향으로 입력받고 저장함
			g.get(A).add(new Node(B,1));
			g.get(B).add(new Node(A,1));
		}
		
		//BFS탐색에 사용할 큐를 선언
		Queue<Node> q = new LinkedList<Node>();
		
		//정점 S부터 탐색할 수 있도록 큐에 추가함
		q.add(new Node(S,0));
		
		//정점 S부터 탐색을 시작하도록 방문처리함
		boolean[] v = new boolean[N];
		v[S] = true;
		
		//결과를 저장할 변수
		int answer = -1;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//만약 종료 정점에 도달했다면
			if(n.v == E) {
				//그때까지의 촌수를 결과 변수에 저장하고 반복을 종료함
				answer = n.c;
				break;
			}
			
			//현재 탐색의 기준이 되는 자신과 인접한 노드들을 탐색함
			for(int i=0; i<g.get(n.v).size(); i++) {
				//인접한 노드 정보를 얻음
				Node node = g.get(n.v).get(i);
				
				//인접한 정점에 방문한 적이 없다면
				if(!v[node.v]) {
					//방문처리하고, BFS탐색을 이어나가도록 큐에 추가함
					v[node.v]=true;
					q.add(new Node(node.v,n.c+node.c));
				}
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}