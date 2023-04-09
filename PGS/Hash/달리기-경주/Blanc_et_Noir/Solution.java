import java.util.*;

class Solution {
    public String[] solution(String[] players, String[] callings) {
        String[] answer = new String[players.length];
        
        //특정한 선수 이름에 대하여 등수를 저장하는 해쉬맵
        HashMap<String,Integer> hm1 = new HashMap<String,Integer>();
        //특정한 등수에 대하여 선수 이름을 저장하는 해쉬맵
        HashMap<Integer,String> hm2 = new HashMap<Integer,String>();
        
        //선수 이름 및 등수에 대하여 각각 등수 및 선수 이름을 저장함
        for(int i=0;i<players.length;i++){
            hm1.put(players[i],i);
            hm2.put(i,players[i]);
        }
        
        for(int i=0;i<callings.length;i++){
        	//호명된 선수의 이름을 얻음
            String player1 = callings[i];
            //호명된 선수의 등수를 얻음
            int idx1 = hm1.get(player1);
            
            //제치려는 선수의 등수는 호명된 선수보다 1앞섬
            int idx2 = idx1-1;
            //제치려는 선수의 이름을 얻음
            String player2 = hm2.get(idx2);
            
            //서로의 등수를 바꿔서 저장함
            hm1.put(player1,idx2);
            hm1.put(player2,idx1);
            
            //각 등수에 대하여 서로의 이름을 바꿔서 저장함
            hm2.put(idx1,player2);
            hm2.put(idx2,player1);
        }
        
        //해쉬맵에 저장된 등수에 대한 선수 이름을 배열에 저장함
        for(int i=0;i<answer.length;i++){
            answer[i] = hm2.get(i);
        }
        
        return answer;
    }
}