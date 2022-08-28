//https://school.programmers.co.kr/learn/courses/30/lessons/76502

import java.util.*;

class Solution {
	
	//올바른 괄호 문자열인지 아닌지 판단하는 메소드
    public boolean isGood(String str){
        Stack<Character> s = new Stack<Character>();
        char[] arr = str.toCharArray();
        
        for(int i=0; i<arr.length; i++){
        	//여는 괄호는 무조건 스택에 추가함
            if(arr[i]=='('||arr[i]=='{'||arr[i]=='['){
                s.push(arr[i]);
            //닫는 괄호라면
            }else{
            	//스택이 비어있다면 닫는 괄호와 짝을 짓는 여는 괄호가 없으므로 false
                if(s.isEmpty()){
                    return false;
                //스택에 가장 최근에 추가한 여는 괄호와 짝을 이루는 닫는 괄호라면
                }else if((arr[i]==')'&&s.peek()=='(')||(arr[i]=='}'&&s.peek()=='{')||(arr[i]==']'&&s.peek()=='[')){
                	//스택에서 여는 괄호를 제거함
                    s.pop();
                //스택에 가장 최근에 추가한 여는 괄호가 있긴 하지만 짝을 이루지 않는다면 false
                }else{
                    return false;
                }
            }
        }
        
        //스택이 비어있다면 모든 여는 괄호에 대한 괄호 짝이 모두 존재하는 것이므로 true
        if(s.isEmpty()){
            return true;
        //스택이 비어있지 않다면 짝이 맞지 않는 여는 괄호가 있는 것이므로 false
        }else{
            return false;
        }
    }
    public int solution(String s) {
        int answer = 0;

        for(int i=0; i<s.length(); i++){
        	//괄호문자열을 회전시킨 결과에 대해 올바른 괄호 문자열인지 판단함
            if(isGood(s.substring(i,s.length())+s.substring(0,i))){
            	//괄호문자열이 올바르다면 카운트함
                answer++;
            }
        }
        
        return answer;
    }
}