//https://programmers.co.kr/learn/courses/30/lessons/1829

import java.util.*;

//y, x는 각각 좌표, c는 그림의 색깔을 나타냄
class Node{
    int y, x, c;
    Node(int c, int y, int x){
        this.c = c;
        this.y = y;
        this.x = x;
    }
}

class Solution {
    
	//아직 탐색하지 않은 영역이 있다면, 해당 영역의 시작 노드를 리턴하는 메소드
    public static Node find(int[][] n, boolean[][] v){
        for(int i=0; i<n.length; i++){
            for(int j=0; j<n[i].length; j++){
                if(n[i][j]>=1&&!v[i][j]){
                    return new Node(n[i][j],i,j);
                }
            }
        }
        return null;
    }
    
    //m은 그림, v는 방문배열, sn은 시작 노드
    public static int bfs(int[][] m, boolean [][] v, Node sn){
        int cnt = 1;
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //첫 시작노드를 큐에 추가하고 방문했음을 표시
        Queue<Node> q = new LinkedList<Node>();
        q.add(sn);
        v[sn.y][sn.x]=true;
        
        while(!q.isEmpty()){
            Node n = q.poll();
            int y, x;
            for(int i=0; i<dist.length; i++){
                y = n.y + dist[i][0];
                x = n.x + dist[i][1];
                //아직 방문하지 않은 인접한 노드일때
                if(y>=0&&y<m.length&&x>=0&&x<m[0].length&&!v[y][x]){
                	//해당 인접한 노드의 색이 기준 노드의 색과 일치한다면
                    if(m[y][x]==n.c){
                    	//해당 노드에서 다시 탐색할 수 있도록 큐에 추가 및 방문 처리
                        q.add(new Node(n.c,y,x));
                        v[y][x]=true;
                        //영역의 크기를 1증가시킴
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
    
    public int[] solution(int m, int n, int[][] picture) {
        int[] answer = new int[2];
        
        boolean[][] v = new boolean[m][n];
        
        Node node = null;
        
        //아직 탐색하지 않은 색칠된 영역이 있다면
        while((node = find(picture,v))!=null){
        	//해당 영역의 크기를 구함
            int cnt = bfs(picture,v,node);
            
            //해당 영역의 크기가 최대값이라면 갱신함
            answer[1] = answer[1] < cnt ? cnt : answer[1];
            
            //영역의 수를 1증가시킴
            answer[0]++;
        }
        
        return answer;
    }
}