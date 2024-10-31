/* https://www.acmicpc.net/problem/1197 */

import java.io.*;
import java.util.*;

/* 간선 정보를 저장할 클래스, v1, v2 정점 및 가중치 c를 관리 */
class Edge {
	int v1, v2, c;

	Edge(int v1, int v2, int c){
		this.v1 = v1;
		this.v2 = v2;
		this.c = c;
	}
}

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static int V, E, K, S;
	public static int[] parents;

	/* 최상위 부모 정점을 찾는 메소드 */
	public static int find(int v){
		if(parents[v] == v){
			return v;
		}else{
			return parents[v] = find(parents[v]);
		}
	}

	/* 해당 간선을 선택해도 사이클이 형성되지 않는지 판단하는 메소드 */
	public static boolean union(Edge e){
		int p1 = find(e.v1);
		int p2 = find(e.v2);

		/* 두 정점의 최상위 부모가 같다면, 사이클이 형성되므로 해당 간선을 선택하지 않음 */
		if(p1==p2){
			return false;
		}

		/* 둘 중에 더 작은 최상위 부모가, 다른 최상위 부모의 부모가 되도록 함 */
		if(p1<p2){
			parents[p2] = p1;
		}else{
			parents[p1] = p2;
		}

		return true;
	}

	public static void main(String[] args) throws IOException {
		String[] input = br.readLine().split(" ");

		V = Integer.parseInt(input[0]);
		E = Integer.parseInt(input[1]);

		/* 최상위 부모를 저장할 배열 선언 및 초기화 */
		parents = new int[V];

		for(int i=0; i<V; i++){
			parents[i] = i;
		}

		/* 비용이 더 적은 간선 정보를 먼저 리턴하는 우선순위 큐 선언 */
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.c-e2.c;
			}
		});

		for(int i=0; i<E; i++){
			input = br.readLine().split(" ");

			int v1 = Integer.parseInt(input[0])-1;
			int v2 = Integer.parseInt(input[1])-1;
			int c = Integer.parseInt(input[2]);

			pq.add(new Edge(v1, v2, c));
		}

		while(!pq.isEmpty()){
			Edge e = pq.poll();

			/* 해당 간선을 선택했을때, 사이클이 형성되지 않으면 해당 간선 선택을 확정함 */
			if(union(e)){
				/* 가중치 합을 증가시킴 */
				S += e.c;
				
				/* 선택된 간선의 개수를 1 증가시킴 */
				K++;
			}

			/* 정점이 V개라면, V-1개의 간선이 선택된 즉시 스패닝 트리가 완성되어있음 */
			if(K==V-1){
				bw.write(S+"\n");
				break;
			}
		}

		bw.flush();
		bw.close();
		br.close();
	}
}