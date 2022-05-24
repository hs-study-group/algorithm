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
		//Ž���ϰ��� �ϴ� ������ ����� ������ ������ ����� 0�� ����
		//0�� �հ迡 �߰��Ͽ��� ��ȭ�� ����
		if(left>end||right<start) {
			return 0;
		}
		//Ž���ϰ��� �ϴ� ������ ����� ������ ������ �����ϹǷ� �� ���� ����
		if(left<=start&&right>=end) {
			return tree[node];
		}
		//����, ������ �ڽĿ� ���� ������ ������ ���� ����
		return query(node*2,start,(start+end)/2,left,right)+query(node*2+1,(start+end)/2+1,end,left,right);
	}
	
	public static void update(int node, int index, int start, int end, long val) {
		//�ε����� ������ ������ ����� ����
		if(index<start||index>end) {
			return;
		}
		//��������� �� ���� val�� ����
		if(start==end) {
			tree[node]=val;
			return;
		}
		//����, ������ �ڽĿ� ���� �޼ҵ带 ��� ȣ��
		update(node*2,index,start,(start+end)/2,val);
		update(node*2+1,index,(start+end)/2+1,end,val);
		
		//�θ� ����� ���� �ڽ� ������ ���� ������ ����
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