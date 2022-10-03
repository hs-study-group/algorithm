//https://www.acmicpc.net/problem/4386

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

//간선의 비용과, 해당 간선에 포함되는 두 정점의 번호를 저장할 클래스
class Edge{
	int v1, v2;
	double d;
	Edge(int v1, int v2, double d){
		this.v1 = v1;
		this.v2 = v2;
		this.d = d;
	}
}

//특정 별의 y, x좌표를 저장할 클래스
class Star{
	double y, x;
	Star(double y, double x){
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//별들의 위치정보를 저장할 어레이리스트 선언
	static ArrayList<Star> stars = new ArrayList<Star>();
	
	//비용이 적은 간선정보가 먼저 반환되도록 하는 우선순위큐 선언
	static PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
		@Override
		public int compare(Edge e1, Edge e2) {
			if(e1.d < e2.d) {
				return -1;
			}else if(e1.d>e2.d) {
				return 1;
			}else {
				return 0;
			}
		}
	});
	
	//두 별의 거리를 반환하는 메소드
	public static double getDistance(Star s1, Star s2) {
		double dx = (s1.x-s2.x);
		double dy = (s1.y-s2.y);
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	//중복없이 2개의 별을 선택하는 조합을 구하는 메소드
	public static void combinate(int[] result, boolean[] v, int idx, int cnt) {
		//2개의 별을 모두 선택했다면
		if(cnt==0) {
			//해당 별 2개의 정보를 얻음
			Star s1 = stars.get(result[0]);
			Star s2 = stars.get(result[1]);
			
			//두 별 사이의 거리를 구함
			double c = getDistance(s1,s2);
			
			//두 별 사이에 존재하는 간선의 정보를 우선순위 큐에 추가함
			pq.add(new Edge(result[0],result[1],c));
		}else {
			for(int i=idx; i<v.length; i++) {
				//아직 해당 별을 선택한 적이 없으면
				if(!v[i]) {
					//해당 별을 선택한 것으로 처리함
					v[i]=true;
					
					//해당 별을 선택했음을 기록함
					result[idx] = i;
					
					//재귀적으로 조합을 구함
					combinate(result,v,idx+1, cnt-1);
					
					//해당 별을 선택하지 않은 것으로 처리함
					v[i] = false;
				}
			}
		}
	}
	
	//해당 정점의 최상위 부모를 반환하는 메소드
	public static int find(int[] parent, int i) {
		if(parent[i]==i) {
			return i;
		}else {
			return find(parent,parent[i]);
		}
	}
	
	//두 정점의 최상위 부모를 하나로 통합하는 메소드
	public static boolean union(int[] parent, int v1, int v2) {
		//두 정점의 최상위 부모를 얻음
		int pv1 = find(parent,v1);
		int pv2 = find(parent,v2);
		
		//만약 두 정점의 최상위 부모가 다르다면
		//사이클을 형성하지 않았으므로 해당 간선을 선택함
		if(pv1!=pv2) {
			//두 최상위 부모중 번호가 더 작은 최상위 부모를 기준으로 통합함
			if(pv1<=pv2) {
				parent[pv2] = pv1;
			}else {
				parent[pv1] = pv2;
			}
			return true;
		//만약 두 정점의 최상위 부모가 같다면
		//사이클을 형성한 것이므로 해당 간선을 선택하지 않음
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);

		//어떤 정점에 대하여 부모 정점 번호를 저장할 배열
		int[] parent = new int[N];
		
		//초기에는 자기 자신을 부모로 설정함
		for(int i=0; i<parent.length; i++) {
			parent[i] = i;
		}
		
		//별의 좌표를 입력받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			double x = Double.parseDouble(temp[0]);
			double y = Double.parseDouble(temp[1]);
			stars.add(new Star(y,x));
		}
		
		//선택된 간선의 개수를 저장할 변수
		int cnt = 0;
		
		//선택된 간선의 가중치 합을 저장할 변수
		double sum = 0;
		
		//모든 별에 대하여 중복없이 모든 가능한 간선을 우선순위 큐에 추가함
		combinate(new int[2], new boolean[N],0,2);
		
		while(!pq.isEmpty()) {
			Edge e = pq.poll();
			
			//해당 간선을 선택해도 사이클이 형성되지 않는다면
			if(union(parent,e.v1,e.v2)) {
				//해당 간선을 선택한 것으로 처리하고 가중치합과 선택된 간선의 개수를 갱신함
				sum += e.d;
				cnt++;
			}
			
			//간선을 N-1개 선택했다면 더이상 간선을 선택할 필요가 없음
			if(cnt==N-1) {
				break;
			}
		}
		
		//최소비용 스패닝트리의 가중치 합을 출력함
		bw.write(sum+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}