//https://school.programmers.co.kr/learn/courses/30/lessons/1832

class Solution {
    
    public int solution(int m, int n, int[][] cityMap) {
        int answer = 0;
        
        //DP[ i ][ j ][ k ] = c 는 아래와 같은 의미를 가짐
        //y좌표 i, x좌표 j에 도달하는데 k가 0이면 위에서 아래로, 1이면 좌에서 우로 해당 위치에 c가지 경우의 수로 도달할 수 있음을 의미함
        int[][][] DP = new int[m][n][2];
        final int MOD = 20170805;
        
        //0,0위치에 위에서 아래, 좌에서 우 방향으로 도달한 경우의 수를 편의상 1로 초기화함
        DP[0][0][0] = 1;
        DP[0][0][1] = 1;
        
        //맨 위쪽 행은 경로가 단 한가지 밖에 없으므로 통행 불가 표지판을 만날때까지 그 경우의 수를 1로 초기화 함
        for(int i=0; i<DP[0].length; i++){
            if(cityMap[0][i]==1){
                break;
            }else{
                DP[0][i][1] = 1;
            }
        }
        
        //맨 완쪽 열은 경로가 단 한가지 밖에 없으므로 통행 불가 표지판을 만날때까지 그 경우의 수를 1로 초기화 함
        for(int i=0; i<DP.length; i++){
            if(cityMap[i][0]==1){
                break;
            }else{
                DP[i][0][0] = 1;
            }
        }
        
        for(int i=1; i<DP.length; i++){
            for(int j=1; j<DP[0].length; j++){
            	//이동하고자 하는 위치가 방문 가능하긴 하다면
                if(cityMap[i][j]!=1){
                	//해당 위치를 기준으로 위쪽 위치가 일방통행길이었다면
                    if(cityMap[i-1][j]==2){
                    	//위에서 아래방향으로 위쪽 위치에 도달했던 경우의 수가
                    	//위에서 아래방향으로 현재 위치에 도달하는 경우의 수랑 동일함
                        DP[i][j][0] = DP[i-1][j][0];
                    //일방통행이 아니었다면
                    }else{
                    	//위쪽 위치에서 현재 위치로 위에서 아래 방향으로 이동하여 도달하는 경우의 수는
                    	//위쪽에서 아래쪽 방향으로 위쪽 위치로 이동한 경우의 수 + 왼쪽에서 오른쪽 방향으로 위쪽 위치로 이동한 경우의 수가 됨 
                        DP[i][j][0] = (DP[i-1][j][0] + DP[i-1][j][1])%MOD;
                    }
                    
                    //해당 위치를 기준으로 왼쪽 위치가 일방통행길이었다면
                    if(cityMap[i][j-1]==2){
                    	//좌에서 우방향으로 왼쪽 위치에 도달했던 경우의 수가
                    	//좌에서 우방향으로 현재 위치에 도달하는 경우의 수랑 동일함
                        DP[i][j][1] = DP[i][j-1][1];
                    //일방통행이 아니었다면
                    }else{
                    	//왼쪽 위치에서 현재 위치로 왼쪽에서 오른쪽 방향으로 이동하여 도달하는 경우의 수는
                    	//왼쪽에서 오른쪽 방향으로 왼쪽 위치로 이동한 경우의 수 + 위쪽에서 아래쪽 방향으로 왼쪽 위치로 이동한 경우의 수가 됨 
                        DP[i][j][1] = (DP[i][j-1][0] + DP[i][j-1][1])%MOD;
                    }
                //이동하고자 하는 위치가 방문이 불가한 경우
                }else{
                	//해당 위치에 위에서 아래, 좌에서 우로 이동하여 도달하는 경우의 수가 없다고 처리함
                    DP[i][j][0] = 0;
                    DP[i][j][1] = 0;
                }
            }
        }
        
        //맨 오른쪽 하단 위치에 도달하는 경우의 수는
        //위에서 아래 방향으로 해당 위치에 도달하는 경우의 수 + 왼쪽에서 오른쪽 방향으로 해당 위치에 도달하는 경우의 수임
        return (DP[m-1][n-1][0]+DP[m-1][n-1][1])%MOD;
    }
}