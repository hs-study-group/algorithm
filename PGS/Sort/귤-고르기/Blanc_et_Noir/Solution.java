//https://school.programmers.co.kr/learn/courses/30/lessons/138476

import java.util.*;

class Solution {
    
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        int[] arr = new int[10000001];
        
        //각 귤의 사이즈에 따른 개수를 계산함
        for(int size : tangerine){
            arr[size]++;
        }
        
        //귤의 종류별 개수를 정렬함
        Arrays.sort(arr);
        
        //개수가 많은 사이즈의 귤부터 탐색함
        for(int i=arr.length-1; i>=0; i--){
        	//해당 크기의 귤의 개수만큼 상자에 담을 귤의 개수를 감소시키고, 종류의 개수를 1증가시킴
            if(arr[i]!=0){
                k-=arr[i];
                answer++;
            }
            
            //만약 k개 이상의 귤을 모두 담아냈다면
            if(k<=0){
            	//더이상 반복하지 않음
                break;
            }
        }
        
        //귤의 종류 개수를 리턴함
        return answer;
    }
}