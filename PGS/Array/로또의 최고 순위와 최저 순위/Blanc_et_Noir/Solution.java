import java.util.HashMap;

class Solution {
	//맞춘 번호의 수를 입력받아 등수를 리턴하는 메소드
    public static int rank(int n){
        return n > 0 ? 7 - n : 6;
    }
    
    public int[] solution(int[] lottos, int[] win_nums) {
        int[] answer = new int[2];
        HashMap<Integer, Boolean> hm = new HashMap<Integer,Boolean>();
        
        //당첨 번호들을 해시맵에 추가함
        for(int n : win_nums){
            hm.put(n,true);
        }
        
        //각각 최고, 최악일때의 맞춘 번호의 수를 저장함
        int max = 0;
        int min = 0;
        
        //내가 선택한 번호들을 탐색함
        for(int n : lottos){
        	//선택한 번호를 잊어버린 것이라면
            if(n==0){
            	//그것이 당첨에 포함될 수도 있으므로 최대값을 증가시킴
                max++;
            //선택한 번호가 당첨번호에 포함된다면
            }else if(hm.containsKey(n)){
            	//최악, 최고의 경우 모두 증가시킴
                max++;
                min++;
            }
        }
        
        //최고로 잘 맞춘 횟수, 최악으로 맞춘 횟수를 이용해 등수를 계산함
        answer[0] = rank(max);
        answer[1] = rank(min);
        
        return answer;
    }
}