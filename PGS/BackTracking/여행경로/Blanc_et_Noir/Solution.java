//https://school.programmers.co.kr/learn/courses/30/lessons/43164

import java.util.*;

class Solution {
    static String answer = null;
    static boolean flag = false;
    
    public static void backTracking(HashMap<String,ArrayList<String>> hm, int cnt, int max, String start, String result){
    	//만약 항공권을 모두 소모하여 경로를 출력한 적이 있으면
        if(flag){
        	//더이상 백트래킹을 할 필요가 없음
            return;
        //만약 항공권을 모두 소진했다면
        }else if(cnt==max){
        	//항공권을 모두 소진한 적이 있음을 표시함
            flag = true;
            
            //여행 경로를 저장함
            answer = "ICN"+result;
        //아직 항공권을 모두 소모하지 않았다면
        }else{
            int size;
            
            //현재 위치에서 갈 수 있는 다음 위치가 존재하는지 아닌지 파악함
            if(hm.get(start)!=null) {
            	size = hm.get(start).size();
            }else {
            	size = 0;
            }
            
            //만약 다음 위치로 갈 수 있는 항공권이 아직 있다면
            for(int i=0; i<size; i++){
            	//해당 항공권을 소모함
                String end = hm.get(start).remove(0);
                
                //백트래킹을 통해 다음 위치를 기준으로 다시 탐색함
                backTracking(hm,cnt+1,max,end,result+" "+end);
                
                //해당 항공권을 소모한 적이 없는 것처럼 처리함
                hm.get(start).add(end);
            }
        }
    }
    public static String[] solution(String[][] tickets) {
    	//프로그래머스는 static 변수를 반드시 solution 메소드 내에서 초기화를 해줘야 함
        answer = null;
        flag = false;
        
        //시작위치에 대하여 도착할 수 있는 위치 정보를 저장할 해시맵
        HashMap<String,ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
        
        //항공권 정보를 해시맵에 추가함
        for(int i=0; i<tickets.length; i++){
            if(hm.containsKey(tickets[i][0])){
                ArrayList<String> list = hm.get(tickets[i][0]);
                list.add(tickets[i][1]);
            }else{
                ArrayList<String> list = new ArrayList<String>();
                list.add(tickets[i][1]);
                hm.put(tickets[i][0],list);
            }
        }
        
        //항공권 정보를 오름차순 정렬함
        for(String key : hm.keySet()){
            Collections.sort(hm.get(key));
        }
        
        //여행의 시작점은 ICN으로 고정되어있음
        backTracking(hm,0,tickets.length,"ICN","");
        
        return answer.split(" ");
    }
}