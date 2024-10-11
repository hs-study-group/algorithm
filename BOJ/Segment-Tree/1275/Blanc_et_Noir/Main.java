//https://www.acmicpc.net/problem/1275

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static long[] arr;
	static long[] tree;
	
	static int N, Q;
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		int mid = (start+end)/2;
		
		if(start==end) {
			tree[node] = arr[start];
			return;
		}else {
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			
			tree[node] = tree[node*2] + tree[node*2+1];
		}
	}
	
	//세그먼트 트리의 내용을 변경하는 메소드
	public static void update(int node, int start, int end, int idx, long val) {
		if(idx>end||idx<start) {
			return;
		}
		
		if(start==end) {
			tree[node] = val;
			arr[idx] = val;
			return;
		}
		update(node*2,start,(start+end)/2,idx,val);
		update(node*2+1,(start+end)/2+1,end,idx,val);
		
		tree[node] = tree[node*2] + tree[node*2+1];
	}
	
	//세그먼트 트리에 쿼리를 수행하는 메소드
	public static long query(int node, int start, int end, int left, int right) {
		if(left>end||right<start) {
			return 0;
		}
		if(left<=start && right>=end) {
			return tree[node];
		}
		return query(node*2,start,(start+end)/2,left,right)+query(node*2+1,(start+end)/2+1,end,left,right);
	}
	
	public static void main(String[] args) throws IOException {
		String[] temp = br.readLine().split(" ");
		N = Integer.parseInt(temp[0]);
		Q = Integer.parseInt(temp[1]);
		
		arr = new long[3*N+1];
		tree = new long[3*N+1];
		
		temp = br.readLine().split(" ");
		for(int i=0; i<temp.length; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//세그먼트 트리 초기화
		init(1,0,N-1);
		
		for(int i=0; i<Q; i++) {
			temp = br.readLine().split(" ");
			int x = Integer.parseInt(temp[0])-1;
			int y = Integer.parseInt(temp[1])-1;
			
			//y보다 x가 크면 서로 교체함
			if(x>y) {
				int t = x;
				x = y;
				y = t;
			}
			
			//쿼리수행후의 결과를 출력
			bw.write(query(1,0,N-1,x,y)+"\n");
			
			//특정값을 업데이트
			update(1,0,N-1,Integer.parseInt(temp[2])-1,Integer.parseInt(temp[3]));
		}
		bw.flush();
	}
}