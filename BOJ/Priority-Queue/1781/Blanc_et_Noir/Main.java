//https://www.acmicpc.net/problem/1781

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

//데드라인 d, 얻을 수 있는 컵라면의 수c를 저장할 과제 클래스 선언
class Node{
	long d, c;
	Node(long d, long c){
		this.d=d;
		this.c=c;
	}
}

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		final int N = Integer.parseInt(br.readLine());
		
		//데드라인이 가장 많이 남아있는 과제가 먼저 반환되도록 하는 우선순위큐
		PriorityQueue<Node> pq1 = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.d>n2.d) {
					return -1;
				}else if(n1.d<n2.d) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//가장 컵라면을 많이 받을 수 있는 과제가 먼저 반환되도록 하는 우선순위큐
		PriorityQueue<Node> pq2 = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.c>n2.c) {
					return -1;
				}else if(n1.c<n2.c) {
					return 1;
				}else {
					return 0;
				}
			}
		});
		
		//과제의 정보를 입력받고, 데드라인이 많이 남은 과제가 먼저 반환되는 우선순위큐에 추가함
		for(int i=0;i<N;i++) {
			String[] temp = br.readLine().split(" ");
			long d = Long.parseLong(temp[0]);
			long c = Long.parseLong(temp[1]);
			pq1.add(new Node(d,c));
		}
		
		//기준이 될 날짜
		long curD = pq1.peek().d;
		//최대로 얻을 수 있는 컵라면의 수
		long sum = 0L;
		
		while(curD>0) {
			//curD보다 큰 데드라인을 가진 과제를 모두 다른 우선순위큐에 추가함
			//pq2는 얻을 수 있는 컵라면의 수가 큰 과제 정보가 먼저 반환됨 
			while(!pq1.isEmpty()&&pq1.peek().d>=curD) {
				pq2.add(pq1.poll());
			}
			
			//curD일차에 해결할 수 있는 과제가 남아있다면
			if(!pq2.isEmpty()) {
				//그중 가장 많은 컵라면을 얻을 수 있는 과제를 수행함
				sum += pq2.poll().c;
			}
			
			//기준 날짜를 감소시킴
			curD--;
		}
		
		bw.write(sum+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}