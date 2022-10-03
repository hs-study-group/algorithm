//https://www.acmicpc.net/problem/20040
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	//어떤 정점에 대하여 자신의 최상위 부모를 얻는 메소드
	public static int find(int[] parent, int i) {
		if(parent[i]==i) {
			return i;
		}else {
			return find(parent,parent[i]);
		}
	}
	
	//두 정점을 하나의 집합으로 만드는 메소드
	public static boolean union(int[] parent, int v1, int v2) {
		//두 정점의 최상위 부모를 얻음
		int pv1 = find(parent,v1);
		int pv2 = find(parent,v2);
		
		//두 정점의 최상위 부모가 서로 다르면 서로 다른 집합에 해당하는 것임
		//즉, 해당 간선을 선택해도 사이클이 형성되지 않음
		if(pv1!=pv2) {
			//최상위 부모의 번호를 기준으로 더 작은 최상위 부모가
			//더 큰 최상위 부모의 부모가 되게끔 설정함
			if(pv1<=pv2) {
				parent[pv2] = pv1;
			}else {
				parent[pv1] = pv2;
			}
			return true;
		//만약 최상위 부모가 같다면 서로 같은 집합에 해당하는 것임
		//즉, 해당 간선을 선택하면 사이클이 형성됨을 의미함
		}else {
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		int[] parent = new int[N];
		int result = 0;
		
		//특정 정점의 부모를 자기자신으로 초기화 함
		for(int i=0; i<parent.length; i++) {
			parent[i] = i;
		}
		
		//간선 정보를 입력받음
		for(int i=0; i<M; i++) {
			temp = br.readLine().split(" ");
			int v1 = Integer.parseInt(temp[0]);
			int v2 = Integer.parseInt(temp[1]);
			
			//해당 문제는 최소비용 스패닝트리를 구성하는 것이 목적이 아니므로
			//굳이 간선의 정보를 저장하는 클래스를 선언하지 않았음
			
			//해당 간선을 선택했을때 사이클이 형성되면
			//그 즉시 탐색을 종료하고 몇번째 선택에서 사이클이 발생했는지 기록함
			if(!union(parent,v1,v2)) {
				result = i + 1;
				break;
			}
		}
		
		bw.write(result+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}