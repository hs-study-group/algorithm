//https://www.acmicpc.net/problem/11505

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static long[] tree = new long[3000001];
	public static long[] arr = new long[1000000];
	
	//구간곱은 long이 표현할 수 있는 범위를 넘어서므로 매번 모듈로 연산하여 나머지를 저장함
	public static final long mod = 1000000007;
	
	public static void init(int node, int start, int end) {
		//리프노드라면 해당 값을 트리에 추가
		if(start==end) {
			tree[node] = arr[start];
		}else {
			//리프노드가 아니라면 왼쪽 자식과 오른쪽 자식에 대해 재귀적으로 메소드 호출
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			//왼쪽 자식과 오른쪽 자식의 값을 곱하여 자신의 값으로 사용
			tree[node] = ((tree[node*2]%mod)*(tree[node*2+1]%mod))%mod;
		}
	}
	
	public static long query(int node, int start, int end, long left, long right) {
		//탐색 범위를 벗어나서 겹치는 영역이 전혀 없으면 종료
		//1은 리턴하더라도 구간 곱에 영향을 미치지 않으므로 적당한 값
		if(left>end||right<start) {
			return 1;
		}
		//탐색하는 범위가 해당 구간을 완전히 포함하면 탐색 종료후 리턴
		if(left<=start && right>=end) {
			return tree[node];
		}
		//왼쪽 자식과 오른쪽 자식의 결과를 곱하여 자신의 결과로 사용
		return ((query(node*2, start, (start+end)/2, left, right)%mod) * (query(node*2+1, (start+end)/2+1, end, left, right)%mod))%mod;
	}
	
	public static void update(int node, int start, int end, int index, long val) {
		//인덱스가 범위를 벗어나면 종료
		if(index>end||index<start) {
			return;
		}
		//리프노드라면 그 노드를 해당 값으로 변경, 트리의 값도 변경
		if(start==end) {
			arr[index] = val;
			tree[node] = val;
			return;
		}
		//왼쪽 자식과 오른쪽 자식에 대해서도 재귀적으로 메소드 호출
		update(node*2,start,(start+end)/2,index,val);
		update(node*2+1,(start+end)/2+1,end,index,val);
		//자신의 값은 변경된 자식들의 곱으로 설정함
		tree[node] = ((tree[node*2]%mod)*(tree[node*2+1]%mod))%mod;
	}
	
	public static void main(String[] args) throws Exception{

		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		int K = Integer.parseInt(temp[2]);
		
		for(int i=0; i<N;i++) {
			arr[i]=Long.parseLong(br.readLine());
		}
		
		init(1,0,N-1);
		
		for(int j=0;j<M+K;j++) {
			temp = br.readLine().split(" ");
			int A = Integer.parseInt(temp[0]);
			int B = Integer.parseInt(temp[1]);
			long C = Long.parseLong(temp[2]);
			
			if(A==1) {
				update(1,0,N-1,B-1,C);
			}else {
				bw.write((query(1,0,N-1,B-1,C-1)%mod)+"\n");
			}
		}
		bw.flush();
	}
}