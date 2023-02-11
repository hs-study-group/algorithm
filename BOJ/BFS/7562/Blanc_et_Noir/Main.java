//https://www.acmicpc.net/problem/7562

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표 및 이동 횟수를 저장할 노드 클래스
class Node{
	int y, x, c;
	Node(int y, int x, int c){
		this.y=y;
		this.x=x;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//s정점에서 e정점으로 이동하기 위한 최소 비용을 리턴하는 메소드
	public static int BFS(int[][] v, Node s, Node e) {
		//나이트의 이동 방법을 표현하기 위한 배열
		int[][] dist = new int[][] {{-2,-1},{-2,1},{-1,2},{1,2},{2,1},{2,-1},{1,-2},{-1,-2}};
		
		//방문 배열을 모두 MAX_VALUE로 초기화
		for(int i=0; i<v.length; i++) {
			Arrays.fill(v[i], Integer.MAX_VALUE);
		}
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(s);
		
		//시작 정점은 0의 비용으로 도착했음을 표시
		v[s.y][s.x] = 0;
		
		//이동하는데 필요한 최소 비용
		int cnt = 0;
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//도착 지점에 도달했다면
			if(n.y==e.y&&n.x==e.x) {
				//그것이 최소 비용임이 보장됨
				cnt = n.c;
				break;
			}
			
			//현재 위치에서 8방향 모두 탐색함
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//만약 해당 위치에 방문한 적 없고, 맵을 벗어나지도 않으면
				if(y>=0&&y<v.length&&x>=0&&x<v[0].length&&v[y][x]>n.c+1) {
					//해당 지점에 방문함
					q.add(new Node(y,x,n.c+1));
					v[y][x]=n.c+1;
				}
			}
			
		}
		
		//이동하는데 필요한 최소 비용을 리턴함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		int T = Integer.parseInt(br.readLine());
		
		//각 테스트케이스에 대해
		for(int i=0; i<T; i++) {
			//맵의 크기를 입력 받음
			int N = Integer.parseInt(br.readLine());
			
			//시작 및 종료 정점 위치를 입력 받음
			String[] temp = br.readLine().split(" ");
			int sy = Integer.parseInt(temp[0]);
			int sx = Integer.parseInt(temp[1]);
			
			temp = br.readLine().split(" ");
			int ey = Integer.parseInt(temp[0]);
			int ex = Integer.parseInt(temp[1]);
			
			//나이트가 종료지점으로 이동하는데 필요한 최소 비용을 출력함
			bw.write(BFS(new int[N][N],new Node(sy,sx,0),new Node(ey,ex,0))+"\n");
		}

		bw.flush();
		bw.close();
		br.close();
	}
}