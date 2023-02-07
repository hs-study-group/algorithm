//https://school.programmers.co.kr/learn/courses/30/lessons/131128

import java.util.*;

class Solution {
    public String solution(String X, String Y) {
        StringBuilder sb = new StringBuilder();
        
        //각 문자열들이 구성하고 있는 숫자들의 개수를 저장할 배열
        int[] arr1 = new int[10];
        int[] arr2 = new int[10];
        
        //해당 문자열을 구성하는 숫자의 종류별 개수를 측정함
        for(char ch : X.toCharArray()){
            arr1[ch-'0']++;
        }
        
        //해당 문자열을 구성하는 숫자의 종류별 개수를 측정함
        for(char ch : Y.toCharArray()){
            arr2[ch-'0']++;
        }
        
        //9부터 0까지 숫자를 탐색함
        for(int i=9; i>=0; i--){
        	//두 문자열을 구성하고 있는 각각의 숫자 종류에 대해서
        	//더 적은 개수를 기준으로 최종 문자열에 덧붙임
            for(int j=0; j<Math.min(arr1[i],arr2[i]); j++){
                sb.append(i);
            }
        }
        
        //빈 문자열이라면 각 문자열들이 갖고있는 숫자들이 중복되는 경우가 전혀 없음
        if(sb.toString().equals("")){
            return "-1";
        //결과 문자열이 0으로 시작한다면 0말고는 다른 숫자는 중복되는 경우가 없는 것임
        }else if(sb.toString().startsWith("0")){
            return "0";
        //결과 문자열을 리턴함
        }else{
            return sb.toString();
        }        
    }
}