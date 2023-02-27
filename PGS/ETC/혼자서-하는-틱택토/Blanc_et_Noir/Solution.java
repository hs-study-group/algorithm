//https://school.programmers.co.kr/learn/courses/30/lessons/160585

class Solution {
	//O 또는 X가 3개가 연속으로 이어져서 게임이 끝났는지 아닌지 판단하는 메소드
    public boolean isFinished(char[][] map, char mark){
        
    	//가로로 3개의 O 또는 X가 연속되어 있는지 확인
        for(int i=0; i<map.length; i++){
            boolean flag = true;
            for(int j=0; j<map[0].length; j++){
                if(map[j][i]!=mark){
                    flag = false;
                }
            }
            
            if(flag){
                return true;
            }
        }
        
        //세로로 3개의 O 또는 X가 연속되어 있는지 확인
        for(int i=0; i<map.length; i++){
            boolean flag = true;
            for(int j=0; j<map[0].length; j++){
                if(map[i][j]!=mark){
                    flag = false;
                }
            }
            
            if(flag){
                return true;
            }
        }
        
        //좌상단에서 우하단으로 이어지는 대각선 방향으로 O 또는 X가 연속되어 있는지 확인
        if(map[0][0]==mark&&map[0][0]==map[1][1]&&map[1][1]==map[2][2]){
            return true;
        }
        
        //우상단에서 좌하단으로 이어지는 대각선 방향으로 O 또는 X가 연속되어 있는지 확인
        if(map[0][2]==mark&&map[0][2]==map[1][1]&&map[1][1]==map[2][0]){
            return true;
        }
        
        //게임이 끝나지 않은 것으로 판단
        return false;
    }
    
    public int solution(String[] board) {
        //각각의 인덱스에는 O와 X의 개수를 카운트하고 그 값이 저장됨
        int[] arr = new int[2];
        
        //문자열로 주어진 맵의 상태를 문자 배열로 변환하여 저장
        char[][] map = new char[3][3];
        
        //주어진 문자열을 문자 배열로 변환함
        for(int i=0; i<3; i++){
            map[i] = board[i].toCharArray();
        }
        
        //맵을 탐색하며 O와 X의 개수를 카운트함
        for(int i=0; i<map.length; i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]=='O'){
                    arr[0]++;
                }else if(map[i][j]=='X'){
                    arr[1]++;
                }
            }
        }
        
        //만약 둘 다 3번 연속되어 게임이 끝난 경우
        if(isFinished(map,'O')&&isFinished(map,'X')){
        	//무조건 게임 과정이 잘못 되었음
            return 0;
        }
        
        //O가 이겼으나 O의 개수가 X의 개수보다 1개 더 많지는 않은 경우
        if(isFinished(map,'O')&&arr[0]!=arr[1]+1){
        	//무조건 게임 과정이 잘못 되었음
            return 0;
        }
        
        //X가 이겼으나 X의 개수가 O의 개수와 동일하지 않은 경우
        if(isFinished(map,'X')&&arr[0]!=arr[1]){
        	//무조건 게임 과정이 잘못 되었음
            return 0;
        }
        
        //게임이 끝나지 않았고 O와 X의 개수를 비교해보고 동일하지 않거나, O가 1개 더 많은 경우가 아니라면
        if(arr[0]!=arr[1]&&arr[0]!=arr[1]+1){
        	//무조건 게임 과정이 잘못 되었음
            return 0;
        }
        
        //게임 과정이 올바름
        return 1;
    }
}