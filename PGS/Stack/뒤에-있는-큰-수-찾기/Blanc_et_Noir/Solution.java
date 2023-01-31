//https://school.programmers.co.kr/learn/courses/30/lessons/154539

import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        Stack<Integer> s = new Stack<Integer>();
        
        int[] answer = new int[numbers.length];
        
        //뒤에서부터 탐색을 시작함
        for(int i=numbers.length-1; i>=0; i--){
            int num = -1;
            
            //스택이 비어있지 않다면
            while(!s.isEmpty()){
            	//스택의 top보다 작다면
                if(s.peek()>numbers[i]){
                	//그것이 자신보다 오른쪽에 있는 큰 값중 가장 가까운 값임
                    num = s.peek();
                    break;
                //스택의 top보다 크거나 같다면
                }else{
                	//그 값을 스택에서 제거함
                    s.pop();
                }
            }
            
            //자신보다 오른쪽에 있는 큰 값중 가장 가까운 값을 배열에 저장함
            answer[i] = num;
            
            //자신을 스택에 추가함
            s.push(numbers[i]);
        }
        
        return answer;
    }
}