//https://school.programmers.co.kr/learn/courses/30/lessons/181187

class Solution {
    public double get(long x, long r){
        return Math.sqrt((r+x)*(r-x));
    }
    
    public long solution(int r1, int r2) {
        long answer = 0;
        
        //더 큰 원의 안에 포함되는 정수쌍의 개수를 구함
        //테두리에 존재하는 정수 쌍도 카운트함
        for(long i=-r2;i<=r2;i++){
            double y = get(i,r2);
            answer += ((long)y)*2+1;
        }
        
        //더 작은 원의 안에 포함되는 정수 쌍의 개수를 구하되
        //테두리에 존재하는 정수 쌍은 카운트하지 않음
        for(long i=-r1+1;i<=r1-1;i++){
            double y = get(i,r1);
            
            if(y==(long)y){
                answer-=((long)y-1)*2+1;
            }else{
                answer-=((long)y)*2+1;
            }
        }
        
        //큰 원의 테두리 포함 정수쌍의 개수 - 작은 원의 테두리 미포함 정수쌍의 개수를 리턴함
        return answer;
    }
}