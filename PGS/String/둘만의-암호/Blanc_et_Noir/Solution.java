//https://school.programmers.co.kr/learn/courses/30/lessons/155652

import java.util.*;

class Solution {
	//해당 문자가 skip에 해당하는 문자인지 아닌지 판단하는 메소드
    public boolean check(HashMap<Character,Boolean> hm, char ch){
    	//만약 skip에 해당하는 문자라면
        if(hm.containsKey(ch)){
        	//false를 리턴함
            return false;
        //만약 skip에 해당하는 문자가 아니라면
        }else{
        	//true를 리턴함
            return true;
        }
    }
    
    public String solution(String s, String skip, int index) {
        StringBuilder sb = new StringBuilder();

        //skip 문자열을 문자 배열로 변환
        char[] arr1 = skip.toCharArray();
        
        //s 문자열을 문자 배열로 변환
        char[] arr2 = s.toCharArray();
        
        //skip에 해당하는 문자들을 저장할 해시맵
        HashMap<Character,Boolean> hm1 = new HashMap<Character,Boolean>();
        
        //문자열 s에 포함되는 문자들의 인덱스를 저장할 해시맵
        HashMap<Character,Integer> hm2 = new HashMap<Character,Integer>();
        
        //skip에 포함된 모든 문자들을 해시맵에 추가함
        for(char ch : arr1){
            hm1.put(ch,true);
        }
        
        //문자열 s에 포함된 문자들을 알파벳순으로 리스트에 담음 
        ArrayList<Character> list = new ArrayList<Character>();
        
        for(int i=0; i<26; i++){
            char ch = (char)('a'+i);
            
            //만약 skip에 포함된 문자열이 아니라면
            if(check(hm1,ch)){
            	//알파벳 순으로 인덱스를 부여하여 그 인덱스를 해시맵에 저장함
                hm2.put(ch,list.size());
                
                //리스트에 알파벳순으로 저장함
                list.add(ch);
            }
        }

        for(int i=0; i<arr2.length; i++){
            char ch = arr2[i];
            
            //변환해야할 문자의 인덱스를 얻음
            int idx = hm2.get(ch);
            
            //만약 index만큼 이동시켜 변환했을때 list 크기를 벗어나면 wrap around 함
            //아니라면 index만큼 이동시켜 변환한 결과를 그대로 사용함
            idx = idx + index < list.size() ? idx + index : (idx + index) % list.size();
            
            //변환 결과를 결과 문자열에 저장함
            sb.append(list.get(idx)+"");
        }
        
        return sb.toString();
    }
}