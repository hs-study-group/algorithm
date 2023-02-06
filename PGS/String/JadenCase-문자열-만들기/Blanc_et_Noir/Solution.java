//https://school.programmers.co.kr/learn/courses/30/lessons/12951

import java.util.*;

class Solution {
    public String solution(String s) {
        String answer = "";
        
        //문자열을 문자배열로 변환
        char[] arr = (s).toCharArray();
        
        //대소문자 변환에 필요한 상수값을 구함
        final int TOL = 'a' - 'A';
        
        //flag가 true일 경우 해당 문자가 알파벳이면 대문자로 변경함
        boolean flag = true;
        
        StringBuilder buffer = new StringBuilder();
        
        for(int i=0; i<arr.length; i++){
        	//공백문자라면 flag를 true로 설정
            if(arr[i]==' '){
                flag = true;
                buffer.append(" ");
            //공백문자가 아니라면
            }else{
                String temp;
                //소문자로 변경해야 한다면
                if(!flag){
                	//대문자 알파벳인 경우에는
                    if(arr[i]>='A'&&arr[i]<='Z'){
                    	//소문자로 변경함
                        temp = (char)(arr[i]+TOL)+"";
                    //소문자 또는 숫자인 경우에는
                    }else{
                    	//그대로 사용함
                        temp = arr[i]+"";
                    }
                    //임시 변환 결과에 저장함
                    buffer.append(temp);
                //대문자로 변경해야 한다면
                }else{
                	//소문자 알파벳인 경우에는
                    if(arr[i]>='a'&&arr[i]<='z'){
                    	//대문자로 변경함
                        temp = (char)(arr[i]-TOL)+"";
                    //대문자 또는 숫자인 경우에는
                    }else{
                    	//그대로 사용함
                        temp = arr[i]+"";
                    }
                    //다음 문자부터는 소문자로 사용하도록 flag 설정
                    flag = false;
                    //임시 변환 결과에 저장함
                    buffer.append(temp);
                }
            }
        }
        
        //변환 결과를 문자열로 변환함
        answer = buffer.toString();
        return answer;
    }
}