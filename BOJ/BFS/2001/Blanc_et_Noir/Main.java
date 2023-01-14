//https://www.acmicpc.net/problem/2001

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//현재 어떤 섬에 있는지, 어떤 섬들의 보석을 주웠는지, 총 몇개의 보석을 주웠는지
//그러한 정보를 저장하는 노드 클래스 선언
class Node{
	//v : 현재 위치한 섬의 번호
	//k : 현재까지 어떤 섬들의 보석을 주웠는지 비트마스킹의 형태로 저장하는 변수
	//    예를들어 k = 6이라면 이진수로 110이며, 이는 2, 3번째 섬의 보석은 주웠으나, 1번째 섬의 보석은 아직 줍지 못한 것임
	//c : 여태까지 몇개의 보석을 주웠는지 저장할 변수
	int v, k, c;
	
	Node(int v, int k, int c){
		this.v = v;
		this.k = k;
		this.c = c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//보석이 있는 섬의 번호를 key로 사용하여 해당 섬을 Node클래스의 k에 맵핑하기 위한 인덱스를 저장하는 해쉬맵
	static HashMap<Integer, Integer> index = new HashMap<Integer,Integer>();
	
	//간선 정보를 저장하는 2차원 어레이리스트
	static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
	
	//N, M, K는 각각 섬의 개수, 간선의 개수, 보석이 있는 섬의 개수를 의미함
	static int N, M, K;
	
	//특정 island의 보석을 주웠을때의 상태값을 갱신하여 리턴하는 메소드
	public static int getJewel(HashMap<Integer, Integer> index, int k, int island) {
		return k | (1<<index.get(island));
	}
	
	//특정 island에의 보석을 이미 주웠는지 아닌지 그 여부를 리턴하는 메소드
	public static boolean hasJewel(HashMap<Integer, Integer> index, int k, int island) {
		return (k & (1<<index.get(island))) > 0;
	}
	
	public static int BFS() {
		int max = 0;
		
		//방문배열은 2차원으로 구성됨
		//v[ n ][ k ]가 의미하는 바는, 번호가 n+1인 섬에 k라는 형태로 보석 섬들의 보석을 주웠을때 방문한 적이 있는지 없는지를 의미함.
		boolean[][] v = new boolean[N][1<<K];
		
		//BFS 탐색을 위한 우선순위 큐 선언
		Queue<Node> q = new LinkedList<Node>();
		
		//1번 섬에, 0개의 보석을 주운 상태로 시작할 수 있도록 큐에 추가후 방문처리함
		q.add(new Node(0,0,0));
		v[0][0] = true;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			//만약 1번 섬으로 되돌아 왔다면
			if(cur.v==0) {
				//여태까지 주운 보석의 수를 최대값과 비교하여 갱신함
				max = Math.max(max, cur.c);
			}
			
			//현재 위치한 섬과 인접한 다른 섬들과의 길을 확인함
			for(int i=0; i<graph.get(cur.v).size(); i++) {
				//현재 위치한 섬과 이어지는 다른 섬과의 길을 얻음
				Node next = graph.get(cur.v).get(i);

				//만약 인접한 섬이 보석이 있는 섬이면서, 아직 그 섬의 보석을 줍지 않았을때
				if(doesIslandHaveJewel(next.v)&&!hasJewel(index,cur.k,next.v)) {
					//해당 섬의 보석을 주웠다고 가정하고 상태를 업데이트함
					int newK = getJewel(index, cur.k, next.v);
					
					//만약 지금 가지고있는 보석의 개수가 제한 개수 이하이면서
					//아직 newK라는 형태로 보석을 주워서 도달해본적이 없는 경우에
					if(cur.c<=next.c&&!v[next.v][newK]) {
						//주운 보석의 수를 1증가시키고, 주운 보석의 상태를 업데이트한 상태로 큐에 추가하고 방문처리함
						q.add(new Node(next.v, newK, cur.c+1));
						v[next.v][newK] = true;
					}
				}

				//인접한 섬의 보석 보유 여부와 상관없이 현재 가진 보석의 개수가 제한을 넘지 않고
				//현재 가진 보석들의 상태로 인접한 섬에 도달해복적이 없다면
				if(cur.c<=next.c&&!v[next.v][cur.k]) {
					//인접한 섬에 방문한 것으로 처리함
					q.add(new Node(next.v, cur.k, cur.c));
					v[next.v][cur.k] = true;
				}
			}
		}
		
		//주운 보석의 최대값을 리턴함
		return max;
	}
	
	//어떤 섬이 보석을 가지고 있는지 아닌지 판단하는 메소드
	public static boolean doesIslandHaveJewel(int island) {
		return index.containsKey(island);
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		N = Integer.parseInt(temp[0]);
		M = Integer.parseInt(temp[1]);
		K = Integer.parseInt(temp[2]);

		//간선 정보를 입력받을 2차원 어레이리스트 초기화
		for(int i=0; i<N; i++) {
			graph.add(new ArrayList<Node>());
		}
		
		//보석을 가진 섬들을 차례대로 인덱스를 부여하여 해시맵에 저장함
		for(int i=0; i<K; i++) {
			index.put(Integer.parseInt(br.readLine())-1, i);
		}
		
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			//편의를 위해 섬의 번호가 0부터 시작하도록 함
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2]);
			
			//양방향 간선이 될 수 있도록 A->B, B->A 두 간선을 추가함
			graph.get(A).add(new Node(B,0,C));
			graph.get(B).add(new Node(A,0,C));
		}
		
		bw.write(BFS()+"\n");
		
		bw.flush();
		bw.close();
		br.close();
	}
}