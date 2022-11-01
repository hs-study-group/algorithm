//https://school.programmers.co.kr/learn/courses/30/lessons/12904

class Solution{
	//시작 위치 s, e에 대하여 가장 긴 팰린드롬의 길이를 얻는 메소드
    public static int getMaxLength(char[] arr, int s, int e){
    	//최소 길이는 시작위치에 해당되는 두 문자의 개수가 길이가 됨
    	//(s와 e가 동일하다면 홀수 길이의 팰린드롬)
    	//(s와 e가 다르다면 짝수 길이의 팰린드롬)
        int cnt = (e-s+1);
        
        //시작위치에 대하여 양 옆으로 1개씩 문자를 탐색함
        for(int i=1; s-i>=0&&e+i<arr.length; i++){
        	//양 옆의 문자가 서로 동일한 문자가 아니라면
            if(arr[s-i]!=arr[e+i]){
            	//더이상 팰린드롬이 아니므로 바로 그 이전까지를 팰린드롬으로 처리함 
                break;
            }
            
            //서로 동일한 문자였다면 팰린드롬의 길이를 2 증가시킴
            cnt += 2;
        }
        return cnt;
    }
    
    public int solution(String s){
    	//기본적으로 하나의 문자가 곧 팰린드롬인 것으로 취급할 수 있으므로 최소 1의 길이를 가짐
        int answer = 1;
        
        //원활한 처리를 위해 문자열을 문자 배열로 변환함
        char[] arr = s.toCharArray();
        
        for(int i=0; i<arr.length; i++){
        	//i번째 문자에 대하여 홀수 팰린드롬의 최대 길이를 구함
            answer = Math.max(answer,getMaxLength(arr,i,i));
            
            //i번째 문자와 i+1번째 문자가 동일하다면, 짝수 팰린드롬의 최대 길이도 구함
            if(i+1<arr.length&&arr[i]==arr[i+1]){
                answer = Math.max(answer,getMaxLength(arr,i,i+1));
            }
        }
        
        //최대 팰린드롬의 길이를 리턴함
        return answer;
    }
}