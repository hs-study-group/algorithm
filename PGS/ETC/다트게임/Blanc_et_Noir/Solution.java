class Solution {
    public int solution(String dartResult) {
        int answer = 0;
        int idx = -1;
        int[] temp = new int[3];
        
        //문자열로 주어진 다트게임 결과를 문자 배열로 변환하여 처리를 용이하게함
        char[] arr = dartResult.toCharArray();
        
        for(int i=0; i<arr.length; i++){
        	//만약 해당 위치의 문자가 숫자라면
            if(arr[i]>='0'&&arr[i]<='9'){
            	//그 숫자가 10이 아니라면
                if(arr[i+1]!='0'){
                	//결과값에 해당 숫자를 정수로 변환하여 저장함
                    temp[++idx] = arr[i]-'0';
                //그 숫자가 10이라면
                }else{
                	//결과값에 10을 저장하고, i를 하나 더 증가시켜 다음 문자를 탐색함
                    temp[++idx] = 10;
                    i++;
                }
            //만약 S라면 해당 값을 1제곱하여 추가함
            }else if(arr[i]=='S'){
                temp[idx]=temp[idx];
            //만약 D라면 해당 값을 2제곱하여 추가함
            }else if(arr[i]=='D'){
                temp[idx]=temp[idx]*temp[idx];
            //만약 T라면 해당 값을 3제곱하여 추가함
            }else if(arr[i]=='T'){
                temp[idx]=temp[idx]*temp[idx]*temp[idx];
            //만약 스타라면
            }else if(arr[i]=='*'){
            	//첫번째 기회에서 스타를 얻은 것이면
                if(idx==0){
                	//그냥 자기 자신만 값을 2배로함
                    temp[idx] = temp[idx]*2;
                //두, 세번째 기회에서 얻은 것이라면
                }else if(idx>=1){
                	//자기 자신과 이전의 값을 2배로 함
                    temp[idx-1] = temp[idx-1]*2;
                    temp[idx] = temp[idx]*2;
                }
            //아차상이라면 해당 점수를 -로 변환함
            }else{
                temp[idx] = temp[idx]*(-1);
            }
        }
        //결과를 반환함
        answer = temp[0]+temp[1]+temp[2];
        return answer;
    }
}