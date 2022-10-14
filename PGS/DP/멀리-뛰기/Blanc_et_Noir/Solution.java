//https://school.programmers.co.kr/learn/courses/30/lessons/12914

class Solution {
    public long solution(int n) {
        long[] dp = new long[n+2];
        
        //1번째 칸에 도착할 수 있는 방법은 단 1칸을 뛰는 방법 뿐임 
        dp[1] = 1;
        //2번째 칸에 도착할 수 있는 방법은 1번째 칸에서 1칸을 뛰거나, 시작지점에서 2칸을 뛰는 방법 뿐임
        dp[2] = 2;
        
        
        for(int i=3; i<dp.length; i++){
        	//N번째 칸에 도달할 수 있는 방법은, N-2번째 칸에서 2칸을 뛰거나, N-1번째 칸에서 1칸을 뛰는 방법뿐임
            dp[i] = (dp[i-1] + dp[i-2])%1234567;
        }
        
        //칸이 N개일때 도달할 수 있는 방법의 수를 리턴함
        return dp[n];
    }
}