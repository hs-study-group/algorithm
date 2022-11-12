//https://school.programmers.co.kr/learn/courses/30/lessons/12913

class Solution {
    public static int max(int[] arr, int idx){
        switch(idx){
            case 0:return Math.max(arr[1],Math.max(arr[2],arr[3]));
            case 1:return Math.max(arr[0],Math.max(arr[2],arr[3]));
            case 2:return Math.max(arr[0],Math.max(arr[1],arr[3]));
            default:return Math.max(arr[0],Math.max(arr[1],arr[2]));
        }
    }
    
    int solution(int[][] land) {
        int answer = 0;
        
        //DP[i][j]가 의미하는 것은, i번째줄에서 j번째를 선택했을때 얻을 수 있는 최대 점수를 의미함
        int[][] dp = new int[land.length][4];
        
        //i번째 줄에서 j번째 값을 선택했을 때의 누적합을 land[i][j]로 초기화 함
        dp[0][0] = land[0][0];
        dp[0][1] = land[0][1];
        dp[0][2] = land[0][2];
        dp[0][3] = land[0][3];
        
        for(int i=1; i<dp.length; i++){
            for(int j=0; j<4; j++){
            	//i번째에서 j번째를 선택한다면 그 누적합은
            	//i-1번째에서 j번째를 선택했을때 누적 합중 가장 큰 값 + j번째 값이 될 것임
                dp[i][j] = land[i][j] + max(dp[i-1],j);
            }
        }

        //마지막 행에 도달했을때의 누적 합이 가장 큰 값이 바로 정답임
        for(int i=0; i<4; i++){
            answer = Math.max(answer,dp[land.length-1][i]);
        }
        
        return answer;
    }
}