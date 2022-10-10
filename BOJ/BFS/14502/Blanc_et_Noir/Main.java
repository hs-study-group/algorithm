//https://www.acmicpc.net/problem/14502

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class Node{
	int y, x;
	Node(int y, int x){
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int max = Integer.MIN_VALUE;
	
	public static int BFS(int[][] map, Queue<Node> virus, Node[] wall, int Z) {
		boolean[][] v = new boolean[map.length][map[0].length];
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		int C = 0;
		
		//모든 바이러스들에 대하여 BFS탐색을 수행함
		Iterator<Node> itor = virus.iterator();
		
		while(itor.hasNext()) {
			//바이러스 하나를 선택하여 큐에 추가함
			Queue<Node> q = new LinkedList<Node>();
			Node node = itor.next();
			q.add(node);
			
			//바이러스의 초기 위치에 방문했음을 표시함
			v[node.y][node.x] = true;
			
			while(!q.isEmpty()) {
				Node n = q.poll();
				
				//바이러스의 현재 위치를 기준으로 상하좌우 방향으로 탐색함
				for(int j=0; j<dist.length; j++) {
					int y = n.y + dist[j][0];
					int x = n.x + dist[j][1];
					
					//바이러스가 이동하고자 하는 다음 위치가 맵의 범위를 벗어나지 않고, 방문한 적도 없으며, 빈 공간이라면
					if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x]&&map[y][x]==0) {
						//만약 그 위치가 새로 세운 벽이라면 false를 가지는 변수
						boolean flag = true;
						
						//해당 위치가 새로 세운 벽인지 확인함
						for(int k=0; k<wall.length; k++) {
							//새로 세운 벽이라면
							if(wall[k].y==y&&wall[k].x==x) {
								//해당 flag는 false값을 가짐
								flag = false;
							}
						}
						
						//만약 해당 위치가 벽을 새로 세우지 않았고, 방문하지도 않았던 빈 공간이라면
						if(flag) {
							//해당 위치를 방문함
							q.add(new Node(y,x));
							v[y][x] = true;
							
							//바이러스가 빈 공간에 퍼진 횟수를 카운트함.
							C++;
						}
					}
				}
			}
		}
		
		//안전영역의 개수는 초기 안전영역의 개수 - 바이러스가 퍼지면서 오염된 영역의 개수 - 3(빈 공간에 벽을 세우면서 사라진 안전 영역의 개수)임
		return Z-C-3;
	}
	
	//맵에서 빈 공간인 영역 3개를 골라 벽을 세우고 BFS 탐색을 수행하는 메소드
	public static void combinate(int[][] map, boolean[] v, int idx, int cnt, Queue virus, Node[] wall, int Z) {
		//벽을 3개 모두 다 세웠다면
		if(cnt==3) {
			//바이러스를 그 상태로 바이러스가 퍼졌을때 안전 영역의 크기를 구한 뒤 최대 안전 영역의 크기보다 크다면 그것으로 갱신시킴
			max = Math.max(max, BFS(map,virus,wall,Z));
		}else {
			for(int i=idx; i<v.length; i++) {
				//벽을 세울 위치의 좌표를 얻음
				int y = i/map[0].length;
				int x = i%map[0].length;
				
				//해당 위치에 아직 벽을 세우지 않았고, 빈 공간이라면
				if(!v[i]&&map[y][x]==0) {
					//해당 위치에 벽을 세움
					v[i] = true;
					
					//벽의 정보를 저장함
					wall[cnt] = new Node(y,x);
					
					//재귀적으로 다음에 세울 벽의 위치를 구함
					combinate(map,v,i+1,cnt+1,virus,wall, Z);
					
					//벽의 정보를 없던 것으로 처리함
					wall[cnt] = null;
					
					//해당 위치를 방문하지 않은 것처럼 처리함
					v[i] = false;
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		Queue<Node> virus = new LinkedList<Node>();
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		int Z = 0, V=0;
		int[][] map = new int[N][M];
		
		//맵의 정보를 입력 받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<map[0].length; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
				
				//입력 받은 위치가 벽이라면
				if(map[i][j]==0) {
					//초기 안전 영역의 개수를 1 증가시킴
					Z++;
				//입력 받은 위치가 벽이라면
				}else if(map[i][j]==2) {
					//바이러스의 위치를 큐에 추가함
					virus.add(new Node(i,j));
				}
			}
		}
		
		//현재 맵에서 0인 위치 3개를 골라 벽을 세웠을때의 안전영역을 크기를 구함
		combinate(map, new boolean[N*M], 0, 0, virus, new Node[3], Z);
		
		//최대 안전영역의 크기를 출력함.
		bw.write(max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}