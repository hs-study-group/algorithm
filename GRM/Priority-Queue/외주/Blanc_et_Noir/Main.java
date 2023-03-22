//https://level.goorm.io/exam/49104/%EC%99%B8%EC%A3%BC/quiz/1

import java.io.*;
import java.util.*;

//외주를 통해 얻는 보수 m, 기한 d를 저장할 노드 클래스 선언
class Node{
	long m, d;
	Node(long m, long d){
		this.m=m;
		this.d=d;
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		String[] temp;
		
		//기한이 더 많이 남은 외주 작업이 먼저 반환되도록 하는 우선순위 큐 선언
		PriorityQueue<Node> remains = new PriorityQueue<Node>(new Comparator<Node>(){
			@Override
			public int compare(Node n1, Node n2){
				if(n1.d>n2.d){
					return -1;
				}else if(n1.d<n2.d){
					return 1;
				}else{
					return 0;
				}
			}
		});
		
		//현재 날짜에 해결할 수 있는 모든 작업들을 수행할 수 있는 작업들을 저장해두는 우선순위 큐 선언
		//현재 날짜가 예를들어 3일이라고 한다면, 마감기한이 3, 4, 5, 6 ...일인 모든 작업들이 저장되며
		//얻을 수 있는 보수가 큰 작업이 먼저 반환되도록 함
		PriorityQueue<Node> ready = new PriorityQueue<Node>(new Comparator<Node>(){
			@Override
			public int compare(Node n1, Node n2){
				if(n1.m>n2.m){
					return -1;
				}else if(n1.m<n2.m){
					return 1;
				}else{
					return 0;
				}
			}
		});
		
		//모든 외주 작업에 대한 정보를 입력받고, 이를 remains 큐에 저장함
		for(int i=0;i<N;i++){
			temp = br.readLine().split(" ");
			long m = Long.parseLong(temp[0]);
			long d = Long.parseLong(temp[1]);
			remains.add(new Node(m,d));
		}
		
		//현재 날짜를 의미하는 변수를 선언, 마감 기한이 가장 많이 남은 작업과 동일한 날짜로 지정함
		long currentD = remains.peek().d;
		
		//외주 작업을 통해 얻을 수 있는 보수의 최대값을 저장할 변수 선언
		long answer = 0;
		
		while(currentD>0){
			//아직 탐색하지 못한 작업들이 남아있으면, 현재 날짜가 마감 기한인 모든 작업들을 ready큐로 이동시킴
			while(!remains.isEmpty()&&remains.peek().d==currentD){
				ready.add(remains.poll());
			}
			
			//현재 날짜에 처리할 수 있는 작업들이 아직 남아있다면
			if(!ready.isEmpty()){
				//그 작업중 보수가 가장 많은 작업을 처리함
				answer += ready.poll().m;
			}
			
			//현재 날짜를 감소시킴
			currentD--;
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}