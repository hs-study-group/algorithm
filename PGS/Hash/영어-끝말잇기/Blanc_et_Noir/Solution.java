//https://school.programmers.co.kr/learn/courses/30/lessons/12981

import java.util.*;

class Solution {
    public int[] solution(int n, String[] words) {
        int[] answer = {};
        HashMap<String,Boolean> hm = new HashMap<String,Boolean>();
        
        for(int i=0; i<words.length; i++){
        	
            int turn = i % n;
            int cnt = i / n;
            
            if(hm.containsKey(words[i])){
                return new int[]{turn+1,cnt+1};
            }else if(i>0&&words[i-1].charAt(words[i-1].length()-1)!=words[i].charAt(0)){
                return new int[]{turn+1,cnt+1};
            }else{
                hm.put(words[i],true);
            }
        }

        return new int[]{0,0};
    }
}