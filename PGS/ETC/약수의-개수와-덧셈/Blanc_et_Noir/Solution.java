//https://school.programmers.co.kr/learn/courses/30/lessons/77884

class Solution {
	//해당 숫자의 약수 개수가 짝수면 true, 홀수면 false를 리턴하는 메소드
    boolean check(int num){
    	//1은 약수의 개수가 홀수이므로 false를 리턴
        if(num==1){
            return false;
        }
        
        //만약 자기 자신의 제곱근이 약수인 경우에는
        //즉, 자기 자신의 제곱근이 정수로 나누어 떨어지는 경우에는
        if(Math.sqrt(num)==(int)Math.sqrt(num)){
        	//약수의 개수는 반드시 홀수개이므로 false를 리턴
            return false;
        }
        
        //그 외에 경우 모두 약수의 개수가 짝수이므로 true를 리턴
        return true;
    }
    
    public int solution(int left, int right) {
        int answer = 0;
        
        //left와 right사이의 모든 정수에 대하여
        for(int i=left; i<=right; i++){
        	//약수의 개수가 짝수개면 더함
            if(check(i)){
                answer+=i;
            //약수의 개수가 홀수개면 뺌
            }else{
                answer-=i;
            }
        }
        
        return answer;
    }
}