//https://www.acmicpc.net/problem/20419

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표 및 좌회전, 우회전 주문서의 개수를 저장할 클래스
class Node{
	int y, x, l, r;
	Node(int y, int x, int l, int r){
		this.y = y;
		this.x = x;
		this.l = l;
		this.r = r;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	//입력받은 문자를 0 ~ 3 사이의 정수로 변환하는 메소드
	public static int encode(char ch) {
		switch(ch) {
			case 'U':return 0;
			case 'R':return 1;
			case 'D':return 2;
			default:return 3;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		String answer = "No";
		
		int[][] dist = {{-1,0},{0,1},{1,0},{0,-1}};
		
		int R = Integer.parseInt(temp[0]);
		int C = Integer.parseInt(temp[1]);
		
		//주문서 쌍의 개수
		int K = Integer.parseInt(temp[2]);
		
		int[][] m = new int[R][C];
		
		//방문배열은 y, x좌표 및 남은 좌회전, 우회전 주문서의 개수 등 4차원으로 구성함
		boolean[][][][] v = new boolean[R][C][K+1][K+1];
		
		//맵 정보를 입력받음
		for(int i=0; i<R; i++) {
			char[] arr = br.readLine().toCharArray();
			for(int j=0; j<C; j++) {
				m[i][j] = encode(arr[j]);
			}
		}
		
		//BFS탐색에 사용할 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		
		//0,0 위치에서 좌, 우회전 주문서 각각 K개를 보유한 상태로 방문한 적이 있는 것으로 처리함
		v[0][0][K][K] = true;
		
		q.add(new Node(0,0,K,K));
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//도착지점에 도착했다면
			if(n.y==R-1&&n.x==C-1) {
				//도착할 수 있는 것으로 처리함
				answer = "Yes";
				break;
			}
			
			//현재 위치의 타일이 가리키는 방향
			int d = m[n.y][n.x];
			
			//해당 타일의 방향대로 움직였을때의 좌표
			int y = n.y + dist[d][0];
			int x = n.x + dist[d][1];
			
			//만약 이동하려는 좌표가 맵의 범위를 벗어나지 않으면
			if(y>=0&&y<R&&x>=0&&x<C) {
				//해당 위치에 방문한 적이 없다면
				if(!v[y][x][n.l][n.r]) {
					//해당위치를 방문함
					v[y][x][n.l][n.r] = true;
					q.add(new Node(y,x,n.l,n.r));
				}
			}
			
			//좌회전 주문서가 남아있으면
			if(n.l>0) {
				//현재 방향을 90도 좌로 회전한 새로운 방향
				int nd = (d+4-1)%4;
				
				//바뀐 방향으로 이동했을때의 좌표
				y = n.y + dist[nd][0];
				x = n.x + dist[nd][1];
				
				//해당 위치로 주문서를 1개 사용했을때 방문한 적이 없다면
				if(y>=0&&y<R&&x>=0&&x<C&&!v[y][x][n.l-1][n.r]) {
					//해당위치를 방문함
					v[y][x][n.l-1][n.r] = true;
					q.add(new Node(y,x,n.l-1,n.r));
				}
			}

			//우회전 주문서가 남아있으면
			if(n.r>0) {
				//현재 방향을 90도 좌로 회전한 새로운 방향
				int nd = (d+1)%4;
				
				//바뀐 방향으로 이동했을때의 좌표
				y = n.y + dist[nd][0];
				x = n.x + dist[nd][1];
				
				//해당 위치로 주문서를 1개 사용했을때 방문한 적이 없다면
				if(y>=0&&y<R&&x>=0&&x<C&&!v[y][x][n.l][n.r-1]) {
					//해당위치를 방문함
					v[y][x][n.l][n.r-1] = true;
					q.add(new Node(y,x,n.l,n.r-1));
				}
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}