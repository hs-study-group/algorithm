//https://school.programmers.co.kr/learn/courses/30/lessons/142086

import java.util.*;

class Solution {
    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        
        //어떤 문자열에 대하여 가장 최근에 탐색했을때의 인덱스를 저장할 해시맵 선언
        HashMap<Character, Integer> index = new HashMap<Character, Integer>();
        
        //문자열을 문자 배열로 변환
        char[] arr = s.toCharArray();
        
        for(int i=0; i<arr.length; i++){
        	//어떤 문자의 최근 인덱스 정보가 저장되어 있다면
            if(index.containsKey(arr[i])){
            	//현재 인덱스와 그때의 인덱스의 차이를 정답으로 저장함
                answer[i] = i-index.get(arr[i]);
            //아니라면
            }else{
            	//-1을 저장함
                answer[i] = -1;
            }
            
            //해당 문자의 현재 인덱스를 최근 인덱스로 저장함
            index.put(arr[i],i);
        }
        
        return answer;
    }
}