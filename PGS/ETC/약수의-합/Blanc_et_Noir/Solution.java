//https://school.programmers.co.kr/learn/courses/30/lessons/12928
	
class Solution {
    public int solution(int n) {
        int answer = 0;
        
        //정수가 0이라면 약수가 존재하지 않음
        if(n==0){
            return 0;
        //정수가 1이상의 정수라면
        }else{
        	//1부터 n까지의 정수로 나누어서
            for(int i=1; i<=n; i++){
            	//나누어 떨어지면 그것은 약수임
                if(n%i==0){
                    answer+=i;
                }
            }
            return answer;
        }
    }
}