//https://school.programmers.co.kr/learn/courses/30/lessons/147355

class Solution {
    public int solution(String t, String p) {
        int answer = 0;
        
        //비교해야할 문자열의 길이
        final int SIZE = p.length();
        
        //비교해야할 문자열을 정수로 변환
        final long NUM = Long.parseLong(p);

        for(int i=0; i<t.length()-SIZE+1; i++){
        	//부분문자열을 추출하고, 그것이 NUM보다 작거나 같다면 카운트함
            if(Long.parseLong(t.substring(i,i+SIZE))<=NUM){
                answer++;
            }
        }
        
        return answer;
    }
}