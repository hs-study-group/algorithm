//https://www.acmicpc.net/problem/1989

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//모든 원소들은 1000000을 넘지 않으므로 INF값을 1000000 + 1값으로 설정함
	static final long INF = 1000001;
	
	//점수의 최대값을 저장할 변수, MIN_VALUE로 초기화 함
	static long max = Long.MIN_VALUE;
	
	//최대 점수를 얻을 수 있는 구간의 시작, 종료 위치를 저장할 변수 선언
	static int L, R;
	
	//배열 및 세그먼트 트리 선언
	static long[] arr;
	
	//세그먼트 트리는 tree[n][i] 형태로 구성되며
	//tree[n][0]은 특정 구간의 모든 원소들의 합을 나타냄
	//tree[n][1]은 특정 구간의 모든 원소중 가장 작은 값을 나타냄
	//tree[n][2]은 특정 구간의 모든 원소중 가장 작은 값의 인덱스를 나타냄
	static long[][] tree;
	
	//두 세그먼트 트리의 노드를 하나로 통합하는 메소드
	public static long[] merge(long[] t1, long[] t2) {
		long[] r = new long[3];
		
		//결과 노드의 합은 두 노드의 합으로 초기화 함
		r[0] = t1[0] + t2[0];
		
		//두 노드중 t1노드의 최소값이 t2노드의 최소값보다 작거나 같다면
		if(t1[1]<=t2[1]) {
			//결과노드의 정보는 t1노드의 최소값과 그 인덱스로 갱신함
			r[1] = t1[1];
			r[2] = t1[2];
		//두 노드중 t2노드의 최소값이 t1노드의 최소값보다 작다면
		}else {
			//결과노드의 정보는 t2노드의 최소값과 그 인덱스로 갱신함
			r[1] = t2[1];
			r[2] = t2[2];
		}
		
		//결과 노드를 반환함
		return r;
	}
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int n, int s, int e) {
		//리프노드라면
		if(s==e) {
			//구간합과 최소값을 arr[s]로 갱신함
			tree[n][0] = arr[s];
			tree[n][1] = arr[s];
			
			//최소값의 인덱스를 s로 갱신함
			tree[n][2] = s;
			return;
		
		//리프노드가 아니라면
		}else {
			//중간 지점을 구함
			int m = (s+e)/2;
			
			//두 자식노드에 대하여 재귀적으로 초기화를 수행함
			init(n*2,s,m);
			init(n*2+1,m+1,e);
			
			//부모노드의 정보는 두 자식노드의 정보를 병합한 것으로 갱신함
			tree[n] = merge(tree[n*2],tree[n*2+1]);
			return;
		}
	}
	
	//l, r 구간에 대하여 구간합, 구간의 최소값, 최소값의 인덱스를 구하는 쿼리 메소드
	public static long[] query(int n, int s, int e, int l, int r) {
		//탐색해야할 구간을 완전히 벗어난 경우
		if(s>r||e<l) {
			//쿼리에 영향이 없도록 구간합은 0, 최소값은 INF, 인덱스는 -1을 제공함으로써
			//병합하더라도 결과에는 영향이 없도록 처리해야 함
			return new long[]{0, INF, -1};
		//탐색해야할 구간에 완전히 포함되는 경우
		}else if(l<=s&&e<=r) {
			//해당 노드의 정보를 리턴함
			return tree[n];
		//탐색해야할 구간에 걸치는 경우
		}else {
			//중간지점을 구함
			int m = (s+e)/2;
			
			//두 자식노드에 대하여 재귀적으로 쿼리를 수행하고, 그 결과들을 병합함
			return merge(query(n*2,s,m,l,r),query(n*2+1,m+1,e,l,r));
		}
	}
	
	//재귀적으로 쿼리메소드를 계속 호출하면서 최대 점수와 그때의 구간을 구하는 재귀 메소드
	public static void recursive(int l, int r) {
		//구간의 시작위치가 종료위치보다 앞서있거나 동일한 경우
		if(l<=r) {
			//해당 구간에 대하여 쿼리를 수행함
			long[] q = query(1,0,arr.length-1,l,r);
			
			//해당 구간의 점수가 기존에 기록한 max보다 크다면
			if(q[0]*q[1]>max) {
				//max값을 해당 점수로 갱신함
				max = q[0]*q[1];
				
				//그때의 구간의 시작, 종료 위치를 기록해둠
				L = l+1;
				R = r+1;
			}
			
			//최소값의 인덱스를 얻음
			int idx = (int) q[2];
			
			//최소값을 기준으로 양쪽 구간에 대하여 다시 재귀적으로 쿼리를 수행함
			recursive(l,idx-1);
			recursive(idx+1,r);
		
		//구간의 시작, 종료위치가 정상적이지 않으면
		}else {
			//그대로 재귀를 종료함
			return;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		
		//배열을 선언함
		arr = new long[N];
		
		//세그먼트 트리를 선언함
		tree = new long[4*N][3];
		
		//배열의 정보를 입력 받음
		String[] temp = br.readLine().split(" ");
		for(int i=0; i<temp.length; i++) {
			arr[i] = Long.parseLong(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,arr.length-1);
		
		//재귀적으로 최대 점수와 그 구간의 시작, 종료 위치를 구함
		recursive(0,arr.length-1);
		
		//최대 점수와 그 구간의 시작, 종료 위치를 출력함
		bw.write(max+"\n"+L+" "+R+"\n");
		
		bw.close();
		br.close();
	}
}