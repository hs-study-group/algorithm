//https://www.acmicpc.net/problem/13544

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static int[] arr;
	static ArrayList<Integer>[] tree;
	
	static int N, Q;
	
	//자신보다 큰 수가 처음 나타나는 인덱스를 반환하는 메소드
	public static int upperbound(ArrayList<Integer> list, int val) {
		int start = 0;
		int end = list.size();
		while(start<end) {
			int mid = (start+end)/2;
			if(list.get(mid)<=val) {
				start = mid + 1;
			}else {
				end = mid;
			}
		}
		return end;
	}
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		if(start==end) {
			tree[node] = new ArrayList<Integer>();
			tree[node].add(arr[start]);
			return;
		}else {
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			
			tree[node] = merge(tree[node*2],tree[node*2+1]);
			return;
		}
	}
	
	//어떤 배열에 대해 [left : right] 범위에서 val보다 큰 숫자의 수를 반환하는 메소드
	public static int query(int node, int start, int end, int left, int right, int val) {
		//탐색하는 범위를 벗어나면 0을 리턴하여 쿼리에 영향을 주지 않게 함
		if(end<left||start>right) {
			return 0;
		}
		//탐색하는 범위안에 완전히 포함되는경우
		if(left<=start&&end<=right) {
			//특정 값인 K보다 큰 값이 처음 나타나게되는 인덱스를 빼서 K보다 큰 값의 개수를 구하여 리턴함
			return tree[node].size()-upperbound(tree[node],val);
		}
		return query(node*2,start,(start+end)/2,left,right,val)+query(node*2+1,(start+end)/2+1,end,left,right,val);
	}
	
	//머지소트트리 구현을 위한 머지 메소드
	public static ArrayList<Integer> merge(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		//어레이리스트이므로 remove()의 경우 O(N)의 시간복잡도가 필요하므로
		//대신 get()을 통해 O(1)로 해결할 수 있도록 함
		int idx1=0, idx2=0;
		
		//머징을 수행함
		while(idx1<arr1.size()&&idx2<arr2.size()) {
			if(arr1.get(idx1)<=arr2.get(idx2)) {
				result.add(arr1.get(idx1++));
			}else {
				result.add(arr2.get(idx2++));
			}
		}
		
		while(idx1<arr1.size()) {
			result.add(arr1.get(idx1++));
		}
		
		while(idx2<arr2.size()) {
			result.add(arr2.get(idx2++));
		}
		
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		int N = Integer.parseInt(br.readLine());
		arr = new int[N];
		
		//세그먼트 트리의 크기는 간단하게 배열의크기 * 4로 처리할 수도 있음
		tree = new ArrayList[N*4];
		
		String[] temp = br.readLine().split(" ");
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리 초기화
		init(1,0,N-1);
		
		int Q = Integer.parseInt(br.readLine());
		int last_ans = 0;
		
		//쿼리를 수행함
		for(int l=0; l<Q; l++) {
			temp = br.readLine().split(" ");
			int a = Integer.parseInt(temp[0]);
			int b = Integer.parseInt(temp[1]);
			int c = Integer.parseInt(temp[2]);
			
			int i = (a ^ last_ans)-1;
			int j = (b ^ last_ans)-1;
			int k = (c ^ last_ans);
			last_ans = query(1,0,N-1,i,j,k);
			bw.write(last_ans+"\n");
		}
		bw.flush();
	}
}
