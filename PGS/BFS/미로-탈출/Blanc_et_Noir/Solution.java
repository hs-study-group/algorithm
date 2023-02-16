//https://school.programmers.co.kr/learn/courses/30/lessons/159993

import java.util.*;

//y,x 좌표 및 이동 횟수, 레버 조작 여부를 저장할 변수를 관리하는 노드 클래스
class Node{
    int y, x, c, k;
    Node(int y, int x, int c, int k){
        this.y=y;
        this.x=x;
        this.c=c;
        this.k=k;
    }
}

class Solution {
    public int solution(String[] maps) {
    	//탈출이 불가능하다고 가정
        int answer = -1;
        
        //미로의 정보를 저장할 배열
        char[][] arr = new char[maps.length][maps[0].length()];
        
        //방문배열, y,x좌표 및 레버 조작 여부를 인덱스로 가짐
        boolean[][][] v = new boolean[arr.length][arr[0].length][2];
    
        //문자열을 문자 배열 맵으로 변환
        for(int i=0; i<arr.length; i++){
            arr[i] = maps[i].toCharArray();
        }
        
        //시작 및 종료 위치를 저장할 변수
        Node start = null;
        Node end = null;
        
        //문자 배열로 변환된 미로를 탐색
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
            	//시작 및 종료 위치를 저장
                if(arr[i][j]=='S'){
                    start = new Node(i,j,0,0);
                }else if(arr[i][j]=='E'){
                    end = new Node(i,j,0,0);
                }
            }
        }
        
        //BFS탐색을 위한 벡터 배열 선언
        int dist[][] = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        
        //BFS탐색에 사용할 큐 선언 및 시작 노드 추가, 방문처리
        Queue<Node> q = new LinkedList<Node>();
        q.add(start);        
        v[start.y][start.x][0] = true;
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //종료 위치에 도달했다면
            if(n.y==end.y&&n.x==end.x&&n.k==1){
            	//그때의 이동횟수가 최소임이 보장됨
                answer = n.c;
                break;
            }
            
            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                
                //맵의 범위를 벗어나지 않고, 벽이 아니라면
                if(y>=0&&y<arr.length&&x>=0&&x<arr[0].length&&arr[y][x]!='X'){
                	//해당 위치가 레버이고, 레버를 조작한 상태로 도달한 적이 없다면
                    if(arr[y][x]=='L'&&!v[y][x][1]){
                    	//해당 위치를 방문함과 동시에 레버를 조작함
                    	//레버를 조작한 상태에서 해당 위치를 방문한 적이 있음으로 처리
                        q.add(new Node(y,x,n.c+1,1));
                        v[y][x][1] = true;
                    //해당 위치에 현재 레버상태로 방문한 적이 없다면
                    }else if(!v[y][x][n.k]){
                    	//해당 위치를 방문함
                        q.add(new Node(y,x,n.c+1,n.k));
                        v[y][x][n.k] = true;
                    }
                }
            }
        }
        
        return answer;
    }
}