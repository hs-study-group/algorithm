//https://school.programmers.co.kr/learn/courses/30/lessons/17677

import java.util.*;

class Solution {
    public int solution(String str1, String str2) {
        //각 문자열들을 2글자씩 분리한 후의 결과를 저장하는 해시맵
        HashMap<String,Integer> hm1 = new HashMap<String,Integer>();
        HashMap<String,Integer> hm2 = new HashMap<String,Integer>();
        
        //합집합을 저장할 해시맵
        HashMap<String,Integer> hm3 = new HashMap<String,Integer>();
        
        //각각 교집합 및 합집합의 크기를 저장할 변수
        int A=0, B=0;
        
        //두 문자열을 모두 대문자로 변환함
        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();
        
        //문자열을 두 글자씩 분리하여 해시맵에 저장
        for(int i=0; i<str1.length()-1; i++){
            
            String k = str1.substring(i,i+2);
            
            char[] ch = k.toCharArray();
            
            //분리된 문자열에 만약 공백이나 특수문자가 포함되어있으면 저장하지 않음
            if(!(ch[0]>='A'&&ch[0]<='Z'&&ch[1]>='A'&&ch[1]<='Z')){
                continue;
            }
            
            //이미 이전에 저장했던 문자열이면 그 수를 1 증가시킴
            if(hm1.containsKey(k)){
                hm1.put(k,hm1.get(k)+1);
            //이전에 저장했던 문자열이 아니라면 그 수를 1로 초기화함
            }else{
                hm1.put(k,1);
            }
        }
        
        //문자열을 두 글자씩 분리하여 해시맵에 저장
        for(int i=0; i<str2.length()-1; i++){
            
            String k = str2.substring(i,i+2);
            
            char[] ch = k.toCharArray();
            
            //분리된 문자열에 만약 공백이나 특수문자가 포함되어있으면 저장하지 않음
            if(!(ch[0]>='A'&&ch[0]<='Z'&&ch[1]>='A'&&ch[1]<='Z')){
                continue;
            }
            
            //이미 이전에 저장했던 문자열이면 그 수를 1 증가시킴
            if(hm2.containsKey(k)){
                hm2.put(k,hm2.get(k)+1);
            //이전에 저장했던 문자열이 아니라면 그 수를 1로 초기화함
            }else{
                hm2.put(k,1);
            }
        }
        
        //교집합을 구함
        for(String k : hm1.keySet()){
            //합집합에도 미리 추가함
            hm3.put(k,hm1.get(k));
            
            //만약 두 집합에 같은 문자열이 있다면
            if(hm2.containsKey(k)){
                int n1 = hm1.get(k);
                int n2 = hm2.get(k);
                //두 집합중에 더 적은 숫자를 교집합의 수에 더함
                if(n1<n2){
                    A+=n1;
                }else{
                    A+=n2;
                }
            }else{
                continue;
            }
        }

        //합집합을 구함
        for(String k : hm2.keySet()){
            //두 집합에 모두 같은 문자열이 있다면 
            if(hm3.containsKey(k)){
                int n3 = hm3.get(k);
                int n2 = hm2.get(k);
                //더 큰 수를 합집합에 추가함
                if(n3>=n2){
                    continue;
                }else{
                    hm3.put(k,n2);
                }
            //첫번째 문자열에는 없고 두 번째 문자열에만 있는 문자열이라면
            //두 번째 문자열의 것으로 합집합을 초기화함
            }else{
                hm3.put(k,hm2.get(k));
            }
        }
        
        //합집합의 수를 구함
        for(String k : hm3.keySet()){
            B += hm3.get(k);
        }
        
        if(B==0){
            return 65536;
        }else{
            return (int) Math.floor(65536*(A*1.0/B));
        }
    }
}