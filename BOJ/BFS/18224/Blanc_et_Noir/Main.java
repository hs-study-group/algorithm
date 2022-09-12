//https://www.acmicpc.net/problem/18224

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//특정 노드의 좌표, 방향, 이동횟수, 몇 번째 날인지 기록할 변수를 저장하는 노드 클래스
class Node{
	//y, x는 노드의 좌표, d는 방향, m는 낮과 밤을 변화시킬때 사용할 변수, c는 몇 번째 날인지 기록할 변수
	int y, x, d, m, c;
	Node(int y, int x, int d, int m, int c){
		this.y = y;
		this.x = x;
		this.d = d;
		this.m = m;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//지금 현재 노드의 상태가 밤인지 낮인지 체크하는 메소드
	public static boolean isNight(int time, int m) {
		//노드의 낮과 밤을 변화시킬때 사용될 이동 횟수가
		//1. 0 <= time < m이면 낮
		//2. m <= time < 2*m이면 밤
		if(time<m) {
			return false;
		}else {
			return true;
		}
	}
	
	//밤에 벽을 넘을때 최초로 마주하는 빈 공간의 좌표를 반환하는 메소드
	public static Node find(int[][] map , int y, int x, int d) {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//탐색하는 좌표가 맵을 벗어나지 않으면
		while(y>=0&&y<map.length&&x>=0&&x<map.length) {
			//만약 그 좌표가 빈 공간이라면
			if(map[y][x] == 0) {
				//해당 좌표를 반환함
				return new Node(y,x,0,0,0);
			//해당 좌표가 벽이라면
			}else {
				//해당 방향으로 탐색을 계속 진행할 수 있도록 좌표를 변화시킴
				y = y + dist[d][0];
				x = x + dist[d][1];
			}
		}
		
		//중간에 빈 공간의 위치를 반환한 적이 없으므로 좌표를 반환할 수 없음
		return null;
	}
	
	//주어진 맵에 대하여 몇번째 낮 또는 밤에 도착할 수 있는지 없는지 판단하는 BFS 탐색 메소드
	public static String BFS(int[][] map, int m) {
		//방문배열 v[y][x][d][m]에 대하여
		//y, x 위치에 d방향, m번째 이동한 상태에서 방문한 적이 있는지 그 여부를 저장함함
		boolean[][][][] v = new boolean[map.length][map.length][5][m*2];
		Queue<Node> q = new LinkedList<Node>();
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		q.add(new Node(0,0,4,0,1));
		
		//0,0 위치에 정지 상태로, 이동횟수가 0일때 방문한 적이 있다고 처리함
		v[0][0][4][0] = true;
		
		while(!q.isEmpty()) {
			Node n = q.poll();

			//목적지에 도달했다면
			if(n.y == map.length-1&&n.x == map.length-1) {
				//현재 몇 번째 낮 또는 밤인지 확인하여 문자열을 반환함
				return n.m<m?n.c+" sun":n.c+" moon";
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//해당 좌표가 맵을 벗어나지 않는다면
				if(y>=0&&y<map.length&&x>=0&&x<map.length) {
					//다음 이동 횟수를 미리 구해둠
					int next = (n.m+1)%(2*m);
					
					//해당 위치가 빈 공간이라면
					if(map[y][x]==0) {
						//해당 방향으로 그 위치에 방문한 적이 없다면
						if(!v[y][x][i][next]) {
							//이번에 이동하면 새로운 날의 낮이 된다면
							if((n.m+1)/(2*m)>0) {
								//날짜를 증가시킨 상태로 해당 위치로부터 다시 탐색할 수 있도록 노드를 큐에 추가함
								q.add(new Node(y,x,i,next,n.c+1));
								
								//해당 위치로의 방문여부를 처리함
								v[y][x][i][next] = true;
							//이번에 이동해도 날짜가 변하지 않는 경우
							}else {
								//날짜를 증가시키지 않고 해당 위치로부터 다시 탐색할 수 있도록 노드를 큐에 추가함
								q.add(new Node(y,x,i,next,n.c));
								
								//해당 위치로의 방문여부를 처리함
								v[y][x][i][next] = true;
							}
						}else {
							continue;
						}
					//해당 위치가 벽이라면
					}else {
						//만약 지금이 밤이라면
						if(isNight(n.m, m)) {
							//그 벽을 넘어서 최초로 도착할 수 있는 빈 공간의 좌표를 얻음
							Node space = find(map, y, x, i);
							
							//벽 너머에 만약 빈 공간이 있다면
							if(space != null) {
								//해당 위치에 방문한 적이 없다면
								if(!v[space.y][space.x][i][next]) {
									//이번에 이동하면 새로운 날의 낮이 된다면
									if((n.m+1)/(2*m)>0) {
										//날짜를 증가시킨 상태로 해당 위치로부터 다시 탐색할 수 있도록 노드를 큐에 추가함
										q.add(new Node(space.y,space.x,i,next,n.c+1));
										
										//해당 위치로의 방문여부를 처리함
										v[space.y][space.x][i][next] = true;
									//이번에 이동해도 날짜가 변하지 않는 경우
									}else {
										//날짜를 증가시키지 않고 해당 위치로부터 다시 탐색할 수 있도록 노드를 큐에 추가함
										q.add(new Node(space.y,space.x,i,next,n.c));
										
										//해당 위치로의 방문여부를 처리함
										v[space.y][space.x][i][next] = true;
									}
								}else {
									continue;
								}
							//벽 너머에 빈 공간이 없다면
							}else {
								//다음 노드를 탐색함
								continue;
							}
						}
					}
				}
			}
		}
		return "-1";
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[][] map = new int[N][N];
		
		//맵의 정보를 입력 받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//BFS 탐색의 결과를 출력함
		bw.write(BFS(map,M)+"\n");
		
		br.close();
		bw.flush();
		bw.close();
	}
}