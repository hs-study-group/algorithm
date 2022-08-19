//https://school.programmers.co.kr/learn/courses/30/lessons/86491

class Solution {
    public int solution(int[][] sizes) {
    	//모든 명함에 대하여 최대 너비, 최대 높이를 저장할 변수
        int mw = 0, mh = 0;
        
        //모든 명함을 탐색하면서
        for(int i=0; i<sizes.length; i++){
        	//어떤 명함에 대해 너비 > 높이라면
            if(sizes[i][0]>sizes[i][1]){
            	//최대 너비보다 현재 명함의 너비가 크다면 갱신
                if(mw < sizes[i][0]) mw = sizes[i][0];
                //최대 높이보다 현재 명함의 높이가 크다면 갱신
                if(mh < sizes[i][1]) mh = sizes[i][1];
            //어떤 명함에 대해 너비 < 높이라면
            }else{
            	//마치 명함을 90도 돌려서 너비와 높이를 변경한 것 처럼 처리
            	//최대 너비보다 현재 명함의 너비가 크다면 갱신
                if(mw < sizes[i][1]) mw = sizes[i][1];
              //최대 높이보다 현재 명함의 높이가 크다면 갱신
                if(mh < sizes[i][0]) mh = sizes[i][0];
            }
        }
        return mw * mh;
    }
}