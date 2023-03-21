//https://level.goorm.io/exam/177478/%EB%AD%89%EC%B9%9C-k/quiz/1

import java.io.*;
import java.util.*;

class Node{
	int y, x, v;
	Node(int y, int x, int v){
		this.y=y;
		this.x=x;
		this.v=v;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//주어진 맵에서 node가 가진 값을 기준으로 node와 같은 그룹에 포함되는 영역의 크기를 구하는 메소드
	public static int BFS(int[][] map, boolean[][] v, Node node){
		//BFS탐색에 사용할 벡터 배열 선언
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//BFS탐색에 사용할 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//방문배열 선언 및 시작 위치를 방문처리
		v[node.y][node.x] = true;
		
		//그룹에 포함되는 영역의 크기를 저장할 변수, 최소 1의값을 가짐
		int cnt = 1;
		
		while(!q.isEmpty()){
			Node n = q.poll();
			for(int i=0;i<dist.length;i++){
				int y = n.y+dist[i][0];
				int x = n.x+dist[i][1];
				
				//만약 인접한 위치가 맵을 벗어나지 않고, 방문하지 않았으며, 현재 탐색중인 값과 동일하다면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x]&&map[y][x]==n.v){
					//해당 위치를 방문처리하고 큐에 추가함
					q.add(new Node(y,x,n.v));
					v[y][x]=true;
					//영역의 크기를 1증가시킴
					cnt++;
				}
			}
		}
		//영역의 크기를 리턴함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		final int N = Integer.parseInt(br.readLine());
		String[] temp = br.readLine().split(" ");
		
		int[][] map = new int[N][N];
		boolean[][] v = new boolean[N][N];
		int x = Integer.parseInt(temp[1])-1;
		int y = Integer.parseInt(temp[0])-1;
		
		//맵의 정보를 입력받음
		for(int i=0;i<N;i++){
			temp = br.readLine().split(" ");
			for(int j=0;j<N;j++){
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//y, x위치의 값을 구함
		final int VAL = map[y][x];
		
		//그룹의 최대 크기를 저장할 변수를 MIN_VALUE로 초기화
		int max = Integer.MIN_VALUE;
		
		//맵 전체를 탐색함
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				//아직 탐색하지 않은 위치이면서, VAL값을 갖는 위치에 대해서는
				if(map[i][j]==VAL&&!v[i][j]){
					//BFS탐색을 시작하고 그때 얻은 그룹의 크기가 max보다 크다면 그것을 max로 갱신함
					max = Math.max(max,BFS(map,v,new Node(i,j,VAL)));
				}
			}
		}
		
		bw.write(max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}