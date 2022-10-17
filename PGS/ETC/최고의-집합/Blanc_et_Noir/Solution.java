//https://school.programmers.co.kr/learn/courses/30/lessons/12938

class Solution {
    public int[] solution(int n, int s) {        
        int[] answer = new int[n];
        
        //A가 0이상의 정수일 때
        //X^2과 (X-A)(X+A)는 모두 각 항의 합이 2X로 동일하지만
        //X^2과 X^2-A^2중에서 X^2의 값이 더 크거나 같음
        
        //왜냐하면 A는 0이상의 허수가 아닌 정수이므로 제곱하면 당연히 양수가 나오며
        //X^2-A^2의 결과는 당연히 아무리 커도 X^2보다 작거나 같을 수 밖에 없기 때문임
        
        //즉, 곱하고자 하는 항들간의 크기 차이가 최소가 되어야
        //그 숫자들의 곱의 결과가 최대가 될 것임
        
        //따라서 주어진 합 s에 대하여 각각 동일한 몫을 챙기고
        //남은 나머지들은 1씩 분배를 함으로써 그 차이를 최소로 만들면 됨
        
        //s를 n으로 나눈 몫과 나머지를 계산함
        int Q = s/n;
        int R = s%n;
        
        //자연수는 최소 1의 값을 가지므로, s가 1*n보다 작다면
        //이는 자연수들의 합으로는 표현할 수 없음
        if(n>s){
            return new int[]{-1};
        //자연수 n개의 합으로 s를 표현할 수 있는 경우
        }else{
        	//정답을 반환할 배열의 맨 끝부터 탐색함
            for(int i=answer.length-1; i>=0; i--){
            	//남은 나머지가 0이 아니라면
                if(R!=0){
                	//해당 인덱스에는 몫 + 1값을 추가함
                    answer[i] = Q+1;
                    
                    //나머지를 1감소시킴
                    R--;
                //남은 나머지가 0이라면
                }else{
                	//해당 인덱스에는 몫의 크기만을 대입함
                    answer[i] = Q;
                }
            }
            return answer;
        }
    }
}