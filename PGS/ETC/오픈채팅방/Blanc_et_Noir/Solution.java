import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        String[] answer = {};
        
        //특정 사용자의 ID에 대한 최신 닉네임 정보를 저장할 해시맵
        HashMap<String,String> db = new HashMap<String,String>();
        
        //채팅방 출입기록을 순서대로 저장할 큐
        Queue<String> q = new LinkedList<String>();
        
        for(int i=0; i<record.length; i++){
            String[] temp = record[i].split(" ");
            //닉네임 변경일 경우 DB를 최신화
            if(temp[0].equals("Change")){
                db.put(temp[1],temp[2]);
            //채팅방 입장일 경우 변경된 닉네임일 수 있으므로 DB 최신화
            //채팅방 출입기록을 저장하되, 유저 ID와 입출여부를 E,L등 공백으로 표기
            }else if(temp[0].equals("Enter")){
                db.put(temp[1],temp[2]);
                q.add(temp[1]+" E");
            //채팅방 퇴장일 경우 기록만 해두면 됨
            }else{
                q.add(temp[1]+" L");
            }
        }
        
        answer = new String[q.size()];
        int idx = 0;
        
        while(!q.isEmpty()){
            String[] temp = q.poll().split(" ");
            //입장, 퇴장 기록에 대해 ID를 최신화된 닉네임으로 교체함
            if(temp[1].equals("E")){
                answer[idx++] = db.get(temp[0])+"님이 들어왔습니다.";
            }else{
                answer[idx++] = db.get(temp[0])+"님이 나갔습니다.";
            }
        }
        
        return answer;
    }
}