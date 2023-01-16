//https://www.acmicpc.net/problem/2638

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//탐색 위치 정보를 저장할 노드 클래스 선언
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
	
	public static int BFS(int[][] map, Node node) {
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		int[][] v = new int[map.length][map[0].length];
		v[node.y][node.x] = 1;
		
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);

		while(!q.isEmpty()) {
			Node n = q.poll();
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//좌표가 맵을 벗어나지 않는다면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length) {
					//그곳이 치즈라면
					if(map[y][x]==1) {
						//탐색한 적이 있음을 표시함
						v[y][x]++;
					//만약 그곳이 치즈가 아니면서 방문한 적이 없는 공간이라면
					}else if(map[y][x]==0&&v[y][x]==0) {
						//해당 위치로 탐색을 다시 할 수 있도록 큐에 추가함
						q.add(new Node(y,x));
						v[y][x]++;
					}
				}
			}
		}
		
		//사라진 치즈의 개수를 기록할 변수
		int cnt = 0;
		
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				//해당 위치가 치즈이고, 2번이상 방문한 적이 있다면
				if(map[i][j]==1&&v[i][j]>=2) {
					//해당 치즈를 제거하고 제거된 치즈 개수를 증가시킴
					map[i][j] = 0;
					cnt++;
				}
			}
		}
		
		//사라진 치즈의 개수를 반환함
		return cnt;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int C = 0;
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[][] map = new int[N][M];
		
		//맵 정보를 입력 받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<temp.length; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
				
				//해당 위치가 치즈라면, 총 치즈의 개수를 1증가시킴
				if(map[i][j] == 1) {
					C++;
				}
			}
		}
		
		//몇번의 시도끝에 모든 치즈가 사라지는지 횟수를 기록할 변수
		int D = 0;
		
		//치즈가 남아있다면
		while(C>0) {
			//시도 횟수를 1증가시킴
			D++;
			
			//BFS탐색의 결과로 사라진 치즈 개수만큼을 감소시킴
			//0,0위치는 항상 치즈가 없음이 보장되므로 이곳에서 BFS탐색을 시작함
			C -= BFS(map,new Node(0,0));
		}
		
		bw.write(D+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}