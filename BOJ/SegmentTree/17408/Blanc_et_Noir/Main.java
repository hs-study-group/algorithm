//https://www.acmicpc.net/problem/17408

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
	
	static int[][] tree;
	static int[] arr;

	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int node, int start, int end){
		//리프 노드일 경우에는
		if(start==end) {
			//리프 노드에 각각 그 정수 값과 해당 인덱스를 저장함
			tree[node][1] = start;
			tree[node][0] = arr[start];
		//리프 노드가 아니라면
		}else {
			//왼쪽, 오른쪽 노드 모두에 대해서 init( )메소드를 재귀적으로 호출함
			int mid = (start+end)/2;
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//두 자식 노드에 대하여 왼쪽 노드의 값이 오른쪽 노드의 값보다 크다면
			if(tree[node*2][0]>=tree[node*2+1][0]) {
				//부모 노드의 값은 왼쪽 노드의 값, 인덱스는 왼쪽 노드의 인덱스로 설정함
				tree[node][0] = tree[node*2][0];
				tree[node][1] = tree[node*2][1];
			//두 자식 노드에 대하여 오른쪽 노드의 값이 왼쪽 노드의 값보다 크다면
			}else {
				//부모 노드의 값은 오른쪽 노드의 값, 인덱스는 오른쪽 노드의 인덱스로 설정함
				tree[node][0] = tree[node*2+1][0];
				tree[node][1] = tree[node*2+1][1];
			}
		} 
	}
	
	//특정 구간에 대하여 최댓값과 그 인덱스를 반환하는 메소드
	public static int[] query(int node, int start, int end, int left, int right){
		//만약 탐색하고 있는 구간이 특정 구간을 벗어나는 경우
		if(start>right||end<left) {
			//-1값을 리턴하여 쿼리에 영향을 주지 않도록 함
			return new int[] {-1,-1};
		}
		
		//만약 현재 탐색중인 구간이 완전히 포함되는 경우
		if(left<=start&&end<=right) {
			//세그먼트 트리의 노드를 반환함
			return tree[node];
		}
		
		//왼쪽, 오른쪽 자식 노드에 대하여 재귀적으로 쿼리를 수행함
		int[] q1 = query(node*2,start,(start+end)/2,left,right);
		int[] q2 = query(node*2+1,(start+end)/2+1,end,left,right);
		
		//두 자식노드 중에서 값이 더 큰 노드의 값과 인덱스를 반환함
		return q1[0]>q2[0]?q1:q2;
	}
	
	//특정 인덱스의 값을 val로 변경하고, 세그먼트 트리를 갱신하는 메소드
	public static void update(int node, int start, int end, int idx, int val) {
		//인덱스가 해당 범위를 벗어난 경우 아무런 처리도 하지 않음
		if(idx<start||idx>end) {
			return;
		}
		
		//리프일 경우에는
		if(start==end) {
			//배열의 값을 val로 바꿈
			arr[idx] = val;
			
			//세그먼트 트리의 리프노드 또한 해당 값, 인덱스로 변화시킴
			tree[node][0] = val;
			tree[node][1] = idx;
			return;
		}
		
		int mid = (start+end)/2;
		
		//왼쪽, 오른쪽 자식 노드에 대하여 update( )를 재귀적으로 호출함
		update(node*2,start,mid,idx,val);
		update(node*2+1,mid+1,end,idx,val);
		
		//두 자식 노드에 대하여 왼쪽 노드의 값이 오른쪽 노드의 값보다 크다면
		if(tree[node*2][0]>=tree[node*2+1][0]) {
			//부모 노드의 값은 왼쪽 노드의 값, 인덱스는 왼쪽 노드의 인덱스로 설정함
			tree[node][0] = tree[node*2][0];
			tree[node][1] = tree[node*2][1];
		//두 자식 노드에 대하여 오른쪽 노드의 값이 왼쪽 노드의 값보다 크다면
		}else {
			//부모 노드의 값은 오른쪽 노드의 값, 인덱스는 오른쪽 노드의 인덱스로 설정함
			tree[node][0] = tree[node*2+1][0];
			tree[node][1] = tree[node*2+1][1];
		}
	}
	
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(br.readLine());
		arr = new int[N];
		
		//세그먼트 트리의 크기는 배열의 크기의 4배면 충분함
		//tree[node][f]에 대하여
		//tree[node][0]은 해당 구간에서의 최대값을
		//tree[node][1]은 해당 구간에서의 최대값의 인덱스를 저장함
		tree = new int[4*N][2];
		
		String[] temp = br.readLine().split(" ");

		//배열에 값을 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		//쿼리의 개수를 입력 받음
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			//쿼리에 입력할 파라미터를 입력 받음
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			int B = Integer.parseInt(temp[1]);
			int C = Integer.parseInt(temp[2]);
			
			//만약 update( ) 쿼리라면
			if(A==1) {
				//B-1 인덱스의 값을 C로 변경하고 세그먼트 트리를 갱신함
				update(1,0,N-1,B-1,C);
			//만약 query( ) 쿼리라면
			}else {
				//해당 특정 구간 [B-1, C-1]에 대하여 최대값과 그 인덱스를 얻음
				int[] result = query(1,0,N-1,B-1,C-1);
				
				//최대값의 인덱스를 제외한 나머지 구간인 [B-1,K-1], [K+1,C-1] 에서의 최대값과 인덱스를 구함
				int[] result1 = query(1,0,N-1,B-1,result[1]-1);
				int[] result2 = query(1,0,N-1,result[1]+1,C-1);
				
				//둘 중 더 큰 값을 찾으면 그것이 두 번째로 큰 값이 됨
				//따라서 기존에 구한 최대값 + 두 번째 최대값 = 구하고자 하는 값임
				if(result1[0]>result2[0]) {
					bw.write((result[0]+result1[0])+"\n");
				}else {
					bw.write((result[0]+result2[0])+"\n");
				}
			}			
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}