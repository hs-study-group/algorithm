//https://www.acmicpc.net/problem/18500

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
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
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static char[][] map;
	
	//파괴된 조각의 상하좌우를 시작점으로 하여 미네랄의 형태를 파악하는 메소드
	public static void BFS(Node node, boolean[][] v) {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		Queue<Node> r = new LinkedList<Node>();
		Queue<Node> q = new LinkedList<Node>();
		
		q.add(node);
		
		//하나의 큰 조각에 대한 탐색을 위한 방문배열
		//v[][]는 전체 조각에 대한 방문배열
		//visit[][]는 하나의 조각에 대한 방문배열
		boolean[][] visit = new boolean[map.length][map[0].length];
		visit[node.y][node.x] = true;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//해당 1x1미네랄 조각을 큐에 추가함
			r.add(n);
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//아직 방문한 적이 없는 미네랄 조각이라면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!visit[y][x]&&map[y][x]=='x') {
					//해당 위치를 방문처리하고 큐에 추가함
					q.add(new Node(y,x));
					visit[y][x] = true;
					v[y][x] = true;
				}
			}
		}
		
		//해당 미네랄 조각이 공중에 떠 있는 상태이면, 아래로 끌어내려야할 그 최소 간격을 구함
		int G = findMinGap(r,visit);
		
		//미네랄 조각이 공중에 떠 있다면
		if(G!=0) {
			Iterator<Node> itor = r.iterator();
			
			//해당 미네랄 조각을 지도에서 제거함
			while(itor.hasNext()) {
				Node n = itor.next();
				map[n.y][n.x] = '.';

			}
			
			itor = r.iterator();
			
			//해당 미네랄 조각을 G만큼 아래로 내려서 다시 지도에 그림
			while(itor.hasNext()) {
				Node n = itor.next();
				map[n.y+G][n.x] = 'x';

			}
		}
	}
	
	public static int findMinGap(Queue<Node> r, boolean[][] v) {
		Iterator<Node> itor = r.iterator();
		int min = Integer.MAX_VALUE;
		
		while(itor.hasNext()) {
			Node n = itor.next();
			
			//현재 탐색중인 1x1크기의 미네랄 조각 바로 아래위치부터 탐색함
			int y = n.y+1;
			int x = n.x;
			int g = 0;
			
			while(y>=0&&y<map.length) {
				//만약 빈 공간이라면
				if(map[y][x]=='.') {
					//이동시켜야할 간격을 1증가시킴
					g++;
					//1칸 아래를 탐색함
					y++;
				//만약 미네랄이라면
				}else if(map[y][x]=='x') {
					//현재 탐색중인 미네랄 조각이라면
					if(v[y][x]) {
						//g값을 무한대로 수정함
						g = Integer.MAX_VALUE;
						//반복을 종료함
						break;
					//다른 미네랄 조각이라면
					}else {
						//반복을 종료함
						break;
					}
				}
			}
			
			//이동시켜야 할 최소 간격을 갱신함
			min = Math.min(min, g);
		}
		
		//이동시켜야 할 최소 간격을 리턴함
		return min;
		
	}
	
	//막대를 던져서 처음 부딫히는 미네랄조각을 찾는 메소드
	public static void throwStick(int Y, int X){
		boolean[][] v = new boolean[map.length][map[0].length];
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		//델타값(변화율)
		int D;
		
		//X가 0이라면, 좌에서 우로 막대를 던지는 것이므로 델타값을 1로 설정함
		if(X == 0) {
			D = 1;
		//X가 map[0].length-1이라면, 우에서 좌로 막대를 던지는 것이므로 델타값을 -1로 설정함
		}else {
			D = -1;
		}
		
		//현재 탐색중인 위치가 빈공간이라면
		while(map[Y][X]=='.') {
			//X좌표를 델타만큼 변화시킴
			X += D;
			
			//맵을 벗어났다면
			if(!(X>=0&&X<map[0].length)) {
				//그 즉시 종료
				return;
			}
		}
		
		//아직 return되지 않았다면, Y, X위치는 미네랄이므로
		//해당 미네랄을 부수고 빈 공간으로 만듦
		map[Y][X] = '.';
		
		for(int i=0; i<dist.length; i++) {
			//파괴된 미네랄을 중심으로 상하좌우 미네랄 조각을 탐색함
			int y = Y + dist[i][0];
			int x = X + dist[i][1];
			
			//파괴되고 남은 해당 미네랄 조각의 형태 파악을 위해 BFS탐색을 실시함
			if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x]&&map[y][x]=='x') {
				BFS(new Node(y,x),v);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int R = Integer.parseInt(temp[0]);
		int C = Integer.parseInt(temp[1]);
		
		//미네랄 정보를 입력 받음
		map = new char[R][C];
		
		for(int i=0; i<R; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		//막대를 던질 횟수를 입력 받음
		int N = Integer.parseInt(br.readLine());
		
		temp = br.readLine().split(" ");
		for(int i=0; i<temp.length; i++) {
			//막대기를 던질 높이를 얻음, 이때 문제와는 다르게 편의상 가장 위에 위치한 높이를 1로 설정하였음
			int Y = map.length-Integer.parseInt(temp[i]);

			//현재 왼쪽에서 오른쪽으로 막대기를 던질 차례라면
			if(i%2==0) {
				//Y높이에서 X좌표는 0부터 map[0].length-1까지 탐색
				throwStick(Y, 0);
			//오른쪽에서 왼쪽으로 막대기를 던질 차례라면
			}else {
				//Y높이에서 X좌표는 map[0].length-1부터 0까지 탐색
				throwStick(Y, C-1);
			}
			
		}
		
		//막대를 모두 던지고 난 후의 모습을 출력함
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				bw.write(map[i][j]+"");
			}
			bw.write("\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}