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
		//��������� �Էµ� �迭�� ���� �߰�
		if(start==end) {
			tree[node] = arr[start];
		}else {
			//������尡 �ƴ϶�� ��, ���� �ڽ��� �̿��� ��� Ž�� �ǽ�
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			
			//�ڽ��� ���� �� ���� �ڽ��� �� ���� ���� ���� �ڽ��� �Ͱ� �����ϰ� ����
			tree[node] = Math.min(tree[node*2], tree[node*2+1]);
		}
	}
	
	public static long query(int node, int start, int end, int left, int right) {
		//������ ����� 1000000001�� ����
		//�̺��� ū ���� �����Ƿ� �ּҰ��� ã���� ���� ���ٸ� ó���� �ʿ� ����
		if(left>end||right<start) {
			return 1000000001;
		}
		//�ش� ������ ������ �����ϸ� �� ���� ����
		if(left<=start&&right>=end) {
			return tree[node];
		}
		//��ġ�� ������ ���ٸ� ��, ���� �ڽ��� ������� Ž�� �ǽ� �� �� �ּҰ��� �ڽ��� ������ ����
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