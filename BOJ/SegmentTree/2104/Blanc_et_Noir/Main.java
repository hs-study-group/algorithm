//https://www.acmicpc.net/problem/2104

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static long[] arr;
	static long[][] tree;
	
	//두 자식 쿼리의 결과를 하나로 병합하는 메소드
	public static long[] merge(long[] q1, long[] q2) {
		long[] r = new long[3];
		
		//0번째 인덱스에는 두 자식 쿼리의 합을 (구간합을 구함)
		r[0] = q1[0] + q2[0];
		
		//1번째 인덱스와 2번째 인덱스에는 둘 중 더 작은 최소값의 크기와 그 인덱스를 저장함
		if(q1[1]<=q2[1]) {
			r[1] = q1[1];
			r[2] = q1[2];
		}else {
			r[1] = q2[1];
			r[2] = q2[2];
		}
		
		//병합된 결과를 리턴함
		return r;
	}
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int n, int s, int e) {
		//리프노드라면
		if(s==e) {
			//구간의 합, 구간의 최소값, 최소값의 인덱스를 각각 0, 1, 2번 인덱스에 추가함
			tree[n][0] = arr[s];
			tree[n][1] = arr[s];
			tree[n][2] = s;
			return;
		}else {
			int m = (s+e)/2;
			
			//두 자식 노드에 대하여 재귀적으로 초기화를 수행함
			init(n*2,s,m);
			init(n*2+1,m+1,e);
			
			//부모노드의 정보는 두 자식노드의 정보를 병합한 결과를 담고있음
			tree[n] = merge(tree[n*2],tree[n*2+1]);
			return;
		}
	}
	
	//l, r 구간에 대하여 구간합, 구간의 최소값, 그 최소값의 인덱스를 반환하는 쿼리 메소드
	public static long[] query(int n, int s, int e, int l, int r) {
		//탐색해야할 구간을 완전히 벗어난 경우
		if(s>r||e<l) {
			//쿼리 결과를 병합하는데 영향을 주지 않도록 구간합은 0, 구간의 최소값은 MAX_VALUE, 구간 최소값의 인덱스는 -1을 리턴함
			return new long[] {0, Long.MAX_VALUE,-1};
		//탐색해야할 구간에 완전히 포함된 경우
		}else if(l<=s&&e<=r) {
			//해당 노드 정보를 리턴함
			return tree[n];
		//탐색해야할 구간에 걸치는 경우
		}else {
			//두 자식노드에 대하여 재귀적으로 쿼리를 수행하고 그 결과를 병합하여 리턴함
			return merge(query(n*2,s,(s+e)/2,l,r),query(n*2+1,(s+e)/2+1,e,l,r));
		}
	}
	
	//재귀적으로 l, r 구간의 위치를 조정하며 최대 점수를 구하는 메소드
	public static long recursive(int l, int r) {
		//구간의 시작 위치가 종료위치보다 이후인 경우에는
		//더이상 탐색할 필요가 없음
		if(l>r) {
			//점수의 최대값을 MIN_VALUE로 리턴하여 결과에 영향이 없도록 함
			return Long.MIN_VALUE;
		//구간이 정상적인 경우
		}else {
			//현재 구간에 대하여 쿼리를 수행함
			long[] t = query(1,0,arr.length-1,l,r);
			
			//구간의 최소값의 인덱스를 얻음
			int idx = (int) t[2];
			
			//구간의 최소값의 인덱스를 제외한 양쪽 구간에 대하여 재귀적으로 쿼리를 수행하고
			//현재 탐색한 구간의 최대 점수와 양쪽 구간에 대하여 재귀적으로 구한 최대 점수를 비교하여
			//가장 큰 점수를 리턴함
			return Math.max(t[0]*t[1], Math.max(recursive(l,idx-1), recursive(idx+1,r)));
		}
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//세그먼트 트리와 배열을 선언함
		arr = new long[N];
		
		//세그먼트 트리의 2차원 인덱스는 각각 구간합, 구간의 최소값, 구간의 최소값의 인덱스 정보를 가짐
		tree = new long[4*N][3];
		
		String[] temp = br.readLine().split(" ");
		
		//배열에 정보를 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		//최대 점수를 구함
		bw.write(recursive(0,N-1)+"\n");
		bw.close();
		br.close();
	}
}