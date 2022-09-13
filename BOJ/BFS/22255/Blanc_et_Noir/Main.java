//https://www.acmicpc.net/problem/22255

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//특정 위치에서의 정보를 저장할 노드 클래스
class Node{
	//각각 y, x좌표, 충격량, 어떤방향으로 이동해야할지 저장할 변수를 나타냄
	int y, x, c, k;
	Node(int y, int x, int c, int k){
		this.y = y;
		this.x = x;
		this.c = c;
		this.k = k;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int BFS(int[][] map, int SX, int SY, int EX, int EY) {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//방향을 굳이 고려하지 않아도 되는 이유는 우선순위 큐를 활용하여 BFS탐색을 수행하고
		//충격량이 모두 양수이므로 멀리 돌아서 해당위치에 도달하는 경우보다 지금 비용으로 도착했을때가
		//가장 최소일 것이라 확신할 수 있기 때문임
		boolean[][][] v = new boolean[map.length][map[0].length][3];
		
		//우선순위큐를 선언, 노드의 충격량이 가장 적은 노드가 먼저 반환됨
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.c < n2.c) {
					return -1;
				}else if(n1.c > n2.c) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//시작위치를 방문처리함
		v[SY][SX][0] = true;
		
		//우선순위큐에 노드를 추가함
		pq.add(new Node(SY, SX, 0, 0));
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();

			//도착위치에 도착했다면 BFS 탐색을 종료하고, 충격량을 반환함
			//해당 충격량이 최소임은 우선순위 큐를 통해 보장할 수 있음
			if(n.y == EY && n.x == EX) {
				return n.c;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//다음에 이동할 위치가 맵을 벗어나지 않고, 벽이 아니라면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&map[y][x]!=-1) {
					//3k번째 이동이거나
					//3k+1 번째 이동이면서 상, 하 이동이거나
					//3k+2 번째 이동이면서 좌, 우 이동인경우에만 이동 가능
					
					//여기서 중요한 것은 처음 이동할때는 0번째 이동이 아닌, 1번째 이동으로 취급한다는 것임
					//즉, 처음 이동은 3k+1에 해당하기때문에, n.k를 3으로 나눈 나머지가 0이어야 함
					if(n.k%3==2||(n.k%3==0&&(i==0||i==1))||(n.k%3==1&&(i==2||i==3))){
						//해당 위치에 방문한 적이 없다면
						if(!v[y][x][(n.k+1)%3]) {
							//우선순위 큐에 다음 위치로의 이동 정보를 노드로 추가함
							pq.add(new Node(y,x,n.c+map[y][x],(n.k+1)%3));
							
							//해당 위치에 방문 한 것으로 처리함
							v[y][x][(n.k+1)%3] = true;
						}
					}
				}
			}
		}
		
		//목적지에 도달할 수 없으므로 -1을 반환함
		return -1;
	}
	
	public static void main(String[] args) throws Exception{
		//맵의 크기를 입력받음
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[][] map = new int[N][M];
		
		//시작위치와 종료위치를 입력받음
		temp = br.readLine().split(" ");
		
		//여기서 중요한 것은 일반적인 문제와는 달리 행, 열의 순서가 아니라
		//열, 행의 순서로 좌표를 전달하고 있다는 사실임
		//즉 이 문제에서 x좌표는 열이 아니라 행을 의미하고
		//y좌표는 행이 아니라 열을 의미하므로 보통의 문제와는 달리 역순으로 입력받아야함
		int SX = Integer.parseInt(temp[1])-1;
		int SY = Integer.parseInt(temp[0])-1;
		int EX = Integer.parseInt(temp[3])-1;
		int EY = Integer.parseInt(temp[2])-1;
		
		//지도 정보를 입력받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		bw.write(BFS(map,SX,SY,EX,EY)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}