//https://www.acmicpc.net/problem/14948

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;

//y, x좌표 및 여태까지 필요한 최대 레벨, 남은 특수장비 사용가능 횟수를 저장할 노드 클래스
class Node implements Comparable<Node>{
	int y, x, v, c;
	Node(int y, int x, int v, int c){
		this.y=y;
		this.x=x;
		this.c=c;
		this.v=v;
	}
	
	//최대 레벨이 적은 노드가 먼저 반환되며
	//둘다 레벨이 동일하면, 특수장비 사용가능 횟수가 더 적은 노드가 먼저 반환되도록 함
	@Override
	public int compareTo(Node n) {
		if(this.v<n.v) {
			return -1;
		}else if(this.v>n.v) {
			return 1;
		}else if(this.c>n.c){
			return -1;
		}else if(this.c<n.c) {
			return 1;
		}
		
		return 0;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//군대를 탈출했는지, 아닌지를 검증하는 메소드
	public static boolean isFinished(int[][] arr, Node n) {
		if(n.y==arr.length-1&&n.x==arr[0].length-1) {
			return true;
		}
		
		return false;
	}
	
	//좌표가 맵을 벗어나는지 아닌지를 리턴하는 메소드
	public static boolean isInRange(int[][] arr, int y, int x) {
		if(y>=0&&y<arr.length&&x>=0&&x<arr[0].length) {
			return true;
		}
		
		return false;
	}
	
	//해당 위치에 방문할 수 있는지 없는지를 리턴하는 메소드
	public static boolean canVisit(int[][][] visit, int y, int x, int v, int c) {
		if(visit[y][x][c] > v) {
			return true;
		}
		
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//맵의 크기를 입력받음
		int H = Integer.parseInt(temp[0]);
		int W = Integer.parseInt(temp[1]);
		
		int[][] arr = new int[H][W];
		
		//맵 정보를 입력받음
		for(int i=0; i<H; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<W; j++) {
				arr[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//방문배열은 y, x좌표 및 특수장비 사용 횟수를 인덱스로 가짐
		int[][][] visit = new int[H][W][2];
		
		//BFS탐색에 사용할 벡터 배열
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		//우선순위큐를 활용하여 BFS탐색을 수행
		//최대 레벨이 더 적은 노드가 먼저 반환되며, 동일하다면 특수장비를 아직 사용하지 않은 노드가 먼저 반환됨
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(new Node(0,0,arr[0][0],1));
		
		//방문배열을 MAX_VALUE로 초기화
		for(int i=0; i<visit.length; i++) {
			for(int j=0;j<visit[0].length; j++) {
				for(int k=0; k<visit[0][0].length; k++) {
					visit[i][j][k] = Integer.MAX_VALUE;
				}
			}
		}
		
		//시작 위치에서의 최대 레벨은 곧 0,0 위치의 레벨
		visit[0][0][1] = arr[0][0];
		
		int answer = 0;
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//군대를 탈출했다면
			if(isFinished(arr,n)) {
				//그때의 최대 레벨이 곧 최소값임을 보장할 수 있으며 반복 종료함
				answer = n.v;
				break;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//맵의 범위를 벗어나지 않는다면
				if(isInRange(arr,y,x)) {
					//현재 가진 최대 레벨과 해당 위치에 도달했을때의 레벨중 더 큰 값을 max로 지정
					int max = Math.max(n.v,arr[y][x]);
					
					//만약 max 레벨로 특수장비를 사용하지 않고 해당 위치로 이동해도
					//기존에 기록된 레벨보다 max 레벨이 더 작아서 방문할 수 있다면
					if(canVisit(visit,y,x,max,n.c)) {
						//해당 위치를 방문처리함
						pq.add(new Node(y,x,max,n.c));
						visit[y][x][n.c] = max;
					}
				}
				
				//특수장비를 사용했을 때의 좌표
				int dy = n.y + dist[i][0]*2;
				int dx = n.x + dist[i][1]*2;
				
				//만약 특수장비 사용 횟수가 남아있고, 맵을 벗어나지 않는다면
				if(n.c>0&&isInRange(arr,dy,dx)) {
					//현재 가진 최대 레벨과 해당 위치에 도달했을때의 레벨중 더 큰 값을 max로 지정
					int max = Math.max(n.v,arr[dy][dx]);
					
					//만약 max 레벨로 특수장비를 사용하고 해당 위치로 이동해도
					//기존에 기록된 레벨보다 max 레벨이 더 작아서 방문할 수 있다면
					if(canVisit(visit,dy,dx,max,n.c-1)) {
						//해당 위치를 방문처리함
						pq.add(new Node(dy,dx,max,n.c-1));
						visit[y][x][n.c-1] = max;
					}
				}
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}