//https://school.programmers.co.kr/learn/courses/30/lessons/1836

import java.util.*;

//노드 클래스
class Node{
    /*
        y : y좌표
        x : x좌표
        c : 문자
        d : 방향 0, 1, 2, 3, 4는 각각 상, 하, 좌, 우, 정지를 나타냄
        r : 남은 회전가능 횟수 + 1값, ex) 2라면 1번만 회전이 가능함
    */
    int y, x, c, d, r;
    Node(int y, int x, int c, int d, int r){
        this.y = y;
        this.x = x;
        this.c = c;
        this.d = d;
        this.r = r;
    }
}

class Solution {
    public static char[][] map;
    //각 타일은 항상 2개씩만 존재하며, 탐색을 시작할 타일의 위치는 shm 해시맵에 추가
    //탐색을 종료할 타일의 위치는 ehm 해시맵에 추가
    public static HashMap<Character, Node> shm;
    public static HashMap<Character, Node> ehm;
    
    //특정 노드를 기준으로 BFS탐색 실시
    public static boolean BFS(Node node){  
        int[][] dist={{-1,0},{1,0},{0,-1},{0,1}};
        Queue<Node> q = new LinkedList<Node>();

        /*
            방문배열을 4차원으로 구성함
            v[y][x][d][r]에 대하여
            y : y 좌표
            x : x 좌표
            d : 방향
            r : 남은 회전 횟수
            를 의미함.
            
            방향을 방문배열에서 고려해야 하는 이유는 회전의 횟수가 정해져있고
            
            .A
            ..
            ..
            ..
            ..
            A.
            (1)만약 위와 같은 상황이 주어진다면
            
            .A
            .^
            .^
            .^
            .^
            A>
            (2)위와 같이 이동하여 타일을 제거할 수 있음에도
            
            
            .A
            .X
            >>
            ^.
            ^.
            A.
            (3)위와 같이 다른 루트로 BFS탐색시 방문했다고 취급하여
            (2)의 루트로 탐색을 할 수 없게되는 문제가 발생함.  
        */
        boolean[][][][] v = new boolean[map.length][map[0].length][5][3];
        
        q.add(node);
        
        while(!q.isEmpty()){
            Node n = q.poll();
            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                //좌표가 유효범위내이면서 벽이 아니라면
                if(y>=0&&y<map.length&&x>=0&&x<map[0].length&&map[y][x]!='*'){
                    //만약 방향이 다르다면
                    if(n.d!=i){
                        //회전횟수가 남아있고 아직 방문하지 않았다면
                        if(n.r>0&&!v[y][x][i][n.r-1]){
                            //만약 그것이 자신과 같은 타일이면서, 자기 자신이 아니라면
                            if(map[y][x] == n.c && !(y==node.y && x==node.x)){
                                //BFS탐색에 성공한 것임
                                return true;
                            //빈칸이라면 그대로 탐색을 진행함
                            }else if(map[y][x] == '.'){
                                q.add(new Node(y,x,n.c,i,n.r-1));
                                v[y][x][i][n.r-1] = true;
                            //자신과 다른 문자 타일 또는 자기 자신이라면 다음 탐색으로 넘어감
                            }else{
                                continue;
                            }
                        //회전횟수가 남아있지 않다면 그 방향으로의 탐색을 종료함
                        }else{
                            continue;
                        }
                    //방향이 같다면
                    }else{
                        //아직 방문하지 않았다면
                        if(!v[y][x][i][n.r]){
                            //만약 그것이 자신과 같은 타일이면서, 자기 자신이 아니라면 
                            if(map[y][x] == n.c&& !(y==node.y && x==node.x)){
                                //BFS탐색에 성공한 것임
                                return true;
                            //빈칸이라면 그대로 탐색을 진행함
                            }else if(map[y][x] == '.'){
                                q.add(new Node(y,x,n.c,i,n.r));
                                v[y][x][i][n.r] = true;
                            //자신과 다른 문자 타일 또는 자기 자신이라면 다음 탐색으로 넘어감
                            }else{
                                continue;
                            }
                        //방문했다면 그 방향으로의 탐색을 종료함
                        }else{
                             continue;   
                        }                        
                    }
                }
            }
        }
        //중간에 BFS탐색을 종료하지 않았다면 BFS탐색에 실패한 것임
        return false;
    }
    
    public String solution(int m, int n, String[] board) {
        map = new char[m][n];
        shm = new HashMap<Character,Node>();
        ehm = new HashMap<Character,Node>();
        
        //문자타일을 탐색할때 알파벳 순서대로 BFS탐색을 할 수 있도록 정렬하여 저장할 리스트
        ArrayList<Character> arr = new ArrayList<Character>();
        
        //주어진 문자열 맵을 2차원 문자 배열로 변환함
        for(int i=0; i<board.length; i++){
            map[i] = board[i].toCharArray();
            for(int j=0; j<map[i].length; j++){
                //만약 문자 타일이라면
                if(map[i][j]>='A'&&map[i][j]<='Z'){
                    //탐색을 시작할 문자 타일로 저장한 적이 없으면
                    if(!shm.containsKey(map[i][j])){
                        //해당 위치를 저장함
                        shm.put(map[i][j],new Node(i,j,map[i][j],4, 2));
                        //리스트에 해당 문자를 저장함
                        arr.add(map[i][j]);
                    //탐색을 시작할 문자 타일로 저장한 적이 있으면
                    }else{
                        //시작위치 대신 종료 위치로 저장함
                        ehm.put(map[i][j],new Node(i,j,map[i][j],4, 2));
                    }
                }
            }
        }
        
        //문자를 오름차순 정렬함
        Collections.sort(arr);
        
        String answer = "";
        
        //만약 탐색을 시작할 타일이 아직 남아있다면
        while(!shm.isEmpty()){
            //BFS가 한번이라도 성공했는지 기록할 변수 선언
            boolean f = false;
            
            for(int i=0; i<arr.size(); i++){
                //타일을 하나 가져와서 BFS탐색을 실시함
                Node temp = shm.get(arr.get(i));
                //만약 타일 제거가 가능하면
                if(BFS(temp)){
                    //정답에 해당 문자를 추가함
                    answer += Character.toString(arr.get(i));
                    //BFS에 성공했음을 표시함
                    f = true;
                    
                    //시작 및 종료 위치를 저장했던 해시맵에서 해당 타일 제거
                    Node n1 = shm.remove(arr.get(i));
                    Node n2 = ehm.remove(arr.get(i));
                    
                    //맵에서 실제로 해당 타일을 제거함
                    map[n1.y][n1.x] = '.';
                    map[n2.y][n2.x] = '.';
                    
                    //정렬된 타일에서 해당 타일 제거함
                    arr.remove(i);
                    
                    //for문이 0부터 시작할 수 있도록 함
                    i=-1;
                }
            }
            //한번도 BFS가 성공한 적이 없으면
            //제거할 수 있는 타일이 없다는 의미이므로 BFS 탐색을 종료함
            if(!f){
                break;
            }
        }
        //타일이 모두 제거되었다면 answer를 반환함
        if(shm.isEmpty()){
            return answer;
        //타일이 모두 제거되지 않았다면 IMPOSSIBLE를 반환함
        }else{
            return "IMPOSSIBLE";
        }
    }
}