import java.util.*;
import java.io.*;

class Node{
	int y, x;
	Node(int y, int x){
		this.y=y;
		this.x=x;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//영상 정보 및 방문배열, 탐색을 시작할 노드를 전달받아 물체의 크기를 리턴하는 메소드
	public static int BFS(char[][] map, boolean[][] v, Node node){
		//BFS에 활용할 큐 선언 및 시작 노드 추가
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//시작 정점을 방문처리함
		v[node.y][node.x] = true;
		
		//물체의 크기를 저장할 변수, 기본적으로 1의 값을 갖고 시작함
		int size = 1;
		
		//BFS탐색에 사용할 방문배열 선언
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		while(!q.isEmpty()){
			Node n = q.poll();
			
			for(int i=0;i<dist.length;i++){
				int y = n.y+dist[i][0];
				int x = n.x+dist[i][1];
				//해당 위치가 영상범위를 벗어나지 않고, 아직 방문하지않은 물체라면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x]&&map[y][x]=='#'){
					//그 위치 정보를 큐에 추가함
					q.add(new Node(y,x));
					//물체의 크기를 1증가시킴
					size++;
					//해당 위치를 방문처리함
					v[y][x]=true;
				}
			}
		}
		//물체의 크기를 리턴함
		return size;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		//처리할 영상의 크기를 입력받음
		final int C = Integer.parseInt(temp[0]);
		final int R = Integer.parseInt(temp[1]);
		
		//영상의 정보와 방문여부를 저장할 배열 선언
		char[][] map = new char[R][C];
		boolean[][] v = new boolean[R][C];
		
		//영상의 정보를 입력받음
		for(int i=0;i<R;i++){
			map[i] = br.readLine().toCharArray();
		}
		
		//물체의 개수와, 가장큰 물체의 크기를 저장할 변수 선언
		int cnt = 0, max = 0;
		
		//영상을 탐색하면서
		for(int i=0;i<R;i++){
			for(int j=0;j<C;j++){
				//아직 탐색하지 않은 물체를 발견하면
				if(map[i][j]=='#'&&!v[i][j]){
					//BFS탐색을 수행하여 물체의 크기를 구하고, 그것이 최대 크기보다 크다면
					//그것을 최대 크기로 갱신함
					max = Math.max(BFS(map,v,new Node(i,j)),max);
					//물체의 개수를 1 증가시킴
					cnt++;
				}
			}
		}
		
		//물체의 개수와 가장 큰 물체의 크기를 출력함
		bw.write(cnt+"\n"+max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}