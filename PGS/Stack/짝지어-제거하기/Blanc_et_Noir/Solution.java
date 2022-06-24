import java.util.*;

class Solution{
    public int solution(String s){
    	//제거되지않은 가장 최근에 탐색한 두 문자가 일치하는지 판단하기 위한 스택 선언
        Stack<Character> stack = new Stack<Character>();
        
        //문자열을 문자배열로 변환함
        char[] arr = s.toCharArray();
        
        //문자를 하나씩 탐색함
        for(int i=0; i<arr.length; i++){
        	//스택이 비어있다면 무조건 스택에 추가
            if(stack.isEmpty()){
                stack.push(arr[i]);
            //스택이 비어있지 않다면
            }else{
            	//가장 최근에 추가한 문자와 현재 문자가 동일하다면
                if(stack.peek()==arr[i]){
                	//스택에서 가장 최근 추가한 문자를 제거함
                    stack.pop();
                //동일하지 않다면 해당 문자를 추가함
                }else{
                    stack.push(arr[i]);
                }
            }
        }
        
        //모든 탐색후 스택에 문자가 남아있지 않다면
        //이는 모든 문자가 짝이 맞다는 뜻임
        if(stack.isEmpty()){
            return 1;
        //모든 탐색후 스택에 문자가 남아있으면
        //짝이 맞지않는 문자가 존재하다는 뜻임
        }else{
            return 0;
        }
    }
}