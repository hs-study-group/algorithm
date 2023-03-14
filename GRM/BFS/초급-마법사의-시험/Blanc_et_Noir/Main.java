import java.util.*;
import java.io.*;

//마법사의 y, x좌표 및 소모한 시간 c, 남은 마력 k를 저장할 노드 클래스
class Node implements Comparable<Node>{
	int y,x,c,k;
	Node(int y,int x,int c,int k){
		this.y=y;
		this.x=x;
		this.c=c;
		this.k=k;
	}
	
	//Comparable 인터페이스를 구현하여 소모한 시간이 더 적은 노드가 먼저 반환되도록하며
	//그러한 노드가 여러개라면 마력이 많이 남은 노드가 먼저 반환되도록 함
	@Override
	public int compareTo(Node n){
		if(this.c<n.c){
			return -1;
		}else if(this.c>n.c){
			return 1;
		}else{
			if(this.k>n.k){
				return -1;
			}else if(this.k<n.k){
				return 1;
			}else{
				return 0;
			}
		}
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//y, x좌표가 맵을 벗어났는지 아닌지를 판단하는 메소드
	public static boolean isInRange(char[][] map, int y, int x){
		if(y>=0&&y<map.length&&x>=0&&x<map[0].length){
			return true;
		}
		return false;
	}
	
	//주어진 map정보를 바탕으로 가장 적은 비용으로 숲을 탈출할때의 시간을 구하는 메소드
	public static int BFS(char[][] map, int[][][] v, Node node) {
		//방문배열을 MAX_VALUE로 초기화함
		for(int i=0;i<v.length;i++){
			for(int j=0;j<v[0].length;j++){
				for(int k=0;k<v[0][0].length;k++){
					v[i][j][k] = Integer.MAX_VALUE;
				}
			}
		}
		
		//BFS탐색에 사용할 벡터배열 선언
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//BFS탐색에 사용할 우선순위큐 선언
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(node);
		
		//시작 정점은 0의 시간을 소모했다고 처리함
		v[node.y][node.x][node.k] = 0;
		
		while(!pq.isEmpty()){
			Node n = pq.poll();
			
			//도착지점에 도착했다면, 그때 소요한 시간이 최소시간임을 우선순위큐로 보장할 수 있음
			if(n.y==map.length-1&&n.x==map[0].length-1){
				return n.c;
			}			
			
			for(int i=0; i<dist.length; i++){
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				int ny = n.y + dist[i][0]*2;
				int nx = n.x + dist[i][1]*2;
				
				//인접한 위치가 맵을 벗어나지 않고, 빈 공간이며, 기존에 동일한 마력 상태로 그 위치에
				//더 적은 시간을 소모해서 도달했던 적이 없었다면
				if(isInRange(map,y,x)&&map[y][x]=='0'&&n.c+1<v[y][x][n.k]){
					//해당 위치를 방문처리하고, 다음 노드정보를 큐에 추가함
					pq.add(new Node(y,x,n.c+1,n.k));
					v[y][x][n.k] = n.c+1;
				//인접한 위치가 나무고, 해당 나무 너머에 빈 공간이 있으며, 마력을 10 소모해서 해당 위치에 도달했을때
				//더 적은 시간을 소모해서 도달했던 적이 없었다면
				}else if(isInRange(map,y,x)&&isInRange(map,ny,nx)&&map[y][x]=='1'&&map[ny][nx]=='0'&&n.k>=10&&n.c+1<v[ny][nx][n.k-10]){
					//마력을 10소모하고 나무 너머에 있는 빈 공간에 방문한 것으로 처리하고, 그 정보를 큐에 추가함
					pq.add(new Node(ny,nx,n.c+1,n.k-10));
					v[ny][nx][n.k-10] = n.c+1;
				}
			}
		}
		//중간에 return된 적이 없다면 탈출 불가능한 것임
		return -1;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		final int R = Integer.parseInt(temp[0]);
		final int C = Integer.parseInt(temp[1]);
		final int K = Integer.parseInt(temp[2]);
		
		//맵의 정보를 입력받을 배열
		char[][] map = new char[R][C];
		
		//방문배열, y,x좌표 및 남은 마력을 인덱스로 가짐
		int[][][] v = new int[R][C][K+1];
		
		//맵을 입력받음
		for(int i=0;i<R;i++){
			map[i] = br.readLine().toCharArray();
		}
		
		//최소 시간을 출력함
		bw.write(BFS(map,v,new Node(0,0,0,K))+"\n");
		bw.flush();
		br.close();
		bw.close();
	}
}