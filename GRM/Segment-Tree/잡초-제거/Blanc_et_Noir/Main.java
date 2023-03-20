//https://level.goorm.io/exam/51351/%EC%9E%A1%EC%B4%88-%EC%A0%9C%EA%B1%B0/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[] arr = null;
	static int[] tree = null;
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int n, int s, int e){
		//리프노드라면
		if(s==e){
			//세그먼트 트리의 노드에 값을 대입함
			tree[n] = arr[s];
		//아니라면
		}else{
			int m = (s+e)/2;
			//세그먼트 트리의 두 자식노드에 대하여 재귀적으로 초기화를 수행함
			init(n*2,s,m);
			init(n*2+1,m+1,e);
			
			//초기화된 두 자식노드의 결과를 병합함
			tree[n] = tree[n*2] + tree[n*2+1];
		}
	}
	
	//세그먼트 트리의 l, r구간에 대하여 구간합을 구하는 메소드
	public static int query(int n, int s, int e, int l, int r){
		//현재 탐색중인 구간이 탐색하고자 하는 구간을 완전히 벗어난 경우
		if(s>r||e<l){
			//0을 리턴하여 결과에 영향이 없도록 함
			return 0;
		//현재 탐색중인 구간이 탐색하고자 하는 구간에 완전히 포함된 경우
		}else if(l<=s&&e<=r){
			//그때의 세그먼트 트리의 값을 리턴함
			return tree[n];
		//구간이 걸치기만 한 경우
		}else{
			int m = (s+e)/2;
			//두 자식노드에 대하여 재귀적으로 쿼리를 수행하고, 그 결과를 병합함
			return query(n*2,s,m,l,r) + query(n*2+1,m+1,e,l,r);
		}
	}
	
	//idx에 해당하는 값을 v만큼 증가시키는 메소드
	public static void update(int n, int s, int e, int idx, int v){
		//idx가 현재 탐색중인 구간을 완전히 벗어난경우
		if(idx>e||idx<s){
			//즉시 종료함
			return;
		//리프노드라면
		}else if(s==e){
			//arr 배열과 세그먼트 트리의 값을 v증가시킴
			arr[idx]+=v;
			tree[n]+=v;
			return;
		//구간이 걸치기만 하는 경우
		}else{
			int m = (s+e)/2;
			//두 자식노드에 대하여 재귀적으로 갱신을 수행함
			update(n*2,s,m,idx,v);
			update(n*2+1,m+1,e,idx,v);
			
			//두 자식노드의 값을 병합하여 부모노드의 값으로 설정함
			tree[n] = tree[n*2] + tree[n*2+1];
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int Q = Integer.parseInt(temp[1]);
		
		//배열과 세그먼트 트리 선언
		arr = new int[N];
		tree = new int[4*N];
		
		temp = br.readLine().split(" ");
		
		for(int i=0;i<N;i++){
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화함
		init(1,0,N-1);
		
		//Q개의 쿼리를 수행함
		for(int i=0;i<Q;i++){
			temp = br.readLine().split(" ");
			
			//q, a, b를 각각 입력받음
			int q = Integer.parseInt(temp[0]);
			int a = Integer.parseInt(temp[1]);
			int b = Integer.parseInt(temp[2]);
			
			switch(q){
				//1번 쿼리라면
				case 1:
					//주어진 구간에 대하여 구간합을 출력함
					bw.write(query(1,0,N-1,a-1,b-1)+"\n");
					break;
				//2번 쿼리라면
				case 2:
					//해당 인덱스의 값을 b증가시킴
					update(1,0,N-1,a-1,b);
					break;
				//3번 쿼리라면
				case 3:
					//해당 인덱스의 값을 b감소시킴
					update(1,0,N-1,a-1,-b);
					break;
			}
		}
		bw.flush();
		bw.close();
		br.close();
	}
}