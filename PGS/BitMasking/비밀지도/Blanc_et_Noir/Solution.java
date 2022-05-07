//https://programmers.co.kr/learn/courses/30/lessons/17681

class Solution {
	
	//숫자 한 변의 길이 n, 두 지도의 한 행을 or한 결과인 b를 전달받아
	//그에 따라 지도를 렌더링한 결과를 리턴하는 메소드
    public String func(int n, int b){
        String r = "";
        int m = 1;
        
        //MSB부터 LSB까지 차례로 비트를 탐색하며
        //그에 따라 벽 또는 공백을 렌더링함
        for(int i=n-1; i>=0; i--){
            if((b&(m<<i))!=0){
                r+="#";
            }else{
                r+=" ";
            }
        }
        return r;
    }
    
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        int[] arr3 = new int[n];
        
        //두 지도에 대해 or연산을 수행함
        //두 지도중 최소 어느 하나에 벽이라고 표시되어있으면 1
        //두 지도 모두 공백인 0을나타나면 최종 지도는 공백인 0이기 때문
        for(int i=0; i<n; i++){
            arr3[i] = arr1[i]|arr2[i];
            answer[i] = func(n,arr3[i]);
        }

        return answer;
    }
}