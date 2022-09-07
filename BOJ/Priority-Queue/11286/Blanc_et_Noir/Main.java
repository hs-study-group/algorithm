//https://www.acmicpc.net/problem/11286

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws IOException {
		//우선순위큐를 구현함
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				//만약 두 정수 A, B에 대하여 A의 절댓값이 더 작다면 A가 반환됨
				if(Math.abs(i1)<Math.abs(i2)) {
					return -1;
				//만약 두 정수 A, B에 대하여 B의 절댓값이 더 작다면 B가 반환됨
				}else if(Math.abs(i1)>Math.abs(i2)) {
					return 1;
				//만약 절댓값이 같다면
				}else {
					//A가 음수, B가 양수라면 A가 반환됨
					if(i1<i2) {
						return -1;
					//A가 양수, B가 음수라면 B가 반환됨
					}else if(i1>i2) {
						return 1;
					//절대값, 부호 모두 같다면 동등한 레벨임
					}else {
						return 0;
					}
				}
			}
		});
		
		int N = Integer.parseInt(br.readLine());
		
		for(int i=0; i<N; i++) {
			int X = Integer.parseInt(br.readLine());
			//입력받은 정수가 0이 아니라면
			if(X!=0) {
				//우선순위큐에 추가함
				pq.add(X);
			//입력받은 정수가 0이라면
			}else {
				//우선순위큐가 비어있지 않다면
				if(!pq.isEmpty()) {
					//우선순위큐에서 값을 하나 꺼내서 출력함
					bw.write(pq.poll()+"\n");
				//우선순위큐가 비어있다면
				}else {
					//0을 출력함
					bw.write(0+"\n");
				}
			}
		}
		
		bw.flush();
		bw.close();
	}
}