//https://school.programmers.co.kr/learn/courses/30/lessons/172928

import java.util.*;

class Solution {
	//y, x좌표가 맵을 벗어나는지 아닌지 판단하는 메소드
    public boolean isInRange(char[][] map, int y, int x){
        if(!(y>=0&&y<map.length&&x>=0&&x<map[0].length)){
            return false;
        }
        return true;
    }
    
    //y, x위치로부터 d방향으로 v만큼 이동할 때, 중간에 장애물을 만나는지 아닌지를 판단하는 메소드
    public boolean isMovableTo(char[][] map, int y, int x, int d, int v){
        int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        
        while(v>0){
        	//d방향으로 한 칸 이동함
            y += dist[d][0];
            x += dist[d][1];
            
            //만약 그위치가 장애물이면
            if(map[y][x]=='X'){
            	//해당 위치로 이동이 불가함
                return false;
            }
            
            v--;
        }
        
        return true;
    }
    
    //맵 정보와 경로 정보를 입력 받아 로봇 강아지를 움직이는 메소드
    public int[] move(char[][] map, String[] routes){
        int[][] dist = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
        
        //N, S, W, E방향을 각각 0, 1, 2, 3 인덱스로 변환하기 위한 해쉬맵 선언
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        hm.put("N",0);
        hm.put("S",1);
        hm.put("W",2);
        hm.put("E",3);
        
        //가장 최근의 로봇 강아지의 y, x좌표
        int ry=0, rx=0;
        
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
            	//시작 지점이라면
                if(map[i][j]=='S'){
                	//그때의 y, x좌표를 현재 위치로 기록함
                    ry=i;
                    rx=j;
                }
            }
        }
        
        //경로 정보를 차례대로 탐색함
        for(int i=0;i<routes.length;i++){
            String[] temp = routes[i].split(" ");
            
            //d방향으로 v만큼 이동한다는 정보를 추출함
            int d = hm.get(temp[0]);
            int v = Integer.parseInt(temp[1]);
            
            //만약 d방향으로 v만큼 이동했을때 맵을 벗어나지 않고, 중간에 장애물도 만나지 않으면
            if(isInRange(map,ry+dist[d][0]*v,rx+dist[d][1]*v)&&isMovableTo(map,ry,rx,d,v)){
            	//그 위치를 로봇강아지의 새로운 위치로 갱신함
                ry=ry+dist[d][0]*v;
                rx=rx+dist[d][1]*v;
            }
        }
        return new int[]{ry,rx};
    }
    
    public int[] solution(String[] park, String[] routes) {
        char[][] map = new char[park.length][park[0].length()];
        
        //1차원 문자열 배열로 주어진 맵 정보를 2차원 문자 배열로 변환
        for(int i=0;i<park.length;i++){
            map[i] = park[i].toCharArray();
        }
        
        return move(map,routes);
    }
}