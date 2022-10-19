//https://school.programmers.co.kr/learn/courses/30/lessons/43105

class Solution {
    public int solution(int[][] triangle) {
        int answer = Integer.MIN_VALUE;
        
        //DP 배열은 DP[i][j]로 나타내며, i는 노드의 레벨, j는 선택한 노드 번호를 가리키며
        //dp[i][j]의 값은 i레벨에서 j노드를 선택했을때 여태까지 선택한 값들의 합의 최대값을 의미함
        int[][] dp = new int[triangle.length][triangle.length];
        
        //0레벨에서 0노드를 선택했을때는 트리의 루트노드를 선택할 수 밖에 없음
        dp[0][0] = triangle[0][0];
        
        for(int i=1; i<triangle.length; i++){
            for(int j=0;j <=i; j++){
            	//만약 j노드가 각 레벨의 맨 왼쪽노드라면
                if(j==0){
                	//바로 이전 레벨까지 탐색했을때의 최대값 + 이번 레벨의 맨 왼쪽노드 값으로 초기화 함
                    dp[i][j] = dp[i-1][j] + triangle[i][j];
                //만약 j노드가 각 레벨의 맨 왼쪽노드가 아니라면
                }else{
                	//바로 이전 레벨까지 탐색했을때의 최대값 2개중 더 큰값에 이번 레벨의 j노드가 가진 값의 합으로 초기화 함
                    dp[i][j] = Math.max(dp[i-1][j-1],dp[i-1][j]) + triangle[i][j];
                }
                //만약 그 합이 기존 최대값보다 크다면, 그것으로 갱신함
                answer = Math.max(dp[i][j], answer);
            }
        }
        
        return answer;
    }
}