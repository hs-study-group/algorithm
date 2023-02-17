//https://school.programmers.co.kr/learn/courses/30/lessons/152996

import java.util.*;

class Solution {
	//인덱스 l부터 배열의 끝까지의 구간에서 가중치를 w만큼을 곱한 값이 v와 동일하거나
	//그보다 큰 정수가 처음 나타나는 때의 인덱스를 반환하는 메소드	
    public int lowerbound(int[] arr, int l, int v, int w){
        int r = arr.length;
        
        //l값이 r보다 작을때 반복 수행
        while(l<r){
        	//중간 인덱스를 구함
            int m = (l+r)/2;
            
            //만약 중간 인덱스에 해당하는 값을 w만큼 곱했을때의 값이 찾고자하는 v값보다 크거나 같다면
            if(arr[m]*w>=v){
            	//lowerbound값 중에서 그보다 더 작은 인덱스가 존재할 수 있으므로 r값을 줄여봄
                r = m;
            //만약 중간 인덱스에 해당하는 값을 w만큼 곱했을때의 값이 찾고자하는 v값보다 작다면
            }else{
            	//lowerbound값은 인덱스 m보다 우측에 있으므로 l값을 늘려봄
                l = m + 1;
            }
        }
        
        //찾지 못했다면 r값은 arr.length와 같으며, 찾았다면 그 인덱스를 반환
        return r;
    }
    
    //인덱스 l부터 배열의 끝까지의 구간에서 가중치를 w만큼을 곱한 값이
  	//그보다 큰 정수가 처음 나타나는 때의 인덱스를 반환하는 메소드	
    public int upperbound(int[] arr, int l, int v, int w){
        int r = arr.length;
        
        //l값이 r보다 작을때 반복 수행
        while(l<r){
        	//중간 인덱스를 구함
            int m = (l+r)/2;
            
            //만약 중간 인덱스에 해당하는 값을 w만큼 곱했을때의 값이 찾고자하는 v값보다 크다면
            if(arr[m]*w>v){
            	//upperbound값 중에서 그보다 더 작은 인덱스가 존재할 수 있으므로 r값을 줄여봄
                r = m;
            //만약 중간 인덱스에 해당하는 값을 w만큼 곱했을때의 값이 찾고자하는 v값보다 작거나 같다면
            }else{
            	//upperbound값은 인덱스 m보다 우측에 있으므로 l값을 늘려봄
                l = m + 1;
            }
        }
        
        //찾지 못했다면 r값은 arr.length와 같으며, 찾았다면 그 인덱스를 반환
        return r;
    }
    
    public long solution(int[] weights) {
        long answer = 0;
        
        //2:2, 2:3, 2:4, 3:2, 3:3, 3:4, 4:2, 4:3, 4:4와 같은 비율이 존재할 수 있으나
        //약분했을때 중복되는 비율을 제거해보면 1:1, 1:2, 2:1, 1:3, 3:1, 1:4, 4:1의 비율로 줄일 수 있음
        //그러나 몸무게를 오름차순 정렬한다면, 기준이 될 몸무게는 반드시 자신보다 오른쪽에 존재하는 몸무게보다는 작거나 같음이 보장되므로
        //1:2, 1:3, 1:4 비율은 전혀 고려할 필요가 없음, 따라서 1:1, 2:1, 3:1, 4:1 비율만 고려하면 됨
        int[][] dist = new int[][]{
            {1,1},{2,1},{3,2},{4,3}
        };
        
        //몸무게를 오름차순 정렬함
        Arrays.sort(weights);
        
        for(int i=0; i<weights.length-1; i++){
            for(int j=0; j<dist.length; j++){
            	//자신의 몸무게에 가중치를 곱함
                int me = weights[i]*dist[j][0];
                
                //자신보다 오른쪽에 있는 수들에 대하여 dist[i][j]만큼의 가중치를 곱했을때
                //자기 자신과 동일한 값이 몇개나 존재하는지를 확인함
                answer += upperbound(weights,i+1,me,dist[j][1]) - lowerbound(weights,i+1,me,dist[j][1]);
                
            }
        }
        
        return answer;
    }
}