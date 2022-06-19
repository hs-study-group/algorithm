class Solution {
    public String solution(String s) {
        String answer = "";
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        //문자열을 공백을 기준으로 분리함
        String[] str = s.split(" ");
        
        //각 문자열에 대하여
        for(String strnum : str){
        	//해당 문자열을 정수로 변환함
            int num = Integer.parseInt(strnum);
            //여태까지의 최솟값보다 해당 값이 작다면 해당 값을 최소값으로 초기화
            if(min>num){
                min = num;
            }
            //여태까지의 최댓값보다 해당 값이 크다면 해당 값을 최대값으로 초기화
            if(max<num){
                max = num;
            }
        }
        
        //최소값과 최대값을 문자열로 변환하여 리턴함
        answer = Integer.toString(min)+" "+Integer.toString(max);
        return answer;
    }
}