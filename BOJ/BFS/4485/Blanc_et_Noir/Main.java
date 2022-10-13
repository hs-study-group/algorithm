//https://www.acmicpc.net/problem/4485

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node{
	int y, x, c;
	Node(int y, int x, int c){
		this.y = y;
		this.x = x;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static int BFS(int[][] map) {
		int result = 0;
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//방문배열은 방향을 고려하지 않아도 되므로 2차원으로 선언하되, boolean이 아닌 int 배열을 사용함
		//왜냐하면, 이미 방문한 위치라 하더라도, 다른 루트를 통해 해당위치에 도달했을때 누적하여 사용한 비용이
		//기존에 도착했을때 보다 적은 값이라면, 재방문이 가능해야 하기 때문임
		int[][] v = new int[map.length][map[0].length];
		
		//방문 배열을 MAX_VALUE로 초기화 함
		for(int i=0; i<v.length; i++) {
			for(int j=0; j<v[0].length; j++) {
				v[i][j] = Integer.MAX_VALUE;
			}
		}
		
		//우선순위 큐를 활용하여 BFS탐색을 수행하며, 해당 큐는 사용한 비용이 가장 적은 노드가 먼저 반환되도록 함 
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
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
		
		//우선순위 큐에 시작 노드정보를 추가함, 초기 비용은 0, 0 위치의 비용임
		pq.add(new Node(0,0,map[0][0]));
		
		//해당 위치에 방문한 적이 있음을 표시하되, 여태까지 사용한 비용인 초기비용을 저장함
		v[0][0] = map[0][0];
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//도착위치에 도달했다면, 이는 우선순위 큐 및 방문배열에 의해 반드시 가장 적은 값을 가지고 있음을 보장할 수 있음
			if(n.y==map.length-1&&n.x==map[0].length-1) {
				//사용한 비용을 result 변수에 대입함
				result = n.c;
				
				//BFS 탐색을 종료함
				break;
			}
			
			//도착위치가 아니라면, 4방향을 탐색함
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//다음에 이동할 위치가 맵의 범위를 벗어나지 않고, 기존에 방문했던 비용보다 더 적은 비용으로 재방문할 수 있다면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&v[y][x]>n.c+map[y][x]) {
					//해당 위치를 재방문함
					pq.add(new Node(y,x,n.c+map[y][x]));
					
					//해당 위치에 방문한 비용을 더 적은 비용으로 갱신함
					v[y][x] = n.c+map[y][x];
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		int idx = 1;
		while(true) {
			//맵의 크기를 입력 받음
			int N = Integer.parseInt(br.readLine());
			
			//만약 0이라면 더이상 BFS 탐색을 수행하지 않음
			if(N==0) {
				break;
			//0이 아니라면
			}else {
				//맵의 정보를 저장할 배열 선언
				int[][] map = new int[N][N];
				
				//맵의 정보를 입력받음
				for(int i=0; i<N; i++) {
					String[] temp = br.readLine().split(" ");
					for(int j=0; j<temp.length; j++) {
						map[i][j] = Integer.parseInt(temp[j]);
					}
				}
				
				//BFS 탐색의 결과를 출력함
				bw.write("Problem "+(idx++)+": "+BFS(map)+"\n");
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}