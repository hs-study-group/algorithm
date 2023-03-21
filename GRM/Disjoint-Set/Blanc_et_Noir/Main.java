//https://level.goorm.io/exam/49052/%EA%B7%B8%EB%A3%B9-%EC%A7%80%EC%A0%95/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//idx 인덱스를 갖는 정점의 최상위 부모를 리턴하는 find 메소드
	public static int find(int[] parents, int idx){
		//최상위 부모가 자기 자신이라면
		if(parents[idx]==idx){
			//자기 자신을 리턴함
			return idx;
		//최상위 부모가 자기 자신이 아니라면
		}else{
			//자신의 부모에 대하여 재귀적으로 부모를 구하고
			//자신의 조부모를 자신의 부모로 설정함
			return parents[idx] = find(parents,parents[idx]);
		}
	}
	
	//두 정점 idx1, idx2를 하나의 그룹으로 묶는 union 메소드
	public static void union(int[] parents, int idx1, int idx2){
		int parent1 = find(parents,idx1);
		int parent2 = find(parents,idx2);
		
		//최상위 부모가 동일하다면
		if(parent1==parent2){
			//이미 두 정점은 같은 그룹에 속하는 것임
			return;
		//최상위 부모가 다르다면
		}else{
			//두 최상위 부모중 더 작은 최상위 부모가 더 큰 최상위 부모의 부모가 되도록 함
			if(parent1<parent2){
				parents[parent2] = parent1;
			}else{
				parents[parent1] = parent2;
			}
		}		
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		final int N = Integer.parseInt(temp[0]);
		final int M = Integer.parseInt(temp[1]);
		int[] parents = new int[N];
		
		//최상위 부모를 자기자신으로 초기화함
		for(int i=0;i<parents.length;i++){
			parents[i]=i;
		}
		
		//두 정점을 입력 받고, 그 정점을 하나의 그룹으로 묶음
		for(int i=0;i<M;i++){
			temp = br.readLine().split(" ");
			int a = Integer.parseInt(temp[0])-1;
			int b = Integer.parseInt(temp[1])-1;
			
			union(parents,a,b);
		}
		
		//중복없이 최상위 부모 번호를 저장할 set선언
		HashSet<Integer> set = new HashSet<Integer>();
		
		//최상위 부모가 동일하다면 동일한 그룹에 속하는 것임
		//따라서, 최상위 부모를 중복없이 카운트 해야 하므로 set에 최상위 부모를 저장함
		for(int i=0;i<parents.length;i++){
			set.add(find(parents,i));
		}
		
		//set의 크기가 곧 분리집합의 개수임
		bw.write(set.size()+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}