//https://www.acmicpc.net/problem/15591

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//어떤 정점과 해당 정점으로의 USADO를 저장할 노드 클래스 선언
class Node{
	int v, u;
	
	Node(int v, int u){
		this.v = v;
		this.u = u;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 간선 정보를 바탕으로, s라는 동영상을 시청할때 USADO가 K이상인 영상이 몇 개 추천되는지를 반환함
	public static int BFS(ArrayList<ArrayList<Node>> graph, int k, int s) {
		int cnt = 0;
		boolean[] v = new boolean[graph.size()];
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s);
		v[s] = true;
		
		while(!q.isEmpty()) {
			//현재 동영상부터 탐색함
			int c = q.poll();
			
			for(int i=0; i<graph.get(c).size(); i++) {
				//인접한 동영상의 번호와 USADO를 얻음
				int n = graph.get(c).get(i).v;
				int u = graph.get(c).get(i).u;
				
				//만약 아직 방문하지 않은 동영상이고, USADO가 K이상이면
				if(!v[n]&&u>=k) {
					//해당 동영상으로 탐색을 이어나감
					q.add(n);
					v[n]=true;
					cnt++;
				}
			}
		}
		
		//추천된 동영상의 개수를 리턴함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int Q = Integer.parseInt(temp[1]);
		
		//간선정보를 저장할 2차원 어레이리스트 선언
		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
		
		//2차원 어레이리스트 초기화
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		for(int i=0; i<N-1; i++) {
			temp = br.readLine().split(" ");
			
			//간선정보를 입력받고 이것을 양방향 그래프로 설정함
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			graph.get(A).add(new Node(B,C));
			graph.get(B).add(new Node(A,C));
		}
		
		for(int i=0; i<Q; i++) {
			temp = br.readLine().split(" ");
			
			//s 동영상을 시청할 때 USADO가 k이상인 동영상이 몇 개 추천되는지 BFS탐색을 수행함
			int k = Integer.parseInt(temp[0]);
			int s = Integer.parseInt(temp[1])-1;
			
			bw.write(BFS(graph,k,s)+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}