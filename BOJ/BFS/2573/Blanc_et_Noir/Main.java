//https://www.acmicpc.net/problem/2573

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

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
	
	//빙산의 개수가 총 몇개인지 카운트하고 그 개수를 리턴하는 메소드
	public static int getNumber(int[][] m) {
		//분리된 빙산의 개수
		int cnt = 0;
		
		//방문배열
		boolean[][] v = new boolean[m.length][m[0].length];
		
		//빙산 정보를 완전탐색
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m[0].length; j++) {
				//아직 탐색하지 않은 빙산 조각이 있다면
				if(!v[i][j]&&m[i][j]>0) {
					//빙산의 개수를 1 증가시킴
					cnt++;
					
					//빙산을 BFS 탐색하기 위해 좌표를 변환하기 위한 벡터 배열
					int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
					
					//BFS 탐색에 사용할 큐 선언 및 해당 위치부터 탐색하도록 큐에 노드정보 추가
					Queue<Node> q = new LinkedList<Node>();
					q.add(new Node(i,j));
					
					//첫 탐색 위치를 방문처리함
					v[i][j] = true;
					
					while(!q.isEmpty()) {
						Node n = q.poll();
						
						//현재 위치로부터 4방향으로 탐색
						for(int k=0; k<dist.length; k++) {
							int y = n.y+dist[k][0];
							int x = n.x+dist[k][1];
							
							//만약 아직 방문하지 않은 인접한 빙산 조각이라면
							if(y>=0&&y<m.length&&x>=0&&x<m[0].length&&!v[y][x]&&m[y][x]>0) {
								//해당 조각부터 다시 BFS탐색을 할 수 있도록 노드에 정보를 추가함
								q.add(new Node(y,x));
								v[y][x]=true;
							}
						}
					}
				}
			}
		}
		
		//분리된 빙산의 개수를 리턴함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		final int H = Integer.parseInt(temp[0]);
		final int W = Integer.parseInt(temp[1]);
		int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
		int[][] m = new int[H][W];
		
		//빙산 정보를 입력 받음
		for(int i=0; i<m.length; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<m[0].length; j++) {
				m[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//빙산이 2개 이상으로 분리될 경우 얼마의 시간이 걸리는지를 저장할 변수
		int answer = 0;
		
		while(true) {
			//빙산의 개수를 구함
			int num = getNumber(m);
			
			//2개 이상의 빙산이 존재한다면 그 즉시 반복을 종료
			if(num>=2) {
				break;
			//빙산이 모두 녹아 없어졌으면, 0을 리턴해야함
			}else if(num==0) {
				answer = 0;
				break;
			//1개의 빙산만 존재한다면 빙산을 녹여봄
			}else {
				boolean[][] v = new boolean[m.length][m[0].length];
				
				//각각의 빙산 조각을 얼마나 녹여야 하는지, 그 가중치를 저장할 배열
				int[][] arr = new int[m.length][m[0].length];
				
				//빙산 정보를 완전탐색
				for(int i=0; i<m.length; i++) {
					for(int j=0; j<m[0].length; j++) {
						//아직 방문하지 않은 바다라면
						if(!v[i][j]&&m[i][j]==0) {
							//그 바다의 위치를 큐에 추가하고 방문처리함
							Queue<Node> q = new LinkedList<Node>();
							q.add(new Node(i,j));
							v[i][j]=true;
							
							while(!q.isEmpty()) {
								Node n = q.poll();
								
								//현재 위치를 기준으로 4방향으로 탐색
								for(int k=0; k<dist.length; k++) {
									int y = n.y + dist[k][0];
									int x = n.x + dist[k][1];
									
									//만약 인접한 위치가 방문하지 않은 위치라면
									if(y>=0&&y<m.length&&x>=0&&x<m[0].length&&!v[y][x]) {
										//그것이 빙산조각이라면
										if(m[y][x]>0) {
											//빙산 조각에 대한 가중치를 1증가시킴
											arr[y][x]++;
										//그것이 바다라면
										}else {
											//해당 위치를 큐에 추가함
											q.add(new Node(y,x));
											v[y][x]=true;
										}
									}
								}
							}							
						}
					}
				}
				
				//저장했던 가중치들을 실제 빙산 조각들에 반영함
				for(int i=0; i<m.length; i++) {
					for(int j=0; j<m[0].length; j++) {
						//만약 빙산 조각의 값이 가중치보다 크거나 같다면
						if(m[i][j]>=arr[i][j]) {
							//단순히 가중치만큼을 빼줌
							m[i][j]-=arr[i][j];
						//가중치가 더 크다면
						}else {
							//빙산을 바다로 변경함
							m[i][j]=0;
						}
					}
				}
				
				//빙산을 분리시키는데 소모된 시간을 1증가시킴
				answer++;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}