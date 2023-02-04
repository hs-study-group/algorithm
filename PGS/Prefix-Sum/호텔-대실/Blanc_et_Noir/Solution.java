//https://school.programmers.co.kr/learn/courses/30/lessons/155651

import java.util.*;

class Solution {
	//시각 문자열을 분단위 정수값으로 변환하는 메소드
    public int parse(String time){
        String[] temp = time.split(":");
        return Integer.parseInt(temp[0])*60+Integer.parseInt(temp[1]);
    }
    
    public int solution(String[][] book_time) {
        final int MIN_TIME = 0;
        //23:59시각에 사용이 종료된다면, 00:09시각까지 청소후에 사용가능하며
        //청소시간을 편의상 대실 시간에 포함하여 00:08시각까지 사용하는 것으로 처리
        final int MAX_TIME = 24*60+10+1;
        
        int answer = 0;
        
        //누적합을 구할 배열
        int[] prefixSum = new int[MAX_TIME];
        
        for(int i=0; i<book_time.length; i++){
        	//시작 및 종료시각 문자열을 각각 분단위 정수값으로 변환함
            int startTime = parse(book_time[i][0]);
            
            //보통 닫힌구간 [A , B]의 누적합을 구하려면
            //arr[A]에는 N, arr[B+1]에는 -N을 누적하여 더해야 하지만
        	//종료시각은 청소시간 10분을 포함해야하며 청소가 종료되자마자 대실이 가능하므로 종료시각이 10:20이라면
        	//10:19까지는 회의실을 사용하는 것으로 처리하고, 10:20에는 더이상 사용하지 않는 것으로 처리해야 하므로
            //endTime은 parse(book_time[i][1])+10+1이 아니라 parse(book_time[i][1])+10으로 사용함
            int endTime = parse(book_time[i][1])+10;
            
            prefixSum[startTime] += 1;
            prefixSum[endTime] -= 1;
        }
        
        //누적합을 구함
        for(int i=1; i<MAX_TIME; i++){
            prefixSum[i] += prefixSum[i-1];
            //어떤 특정한 시각에서 동시에 대실된 호실의 개수의 최대값을 누적하여 구함
            answer = Math.max(prefixSum[i],answer);
        }
        
        return answer;
    }
}