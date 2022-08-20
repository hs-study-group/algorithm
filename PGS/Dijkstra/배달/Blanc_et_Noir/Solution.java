//https://school.programmers.co.kr/learn/courses/30/lessons/12978

import java.util.*;

//어느 인덱스까지 오는데 소요된 비용 정보를 저장할 노드 클래스
class Node{
    int idx, cost;
    Node(int idx, int cost){
        this.idx = idx;
        this.cost = cost;
    }
}

class Solution {
	//간선 정보를 저장할 2차원 어레이리스트
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
    
    //N개의 노드가있고, S번째 노드로부터 다른 노드로 가는데 필요한 최소비용을 반환하는 메소드
    public static int[] func(int N, int S){
    	//최소비용을 저장할 배열
        int[] d = new int[N];
        
        //최단거리로 가기위한 경로를 저장할 배열
        int[] route = new int[N];
        
        //다익스트라 알고리즘에 사용할 우선순위큐 선언, 소모된 비용이 적은 순으로 가져옴
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                if(n1.cost < n2.cost){
                    return -1;
                }else if(n1.cost > n2.cost){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //비용 정보를 모두 무한으로 초기화
        Arrays.fill(d,Integer.MAX_VALUE);
        
        //시작 노드는 자기 자신까지 오는데 필요한 비용을 0으로 초기화함
        d[S] = 0;
        pq.add(new Node(S,0));
        
        while(!pq.isEmpty()){
            Node now = pq.poll();
            
            //여태 소모한 비용보다 해당 인덱스로 가기위해 쓰이는 최소 비용이 더 적다면
            //굳이 더 탐색할 필요가 없음
            if(now.cost > d[now.idx]){
                continue;
            }
            
            //현재 노드와 인접한 노드들을 탐색함
            for(int i=0; i<graph.get(now.idx).size(); i++){
            	//인접한 노드
                Node next = graph.get(now.idx).get(i);
                
                //만약 현재 위치까지 오는데 필요한 최소비용 + 다음 인접한 노드로 이동하는데 필요한 비용이
                int cost = d[now.idx] + next.cost;
                
                //다음 인접한 노드로 이동하는데 필요한 비용보다 더 적다면
                //즉, 기존에 저장해두었던 최소비용보다 여태껏 계산해온 비용이 더 적다면
                if(cost < d[next.idx]){
                	//그것을 최소비용으로 설정함
                    d[next.idx] = cost;
                    
                    //우선순위큐에 추가함
                    pq.add(new Node(next.idx, cost));
                    
                    //다음 인덱스로 가기위해서는 지금 인덱스를 꼭 방문해야함을 기록함
                    route[next.idx] = now.idx;
                }
            }
            
        }
        
        //최단비용 정보가 담긴 배열을 반환함
        return d;
    }
    
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        
        //간선정보를 저장할 어레이리스트 초기화
        for(int i=0; i<N; i++){
            graph.add(new ArrayList<Node>());
        }
        
        //무방향 그래프임을 고려하여 간선정보 추가 
        for(int i=0; i<road.length; i++){
            graph.get(road[i][0]-1).add(new Node(road[i][1]-1,road[i][2]));
            graph.get(road[i][1]-1).add(new Node(road[i][0]-1,road[i][2]));
        }
        
        //다익스트라 알고리즘 수행
        int[] d = func(N, 0);
        
        //주어진 K시간 이하로 갈 수 있는 지역의 수를 모두 카운트 함
        for(int i=0; i<d.length; i++){
            if(d[i] <= K){
                answer++;
            }
        }
        
        return answer;
    }
}