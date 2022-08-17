//https://www.acmicpc.net/problem/1261

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node{
	//각각 y, x좌표 및 벽을 부순 횟수 c
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
	static int N,M;
	static int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
	static int[][] map;
	static boolean[][] v;
	
	public static void main(String[] args) throws IOException {
		String[] temp = br.readLine().split(" ");
		
		N = Integer.parseInt(temp[1]);
		M = Integer.parseInt(temp[0]);
		map = new int[N][M];
		v = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			temp = br.readLine().split("");
			for(int j=0; j<temp.length; j++) {
				if(temp[j].equals("1")){
					map[i][j] = 1;
				}
			}
		}
		
		//보편적인 BFS와는 달리 우선순위큐를 활용하여 더 적게 벽을 부순 노드 정보가 먼저 나오도록 함
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.c<n2.c) {
					return -1;
				}else if(n1.c>n2.c) {
					return 1;
				}else {
					return 0;
				}
			}
			
		});
		pq.add(new Node(0,0,0));
		v[0][0] = true;
		
		while(!pq.isEmpty()) {
			Node n = pq.poll();
			
			//알고스팟에 도착했다면 탐색을 종료함
			if(n.y==N-1&&n.x==M-1) {
				bw.write(n.c+"\n");
				bw.flush();
				return;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//알고스팟이 아니라면 다음 위치로 이동하되, 비용은 벽의 유무에 따라 증가시킴
				if(y>=0&&y<N&&x>=0&&x<M&&!v[y][x]) {
					pq.add(new Node(y,x,n.c+map[y][x]));
					v[y][x] = true;
				}
			}
		}
	}
}