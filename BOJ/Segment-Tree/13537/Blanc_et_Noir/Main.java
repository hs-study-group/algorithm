//https://www.acmicpc.net/problem/13537

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static ArrayList<Integer>[] tree;
	static int[] arr;
	
	//두 리스트를 오름차순 정렬하여 하나의 리스트로 병합하는 메소드
	public static ArrayList<Integer> merge(ArrayList<Integer> list1, ArrayList<Integer> list2){
		int idx1 = 0, idx2 = 0;
		
		//병합된 결과를 저장할 리스트
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		//두 리스트의 인덱스가 범위를 모두 벗어나지 않았으면
		while(idx1<list1.size()&&idx2<list2.size()) {
			//두 리스트 중에서 더 적은 값을 가리키고있는 리스트에서 값을 꺼내어 결과 리스트에 추가함
			if(list1.get(idx1)<=list2.get(idx2)) {
				list.add(list1.get(idx1++));
			}else{
				list.add(list2.get(idx2++));
			}
		}
		
		//리스트의 남은 요소를 전부 결과 리스트에 추가함
		while(idx1<list1.size()) {
			list.add(list1.get(idx1++));
		}
		
		//리스트의 남은 요소를 전부 결과 리스트에 추가함
		while(idx2<list2.size()) {
			list.add(list2.get(idx2++));
		}
		
		//병합된 결과 리스트를 반환함
		return list;
	}
	
	//어떤 리스트에서 val값보다 큰 값이 처음 나타나는 위치를 반환하는 upperbound 메소드
	//그러한 값이 없으면 리스트의 길이를 반환함
	public static int upperbound(ArrayList<Integer> list, int val) {
		//start는 0, end는 list의 크기로 초기화 함
		int start = 0, end = list.size();
		
		while(start<end) {
			int mid = (start+end)/2;
			//중간 위치의 값이 만약 val값보다 작거나, 같은 값이라면
			if(list.get(mid)<=val) {
				//start위치를 중간위치 +1로 변경함
				start = mid+1;
			//아니라면
			}else {
				//end위치를 중간위치로 변경함
				end = mid;
			}
		}
		
		//list의 크기 - 처음으로 val보다 큰 값이 나타나는 위치 = val보다 큰 값의 개수임
		//만약 그러한 값이 없다면 list의 크기 - list의 크기 = 0이 반환됨
		return list.size()-end;
	}
	
	//세그먼트 트리를 초기화 하는 메소드
	public static void init(int node, int start, int end) {
		//start와 end가 같다면
		if(start==end) {
			//트리의 해당 위치에 리스트를 생성하고 그곳에 정수 값을 추가함
			tree[node] = new ArrayList<Integer>();
			tree[node].add(arr[start]);
		//아니라면
		}else {
			//왼쪽, 오른쪽 자식 노드에 대해 재귀적으로 세그먼트 트리 초기화를 수행함
			init(node*2,start,(start+end)/2);
			init(node*2+1,(start+end)/2+1,end);
			//부모 트리는 자식 트리들을 병합한 결과를 가지고 있어야함
			tree[node] = merge(tree[node*2],tree[node*2+1]);
		}
	}
	
	//어떤 특정한 값 val에 대하여 특정 구간에서의 자신보다 큰 값의 개수를 구하는 메소드 
	public static int query(int node, int start, int end, int left, int right, int val) {
		//start, end가 탐색하고자 하는 구간을 완전히 벗어난 경우
		//0을 리턴하여 쿼리 결과에 영향이 없도록 함
		if(start>right||end<left) {
			return 0;
		}
		//start, end가 탐색하고자 하는 구간에 완전히 포함되는 경우
		if(left<=start&&end<=right) {
			//upperbound( )메소드를 통해 val보다 큰 값의 개수를 반환함
			return upperbound(tree[node],val);
		}
		
		//왼쪽, 오른쪽 자식들에 대해서 쿼리를 재귀적으로 수행함
		return query(node*2,start,(start+end)/2,left,right,val) + query(node*2+1,(start+end)/2+1,end,left,right,val);
	}
	
	public static void main(String[] args) throws Exception{
		int N = Integer.parseInt(br.readLine());
		
		String[] temp = br.readLine().split(" ");
		//세그먼트 트리의 크기는 대략 배열의 크기의 4배면 충분함
		tree = new ArrayList[4*N];
		arr = new int[N];
		
		//배열에 정보를 입력받음
		for(int i=0; i<temp.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//세그먼트 트리를 초기화 함
		init(1,0,N-1);
		
		//쿼리의 개수를 입력받음
		int M = Integer.parseInt(br.readLine());
		
		//쿼리를 반복하여 수행함
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			//구간 A, B는 인덱스보다 1이 크므로 1을 빼서 인덱스로 변환함
			int A = Integer.parseInt(temp[0])-1;
			int B = Integer.parseInt(temp[1])-1;
			
			//[A, B] 구간에서의 C보다 큰 값의 개수를 구함
			int C = Integer.parseInt(temp[2]);
			bw.write(query(1,0,N-1,A,B,C)+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}