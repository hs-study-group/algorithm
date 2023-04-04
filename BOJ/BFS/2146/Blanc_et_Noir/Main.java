//https://www.acmicpc.net/problem/2146

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;


//y, x좌표 및 그룹번호 v, 사다리 길이 c를 저장할 노드 클래스 선언
class Node{
	int y, x, v, c;
	Node(int y, int x, int v, int c){
		this.y=y;
		this.x=x;
		this.v=v;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//t[][]배열에 1, 2, 3과 같이 같은 그룹에 속하는 지역은 같은 숫자를 할당하는 메소드
	public static void assign(int[][] m, int[][] t, boolean[][] v, Node node) {
		//BFS탐색에 사용할 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//시작위치는 모두 방문처리함
		v[node.y][node.x] = true;
		//시작위치는 node.v의 그룹번호를 갖도록 배정함
		t[node.y][node.x] = node.v;
		
		//BFS탐색에 사용할 벡터 배열 선언
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//인접한 위치가 맵을 벗어나지 않고, 아직 방문하지 않은 땅이라면
				if(y>=0&&y<m.length&&x>=0&&x<m[0].length&&!v[y][x]&&m[y][x]==1) {
					//해당 위치를 방문처리하고, node.v와 같은 그룹 번호를 할당함
					q.add(new Node(y,x, node.v, 0));
					v[y][x] = true;
					t[y][x] = node.v;
				}
			}
		}
	}
	
	public static int BFS(int[][] t, Node node) {
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		boolean[][] v = new boolean[t.length][t[0].length];
		v[node.y][node.x] = true;
		
		//최소 사다리 길이를 저장할 변수
		int answer = 0;
		
		//BFS탐색에 사용할 벡터배열 선언
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//현재 위치가 바다가 아닌 땅이면서, 자신과 다른 그룹에 속한 땅이라면
			if(t[n.y][n.x]!=0&&n.v!=t[n.y][n.x]) {
				//그때의 사다리 길이를 리턴함
				answer = n.c;
				break;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//인접한 위치가 맵의 범위를 벗어나지 않고, 아직 방문한 적이 없는 위치라면
				if(y>=0&&y<t.length&&x>=0&&x<t[0].length&&!v[y][x]) {
					//방문처리함
					v[y][x] = true;
					
					//자신과 같은 그룹에 속하는 위치라면
					if(t[y][x]==n.v) {
						//사다리 길이를 그대로 유지한채 탐색을 이어나감
						q.add(new Node(y,x, node.v, n.c));
					//바다라면 다리 길이를 1증가시킨채 탐색을 이어나감
					}else if(t[y][x]==0) {
						q.add(new Node(y,x, node.v, n.c+1));
					//자신과 다른 그룹에 속하는 위치라면 사다리 길이를 그대로 유지한채 탐색을 이어나감
					}else {
						q.add(new Node(y,x, node.v, n.c));
					}
				}
			}
		}
		
		return answer;
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//m배열은 그 지역이 땅인지 바다인지를 나타냄
		int[][] m = new int[N][N];
		
		//t배열은 그 지역이 어떤 그룹에 속하는지를 나타냄
		int[][] t = new int[N][N];
		
		//지도 정보를 입력받음
		for(int i=0; i<m.length; i++) {
			String[] temp = br.readLine().split(" ");
			
			for(int j=0; j<m[0].length; j++) {
				m[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//t배열에 할당할 그룹번호는 1부터 시작함
		int num = 1;
		
		boolean[][] v = new boolean[N][N];
		
		//입력받은 지도 정보를 탐색하여 서로 상하좌우로 연결된 지역은 같은 그룹으로 묶음
		for(int i=0; i<m.length; i++) {			
			for(int j=0; j<m[0].length; j++) {
				if(!v[i][j]&&m[i][j]==1) {
					//num이 동일한 위치들은 서로 같은 그룹에 속함
					assign(m,t,v,new Node(i,j,num++, 0));
				}
			}
		}
		
		//다리의 최소길이를 저장할 변수
		int answer = Integer.MAX_VALUE;
		
		//아직 탐색하지않은 
		for(int i=0; i<t.length; i++) {			
			for(int j=0; j<t[0].length; j++) {
				if(t[i][j]!=0) {
					answer = Math.min(answer, BFS(t,new Node(i,j,t[i][j],0)));
				}
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}