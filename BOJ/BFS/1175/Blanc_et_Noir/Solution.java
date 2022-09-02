//https://www.acmicpc.net/problem/1175

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class Node{
	//각각 y, x좌표, 노드의 방향, 이동 횟수, 배달 상태를 나타냄
	//p의 경우 배달상태를 나타내며, 비트마스킹을 활용함
	
	//예를들어 p의 값이 3이라면 2진 비트로는 11(2) 이므로
	//C1, C0 두 개의 소포 모두 배달에 성공한 상태인 것임
	
	//만약 p값이 2라면 2진비트로는 10(2) 이므로
	//C1만 배달에 성공했고, C0는 아직 배달이 완료되지 않은 것임
	int y, x, d, c, p;
	Node(int y, int x, int d, int c, int p){
		this.y = y;
		this.x = x;
		this.d = d;
		this.c = c;
		this.p = p;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//현재 소포 배달 상태에서, idx번째 패킷을 배달하였을때의 상태를 반환하는 메소드
	public static int setPakage(int pkg, int idx) {
		return pkg|(1<<idx);
	}
	
	public static void main(String[] args) throws IOException {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		Queue<Node> q = new LinkedList<Node>();
		int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
		String[] temp;
		int N, M;
		
		temp = br.readLine().split(" ");
		N = Integer.parseInt(temp[0]);
		M = Integer.parseInt(temp[1]);
		
		//방문여부를 저장할 배열, v[y][x][i][p]에 대하여
		//y, x위치에, i방향으로, p라는 형태로 배달한 상태로 방문했는지 아닌지를 나타냄
		boolean[][][][] v = new boolean[N][M][5][4];
		char[][] map = new char[N][M];

		for(int i=0; i<N; i++) {
			temp = br.readLine().split("");
			for(int j=0; j<M; j++) {
				map[i][j] = temp[j].charAt(0);
				//만약 입력받은 위치가 시작 위치라면
				if(map[i][j]=='S') {
					//그것을 큐에 추가하고, 방문처리함
					//방향은 4값을 갖도록 하여, 상하좌우 그 어느방향도 아님을 나타냄
					q.add(new Node(i,j,4,0,0));
					v[i][j][4][0] = true;
					//그 후에 빈 공간으로 치환함
					map[i][j] = '.';
				//만약 배달 목적지라면
				}else if(map[i][j] == 'C') {
					//해시맵에 해당 소포의 위치와 그에 해당하는 인덱스를 저장해둠
					hm.put(i+":"+j, hm.size());
				}
			}
		}
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			//모든 소포가 배달이 완료된 것이라면 탐색을 종료함
			if(n.p==3) {
				bw.write(n.c+"\n");
				bw.flush();
				return;
			}
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				if(y>=0&&y<N&&x>=0&&x<M) {
					//만약 방향이 기존과 다르고, 아직 그 위치를 방문한 적이 없었다면
					if(i!=n.d&&!v[y][x][i][n.p]) {
						//그것이 배달 목적지라면
						if(map[y][x]=='C') {
							//해당 배달 목적지의 인덱스를 얻음
							int idx = hm.get(y+":"+x);
							//방문처리함
							v[y][x][i][n.p] = true;
							//해당 위치에서 다시 탐색할 수 있도록 노드를 추가함
							//단, 현재 배달의 상태는 setPackage( )메소드를 사용해 갱신함
							q.add(new Node(y,x,i,n.c+1,setPakage(n.p,idx)));
						//만약 빈 공간이라면
						}else if(map[y][x]=='.') {
							//방문처리함
							v[y][x][i][n.p] = true;
							//방향만 전환한채로 그 위치에서 다시 탐색할 수 있도록 노드를 추가함
							q.add(new Node(y,x,i,n.c+1,n.p));
						}
					}
				}
			}
		}
		
		//배달할 수 없음
		bw.write(-1+"\n");
		bw.flush();
	}
}