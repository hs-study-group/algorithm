//https://www.acmicpc.net/problem/16234

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

//y, x좌표를 저장하는 노드 클래스 선언
class Node{
	int y, x;
	Node(int y, int x){
		this.y=y;
		this.x=x;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//아직 탐색하지 않은 국가를 찾고, BFS탐색을 수행하며, 인구이동이 한 번이라도 발생한 적 있는지 없는지를 리턴하는 메소드
	public static boolean find(int[][] arr, boolean[][] v, int L, int R) {
		//flag가 true이면 한 번이라도 인구이동이 발생한 적이 있다는 의미
		boolean flag = false;
		
		//완전탐색으로 아직 탐색하지 않은 국가를 찾음
		for(int i=0;i<v.length;i++) {
			for(int j=0; j<v.length;j++) {
				if(!v[i][j]) {
					//인구이동이 한 번이라도 발생한 적이 있다면 flag는 true가 됨
					flag |= BFS(arr,v, new Node(i,j), L, R);
				}
			}
		}
		
		return flag;
	}
	
	//탐색하려는 위치가 범위를 벗어나는지 아닌지를 리턴하는 메소드
	public static boolean isNotOutOfRange(int[][] arr, int y, int x) {
		if(y>=0&&y<arr.length&&x>=0&&x<arr.length) {
			return true;
		}
		
		return false;
	}
	
	//인구 차이가 L이상, R이하인지 아닌지를 리턴하는 메소드
	public static boolean check(int v1, int v2, int L, int R) {
		int abs = Math.abs(v1-v2);
		
		if(abs>=L&&abs<=R) {
			return true;
		}
		
		return false;
	}
	
	//node 위치를 기준으로, 인구 차이가 L이상, R이하인 인접한 국가들을 탐색하고
	//인구 이동의 결과를 적용하며, 인구이동이 한 번이라도 발생했는지를 리턴하는 메소드
	public static boolean BFS(int[][] arr, boolean[][] v, Node node, int L, int R) {
		//BFS 탐색에 사용할 큐
		Queue<Node> q = new LinkedList<Node>();
		
		//같은 그룹에 포함되는 모든 국가들의 정보를 저장할 큐
		Queue<Node> temp = new LinkedList<Node>();
		
		//탐색을 시작할 국가를 각각의 큐에 추가하고 방문처리함
		temp.add(node);
		q.add(node);
		v[node.y][node.x] = true;
		
		//인접한 국가를 탐색하기 위한 벡터 배열
		int[][] dist = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
		
		//인접한 국가들의 인구 총 합을 저장할 변수, 시작 노드 값을 초기값으로 사용함
		int sum = arr[node.y][node.x];
		
		while(!q.isEmpty()) {
			Node n = q.poll();
			
			for(int i=0; i<dist.length; i++) {
				int y = n.y + dist[i][0];
				int x = n.x + dist[i][1];
				
				//인접한 위치가 아직 방문하지 않은 곳이고, 인구 차이가 L이상 R이하라면
				if(isNotOutOfRange(arr,y,x)&&!v[y][x]&&check(arr[n.y][n.x],arr[y][x],L,R)) {
					//각각의 큐에 노드 정보 추가
					q.add(new Node(y,x));
					temp.add(new Node(y,x));
					
					//인접 국가의 인구수를 누적하여 더함
					sum += arr[y][x];
					
					//방문처리함
					v[y][x]=true;
				}
			}
		}
		
		//만약 같은 그룹에 포함되는 국가가 2개 이상이라면 인구이동이 발생했다는 것임
		if(temp.size()>=2) {
			//각 국가의 인구수 총합의 평균을 구함
			int num = sum / temp.size();
			
			//그룹에 포함되는 모든 국가들의 인구수를 평균치로 변경함
			for(Node n : temp) {
				arr[n.y][n.x] = num;
			}
			
			//인구 이동이 발생했음을 의미하도록 true 리턴
			return true;
		}
		
		//인구 이동이 발생하지 않았으므로 false 리턴
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		final int N = Integer.parseInt(temp[0]);
		final int L = Integer.parseInt(temp[1]);
		final int R = Integer.parseInt(temp[2]);
		
		//각 국가들의 인구수 정보를 저장할 배열
		int[][] arr = new int[N][N];
		
		//국가들의 인구 정보를 입력 받음
		for(int i=0; i<N; i++) {
			temp = br.readLine().split(" ");
			for(int j=0; j<N; j++) {
				arr[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		//인구 이동이 며칠동안 발생했는지 기록할 변수
		int cnt = 0;
		
		while(true) {
			//방문 배열
			boolean[][] v = new boolean[N][N];
			
			//한 번이라도 인구이동이 발생했는지 아닌지를 저장함
			boolean flag = find(arr,v,L,R);
			
			//한 번도 인구이동이 발생하지 않았다면
			if(!flag) {
				//반복을 종료함
				break;
			}
			
			//인구 이동이 발생한 일수를 1증가시킴
			cnt++;
		}
		
		bw.write(cnt+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}