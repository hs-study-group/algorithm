//https://www.acmicpc.net/problem/1725

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[] arr;
	static int[][] tree;
	
	//히스토그램에서의 직사각형의 최대 크기
	static int max = Integer.MIN_VALUE;
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		if(start==end) {
			//해당 트리의 노드에 히스토그램의 높이와 그 인덱스를 저장함
			tree[node][0] = arr[start];
			tree[node][1] = start;
			return;
		}else {
			int mid = (start+end)/2;
			
			//재귀적으로 두 자식노드에 대해서도 초기화를 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//부모노드의 높이와 인덱스는 자식 노드중 높이가 더 작은 노드의 것으로 함
			if(tree[node*2][0]<tree[node*2+1][0]) {
				tree[node][0] = tree[node*2][0];
				tree[node][1] = tree[node*2][1];
			}else {
				tree[node][0] = tree[node*2+1][0];
				tree[node][1] = tree[node*2+1][1];
			}
			return;
		}
	}
	
	//left, right 구간에 대하여 히스토그램의 최소 높이를 구함
	public static int[] query(int node, int start, int end, int left, int right) {
		//현재 탐색중인 구간이 탐색해야할 구간을 완전히 벗어난 경우
		if(start>right||end<left) {
			//높이는 MAX를 리턴하여 결과에 영향이 없도록 함
			return new int[] {Integer.MAX_VALUE,-1};
		//현재 탐색중인 구간이 탐색해야할 구간에 완전히 포함된 경우
		}else if(left<=start&&end<=right) {
			//해당 노드 정보를 리턴함
			return tree[node];
		//현재 탐색중인 구간이 탐색해야할 구간에 걸치는 경우
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 쿼리를 재귀적으로 수행함
			int[] r1 = query(node*2,start,mid,left,right);
			int[] r2 = query(node*2+1,mid+1,end,left,right);
			
			//두 자식노드의 결과중 히스토그램의 높이가 더 작은 노드의 값을 리턴함
			if(r1[0]<r2[0]) {
				return r1;
			}else {
				return r2;
			}
		}
	}
	
	//히스토그램의 높이가 최소가 되는 지점을 찾고, 그 지점을 제외한 나머지 양쪽 구간에 대해 쿼리를 재귀적으로 수행하는 메소드
	public static void recursive(int left, int right) {
		//left가 right보다 우측에 존재하면
		if(left>right) {
			//모든 탐색을 마친 것이므로 return함
			return;
	
		//left right구간이 유효하다면
		}else {
			//해당 left right 구간에 대하여 히스토그램의 최소 높이를 구함
			int[] r = query(1,0,arr.length-1,left,right);
			
			//히스토그램에서의 직사각형의 넓이를 구한후, 그것이 최대 넓이보다 크다면 그것으로 최대 넓이를 갱신함
			max = Math.max(max,r[0] * (right-left+1));
			
			//히스토그램의 높이가 최소가 되는 지점을 기준으로 양쪽 구간에 대하여 재귀적으로 탐색함
			recursive(left,r[1]-1);
			recursive(r[1]+1,right);
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//세그먼트 트리의 사이즈는 N을 기준으로 아래와 같이 계산할 수 있음 
		final int TREE_SIZE = 1<<((int) Math.ceil(Math.log(N)/Math.log(2))+1);
		arr = new int[N];
		tree = new int[TREE_SIZE][2];
		
		//히스토그램의 높이를 입력받음
		for(int i=0; i<N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		//재귀적으로 히스토그램의 높이가 최소가 되는 지점을 찾고 그때의 넓이를 구함
		recursive(0,N-1);
		
		//최대 넓이를 출력함
		bw.write(max+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}