//https://school.programmers.co.kr/learn/courses/30/lessons/12927

import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;
        
        //남은 작업량이 가장 큰 작업부터 반환되도록 하는 우선순위 큐 선언
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
            @Override
            public int compare(Integer n1, Integer n2){
                if(n1>n2){
                    return -1;
                }else if(n1<n2){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //모든 작업을 우선순위 큐에 추가함
        for(int i=0; i<works.length; i++){
            pq.add(works[i]);
        }
        
        
        for(int i=0; i<n; i++){
        	//우선순위큐가 비어있다면, 즉 모든 작업을 주어진 n시간내로 끝낼 수 있다면
            if(pq.isEmpty()){
            	//굳이 더이상 계산할 필요가 없음
                break;
            }
            
            //남은 작업량이 가장 큰 작업의 시간을 1 감소시킴
            int temp = pq.poll()-1;
            
            //그럼에도 아직 해당 작업에 대한 작업량이 남아 있다면
            if(temp>0){
            	//해당 작업을 다시 우선순위 큐에 추가함
                pq.add(temp);
            }
        }
        
        //우선순위 큐에 있는 모든 잔여 작업들의 제곱의 합을 구함
        while(!pq.isEmpty()){
            n = pq.poll();
            answer += n*n;
        }
        
        //야근 지수를 반환함
        return answer;
    }
}