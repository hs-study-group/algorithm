//https://www.acmicpc.net/problem/10021

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//간선정보를 저장할 클래스 선언
class Edge{
	//두 정점의 번호와 둘 사이의 유클리디안 거리의 제곱 값을 저장함
	int v1, v2, c;
	Edge(int v1, int v2, int c){
		this.v1 = v1;
		this.v2 = v2;
		this.c = c;
	}
}

//정점의 좌표를 저장할 클래스 선언
class Node{
	int y, x;
	Node(int y, int x){
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	//크루스칼 알고리즘에 사용할 부모 정점 번호를 저장할 배열
	static int[] parent;
	
	//각 정점번호에 대한 좌표를 저장할 배열
	static Node[] vertex;
	
	//건설업자가 요구하는 최소 파이프 설치 비용
	static int C;
	
	//크루스칼 알고리즘에 사용할 우선순위 큐, 설치 비용이 가장 작은 간선정보가 먼저 반환됨
	static PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
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
	
	//두 정점에 대한 유클리디안 거리의 제곱 값을 계산하는 메소드
	public static int getCost(int v1, int v2) {
		Node n1 = vertex[v1];
		Node n2 = vertex[v2];
		return (n1.x-n2.x)*(n1.x-n2.x)+(n1.y-n2.y)*(n1.y-n2.y);
	}
	
	//N개의 정점중, 중복 없이 2개를 선택하는 메소드
	public static void combinate(int[] v, int idx, int c) {
		if(c==0) {
			//선택된 두 정점사이에 파이프를 설치할 때 필요한 비용을 계산함
			int cost = getCost(v[0],v[1]);
			
			//해당 비용이 C보다 크거나 같다면
			if(cost>=C) {
				//해당 파이프는 설치할 수 있음
				pq.add(new Edge(v[0],v[1],cost));
			}
			
			return;
		}else {
			//재귀적으로 2개의 정점을 선택함
			for(int i=idx; i<v.length; i++) {
				v[2-c] = i;
				combinate(v,i+1,c-1);
			}
			return;
		}
	}
	
	//어떤 정점에 대하여 최상위 부모를 얻는 메소드
	public static int find(int v) {
		//자기 자신이 최상위 부모인 경우
		if(parent[v]==v) {
			//자기 자신을 리턴함
			return v;
		//자기 자신이 최상위 부모가 아니라면
		}else {
			//자신의 부모에 대하여 부모를 재귀적으로 얻고
			//자신의 부모의 부모를 자신의 부모로 설정함
			//즉, 자신의 부모와 자기 자신은 최상위 부모가 같아지도록 함
			return parent[v] = find(parent[v]);
		}
	}
	
	//두 정점에 대하여 사이클을 이루고 있지 않다면 두 정점을 같은 그룹으로 묶는 메소드
	public static boolean union(int v1, int v2) {
		//두 정점의 최상위 부모를 얻음
		int p1 = find(v1);
		int p2 = find(v2);
		
		//두 정점의 최상위 부모가 같지 않다면 사이클을 형성하고 있지 않은 것임
		if(p1!=p2) {
			//두 부모중 더 작은 값을 갖는 부모가 더 큰 값을 갖는 부모의 부모가 되도록 함
			if(p1<p2) {
				parent[p2] = p1;
			}else {
				parent[p1] = p2;
			}
			
			//사이클을 형성하지 않으므로 해당 간선은 선택 가능함
			return true;
		//사이클을 형성하고 있으므로 해당 간선을 선택하지 않음
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//정점의 개수를 입력 받음
		int N = Integer.parseInt(temp[0]);
		
		//건설업자가 요구하는 최소 설치 비용을 입력 받음
		C = Integer.parseInt(temp[1]);
		
		//부모 배열을 초기화 하는데, 처음에는 자기 자신이 부모가 되도록 함
		parent = new int[N];
		for(int i=0; i<parent.length; i++) {
			parent[i] = i;
		}
		
		//각 정점에 대한 좌표를 입력 받음
		vertex = new Node[N];	
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			int x = Integer.parseInt(temp[0]);
			int y = Integer.parseInt(temp[1]);
			
			vertex[i] = new Node(y,x);
		}
		
		//N개의 정점중 2개를 선택하고 그 정점간의 유클리디안 거리의 제곱값을 구하고
		//그 값이 C이상이라면 해당 간선 정보를 우선순위 큐에 추가함
		combinate(new int[N], 0, 2);
		
		//선택된 간선의 개수
		int K = 0;
		
		//선택된 간선의 가중치 합
		int S = 0;
		
		//우선순위 큐가 비어있지 않다면
		while(!pq.isEmpty()) {
			//간선 정보를 하나 얻음
			Edge e = pq.poll();
			
			//해당 간선을 선택하더라도 사이클이 형성되지 않는 경우
			if(union(e.v1,e.v2)) {
				//간선을 선택함
				K++;
				
				//간선 가중치를 누적하여 더함
				S += e.c;
			}
			
			//N-1개의 간선이 선택되었다면 즉시 종료함
			if(K==N-1) {
				break;
			}
		}
		
		//만약 N-1개의 간선을 모두 선택했기 때문에 while문을 빠져 나온것이라면
		if(K==N-1) {
			//최소 비용으로 모든 정점을 연결할 수 있는 것이므로 그 가중치 합을 출력함
			bw.write(S+"\n");
		//만약 N-1개를 아직 선택하지 않았는데 while문을 빠져 나온것이라면
		}else {
			//최소 비용으로 모든 정점을 연결할 방법이 없으므로 -1을 출력함
			bw.write("-1\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}