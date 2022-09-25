//https://school.programmers.co.kr/learn/courses/30/lessons/67259

import java.util.*;

//각각 y, x좌표, 방향, 소모한 비용 정보를 저장할 노드클래스 선언
class Node{
    int y, x, d, c;
    Node(int y, int x, int d, int c){
        this.y = y;
        this.x = x;
        this.d = d;
        this.c = c;
    }
}

class Solution {
    public int solution(int[][] board) {
        int answer = 0;
        
        //방문 배열은 3차원으로 구성, int[y][x][d]이며 특히 d는 방향을 의미하는데
        //이 문제에서는 반드시 방향을 고려해야만 함
        //목적지에 도달하기 위해서 회전을 하면서 도착한 것과, 직진해서 도착한 것은 비용적으로 차이가 있음
        int[][][] v = new int[board.length][board[0].length][5];
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};

        //비용이 적은 노드가 먼저 반환되도록 하는 우선순위 큐 선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
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

        //0,0좌표에서 시작, 방향은 4를 지정하여 초기상태를 나타냄
        //비용을 -500으로 지정한 이유는, 첫 방향 4이며 이 상태에서는 어떤식으로 이동하든지 간에 반드시
        //기존에 이동한 방향과는 다른 방향으로 이동했다고 생각하기 때문임.
        //즉, 맨 처음에는 직진으로 이동하되, 방향이 바뀌면서 이동했다고 생각하기에 600의 비용을 소모하게 되는데
        //이를 상쇄시키기 위해 500을 역으로 빼는 것임
        pq.add(new Node(0,0,4, -500));
        v[0][0][4] = 100;
        
        //방문 배열을 모두 정수의 최대치로 초기화함
        //이유는, 방문 배열에는 해당 위치에 도달하기위해 여태까지 소모한 비용의 최소값을 저장해두는데
        //초기화를 하지 않으면 비용이 0이 되므로 BFS탐색이 실행조차 되지 않음
        for(int i=0; i<v.length; i++){
            for(int j=0; j<v[0].length; j++){
                for(int k=0; k<v[0][0].length; k++){
                    v[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }
        
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //목적지에 도달했다면 그 비용을 반환함
            if(n.y==board.length-1&&n.x==board[0].length-1){
                answer = n.c;
                break;
            }
            
            for(int i=0; i<dist.length; i++){
                int y = n.y + dist[i][0];
                int x = n.x + dist[i][1];
                
                //해당 위치가 맵을 벗어나지 않고, 벽이 아닌 빈 공간이라면
                if(y>=0&&y<board.length&&x>=0&&x<board[0].length&&board[y][x]!=1){
                	//기존과 방향이 다르고 기존에 해당 위치에 방문 했던 비용보다 적거나, 동일한 비용을 소모했다면
                    if(n.d!=i&&n.c+600<=v[y][x][i]){
                    	//해당 지역을 방문할 수 있음
                    	//비용이 500이 아닌 600이 추가되는 이유는 방향을 회전함과 동시에 직진하므로 500 + 100의 비용 필요
                        pq.add(new Node(y,x,i,n.c+600));
                        v[y][x][i] = n.c+600;
                    //기존과 방향이 같고 기존에 해당 위치에 방문 했던 비용보다 적거나, 동일한 비용을 소모했다면
                    }else if(n.d==i&&n.c+100<=v[y][x][i]){
                    	//해당 지역을 방문할 수 있음
                        pq.add(new Node(y,x,i,n.c+100));
                        v[y][x][i] = n.c+100;
                    }
                }
            }
        }

        return answer;
    }
}