//https://school.programmers.co.kr/learn/courses/30/lessons/135807

class Solution {
	//두 정수 A, B에 대하여 최대공약수를 계산하는 메소드
    public static int GCD(int A, int B){
        if(B==0){
            return A;
        }else{
            return GCD(B,A%B);
        }
    }
    
    public int solution(int[] arrayA, int[] arrayB) {
    	//각 배열의 가장 첫번째 수를 최대 공약수로 가정함
        int gcd1 = arrayA[0];
        int gcd2 = arrayB[0];
        
        //각 배열의 최대공약수와 다음 정수간의 최대 공약수를 누적하여 구함
        //최종적으로 해당 배열에 포함된 모든 정수들이 공통으로 가진 가장 큰 약수가 반환됨
        for(int i=0; i<arrayA.length; i++){
            gcd1 = GCD(gcd1,arrayA[i]);
            gcd2 = GCD(gcd2,arrayB[i]);
        }
        
        //gcd1은 배열 A를 모두 나눌 수 있는 가장 큰 정수이며
        //그것으로 배열 B를 하나라도 나눌 수 있다면 0으로 처리해야함
        for(int i=0; i<arrayB.length; i++){
            if(arrayB[i]%gcd1==0){
                gcd1 = 0;
                break;
            }
        }
        
        //gcd2은 배열 B를 모두 나눌 수 있는 가장 큰 정수이며
        //그것으로 배열 A를 하나라도 나눌 수 있다면 0으로 처리해야함
        for(int i=0; i<arrayA.length; i++){
            if(arrayA[i]%gcd2==0){
                gcd2 = 0;
                break;
            }
        }
        
        //두 최대공약수중 더 큰 값을 정답으로 처리함
        return Math.max(gcd1,gcd2);
    }
}