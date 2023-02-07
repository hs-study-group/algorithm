//https://school.programmers.co.kr/learn/courses/30/lessons/12943

class Solution {
    public int solution(int num) {
        int cnt = 0;
        
        //계산중 범위가 int를 벗어날 수 있으므로 long으로 관리해야함
        long temp = num;
        
        //1이 될 때까지 반복함
        while(temp!=1){
        	//변환 횟수를 기록함
            cnt++;
            
            //짝수라면 2로 나눔
            if(temp%2==0){
                temp=temp/2;
            //홀수라면 3을 곱하고 1을 더함
            }else{
                temp=temp*3+1;
            }
        }
        
        //변환 횟수가 500초과면 -1을 리턴해야함
        if(cnt>500){
            cnt=-1;
        }
        
        return cnt;
    }
}