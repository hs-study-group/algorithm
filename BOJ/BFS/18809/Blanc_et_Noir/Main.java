//https://www.acmicpc.net/problem/18809

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node{
	//각각 y, x좌표, 페이즈(단계), 배양액(0이면 녹색, 1이면 빨간색)
	int y, x, p, c;
	
	Node(int y, int x, int p, int c){
		this.y = y;
		this.x = x;
		this.p = p;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[][] map;
	static int max = Integer.MIN_VALUE;
	
	//G, R은 각각 배치해야할 녹색, 빨간색 배양액의 숫자를 의미함
	static int G, R;
	
	//배양액을 배치할 수 있는 위치가 담긴 list에서, 녹색, 빨간색 배양액을 배치할 위치에 배치한 후에
	//BFS탐색을 통해 총 몇개의 꽃이 피어나는지 확인하는 메소드
	public static int BFS(ArrayList<Node> list, int[] vg, int[] vr) {
		int cnt = 0;
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		
		int[][][] v = new int[map.length][map[0].length][2];
		
		//꽃이 피어있는 위치에 몇번째 단계에서 꽃이 피어났는지를 저장함
		int[][] bloom = new int[map.length][map[0].length];
		
		Queue<Node> q = new LinkedList<Node>();
		
		//녹색 배양액을 설치하기로 결정한 위치의 정보를 얻고, 이를 큐에 추가하며 동시에 방문처리함
		for(int idx : vg) {
			Node n = list.get(idx);
			q.add(new Node(n.y,n.x,n.p,0));
			//n.p는 1값을 가지는데, 이는 1번째 단계에서 배양액이 해당 위치에 도달했음을 의미함
			v[n.y][n.x][0] = n.p;
		}
		
		//빨간색 배양액을 설치하기로 결정한 위치의 정보를 얻고, 이를 큐에 추가하며 동시에 방문처리함
		for(int idx : vr) {
			Node n = list.get(idx);
			q.add(new Node(n.y,n.x,n.p,1));
			//n.p는 1값을 가지는데, 이는 1번째 단계에서 배양액이 해당 위치에 도달했음을 의미함
			v[n.y][n.x][1] = n.p;
		}

		while(!q.isEmpty()) {

			Node n = q.poll();
			
			//만약 해당 위치에 꽃이 피어있다면 BFS탐색을 하지 않음
			if(bloom[n.y][n.x]!=0) {
				continue;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//해당 배양액이 해당 위치에 방문한 적이 없다면
				if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&map[y][x]!=0&&v[y][x][n.c]==0) {
					//해당 위치에 n.p+1번째 단계에 도달한 적이 있다고 처리함
					v[y][x][n.c]=n.p+1;
					
					//만약 현재 탐색중인 배양액과 다른 색의 배양액이 n.p+1번째 단계에 도달한 적이 있다면
					if(v[y][x][(n.c+1)%2]==n.p+1) {
						//해당 위치에는 n.p+1번째 단계에서 꽃이 피어남
						bloom[y][x] = n.p+1;
						cnt++;
					//만약 현재 탐색중인 배양액과 다른 색의 배양액이 n.p+1번째 단계에 도달한 적이 없다면
					}else if(v[y][x][(n.c+1)%2]==0) {
						//현재 배양액에 대한 정보를 다음 위치를 탐색할 수 있도록 큐에 추가함
						q.add(new Node(y,x,n.p+1,n.c));
					}
				}
			}
		}

		//꽃이 피어난 횟수를 리턴함
		return cnt;
	}
	
	public static void DFS(ArrayList<Node> list,int[] vg, int[] vr, int idx, int g, int r) {
		//만약 녹색 배양액 G개, 빨간색 배양액 R개를 모두 선택했다면
		if(g==G&&r==R) {
			//BFS탐색의 결과로 얻은 꽃의 개수를 최대값과 비교하여 갱신함
			max = Math.max(max,BFS(list,vg,vr));
		}
		
		//아직 녹색 배양액을 배치할 위치를 모두 결정하지 못했다면
		if(g<G) {
			//i번째 위치를 녹색 배양액을 배치할 위치로 결정하고 재귀호출을 수행함
			for(int i=idx; i<list.size(); i++) {
				vg[g] = i;
				DFS(list,vg,vr,i+1,g+1,r);
			}
		}

		//아직 빨간색 배양액을 배치할 위치를 모두 결정하지 못했다면
		if(r<R) {
			//i번째 위치를 빨간색 배양액을 배치할 위치로 결정하고 재귀호출을 수행함
			for(int i=idx; i<list.size(); i++) {
				vr[r] = i;
				DFS(list,vg,vr,i+1,g,r+1);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		//각각 녹색, 빨간색 배양액을 배치할 횟수를 의미함
		G = Integer.parseInt(temp[2]);
		R = Integer.parseInt(temp[3]);
		
		map = new int[N][M];
		
		//지도에서 2로 표시되는 지역은 배양액을 배치할 수 있는 위치이므로 이를 기록해둘 리스트 선언
		ArrayList<Node> list = new ArrayList<Node>();
		
		//지도 정보를 입력받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<temp.length; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
				
				//해당 위치가 배양액 배치가 가능한 지역이라면
				if(map[i][j]==2) {
					//이를 리스트에 저장해둠
					list.add(new Node(i,j,1,0));
				}
			}
		}

		//DFS탐색을 통해 N개의 배양액 배치 가능 지역에서 G, R개의 위치를 조합으로 구하고, 해당 위치에 실제로 배양액을 배치하여 BFS탐색으로 꽃의 개수를 구함
		DFS(list,new int[G],new int[R],0,0,0);
		
		bw.write(max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}