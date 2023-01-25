//https://school.programmers.co.kr/learn/courses/30/lessons/42747

import java.util.*;

class Solution {
    public int solution(int[] citations) {
        int answer = 0;
        
        //논문 인용 횟수를 오름차순 정렬함
        Arrays.sort(citations);

        for(int i=0; i<citations.length; i++){
        	//어떤 논문의 인덱스가 citatations.length - i인데
        	//만약 해당 논문이 그 인덱스값 이상으로 인용되었다면
        	//그것이 논문 저자의 H-Index가 된다.
            if(citations[i]>=citations.length - i){
                answer = citations.length - i;
                break;
            }
        }
        return answer;
    }
}