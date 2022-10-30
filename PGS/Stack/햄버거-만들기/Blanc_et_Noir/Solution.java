//https://school.programmers.co.kr/learn/courses/30/lessons/133502

import java.util.*;

class Solution {
    public int solution(int[] ingredient) {
        int answer = 0;
        
        //햄버거 재료를 쌓아놓을 스택 선언
        Stack<Integer> s = new Stack<Integer>();
        
        for(int i=0; i<ingredient.length; i++){
        	//재료를 스택에 추가함
            s.push(ingredient[i]);
            
            //해당 재료가 빵이라면
            if(s.peek()==1){
            	//스택에 4개 이상의 재료가 쌓여있다면
                if(s.size()>=4){
                    StringBuilder sb = new StringBuilder();
                    
                    //스택에서 차례로 4개의 재료를 꺼냄
                    for(int j=0; j<4; j++){
                        sb.append(s.pop());
                    }
                    
                    //순서대로 꺼낸 재료가 햄버거의 모양을 하고 있다면
                    //(햄버거가 180도 뒤집힌 모양이라면)
                    if(sb.toString().equals("1321")){
                    	//햄버거 하나를 완성시킨 것임
                        answer++;
                    //햄버거 모양이 아니라면
                    }else{
                    	//해당 재료를 다시 그대로 스택에 쌓음
                    	//즉, 원 상태로 복구함
                        String[] str = sb.toString().split("");
                        for(int j=3; j>=0; j--){
                            s.push(Integer.parseInt(str[j]));
                        }
                    }
                }
            }
        }
        
        return answer;
    }
}