//https://school.programmers.co.kr/learn/courses/30/lessons/12926

class Solution {
	//문자를 i만큼 이동시켜 변환하는 메소드
    public static char func(char c, int i){
    	//공백 문자라면 그대로 리턴함
        if(c==' '){
            return ' ';
        //대문자 알파벳이라면
        }else if(c>='A'&&c<='Z'){
        	//입력받은 문자를 A로 나눈 나머지를 i만큼 이동시키고 26으로 나눈 나머지만큼을 A에서 이동시켜 변환함
            return (char)('A'+(c%'A'+i)%26);
          //입력받은 문자를 a로 나눈 나머지를 i만큼 이동시키고 26으로 나눈 나머지만큼을 a에서 이동시켜 변환함
        }else{
            return (char)('a'+(c%'a'+i)%26);
        }
    }
    public String solution(String s, int n) {
        String answer = "";
        
        //문자열 처리를 쉽게 하기 위하여 문자 배열로 변환함
        char[] arr = s.toCharArray();
        
        //문자를 적절히 변환한 결과를 정답 문자열에 덧붙임
        for(char ch : arr){
            answer += func(ch,n);
        }
        
        return answer;
    }
}