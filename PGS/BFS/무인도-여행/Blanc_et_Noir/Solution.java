//https://school.programmers.co.kr/learn/courses/30/lessons/154540

import java.util.*;

//현재 y, x좌표를 저장할 노드 클래스 선언
class Node{
    int y, x;
    Node(int y, int x){
        this.y = y;
        this.x = x;
    }
}

class Solution {
    public int[] solution(String[] maps) {
        int[] answer = new int[1];
        
        //방문배열
        boolean[][] v = new boolean[maps.length][maps[0].length()];
        char[][] map = new char[v.length][v[0].length];
        
        //더 적은 식량을 가진 섬의 정보가 먼저 출력되도록 하는 우선순위 큐
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        
        //문자열로 주어진 지도를 문자배열로 변환함
        for(int i=0; i<maps.length; i++){
            map[i] = maps[i].toCharArray();
        }
        
        //아직 탐색하지 않은 노드를 순차적으로 찾음
        for(int i=0; i<map.length; i++){
            for(int j=0; j<map[0].length; j++){
            	//바다가 아니면서 방문한적이 없는 땅이라면
                if(!v[i][j]&&map[i][j]!='X'){
                	//섬에 포함된 식량의 총 합
                    int sum = 0;
                    int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
                    
                    //현재 위치를 큐에 추가함
                    Queue<Node> q= new LinkedList<Node>();
                    q.add(new Node(i,j));
                    
                    //방문처리함
                    v[i][j] = true;
                    
                    //현재 위치의 식량을 sum에 더함
                    sum += map[i][j]-'0';
                    
                    //BFS탐색을 수행함
                    while(!q.isEmpty()){
                        Node n = q.poll();
                        
                        for(int k=0; k<dist.length; k++){
                            int y = n.y + dist[k][0];
                            int x = n.x + dist[k][1];
                            //만약 이동하려는 위치가 범위를 벗어나지 않고 방문하지 않은 땅이라면 방문함
                            if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&!v[y][x]&&map[y][x]!='X'){
                            	//해당 지역의 식량을 sum에 더하고 그 지역부터 다시 탐색하도록 큐에 추가함
                                q.add(new Node(y,x));
                                sum += map[y][x]-'0';
                                v[y][x]=true;
                            }
                        }
                    }
                    
                    //섬의 총 식량을 우선순위 큐에 추가함
                    pq.add(sum);
                }
            }
        }
        
        //저장된 섬의 총 식량이 없으면
        if(pq.size()==0){
        	//식량이 있는 섬이 아무것도 없는 것임
            return new int[]{-1};
        //저장된 섬의 총 식량이 있긴 있으면
        }else{
            answer = new int[pq.size()];
            
            //총 식량의 수가 적은 섬의 정보가 먼저 반환되도록함
            for(int i=0; i<answer.length; i++){
                answer[i] = pq.poll();
            }
            
            return answer;
        }
    }
}