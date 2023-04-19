//https://www.acmicpc.net/problem/15683

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//CCTV의 y, x좌표 및 CCTV타입을 저장할 노드 클래스
class Node{
	int y, x, t;
	Node(int y,int x, int t){
		this.y=y;
		this.x=x;
		this.t=t;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int min = Integer.MAX_VALUE;
	
	//상,하,좌,우중 한 방향으로 벽을 만날때까지 방문처리하는 메소드
	//다른 CCTV에 의해 이미 방문처리된 위치가 아닌 위치만 큐에 그 정보를 저장함
	public static void paint(Queue<Node> q, int[][] map, boolean[][] v, Node n, int d) {
		int[][] dist = new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
		
		//CCTV가 있는 위치는 무조건 방문처리함
		v[n.y][n.x] = true;
		
		int y = n.y + dist[d][0];
		int x = n.x + dist[d][1];
		
		//벽을 만나기전까지 계속해서 반복함
		while(y>=0&&y<map.length&&x>=0&&x<map[0].length&&map[y][x]!=6) {
			//만약 다른 CCTV가 방문하지 않았던 위치라면
			if(!v[y][x]) {
				//그 위치를 큐에 추가함
				q.add(new Node(y,x,n.t));
			}

			//방문처리함
			v[y][x]=true;
			
			//d방향으로 좌표를 이동시킴
			y += dist[d][0];
			x += dist[d][1];
		}
	}
	
	//큐에 저장되어있는 위치 정보에 대하여 방문처리를 취소하는 메소드
	public static void remove(Queue<Node> q, boolean[][] v) {
		while(!q.isEmpty()) {
			Node n2 = q.poll();
			v[n2.y][n2.x] = false;
		}
	}
	
	public static void backTracking(int[][] map, boolean[][] v, ArrayList<Node> list, int idx) {
		//모든 CCTV에 대한 탐색을 마쳤다면
		if(idx>=list.size()) {
			int cnt = 0;
			
			//CCTV에 의해 방문처리되지 않은 위치의 개수를 구함
			for(int i=0;i<v.length;i++) {
				for(int j=0;j<v[0].length;j++) {
					//CCTV에 의해 방문처리되지 않은 위치이면서, 벽이 아닌 빈 공간이라면
					if(!v[i][j]&&map[i][j]==0) {
						//카운트함
						cnt++;
					}
				}
			}
			
			//방문처리되지 않은 빈 공간의 개수가 기존의 최소값보다 작다면 그것을 최소값으로 갱신함
			min = Math.min(min,cnt);
		}else {
			Node n = list.get(idx);
			
			//만약 타입1 CCTV라면
			if(n.t==1) {
				//4방향중 한 방향에 대해서만 방문처리함
				for(int i=0;i<4;i++) {
					//방문처리를 취소하기 위해 새로 방문한 위치 정보를 기록할 큐
					Queue<Node> q = new LinkedList<Node>();
					//i방향으로 방문처리를 진행함
					paint(q,map,v,n,i);
					//다음 CCTV에 대해서도 탐색하기 위해 백 트래킹을 수행함
					backTracking(map,v,list,idx+1);
					//해당 CCTV에 의해 새로 방문처리된 위치를 전부 롤백처리함
					remove(q,v);
				}
			//만약 타입2 CCTV라면
			}else if(n.t==2) {
				for(int i=0;i<2;i++) {
					//방문처리를 취소하기 위해 새로 방문한 위치 정보를 기록할 큐
					Queue<Node> q = new LinkedList<Node>();
					//i및 i+2방향으로 방문처리를 진행함
					paint(q,map,v,n,i);
					paint(q,map,v,n,i+2);
					//다음 CCTV에 대해서도 탐색하기 위해 백 트래킹을 수행함
					backTracking(map,v,list,idx+1);
					//해당 CCTV에 의해 새로 방문처리된 위치를 전부 롤백처리함
					remove(q,v);
				}
			//만약 타입3 CCTV라면
			}else if(n.t==3) {
				for(int i=0;i<4;i++) {
					//방문처리를 취소하기 위해 새로 방문한 위치 정보를 기록할 큐
					Queue<Node> q = new LinkedList<Node>();
					//i및 i+1방향으로 방문처리를 진행함
					paint(q,map,v,n,i);
					paint(q,map,v,n,(i+1)%4);
					//다음 CCTV에 대해서도 탐색하기 위해 백 트래킹을 수행함
					backTracking(map,v,list,idx+1);
					//해당 CCTV에 의해 새로 방문처리된 위치를 전부 롤백처리함
					remove(q,v);
				}
			//만약 타입4 CCTV라면
			}else if(n.t==4) {
				for(int i=0;i<4;i++) {
					//방문처리를 취소하기 위해 새로 방문한 위치 정보를 기록할 큐
					Queue<Node> q = new LinkedList<Node>();
					//i및 i+1, i+2방향으로 방문처리를 진행함
					paint(q,map,v,n,i);
					paint(q,map,v,n,(i+1)%4);
					paint(q,map,v,n,(i+2)%4);
					//다음 CCTV에 대해서도 탐색하기 위해 백 트래킹을 수행함
					backTracking(map,v,list,idx+1);
					//해당 CCTV에 의해 새로 방문처리된 위치를 전부 롤백처리함
					remove(q,v);
				}
			//만약 타입5 CCTV라면
			}else {
				//방문처리를 취소하기 위해 새로 방문한 위치 정보를 기록할 큐
				Queue<Node> q = new LinkedList<Node>();
				//모든 방향으로 방문처리를 진행함
				for(int i=0;i<4;i++) {
					paint(q,map,v,n,i);
				}
				//다음 CCTV에 대해서도 탐색하기 위해 백 트래킹을 수행함
				backTracking(map,v,list,idx+1);
				//해당 CCTV에 의해 새로 방문처리된 위치를 전부 롤백처리함
				remove(q,v);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		final int N = Integer.parseInt(temp[0]);
		final int M = Integer.parseInt(temp[1]);
		
		int[][] map = new int[N][M];
		
		ArrayList<Node> list = new ArrayList<Node>();
		
		//맵의 정보를 입력받음
		for(int i=0;i<N;i++) {
			temp = br.readLine().split(" ");
			for(int j=0;j<M;j++) {
				map[i][j]=Integer.parseInt(temp[j]);
				//만약 CCTV라면
				if(map[i][j]>=1&&map[i][j]<=5) {
					//어레이리스트에 그 정보를 추가함
					list.add(new Node(i,j,map[i][j]));
				}
			}
		}
		
		//백 트래킹을 수행함
		backTracking(map,new boolean[N][M],list,0);
		
		bw.write(min+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}