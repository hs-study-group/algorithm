//https://school.programmers.co.kr/learn/courses/30/lessons/160586

import java.util.*;

class Solution {
    public int[] solution(String[] keymap, String[] targets) {
        int[] answer = new int[targets.length];
        
        //각각의 keymap에 대하여, 특정 문자가 처음 나타날때의 인덱스를 저장하는 해시맵들을 저장할 어레이리스트 선언
        ArrayList<HashMap<Character,Integer>> keys = new ArrayList<HashMap<Character,Integer>>();
        
        //어레이리스트에 해시맵을 추가함
        for(int i=0;i<keymap.length;i++){
            keys.add(new HashMap<Character,Integer>());
        }
        
        //각각의 keymap에 대하여 특정 문자들이 처음 나타날때의 인덱스를 저장함
        for(int i=0; i<keymap.length; i++){
            char[] arr = keymap[i].toCharArray();
            for(int j=0; j<arr.length; j++){
                if(!keys.get(i).containsKey(arr[j])){
                    keys.get(i).put(arr[j],j+1);
                }
            }
        }
        
        //각각의 타겟 문자열에 대하여
        for(int i=0;i<targets.length;i++){
        	//처리를 편하게 하기 위해 문자 배열로 변환함
            char[] temp = targets[i].toCharArray();
            for(int j=0;j<temp.length;j++){
            	//그 문자에 대하여 가장 작은 인덱스를 저장할 변수
                int idx = Integer.MAX_VALUE;
                //각각의 자판에 대하여
                for(int k=0; k<keys.size();k++){
                	//그 문자를 만들기 위한 최소한의 횟수를 누적하여 구함
                    if(keys.get(k).containsKey(temp[j])){
                    	idx = Math.min(idx,keys.get(k).get(temp[j]));
                    }
                }
                
                //만약 어떤 자판으로 해당 문자를 최소한의 횟수로 만들 수 있다면
                if(idx!=Integer.MAX_VALUE){
                	//그 횟수를 정답배열에 누적하여 더함
                    answer[i] += idx;
                //만약 모든 자판을 사용해도 해당 문자를 만들 수 없다면
                }else{
                	//-1로 갱신하고 반복을 종료함
                    answer[i] = -1;
                    break;
                }
            }
        }
        
        return answer;
    }
}