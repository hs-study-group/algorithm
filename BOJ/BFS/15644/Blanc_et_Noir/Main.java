//https://www.acmicpc.net/problem/15644

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//두 구슬의 위치와 기존에 움직인 방향, 움직인 횟수, 여태까지 이동한 방향 정보를 저장하는 노드 클래스 선언
class Node{
	Marble r, b;
	String p;
	int d, c;
	
	Node(Marble r, Marble b, int d, int c, String p){
		this.r = r;
		this.b = b;
		this.d = d;
		this.c = c;
		this.p = p;
	}
}

//구슬의 색상과 구슬의 y, x좌표를 저장하는 구슬 클래스
class Marble{
	char c;
	int y, x;
	Marble(char c, int y, int x){
		this.c = c;
		this.y = y;
		this.x = x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//현재 구슬의 상태와 이동할 방향을 분석하여 빨강구슬과 파랑구슬중 어떤 구슬이 먼저 움직여야 하는지 계산하는 메소드
	public static boolean isRedFirst(Node n, int d) {
		if((d==0&&n.r.y<n.b.y)||(d==1&&n.r.x>n.b.x)||(d==2&&n.r.y>n.b.y)||(d==3&&n.r.x<n.b.x)) {
			return true;
		}else {
			return false;
		}
	}
	
	//구슬 m1에 대하여 d방향으로 기울였을때 도달하는 위치를 반환하는 메소드
	//m2구슬은 m1구슬보다 먼저 움직인 구슬이며, m1구슬은 m2구슬에 가로막힐 수도 있음.
	//m2구슬이 null인 경우 m1구슬은 벽에 부딫히거나 탈출구를 찾을때까지만 움직임.
	public static Marble moveMarble(char[][] map, int d, Marble m1, Marble m2) {
		Marble result = null;
		
		int[][] dist = {{-1,0},{0,1},{1,0},{0,-1}};
		int y = m1.y;
		int x = m1.x;
		
		//해당 d 방향으로 계속 탐색을 이어나감
		while((y>=0&&y<map.length&&x>=0&&x<map[0].length)) {
			//만약 탈출구를 만났다면
			if(map[y][x]=='O') {
				//구슬이 탈출했음을 알리도록 null값을 리턴함
				break;
			//만약 m2 구슬에 가로막힌 경우
			}else if(m2!=null&&y==m2.y&&x==m2.x) {
				//m2구슬에 부딫히기 직전까지의 위치를 리턴함
				result = new Marble(m1.c,y-dist[d][0],x-dist[d][1]);
				break;
			//만약 구슬이 벽에 부딫힌 경우
			}else if(map[y][x]=='#') {
				//해당 벽에 부딫히기 직전까지의 위치를 리턴함
				result = new Marble(m1.c,y-dist[d][0],x-dist[d][1]);
				break;
			}
			
			//벽에 부딫히지 않았거나, 다른 구슬과 충돌하지 않았거나, 탈출하지 않았다면
			//d방향으로 한 칸 더 이동하여 다시 탐색을 수행함
			y = y + dist[d][0];
			x = x + dist[d][1];
		}
		
		//최종 구슬의 위치를 null 또는 Marble클래스로 리턴함
		return result;
	}
	
	//방향값 d를 전달받아 이를 문자열로 변환하는 메소드
	public static String encode(int d) {
		//정수값 0, 1, 2, 3은 각각 상, 우, 하, 좌 방향을 나타냄
		switch(d) {
			case 0:return "U";
			case 1:return "R";
			case 2:return "D";
			default:return "L";
		}
	}
	
	public static Node BFS(char[][] map, Marble r, Marble b) {
		//방문 배열은 빨강구슬의 y, x좌표 및 파랑구슬의 y, x좌표를 인덱스로 함
		//구슬의 최종 위치를 기록해두는 이유는 불필요한 반복을 줄이기 위함
		boolean[][][][] v = new boolean[map.length][map[0].length][map.length][map[0].length];
		
		//최초 빨강, 파랑 구슬의 위치 및 초기 방향, 초기 이동 횟수를 노드에 저장함
		Node node = new Node(r,b,4,1,"");
		
		//BFS탐색을 위한 큐를 선언함
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//방문 배열에 빨강 및 파랑 구슬의 위치에 대해 방문처리를 함
		v[r.y][r.x][b.y][b.x] = true;
		
		while(!q.isEmpty()) {
			Node n = q.poll();

			//만약 10번을 초과하여 이동한 경우
			if(n.c>10) {
				//더이상 탐색하지 않음
				continue;
			}

			for(int i=0; i<4; i++) {
				int nd = i;
				
				//만약 기존에 기울인 적이 있고, 기존 이동방향을 기준으로 앞 또는 뒤로 이동하려는 경우
				if(n.d!=4&&!(i==1||i==3)) {
					//해당 방향은 중복되므로 굳이 탐색할 필요 없음
					continue;
				//만약 기울인적이 없거나, 기울인적이 있다 하더라도 기존 이동방향을 기준으로 좌 또는 우로 이동하려는 경우
				}else {
					//해당 방향으로 이동함
					nd = (n.d+i)%4;
				}
				
				//만약 빨강 구슬이 먼저 움직여야 한다면
				if(isRedFirst(n,nd)) {
					//빨강 구슬을 먼저 움직이고 그 최종 좌표를 얻음
					Marble nr = moveMarble(map, nd, n.r, null);
					
					//파랑 구슬을 움직이되, 위치가 변경된 빨강 구슬과 충돌되는지 포함하여 그 최종 좌표를 얻음
					Marble nb = moveMarble(map, nd, n.b, nr);
					
					//만약 파랑 구슬이 탈출한 경우
					if(nb==null) {
						//이는 실패한 것이므로 더는 탐색하지 않음
						continue;
					//만약 파랑구슬은 탈출에 실패했으나, 빨강구슬은 탈출에 성공한 경우
					}else if(nr==null) {
						//여태까지 기울인 방향을 마지막으로 갱신한 후 노드 정보를 리턴함
						n.p=n.p+encode(nd);
						return n;
					//만약 빨강구슬과 파랑구슬의 위치가 기존에는 없었던 새로운 좌표 쌍이라면
					}else if(!v[nr.y][nr.x][nb.y][nb.x]){
						//해당 지점에서 다시 BFS 탐색을 수행함
						q.add(new Node(nr,nb,nd,n.c+1,n.p+encode(nd)));
						v[nr.y][nr.x][nb.y][nb.x] = true;
					}
				//만약 파랑 구슬이 먼저 움직여야 한다면
				}else {
					//파랑 구슬을 먼저 움직이고 그 최종 좌표를 얻음
					Marble nb = moveMarble(map, nd, n.b, null);
					
					//빨강 구슬을 움직이되, 위치가 변경된 파랑 구슬과 충돌되는지 포함하여 그 최종 좌표를 얻음
					Marble nr = moveMarble(map, nd, n.r, nb);
					
					//만약 파랑 구슬이 탈출한 경우
					if(nb==null) {
						//이는 실패한 것이므로 더는 탐색하지 않음
						continue;
						//만약 파랑구슬은 탈출에 실패했으나, 빨강구슬은 탈출에 성공한 경우
					}else if(nr==null) {
						//여태까지 기울인 방향을 마지막으로 갱신한 후 노드 정보를 리턴함
						n.p=n.p+encode(nd);
						return n;
					//만약 빨강구슬과 파랑구슬의 위치가 기존에는 없었던 새로운 좌표 쌍이라면
					}else if(!v[nr.y][nr.x][nb.y][nb.x]){
						//해당 지점에서 다시 BFS 탐색을 수행함
						q.add(new Node(nr,nb,nd,n.c+1,n.p+encode(nd)));
						v[nr.y][nr.x][nb.y][nb.x] = true;
					}
				}
			}		
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		char[][] map = new char[N][M];
		
		Marble r = null, b = null;
		
		//맵의 정보를 입력받음
		for(int i=0; i<N; i++) {
			map[i] = br.readLine().toCharArray();
			for(int j=0; j<map[0].length; j++) {
				//빨강 구슬 또는 파랑 구슬인 경우 그 좌표를 기록해둠
				if(map[i][j]=='R') {
					r = new Marble('R',i,j);
				}else if(map[i][j]=='B') {
					b = new Marble('B',i,j);
				}
			}
		}
		
		Node n = BFS(map,r,b);
		
		//BFS 수행 결과로 전달받은 노드가 null이 아니라면
		if(n!=null) {
			//기울인 횟수의 최소값과 그 누적 방향을 출력함
			bw.write(n.c+"\n"+n.p+"\n");
		//null이라면
		}else {
			//10번 이내로 기울여서 성공 가능한 경우가 없으므로 -1을 출력함
			bw.write("-1\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}