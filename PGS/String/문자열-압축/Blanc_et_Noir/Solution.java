class Solution {
    public int solution(String s) {
        int answer = 0;
        int len = s.length();
        int div = len/2;
        
        //문자열 압축후의 길이는 최소한 원본 문자열의 길이보다는 작거나 같을 것임
        int min = len;
        
        for(int i=div; i>=1; i--){
        	//prev는 탐색의 기준이 되는 문자열
            String prev = s.substring(0,i);
            String result="";
            
            //문자열이 반복되는 횟수를 기록할 변수
            int cnt=1;
            
            for(int j=i; j+i<len+1; j=j+i){
            	
            	//next는 기준이되는 문자열 이후의 문자열들을 i만큼씩 간격을 두어 얻음
                String next = s.substring(j,j+i);
                
                //기준문자열과 일치한다면 반복된 횟수를 1 증가시킴
                if(prev.equals(next)){
                    cnt++;
                }else{
                	//만약 1번의 반복으로 끝났다면 굳이 압축한 횟수 1을 표기할 필요가 없음
                    if(cnt==1){
                        result += prev;
                    }else{
                        result += cnt + prev;
                    }
                    //기준이 되는 문자열을 갱신함
                    prev = next;
                    cnt=1;
                }
                
                //만약 i보다 크기가 작은 문자열, 즉 자투리 문자열이 남아있으면
                if(j+i*2>=len+1){
                    //기존까지 탐색했던 문자열과 자투리 문자열을 덧붙임
                    if(cnt==1){
                        result += next+s.substring(j+i,len);
                    }else{
                        result += cnt+next+s.substring(j+i,len);
                    }
                    
                    //그 문자열의 길이가 최소값보다 작으면 갱신함
                    if(min>result.length()){
                        min = result.length();
                    }
                    break;
                }
            }
        }
        answer = min;
        return answer;
    }
}