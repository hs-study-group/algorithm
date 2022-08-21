//https://school.programmers.co.kr/learn/courses/30/lessons/118667

import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        long answer = -1;
        long sum1 = 0, sum2 = 0;
        
        Queue<Integer> q1 = new LinkedList<Integer>();
        Queue<Integer> q2 = new LinkedList<Integer>();
        
        //두 개의 큐 각각의 합을 구하고, 배열을 큐로 변환함
        for(int i=0; i<queue1.length; i++){
            sum1 += queue1[i];
            sum2 += queue2[i];
            
            q1.add(queue1[i]);
            q2.add(queue2[i]);
        }

        //추출 횟수를 기록하는 변수
        int cnt = 0;
        
        //두 큐의 합이 같지 않으면
        while(sum1 != sum2){
        	//만약 1번 큐의 합이 더 크다면
            if(sum1 > sum2){
            	//1번 큐의 원소 하나를 빼내어 2번 큐에 삽입함
            	//또한 각각의 큐의 합을 갱신함
                sum1 -= q1.peek();
                sum2 += q1.peek();
                q2.add(q1.poll());
                cnt++;
            //만약 2번 큐의 합이 더 크다면
            }else if(sum1 < sum2){
            	//2번 큐의 원소 하나를 빼내어 1번 큐에 삽입함
            	//또한 각각의 큐의 합을 갱신함
                sum2 -= q2.peek();
                sum1 += q2.peek();
                q1.add(q2.poll());
                cnt++;
            }
            
            //만약 1번 큐와 2번 큐를 모두 순회하였음에도 두 큐의 합이 같지 않다면
            //절대로 두 큐의 합을 같게 만들 수 없다는 의미이므로 -1을 리턴함
            if(cnt>queue1.length+queue2.length+2){
                return -1;
            }
        }
        
        return cnt;
    }
}