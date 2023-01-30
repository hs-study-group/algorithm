//https://school.programmers.co.kr/learn/courses/30/lessons/154538

import java.util.*;

//v값을 만들기 위해서 c번의 연산을 수행했음을 저장하는 노드 클래스 선언
class Node{
    int v, c;
    Node(int v, int c){
        this.v = v;
        this.c = c;
    }
}

class Solution {
    public int solution(int x, int y, int n) {
        int answer = -1;
        
        //v값을 만들기위해 필요한 연산 최소 횟수를 저장할 방문배열
        int[] v = new int[1000001];
        
        //방문배열을 MAX_VALUE로 초기화함
        Arrays.fill(v,Integer.MAX_VALUE);
        
        Queue<Node> q = new LinkedList<Node>();
        q.add(new Node(x,0));
        v[x] = 0;
        
        while(!q.isEmpty()){
            Node node = q.poll();
            int num;
            int cnt = node.c + 1;
            
            //만약 y를 만들었다면, 이것이 최소 연산 횟수로 만들었음을 보장할 수 있음
            if(node.v == y){
                answer = node.c;
                break;
            }
            
            //현재 값에 n을 더해보고, 그 값을 만들기 위한 연산 횟수가 기존보다 적다면
            num = node.v + n;
            if(num >=1 && num <= 1000000 && v[num] > cnt){
            	//그것을 최소 횟수로 갱신하고 탐색을 진행함
                q.add(new Node(num,cnt));
                v[num] = cnt;
            }
            
            //현재 값에 2를 곱해보고, 그 값을 만들기 위한 연산 횟수가 기존보다 적다면
            num = node.v*2;
            if(num >=1 && num <= 1000000 && v[num] > cnt){
            	//그것을 최소 횟수로 갱신하고 탐색을 진행함
                q.add(new Node(num,cnt));
                v[num] = cnt;
            }
            
            //현재 값에 3를 곱해보고, 그 값을 만들기 위한 연산 횟수가 기존보다 적다면
            num = node.v*3;
            if(num >=1 && num <= 1000000 && v[num] > cnt){
            	//그것을 최소 횟수로 갱신하고 탐색을 진행함
                q.add(new Node(num,cnt));
                v[num] = cnt;
            }
        }
        
        
        return answer;
    }
}