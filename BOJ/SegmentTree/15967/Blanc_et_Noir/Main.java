//https://www.acmicpc.net/problem/15967

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static long tree[];
	static long lazy[];
	static long arr[];
	
	//세그먼트 트리 초기화 메소드
	public static void init(int node, int start, int end) {
		//리프노드일 경우에는
		if(start==end) {
			//해당 노드에 배열의 값을 저장함
			tree[node] = arr[start];
			return;
		//리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대해 초기화를 재귀적으로 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//부모 노드의 값은 두 자식노드의 값의 합임
			tree[node] = tree[node*2]+tree[node*2+1];
			return;
		}
	}
	
	//주어진 구간의 모든 값들의 합을 구하는 쿼리 메소드
	public static long query(int node, int start, int end, int left, int right) {
		//만약 해당 노드에서 미처 처리하지 못하고 남은 lazy값이 있다면 처리함
		process(node,start,end);
		
		//탐색 범위를 벗어나면
		if(start>right||end<left) {
			//결과에 영향이 없도록 0을 리턴함
			return 0;
		//현재 탐색중인 범위가 입력받은 구간에 완전히 포함이 되면
		}else if(left<=start&&end<=right) {
			//해당 노드의 값을 반환함, 이 값은 start와 end에 포함된 숫자들의 합임
			return tree[node];
		//만약 현재 탐색중인 범위가 반쯤 살짝 걸쳐있다면
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 재귀적으로 쿼리를 수행하고 그 결과의 합을 반환함
			return query(node*2,start,mid,left,right) + query(node*2+1,mid+1,end,left,right);
		}
	}
	
	//느리게 갱신되는 세그먼트 트리에서 해당 노드에 도달했을때, lazy값이 남아있다면 이를 청산하는 메소드
	public static void process(int node, int start, int end) {
		//lazy값이 남아있다면
		if(lazy[node]!=0) {
			//자식 노드의 개수 * lazy값만큼을 더함
			tree[node] += (end-start+1)*lazy[node];
			
			//만약 현재 노드가 리프노드가 아니라면
			if(start!=end) {
				//두 자식노드에게도 lazy값을 전파함
				lazy[node*2] += lazy[node];
				lazy[node*2+1] += lazy[node];
			}
			
			//lazy값을 0으로 하여 청산이 완료되었음을 표시함
			lazy[node] = 0;
		}
	}
	
	//주어진 구간 left, right에 속한 모든 정수에 val값을 더하거나 빼는 update 메소드
	public static void update(int node, int start, int end, int left, int right, int val) {
		//update 수행이전에 처리해야할 lazy값이 남아있다면 lazy값을 청산함
		process(node,start,end);
		
		//현재 탐색중인 구간이 입력받은 구간을 완전히 벗어났으면
		if(start>right||end<left) {
			//그 즉시 종료함
			return;
		//만약 입력받은 구간이 현재 탐색중인 구간을 완전히 포함한다면
		}else if(left<=start&&end<=right) {
			//부모 노드는 자식노드의 개수 * val값만큼 증가 또는 감소시킴
			tree[node] += (end-start+1)*val;
			
			//자신이 리프노드가 아니라면
			if(start!=end) {
				//자식들에게도 val만큼의 lazy를 전파함
				lazy[node*2] += val;
				lazy[node*2+1] += val;
			}
			
			return;
			
		//만약 현재 탐색중인 구간이 반쯤 걸치는 구간이라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식 노드에 대하여 재귀적으로 update를 수행함
			update(node*2,start,mid,left,right,val);
			update(node*2+1,mid+1,end,left,right,val);
			
			//부모노드의 값은 두 자식노드의 값의 합임
			tree[node] = tree[node*2]+tree[node*2+1];
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" "); 
		int N = Integer.parseInt(temp[0]);
		int Q = Integer.parseInt(temp[1]) + Integer.parseInt(temp[2]);
		
		arr = new long[N];
		
		//세그먼트 트리의 크기는 일반적으로 배열의 크기의 4배면 충분함
		tree = new long[4*N];
		lazy = new long[4*N];
		
		temp = br.readLine().split(" ");
		
		//배열에 초기 값들을 입력 받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		for(int i=0; i<Q; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			int B = Integer.parseInt(temp[1])-1;
			int C = Integer.parseInt(temp[2])-1;
			//구간 합을 출력하는 요청일 경우
			if(A==1) {
				//B, C구간의 정수들의 합을 출력함
				bw.write(query(1,0,N-1,B,C)+"\n");
			//갱신 요청일 경우
			}else {
				int D = Integer.parseInt(temp[3]);
				//B, C구간의 정수들에 D값을 더함
				update(1,0,N-1,B,C,D);
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}