//https://school.programmers.co.kr/learn/courses/30/lessons/70129
import java.util.*;

class Solution {
	//주어진 문자열에서 0을 제거하고, 1의 개수를 리턴하는 메소드
	//0을 진짜 제거하는 대신, 1의 수를 세는 것으로 처리함
    public int preProcess(String str){
    	//String대신 처리하기 쉬운 char 배열로 변환함
        char[] arr = str.toCharArray();
        int cnt = 0;
        
        //문자열을 탐색하면서
        for(int i=0; i<arr.length; i++){
            //1이라면 카운트함
            if(arr[i]=='1'){
                cnt++;
            }
        }
        
        //1의 개수를 리턴함
        return cnt;
    }
    
    //주어진 정수를 2진 문자열로 변환하는 메소드
    public String toBinaryString(int n){
    	//2진 변환시의 값을 역순으로 배치하기 위해 Stack 자료구조 사용
        Stack<Integer> s = new Stack<Integer>();
        
        //String 대신 문자열 처리가 빠른 StringBuilder 사용
        StringBuilder sb = new StringBuilder();
        
        //n이 0이상이라면
        while(n>0){
        	//2로 나눈 나머지를 스택에 저장함
            s.push(n%2);
            
            //n을 실제로 2로 나눔
            n = n/2;            
        }
        
        //Stack에 저장된 변환 결과를 역순으로 정렬함
        while(!s.isEmpty()){
            sb.append(s.pop()+"");
        }
        
        //2진 변환된 문자열을 반환함
        return sb.toString();
    }
    
    public int[] solution(String s) {
        int[] answer = {};
        
        //문자열 변환 횟수를 기록할 변수
        int cnt = 0;
        
        //0을 제거한 횟수를 기록할 변수
        int sum = 0;
        
        while(!s.equals("1")){
        	//변환 이전 문자열의 길이를 저장함
            int prev = s.length();
            
            //변환 이후 문자열의 길이를 저장함
            int next = preProcess(s);
            
            //두 값의 차이가 바로 제거된 0의 개수임
            sum += (prev - next);
            
            //변환을 진행한 횟수를 카운트함
            cnt++;
            
            //변환 이후의 문자열의 길이값을 2진 문자열로 변환함
            s = toBinaryString(next);
        }
        
        return new int[]{cnt,sum};
    }
}