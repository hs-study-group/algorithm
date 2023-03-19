//https://school.programmers.co.kr/learn/courses/30/lessons/169199

import java.util.*;

//y, x좌표 및 이동한 횟수 c를 저장할 클래스 선언
class Node{
    int y, x, c;
    Node(int y, int x, int c){
        this.y=y;
        this.x=x;
        this.c=c;
    }
}

class Solution {
	//주어진 맵에서, 현재 위치를 기준으로 d방향으로 이동했을때 벽에 부딫히거나
	//맵의 끝에 도달했을때의 위치를 리턴하는 메소드
    public Node find(char[][] map,int[][] dist, Node n, int d){
        int y = n.y;
        int x = n.x;
        
        Node end = null;
        
        while(true){
        	//만약 맵의 끝에 도달했거나, 벽을 만났다면
            if(!(y>=0&&y<map.length&&x>=0&&x<map[0].length)||map[y][x]=='D'){
            	//바로 이전 위치가 로봇이 도달해야할 새로운 위치가 됨
                end = new Node(y-dist[d][0],x-dist[d][1],0);
                break;
            //빈 공간이라면 그 방향으로 다시 탐색해봄
            }else{
                y+=dist[d][0];
                x+=dist[d][1];
            }
        }
        
        //로봇이 도달해야할 새로운 위치를 리턴함
        return end;
    }
    
    public int BFS(char[][] map, Node start, Node end){
    	//방문 배열을 boolean[][][] 또는 int[][]와 같이 표현할 수 있음
        int[][] v = new int[map.length][map[0].length];
        
        //BFS탐색에 사용할 벡터 배열 선언
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //BFS탐색에 사용할 큐 선언
        Queue<Node> q = new LinkedList<Node>();
        q.add(start);
        
        //방문배열을 모두 MAX_VALUE로 초기화함
        for(int i=0;i<v.length;i++){
            for(int j=0;j<v[0].length;j++){
                v[i][j]=Integer.MAX_VALUE;
            }
        }
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //목표 위치에 도달했다면
            if(n.y==end.y&&n.x==end.x){
            	//그때의 이동 횟수를 리턴함
                return n.c;
            }
            
            //4방향으로 탐색하여 로봇이 도달해야할 위치를 찾음
            for(int i=0;i<dist.length;i++){
            	//로봇이 도달해야할 빈 공간의 위치를 찾음
                Node vacancy = find(map,dist, n, i);
                
                //만약 그러한 위치가 존재하고, 기존에 도달할때 사용한 이동횟수보다 더 적은 이동횟수로 이동할 수 있다면
                if(vacancy!=null&&n.c+1<v[vacancy.y][vacancy.x]){
                	//로봇이 해당 위치를 방문하도록 처리함
                    v[vacancy.y][vacancy.x] = n.c+1;
                    q.add(new Node(vacancy.y,vacancy.x,n.c+1));
                }
            }
        }
        
        //중간에 리턴되지 않는다면 목표에 도달할 수 없다는 것이므로 -1을 리턴함
        return -1;
    }
    
    public int solution(String[] board) {        
        char[][] map = new char[board.length][board[0].length()];
        
        //BFS탐색을 시작할 위치와 종료할 위치
        Node start = null;
        Node end = null;
        
        //주어진 문자열 배열을 2차원 문자 배열로 변환함
        for(int i=0;i<map.length;i++){
            map[i] = board[i].toCharArray();
            for(int j=0;j<map[0].length;j++){
            	//만약 시작위치라면
                if(map[i][j]=='R'){
                	//그때의 위치를 기록함
                    start = new Node(i,j,0);
                //만약 종료위치라면
                }else if(map[i][j]=='G'){
                	//그때의 위치를 기록함
                    end = new Node(i,j,0);
                }
            }
        }
        
        return BFS(map,start,end);
    }
}