import java.util.*;
import java.io.*;

//y, x좌표 및 전파되는데 걸리는 시간 c를 저장할 노드 클래스
class Node{
	int y, x, c;
	Node(int y, int x, int c){
		this.y=y;
		this.x=x;
		this.c=c;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//y, x좌표가 맵을 벗어나지 않는지 확인하는 메소드
	public static boolean isInRange(char[][] map, int y, int x){
		if(y>=0&&y<map.length&&x>=0&&x<map[0].length){
			return true;
		}
		
		return false;
	}
	
	//2차원 맵을 입력받고, 각각의 불씨를 상하좌우로 퍼뜨리면서 구름이에게 처음 닿을때의 시각을 리턴하는 메소드
	public static int BFS(char[][] map, Queue<Node> q, boolean[][] visit, Node goorm){
		//BFS탐색에 사용할 벡터 배열
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		int answer = -1;
		
		//만약 큐가 비어있지 않다면
		while(!q.isEmpty()){
			//해당 불씨의 정보를 얻음
			Node n = q.poll();
			
			//그 불씨가 구름이에게 닿았다면
			if(n.y==goorm.y&&n.x==goorm.x){
				//그때 소요된 시간 -1이 구름이가 버틸 수 있는 최대의 시간임
				answer = n.c-1;
				break;
			}
			
			//현재 위치로부터 4방향으로 탐색함
			for(int i=0;i<dist.length; i++){
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				//이동할 위치가 맵을 벗어나지 않고, 아직 방문하지 않았으며, 벽이 아니라면
				if(isInRange(map,y,x)&&!visit[y][x]&&map[y][x]!='#'){
					//해당 위치를 방문처리하고 큐에 추가하며, 시간을 1 증가시킴
					q.add(new Node(y,x,n.c+1));
					visit[y][x] = true;
				}
			}
		}
		//구름이가 버틸 수 있는 최대의 시간을 리턴함
		return answer;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//맵의 크기를 입력 받음
		int r = Integer.parseInt(temp[0]);
		int c = Integer.parseInt(temp[1]);
		
		//맵의 정보를 저장할 배열과 방문배열 선언
		char[][] map = new char[r][c];
		boolean[][] visit = new boolean[r][c];
		
		//탐색을 처음 시작할 위치를 저장할 노드 선언
		Node goorm = null;
		
		//BFS탐색에 사용할 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		
		//맵의 정보를 입력 받음
		for(int i=0;i<r;i++){
			map[i] = br.readLine().toCharArray();
			for(int j=0;j<c;j++){
				//해당 위치가 구름이가 있는 위치라면
				if(map[i][j]=='&'){
					//그것을 구름의 위치로 기록하고 노드 클래스로 저장함
					goorm = new Node(i,j,0);
				//만약 불이 시작할 위치라면
				}else if(map[i][j]=='@'){
					//해당 위치를 큐에 추가하고 방문처리함
					q.add(new Node(i,j,0));
					visit[i][j] = true;
				}
			}
		}
		
		//BFS탐색의 결과로 구름이가 최대한 버틸 수 있는 시간을 구하고 출력함
		bw.write(BFS(map,q,visit,goorm)+"\n");
		bw.flush();		
		br.close();
		bw.close();
	}
}