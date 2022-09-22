//https://school.programmers.co.kr/learn/courses/30/lessons/68644

import java.util.*;

class Solution {
	//이미 확인했던 숫자인지 아닌지 판단하기 위한 해시맵
    static HashMap<Integer, Boolean> hm = new HashMap<Integer,Boolean>();
    
    //정렬을 수행하는 대신 우선순위큐에 해당 숫자를 추가했다가 우선순위대로 꺼내고자 함
    static PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
        @Override
        //작은 정수가 먼저 반환되도록 함
        public int compare(Integer n1, Integer n2){
            if(n1<n2){
                return -1;
            }else if(n1>n2){
                return 1;
            }else{
                return 0;
            }
        }
    });
    
    //numbers 배열에서 c개의 정수의 합을 가능한 모든 경우의 수에 따라 구해냄
    public static void combinate(int[] numbers,boolean[] v, int idx, int c, int result){
    	//c개의 숫자를 모두 더했다면
        if(c<=0){
        	//그 결과가 기존에 없던 새로운 값이라면
            if(!hm.containsKey(result)){
            	//그것을 우선순위큐에 추가함
                pq.add(result);
                
                //해시맵에 해당 값을 기록해둠
                hm.put(result,true);
            }
            return;
        }else{
        	//numbers.length개의 숫자중에서 c개의 숫자의 조합을 구함
            for(int i=idx; i<numbers.length; i++){
            	//해당 숫자를 사용했던 적이 없다면
                if(!v[i]){
                	//숫자를 사용한 것으로 처리함
                    v[i] = true;
                    //조합을 재귀적으로 구하고, 합계 또한 누적하여 구함
                    combinate(numbers,v,i,c-1,result+numbers[i]);
                    //숫자를 사용하지 않은 것으로 처리하여 다음에 다시 또 사용할 수 있도록 함
                    v[i] = false;
                }
            }
        }
    }
    
    public int[] solution(int[] numbers) {
    	//조합을 구하고, 그 조합의 결과를 구함
        combinate(numbers,new boolean[numbers.length],0,2,0);
        
        int[] answer = new int[pq.size()];
        
        int idx = 0;
        
        //우선순위 큐에 저장된 값을 순서대로 꺼내어 배열에 저장함
        while(!pq.isEmpty()){
            answer[idx++] = pq.poll();
        }
        
        return answer;
    }
}