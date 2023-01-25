//https://school.programmers.co.kr/learn/courses/30/lessons/92344

class Solution {
	//누적합할 가중치를 누적합 배열에 누적시키는 메소드
    public void add(int[][] temp,int r1, int c1, int r2, int c2, int v){
        temp[r1][c1] += v;
        temp[r1][c2+1] -= v;
        temp[r2+1][c1] -= v;
        temp[r2+1][c2+1] += v;
    }
    
    //누적합을 가로로 수행하는 메소드
    public void processHorizontally(int[][] temp){
        for(int i=0; i<temp.length; i++){
            for(int j=1; j<temp[0].length; j++){
                temp[i][j] += temp[i][j-1];
            }
        }
    }
    
    //누적합을 세로로 수행하는 메소드
    public void processVertically(int[][] temp){
        for(int j=0; j<temp[0].length; j++){
            for(int i=1; i<temp.length; i++){
                temp[i][j] += temp[i-1][j];
            }
        }
    }
    
    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        
        //가중치를 누적해둘 누적 배열 선언
        int[][] temp = new int[board.length+1][board[0].length+1];
        
        //스킬을 차례대로 탐색함
        for(int i=0; i<skill.length; i++){
        	//가중치v가 회복스킬이라면 +, 공격스킬이라면 -값을 갖도록 함
            int v = (skill[i][0]==1?-skill[i][5]:skill[i][5]);
            
            //가중치를 누적함
            add(temp,skill[i][1],skill[i][2],skill[i][3],skill[i][4],v);
        }
        
        //2차원 배열의 누적합을 구함
        processHorizontally(temp);
        processVertically(temp);
        
        //누적합을 구한 누적합 배열과 원본 배열을 합함
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
            	//누적된 결과를 실제 원본 배열에 반영했을때 0보다 큰 값을 갖는다면 파괴되지 않은 건물임
                if(board[i][j]+temp[i][j]>0){
                    answer++;
                }
            }
        }
        
        return answer;
    }
}