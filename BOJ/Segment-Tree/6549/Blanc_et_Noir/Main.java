//https://www.acmicpc.net/problem/6549

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	static long[] arr;
	static long[][] tree;
	
	//두 쿼리의 결과에 대하여 더 작은 높이를 갖는 쿼리 결과를 리턴하는,
	//두 쿼리의 결과의 높이가 동일하다면 더 작은 인덱스를 갖는 결과를 리턴하는 메소드
	public static long[] process(long[] r1, long[] r2) {
		if(r1[0]<r2[0]) {
			return r1;
		}else if(r1[0]>r2[0]) {
			return r2;
		}else {
			if(r1[1]<=r2[1]) {
				return r1;
			}else {
				return r2;
			}
		}
	}
	
	//세그먼트 트리를 초기화하는 메소드
	public static void init(int node, int start, int end) {
		//만약 리프노드라면
		if(start==end) {
			//해당 트리의 노드에는 해당 인덱스에 대응되는 높이와 인덱스 값을 저장함 
			tree[node][0] = arr[start];
			tree[node][1] = start;
			return;
		//만약 리프노드가 아니라면
		}else {
			int mid = (start+end)/2;
			
			//두 자식노드에 대하여 초기화를 재귀적으로 수행함
			init(node*2,start,mid);
			init(node*2+1,mid+1,end);
			
			//부모 노드는 자식노드중 더 작은 높이 값을 갖는 노드의 값으로 함
			//두 자식노드의 높이가 동일하다면 인덱스가 더 작은 노드의 것으로 함
			tree[node] = process(tree[node*2],tree[node*2+1]);
			return;
		}
	}
	
	//left, right 구간에 대하여 최소높이와 그 인덱스를 반환하는 메소드
	public static long[] query(int node, int start, int end, int left, int right) {
		//현재 탐색중인 구간이 탐색할 구간에서 완전히 벗어났다면
		if(start>right||end<left) {
			//쿼리의 결과에 영향을 주지 않도록 MAX_VALUE를 높이로 설정함
			return new long[] {Long.MAX_VALUE,-1};
		//현재 탐색중인 구간이 탐색할 구간에 완전히 포함되는 경우
		}else if(left<=start&&end<=right) {
			//해당 트리의 노드를 리턴함
			return tree[node];
		//현재 탐색중인 구간이 탐색할 구간에 걸치는 경우
		}else {
			int mid = (start+end)/2;
			
			//두 자식 노드에 대하여 쿼리를 재귀적으로 수행한 후, 그 결과값중 높이가 더 작은 쿼리 결과를 리턴함
			//만약 높이가 동일하다면, 더 작은 인덱스 값을 갖는 쿼리의 결과를 리턴함 
			return process(query(node*2,start,mid,left,right),query(node*2+1,mid+1,end,left,right));
		}
	}
	
	//재귀적으로 left, right 구간에 대하여 최소높이와 그 인덱스를 구한 후
	//그때의 넓이의 최대값을 리턴하는 메소드
	public static long recursive(int left, int right) {
		//left가 right보다 크다면
		if(left>right) {
			//탐색할 필요가 없으며, 전체 결과에 영향을 주지 않도록 음수를 리턴함
			return -1;
		//left, right의 값이 정상적이면
		}else {
			//left, right 구간에서의 최소높이와 그 인덱스를 얻음
			long[] r = query(1,0,arr.length-1,left,right);
			
			//그때의 직사각형 넓이를 구함
			long extent = (right-left+1)*r[0];
			
			//현재 계산한 직사각형 넓이와 최소높이가 되는 인덱스를 기준으로 양쪽으로 분할하여 구해낸 최대 넓이중
			//가장 큰 값을 결과값으로 하여 리턴함
			return getMax(extent,recursive(left,(int)r[1]-1),recursive((int)r[1]+1,right));
		}
	}
	
	//e1, e2, e3 넓이중 가장 큰 값을 리턴하는 메소드
	public static long getMax(long e1, long e2, long e3) {
		long[] arr = {e1,e2,e3};
		Arrays.sort(arr);
		return arr[2];
	}
	
	public static void main(String[] args) throws Exception {
		
		while(true) {
			String[] temp = br.readLine().split(" ");
			int N = Integer.parseInt(temp[0]);
			
			//만약 입력이 0이라면
			if(N==0) {
				//더이상 테스트를 수행하지 않음
				break;
			//입력이 0이 아니라면
			}else {
				//배열과 세그먼트 트리를 선언함
				arr = new long[N];
				tree = new long[4*N][2];
				
				//배열에 히스토그램 높이를 입력 받음
				for(int i=0; i<N; i++) {
					arr[i] = Integer.parseInt(temp[i+1]);
				}
				
				//세그먼트 트리를 초기화 함
				init(1,0,N-1);
				
				//0 ~ N-1 구간에서의 직사각형 최대 크기를 출력함
				bw.write(recursive(0,N-1)+"\n");
			}
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}