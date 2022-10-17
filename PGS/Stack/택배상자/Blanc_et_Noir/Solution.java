//https://school.programmers.co.kr/learn/courses/30/lessons/131704

import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0;
        
        //보조 컨테이너의 역할을 할 스택
        Stack<Integer> s = new Stack<Integer>();
        
        //메인 컨테이너의 역할을 할 큐
        Queue<Integer> q = new LinkedList<Integer>();
        
        //택배를 순서대로 1부터 큐에 추가함
        for(int i=0; i<order.length; i++){
            q.add(i+1);
        }
        
        //현재 탐색중인 택배의 인덱스
        int idx = 0;
        
        while(true){
        	//만약 컨테이너 벨트 맨 앞의 택배가 현재 순서에 맞다면
            if(!q.isEmpty()&&q.peek()==order[idx]){
            	//해당 택배를 싣고 answer를 1증가시킴
                q.poll();
                answer++;
                
                //다음 순서로 실어야 할 택배를 찾음
                idx++;
            //만약 최근에 잠시 보조 컨테이너에 넣었 택배라면
            }else if(!s.isEmpty()&&s.peek()==order[idx]){
            	//보조 컨테이너에서 택배를 꺼내고 answer를 1증가시킴
                s.pop();
                answer++;
                
                //다음 순서로 실어야 할 택배를 찾음
                idx++;
            //만약 해당 택배가 메인 컨테이너에도 없고, 보조 컨테이너에도 없다면
            }else if(!q.isEmpty()){
            	//해당 택배를 임시로 보조컨테이너에 넣음
                s.push(q.poll());
            //더이상 실을 수 있는 택배가 없다면
            }else{
            	//무한 반복문을 탈출함
                break;
            }
        }
        
        return answer;
    }
}