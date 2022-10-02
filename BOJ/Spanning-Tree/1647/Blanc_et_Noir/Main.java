//https://www.acmicpc.net/problem/1647

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//두 정점 v1, v2간에 존재하는 간선의 비용 정보를 저장할 클래스
class Edge{
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

	//특정 정점 i에 대하여 최상위 부모가 되는 정점을 반환하는 메소드
	public static int find(int[] parent, int i) {
		if(parent[i]==i) {
			return i;
		}else {
			return find(parent,parent[i]);
		}
	}
	
	//두 정점의 부모가 같은지 다른지 판단하여, 같다면 사이클이 형성된 것으로 간주하고
	//같지 않다면 아닌 것으로 판단하여 두 정점의 최상위 부모간 부모관계를 설정하는 메소드
	public static boolean union(int[] parent, int v1, int v2) {
		//두 정점의 최상위 부모를 얻음
		int pv1 = find(parent,v1);
		int pv2 = find(parent,v2);
		
		//두 최상위 부모가 동일하지 않다면 사이클이 형성되지 않은 것임
		if(pv1!=pv2) {
			//두 최상위 부모중 더 작은 정점이 더 큰 정점의 부모가 되도록 함
			if(pv1<=pv2) {
				parent[pv2] = pv1;
			}else {
				parent[pv1] = pv2;
			}
			
			//사이클이 형성되지 않으므로 해당 간선을 선택함
			return true;
		//사이클이 형성되었다면
		}else {
			//해당 간선을 선택하지 않음
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[] parent = new int[N];
		for(int i=0; i<parent.length; i++) {
			parent[i] = i;
		}
		
		//간선정보를 비용이 적은 순으로 출력하는 우선순위 큐
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
		
		//간선의 정보를 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int v1 = Integer.parseInt(temp[0])-1;
			int v2 = Integer.parseInt(temp[1])-1;
			int c = Integer.parseInt(temp[2]);
			
			//간선정보를 우선순위 큐에 추가함
			pq.add(new Edge(v1,v2,c));
		}
		
		//선택된 간선의 개수를 저장할 변수
		int cnt = 0;
		
		//최소비용 신장트리의 가중치 합을 저장할 변수
		int total = 0;
		
		//우선순위 큐가 비어있지 않다면
		while(!pq.isEmpty()) {
			//간선 정보를 얻음
			Edge e = pq.poll();
			
			//해당 간선을 선택했을때 사이클이 형성되지 않는다면
			if(union(parent,e.v1,e.v2)) {
				//가중치 합을 증가시킴
				total += e.c;
				
				//선택된 간선의 수를 증가시킴
				cnt++;
			}
			
			//간선이 정점의 개수 - 1 만큼 선택되었다면
			if(cnt==N-1) {
				//최소비용 신장트리가 완성된 것이며
				//여기서 가장 마지막에 추가했던 간선의 비용이 선택된 간선중에서는 가장 큰 비용을 갖고 있으므로
				//해당 간선의 비용을 역으로 빼서 최소비용 신장트리를 2개를 구성한 것처럼 처리함
				total -= e.c;
				break;
			}
		}
		
		bw.write(total+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}