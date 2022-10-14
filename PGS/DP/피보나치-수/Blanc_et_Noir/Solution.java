//https://school.programmers.co.kr/learn/courses/30/lessons/12945

class Solution {
    public int solution(int n) {
        int[] dp = new int[n+2];
        
        //1번째 피보나치 수는 1임
        dp[1] = 1;
        
        //2번째 피보나치 수는 1임
        dp[2] = 1;
        
        for(int i=3; i<dp.length; i++){
        	//N번째 피보나치 수는 N-2번째 피보나치 수와 N-1번째 피보나치 수의 합임
            dp[i] = (dp[i-1] + dp[i-2])%1234567;
        }
        
        //N번째 피보나치 수를 반환함
        return dp[n];
    }
}