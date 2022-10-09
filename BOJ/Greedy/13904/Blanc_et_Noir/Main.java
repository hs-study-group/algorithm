//https://www.acmicpc.net/problem/13904

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		int sum = 0;
		
		//가장 큰 과제의 점수를 반환하는 우선순위 큐
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				if(n1>n2) {
					return -1;
				}else if(n1<n2) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//마감일이 d일인 과제들의 점수 w를 저장할 이중 리스트 선언
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		
		//이중리스트 초기화
		for(int i=0; i<1001; i++) {
			list.add(new ArrayList<Integer>());
		}
		
		//가장 큰 값을 갖는 마감일을 저장할 변수
		int D = -1;
		
		//각 과제들의 마감일과 점수를 입력받음
		for(int i=0; i<N; i++) {
			String[] temp = br.readLine().split(" ");
			int d = Integer.parseInt(temp[0]);
			int w = Integer.parseInt(temp[1]);
			
			//마감일중에서 가장 큰 값을 기록함
			D = Math.max(D, d);
			
			//마감일이 d일인 과제의 가중치가 w임을 저장
			list.get(d).add(w);
		}
		
		//1, 2, 3일차와 같이 오름차순이 아닌
		//3, 2, 1일차와 같이 내림차순으로 해결할 과제를 선택하고자 함
		while(D>0) {
			//D일에 해결할 수 있는 과제들을 모두 우선순위 큐에 추가함
			Iterator<Integer> itor = list.get(D).iterator();
			while(itor.hasNext()) {
				pq.add(itor.next());
			}
			
			//D일에 해결할 수 있는 과제가 남아있다면
			if(!pq.isEmpty()) {
				//그중에서 가장 큰 점수를 갖는 과제를 해결함
				sum += pq.poll();
			}
			
			//D-1일차에 대하여 탐색함
			D--;
		}
		
		//점수의 최대치를 출력함
		bw.write(sum+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}