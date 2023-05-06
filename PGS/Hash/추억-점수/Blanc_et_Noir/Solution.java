//https://school.programmers.co.kr/learn/courses/30/lessons/176963

import java.util.*;

class Solution {
    public int[] solution(String[] name, int[] yearning, String[][] photo) {
        int[] answer = new int[photo.length];
        
        //사람들의 추억점수를 저장할 해쉬맵
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        
        //각 사람들의 추억점수를 해쉬 맵에 저장함
        for(int i=0; i<name.length; i++){
            hm.put(name[i],yearning[i]);
        }
        
        for(int i=0;i<photo.length;i++){
            int sum = 0;
            for(int j=0;j<photo[i].length;j++){
            	//만약 추억점수 정보가 있는 사람이라면 그 점수를 더하고, 아니라면 0을 더함
                sum += hm.containsKey(photo[i][j])?hm.get(photo[i][j]):0;
            }
            //최종 추억점수를 배열에 저장함
            answer[i] = sum;
        }
        
        return answer;
    }
}