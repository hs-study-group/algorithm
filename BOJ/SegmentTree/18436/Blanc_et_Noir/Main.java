import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	//���׸�Ʈ Ʈ���� 2�������� ����
	public static int[][] tree;
	public static int[] arr;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//���׸�ƮƮ���� �ʱ�ȭ�ϴ� �޼ҵ�
	public static void init(int node, int start, int end) {
		
		//��������ϰ�� ������忡 Ȧ�� �Ǵ� ¦���� ������ �����
		if(start == end) {
			tree[node][arr[start]%2] = 1;
			return;
		//������尡 �ƴҰ��
		}else {
			int mid = (start+end)/2;
			
			//��������� Ž���� �ǽ���
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//�θ����� ���� �� �ڽĳ���� Ȧ��, ¦���� ������ ���� ���� ������ ����
			tree[node][0] = tree[node*2][0] + tree[node*2+1][0];
			tree[node][1] = tree[node*2][1] + tree[node*2+1][1];
			
			return;
		}		
	}
	
	//���׸�Ʈ Ʈ���� Ư�� ���������� Ȧ��, ¦���� ������ �����ϴ� ���� �޼ҵ�
	public static int query(int node,int Q, int start, int end, int left, int right) {
		
		//Ž���ϰ��ִ� ������ Ž���ϰ��� �ϴ� ������ ��� ���
		if(left>end||right<start) {
			//0�� ���������ν� �θ��忡 ������ ���� �ʵ��� ��
			return 0;			
		}
		
		//Ž���ϰ��ִ� ������ Ž���ϰ��� �ϴ� ���� �ȿ� ������ ���Ե� ���
		if(left<=start&&right>=end) {
			//Ȧ�� �Ǵ� ¦���� ������ ������
			return tree[node][Q];
		}
		
		int mid = (start+end)/2;
		
		//�� �ڽĳ��κ��� ������ ������ ���� ����
		return query(node*2,Q,start,mid,left,right)+query(node*2+1,Q,mid+1,end,left,right);
	}
	
	//Ư�� �迭�� ���� ������Ʈ�ϰ�, �׿����� ��������� ���׸�Ʈ Ʈ���� ������Ʈ�ϴ� �޼ҵ�
	public static void update(int node, int idx, int val, int start, int end) {
		//�ش� �ε����� Ž�������� ������� ����
		if(idx<start||idx>end) {
			return;
		}
		
		//��������ΰ��
		if(start==end) {
			//���� �迭�� ����� ���� ¦������ Ȧ������ �ӽ÷� ������
			int b = arr[idx]%2;
			
			//���� �迭�� ������Ʈ��
			arr[idx] = val;
			
			//������ ����� ¦�� �Ǵ� Ȧ���� ������ ���ҽ�Ű��
			tree[node][b] = tree[node][b]-1;
			
			//���� ������ ¦�� �Ǵ� Ȧ���� ������ ������Ŵ
			tree[node][val%2] = tree[node][val%2]+1;
			return;
		}
		
		int mid = (start+end)/2;
		
		//��������� ������Ʈ�� ������
		update(node*2,idx,val,start,mid);
		update(node*2+1,idx,val,mid+1,end);
		
		//�θ����� ���� �� �ڽĳ���� ���� ������ ������Ʈ��
		tree[node][0] = tree[node*2][0]+tree[node*2+1][0];
		tree[node][1] = tree[node*2][1]+tree[node*2+1][1];
		
		return;
	}
	
	public static void main(String[] args) throws Exception{
		
		int N = Integer.parseInt(br.readLine());
		
		arr = new int[N];
		tree = new int[N*3][2];
		
		String[] str = br.readLine().split(" ");
		
		for(int i=0; i<str.length; i++) {
			arr[i] = Integer.parseInt(str[i]);
		}
		
		init(1,0,arr.length-1);
		
		int M = Integer.parseInt(br.readLine());
		
		for(int i=0; i<M; i++) {
			str = br.readLine().split(" ");
			
			//Q�� ������ ����� �ǹ�
			//1�̸� ������Ʈ, 2�̸� ¦��, 3�̸� Ȧ���� ���� ����
			//���ǻ� 2%2, 2%3�� ���� 0, 1�� ���� ¦��, Ȧ���� ������ ������ Ʈ�� �ε����� ���
			int Q = Integer.parseInt(str[0]);
			int A = Integer.parseInt(str[1]);
			int B = Integer.parseInt(str[2]);
			
			switch(Q) {
				case 1:
					update(1,A-1,B,0,arr.length-1);
					break;
				default :
					bw.write(query(1,Q%2,0,arr.length-1,A-1,B-1)+"\n");
			}
			
		}
		bw.flush();
	}
}