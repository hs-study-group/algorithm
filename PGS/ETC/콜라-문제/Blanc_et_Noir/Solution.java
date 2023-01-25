//https://school.programmers.co.kr/learn/courses/30/lessons/132267

class Solution {
    public int solution(int a, int b, int n) {
        int answer = 0;
        
        int r = n;
        
        //빈 콜라병이 a개 이상이라면
        while(r>=a){
        	//빈 콜라병 a개당 b개의 새로운 콜라를 얻을 수 있음
            answer += (r/a)*b;
            
            //빈 콜라병 a개당 얻게되는 b개의 새로운 콜라와
            //빈 콜라명 a개씩을 교환하고 남은 빈병을 합한것이 새로운 빈병의 개수가 됨
            r=r/a*b+r%a;
        }
        
        return answer;
    }
}