//https://school.programmers.co.kr/learn/courses/30/lessons/43236

import java.util.*;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        int answer = 0;
        
        //징검다리의 위치를 저장할 배열
        int[] arr = new int[rocks.length+2];
        
        //계산 편의를 위해 0, distance위치에도 돌이 있다고 가정함
        arr[0] = 0;
        arr[arr.length-1] = distance;
        
        for(int i=1; i<arr.length-1; i++){
            arr[i] = rocks[i-1];
        }
        
        //징검다리 정보를 오름차순 정렬함
        Arrays.sort(arr);
        
        //징검다리 간격은 최소 min, 최대 max값을 가질 수 있음
        int min = 1;
        int max = 1000000000;
        
        while(min<=max){
        	//징검다리의 최소 간격을 mid라고 정함
            int mid = (min+max)/2;
            int cnt = 0;
            
            //탐색을 시작할 기준이 될 위치
            int current = 0;
            
            for(int i=1; i<arr.length; i++){
            	//현재 위치로부터 다음 징검다리와의 거리를 구함
                int d = arr[i]-arr[current];
                
                //그 거리가 최소 기준이 되는 거리보다 작다면
                if(d<mid){
                	//돌을 제거해야만 최소 기준을 만족할 수 있음
                    cnt++;
                //그 거리가 최소 기준이 되는 거리보다 크거나 같아서, 기준을 만족한다면
                }else{
                	//탐색 시작 위치를 i로 변경함
                    current = i;
                }
            }
            
            //만약 n개 미만의 돌을 제거했다면
            if(cnt<n){
            	//최소 거리를 너무 짧게 잡은 것이므로 이 거리를 늘려야함
                min = mid + 1;
                
                //n개 미만의 돌을 제거했다면, 조금 더 돌을 제거해도 최소 거리 mid를 만족할 수 있으므로
                //지금 최소 거리 또한 정답이 될 여지가 있음
                answer = mid;
            //만약 n개 초과의 돌을 제거했다면
            }else if(cnt>n){
            	//최소 거리를 너무 길게 잡은 것이므로 이 거리를 줄여야함
                max = mid - 1;
            //만약 n개의 돌을 제거했다면
            }else{
            	//징검다리들의 간격의 최소값이 더 늘어날 수 있는지 확인하기위해 그 기준값을 늘림
                min = mid + 1;
                
                //지금 최소 거리 또한 정답이 될 여지가 있음
                answer = mid;
            }
        }
        
        return answer;
    }
}