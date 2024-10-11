//https://www.acmicpc.net/problem/11962

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static long[] arr;
	static long[][] tree;
	static long[] lazy;
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int node, int start, int end) {
		//현재 탐색중인 노드가 리프노드라면
		if(start==end) {
			//tree[node][0]에는 구간의 합을 저장함
			//tree[node][1]에는 구간의 최소값을 저장함
			tree[node][0] = arr[start];
			tree[node][1] = arr[start];
			return;
		//리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식 노드에 대하여 재귀적으로 초기화를 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//두 자식노드의 값을 적절히 조합하여 그 결과를 저장할 배열
			long[] r = new long[2];
			
			//부모 노드의 구간합은 두 자식노드의 구간합을 합한 값임
			r[0] = tree[node*2][0]+tree[node*2+1][0];
			
			//부모 노드의 최소값은, 두 자식노드의 값중 더 작은 값임
			r[1] = Math.min(tree[node*2][1], tree[node*2+1][1]);

			//부모노드의 구간합 및 최소값을 설정함
			tree[node] = r;
			return;
		}
	}
	
	//left, right 구간에 대하여 구간 합을 구하는 메소드
	public static long sQuery(int node, int start, int end, int left, int right) {
		//lazy값이 남아있다면 이를 처리함
		process(node,start,end);
		
		//탐색해야할 구간을 완전히 벗어난 경우
		if(start>right||end<left) {
			//0을 리턴하여 쿼리 결과에 영향을 주지 않도록 함
			return 0;
		//탐색해야할 구간에 완전히 포함된 경우
		}else if(left<=start&&end<=right) {
			//해당 노드의 구간합을 리턴함
			return tree[node][0];
		//탐색해야할 구간에 걸치는 경우
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 동일한 쿼리를 수행하고 그 결과의 합을 리턴함
			return sQuery(node*2,start,mid,left,right)+sQuery(node*2+1,mid+1,end,left,right);
		}
	}
	
	//left, right 구간에 대하여 가장 작은 값을 리턴하는 메소드
	public static long mQuery(int node, int start, int end, int left, int right) {
		//lazy값이 남아있다면 이를 처리함
		process(node,start,end);
		
		//탐색해야할 구간을 완전히 벗어난 경우
		if(start>right||end<left) {
			//MAX_VALUE를 리턴하여 쿼리 결과에 영향을 주지 않도록 함
			return Long.MAX_VALUE;
		//탐색해야할 구간에 완전히 포함된 경우	
		}else if(left<=start&&end<=right) {
			//해당 노드의 최소값을 리턴함
			return tree[node][1];
		//탐색해야할 구간에 걸치는 경우	
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 동일한 쿼리를 수행하고 그 두 값중 더 작은 값을 리턴함
			return Math.min(mQuery(node*2,start,mid,left,right),mQuery(node*2+1,mid+1,end,left,right));
		}
	}
	
	//특정 구간의 값을 val만큼 증가시키는 메소드
	public static void update(int node, int start, int end, int left, int right, long val) {
		//lazy값이 남아있다면 이를 처리함
		process(node,start,end);
		
		//탐색해야할 구간을 완전히 벗어난 경우
		if(start>right||end<left) {
			//그 즉시 종료함
			return;
		//탐색해야할 구간에 완전히 포함된 경우
		}else if(left<=start&&end<=right) {
			//부모노드의 구간합은 자식노드의 수 * val만큼 미리 증가시킴
			tree[node][0] += (end-start+1)*val;
			
			//부모노드의 최소값은 기존 최소값에서 val만큼 증가시킴
			tree[node][1] += val;
			
			//리프노드가 아니라면
			if(start!=end) {
				//두 자식노드에게 가중치를 전파함
				lazy[node*2] += val;
				lazy[node*2+1] += val;
			}
			
			return;
		//탐색해야할 구간에 걸치는 경우	
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 재귀적으로 갱신을 수행함
			update(node*2,start,mid,left,right,val);
			update(node*2+1,mid+1,end,left,right,val);
			
			//부모노드의 구간합은 두 자식노드의 구간합의 합으로 설정함
			tree[node][0] = tree[node*2][0] + tree[node*2+1][0];
			
			//부모노드의 최소값은 두 자식노드의 최소값중 더 작은 값으로 설정함
			tree[node][1] = Math.min(tree[node*2][1], tree[node*2+1][1]);
			return;
		}
	}
	
	//lazy값이 남아있다면 이를 처리하는 메소드
	public static void process(int node, int start, int end) {
		//lazy값이 남아있다면
		if(lazy[node]!=0) {
			//부모 노드의 구간합은 노드의 수 * lazy값만큼 미리 증가시킴
			tree[node][0] += lazy[node]*(end-start+1);
			
			//부모 노드의 최소값은 기존보다 lazy만큼 증가시킴
			tree[node][1] += lazy[node];
			
			//리프노드가 아니라면
			if(start!=end) {
				//두 자식노드에게 lazy를 전파함
				lazy[node*2] += lazy[node];
				lazy[node*2+1] += lazy[node];
			}
			
			//lazy를 초기화 함
			lazy[node] = 0;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int Q = Integer.parseInt(temp[1]);
		
		arr = new long[N];
		
		//세그먼트 트리의 노드는 구간합 및 최소값을 저장함
		tree = new long[4*N][2];
		lazy = new long[4*N];
		
		temp = br.readLine().split(" ");
		
		//배열의 정보를 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		for(int i=0; i<Q; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[1])-1;
			int B = Integer.parseInt(temp[2])-1;
			
			//S 쿼리라면
			if(temp[0].equals("S")) {
				//구간합을 출력함
				bw.write(sQuery(1,0,N-1,A,B)+"\n");
			//M 쿼리라면
			}else if(temp[0].equals("M")) {
				//구간의 최소값을 출력함
				bw.write(mQuery(1,0,N-1,A,B)+"\n");
			//P 쿼리라면
			}else {
				//해당 구간에 포함되는 숫자를 C만큼 증가시킴
				long C = Long.parseLong(temp[3]);
				update(1,0,N-1,A,B,C);
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}