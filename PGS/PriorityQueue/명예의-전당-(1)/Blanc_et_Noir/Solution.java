//https://school.programmers.co.kr/learn/courses/30/lessons/138477

import java.util.*;

class Solution {
    public int[] solution(int k, int[] score) {
        int[] answer = new int[score.length];
        
        //명예의 전당의 역할을 수행할 우선순위큐(힙) 선언
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        
        for(int i=0; i<score.length; i++){
        	//점수를 힙에 추가함
            pq.add(score[i]);
            
            //명예의 전당에 등록된 가수들의 점수가 k개보다 많다면
            if(pq.size()>k){
            	//가장 낮은 점수를 명예의 전당에서 제외시킴
                pq.poll();
            }
            
            //가장 낮은 점수를 명예의 전당 점수로 발표함
            answer[i] = pq.peek();
        }
        
        return answer;
    }
}