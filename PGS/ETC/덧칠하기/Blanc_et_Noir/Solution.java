//https://school.programmers.co.kr/learn/courses/30/lessons/161989

class Solution {
    public int solution(int n, int m, int[] section) {
        int answer = 0;
        
        //true면 새로 덧칠해야할 위치임을 의미함
        boolean[] arr = new boolean[n];
        
        //덧칠해야할 좌표를 true로 설정함
        for(int i=0;i<section.length;i++){
            arr[section[i]-1]=true;
        }
        
        for(int i=0;i<arr.length;i++){
        	//만약 해당 위치가 덧칠해야할 위치라면
            if(arr[i]){
            	//해당위치를 포함하여 m개의 위치를 모두 덧칠함
            	//i+j가 배열 범위를 벗어나지 않도록 조건을 하나 더 추가함
                for(int j=0;i+j<n&&j<m;j++){
                    arr[i+j]=false;
                }
                //덧칠한 횟수를 1증가시킴
                answer++;
            }
        }
        
        return answer;
    }
}