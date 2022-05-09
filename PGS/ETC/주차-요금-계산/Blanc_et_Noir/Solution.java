import java.util.*;

class Solution {
	
	//주차장 진입, 출차 시간을 입력받아 그 차이를 분 단위로 리턴하는 메소드
    public static int dif(String start, String end){
        String[] temp = start.split(":");
        int s = Integer.parseInt(temp[0])*60+Integer.parseInt(temp[1]);
        
        temp = end.split(":");
        int e = Integer.parseInt(temp[0])*60+Integer.parseInt(temp[1]);
        
        return e - s;
    }
    
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        //주차장 출입 기록을 저장하는 해시맵
        HashMap<String,String> hm = new HashMap<String,String>();
        
        //각 자동차가 answer 배열의 어느 위치에 존재하는지 인덱스를 담아두는 해시맵
        HashMap<String,Integer> idx = new HashMap<String,Integer>();
        
        //각 자동차의 번호를 오름차순으로 answer에 배치하기위해 record를 오름차순 정렬
        Arrays.sort(records,new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                int n1 = Integer.parseInt(s1.split(" ")[1]);
                int n2 = Integer.parseInt(s2.split(" ")[1]);
                if(n1<n2){
                    return -1;
                }else if (n1>n2){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //answer의 인덱스를 가리키는 변수
        int cnt = 0;
        
        //중복되지 않도록 해시맵에 각 자동차들의 인덱스를 저장함
        for(int i=0; i<records.length; i++){
            String[] temp = records[i].split(" ");
            if(!idx.containsKey(temp[1])){
                idx.put(temp[1],cnt++);
            }
        }
        
        answer = new int[idx.size()];
        
        for(int i=0; i<records.length; i++){
            String[] temp = records[i].split(" ");
            
            //주차장 진입 시도라면 자동차 번호에 대한 주차장 진입 시각을 기록함
            if(temp[2].equals("IN")){
                hm.put(temp[1],temp[0]);
            //주차장 출차 시도라면 해당 자동차번호에 대한 주차장 진입 시각을 조회하고
            //해당 시간과 출차시간의 차이를 answer에 임시로 기록함
            }else{
                String start = hm.get(temp[1]);
                int m = dif(start, temp[0]);

                answer[idx.get(temp[1])] += m;
                //계산이 끝났으면 해당 주차장 진입 기록을 말소시킴
                hm.remove(temp[1]);
            }
        }
        
        //아직 주차장 진입 기록이 사라지지 않은 자동차들은
        //출차하지 않은 것으로 판단하고 23:59분에 자동으로 출차된 것으로 처리
        
        for(String num : hm.keySet()){
            String start = hm.get(num);
            int m = dif(start, "23:59");
            answer[idx.get(num)] += m;
        }
        
        for(int i=0; i<answer.length; i++){
            //해당 자동차의 누적 주차 시간이 기본 시간 이하일 경우
            if(answer[i]<=fees[0]){
            	//기본요금만 부과
                answer[i] = fees[1];
            //누적 주차 시간이 기본 시간을 초과했을경우
            }else{
            	//기본요금 + 초과한 주차 시간만큼의 요금으로 갱신함
                answer[i] = fees[1] + (int)Math.ceil((answer[i]-fees[0])*1.0/fees[2])*fees[3];
            }
        }
        
        return answer;
    }
}