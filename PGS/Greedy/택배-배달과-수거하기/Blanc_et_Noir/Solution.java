//https://school.programmers.co.kr/learn/courses/30/lessons/150369

import java.util.*;

//어떤 지역의 인덱스와 그 가중치를 저장할 노드 클래스 선언
class Node implements Comparable{
    int i, v;
    Node(int i, int v){
        this.i = i;
        this.v = v;
    }
    
    //인덱스가 큰 노드(출발지로부터 멀리 있는 노드)부터 반환되도록 한다
    @Override
    public int compareTo(Object obj){
        Node n = (Node) obj;
        if(this.i>n.i){
            return -1;
        }else if(this.i<n.i){
            return 1;
        }else{
            return 0;
        }
    }
}

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        
        //배달해야할 택배 정보를 저장할 우선순위큐 선언
        PriorityQueue<Node> pq1 = new PriorityQueue<Node>();
        
        //수거해야할 상자 정보를 저장할 우선순위큐 선언
        PriorityQueue<Node> pq2 = new PriorityQueue<Node>();
        
        //배달 및 수거해야할 정보를 탐색하며 적절히 우선순위큐에 추가한다.
        for(int i=0; i<n; i++){
            if(deliveries[i]!=0){
                pq1.add(new Node(i,deliveries[i]));
            }
            if(pickups[i]!=0){
                pq2.add(new Node(i,pickups[i]));
            }
        }
        
        //만약 배달해야할 택배가 남았거나, 수거해야할 상자가 남았다면 계속 반복한다.
        while(!pq1.isEmpty()||!pq2.isEmpty()){
            int dist1 = 0;
            int dist2 = 0;
            
            //배달해야할 택배가 남아있다면
            if(!pq1.isEmpty()){
                int sum = 0;
                
                //가장 멀리까지 이동해서 배달해야할 거리를 구한다.
                dist1 = pq1.peek().i+1;
                
                //만약 배달해야할 택배가 남아있다면
                while(!pq1.isEmpty()){
                	//그 지역에 택배를 배달하기 위해 출발지에서 택배를 실어도 cap을 넘지 않는 경우
                    if(sum+pq1.peek().v<=cap){
                    	//해당 지역에 배달할 택배를 모두 싣는다.
                        sum += pq1.poll().v;
                    //만약 해당 지역에 배달할 택배를 모두 싣게되면 cap을 넘어 더이상 실을 수 없는 경우
                    }else{
                    	//여유분만큼만 해당 지역에 배달할 택배를 싣는다.
                        int val = cap-sum;
                        
                        //여유분만큼을 해당 지역에 배달할 택배 정보에서 제외한다.
                        pq1.peek().v = pq1.peek().v - val;
                        
                        //여유분만큼을 택배를 싣는다.
                        sum += val;
                        break;
                    }
                }
            }
            
            //수거해야할 상자가 남아 있다면
            if(!pq2.isEmpty()){
                int sum = 0;
                
                //가장 멀리까지 이동해서 수거해야할 거리를 구한다.
                dist2 = pq2.peek().i+1;
                
                //만약 수거해야할 상자가 남아있다면
                while(!pq2.isEmpty()){
                	//그 지역에 상자를 수거해도 cap을 넘지 않는 경우
                    if(sum+pq2.peek().v<=cap){
                    	//그 지역의 모든 상자를 수거한다.
                        sum += pq2.poll().v;
                    //그 지역에 상자를 모두 수거할 수 없는 경우
                    }else{
                    	//여유분 만큼을 수거한다.
                        int val = cap-sum;
                        
                        //여유분만큼을 해당 지역에서 수거할 상자 정보에서 제외한다.
                        pq2.peek().v = pq2.peek().v - val;
                        
                        //여유분만큼을 수거한다.
                        sum += val;
                        break;
                    }
                }
            }

            //택배를 배달하기위해 이동하는 거리와 수거하기 위해 이동하는 거리중 더 먼 거리를 왕복해야하므로
            //둘중 더 큰 거리의 2배만큼을 누적하여 정답에 더한다.
            answer += Math.max(dist1,dist2)*2;
        }
        
        return answer;
    }
}