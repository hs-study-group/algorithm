//https://school.programmers.co.kr/learn/courses/30/lessons/87694

import java.util.*;

//y, x좌표 및 이동하는데 필요한 비용을 저장할 노드 클래스
class Node{
    int y, x, c;
    Node(int y, int x, int c){
        this.y=y;
        this.x=x;
        this.c=c;
    }
}

class Solution {
	//각 모서리의 y, x좌표를 입력받아 2차원 배열에 사각형을 채워넣는 메소드
    public void paint(int[][] arr, int[] rectangle){
        int ldx = rectangle[0]*2;
        int ldy = rectangle[1]*2;
        int rux = rectangle[2]*2;
        int ruy = rectangle[3]*2;
        
        for(int i=ldy; i<=ruy; i++){
            for(int j=ldx; j<=rux;j++){
                arr[i][j]=1;
            }
        }
    }
    
    //각 모서리의 y, x좌표를 입력 받아 해당 사각형의 내부를 제거하는 메소드
    public void remove(int[][] arr, int[] rectangle){
        int ldx = rectangle[0]*2;
        int ldy = rectangle[1]*2;
        int rux = rectangle[2]*2;
        int ruy = rectangle[3]*2;
        
        for(int i=ldy+1; i<ruy; i++){
            for(int j=ldx+1; j<rux;j++){
                arr[i][j]=0;
            }
        }
    }
    
    //BFS탐색시 y, x좌표가 배열 범위를 벗어나는지 확인하는 메소드
    public boolean isInRange(int[][] arr, int y, int x){
        if(y>=0&&y<arr.length&&x>=0&&x<arr[0].length){
            return true;
        }
        return false;
    }
    
    //sy, sx위치에서 시작하여 ey, ex로 이동하는데 필요한 최소 비용 / 2를 리턴하는 메소드
    public int BFS(int[][] arr, int sy, int sx, int ey, int ex){
        int result = 0;
        
        //BFS에 사용할 큐 선언
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(sy,sx,0));
        
        int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        
        boolean[][] v = new boolean[arr.length][arr[0].length];
        v[sy][sx] = true;
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //아이템을 주웠다면
            if(n.y==ey&&n.x==ex){
            	//좌표를 2배 했으므로, 비용은 그 절반이 되어야 함
                result = n.c / 2;
                break;
            }
            
            for(int i=0;i<dist.length;i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                
                //다음에 이동할 테두리가 아직 방문하지 않았고, 맵을 벗어나지도 않는다면
                if(isInRange(arr,y,x)&&!v[y][x]&&arr[y][x]==1){
                	//해당 위치를 탐색할 수 있도록 큐에 추가하고 방문처리함
                    q.add(new Node(y,x,n.c+1));
                    v[y][x]=true;
                }
            }
        }
        
        //최소 비용을 리턴함
        return result;
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {        
        int[][] arr = new int[50*2+1][50*2+1];
        
        //주어진 사각형을 arr2차원 배열에 그림
        for(int i=0; i<rectangle.length;i++){
        	//좌표를 2배로 확대하여 사각형을 채워 넣음
            paint(arr,rectangle[i]);
        }
        
        //주어진 사각형의 내부를 제거함
        for(int i=0; i<rectangle.length;i++){
            remove(arr,rectangle[i]);
        }
        
        //좌표가 2배가 되었으므로, 시작 및 종료 좌표도 2배가 되어야 함
        return BFS(arr,characterY*2, characterX*2, itemY*2, itemX*2);
    }
}