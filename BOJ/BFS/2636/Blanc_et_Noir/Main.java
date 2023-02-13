//https://www.acmicpc.net/problem/2636

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
	
	//치즈 정보를 전달받고, BFS탐색을 수행하여 제거되는 치즈의 개수를 리턴하는 메소드
	public static int BFS(int[][] arr) {
		//BFS탐색에 사용할 벡터 배열
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		//제거된 치즈의 개수
		int cnt = 0;
		
		//BFS탐색에 사용할 큐
		Queue<Node> q = new LinkedList<Node>();
		q.add(new Node(0,0));
		
		//방문 배열
		boolean[][] v = new boolean[arr.length][arr[0].length];
		v[0][0] = true;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//현재 탐색중인 위치와 인접한 위치를 탐색함
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//아직 방문하지않은 위치라면
				if(y>=0&&y<arr.length&&x>=0&&x<arr[0].length&&!v[y][x]) {
					//그것이 빈 공간이라면
					if(arr[y][x]==0) {
						//해당위치부터 탐색할 수 있도록 큐에 추가함
						q.add(new Node(y,x));
					//그것이 치즈라면
					}else if(arr[y][x]==1) {
						//해당 위치를 빈 공간으로 바꿈
						arr[y][x]=0;
						//제거된 치즈의 수를 1증가시킴
						cnt++;
					}
					
					//해당 위치를 방문처리함
					v[y][x]=true;
				}
			}
		}
		
		//제거된 치즈의 수를 리턴함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int H = Integer.parseInt(temp[0]);
		int W = Integer.parseInt(temp[1]);
		
		int[][] arr = new int[H][W];
		
		int cheese = 0;
		
		for(int i=0; i<H; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<W; j++) {
				arr[i][j] = Integer.parseInt(temp[j]);
				
				if(arr[i][j]==1) {
					cheese++;
				}
			}
		}
		
		//BFS를 수행하기 전에 남아있던 치즈의 개수
		int before = cheese;
		
		//BFS를 수행한 후에 남이있는 치즈의 개수
		int after = cheese;
		
		//모든 치즈를 제거하는데 필요한 시간
		int time = 0;
		
		//아직 치즈가 남아 있다면
		while(after>0) {
			//치즈를 제거함
			int cnt = BFS(arr);
			
			//before는 after로 초기화
			before = after;
			
			//after는 제거된 치즈 개수만큼 뺌
			after -= cnt;
			
			//시간을 증가시킴
			time++;
		}
		
		bw.write(time+"\n"+before+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}