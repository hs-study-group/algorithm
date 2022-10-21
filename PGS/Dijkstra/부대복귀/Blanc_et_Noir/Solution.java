//https://school.programmers.co.kr/learn/courses/30/lessons/132266

import java.util.*;

class Node{
    int idx, cost;
    Node(int idx, int cost){
        this.idx = idx;
        this.cost = cost;
    }
}

class Solution {
	
	//s에서 다른 노드로 이동하는데 필요한 최소 비용이 담긴 배열을 반환하는 다익스트라 메소드
    public static int[] dijkstra(ArrayList<ArrayList<Node>> g, int s){        
        int[] dist = new int[g.size()];
        Arrays.fill(dist,Integer.MAX_VALUE);
        
        //비용 정보가 가장 작은 노드가 먼저 반환되도록 하는 우선순위 큐 선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
           @Override
            public int compare(Node n1, Node n2){
                if(n1.cost<n2.cost){
                    return -1;
                }else if(n1.cost>n2.cost){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //시작위치는 비용이 0임
        dist[s] = 0;
        
        //우선순위 큐에 시작 노드 정보를 추가함
        pq.add(new Node(s,0));
        
        
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //만약 다음 위치로 이동하는데 필요한 최소비용보다 더 많은 비용을 사용했다면
            if(n.cost>dist[n.idx]){
            	//더이상 해당 노드 정보로는 탐색할 필요가 없음
                continue;
            }
            
            //현재 위치에 대하여 인접한 노드들을 탐색함
            for(int i=0; i<g.get(n.idx).size(); i++){
                Node next = g.get(n.idx).get(i);
                
                //여태까지 사용한 비용 + 다음 인접한 위치로 이동하기위한 비용
                int cost = n.cost + next.cost;
                
                //위에서 계산한 비용이 여태 기록했던 최소 비용보다 더 적은 비용인 경우에
                if(cost<dist[next.idx]){
                	//최소 비용을 해당 비용으로 갱신함
                    dist[next.idx] = cost;
                    
                    //다음 위치에서 다시 탐색할 수 있도록 해당 정보를 큐에 추가함
                    pq.add(new Node(next.idx,cost));
                }
            }
        }
        
        //s에서 다른 노드로 이동하는데 필요한 최소비용이 저장된 배열을 반환함
        return dist;
    }
    
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        int[] answer = new int[sources.length];
        
        //간선정보를 저장할 이중리스트 선언
        ArrayList<ArrayList<Node>> g = new ArrayList<ArrayList<Node>>();
        
        //이중리스트를 초기화 함
        for(int i=0; i<n; i++){
            g.add(new ArrayList<Node>());
        }
        
        //간선 정보를 저장함, 무방향 그래프임에 유의해야함
        for(int i=0; i<roads.length; i++){
            g.get(roads[i][0]-1).add(new Node(roads[i][1]-1,1));
            g.get(roads[i][1]-1).add(new Node(roads[i][0]-1,1));
        }
        
        //양방향그래프이므로 도착지에서 각 출발지로 이동하는데 필요한 비용을 역으로 계산하면
        //쉽게 문제를 해결할 수 있음.
        int[] dist = dijkstra(g,destination-1);
        
        for(int i=0; i<sources.length; i++){
        	//만약 목적지에서 해당 출발지로 이동하는데 필요한 비용이 무한대가 아니라면
            if(dist[sources[i]-1]!=Integer.MAX_VALUE){
            	//반대로 출발지에서 목적지로 이동하는 것이 가능함
                answer[i] = dist[sources[i]-1];
            //만약 목적지에서 해당 출발지로 이동하는데 필요한비용이 무한대라면
            }else{
            	//반대로 출발지에서 목적지로 이동하는 것은 불가능함
                answer[i] = -1;
            }            
        }
        
        return answer;
    }
}