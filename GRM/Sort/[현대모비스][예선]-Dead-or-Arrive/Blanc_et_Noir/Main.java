//https://level.goorm.io/exam/152114/%ED%98%84%EB%8C%80%EB%AA%A8%EB%B9%84%EC%8A%A4-%EC%98%88%EC%84%A0-dead-or-arrive/quiz/1

import java.io.*;
import java.util.*;

//자동차의 번호 n, 자동차의 속도 v, 자동차의 무게 w를 저장하는 클래스
class Node implements Comparable<Node>{
	int n, v, w;
	Node(int n, int v, int w){
		this.n=n;
		this.v=v;
		this.w=w;
	}
	
	//속도가 더 빠른 자동차가 먼저 반환되며
	//속도가 같다면 무게가 더 많은 자동차가 먼저 반환되고
	//무게도 같다면 번호가 더 큰 자동차가 먼저 반환됨
	@Override
	public int compareTo(Node n){
		if(this.v>n.v){
			return -1;
		}else if(this.v<n.v){
			return 1;
		}else{
			if(this.w>n.w){
				return -1;
			}else if(this.w<n.w){
				return 1;
			}else{
				if(this.n>n.n){
					return -1;
				}else if(this.n<n.n){
					return 1;
				}else{
					return 0;
				}
			}
		}
	}
}

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());
		long answer = 0;
		
		Node[] nodes = new Node[N];
		
		//자동차들의 정보를 저장함
		for(int i=0;i<N;i++){
			String[] temp = br.readLine().split(" ");
			int v = Integer.parseInt(temp[0]);
			int w = Integer.parseInt(temp[1]);
			nodes[i] = new Node(i+1,v,w);
		}
		
		//자동차를 기준에 맞게 정렬함
		Arrays.sort(nodes);
		
		//가장 최근에 결승선에 도착한 자동차의 속도, 무게를 저장
		int curV = nodes[0].v;
		int curW = nodes[0].w;
		
		//첫 자동차는 무조건 결승선에 도착하므로 자동차 번호를 카운트함
		answer += nodes[0].n;
		
		//두 번째 자동차부터는 조건에 따라 처리함
		for(int i=1;i<N;i++){
			int v = nodes[i].v;
			int w = nodes[i].w;
			
			//현재 자동차의 속도가 최근에 도착한 자동차의 속도와 다르면
			if(curV!=v){
				//해당 자동차는 결승선에 도착할 수 있음
				answer += (long)nodes[i].n;
				//최근에 도착한 자동차의 속도, 무게를 갱신함
				curV = v;
				curW = w;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}