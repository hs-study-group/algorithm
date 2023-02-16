//https://school.programmers.co.kr/learn/courses/30/lessons/159994

import java.util.*;

class Solution {
    public String solution(String[] cards1, String[] cards2, String[] goal){
    	//기본적으로는 가능하다고 가정
        String answer = "Yes";
        
        //두 문자열 배열의 문자열들을 저장할 큐 선언
        Queue<String> q1 = new LinkedList<String>();
        Queue<String> q2 = new LinkedList<String>();
        
        //각각의 문자열 배열의 문자열들을 큐에 추가함
        for(String str : cards1){
            q1.add(str);
        }
        
        for(String str : cards2){
            q2.add(str);
        }
        
        //peek( )메소드 사용시 예외가 발생할 수 있으므로
        //간편하게 처리하기 위해 의미없는 문자열들을 큐의 마지막에 추가함
        q1.add("");
        q2.add("");
        
        for(String str : goal){
        	//만약 첫 번째 큐의 맨 앞에 있는 문자열과 동일하면
            if(q1.peek().equals(str)){
            	//그 문자열을 제거함
                q1.poll();
            //만약 두 번째 큐의 맨 앞에 있는 문자열과 동일하면
            }else if(q2.peek().equals(str)){
            	//그 문자열을 제거함
                q2.poll();
            //두 큐의 맨 앞 문자열중 그 어느 것과도 동일하지 않으면
            }else{
            	//No를 리턴해야함
                answer = "No";
                break;
            }
        }
        
        return answer;
    }
}