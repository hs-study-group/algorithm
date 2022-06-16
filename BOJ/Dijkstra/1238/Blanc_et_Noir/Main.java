import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//�ش� �ε������� ���µ� �ҿ��� ����� �����ϴ� ��� Ŭ����
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
		
		//�켱����ť�߿����� ���� ����Ͽ�����
		//����� ���� ���� ��尡 �켱������ ��ȯ��
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
		
		//�ִܰŸ� ��� ������ ������ �迭
		d = new int[N];
		
		//�ִܰŸ� ��� ������ ������ �迭�� ��� INF������ ä��
		Arrays.fill(d, INF);
		
		//�����ϴ� ��ġ�� ����� 0�� �ǵ��� ������ �켱����ť�� �߰���
		d[s] = 0;
		pq.add(new Node(s,0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			int now = n.index;
			
			//�ش� �ε������� ���µ� �ҿ�� ����� �ִ� ����� �Ѿ�� ���̻� Ž���� �ʿ� ����
			//�̶�, ���� �湮ó���� �����ʾƵ� �Ǵ� ������ ����
			if(d[now]<n.dist) {
				continue;
			}
			
			//�����ѳ�忡 ���Ͽ� Ž���� �ǽ���
			for(int i=0; i<graph.get(now).size(); i++) {
				int next = graph.get(now).get(i).index;
				
				//���� �ε������� ���µ� �ʿ��� �ִ� ��� + �� �ε������� ������ ��ġ�� �̵��Ҷ� �ʿ��� ����� �����
				int cost = d[now] + graph.get(now).get(i).dist;
				
				//���� �ռ� ����� �����, ������ ���� �̵��Ҷ��� �ִܺ�뺸�� ���ٸ�
				if(cost < d[next]) {
					//�ִܺ���� �ռ� ����� cost�� �����ϰ�, ������忡�� �ٽ� Ž���� �����ϵ��� �켱����ť�� �߰���
					d[next] = cost;
					pq.add(new Node(next,cost));
				}
			}
		}
		//�ִܺ���� ��ȯ��
		return d[e];
	}
	
	public static void main(String[] args) throws Exception{
		int max = Integer.MIN_VALUE;
		String[] str = br.readLine().split(" ");
		N = Integer.parseInt(str[0]);
		M = Integer.parseInt(str[1]);
		X = Integer.parseInt(str[2])-1;

		//N���� ������ �����ϹǷ� N���� ����Ʈ�� �����ؾ���
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//���������� �׷����� �߰���
		for(int i=0; i<M; i++) {
			str = br.readLine().split(" ");
			int A = Integer.parseInt(str[0])-1;
			int B = Integer.parseInt(str[1])-1;
			int T = Integer.parseInt(str[2]);
			graph.get(A).add(new Node(B,T));
		}
		
		//i���� X�� ���ٰ�, X���� i�� ���� ����� �����
		//�ܹ��� �׷����̹Ƿ� �ܼ��� i���� X�� ���� ����� 2���Ͽ����� ���� �� ����
		for(int i=0; i<N; i++) {
			int r = dijkstra(i,X)+dijkstra(X,i);
			if(r>max) {
				max = r;
			}
		}
		
		//���� ���� �ð��� �ɸ��� ����� �ð��� �����
		bw.write(max+"\n");
		bw.flush();
	}
}