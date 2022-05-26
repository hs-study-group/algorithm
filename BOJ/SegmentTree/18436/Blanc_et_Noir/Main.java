import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	//세그먼트 트리를 2차원으로 구성
	public static int[][] tree;
	public static int[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//세그먼트트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		
		//리프노드일경우 리프노드에 홀수 또는 짝수의 갯수를 기록함
		if(start == end) {
			tree[node][arr[start]%2] = 1;
			return;
		//리프노드가 아닐경우
		}else {
			int mid = (start+end)/2;
			
			//재귀적으로 탐색을 실시함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//부모노드의 값은 두 자식노드의 홀수, 짝수의 개수를 합한 값을 가지고 있음
			tree[node][0] = tree[node*2][0] + tree[node*2+1][0];
			tree[node][1] = tree[node*2][1] + tree[node*2+1][1];
			
			return;
		}		
	}
	
	//세그먼트 트리의 특정 구간에서의 홀수, 짝수의 갯수를 리턴하는 쿼리 메소드
	public static int query(int node,int Q, int start, int end, int left, int right) {
		
		//탐색하고있는 범위가 탐색하고자 하는 범위를 벗어난 경우
		if(left>end||right<start) {
			//0을 리턴함으로써 부모노드에 영향을 주지 않도록 함
			return 0;			
		}
		
		//탐색하고있는 범위가 탐색하고자 하는 범위 안에 완전히 포함된 경우
		if(left<=start&&right>=end) {
			//홀수 또는 짝수의 갯수를 리턴함
			return tree[node][Q];
		}
		
		int mid = (start+end)/2;
		
		//두 자식노드로부터 쿼리를 진행한 값을 합함
		return query(node*2,Q,start,mid,left,right)+query(node*2+1,Q,mid+1,end,left,right);
	}
	
	//특정 배열의 값을 업데이트하고, 그에따라 재귀적으로 세그먼트 트리를 업데이트하는 메소드
	public static void update(int node, int idx, int val, int start, int end) {
		//해당 인덱스가 탐색범위를 벗어났으면 종료
		if(idx<start||idx>end) {
			return;
		}
		
		//리프노드인경우
		if(start==end) {
			//기존 배열에 저장된 값이 짝수인지 홀수인지 임시로 저장함
			int b = arr[idx]%2;
			
			//실제 배열을 업데이트함
			arr[idx] = val;
			
			//기존에 저장된 짝수 또는 홀수의 갯수를 감소시키고
			tree[node][b] = tree[node][b]-1;
			
			//새로 저장할 짝수 또는 홀수의 갯수를 증가시킴
			tree[node][val%2] = tree[node][val%2]+1;
			return;
		}
		
		int mid = (start+end)/2;
		
		//재귀적으로 업데이트를 수행함
		update(node*2,idx,val,start,mid);
		update(node*2+1,idx,val,mid+1,end);
		
		//부모노드의 값은 두 자식노드의 값의 합으로 업데이트함
		tree[node][0] = tree[node*2][0]+tree[node*2+1][0];
		tree[node][1] = tree[node*2][1]+tree[node*2+1][1];
		
		return;
	}
	
	public static void main(String[] args) throws Exception{
		
		int N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		tree = new int[N*3][2];
		
		String[] str = br.readLine().split(" ");
		
		for(int i=0; i<str.length; i++) {
			arr[i] = Integer.parseInt(str[i]);
		}
		
		init(1,0,arr.length-1);
		
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			str = br.readLine().split(" ");
			
			//Q는 수행할 명령을 의미
			//1이면 업데이트, 2이면 짝수, 3이면 홀수의 갯수 리턴
			//편의상 2%2, 2%3과 같이 0, 1을 각각 짝수, 홀수의 갯수를 저장할 트리 인덱스로 사용
			int Q = Integer.parseInt(str[0]);
			int A = Integer.parseInt(str[1]);
			int B = Integer.parseInt(str[2]);
			
			switch(Q) {
				case 1:
					update(1,A-1,B,0,arr.length-1);
					break;
				default :
					bw.write(query(1,Q%2,0,arr.length-1,A-1,B-1)+"\n");
			}
			
		}
		bw.flush();
	}
}