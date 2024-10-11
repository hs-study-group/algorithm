//https://www.acmicpc.net/problem/1306

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int[] tree, int[] arr) {
		init(tree, arr, 1, 0, arr.length-1);
	}
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int[] tree, int[] arr, int n, int s, int e) {
		int m = (s+e)/2;
		
		//리프 노드에 도달한 경우
		if(s==e) {
			//세그먼트 트리에 값을 부여함
			tree[n] = arr[s];
			return;
		//리프노드가 아닌경우
		}else {
			
			//두 서브트리에 대해 초기화를 재귀적으로 수행함
			init(tree, arr, n*2, s, m);
			init(tree, arr, n*2+1, m+1, e);
			
			//두 서브트리의 값중 더 큰 값을 현재 트리의 값으로 설정함
			tree[n] = Math.max(tree[n*2], tree[n*2+1]);
			
			return;
		}
	}	
	
	//세그먼트 트리에 질의하는 메소드
	public static int query(int[] tree, int[] arr, int l, int r) {
		return query(tree, 1, 0, arr.length-1, l, r);
	}
	
	//세그먼트 트리에 질의하는 메소드
	public static int query(int[] tree, int n, int s, int e, int l, int r) {
		int m = (s+e)/2;
		
		//현재 탐색중인 범위 s ~ e가 탐색할 구간 l ~ r을 완전히 벗어난 경우
		if(l>e||r<s) {
			//최소값을 리턴하여 질의 결과에 영향이 없도록 함
			return Integer.MIN_VALUE;
			
		//현재 탐색중인 범위 s ~ e가 탐색할 구간 l ~ r에 완전히 포함된 경우
		}else if(l<=s&&e<=r) {
			//해당 구간의 값을 리턴함
			return tree[n];
		//현재 탐색중인 범위 s ~ e가 탐색할 구간 l ~ r에 일부 걸치는 경우
		}else {
			//두 서브 쿼리의 결과값중 더 큰 값을 리턴함
			return Math.max(query(tree, n*2, s, m, l, r), query(tree, n*2+1, m+1, e, l, r));
		}
	}
	
	public static void main(String[] args) throws IOException {		
		String[] input = br.readLine().split(" ");
		
		//N과 M을 입력받음
		final int N = Integer.parseInt(input[0]);
		final int M = Integer.parseInt(input[1]);
		
		//배열의 크기가 N이라면, 세그먼트 트리의 크기는 4*N이면 충분함
		int[] arr = new int[N];
		int[] tree = new int[4*N];
		
		input = br.readLine().split(" ");
		
		//배열에 값을 저장함
		for(int i=0;i<N;i++) {
			arr[i] = Integer.parseInt(input[i]);
		}
		
		//세그먼트 트리를 초기화함
		init(tree, arr);
		
		for(int i=0; i<N; i++) {
			//탐색할 구간 l과 r을 계산함
			int l = Math.max(0, i-(M-1));
			int r = Math.max(N-1, i+(M-1));
			
			//구간 l ~ r에 대하여 최대값을 구함
			bw.write(query(tree, arr, l, r)+" ");
		}
		
		bw.write("\n");
		bw.flush();
		br.close();
		bw.close();
	}
}