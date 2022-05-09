//https://programmers.co.kr/learn/courses/30/lessons/64061

import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        
        //board배열에서 인형들을 각 열마다 저장할 스택 선언
        Stack<Integer>[] s = new Stack[board.length];
      
        //뽑은 인형을 저장할 스택 선언
        Stack<Integer> r = new Stack<Integer>();
        
        //스택 배열 초기화
        for(int i=0; i<s.length; i++){
            s[i] = new Stack<Integer>();
        }
        
        //왼쪽에서 오른쪽 열을 탐색하는데
        //행은 아래에서 위로 탐색하며 해당 스택열에 추가함
        for(int i=0; i<board[0].length; i++){
            for(int j=board.length-1; j>=0; j--){
                if(board[j][i]!=0){
                    s[i].push(board[j][i]); 
                }
            }
        }
        
        for(int i=0; i<moves.length; i++){
            //해당 스택열에 인형이 존재할때만 인형을 정상적으로 뽑을 수 있음
            if(!s[moves[i]-1].isEmpty()){
              
                //여지껏 뽑은 인형들을 모아둔 스택 r에 대해서
                //스택 r과 방금 뽑은 인형이 같은 인형이면
                //굳이 뽑은 인형을 결과 스택에 추가할 필요 없이
                //즉시 뽑은 것으로 처리함
                if(!r.isEmpty()&&r.peek()==s[moves[i]-1].peek()){
                    s[moves[i]-1].pop();
                    r.pop();
                    answer+=2;
                
                //아직 아무것도 뽑지 않았다면 그냥 결과 스택에 쌓아둠
                }else{
                    r.push(s[moves[i]-1].pop());
                }
            }
        }
        return answer;
    }
}