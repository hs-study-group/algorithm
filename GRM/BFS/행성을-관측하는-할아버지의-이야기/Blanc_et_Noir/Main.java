import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 간선정보를 토대로 vertex에서 BFS탐색을 수행했을때 방문하게되는 정점의 수를 구하는 메소드
	public static int BFS(ArrayList<ArrayList<Integer>> graph, int vertex){
		int cnt = 0;
		
		//동일한 정점으로 이동하는 경로가 여럿 중복해서 존재할 수 있어서
		//반드시 방문처리를 해야함
		boolean[] visit = new boolean[graph.size()];
		
		//BFS탐색에 사용할 큐 선언
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(vertex);
		
		while(!q.isEmpty()){
			Integer v = q.poll();
			
			//인접한 정점을 탐색함
			for(int i=0;i<graph.get(v).size();i++){
				int n = graph.get(v).get(i);
				//해당 인접한 정점을 아직 방문하지 않앗따면
				if(!visit[n]){
					//해당 정점을 방문처리함
					visit[n]=true;
					q.add(n);
					//방문한 정점의 수를 1증가시킴
					cnt++;					
				}
			}
		}
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		
		String[] temp = br.readLine().split(" ");
		final int N = Integer.parseInt(temp[0]);
		final int M = Integer.parseInt(temp[1]);
		
		//graph1과 graph2는 각각 두 정점을 서로 다른 방향으로 연결
		ArrayList<ArrayList<Integer>> graph1 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> graph2 = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0;i<N;i++){
			graph1.add(new ArrayList<Integer>());
			graph2.add(new ArrayList<Integer>());
		}
		
		//간선 정보를 입력 받음
		for(int i=0; i<M;i++){
			temp = br.readLine().split(" ");
			int v1 = Integer.parseInt(temp[0])-1;
			int v2 = Integer.parseInt(temp[1])-1;
			graph1.get(v1).add(v2);
			graph2.get(v2).add(v1);
		}
		
		//graph2에는 자신보다 큰 행성으로 이동할 수 있는 경로가,
		//graph1에는 자신보다 작은 행성으로 이동할 수 있는 경로가 저장됨
		//그 간선정보를 바탕으로 방문할 수 있는 정점의 개수를 구함
		for(int i=0;i<N;i++){
			bw.write(BFS(graph2,i)+" "+BFS(graph1,i)+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}