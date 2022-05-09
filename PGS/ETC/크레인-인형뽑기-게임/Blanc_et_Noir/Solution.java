//https://programmers.co.kr/learn/courses/30/lessons/64061

import java.util.*;

class Solution {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        
        //board�迭���� �������� �� ������ ������ ���� ����
        Stack<Integer>[] s = new Stack[board.length];
      
        //���� ������ ������ ���� ����
        Stack<Integer> r = new Stack<Integer>();
        
        //���� �迭 �ʱ�ȭ
        for(int i=0; i<s.length; i++){
            s[i] = new Stack<Integer>();
        }
        
        //���ʿ��� ������ ���� Ž���ϴµ�
        //���� �Ʒ����� ���� Ž���ϸ� �ش� ���ÿ��� �߰���
        for(int i=0; i<board[0].length; i++){
            for(int j=board.length-1; j>=0; j--){
                if(board[j][i]!=0){
                    s[i].push(board[j][i]); 
                }
            }
        }
        
        for(int i=0; i<moves.length; i++){
            //�ش� ���ÿ��� ������ �����Ҷ��� ������ ���������� ���� �� ����
            if(!s[moves[i]-1].isEmpty()){
              
                //������ ���� �������� ��Ƶ� ���� r�� ���ؼ�
                //���� r�� ��� ���� ������ ���� �����̸�
                //���� ���� ������ ��� ���ÿ� �߰��� �ʿ� ����
                //��� ���� ������ ó����
                if(!r.isEmpty()&&r.peek()==s[moves[i]-1].peek()){
                    s[moves[i]-1].pop();
                    r.pop();
                    answer+=2;
                
                //���� �ƹ��͵� ���� �ʾҴٸ� �׳� ��� ���ÿ� �׾Ƶ�
                }else{
                    r.push(s[moves[i]-1].pop());
                }
            }
        }
        return answer;
    }
}