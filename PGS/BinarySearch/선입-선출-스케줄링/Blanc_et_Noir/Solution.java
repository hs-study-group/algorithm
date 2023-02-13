//https://school.programmers.co.kr/learn/courses/30/lessons/12920

import java.util.*;

class Solution {
	//특정한 시각 time을 코어의 작업 처리 소요 시간으로 나누었을때
	//나누어 떨어진다면, 그것은 time 시각대에 해당 코어가 새로운 작업을 수행하기 위해 대기중임을 의미함
    public boolean check(long time, int coreTime){
    	//해당 코어가 time 시각에 새로운 작업이 입력되도록 기다리는 상태인지 아닌지를 판단함
        return time%coreTime==0;
    }
    
    public int solution(int n, int[] cores) {
        int answer = 0;
        
        //코어의 개수보다 작업의 개수가 적거나, 같으면
        if(n<=cores.length){
        	//n번째 코어가 마지막 작업을 처리함
            return n;
        }
        
        int fin = 0;
        
        //모든 작업을 처리하는데 걸리는 시간의 최소값
        long min = 0L;
        
        //모든 작업을 처리하는데 걸리는 시간의 최대값
        long max = 50000L*10000L;
        
        //모든 작업을 처리하기 위해 필요한 최소 시간을 저장할 변수
        long time = 0L;
        
        while(min<=max){
        	//mid시간내에 처리할 수 있는 작업의 개수를 구하기 위한 기준
            long mid = (min+max)/2L;
            long temp = 0L;
            
            //특정한 코어가 작업을 처리하는데 T만큼의 시간이 걸린다면
            //mid 시간 내에는 mid/T만큼의 작업을 처리할 수 있음
            for(int i=0; i<cores.length; i++){
                temp += mid/cores[i];
            }
            
            //mid시간내에 모든 코어들이 처리할 수 있는 작업의 개수 temp가 남은 작업들의 개수보다 적다면
            //모든 작업을 처리하는데 mid시간으로는 부족하다는 의미임
            if(temp<n-cores.length){
            	//mid 시간을 좀 늘려봄
                min = mid+1;
            //mid시간내에 모든 코어들이 처리할 수 있는 작업의 개수 temp가 남은 작업들의 개수보다 크거나 같다면
            //모든 작업을 처리하는데 mid시간이면 충분하다는 의미임
            }else{
            	//mid 시간을 좀 줄여봄
                max = mid-1;
                
                //모든 작업을 처리하는데 필요한 최소 시간을 mid로 갱신함
                time = mid;
            }
        }
        
        int num=cores.length;
        
        //모든 작업을 처리하는데 최소 time만큼의 시간이 필요하다면
        //time - 1만큼의 시간으로는 모든 작업을 당연히 처리할 수 없음
        //time - 1만큼의 시간으로 처리할 수 있는 작업의 개수를 구함
        for(int i=0; i<cores.length; i++){
            num += (time-1)/cores[i];
        }
        
        int cnt = 0;
        
        for(int i=0;i<cores.length; i++){
        	//만약 time시간이 해당 코어가 작업을 처리하는데 필요한 시간으로 나누어 떨어진다면
        	//time시각에 해당 코어는 작업을 수행할 준비가 되어있다는 의미임
            if(check(time,cores[i])){
            	//따라서 해당 코어가 작업을 처리하도록 함
                cnt++;
            }
            //만약 time-1시간까지 처리하고서 남은 작업들을 time 시각에 모두 처리했다면
            if(cnt==n-num){
            	//그때의 코어가 바로 마지막으로 작업을 처리하는 코어가 됨
                answer = i+1;
                break;
            }
        }
        
        return answer;
    }
}