import java.util.ArrayList;
import java.util.Stack;

class Solution {
	//배포까지 남은 기간을 임시로 저장할 스택
    public static Stack<Integer> s = new Stack<Integer>();
    
    public int[] solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] answer = {};
        int cnt=0;
        
        for(int i=0; i<progresses.length; i++){
        	
        	//남은 진행도를 스피드로 나누어 소수점에서 올림처리를 하면, 배포까지 남은 일수를 계산할 수 있음
            int d = (int) Math.ceil((100 - progresses[i])*1.0/speeds[i]);
            
            //스택이 비어있지 않다면
            if(!s.isEmpty()){
            	//만약 기존 작업이 더 빨리 끝난다면
                if(s.peek()<d){
                	//기존작업은 기존 작업대로 배포를 마치고, 차후의 작업은 따로 배포해야함
                	//따라서 기존 작업을 스택에서 제거하고, 새 작업의 남은 기간을 추가함
                    s.pop();
                    s.push(d);
                    
                    //여태까지 한꺼번에 배포가능한 기능의 수를 카운트 한 값을 저장함
                    list.add(cnt);
                    
                    //카운트를 초기화함
                    cnt=1;
                    
                //기존 작업이 더 늦게 끝난다면
                }else{
                	//차후의 작업은 기존 작업과 같이 배포가능함
                    cnt++;
                }
            //스택이 비어있다면
            }else{
            	//가장 앞에 위치한 기능의 남은 기간을 스택에 추가함
                s.push(d);
                
                //배포될 기능의 수를 초기화함
                cnt=1;
            }
        }
        
        list.add(cnt);
        
        answer = new int[list.size()];
        
        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}