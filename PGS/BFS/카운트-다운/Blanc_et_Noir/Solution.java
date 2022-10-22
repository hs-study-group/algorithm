//https://school.programmers.co.kr/learn/courses/30/lessons/131129

import java.util.*;

//여태까지 남은 점수 v, 다트를 던진 횟수 c, 불 또는 싱글을 맞춘 횟수 s를 저장할 노드 클래스 선언
class Node{
    int v, c, s;
    Node(int v, int c, int s){
        this.v = v;
        this.c = c;
        this.s = s;
    }
}

class Solution {
    public int[] solution(int target) {
        int[] answer = new int[2];
        
        //해당 점수를 탐색한 적이 있는지 없는지 정보를 저장할 방문 배열 선언
        boolean[] v = new boolean[target+1];
        
        //다트를 던진 횟수가 더 적은, 횟수가 같다면 불을 맞춘 횟수가 많은, 불 또는 싱글을 맞춘 횟수가 동일하다면 남은 점수의 크기가 작은
        //노드가 우선적으로 반환될 수 있도록 하는 우선순위 큐 선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
           @Override
            public int compare(Node n1, Node n2){
                if(n1.c<n2.c){
                    return -1;
                }else if(n1.c>n2.c){
                    return 1;
                }else{
                    if(n1.s>n2.s){
                        return -1;
                    }else if(n1.s<n2.s){
                        return 1;
                    }else{
                        if(n1.v<n2.v){
                            return -1;
                        }else if(n1.v>n2.v){
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                }
            }
        });
        
        //초기에 남은 점수를 탐색한 적이 있음으로 처리함
        v[target] = true;
        
        //시작 점수부터 탐색할 수 있도록 우선순위 큐에 노드를 추가함
        pq.add(new Node(target, 0,0));
        
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //남은 점수가 0이라면
            if(n.v==0){
            	//우선순위 큐에 의해 그것이 정답임을 보장할 수 있음
                answer = new int[]{n.c,n.s};
                break;
            }
            
            //만약 불을 맞췄을때 언더플로우가 발생하지 않고, 해당 점수대를 탐색한 적이 없다면
            if(n.v - 50 >= 0 && !v[n.v-50]){
            	//해당 노드 정보를 우선순위 큐에 추가함
                pq.add(new Node(n.v-50,n.c+1,n.s+1));
                
                //해당 점수대를 탐색한 적이 있음으로 처리함
                v[n.v-50] = true;
            }
            
            //싱글, 더블, 트리플을 맞췄을때를 탐색함
            for(int i=1; i<=3; i++){
                int s = 0;
                
                //만약 싱글이라면
                if(i==1){
                	//싱글을 맞춘 횟수를 1증가시키도록 함
                    s=1;
                }
                
                //1 ~ 20까지의 숫자를 맞췄을때의 상태를 각각 탐색함
                for(int j=1; j<=20; j++){
                	//해당 점수 * (싱글 또는 더블 또는 트리플 보너스) 의 값이 언더플로우 상태가 아니고, 아직 해당 점수대를 탐색한 적이 없다면
                    if(n.v-j*i>=0&&!v[n.v-j*i]){
                    	//해당 노드 정보를 우선순위 큐에 추가함
                        pq.add(new Node(n.v-j*i,n.c+1,n.s+s));
                        
                        //해당 점수대를 탐색한 적이 있음으로 처리함
                        v[n.v-j*i] = true;
                    }
                }
            }
        }
        
        return answer;
    }
}