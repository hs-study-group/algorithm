//https://www.acmicpc.net/problem/1197

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//크루스칼 알고리즘에 사용할 간선 클래스
class Edge{
	//간선에 포함되는 두 개의 정점과 간선 비용을 저장함
	int v1, v2, c;
	Edge(int v1, int v2, int c){
		this.v1 = v1;
		this.v2 = v2;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//특정 정점에 대하여 최상위 부모를 반환하는 메소드
	public static int find(int[] parent, int i) {
		//자기 자신이 최상위 부모라면
		if(parent[i]==i) {
			//자기 자신을 리턴함
			return i;
		//아니라면
		}else {
			//자신의 바로 윗 단계의 부모에 대하여 재귀적으로 부모를 구함
			return find(parent,parent[i]);
		}
	}
	
	//두 정점의 최상위 부모가 같으면 false, 다르면 true를 리턴하는 메소드
	//이때, 최상위 부모가 다르다면, 서로 같은 최상위 부모를 갖도록 처리함
	public static boolean union(int[] parent, int a, int b) {
		//정점 a, b에 대하여 최상위 부모를 각각 얻음
		int pa = find(parent,a);
		int pb = find(parent,b);
		
		//최상위 부모가 다르다면, 사이클이 형성되지 않은 것임
		//즉, 최소신장트리를 구성하는데 해당 a - b간 이어진 간선을 사용할 수 있음
		if(pa!=pb) {
			//두 정점의 최상위 부모가 다를때, 두 정점 모두 최상위 부모를 같은 것으로 지정해야 하는데
			//번호가 더 작은 정점이 기준이 될 최상위 부모임
			
			//a의 최상위 부모의 번호가 b의 최상위 부모의 번호보다 작다면
			if(pa<pb) {
				//b의 최상위 부모의 최상위 부모는 a의 최상위 부모가 됨
				parent[pb] = pa;
			//b의 최상위 부모의 번호가 a의 최상위 부모의 번호보다 작다면
			}else{
				//a의 최상위 부모의 최상위 부모는 b의 최상위 부모가 됨
				parent[pa] = pb;
			}
			return true;
		//최상위 부모가 다르다면 사이클이 형성된 것임
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int V = Integer.parseInt(temp[0]);
		int E = Integer.parseInt(temp[1]);
		
		int[] parent = new int[V];
		
		for(int i=0; i<parent.length; i++) {
			parent[i] = i;
		}
		
		//크루스칼 알고리즘은 비용이 가장 적은 간선을 선택하고
		//기존에 선택된 간선을 모아 사이클이 형성되지 않으면 비용이 적은 다음 간선을 선택하고
		//사이클이 형성된다면 다른 비용이 적은 간선을 선택하도록 하여 최소비용신장트리를 구성하는 방식임
		
		//즉, 비용이 가장 적은 간선 정보가 먼저 반환되도록 함
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				if(e1.c<e2.c) {
					return -1;
				}else if(e1.c>e2.c) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//간선 정보를 입력받아 우선순위 큐에 추가함
		for(int i=0; i<E; i++) {
			temp = br.readLine().split(" ");
			int v1 = Integer.parseInt(temp[0])-1;
			int v2 = Integer.parseInt(temp[1])-1;
			int c = Integer.parseInt(temp[2]);
			
			pq.add(new Edge(v1,v2,c));
		}
		
		//선택된 간선의 개수를 저장할 변수
		int cnt = 0;
		
		//최소비용 신장트리의 가중치 합을 저장할 변수
		int total = 0;
		
		while(!pq.isEmpty()) {
			//비용이 가장 적은 간선 정보를 얻음
			Edge e = pq.poll();
			
			//두 정점의 최상위 부모가 같지 않으면 사이클이 형성되지 않은 것이므로
			//해당 간선을 최소비용 신장트리 구성에 사용하며, v1, v2 정점의 최상위 부모중
			//번호가 더 작은 최상위 부모를 기준으로 최상위 부모를 통합함
			if(union(parent,e.v1,e.v2)) {
				//선택된 간선의 개수를 1증가시킴
				cnt++;
				
				//최소비용 신장트리의 가중치 합을 증가시킴
				total+=e.c;
			}
			
			//간선이 정점의 개수 - 1만큼 선택 되었다면
			//더이상 간선을 선택할 필요가 없음
			if(cnt==V-1) {
				break;
			}
		}
		
		bw.write(total+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}