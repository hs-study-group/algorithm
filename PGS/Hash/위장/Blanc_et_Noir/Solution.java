import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        
        //의상의 종류에 대하여 의상의 이름을 저장하는 해시맵
        HashMap<String,HashSet<String>> hm = new HashMap<String,HashSet<String>>();
        
        for(int i=0; i<clothes.length; i++){
            String name = clothes[i][0];
            String type = clothes[i][1];
            
            //아직 해당 의상 종류에 대한 HashSet을 초기화하지 않았다면
            if(!hm.containsKey(type)){
            	//초기화 함
                hm.put(type,new HashSet<String>());
            }
            
            //해당 의상 종류에 해당 의상 이름을 추가함
            hm.get(type).add(name);
        }
        
        //만약 모자, 상의, 하의에 해당하는 옷이 각각 3, 4, 2개가 존재한다면
        //(3 + 1) * ( 4 + 1 ) * ( 2 + 1 ) - 1 = 모든 경우의 수임
        
        //모자에 속한 기존 3개의 모자 이름과, 모자를 착용하지 않을때의 경우의수 3 + 1 = 4가지
        //상의에 속한 기존 4개의 상의 이름과, 상의를 착용하지 않을때의 경우의수 4 + 1 = 5가지
        //하의에 속한 기존 2개의 상의 이름과, 상의를 착용하지 않을때의 경우의수 2 + 1 = 3가지
        //그러나, 옷을 어느 하나의 종류는 반드시 입어야 하므로
        //상의, 모자, 하의 모두 입지않는 경우의수 1가지를 빼야함
        for(String type : hm.keySet()){
        	//1을 더하는 이유는 해당 의상 종류를 착용하지 않았을 때의 경우의수도 고려하기 위함
            answer *= (hm.get(type).size()+1);
        }
        
        //모든 옷 종류를 입지 않았을때의 경우의 수는 제외함
        answer--;
        
        return answer;
    }
}