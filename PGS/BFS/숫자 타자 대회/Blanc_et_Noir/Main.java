//https://school.programmers.co.kr/learn/courses/30/lessons/136797

import java.util.*;

class Node{
	//각각 왼손, 오른손 엄지로 가리키고 있는 숫자, 소요한 비용, 몇번째 숫자를 선택해야할 차례인지를 저장함
    int l, r, c, i;
    Node(int l, int r, int c, int i){
        this.l = l;
        this.r = r;
        this.c = c;
        this.i = i;
    }
}

class Solution {    
    public int solution(String numbers) {
        int answer = 0;
        
        //간선 정보를 2차원 배열로 구성함
        //굳이 BFS탐색으로 비용을 계산하지는 않음
        int[][] cost = new int[][]{
            {1,7,6,7,5,4,5,3,2,3},
            {7,1,2,4,2,3,5,4,5,6},
            {6,2,1,2,3,2,3,5,4,5},
            {7,4,2,1,5,3,2,6,5,4},
            {5,2,3,5,1,2,4,2,3,5},
            {4,3,2,3,2,1,2,3,2,3},
            {5,5,3,2,4,2,1,5,3,2},
            {3,4,5,6,2,3,5,1,2,4},
            {2,5,4,5,3,2,3,2,1,2},
            {3,6,5,4,5,3,2,4,2,1}
        };
        
        char[] arr = numbers.toCharArray();
        
        //BFS탐색시 소모한 비용이 적은 정보로 먼저 탐색할 수 있도록 우선순위큐를 활용
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
        
        //[l][r][i] = c가 의미하는 바는 왼손이 l숫자를, 오른손이 r숫자위에 위치한 상태에서
        //i번째 숫자를 누를 차례가 되었을때 c비용으로 방문한 적이 있음을 말함
        int[][][] v = new int[10][10][arr.length+1];
        
        //3차원 방문 배열을 편의상 MAX_VALUE로 초기화함
        for(int i=0; i<v.length; i++){
            for(int j=0; j<v[0].length; j++){
                for(int k=0; k<v[0][0].length; k++){
                    v[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }
        
        pq.add(new Node(4,6,0,0));
        
        while(!pq.isEmpty()){
            Node n = pq.poll();
            
            //만약 모든 숫자를 누르는데 성공했다면
            if(n.i==arr.length){
            	//우선순위 큐에 의해 비용이 최소가됨을 보장하므로 그것을 정답으로함
                answer = n.c;
                break;
            }
            
            int next = arr[n.i] - '0';
            
            //만약 현재 눌러야 할 버튼이 왼손에 위치했으면
            if(n.l==next){
            	//그냥 왼손으로 누르는 것이 최고의 방법임
                pq.add(new Node(next,n.r,n.c+1,n.i+1));
                v[next][n.r][n.i+1] = n.c+1;
            //만약 현재 눌러야 할 버튼이 오른손에 위치했으면
            }else if(n.r==next){
            	//그냥 오른손으로 누르는 것이 최고의 방법임
                pq.add(new Node(n.l,next,n.c+1,n.i+1));
                v[n.l][next][n.i+1] = n.c+1;
            }else{
                int cl = cost[n.l][next];
                int cr = cost[n.r][next];
                
                //만약 왼손으로 해당 버튼을 눌렀을때의 누적 비용이 기존보다 더 적은비용이라면
                if(n.c+cl<v[next][n.r][n.i+1]){
                	//재방문을 허용하여 다시 탐색할 수 있도록 함
                    pq.add(new Node(next,n.r,n.c+cl,n.i+1));
                    v[next][n.r][n.i+1] = n.c+cl;
                }
                
                //만약 오른손으로 해당 버튼을 눌렀을때의 누적 비용이 기존보다 더 적은비용이라면
                if(n.c+cr<v[n.l][next][n.i+1]){
                	//재방문을 허용하여 다시 탐색할 수 있도록 함
                    pq.add(new Node(n.l,next,n.c+cr,n.i+1));
                    v[n.l][next][n.i+1] = n.c+cr;
                }
            }
        }
        
        return answer;
    }
}