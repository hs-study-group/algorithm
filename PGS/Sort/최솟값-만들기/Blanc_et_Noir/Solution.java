import java.util.*;

class Solution{
    public int solution(int []A, int []B){
    	//두 배열 A, B에 대하여 모두 정렬을 수행
        Arrays.sort(A);
        Arrays.sort(B);
        
        int answer = 0;
        
        //A배열은 최솟값부터, B배열은 최댓값부터 꺼내어 서로 곱한후 그 결과를 누적함
        for(int i=0; i<A.length; i++){
            answer += A[i] * B[B.length-i-1];
        }
        
        //최종적으로 결과값은 최소가 됨
        return answer;
    }
}