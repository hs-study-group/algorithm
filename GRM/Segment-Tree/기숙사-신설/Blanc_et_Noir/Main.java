//https://level.goorm.io/exam/49081/%EA%B8%B0%EC%88%99%EC%82%AC-%EC%8B%A0%EC%84%A4/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//배열, 세그먼트 트리, 가중치 배열
	static long[] arr;
	static long[][] tree;
	static long[] lazy;
	
	//두 배열을 하나로 병합하는 메소드
	//0번 인덱스는 더 큰 값을, 1번 인덱스는 더 작은 값을 가짐
	public static long[] merge(long[] arr1, long[] arr2){
		return new long[]{Math.max(arr1[0],arr2[0]),Math.min(arr1[1],arr2[1])};
	}
	
	//남아있는 잔여 가중치를 처리하는 메소드
	public static void process(int n, int s, int e){
		if(lazy[n]!=0){
			tree[n][0] += lazy[n];
			tree[n][1] += lazy[n];
			
			if(s!=e){
				lazy[n*2] += lazy[n];
				lazy[n*2+1] += lazy[n];
			}
			
			lazy[n]=0;
		}else{
			return;
		}
	}
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int n, int s, int e){
		//리프노드라면
		if(s==e){
			//해당 노드의 최대, 최소값을 arr[s]로 갱신함
			tree[n][0] = arr[s];
			tree[n][1] = arr[s];
			return;
		//리프노드가 아니라면
		}else{
			int m = (s+e)/2;
			//두 자식 노드에 대하여 재귀적으로 초기화를 수행함
			init(n*2,s,m);
			init(n*2+1,m+1,e);
			
			//부모 노드의 최대, 최소값은 두 자식노드의 최대, 최소값을 병합한 결과로 함
			tree[n] = merge(tree[n*2],tree[n*2+1]);
			return;
		}
	}
	
	//특정한 구간 l, r에 대하여 각 인덱스의 값을 v만큼 증가시키는 메소드
	public static void update(int n, int s, int e, int l, int r, long v){
		//가중치가 남아있다면 가중치를 처리함
		process(n,s,e);
		//현재 탐색중인 구간이 탐색할 구간을 벗어났다면
		if(s>r||e<l){
			//즉시 종료함
			return;
		//현재 탐색중인 구간이 탐색할 구간에 완전히 포함된다면
		}else if(l<=s&&e<=r){
			//최대값과 최소값을 각각 v만큼 증가시킴
			tree[n][0] += v;
			tree[n][1] += v;
			
			//리프노드가 아니라면
			if(s!=e){
				//자식 노드에게 가중치를 전파함
				lazy[n*2] += v;
				lazy[n*2+1] += v;
			}
			
			return;
		//현재 탐색중인 구간이 탐색할 구간에 걸친다면
		}else{
			int m = (s+e)/2;
			
			//두 자식노드에 대하여 갱신을 재귀적으로 수행함
			update(n*2,s,m,l,r,v);
			update(n*2+1,m+1,e,l,r,v);
			
			//부모노드의 값은 두 자식노드의 최대, 최소값을 병합한 결과로 함
			tree[n] = merge(tree[n*2],tree[n*2+1]);
			return;
		}
	}
	
	//l, r구간에 대하여 최대값, 최소값을 구하는 메소드
	public static long[] query(int n, int s, int e, int l, int r){
		//가중치가 남아있다면 가중치를 처리함
		process(n,s,e);
		//현재 탐색중인 구간이 탐색할 구간을 벗어났다면
		if(s>r||e<l){
			//재귀 결과에 영향을 주지 않도록 최대값은 MIN_VALUE를, 최소값은 MAX_VALUE를 리턴함
			return new long[]{Long.MIN_VALUE,Long.MAX_VALUE};
		//현재 탐색중인 구간이 탐색할 구간에 완전히 포함된다면
		}else if(l<=s&&e<=r){
			//해당 노드 정보를 리턴함
			return tree[n];
		//현재 탐색중인 구간이 탐색할 구간에 걸친다면
		}else{
			int m = (s+e)/2;
			//두 자식노드에 대하여 재귀적으로 쿼리한 결과를 구하고, 그 결과들을 병합한 결과를 리턴함
			return merge(query(n*2,s,m,l,r),query(n*2+1,m+1,e,l,r));
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		final int N = Integer.parseInt(temp[0]);
		final int L = Integer.parseInt(temp[1]);
		
		//배열의 크기가 L이라면, 세그먼트 트리 및 가중치배열의 크기는 4*L이면 충분함
		arr = new long[L];
		lazy = new long[4*L];
		tree = new long[4*L][2];
		
		//배열을 입력받음
		temp = br.readLine().split(" ");
		
		for(int i=0;i<L;i++){
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//세그먼트 트리를 초기화
		init(1,0,L-1);
		
		//반복적으로 l, r구간에 대하여 v만큼 값을 증가시킴
		for(int i=0;i<N;i++){
			temp = br.readLine().split(" ");
			int l = Integer.parseInt(temp[0])-1;
			int r = Integer.parseInt(temp[1])-1;
			long v = Long.parseLong(temp[2]);
			update(1,0,L-1,l,r,-v);
		}
		
		//전 구간에 대하여 최대값, 최소값을 구함
		long[] result = query(1,0,L-1,0,L-1);
		
		bw.write(result[0]+"\n"+result[1]+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}