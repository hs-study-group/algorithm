//https://www.acmicpc.net/problem/4963

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표를 저장할 노드 클래스
class Node{
	int y, x;
	Node(int y, int x){
		this.y=y;
		this.x=x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		
		while(true) {
			//맵의 너비, 높이를 입력 받음
			String[] temp = br.readLine().split(" ");
			int W = Integer.parseInt(temp[0]);
			int H = Integer.parseInt(temp[1]);
			
			//너비와 높이가 모두 0이라면
			if(W==0&&H==0) {
				//반복을 종료함
				break;
			}
			
			//맵 정보를 입력 받음
			int[][] m = new int[H][W];
			
			for(int i=0; i<H; i++) {
				temp = br.readLine().split(" ");
				for(int j=0; j<temp.length; j++) {
					m[i][j] = Integer.parseInt(temp[j]);
				}
			}
			
			//방문 배열을 선언함
			boolean[][] v = new boolean[H][W];
			
			//섬의 개수를 저장할 변수
			int cnt = 0;
			
			//모든 좌표를 탐색함
			for(int i=0; i<H; i++) {
				for(int j=0; j<W; j++) {
					//아직 방문하지 않은 섬의 좌표라면
					if(m[i][j]==1&&!v[i][j]) {
						//섬의 개수를 1 증가시킴
						cnt++;
						
						//8방향으로 섬을 탐색하기 위한 벡터 배열 선언
						int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1},{1,1},{1,-1},{-1,1},{-1,-1}};
						
						//BFS 탐색에 사용할 큐 선언하고 탐색 시작 위치를 큐에 추가함
						Queue<Node> q = new LinkedList<Node>();
						q.add(new Node(i,j));
						
						//시작 정점을 방문처리함
						v[i][j]=true;
						
						while(!q.isEmpty()) {
							Node n = q.poll();
							
							//현재 위치를 기준으로 8방향으로 탐색함
							for(int k=0;k<dist.length; k++) {
								int y = n.y + dist[k][0];
								int x = n.x + dist[k][1];
								
								//인접한의 섬의 좌표를 아직 방문하지 않았다면
								if(y>=0&&y<H&&x>=0&&x<W&&!v[y][x]&&m[y][x]==1) {
									//해당 위치 정보를 큐에 추가하고 방문처리함
									q.add(new Node(y,x));
									v[y][x]=true;
								}
							}
						}
					}
				}
			}
			
			//섬의 개수를 출력함
			bw.write(cnt+"\n");
		}

		bw.flush();
		bw.close();
		br.close();
	}
}