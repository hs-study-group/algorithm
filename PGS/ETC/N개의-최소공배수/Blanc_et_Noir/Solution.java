//https://school.programmers.co.kr/learn/courses/30/lessons/12953

import java.util.*;

class Solution {
	//두 정수 A, B에 대하여, 최대 공약수를 구하는 GCD 알고리즘
    public static int gcd(int a, int b){
        if(b==0)return a;
        return gcd(b,a%b);
    }
    
    public int solution(int[] arr) {
        int answer = arr[0];
        
        for(int i=1; i<arr.length; i++){
        	//인접한 두 정수에 대하여 최대 공약수를 계산함
            int temp = gcd(answer,arr[i]);
            //두 정수의 곱을 최대 공약수로 나누면 최소공배수가 됨
            //이때 그 최소 공배수에 대하여 다음 정수와의 GCD값을 계산하고
            //다시 최소 공배수를 누적해서 계산하면 N개의 최소 공배수를 계산할 수 있음
            answer = answer*arr[i]/temp;
        }
        return answer;
    }
}