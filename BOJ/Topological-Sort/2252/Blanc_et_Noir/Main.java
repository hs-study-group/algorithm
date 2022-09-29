//https://www.acmicpc.net/problem/2252

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//간선정보 및 진입차수를 바탕으로 위상정렬을 수행하는 메소드
	public static String topologySort(ArrayList<ArrayList<Integer>> list, int[] indegree) {
		Queue<Integer> q = new LinkedList<Integer>();
		StringBuilder sb = new StringBuilder();
		
		//진입차수가 0인 정점을 큐에 추가함.
		//즉, 자신에게 올 수 있는 다른 정점의 수가 0인 정점을 큐에 추가함.
		//자신에게 올 수 있는 다른 정점이 없는 경우는, 해당 정점부터 방문하지 않으면
		//영원히 해당 정점을 방문할 수 없기 때문임.
		for(int i=0; i<indegree.length; i++) {
			if(indegree[i]==0) {
				q.add(i);
			}
		}
		
		while(!q.isEmpty()) {
			//정점 A를 얻음
			int A = q.poll();
			
			//정점 A를 출력함
			sb.append((A+1)+" ");
			
			//자신과 인접한 정점에 대하여
			for(int i=0; i<list.get(A).size(); i++) {
				//자신과 인점한 정점을 얻음
				int B = list.get(A).get(i);
				
				//정점 A를 탐색했으므로, 정점 A를 통해서 갈 수 있는 정점 B의 경우
				//진입차수를 1 감소시켜야함. 왜냐하면 B의 입장에서 자신에게 진입하는 A 정점이 사라졌기 때문
				indegree[B] -= 1;
				
				//진입 차수가 0가 되는 순간
				if(indegree[B]==0) {
					//해당 정점을 큐에 추가함
					q.add(B);
				}
			}
		}
		
		//정점 방문 순서를 반환함
		return sb.toString().trim();
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//간선정보를 리스트로 저장하기 위해 이중 리스트를 선언함
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		
		//이중 리스트를 초기화함
		for(int i=0; i<N; i++) {
			list.add(new ArrayList<Integer>());
		}
		
		//진입차수를 저장할 배열을 선언함
		int[] indegree = new int[N];
		
		//간선정보를 탐색함
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			
			//간선 정보의 A, B에 대하여
			int A = Integer.parseInt(temp[0]);
			int B = Integer.parseInt(temp[1]);
			
			//B의 진입차수를 1 증가시킴
			indegree[B-1] += 1;
			
			//이중 리스트에 간선 정보를 추가함.
			list.get(A-1).add(B-1);
		}
		
		//위상정렬을 수행한 결과인 정점 방문 순서를 출력함
		bw.write(topologySort(list,indegree));
		bw.flush();
		bw.close();
		br.close();
	}
}