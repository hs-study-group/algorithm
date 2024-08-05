//https://www.acmicpc.net/problem/16946

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표를 기록할 노드 클래스
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
	static int[][] d = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
	
	//0이 연속된 구간에 그룹번호를 부여하고, 그 그룹의 크기를 반환하는 BFS 메소드
	public static int BFS(int[][] m, int[][] g, Node node, int idx) throws IOException {
		Queue<Node> q = new LinkedList<Node>();
		q.add(node);
		
		//시작위치도 그룹에 포함시킴
		int cnt = 1;
		g[node.y][node.x] = idx;

		while(!q.isEmpty()) {
			Node n = q.poll();
			
			for(int i=0; i<d.length; i++) {
				int y = n.y + d[i][0];
				int x = n.x + d[i][1];
				
				//좌표를 벗어나거나, 이미 그룹에 할당 되었거나, 그 위치가 벽이라면 스킵함
				if(y<0||y>=m.length||x<0||x>=m[0].length||m[y][x]!=0||g[y][x]!=0) {
					continue;
				}
				
				//빈 공간에 그룹번호를 할당하고, 그룹의 크기를 증가시킴
				g[y][x] = idx;
				cnt++;
				
				q.add(new Node(y,x));
			}
		}
		
		//그룹의 크기를 반환함
		return cnt;
	}
	
	public static void main(String[] args) throws IOException {
		String[] input = br.readLine().split(" ");
		
		final int N = Integer.parseInt(input[0]);
		final int M = Integer.parseInt(input[1]);
		
		//특정 그룹 번호에 대해서 그룹의 크기를 저장하는 해시맵
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		int[][] m = new int[N][M];
		int[][] g = new int[N][M];
		int idx = 0;
		
		//지도를 입력 받음
		for(int i=0; i<N; i++) {
			input = br.readLine().split("");
			
			for(int j=0; j<M; j++) {
				m[i][j] = Integer.parseInt(input[j]);
			}
		}
		
		//지도에서 빈 공간을 찾고, 연속된 빈 공간을 같은 그룹으로 묶고, 그룹의 크기를 해시맵에 저장함
		for(int i=0; i<N; i++) {			
			for(int j=0; j<M; j++) {
				if(g[i][j]==0&&m[i][j]==0) {
					int cnt = BFS(m, g, new Node(i,j), ++idx);
					hm.put(idx, cnt);
				}
			}
		}
		
		//벽을 탐색함
		for(int i=0; i<N; i++) {			
			for(int j=0; j<M; j++) {
				//벽을 찾았다면
				if(m[i][j]==1) {
					//벽을 부수고 그 공간을 빈 공간으로 체크함
					int sum = 1;
					
					//이미 해당 그룹이 이미 계산에 포함되었는지 확인할 해시맵
					HashMap<Integer, Boolean> chk = new HashMap<Integer, Boolean>();
					
					for(int k=0; k<d.length; k++) {
						int y = i + d[k][0];
						int x = j + d[k][1];
						
						//지도를 벗어나거나, 벽을 기준으로 상하좌우 빈 공간 그룹이 아니거나, 이미 계산에 포함된 그룹이면 스킵함
						if(y<0||y>=m.length||x<0||x>=m[0].length||g[y][x]==0||chk.containsKey(g[y][x])) {
							continue;
						}
						
						//그룹의 크기를 누적하여 더함
						sum += hm.get(g[y][x]);
						
						//해당 그룹을 이미 계산에 포함시켰음을 기록함
						chk.put(g[y][x],true);
					}
					
					//영역의 크기를 10으로 나눈 나머지를 출력함
					bw.write((sum%10)+"");
				}else {
					//원래 빈 공간이었다면 그대로 0을 출력함
					bw.write("0");
				}
			}
			bw.write("\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
}
