import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//해당 인덱스까지 오는데 소요한 비용을 저장하는 노드 클래스
class Node{
	int index, dist;
	Node(int index, int dist){
		this.index = index;
		this.dist = dist;
	}
}

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static final int INF = Integer.MAX_VALUE;
	public static int N,M,X;
	public static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
	
	public static int dijkstra(int s, int e) {
		int[] d;
		
		//우선순위큐중에서도 힙을 사용하여야함
		//비용이 가장 적은 노드가 우선적으로 반환됨
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.dist<o2.dist) {
					return -1;
				}else if(o1.dist>o2.dist) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//최단거리 비용 정보를 저장할 배열
		d = new int[N];
		
		//최단거리 비용 정보를 저장할 배열을 모두 INF값으로 채움
		Arrays.fill(d, INF);
		
		//시작하는 위치는 비용이 0이 되도록 설정후 우선순위큐에 추가함
		d[s] = 0;
		pq.add(new Node(s,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			int now = n.index;
			
			//해당 인덱스까지 오는데 소요된 비용이 최단 비용을 넘어서면 더이상 탐색할 필요 없음
			//이때, 굳이 방문처리를 하지않아도 되는 장점이 있음
			if(d[now]<n.dist) {
				continue;
			}
			
			//인접한노드에 대하여 탐색을 실시함
			for(int i=0; i<graph.get(now).size(); i++) {
				int next = graph.get(now).get(i).index;
				
				//현재 인덱스까지 오는데 필요한 최단 비용 + 그 인덱스에서 인접한 위치로 이동할때 필요한 비용을 계산함
				int cost = d[now] + graph.get(now).get(i).dist;
				
				//만약 앞서 계산한 비용이, 인접한 노드로 이동할때의 최단비용보다 적다면
				if(cost < d[next]) {
					//최단비용을 앞서 계산한 cost로 변경하고, 인접노드에서 다시 탐색을 수행하도록 우선순위큐에 추가함
					d[next] = cost;
					pq.add(new Node(next,cost));
				}
			}
		}
		//최단비용을 반환함
		return d[e];
	}
	
	public static void main(String[] args) throws Exception{
		int max = Integer.MIN_VALUE;
		String[] str = br.readLine().split(" ");
		N = Integer.parseInt(str[0]);
		M = Integer.parseInt(str[1]);
		X = Integer.parseInt(str[2])-1;

		//N개의 정점이 존재하므로 N개의 리스트를 저장해야함
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//간선정보를 그래프에 추가함
		for(int i=0; i<M; i++) {
			str = br.readLine().split(" ");
			int A = Integer.parseInt(str[0])-1;
			int B = Integer.parseInt(str[1])-1;
			int T = Integer.parseInt(str[2]);
			graph.get(A).add(new Node(B,T));
		}
		
		//i에서 X로 갔다가, X에서 i로 오는 비용을 계산함
		//단방향 그래프이므로 단순히 i에서 X로 가는 비용을 2배하여서는 구할 수 없음
		for(int i=0; i<N; i++) {
			int r = dijkstra(i,X)+dijkstra(X,i);
			if(r>max) {
				max = r;
			}
		}
		
		//가장 오랜 시간이 걸리는 사람의 시간을 출력함
		bw.write(max+"\n");
		bw.flush();
	}
}