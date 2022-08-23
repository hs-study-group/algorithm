//https://www.acmicpc.net/problem/2468

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	static int N,M;
	
	//특정 노드를 기준으로 BFS탐색 실시
	public static void BFS(Node node, int[][] map, boolean[][][] v, int level) {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		v[node.y][node.x][level] = true;
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				//이웃한 노드각 아직 방문하지 않았고, 수위보다 높아 물에 잠기지 않은 지역일때
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x][level]&&map[y][x]>level) {
					//해당 노드를 방문함
					v[y][x][level] = true;
					q.add(new Node(y,x));
				}
			}
		}
	}
	
	//아직 방문하지 않은 노드가 있는지 파악하는 메소드
	public static Node find(int[][] map, boolean[][][] v, int level) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				//아직 방문하지 않았고, 물에 잠기지 않았을때
				if(!v[i][j][level]&&map[i][j]>level) {
					//해당 노드를 반환함
					return new Node(i,j);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		int M = -1;
		int answer = 1;
		
		int[][] map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			String[] temp = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(temp[j])-1;
				//가장 높은 지역의 높이를 저장함
				if(M<map[i][j]) {
					M = map[i][j]-1;
				}
			}
		}
		
		//모든 지역이 물에 잠기는 것 까지 고려함
		for(int i=0; i<=M; i++) {
			Node n = null;
			boolean[][][] v = new boolean[N][N][M+1];
			int cnt = 0;
			
			//아직 방문하지 않은 노드가 있다면
			while((n = find(map,v,i))!=null) {
				//물에 잠기지 않은 지역의 수를 증가시킴
				cnt++;
				//그 노드를 기준으로 BFS탐색을 실시함
				BFS(n,map,v,i);
			}
			
			//만약 물에 잠기지 않은 지역의 수가 최댓값보다 클때
			if(answer<cnt) {
				//최댓값을 그것으로 갱신함
				answer = cnt;
			}
		}
		
		//최댓값을 출력함
		bw.write(answer+"\n");
		bw.flush();
	}
}