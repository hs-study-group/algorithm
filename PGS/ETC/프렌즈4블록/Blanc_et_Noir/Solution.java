class Solution {
	
	//지울 수 있는 블록이 존재하는지 찾고, 존재한다면 블록을 지우고 지운 블록 수를 리턴하는 메소드
    public static int find(char[][] map){
    	//지워야할 블록의 위치를 저장하는 임시 2차원 배열
        boolean[][] temp = new boolean[map.length][map[0].length];
        int cnt = 0;
        
        for(int i=0; i<map.length-1; i++){
            for(int j=0; j<map[i].length-1; j++){
            	//빈공간이 아닌 블록이라면
                if(map[i][j]>='A'&&map[i][j]<='Z'){
                	//지울 수 있는 상태일때 그 위치 정보를 임시로 저장함
                    if(map[i][j]==map[i+1][j]&&map[i][j]==map[i][j+1]&&map[i][j]==map[i+1][j+1]){
                        temp[i][j] = true;
                        temp[i+1][j] = true;
                        temp[i][j+1] = true;
                        temp[i+1][j+1] = true;
                    }
                }
            }
        }
        
        //지워야할 블록들에 대해 실제 배열에 해당 정보를 반영함
        for(int i=0; i<temp.length; i++){
            for(int j=0; j<temp[i].length; j++){
                if(temp[i][j]){
                    map[i][j]='.';
                    //지운 횟수를 기록함
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    //블록을 지우고 난 이후에 생긴 공백을 채우는 메소드
    public static void arrange(char[][] map){
    	//가장 좌측의 열, 가장 아래쪽의 행부터 탐색함
        for(int j=0; j<map[0].length; j++){
        	//공백의 수를 저장할 변수
            int cnt = 0;
            for(int i=map.length-1; i>=0; i--){
            	//공백이라면 공백의 수를 증가시킴
                if(map[i][j]=='.'){
                    cnt++;
                //공백이 아니라면 이는 블록이므로 해당 블록을 공백만큼 이동시킴
                //단, 공백이 없었다면 굳이 이동시키지 않음
                }else if (cnt!=0){
                    map[i+cnt][j] = map[i][j];
                    map[i][j] = '.';
                }
            }
        }
    }
    
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        char[][] map = new char[m][n];
        
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length(); j++){
                map[i][j] = board[i].charAt(j);
            }
        }

        int cnt;
        
        //지울 수 있는 블록의 수가 0일때까지 반복
        while((cnt = find(map))!=0){
            answer += cnt;
            //블록을 지우고 나서 재정렬함
            arrange(map);
        }
        
        return answer;
    }
}