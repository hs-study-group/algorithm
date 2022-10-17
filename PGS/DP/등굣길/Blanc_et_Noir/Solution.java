//https://school.programmers.co.kr/learn/courses/30/lessons/42898

class Solution {
    public int solution(int m, int n, int[][] puddles) {        
        int[][] map = new int[n][m];
        
        //물에 잠긴 지역의 좌표를 탐색하여 맵에 1로 표시함
        for(int i=0; i<puddles.length; i++){
            map[puddles[i][1]-1][puddles[i][0]-1] = 1;
        }
        
        //dp[n][m]은 n, m 위치에 올 수 있는 모든 경우의 수를 저장함
        int[][] dp = new int[n][m];
        
        //맵의 첫째 열은 시작지점에서 웅덩이가 나타나기 직전까지는 1가지 경우의 수로 도착가능
        for(int i=0; i<map.length; i++){
        	//웅덩이가 아니라면
            if(map[i][0]==0){
            	//해당 행에는 무조건 1가지 경우의 수로 도착 가능
                dp[i][0] = 1;
            //웅덩이가 나타난 경우, 웅덩이부터 아래 행들은 절대로
            //도달할 수 없는 지역이므로 0으로 내버려 둠
            }else{
                break;
            }
        }
        
      //맵의 첫째 행은 시작지점에서 웅덩이가 나타나기 직전까지는 1가지 경우의 수로 도착가능
        for(int i=0; i<map[0].length; i++){
        	//웅덩이가 아니라면
            if(map[0][i]==0){
            	//해당 열에는 무조건 1가지 경우의 수로 도착 가능
                dp[0][i] = 1;
            //웅덩이가 나타난 경우, 웅덩이부터 오른쪽 행들은 절대로
            //도달할 수 없는 지역이므로 0으로 내버려 둠
            }else{
                break;
            }
        }
        
        
        for(int i=1; i<dp.length; i++){
            for(int j=1; j<dp[0].length; j++){
            	//첫째행과 첫째열을 제외한 웅덩이가 아닌 모든 n, m위치는
                if(map[i][j]!=1){
                	//n, m위치에 도달할 수 있는 총 경우의 수는
                	//n-1, m위치까지 올 수 있는 경우의 수와 n, m-1위치까지 올 수 있는 경우의 수를 모두 합친 값임
                    dp[i][j] = (dp[i-1][j] + dp[i][j-1])%1000000007;
                }
                
            }
        }
        
        //도착위치에 도달할 수 있는 총 경우의 수를 리턴함
        return dp[n-1][m-1];
    }
}