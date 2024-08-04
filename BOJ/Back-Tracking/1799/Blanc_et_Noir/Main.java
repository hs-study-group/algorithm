//https://www.acmicpc.net/problem/1799

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Deque;
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
	static ArrayList<Node> al;
	static Deque<Node> dq;
	static int[][] m;
	static boolean[][] v;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int max = 0;
	
	//해당 위치에 비숍을 놓을 수 있는지 확인하는 메소드
	public static boolean isDeployable(int y, int x) {
		//범위를 벗어났다면 비숍을 놓을 수 없음
		if(y<0||y>=v.length||x<0||x>=v.length) {
			return false;
		}
		
		//애당초 비숍을 놓을 수 없다면 불가능한 것으로 처리함
		if(m[y][x]!=1) {
			return false;
		}
		 
		//이미 비숍이 놓여있다면 비숍을 놓을 수 없음
		if(v[y][x]) {
			return false;
		}
		
		//기존에 설치한 비숍들의 경로상에 존재하는지 확인함
		for(Node n : dq) {
			//기존 비숍과 새로운 비숍의 좌표의 기울기 절대값이 1이라면
			//서로의 공격 범위 경로상에 존재하는 것이므로 비숍을 놓을 수 없음
			if(Math.abs(n.y-y)==Math.abs(n.x-x)) {
				return false;
			}
		}
		
		//모든 조건을 통과했다면 비숍을 놓을 수 있음
		return true;
	}
	
	//백트래킹 메소드
	public static void backtrack(int idx, int cnt) {
		for(int i=idx; i<al.size(); i++) {
			Node n = al.get(i);
			int y = n.y;
			int x = n.x;
			
			//해당 위치에 비숍을 놓을 수 있다면
			if(isDeployable(y, x)) {
				//비숍을 놓음
				v[y][x] = true;
				
				//비숍 위치를 저장함
				dq.addLast(n);
				
				//최대 비숍 숫자를 갱신함
				max = Math.max(max, cnt+1);
				
				//백트래킹을 수행함, 이때 이미 놓은 비숍의 위치는 굳이 반복할 필요 없으므로
				//i+1부터 반복할 수 있도록 함
				backtrack(i+1, cnt+1);
				
				//비숍 위치를 제거함
				dq.removeLast();
				
				//방문을 취소함
				v[y][x] = false;
			}
		}
	}
	
	//BFS 탐색을 통해 흑색 또는 백색 비숍이 저장될 수 있는 위치를 계산함
	public static ArrayList<Node> BFS(Node sn){
		//대각선 탐색을 위한 벡터 선언
		int[][] d = {{-1,-1},{1,1},{1,-1},{-1,1}};
		boolean[][] v = new boolean[m.length][m.length];
		Queue<Node> q = new LinkedList<Node>();
		ArrayList<Node> al = new ArrayList<Node>();
		
		//시작 노드를 방문처리함
		q.add(sn);
		v[sn.y][sn.x] = true;
		
		//시작 노드에 비숍을 놓을 수 있다면
		if(m[sn.y][sn.x]==1) {
			//결과 리스트에 추가함
			al.add(sn);
		}
		
		//비숍을 놓을 수 있는 위치를 계산함
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			for(int i=0; i<d.length; i++) {
				int y = n.y + d[i][0];
				int x = n.x + d[i][1];
				
				//범위를 벗어나거나, 이미 방문한 위치라면 패스함
				if(y<0||y>=m.length||x<0||x>=m.length||v[y][x]) {
					continue;
				}
				
				Node node = new Node(y,x);
				
				//만약 비숍을 놓을 수 있는 위치라면 결과 리스트에 추가함
				if(m[y][x]==1) {
					al.add(node);
				}
				
				//해당 지역을 방문처리함
				q.add(node);
				v[y][x] = true;
			}
		}
		
		return al;
	}
	
	public static void main(String[] args) throws IOException {
		String input = br.readLine();
		final int N = Integer.parseInt(input);
		m = new int[N][N];
		
		//지도를 입력 받음
		for(int i=0; i<N; i++) {
			String[] temp = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				m[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		Queue<Node> q = new LinkedList<Node>();
		
		int r = 0;
		
		//흑색, 백색 비숍 시작 위치를 저장함
		//해당 위치는 비숍을 놓지 못할수도 있음
		q.add(new Node(0,0));
		q.add(new Node(0,1));
		
		while(!q.isEmpty()) {
			//흑색 비숍, 백색 비숍을 놓을 수 있는 위치를 각각 저장함
			al = BFS(q.poll());
			max = 0;
			
			//방문배열 초기화
			v = new boolean[N][N];
			
			//덱 초기화
			dq = new LinkedList<Node>();
			
			//백트래킹 시작
			backtrack(0, 0);
			
			//백트래킹 결과를 누적함
			r += max;
		}
		
		bw.write(r+"\n");
		bw.flush();
		br.close();
		bw.close();		
	}
}
