import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static long[] tree = new long[3000000];
	public static long[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static long query(int node, int start, int end, int left, int right) {
		//탐색하고자 하는 범위가 노드의 구간을 완전히 벗어나면 0을 리턴
		//0은 합계에 추가하여도 변화가 없음
		if(left>end||right<start) {
			return 0;
		}
		//탐색하고자 하는 범위가 노드의 구간을 완전히 포함하므로 그 값을 리턴
		if(left<=start&&right>=end) {
			return tree[node];
		}
		//왼쪽, 오른쪽 자식에 대해 수행한 쿼리의 합을 리턴
		return query(node*2,start,(start+end)/2,left,right)+query(node*2+1,(start+end)/2+1,end,left,right);
	}
	
	public static void update(int node, int index, int start, int end, long val) {
		//인덱스가 구간의 범위를 벗어나면 종료
		if(index<start||index>end) {
			return;
		}
		//리프노드라면 그 값을 val로 변경
		if(start==end) {
			tree[node]=val;
			return;
		}
		//왼쪽, 오른쪽 자식에 대해 메소드를 재귀 호출
		update(node*2,index,start,(start+end)/2,val);
		update(node*2+1,index,(start+end)/2+1,end,val);
		
		//부모 노드의 값을 자식 노드들의 값의 합으로 갱신
		tree[node] = tree[node*2]+tree[node*2+1];
	}
	
	public static void main(String[] args) throws Exception{
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		for(int i=0;i<M;i++) {
			temp = br.readLine().split(" ");
			long I = Long.parseLong(temp[0]);
			long J = Long.parseLong(temp[1]);
			long K = Long.parseLong(temp[2]);

			if(I==0) {
				if(J>K) {
					long num = J;
					J = K;
					K = num;
				}
				bw.write(query(1,0,N-1,(int)J-1,(int)K-1)+"\n");
			}else {
				update(1,(int)J-1,0,N-1,K);
			}
		}
		bw.flush();
	}
}