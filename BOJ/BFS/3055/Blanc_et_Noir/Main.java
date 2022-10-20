//https://www.acmicpc.net/problem/3055

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표 및 여태까지 사용한 비용을 저장할 노드 클래스
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
	
	public static void main(String[] args) throws Exception {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//맵의 크기를 입력 받음
		String[] temp = br.readLine().split(" ");
		int R = Integer.parseInt(temp[0]);
		int C = Integer.parseInt(temp[1]);
		
		//맵의 정보를 입력받을 배열 선언
		char[][] map = new char[R][C];
		
		//물의 이동 비용을 저장할 배열 선언
		int[][] wv = new int[R][C];
		
		//물의 시작 노드 정보들을 저장할 큐 선언
		Queue<Node> w = new LinkedList<Node>();
		
		//고슴도치의 시작위치를 기준으로 BFS탐색을 수행할때 사용할 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		
		//비버의 동굴의 좌표를 저장할 노드
		Node d = null;
		
		for(int i=0; i<R; i++) {
			map[i] = br.readLine().toCharArray();
			for(int j=0; j<map[i].length; j++) {
				//물의 비용 정보는 무조건 MAX_VALUE로 일단 초기화 함
				wv[i][j] = Integer.MAX_VALUE;
				
				//만약 목적지라면
				if(map[i][j]=='D') {
					//목적지 좌표를 저장함
					d = new Node(i,j,0);
				//만약 고슴도치라면
				}else if(map[i][j]=='S') {
					//고슴도치 정보를 큐에 추가함
					q.add(new Node(i,j,0));
				//만약 물이라면
				}else if(map[i][j]=='*') {
					//물의 정보를 큐에 추가함
					w.add(new Node(i,j,0));
				}
			}
		}
		
		//입력받은 물들의 위치를 활용하여 BFS탐색을 수행함
		while(!w.isEmpty()) {
			Node n = w.poll();
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//물이 다음에 이동할 위치가 맵을 벗어나지 않았고, 빈 공간이라면
				if(y>=0&&y<R&&x>=0&&x<C&&map[y][x]=='.') {
					//해당 위치에 n.c+1의 비용으로 도달하면 기존보다 더 적은 비용으로 도달할 수 있는 경우
					if(n.c+1<wv[y][x]) {
						//비용 정보가 저장될 배열을 갱신함
						wv[y][x] = n.c + 1;
						//다음 위치에서 BFS 탐색을 수행할 수 있도록 큐에 추가함
						w.add(new Node(y,x,n.c+1));
					}
				}
			}
		}
		
		//고슴도치의 이동에 사용할 방문배열 선언
		boolean[][] v = new boolean[R][C];
		
		//기본적으로 탈출이 불가하다고 가정함
		String result = "KAKTUS";
		
		while(!q.isEmpty()) {
			Node n = q.poll();

			//만약 고슴도치가 목적지에 도달한 경우
			if(n.y==d.y&&n.x==d.x) {
				//해당 목적지에 도달하는데 필요한 최소 비용으로 정답을 갱신함
				result = n.c+"";
				
				//반복문을 탈출함
				break;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//다음에 이동할 위치가 맵을 벗어나지 않았고
				if(y>=0&&y<R&&x>=0&&x<C) {
					//아직 고슴도치가 방문하지 않았으며, 물보다 더 빨리 도착할 수 있고, 그곳이 목적지 또는 빈 공간인경우
					if(!v[y][x]&&n.c+1<wv[y][x]&&(map[y][x]=='.'||map[y][x]=='D')) {
						//해당 위치에 방문한 것으로 처리함
						v[y][x] = true;
						
						//다음 BFS탐색을 위해 큐에 갱신된 정보를 담은 노드를 추가함
						q.add(new Node(y,x,n.c+1));
					}
				}
			}
		}
		
		//결과를 출력함
		bw.write(result+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}