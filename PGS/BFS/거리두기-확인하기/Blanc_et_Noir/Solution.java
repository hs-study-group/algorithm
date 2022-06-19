import java.util.*;

//y, x좌표 및 거리 c를 저장할 노드 클래스
class Node{
    int y, x, c;
    Node(int y, int x, int c){
        this.y = y;
        this.x = x;
        this.c = c;
    }
}

class Solution {
	//존재하는 사람의 정보를 저장할 큐 선언
    public static Queue<Node> persons;
    
    //1차원 String배열을 2차원 char 배열로 변환하여 저장할 맵 선언
    public static char[][] map;
    
    //방문배열 선언
    public static boolean[][] v;
    
    //대기실을 입력받아 2차원 map, 방문배열, 사람의 위치를 저장할 큐를 세팅함
    public static void set(String[] place){
        persons = new LinkedList<Node>();
        
        //문제에서 대기실의 크기는 5x5로 고정됨
        v = new boolean[5][5];
        map = new char[5][5];
        
        //1차원 문자열로 주어진 배치에 대하여
        for(int i=0; i<place.length; i++){
            String[] str = place[i].split("");
            //2차원 char 배열로 변환하고, 그 위치가 사람이라면 해당 정보를 큐에 추가함
            for(int j=0; j<str.length; j++){
                if(str[j].equals("P")){
                    persons.add(new Node(i,j,0));
                }
                //String을 char로 변환하여 저장
                map[i][j]=str[j].charAt(0);
            }
        }
    }
    
    //모든 사람에 대하여 BFS 탐색 실시
    public static boolean BFS(){
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //아직 탐색하지 않은 사람이 있다면
        while(!persons.isEmpty()){
            
        	//해당 사람으로부터 BFS탐색을 실시함
            Node p = persons.poll();
            Queue<Node> q = new LinkedList<Node>();
            
            q.add(p);
            
            //방문배열은 특정 사람에 대하여 BFS탐색을 실시할때에 초기화 해야함
            //그렇지 않으면 아래의 경우에 대해 1을 출력하는 문제 발생
            
            //POOOP
            //OXXOX
            //OXXPX
            //OPXOX
            //PXXXP
            
            v = new boolean[5][5];
            v[p.y][p.x] = true;
            
            while(!q.isEmpty()){
                
                Node n = q.poll();
                
                for(int i=0; i<dist.length; i++){
                    
                    int y = n.y + dist[i][0];
                    int x = n.x + dist[i][1];
                    
                    //해당 위치가 맵을 벗어나지 않을때
                    if(y>=0&&y<5&&x>=0&&x<5){
                    	//만약 아직 방문하지 않은 책상이라면
                        if(!v[y][x]&&map[y][x]=='O'){
                        	//거리를 1 증가시켜 큐에 다시 추가하고 방문처리함
                            q.add(new Node(y,x,n.c+1));
                            v[y][x] = true;
                        //아직 방문하지 않은 P라면
                        }else if(!v[y][x]&&map[y][x]=='P'){
                        	//여태까지 계산한 맨해튼 거리가 현재 2미만이라면
                        	//P까지의 거리는 현재 측정한 거리 + 1이고, 이것이 2이하라는 이야기므로
                        	//거리두기를 지키지 못한 것임
                            if(n.c<2){     
                                return false;
                            }                            
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        
        //각 대기실에 대해
        for(int i=0; i<places.length; i++){
        	//여러 변수, 배열들을 초기화 함
            set(places[i]);
            
            //BFS탐색 결과에 따라 1또는 0을 저장함
            if(BFS()){
                answer[i] = 1;
            }else{
                answer[i] = 0;
            }
        }
        return answer;
    }
}