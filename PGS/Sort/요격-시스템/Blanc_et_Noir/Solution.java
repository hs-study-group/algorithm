//https://school.programmers.co.kr/learn/courses/30/lessons/181188

import java.util.*;

//폭격 미사일 구간의 시작 및 종료위치 s, e를 저장할 노드 클래스 
class Node implements Comparable<Node>{
    int s, e;
    Node(int s,int e){
        this.s=s;
        this.e=e;
    }
    
    //종료 위치가 더 앞에 서는 경우의 노드 정보가 먼저 반환되도록 함
    @Override
    public int compareTo(Node n){
        if(this.e<n.e){
            return -1;
        }else if(this.e>n.e){
            return 1;
        }else{
            return 0;
        }
    }
}

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        //우선순위 큐 선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for(int[] t : targets){
        	//우선순위 큐에 노드 정보를 추가함
            pq.add(new Node(t[0],t[1]));
        }
        
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //정답을 1증가시킴
            answer++;
            
            //만약 현재 기준이 되는 폭격미사일의 구간의 e보다
            //우선순위큐에 있는 노드 정보의 s가 더 작다면
            while(!pq.isEmpty()&&pq.peek().s<n.e){
            	//해당 노드 정보를 우선순위 큐에서 제거함
                pq.poll();
            }
        }
        
        return answer;
    }
}