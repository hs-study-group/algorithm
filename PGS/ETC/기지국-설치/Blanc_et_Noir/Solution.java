//https://school.programmers.co.kr/learn/courses/30/lessons/12979

class Solution {
	//두 스테이션 s1, s2사이의 거리를 구하는 메소드
    public int getDistance(int s1, int s2, int w){
        if(s1>s2){
            int t = s1;
            s1 = s2;
            s2 = t;
        }
        
        return (s2-w)-(s1+w)-1;
    }
    
    //거리 l와 너비 w를 전달받아 몇개의 기지국을 세워야 하는지를 계산하는 메소드
    public int diploy(int l, int w){
        int q = l/(2*w+1);
        int r = l%(2*w+1);
        
        //거리가 음수라면 기지국을 세울 필요가 없음
        if(l<0){
            return 0;
        }
        
        //만약 거리를 2*w+1로 나누었을때, 나머지가 0이라면 q개의 기지국만 설치하면 되며
        //2*w+1로 나누었을때 나머지가 0이 아니라면 q+1개의 기지국을 설치해야함
        return r==0?q:q+1;
    }
    
    public int solution(int n, int[] stations, int w) {
        int answer = 0;

        //만약 가장 처음 설치된 기지국이 w+1보다 더 먼 위치에 설치되어 있다면 마치 -w위치에 기지국이 설치되어있다고 가정하고 설치해야할 기지국 개수를 구함
        answer += stations[0]>w+1?diploy(getDistance(-w,stations[0],w),w):0;
        
        //만약 가장 나중에 설치된 기지국이 n-w보다 더 이전 위치에 설치되어 있다면 마치 n+w+1위치에 기지국이 설치되어 있다고 가정하고 설치해야할 기지국 개수를 구함
        answer += stations[stations.length-1]<n-w?diploy(getDistance(stations[stations.length-1],n+w+1,w),w):0;
        
        //기존에 설치된 기지국 사이에 설치해야할 새로운 기지국의 수를 구함
        for(int i=0; i<stations.length-1; i++){
            answer+=diploy(getDistance(stations[i],stations[i+1],w),w);
        }
        
        return answer;
    }
}