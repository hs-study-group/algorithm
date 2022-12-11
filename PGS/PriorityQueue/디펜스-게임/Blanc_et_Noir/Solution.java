//https://school.programmers.co.kr/learn/courses/30/lessons/142085

import java.util.*;

class Solution {
    public int solution(int n, int k, int[] enemy) {
    	//현재 라운드까지 만난 병사의 숫자가 큰 순으로 k개를 저장할 우선순위큐 선언
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        
        for(int i=0; i<enemy.length; i++){
        	//해당 라운드의 병사를 무적권으로 막았다고 가정함
            pq.add(enemy[i]);
            
            //만약 무적권이 모자랄 경우
            if(pq.size()>k){
            	//가장 적은 수의 병사는 무적권 없이 병사를 소모하여 막았다고 처리함
                n -= pq.poll();
            }
            
            //남은 병사의 수가 0미만일 경우, 바로 이전 라운드가 최대한으로 막을 수 있는 라운드임
            if(n<0){
                return i;
            }
        }
        
        //중간에 return한 적이 없다면 모든 라운드를 방어한 것임
        return enemy.length;
    }
}