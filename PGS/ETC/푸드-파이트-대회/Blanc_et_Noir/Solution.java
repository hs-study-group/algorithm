//https://school.programmers.co.kr/learn/courses/30/lessons/134240

import java.util.*;

class Solution {
    public String solution(int[] food) {        
        StringBuilder sb = new StringBuilder();
        
        //음식의 칼로리가 더 적은 순으로 탐색함
        //0번째는 물이므로 굳이 탐색하지 않음
        for(int i=1; i<food.length; i++){
        	//해당 칼로리를 갖는 음식의 수를 2로 나눈 몫만큼을 공평하게 양 선수에게 할당하면 됨
            for(int j=0; j<food[i]/2; j++){
                sb.append(i);
            }
        }
        
        //왼쪽의 선수는 그대로, 오른쪽 선수는 역순으로 음식을 배치해주고, 가운데에 물을 추가함
        return sb.toString()+"0"+sb.reverse().toString();
    }
}