//https://school.programmers.co.kr/learn/courses/30/lessons/131701

class Solution {
    public int solution(int[] elements) {
        boolean[] v = new boolean[1000*1000+1];
        int answer = 0;
        
        //dp[i][j]는 i번째 번호부터 j개를 선택했을때의 합을 의미함
        int[][] dp = new int[elements.length][elements.length+1];

        for(int i=0; i<elements.length; i++){
            for(int j=1; j<=elements.length; j++){
            	
            	//i번째 숫자부터 j개를 골랐을때의 합은 i번째 숫자부터 j-1개를 고른 후, i + j번째 숫자를 더한 것임
            	//이때, 원형 수열의 범위를 벗어나지 않도록 모듈로 연산을 수행함
                dp[i][j] = dp[i][j-1] + elements[(i+j)%elements.length];

                //해당 수열의 합이 기존에 계산된 적이 없는 수열의 합이라면
                if(!v[dp[i][j]]){
                	//방문처리하고 정답을 1증가시킴
                    v[dp[i][j]]=true;
                    answer++;
                }
            }
        }
        
        return answer;
    }
}