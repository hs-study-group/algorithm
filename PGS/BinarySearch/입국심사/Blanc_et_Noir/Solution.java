//https://school.programmers.co.kr/learn/courses/30/lessons/43238

class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;

        //최고의 경우, 심사관 한명이 1명의 사람을 1분만에 입국심사를 끝내는 경우다.
        long min = 1;
        //최악의 경우 심사관 한명이 1000000000명의 사람을 1000000000분씩 소요하면서
        //모든 사람을 입국심사 하는 경우 1000000000*1000000000만큼의 시간이 소요된다.
        long max = 1000000000L*1000000000L;
        
        while(min<=max){
        	//mid만큼의 시간으로 과연 몇명이나 입국심사를 할 수 있을지 확인하기 위한 기준 시간
            long mid = (min+max)/2L;
            
            //입국심사를 수행한 횟수
            long cnt = 0;
            
            for(int i=0; i<times.length; i++){
            	//어떤 입국심사에 T만큼의 시간을 소요하는 입국심사관이
            	//M만큼의 시간동안 처리할 수 있는 사람의 수는 M/T명이다.
                cnt += (mid/times[i]);
                
                //오버플로우 방지를 위해 N명을 모두 입국심사하고도 남으면 반복문을 탈출함
                if(cnt>=n){
                    break;
                }
            }
            
            //입국심사를 완료한 사람의 수가 N보다 적으면
            if(cnt<n){
            	//입국심사 시간을 너무 적게 준 것이므로 늘려야한다.
                min = mid+1;
            //입국심사를 완료한 사람의 수가 N보다 많거나 같으면
            }else{
            	//입국심사 시간이 충분한 것이므로 시간을 좀 줄여본다.
            	//그때의 소요시간을 정답으로 갱신한다.
                max = mid-1;
                answer = mid;
            }
        }
        
        return answer;
    }
}