//https://www.acmicpc.net/problem/2589

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표 및 이동 횟수를 저장할 노드 클래스
class Node{
	int y, x, c;
	Node(int y,int x,int c){
		this.y=y;
		this.x=x;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//y, x좌표가 범위를 벗어나는지 확인하는 메소드
	public static boolean checkRange(char[][] arr, int y, int x) {
		if(y>=0&&y<arr.length&&x>=0&&x<arr[0].length) {
			return true;
		}
		return false;
	}
	
	//시작 노드로부터 가장 먼 거리에 위치한 노드를 리턴하는 메소드
	public static Node find(char[][] arr, Node node) {
		//BFS 탐색시 인접한 좌표를 계산하기 위한 벡터 배열
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		//BFS 탐색에 활용할 방문 배열 선언 및 방문 처리
		boolean[][] v = new boolean[arr.length][arr[0].length];
		v[node.y][node.x]=true;
		
		//BFS 탐색에 사용할 우선순위 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//시작 노드를 기준으로 가장 먼 거리에 위치한 노드
		Node result = node;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//만약 기존에 저장했던 가장 먼 거리에 위치한 노드보다 더 멀리 있는 노드라면
			if(n.c>result.c) {
				//그것을 가장 먼 거리에 위치한 노드로 갱신함
				result = n;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y+dist[i][0];
				int x = n.x+dist[i][1];
				
				//아직 방문하지 않은 땅이라면
				if(checkRange(arr,y,x)&&!v[y][x]&&arr[y][x]=='L') {
					//해당위치를 방문처리하고 큐에 추가함
					q.add(new Node(y,x,n.c+1));
					v[y][x]=true;
				}
			}
		}
		
		//시작 노드로부터 가장 먼 거리에 있는 노드를 리턴함
		return result;
	}
	
	//시작 노드에서 종료 노드로 이동하는데 필요한 최소 이동 횟수를 리턴하는 메소드
	public static int getMinDistance(char[][] arr, Node start, Node end) {
		//BFS 탐색시 인접한 좌표를 계산하기 위한 벡터 배열
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		//BFS 탐색에 활용할 방문 배열 선언 및 방문 처리
		boolean[][] v = new boolean[arr.length][arr[0].length];
		v[start.y][start.x]=true;
		
		//BFS 탐색에 사용할 우선순위 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		q.add(start);

		//시작노드에서 종료노드로 이동하는데 필요한 최소 이동 횟수를 저장할 변수
		int result = 0;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//종료 노드에 도달했다면
			if(n.y==end.y&&n.x==end.x) {
				//그때의 이동횟수를 저장하고 반복을 종료함
				result = n.c;
				break;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y+dist[i][0];
				int x = n.x+dist[i][1];
				
				//아직 방문하지 않은 땅이라면
				if(checkRange(arr,y,x)&&!v[y][x]&&arr[y][x]=='L') {
					//해당위치를 방문처리하고 큐에 추가함
					q.add(new Node(y,x,n.c+1));
					v[y][x]=true;
				}
			}
		}
		
		//최소 거리를 리턴함
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int H = Integer.parseInt(temp[0]);
		int W = Integer.parseInt(temp[1]);
		
		char[][] arr = new char[H][W];
		
		for(int i=0; i<H; i++) {
			arr[i] = br.readLine().toCharArray();
		}
		
		int max = Integer.MIN_VALUE;
		
		Node start=null;
		Node end=null;
		
		//완전 탐색으로 땅의 위치를 각각 찾음
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				//만약 해당 위치가 땅이라면
				if(arr[i][j]=='L') {
					//해당 위치를 시작점으로 하고 임시로 저장
					Node n1 = new Node(i,j,0);
					
					//시작점을 기준으로 가장 먼 거리에 있는 노드를 얻음
					Node n2 = find(arr,n1);
					
					//만약 여태까지 기록해두었던 최장거리보다도 지금 선택한 시작 및 종료 노드간의 거리가 더 크다면
					if(n2.c>max) {
						//그때의 시작, 종료노드와 이동횟수를 기록해둠
						start = n1;
						end = n2;
						max = n2.c;
					}
				}
			}
		}
		
		//시작노드에서 종료노드로 이동하는데 필요한 최소 이동 횟수를 구함
		int min = getMinDistance(arr,start,end);
		
		bw.write(min+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}