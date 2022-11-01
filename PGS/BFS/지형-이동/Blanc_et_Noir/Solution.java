//https://school.programmers.co.kr/learn/courses/30/lessons/62050

import java.util.*;

//y, x좌표 및 격자칸의 높이 v, 높이의 차이 c를 저장할 노드 클래스 선언
class Node{
    int y, x, v, c;
    Node(int y, int x, int v, int c){
        this.y = y;
        this.x = x;
        this.v = v;
        this.c = c;
    }
}

class Solution {
    public static boolean[][] v;
    public static PriorityQueue<Node> pq;
    
    //주어진 맵에 대하여 node부터 높이차이가 height이하인 지역을 방문하고
    //같은 그룹으로 이어질 수 없는 아직 방문하지 않은 위치중 가장 작은 높이 차이를 갖는 위치를 반환하는 메소드
    public static Node BFS(int[][] land, Node node, int height){
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //node부터 BFS탐색을 수행할 수 있도록 큐에 추가하고 방문처리함
        Queue<Node> q = new LinkedList<Node>();
        q.add(node);
        v[node.y][node.x] = true;
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //상하좌우 4방향에 대하여
            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                
                //이동하고자 하는 위치가 맵의 범위를 벗어나지 않고, 방문한 적도 없다면
                if(y>=0&&y<land.length&&x>=0&&x<land[0].length&&!v[y][x]){
                	//해당 위치와의 높이 차이가 height이하일때
                    if(Math.abs(n.v-land[y][x])<=height){
                    	//같은 그룹으로 이어질 수 있으므로 방문함
                        v[y][x] = true;
                        q.add(new Node(y,x,land[y][x], 0));
                    //해당 위치와의 높이 차이가 height보다 크다면
                    }else{
                    	//1. 같은 그룹이면서 height보다 높이 차이가 큰 경우
                    	//2. 다른 그룹이면서 height보다 높이 차이가 큰 경우 두 가지 경우가 존재함
                    	//일단 높이 차이의 절대값이 작은 순서대로 우선순위 큐에 추가함
                        pq.add(new Node(y,x,land[y][x],Math.abs(n.v-land[y][x])));
                    }
                }
            }
        }
        
        //우선순위 큐가 비어있지 않다면
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //만약 방문한 적이 없는 위치라면, 다른 그룹이면서 height보다 높이 차이가 큰 경우이므로
            //해당 위치를 리턴함
            if(!v[n.y][n.x]){
                return n;
            }
        }
        
        //더이상 다른 그룹이 없다면 null을 리턴함
        return null;
    }
    
    public int solution(int[][] land, int height) {
        int answer = 0;
        v = new boolean[land.length][land[0].length];
        
        //우선순위큐는 사다리를 설치하는데 필요한 비용이 더 적은 노드가 먼저 반환되도록 함
        pq = new PriorityQueue<Node>(new Comparator<Node>(){
           @Override
            public int compare(Node n1, Node n2){
                if(n1.c<n2.c){
                    return -1;
                }else if(n1.c>n2.c){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //편의상 0,0에서부터 BFS탐색을 수행함
        Node n = new Node(0,0,land[0][0],0);
        
        while(true){
            
        	//BFS 탐색을 수행함
            n = BFS(land,n,height);
 
            //BFS 탐색의 결과가 null이 아니라면
            if(n!=null){
            	//그 결과는 가장 적은비용으로 다른 그룹과 연결될 수 있는 사다리 설치 위치와 그 비용을 가지고 있으며
            	//그 비용을 결과값에 누적하여 더함
                answer+=n.c;
            //BFS 탐색의 결과가 null이라면
            }else{
            	//BFS 탐색 반복을 종료함
                break;
            }
        }
        
        return answer;
    }
}