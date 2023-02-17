//https://school.programmers.co.kr/learn/courses/30/lessons/68645

class Solution {
    public int[] solution(int n) {
        int[] answer = new int[n*(n+1)/2];
        
        //다음 좌표를 계산하기 위한 벡터 배열 선언
        int[][] dist = new int[][]{{1,0},{0,1},{-1,-1}};

        //삼각 달팽이 배열을 구현할 배열
        int[][] arr = new int[n][n];
        
        //현재 진행 방향
        int d = 0;
        
        //현재 배열에 저장할 숫자
        int num = 1;
        
        //현재 y, x좌표
        int y = -1, x = 0;

        //숫자를 모두 채울때까지 반복함
        while(num<=(n*(n+1))/2){
        	//현재 진행방향을 고려하여 다음에 채워야할 y, x좌표를 구함
            int ny = y + dist[d][0];
            int nx = x + dist[d][1];
            
            //만약 맵을 벗어나지 않는다면
            if(ny>=0&&ny<n&&nx>=0&&nx<n){
            	//해당 위치에 아직 숫자가 채워지지 않았다면
                if(arr[ny][nx]==0){
                	//현재 좌표를 그것으로 갱신함
                    y = ny;
                    x = nx;
                    
                    //그 좌표에 숫자를 저장하고, 숫자를 1증가시킴
                    arr[y][x] = num++;
                //이미 그곳에 다른 숫자가 채워져있다면
                }else{
                	//진행 방향을 바꿈
                    d = (d+1)%3;
                }
            //만약 맵을 벗어난다면
            }else{
            	//진행 방향을 바꿈
                d = (d+1)%3;
            }
        }
        
        int idx = 0;
        
        //삼각 달팽이 배열에 저장된 값들을 순서대로 1차원 배열에 저장함
        for(int i=0; i<n; i++){
            for(int j=0;j<n; j++){
                if(arr[i][j]!=0){
                    answer[idx++] = arr[i][j];
                }
            }
        }
        
        return answer;
    }
}