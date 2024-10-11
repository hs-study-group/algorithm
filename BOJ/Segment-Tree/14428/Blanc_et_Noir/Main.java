//https://www.acmicpc.net/problem/14428

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//세그먼트 트리는 최소값과 그 최소값의 인덱스를 저장함
	static int[][] tree;
	static int[] arr;
	
	//두 노드에 대하여 더 작은 값을 가진 노드를 반환, 값이 같다면 인덱스가 더 작은 노드를 반환하여 merge( )하는 메소드
	public static int[] merge(int[] arr1, int[] arr2) {
		if(arr1[0]<arr2[0]) {
			return arr1;
		}else if(arr1[0]>arr2[0]) {
			return arr2;
		}else {
			if(arr1[1]<arr2[1]) {
				return arr1;
			}else {
				return arr2;
			}
		}
	}
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		//만약 리프노드라면
		if(start==end) {
			//세그먼트 트리에 해당 값과 해당 인덱스를 저장함
			tree[node][0] = arr[start];
			tree[node][1] = start;
			return;
		//리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//왼쪽 및 오른쪽 자식 노드에 대하여 재귀적으로 init( )을 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//두 자식 노드중에서 더 작은 값을 가진 노드의 것을 그대로 부모의 것으로 사용하도록 merger( )함
			tree[node] = merge(tree[node*2],tree[node*2+1]);
			return;
		}
	}
	
	public static void update(int node, int start, int end, int idx, int val) {
		//인덱스가 탐색범위를 벗어났으면
		if(idx>end||idx<start) {
			//그대로 종료함
			return;
		//리프노드라면
		}else if(start==end) {
			//배열의 값을 갱신함
			arr[idx] = val;
			
			//세그먼트 트리의 값과 인덱스 또한 갱신함
			tree[node][0] = val;
			tree[node][1] = idx;
			return;
		}else {
			int mid = (start+end)/2;
			
			//두 자식 노드에 대하여 update( )를 재귀적으로 수행함
			update(node*2,start,mid,idx,val);
			update(node*2+1,mid+1,end,idx,val);
			
			//두 자식 노드중에서 더 작은 값을 가진 노드의 것을 그대로 부모의 것으로 사용하도록 merger( )함
			tree[node] = merge(tree[node*2],tree[node*2+1]);
			return;
		}
	}
	
	//주어진 구간에 대하여 최소값과 그 인덱스를 조회하는 query( )메소드
	public static int[] query(int node, int start, int end, int left, int right) {
		//현재 탐색중인 구간이 완전히 벗어난 구간이면
		if(left>end||right<start) {
			//값이 MAX_VALUE, 인덱스 또한 MAX_VALUE인 노드를 리턴하여 쿼리 결과에 영향을 주지 않도록 함
			return new int[] {Integer.MAX_VALUE,Integer.MAX_VALUE};
		//현재 탐색중인 구간이 완전히 포함되는 구간이라면
		}else if(left<=start&&end<=right) {
			//해당 세그먼트 트리 노드를 리턴함
			return tree[node];
		//만약 탐색중인 구간이 살짝 걸치는 경우라면
		}else {
			int mid = (start+end)/2;
			//왼쪽 및 오른쪽 자식 노드에 대하여 query( )를 재귀적으로 수행하고, 그 결과를 merge( )함
			return merge(query(node*2,start,mid,left,right),query(node*2+1,mid+1,end,left,right));
		}
	}
	
	public static void main(String[] args) throws Exception{
		//배열의 크기를 입력받음
		int N = Integer.parseInt(br.readLine());
		
		//배열 및 세그먼트 트리를 선언함
		arr = new int[N];
		
		//세그먼트 트리의 크기는 일반적으로 배열의 크기를 N이라고 설정했을때
		//4*N이면 충분하며, 해당 세그먼트 트리는 값과 인덱스 모두를 저장하기 위해 2차원으로 설정
		tree = new int[4*N][2];
		
		//세그먼트 트리에 값과 인덱스에 모두 MAX_VALUE를 저장하여, 최소값을 구할 때 영향이 없도록 함
		for(int i=0; i<tree.length; i++) {
			tree[i][0] = Integer.MAX_VALUE;
			tree[i][1] = Integer.MAX_VALUE;
		}
		
		String[] temp = br.readLine().split(" ");
		
		//배열에 정보를 입력함
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		//쿼리의 개수를 입력받음
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			int B = Integer.parseInt(temp[1]);
			int C = Integer.parseInt(temp[2]);
			
			//만약 update( )요청이라면
			if(A==1) {
				//update( )를 수행함
				update(1,0,N-1,B-1,C);
			//만약 query( )요청이라면
			}else {
				//query( )를 수행한 결과를 출력함, 이때 인덱스는 1부터 시작하므로 1을 더해줌
				int[] result = query(1,0,N-1,B-1,C-1);
				bw.write((result[1]+1)+"\n");
			}			
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}