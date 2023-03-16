import java.io.*;
import java.util.*;

//트리의 level, 해당 레벨에서의 index, 여태까지 얻은 점수 point를 저장하는 노드 클래스
class Node implements Comparable<Node>{
	final int val;
	int level, idx, point;
	boolean flag;
	
	//노드 클래스는 flag변수가 true이냐 false이냐에 따라 우선순위큐에서 다르게 정렬됨
	Node(int level, int idx, int point, boolean flag){
		this.level = level;
		this.idx = idx;
		this.point = point;
		if(flag){
			this.val = 1;
		}else{
			this.val = -1;
		}
	}
	
	//flag가 true라면 점수가 더 큰 노드가 먼저 반환되며, 아니라면 더 작은 노드가 먼저 반환됨
	//점수가 동일하다면 flag가 true라면 레벨이 더 작은 노드가 먼저 반환되며, false라면 레벨이 더 큰 노드가 먼저 반환됨
	@Override
	public int compareTo(Node n){
		if(this.point>n.point){
			return -1*val;
		}else if(this.point<n.point){
			return 1*val;
		}else{
			if(this.level<n.level){
				return -1*val;
			}else if(this.level>n.level){
				return 1*val;
			}else{
				return 0;
			}
		}
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//어떤 인덱스에 대해, 자식들의 인덱스를 구하는 메소드
	public static int[] getChildren(int idx){
		return new int[]{idx*2,idx*2+1};
	}
	
	//어떤 문자열을 점수로 변환하는 메소드
	public static int getPoint(char ch){
		return ch-'A'+1;
	}
	
	//flag가 true라면 가장 큰 점수를, 아니라면 가장 작은 점수를 구하는 BFS메소드
	public static int BFS(char[][] arr, boolean flag){
		//BFS탐색에 사용할 우선순위 큐
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		
		//마지막 level까지 도달했을때의 점수를 오름차순 또는 내림차순 정렬하여 저장할 우선순위 큐
		PriorityQueue<Node> rq = new PriorityQueue<Node>();;
		
		//flag가 true라면 점수가 큰 순서대로 정렬되며
		//flag가 false라면 점수가 작은 순서대로 정렬됨
		pq.add(new Node(0,0,getPoint(arr[0][0]),flag));
		
		while(!pq.isEmpty()){
			Node n = pq.poll();
			
			int level = n.level;
			int point = n.point;
			int idx = n.idx;

			//만약 트리의 마지막 level까지 모두 탐색했다면
			if(level==arr.length-1){
				//그때의 노드를 결과 우선순위 큐에 저장함
				rq.add(n);
				continue;
			}
			
			//자식들의 인덱스를 얻음
			int[] children = getChildren(idx);
			
			//두 자식들을 선택했을때의 점수를 계산하여 우선순위 큐에 추가함
			pq.add(new Node(level+1,children[0],point+getPoint(arr[level+1][children[0]]),flag));
			pq.add(new Node(level+1,children[1],point+getPoint(arr[level+1][children[1]]),flag));
		}
		
		//결과 큐의 가장 맨 앞의 점수가 구하고자하는 값임
		return rq.peek().point;
	}
	
	public static void main(String[] args) throws Exception {
		final int N = Integer.parseInt(br.readLine());
		
		//트리의 정보를 저장할 배열선언
		char[][] arr = new char[N][1<<(N-1)];
		
		//문자를 입력받음
		for(int i=0;i<N;i++){
			arr[i] = br.readLine().toCharArray();
		}
		
		//가장 작을때의 점수와 클 때의 점수를 출력함
		bw.write(BFS(arr, false)+"\n");
		bw.write(BFS(arr, true)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}