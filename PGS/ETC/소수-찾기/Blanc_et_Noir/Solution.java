//https://school.programmers.co.kr/learn/courses/30/lessons/12921?language=java

class Solution {
    public int solution(int n) {
        int answer = 0;
        
        //에라토스테네스의 체의 역할을 수행할 배열 선언
        boolean[] v = new boolean[n+1];
        
        //0과 1은 소수가 아니므로 굳이 탐색하지 않음
        for(int i=2; i<v.length; i++){
        	//만약 해당 숫자가 기존에 소수인지 아닌지 확인한 적이 없다면
            if(!v[i]){
            	//해당 숫자는 소수이므로 카운트함
                answer++;
                
                //해당 숫자를 포함하여 그 숫자의 배수는 모두 소수가 아니므로 방문처리함
                for(int j=i;j<v.length; j=j+i){
                    v[j] = true;
                }
            }
        }
        
        //소수의 개수를 리턴함
        return answer;
    }
}