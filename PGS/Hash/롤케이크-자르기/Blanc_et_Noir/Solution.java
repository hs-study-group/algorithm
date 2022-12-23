import java.util.*;

class Solution {
    public int solution(int[] topping) {
        int answer = 0;
        
        //철수의 롤케이크 조각의 토핑 정보를 저장할 해쉬맵
        HashMap<Integer,Integer> hm1 = new HashMap<Integer,Integer>();
        
        //철수 동생의 롤케이크 조각의 토핑 정보를 저장할 해쉬맵
        HashMap<Integer,Integer> hm2 = new HashMap<Integer,Integer>();
        
        //철수 동생이 롤케이크 전부를 일단 가져갔다고 가정하고 토핑 정보를 갱신함 
        for(int i : topping){
            if(hm2.containsKey(i)){
                hm2.put(i,hm2.get(i)+1);
            }else{
                hm2.put(i,1);
            }
        }
        
        //
        for(int i : topping){
        	//동생에게서 i토핑을 하나 가져옴
            hm2.put(i,hm2.get(i)-1);
            
            //해당 종류의 토핑이 더이상 동생에게 없다면
            if(hm2.get(i)==0){
            	//완전히 제거함, 제거하지 않으면 size( )호출시 값이 예상과는 다르게 나옴
                hm2.remove(i);
            }
            
            //철수의 롤케이크 조각에 해당 토핑의 개수를 1증가시킴
            if(hm1.containsKey(i)){
                hm1.put(i,hm1.get(i)+1);
            }else{
                hm1.put(i,1);
            }
            
            //철수와 동생의 롤케이크 조각의 토핑 종류 개수가 동일하다면
            if(hm1.size()==hm2.size()){
            	//정답으로 처리함
                answer++;
            }
        }
        
        return answer;
    }
}