import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        
        //다리의 역할을 수행할 큐
        Queue<Integer> q = new LinkedList<Integer>();
        
        //주어진 배열을 변환하여 저장할 큐
        Queue<Integer> wq = new LinkedList<Integer>();
        
        //현재의 무게, 시간, 다리위의 트럭 수, 다리를 통과한 트럭 수를 저장할 변수
        int cur_weight = 0;
        int cur_time = 0;
        int cur_truck = 0;
        int cnt = 0;
        
        //우선 현재 다리를 모두 공백으로 가득 채움
        for(int i=0; i<bridge_length; i++){
            q.add(0);
        }
        
        //주어진 배열을 큐로 변환함
        for(int i=0; i<truck_weights.length; i++){
            wq.add(truck_weights[i]);
        }
        
        //트럭이 모두 통과할 때 까지 반복함
        while(cnt != truck_weights.length){
            int out_truck = q.poll();
            
            //만약 다리를 통과한 것이 공백이 아니라 진짜 트럭이라면
            if(out_truck!=0){
            	//현재 다리위의 트럭 수를 감소시킴
                cur_truck--;
                
                //현재 다리위의 트럭 무게를 감소시킴
                cur_weight-=out_truck;
                
                //통과한 트럭의 수를 증가시킴
                cnt++;
            }
            
            //아직 대기중인 트럭이 있고, 가장 맨 앞의 트럭이 다리위에 올라와도
            //제한 트럭 숫자와 제한 트럭 무게 범위를 벗어나지 않는다면
            if(!wq.isEmpty()&&cur_truck<bridge_length&&wq.peek()+cur_weight<= weight){
            	//대기중인 트럭을 다리위에 배치시킴
                q.add(wq.peek());
                
                //현재 다리위의 트럭 수를 증가시킴
                cur_truck++;
                
                //현재 다리위의 트럭 무게를 증가시킴
                cur_weight+= wq.peek();
                
                //대기중인 트럭에서 제외함
                wq.poll();
            //대기중인 맨 앞의 트럭을 다리위로 보낼 수 없다면
            }else{
            	//다리를 공백으로 채움
                q.add(0);
            }
            
            //시간을 증가시킴
            cur_time++;
        }
        
        return cur_time;
    }
}