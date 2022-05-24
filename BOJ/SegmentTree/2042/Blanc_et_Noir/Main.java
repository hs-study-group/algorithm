import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static long[] tree = new long[3000001];
	public static long[] arr = new long[1000000];
	
	//���׸�Ʈ Ʈ���� �ʱ�ȭ�ϴ� �޼ҵ�
	public static void init(int node, int start, int end) {
		if(start==end) {
			tree[node] = arr[start];
		}else {
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			tree[node] = tree[node*2]+tree[node*2+1];
		}
	}
	
	//�������� ���ϴ� �޼ҵ�
	public static long query(int node, int start, int end, long left, long right) {
		//Ž�� ������ ����� 0�� �����Ͽ� ���ϴ��� �ƹ��� ��ȭ�� ������ ��
		if(left>end||right<start) {
			return 0;
		}
		//Ž�������� ������ �����ϸ� Ž�� ������ ����
		if(left<=start && right>=end) {
			return tree[node];
		}
		//�θ� ���� �ڽ� ����� ���� ��ģ ���� ����
		return query(node*2, start, (start+end)/2, left, right) + query(node*2+1, (start+end)/2+1, end, left, right);
	}
	
	//���� �����ϴ� �޼ҵ�
	public static void update(int node, int start, int end, int index, long val) {
		//�ش� �ε����� ������ ����� ����
		if(index>end||index<start) {
			return;
		}
		//��������� �� ���� val�� �ٲٰ� Ʈ���� ���� �ٲ�
		if(start==end) {
			arr[index] = val;
			tree[node] = val;
			return;
		}
		//���� �ڽĳ��� ������ �ڽĳ�忡 ���� ���ȣ��
		update(node*2,start,(start+end)/2,index,val);
		update(node*2+1,(start+end)/2+1,end,index,val);
		//�θ� ����� ���� �ڽĳ���� ���� ������ ����
		//�Ʒ��� �ڵ带 �����Ҷ��뿡�� �̹� �ڽĳ����� ���� ������ �Ϸ�� ����
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