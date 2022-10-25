//https://www.acmicpc.net/problem/14427

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int[] arr;
	static int[][] tree;
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int node, int start, int end) {
		//리프노드라면
		if(start==end) {
			//해당 트리의 노드에는 정수값과 인덱스값을 저장함
			tree[node][0] = arr[start];
			tree[node][1] = start;
		//리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 재귀적으로 초기화를 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//두 자식노드중 더 작은값을 가진 노드의 값과 인덱스가 부모노드의 값과 인덱스가 되도록 하며
			//만약 두 자식노드의 값이 같다면, 인덱스가 더 작은 노드의 것을 부모 노드의값과 인덱스로 함
			if(tree[node*2][0]<tree[node*2+1][0]) {
				tree[node][0] = tree[node*2][0];
				tree[node][1] = tree[node*2][1];
			}else if(tree[node*2][0]>tree[node*2+1][0]) {
				tree[node][0] = tree[node*2+1][0];
				tree[node][1] = tree[node*2+1][1];
			}else {
				if(tree[node*2][1]<=tree[node*2+1][1]) {
					tree[node][0] = tree[node*2][0];
					tree[node][1] = tree[node*2][1];
				}else {
					tree[node][0] = tree[node*2+1][0];
					tree[node][1] = tree[node*2+1][1];
				}
			}
		}
	}
	
	//특정 인덱스의 값을 val로 변경하는 메소드
	public static void update(int node, int start, int end, int idx, int val) {
		//만약 현재 탐색중인 범위가 탐색해야할 범위를 완전히 벗어났다면
		if(!(start <= idx && idx <= end)) {
			//업데이트 하지않고 종료함
			return;
		//만약 리프노드라면
		}else if(start == end) {
			//해당 리프노드의 값과 인덱스, 배열의 값을 갱신함
			arr[idx] = val;
			tree[node][0] = val;
			tree[node][1] = start;
		//만약 현재 탐색중인 범위가 탐색해야할 범위에 포함되는 경우
		}else {
			int mid = (start+end)/2;
			
			//재귀적으로 두 자식노드의 값과 인덱스를 갱신함
			update(node*2,start,mid,idx,val);
			update(node*2+1,mid+1,end,idx,val);
			
			//두 자식노드중 더 작은값을 가진 노드의 값과 인덱스가 부모노드의 값과 인덱스가 되도록 하며
			//만약 두 자식노드의 값이 같다면, 인덱스가 더 작은 노드의 것을 부모 노드의값과 인덱스로 함
			if(tree[node*2][0]<tree[node*2+1][0]) {
				tree[node][0] = tree[node*2][0];
				tree[node][1] = tree[node*2][1];
			}else if(tree[node*2][0]>tree[node*2+1][0]) {
				tree[node][0] = tree[node*2+1][0];
				tree[node][1] = tree[node*2+1][1];
			}else {
				if(tree[node*2][1]<=tree[node*2+1][1]) {
					tree[node][0] = tree[node*2][0];
					tree[node][1] = tree[node*2][1];
				}else {
					tree[node][0] = tree[node*2+1][0];
					tree[node][1] = tree[node*2+1][1];
				}
			}
		}
	}
	
	//left, right 구간에 대하여 가장 작은 값을 갖는 노드의 인덱스를 리턴하는 메소드
	//값이 같은 노드가 여럿이라면 그중 인덱스가 가장 작은 노드의 인덱스를 리턴함
	public static int[] query(int node, int start, int end, int left, int right) {
		//현재 탐색중인 구간이 탐색해야할 구간을 완전히 벗어나는 경우
		if(start>right||end<left) {
			//값을 MAX_VALUE로 함으로써 결과에 영향을 주지 않도록 함
			return new int[] {Integer.MAX_VALUE,-1};
		//현재 탐색중인 구간이 탐색해야할 구간에 완전히 포함되는 경우
		}else if(left<=start&&end<=right) {
			//해당 노드 정보를 리턴함
			return tree[node];
		//현재 탐색중인 구간이 탐색해야할 구간에 걸치는 경우
		}else {
			int mid = (start+end)/2;
			
			//재귀적으로 두 자식노드에 대하여 쿼리를 수행함
			int[] r1 = query(node*2,start,mid,left,right);
			int[] r2 = query(node*2+1,mid+1,end,left,right);
			
			//두 자식노드중 값이 더 작은 노드의 값과 인덱스를 반환하며
			//만약 두 노드의 값이 동일하다면, 인덱스가 더 작은 노드의 값과 인덱스를 반환함
			if(r1[1]!=-1&&r2[1]!=-1) {
				if(r1[0]<r2[0]) {
					return r1;
				}else if(r1[0]>r2[0]) {
					return r2;
				}else {
					if(r1[1]<r2[1]) {
						return r1;
					}else {
						return r2;
					}
				}
			}else if(r1[1]!=-1) {
				return r1;
			}else {
				return r2;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		
		//세그먼트 트리의 크기는 일반적으로 배열의 크기의 4배면 충분하며
		//트리의 노드는 값과 인덱스를 저장하므로 2차원으로 선언해야함
		tree = new int[4*N][2];
		
		String[] temp = br.readLine().split(" ");
		
		//배열에 값을 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			
			//만약 A가 1이라면
			if(A==1) {
				//B위치의 값을 C로 변경함
				int B = Integer.parseInt(temp[1])-1;
				int C = Integer.parseInt(temp[2]);
				update(1,0,N-1,B,C);
			//만약 A가 2라면
			}else {
				//모든 구간에 대하여 크기가 가장 작은 값의 인덱스를 출력함
				//만약 그러한 값이 여러개라면 그중에서 가장 작은 인덱스를 출력함
				bw.write((query(1,0,N-1,0,N-1)[1]+1)+"\n");
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}