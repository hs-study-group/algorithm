//https://www.acmicpc.net/problem/14438

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int[] arr;
	static int[] tree;

	//세그먼트 트리를 초기화 하는 메소드
	public static void init() {
		init(1,0,arr.length-1);
	}
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int node, int start, int end) {
		//리프노드라면
		if(start==end) {
			//리프노드의 값을 해당 배열의 값으로 설정함
			tree[node] = arr[start];
			return;
		//리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식 노드에 대하여 초기화를 재귀적으로 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//두 자식노드의 값중 더 작은 값을 갖는 노드를 부모노드로 설정함
			if(tree[node*2]<=tree[node*2+1]) {
				tree[node] = tree[node*2];
			}else {
				tree[node] = tree[node*2+1];
			}
			return;
		}
	}
	
	//특정 인덱스의 값을 다른 값으로 갱신하고, 세그먼트 트리를 재귀적으로 갱신하는 메소드
	public static void update(int idx, int val) {
		update(1,0,arr.length-1,idx,val);
	}
	
	//특정 인덱스의 값을 다른 값으로 갱신하고, 세그먼트 트리를 재귀적으로 갱신하는 메소드
	public static void update(int node, int start, int end, int idx, int val) {
		//변경하고자 하는 인덱스가 현재 탐색중인 구간을 벗어났다면
		if(!(start<=idx&&idx<=end)) {
			//즉시 종료함
			return;
		//리프노드라면
		}else if(start==end) {
			//해당 인덱스의 값과 세그먼트 트리의 노드의 값을 val로 갱신함
			arr[idx] = val;
			tree[node] = val;
			return;
		//현재 탐색중인 구간이 탐색해야할 구간에 걸치는 경우
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대해 재귀적으로 갱신을 수행함
			update(node*2,start,mid,idx,val);
			update(node*2+1,mid+1,end,idx,val);
			
			//두 자식노드중 더 작은 값을 갖는 노드의 값을 부모노드의 값으로 함
			if(tree[node*2]<=tree[node*2+1]) {
				tree[node] = tree[node*2];
			}else {
				tree[node] = tree[node*2+1];
			}
			return;
		}
	}
	
	//주어진 구간 left, right에 대하여 가장 작은 값을 구하는 메소드
	public static int query(int left, int right) {
		return query(1,0,arr.length-1,left,right);
	}
	
	//주어진 구간 left, right에 대하여 가장 작은 값을 구하는 메소드
	public static int query(int node, int start, int end, int left, int right) {
		//현재 탐색중인 구간이 탐색해야할 구간을 벗어난 경우
		if(start>right||end<left) {
			//최대값을 리턴하여 쿼리 결과에 영향이 없도록 함
			return Integer.MAX_VALUE;
		//현재 탐색중인 구간이 탐색해야할 구간에 완전히 포함되는 경우
		}else if(left<=start&&end<=right) {
			//트리 노드의 값을 리턴함
			return tree[node];
		//탐색중인 구간이 탐색해야할 구간에 걸치는 경우
		}else {
			//두 자식노드에 대한 쿼리 결과중 더 작은 값을 갖는 쿼리의 결과를 리턴함
			return Math.min(query(node*2,start,(start+end)/2,left,right), query(node*2+1,(start+end)/2+1,end,left,right));
		}
	}

	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		tree = new int[4*N];
		
		String[] temp = br.readLine().split(" ");
		
		//배열에 값을 입력받음
		for(int i=0; i<arr.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init();
		
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			//1번 쿼리의 경우 특정 인덱스 값을 변경함
			if(A==1) {
				int B = Integer.parseInt(temp[1])-1;
				int C = Integer.parseInt(temp[2]);
				update(B,C);
			//2번 쿼리의 경우 주어진 구간에서의 최소값을 구함
			}else {
				int B = Integer.parseInt(temp[1])-1;
				int C = Integer.parseInt(temp[2])-1;
				bw.write(query(B,C)+"\n");
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}