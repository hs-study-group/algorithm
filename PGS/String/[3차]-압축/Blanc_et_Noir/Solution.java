//https://school.programmers.co.kr/learn/courses/30/lessons/17684
import java.util.*;

class Solution {
    public int[] solution(String msg) {
        int[] answer = {};

        //사전의 역할을 수행할 해시맵 선언
        HashMap<String, Integer> hm = new HashMap<String,Integer>();
        
        //압축 결과를 배열로 변환하기 위해 임시로 압축 결과를 저장할 큐 선언
        Queue<Integer> q = new LinkedList<Integer>();

        //사전 초기화
        for(int i=0; i<26; i++){
            hm.put(Character.toString('A'+i),i+1);
        }

        //사전에 새로 추가될때의 인덱스 값
        int cnt = 27;
        
        //문자열 처리를 쉽게 하기위해 문자열 마지막에 의미없는 문자를 덧붙임
        msg += "*";
        String temp = msg.substring(0,1);
        char[] arr = msg.toCharArray();

        for(int i=0; i<msg.length(); i++){
            for(int j=i; j<msg.length(); j++){
            	//사전에 없는 최단 문자열
                String next = msg.substring(i,j+1);
                
                //사전에 있는 최장 문자열
                String prev = msg.substring(i,j);
                
                //해당 문자열이 사전에 있으면 다시 반복함
                if(hm.containsKey(next)){
                    continue;
                }else{
                	//해당 문자열이 사전에 없으므로 사전에 추가함
                    hm.put(next,cnt++);
                    
                    //사전에 있는 최장 문자열이 빈 문자열이 아닐때만 
                    if(!prev.equals("")){
                    	//해당 압축 결과를 큐에 추가함
                        q.add(hm.get(prev));
                    }
                    
                    //탐색할 다음 문자는 최장문자열의 길이만큼 이동해야하는데,
                    //for문의 증감식을 고려하여 미리 1을 뺌
                    i += prev.length()-1;
                    break;
                }
            }
        }

        //큐에 저장된 압축 결과를 배열로 변환함
        cnt = 0;
        answer = new int[q.size()];
        while(!q.isEmpty()){
            answer[cnt++] = q.poll();
        }

        return answer;
    }
}