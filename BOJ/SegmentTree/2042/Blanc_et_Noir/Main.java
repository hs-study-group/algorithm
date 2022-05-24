import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static long[] tree = new long[3000001];
	public static long[] arr = new long[1000000];
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		if(start==end) {
			tree[node] = arr[start];
		}else {
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			tree[node] = tree[node*2]+tree[node*2+1];
		}
	}
	
	//구간합을 구하는 메소드
	public static long query(int node, int start, int end, long left, long right) {
		//탐색 범위를 벗어나면 0을 리턴하여 더하더라도 아무런 변화가 없도록 함
		if(left>end||right<start) {
			return 0;
		}
		//탐색범위를 완전히 포함하면 탐색 종료후 리턴
		if(left<=start && right>=end) {
			return tree[node];
		}
		//부모 노드는 자식 노드의 값을 합친 값을 가짐
		return query(node*2, start, (start+end)/2, left, right) + query(node*2+1, (start+end)/2+1, end, left, right);
	}
	
	//값을 변경하는 메소드
	public static void update(int node, int start, int end, int index, long val) {
		//해당 인덱스가 범위를 벗어나면 종료
		if(index>end||index<start) {
			return;
		}
		//리프노드라면 그 값을 val로 바꾸고 트리의 값도 바꿈
		if(start==end) {
			arr[index] = val;
			tree[node] = val;
			return;
		}
		//왼쪽 자식노드와 오른쪽 자식노드에 대해 재귀호출
		update(node*2,start,(start+end)/2,index,val);
		update(node*2+1,(start+end)/2+1,end,index,val);
		//부모 노드의 값을 자식노드의 값의 합으로 갱신
		//아래의 코드를 실행할때쯤에는 이미 자식노드들의 값은 갱신이 완료된 상태
		tree[node] = tree[node*2]+tree[node*2+1];
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
				bw.write(query(1,0,N-1,B-1,C-1)+"\n");
			}
		}
		bw.flush();
	}
}