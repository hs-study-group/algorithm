//https://school.programmers.co.kr/learn/courses/30/lessons/1845

import java.util.*;

class Solution {
    public int solution(int[] nums) {
    	final int MAX_SIZE = nums.length/2;
        
        //중복되지 않게 번호를 저장하도록 하는 해쉬셋
        HashSet<Integer> set = new HashSet<Integer>();
        
        //모든 번호를 셋에 추가함
        //중복이 허용되지않으므로 번호마다 한 번씩만 저장됨
        for(int i=0; i<nums.length; i++){
            set.add(nums[i]);
        }
        
        //해쉬셋의 크기와 최대로 선택할 수 있는 포켓몬의 종류 개수중
        //더 작은 값을 결과값으로 리턴함        
        return Math.min(MAX_SIZE, set.size());
    }
}