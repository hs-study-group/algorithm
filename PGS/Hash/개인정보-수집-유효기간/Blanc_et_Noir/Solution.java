//https://school.programmers.co.kr/learn/courses/30/lessons/150370

import java.util.*;

class Solution {
    HashMap<String,Integer> hm;
    
    //주어진 시간문자열을 일단위로 파싱하는 메소드
    public int parse(String datetime){
        String[] temp = datetime.split("\\.");
        
        //주어진 문자열로부터 년, 월, 일을 분리함
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int date = Integer.parseInt(temp[2]);
        
        //분리한 년, 월, 일을 바탕으로 일수를 계산함
        return (year-1)*12*28+(month-1)*28+date;
    }
    
    public int[] solution(String today, String[] terms, String[] privacies) {
        //기준이 될 시각을 파싱함
        int criteriaTime = parse(today);
        
        int[] answer = new int[3];
        
        
        hm = new HashMap<String,Integer>();
        

        ArrayList<Integer> list = new ArrayList<Integer>();
        
        //약관 종류에 대한 유효기간을 해시맵에 추가함
        for(String term : terms){
            String[] temp = term.split(" ");
            hm.put(temp[0],Integer.parseInt(temp[1])*28);
        }
        
        //각 개인정보들을 탐색함
        for(int i=0; i<privacies.length; i++){
            //해당 개인정보에 대하여
            String[] temp = privacies[i].split(" ");
            
            //수집 날짜와 약관의 종류를 분리함
            String datetime = temp[0];
            String term = temp[1];
            
            //만약 그 수집 날짜 + 보유가능한 기간이 오늘 날짜와 같거나 그보다 앞선다면
            if(criteriaTime>=parse(datetime)+hm.get(term)){
                //그 개인정보는 파기해야함
                list.add(i+1);
            }
        }
        
        //어레이리스트를 배열로 변환함
        answer = new int[list.size()];
        
        for(int i=0; i<answer.length; i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}