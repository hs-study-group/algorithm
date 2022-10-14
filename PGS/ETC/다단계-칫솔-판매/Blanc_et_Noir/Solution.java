//https://school.programmers.co.kr/learn/courses/30/lessons/77486

import java.util.*;

//부모 노드 정보 및 자신의 수익을 저장하는 노드 클래스
class Node{
    Node parent;
    int sum;
    Node(Node parent, int sum){
        this.parent = parent;
        this.sum = sum;
    }
}

class Solution {
    static HashMap<String, Node> hm;
    
    //현재 노드가 amount만큼의 수익을 올렸을때, 해당 수익의 10%만큼을 부모에게 전달하고
    //나머지 90%를 자신이 온전히 갖도록 처리하는 메소드
    public static void process(Node current, int amount){
    	//현재 노드가 null이라면 더이상 탐색할 필요 없음
        if(current==null){
            return;
        }
        
        //자신의 부모 노드에게 지급할 수익금 10%의 지분
        int nextAmount = (int)(amount*0.1);
        
        //부모에게 10%의 수익금 지분을 지불하고 남은 수익
        int myAmount = amount - nextAmount;
        
        //자신의 수익금을 계산함
        current.sum = current.sum + myAmount;
        
        //만약 부모에게 전달할 수익금이 있으면
        if(nextAmount!=0){
        	//재귀적으로 그 수익을 분배함
        	process(current.parent, nextAmount);
        }
    }
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = {};
        
        //각 판매원의 노드 정보를 저장할 해시맵 선언
        hm = new HashMap<String,Node>();
        
        //루트 노드의 이름은 center이며, 루트노드의 부모는 null임
        hm.put("center",new Node(null, 0));
        
        //부모 및 자식관계를 탐색하여 적절하게 노드를 선언하고 해시맵에 추가함
        for(int i=0; i<referral.length; i++){
            if(referral[i].equals("-")){
                hm.put(enroll[i],new Node(hm.get("center"),0));
            }else{
                hm.put(enroll[i],new Node(hm.get(referral[i]),0));
            }
        }
        
        //판매원들이 기록한 수익을 적절히 처리함
        for(int i=0; i<seller.length; i++){
            process(hm.get(seller[i]),amount[i]*100);
        }
        
        //center를 제외한 각 판매원들의 최종 수익을 담을 배열 선언
        answer = new int[hm.size()-1];
        
        //각 판매원들의 수익을 배열에 담음
        for(int i=0; i<enroll.length; i++){
            if(hm.containsKey(enroll[i])){
                answer[i] = hm.get(enroll[i]).sum;
            }
        }
        
        return answer;
    }
}