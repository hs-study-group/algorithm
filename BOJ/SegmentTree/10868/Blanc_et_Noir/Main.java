import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static long[] tree = new long[300000];
	public static long[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void init(int node, int start, int end) {
		//리프노드라면 입력된 배열의 값을 추가
		if(start==end) {
			tree[node] = arr[start];
		}else {
			//리프노드가 아니라면 좌, 우측 자식을 이용해 재귀 탐색 실시
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			
			//자신의 값은 두 개의 자식중 더 작은 값을 갖는 자식의 것과 동일하게 만듦
			tree[node] = Math.min(tree[node*2], tree[node*2+1]);
		}
	}
	
	public static long query(int node, int start, int end, int left, int right) {
		//범위를 벗어나면 1000000001를 리턴
		//이보다 큰 수는 없으므로 최소값을 찾을때 굳이 별다른 처리할 필요 없음
		if(left>end||right<start) {
			return 1000000001;
		}
		//해당 구간을 완전히 포함하면 그 값을 리턴
		if(left<=start&&right>=end) {
			return tree[node];
		}
		//겹치는 영역이 없다면 좌, 우측 자식을 대상으로 탐색 실시 및 그 최소값을 자신의 값으로 설정
		return Math.min(query(node*2,start,(start+end)/2,left,right),query(node*2+1,(start+end)/2+1,end,left,right));
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		arr = new long[N];
		
		for(int i=0; i<N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		init(1,0,N-1);
		
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int left = Integer.parseInt(temp[0])-1;
			int right = Integer.parseInt(temp[1])-1;
			
			if(left>right) {
				int num = left;
				left = right;
				right = num;
			}
			
			bw.write(query(1,0,N-1,left,right)+"\n");
		}
		bw.flush();
	}
}