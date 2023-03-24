//https://level.goorm.io/exam/49112/%EC%A7%95%EA%B2%80%EB%8B%A4%EB%A6%AC-%EA%B1%B4%EB%84%88%EA%B8%B0/quiz/1

import java.io.*;
import java.util.*;

//현재 위치 idx, 독극물의 양 val을 저장할 노드 클래스 선언
class Node{
	int idx, val;
	Node(int idx, int val){
		this.idx = idx;
		this.val = val;
	}
}

class Main {
	//주어진 독극물 배치에 대하여 최소 비용으로 끝에 도달하기 위한 비용을 구하는 메소드
	public static int BFS(int[] arr){
		//더 적은 독극물 양이 누적된 노드가 먼저 반환되며,
		//동일하다면 더 오른쪽에 위치한 노드가 먼저 반환되도록 하는 우선순위 큐 선언
		PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
			@Override
			public int compare(Node n1, Node n2){
				if(n1.val<n2.val){
					return -1;
				}else if(n1.val>n2.val){
					return 1;
				}else{
					if(n1.idx>n2.idx){
						return -1;
					}else if(n1.idx<n2.idx){
						return 1;
					}else{
						return 0;
					}
				}
			}
		});
		
		//시작 위치는 -1, 독극물의 양은 0으로 처리하고 큐에 추가함
		pq.add(new Node(-1,0));
		
		int[] v = new int[arr.length];
		
		//방문 배열을 모두 MAX_VALUE로 초기화
		for(int i=0;i<v.length;i++){
			v[i] = Integer.MAX_VALUE;
		}
		
		int answer = 0;
		
		while(!pq.isEmpty()){
			Node n = pq.poll();
			
			//만약 3칸이내에 목표 지점이 있다면
			if(n.idx >= arr.length-3){
				//독극물을 더 밟을 필요가 없으므로 그때의 독극물을 리턴함
				answer = n.val;
				break;
			}
			
			//현재 상태에 대하여, 1,2,3칸을 각각 움직여봄
			for(int i=1;i<=3;i++){
				//그렇게 움직였을때의 독극물의 양이 기존에 기록해둔 양보다 적다면
				if(n.idx+i<arr.length&&n.val+arr[n.idx+i]<v[n.idx+i]){
					//해당 위치에 방문하고 큐에 정보를 추가함
					pq.add(new Node(n.idx+i,n.val+arr[n.idx+i]));
					v[n.idx+i] = n.val + arr[n.idx+i];
				}
			}
		}
		return answer;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		final int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		
		String[] temp = br.readLine().split(" ");
		
		//독극물의 양을 입력받음
		for(int i=0;i<N;i++){
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		System.out.println(BFS(arr));
		
	}
}