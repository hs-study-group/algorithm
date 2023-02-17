//https://school.programmers.co.kr/learn/courses/30/lessons/12948

class Solution {
    public String solution(String phone_number) {
        String answer = "";
        
        //핸드폰 번호 처리를 쉽게 하기 위해 문자 배열로 변환
        char[] arr = phone_number.toCharArray();
        
        //맨 뒤의 4자리를 제외한 모든 숫자를 *로 치환함
        for(int i=0; i<arr.length-4; i++){
            answer += "*";
        }
        
        //맨 마지막 4자리는 그대로 출력함
        for(int i=arr.length-4; i<arr.length; i++){
            answer += arr[i]+"";
        }
        
        return answer;
    }
}