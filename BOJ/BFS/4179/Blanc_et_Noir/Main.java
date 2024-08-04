//https://www.acmicpc.net/problem/4179

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

class Node{
	//y좌표, x좌표, 시간
	int y, x, c;
	//불인지 사람인지 구분할 변수
	char k;
	
	Node(int y, int x, int c, char k){
		this.y = y;
		this.x = x;
		this.c = c;
		this.k = k;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws IOException {
		String[] input = br.readLine().split(" ");
		String result = "IMPOSSIBLE";
		
		final int R = Integer.parseInt(input[0]);
		final int C = Integer.parseInt(input[1]);
		char[][] map = new char[R][C];
		boolean[][] v = new boolean[R][C];
		int[][] dir = {{-1,0},{1,0},{0,-1},{0,1}};
		
		Queue<Node> q = new LinkedList<Node>();
		
		Node node = null;
		
		for(int i=0; i<R; i++) {
			map[i] = br.readLine().toCharArray();
			
			for(int j=0; j<C; j++) {				
				if(map[i][j]=='F') {
					//불의 좌표는 미리 큐에 추가함
					//불이 먼저 이동할 수 있도록 하기 위함
					q.add(new Node(i,j,1, 'F'));
					v[i][j] = true;
				}else if(map[i][j]=='J') {
					node = new Node(i,j,1, 'J');
					v[i][j] = true;
				}
			}
		}
		
		//사람이 불보다 나중에 이동할 수 있도록 처리함
		q.add(node);
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//가장자리에 사람이 도달했을경우 종료
			if(n.k=='J'&&(n.y==0||n.y==R-1||n.x==0||n.x==C-1)) {
				result = n.c+"";
				break;
			}
			
			for(int i=0;i<dir.length;i++) {
				int y = n.y + dir[i][0];
				int x = n.x + dir[i][1];
				
				if(y<0||y>=R||x<0||x>=C) {
					continue;
				}
				
				//해당 공간에 방문한 적이 없으면 방문함
				if(map[y][x]=='.'&&!v[y][x]) {
					v[y][x] = true;
					q.add(new Node(y,x,n.c+1, n.k));
				}
			}
		}
		
		bw.write(result+"\n");
		bw.flush();
		br.close();
		bw.close();
		
	}
}
