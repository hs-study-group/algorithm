//https://www.acmicpc.net/problem/6087

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//y, x좌표 및 방향, 거울설치 횟수 정보를 기록할 노드 클래스
class Node{
	int y, x, d, c;
	Node(int y, int x, int d, int c){
		this.y = y;
		this.x = x;
		this.d = d;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int BFS(char[][] map) {
		Node s = null, e = null;
		
		//전달받은 맵의 상태를 확인하여 출발지점 및 도착지점을 설정함
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				if(map[i][j]=='C') {
					//아직 출발지점을 설정하지 못했다면
					if(s==null) {
						//i, j좌표에서 시작하며, 방향은 정해지지 않은 4값으로, 거울을 설치한 횟수는 -1로 설정하여
						//첫 이동시에 방향이 달라서 거울을 설치했다고 처리하는 문제를 상쇄시킴
						s = new Node(i,j,4,-1);
					//아직 도착지점을 설정하지 못했다면
					}else if(e==null){
						//i, j좌표를 도착지점으로 설정함
						e = new Node(i,j,0,0);
					}
				}
			}
		}
		
		//방문배열은 각각 y, x좌표 및 방향정보를 차원으로 가지며, 저장되는 정수 값은 거울을 설치한 횟수를 의미함
		int[][][] v = new int[map.length][map[0].length][5];
		
		//방문 배열을 MAX_VALUE값으로 초기화 함
		for(int i=0; i<v.length; i++) {
			for(int j=0; j<v[0].length; j++) {
				for(int k=0; k<v[0][0].length; k++) {
					v[i][j][k] = Integer.MAX_VALUE;
				}
			}
		}
		
		//시작 위치는 거울을 -1개 설치했다고 처리함.
		v[s.y][s.x][s.d] = -1;
		
		//우선순위큐는 노드중에서 가장 적게 거울을 설치한 노드 정보가 먼저 반환되도록 함.
		//일반적인 큐가 아닌 우선순위 큐를 사용하는 이유는, 같은 방향으로 특정 위치에 도달했다 하더라도
		//더 적은 설치 횟수로 해당 위치에 재방문할 수 있으며, 우선순위 큐를 사용하지 않고 일반 큐를 사용하면
		//도착위치에 도달했을때, 그것이 최소의 거울 설치 횟수 정보를 가지고 있으리라 보장할 수 없기 때문임.
		PriorityQueue<Node> q = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.c<n2.c) {
					return -1;
				}else if(n1.c>n2.c) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//우선순위 큐에 시작 노드 정보를 추가함
		q.add(s);
		
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//도착위치에 도달했다면
			if(n.y==e.y&&n.x==e.x) {
				//거울 설치 횟수를 반환함
				return n.c;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//이동하고자 하는 위치가 맵의 범위를 벗어나지 않을때
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length) {
					//해당 위치가 벽이 아니라면
					if(map[y][x]!='*') {
						//만약 이동할 방향이 기존 방향과 다르고, 해당 위치에 해당 방향으로 도달한적이 없거나
						//기존에 해당 위치에 해당 방향으로 도달한 적이 있다 하더라도,
						//그때 기록한 거울 설치 횟수보다 더 적게 거울을 설치하여 도달할 수 있는 경우에
						if(n.d!=i&&v[y][x][i]>n.c+1) {
							//해당 위치에 방문하고, 그 노드 정보를 우선순위 큐에 추가함
							q.add(new Node(y,x,i,n.c+1));
							v[y][x][i] = n.c+1;
							
						//만약 이동할 방향이 기존과 같고, 해당 위치에 해당 방향으로 도달한 적이 없거나
						//기존에 해당 위치에 해당 방향으로 도달한 적이 있다 하더라도,
						//그때 기록한 거울 설치 횟수보다 더 적게 거울을 설치하여 도달할 수 있는 경우에
						}else if(n.d==i&&v[y][x][i]>n.c){
							//해당 위치에 방문하고, 그 노드 정보를 우선순위 큐에 추가함
							q.add(new Node(y,x,i,n.c));
							v[y][x][i] = n.c;
						}
						
					}
				}
			}
		}
		
		//반드시 레이저가 연결될 수 있는 입력만 주어지므로 의미는 없으나
		//반드시 어떤 값을 리턴해야하므로 임의 값을 아무렇게나 반환함
		return 0;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int W = Integer.parseInt(temp[0]);
		int H = Integer.parseInt(temp[1]);
		
		char[][] map = new char[H][W];
		
		//맵의 정보를 입력받음
		for(int i=0; i<map.length; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		bw.write(BFS(map)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}