//https://level.goorm.io/exam/167345/%EC%B1%8C%EB%A6%B0%EC%A7%80-%EB%8B%A8%ED%92%8D%EB%82%98%EB%AC%B4/quiz/1

import java.io.*;
import java.util.*;

//단풍의 위치 y, x좌표를 기록할 노드 클래스 선언
class Node{
	int y, x;
	Node(int y,int x){
		this.y=y;
		this.x=x;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 맵 정보를 토대로 단풍나무를 물들이는 메소드
	public static int BFS(int[][] map){
		Queue<Node> q = new LinkedList<Node>();
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//물들인 횟수를 저장할 변수
		int cnt = 0;
		
		//모두 물들어 있는 단풍나무들의 위치를 큐에 추가함
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[0].length;j++){
				if(map[i][j]==0){
					q.add(new Node(i,j));
				}
			}
		}
		
		while(!q.isEmpty()){
			Node n = q.poll();
			
			for(int i=0;i<dist.length;i++){
				int y = n.y+dist[i][0];
				int x = n.x+dist[i][1];
				
				//인접한 위치에 아직 물들지 않은 단풍이 있다면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&map[y][x]>0){
					//해당 위치의 단풍을 하나 물들임
					map[y][x]--;
					//물들인 횟수를 카운트 함
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {		
		final int N = Integer.parseInt(br.readLine());
		int[][] map = new int[N][N];
		
		for(int i=0;i<N;i++){
			String[] temp = br.readLine().split(" ");
			for(int j=0;j<N;j++){
				map[i][j]=Integer.parseInt(temp[j]);
			}
		}
		
		int cnt = 0;
		
		//만약 이번 회차에 새롭게 물든 단풍이 있다면 계속 반복함
		while(BFS(map)>0){
			//물들이기 위해 필요한 일수를 1증가시킴
			cnt++;
		}

		bw.write(cnt+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}