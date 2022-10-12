//https://www.acmicpc.net/problem/16441

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//늑대의 위치 및 방향을 저장할 노드 클래스
class Node{
	int y, x, d;
	Node(int y, int x, int d){
		this.y = y;
		this.x = x;
		this.d = d;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static boolean[][] result;
	
	public static void BFS(char[][] map,boolean[][][] v, Node wolf) {
		int[][] dist = {{-1,0},{0,1},{1,0},{0,-1}};
		
		//늑대의 시작 위치를 큐에 추가함
		Queue<Node> q = new LinkedList<Node>();
		q.add(wolf);
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//현재 늑대가 서있는 위치가 빙판이라면
			if(map[n.y][n.x]=='+') {
				int y = n.y + dist[n.d][0];
				int x = n.x + dist[n.d][1];
				
				//원래 이동하던 방향으로 움직였을때 벽과 충돌하지 않는다면
				//즉, 그대로 직진하면 초원이나 빙판을 마주하는 경우
				if(map[y][x]!='#') {
					//그대로 직진함
					q.add(new Node(y,x,n.d));
					
					//해당위치에 해당 방향으로 방문한 적이 있음을 표시함
					v[y][x][n.d] = true;
					
					//결과 배열에도 방문한 적이 있음을 표시함
					result[y][x] = true;
				//원래 이동하던 방향으로 움직이면 벽과 충돌하는경우
				}else {
					for(int i=1; i<=3; i+=2) {
						//자신의 직진방향의 수직 방향을 구함
						int nd = (n.d+i)%4;
						
						//수직방향으로 이동했을 때의 좌표
						int ny = n.y + dist[nd][0];
						int nx = n.x + dist[nd][1];
						
						//수직 방향으로 이동했을 때, 벽이 아닌 공간이고, 방문한 적도 없다면 
						if(!v[ny][nx][nd]&&map[ny][nx]!='#') {
							//해당 위치로 이동함
							q.add(new Node(ny,nx,nd));
							
							//해당위치에 해당 방향으로 방문한 적이 있음을 표시함
							v[ny][nx][nd] = true;
							
							//결과 배열에도 방문한 적이 있음을 표시함
							result[ny][nx] = true;
						}
					}
				}
			//만약 빙판이 아니라면, 즉 초원이라면
			}else {
				//현재 위치에서 4방향을 탐색함
				for(int i=0; i<dist.length; i++) {
					int y = n.y + dist[i][0];
					int x = n.x + dist[i][1];
					
					//해당 위치에 도달한 적도 없고, 벽도 아니라면
					if(map[y][x]!='#'&&!v[y][x][i]) {
						//해당 위치로 이동함
						q.add(new Node(y,x,i));
						
						//해당위치에 해당 방향으로 방문한 적이 있음을 표시함
						v[y][x][i] = true;
						
						//결과 배열에도 방문한 적이 있음을 표시함
						result[y][x] = true;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		Queue<Node> wolves = new LinkedList<Node>();
		
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		char[][] map = new char[N][M];
		result = new boolean[N][M];
		
		//맵의 정보를 입력받음
		for(int i=0; i<N; i++) {
			map[i] = br.readLine().toCharArray();
			for(int j=0; j<map[0].length; j++) {
				//해당 위치가 늑대라면, 그 정보를 큐에 추가함
				if(map[i][j]=='W') {
					wolves.add(new Node(i,j,4));
				}
			}
		}
		
		//방문 배열은 모든 늑대가 공유해야 함
		//다른 늑대가 방문한 위치를 굳이 방문할 필요가 없음
		//공유하지 않으면 TLE가 발생함
		boolean[][][] v = new boolean[map.length][map[0].length][4];
		
		//모든 늑대에 대하여 BFS 탐색을 수행함
		while(!wolves.isEmpty()) {
			Node wolf = wolves.poll();
			BFS(map,v,wolf);
		}
		
		//입력받은 맵을 출력함
		for(int i=0; i<N; i++) {
			for(int j=0; j<map[0].length; j++) {
				//만약 해당 지역이 초원인데 그 어떤늑대도 도달하지 못한 공간이라면
				if(map[i][j]=='.'&&!result[i][j]) {
					//그곳은 안전 지대임
					bw.write("P");
				//그 외에는 그대로 출력함
				}else {
					bw.write(map[i][j]+"");
				}
			}
			bw.write("\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}