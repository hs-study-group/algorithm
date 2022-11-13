import java.util.*;

class Solution {
    
    public int solution(int[] people, int limit) {
        int answer = 0;
        
        Deque<Integer> dq = new LinkedList<Integer>();
        
        //사람을 몸무게순으로 오름차순 정렬함
        Arrays.sort(people);
        
        //덱에 사람을 몸무게 순으로 추가함
        for(int n : people){
            dq.add(n);
        }
        
        while(!dq.isEmpty()){
        	//덱에 2명이상의 사람이 있을때
            if(dq.size()>=2){
            	//가장 몸무게가 작은 사람과 큰 사람의 무게의 합이 limit이하라면
                if(dq.peekFirst()+dq.peekLast()<=limit){
                	//두 사람을 모두 덱에서 제거함
                    dq.pollFirst();
                    dq.pollLast();
                //limit보다 크다면
                }else{
                	//몸무게가 큰 사람은 절대로 다른사람과 구명보트를 공유할 수 없으므로 혼자서 구명보트에 타도록 함
                    dq.pollLast();
                }
            //덱에 1명의 정보만 존재하면
            }else{
            	//해당 인원은 혼자 구명보트를 탑승함
                dq.pollLast();
            }
            //구명보트 사용 개수를 1 증가시킴
            answer++;
        }
        
        return answer;
    }
}