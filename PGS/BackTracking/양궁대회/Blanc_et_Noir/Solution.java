import java.util.*;

class Solution {
    public static int gap;
    public static int[] answer;
    
    public static void copy(int[] result){
        answer = new int[11];
        for(int i=0; i<result.length; i++){
            answer[i] = result[i];
        }
    }
    
    public static void backtracking(int[] info, int[] result,int idx, int n, int sum, int goal){
    	//만약 라이언이 1점을 쏘는 경우의 수까지 모두 탐색 했다면
        if(idx>=info.length){
            result[10]=0;
            
            //라이언의 점수가 어피치보다 높다면
            if(sum>goal){
            	//라이언과 어피치의 점수차가 기존에 기록했던 점수차보다 더 크다면
                if((sum-goal)>gap){
                	//만약 화살을 아직 다 쏘지 않았다면
                    if(n>0){
                    	//0점에 남은 화살을 모두 쏴야만 함
                        result[10] = n;
                    }
                    
                    //result배열에 저장된 라이언의 화살 상태를 정답배열에 복사함
                    copy(result);
                    
                    //점수차이의 최대값을 라이언과 어피치의 현재 점수차이로 변경함
                    gap = sum - goal;
                    return;
                //만약 라이언과 어피치의 점수차가 기존에 기록했던 점수차이와 동일하다면
                }else if((sum-goal)==gap){
                	//만약 화살을 아직 다 쏘지 않았다면
                    if(n>0){
                    	//0점에 남은 화살을 모두 쏴야만 함
                        result[10] = n;
                    }
                    //적은 점수대를 더 많이 맞춘 사람이 맞췄을 때를 정답으로 갱신해야함
                    //따라서, 기존에 기록했던 라이언의 화살 상태와 현재의 화살 상태를 0점부터 10점까지 맞춘 횟수를 비교함
                    for(int i=10; i>=0; i--){
                    	//만약 정답배열이 현재배열보다 더 적은 점수대를 더 많이 맞췄다면
                        if(answer[i]>result[i]){
                        	//정답배열을 그대로 둠
                            return;
                        //만약 정답배열보다 현재배열이 더 적은 점수대를 더 많이 맞췄다면
                        }else if(answer[i]<result[i]){
                        	//현재배열을 정답배열에 복사함
                            copy(result);
                            return;
                        //둘 다 같은 점수대를 동일하게 맞췄다면
                        }else{
                        	//다음 점수대를 탐색함
                            continue;
                        }
                    }
                }
            }
            return;
        }else{
        	//만약 어피치가 맞춘 점수대에서 라이언이 그보다 한 발 더 맞춰서 이길 수 있다면
        	//즉, 아직 화살의 수량에 여유가 있다면
            if(info[idx]<n){
            	//해당 점수대는 어피치보다 단 한발만 더 맞추면 점수를 얻을 수 있음
                result[idx]=info[idx]+1;
                
                //만약 해당 점수대에 실제로 어피치가 화살을 쏜 적이 있다면
                if(info[idx]>0){
                	//어피치의 점수를 해당 점수대만큼 감소시키고 백트래킹을 진행함
                    backtracking(info,result,idx+1,n-(info[idx]+1),sum + (10-idx), goal-(10-idx));
                }else{
                	//어피치의 점수를 유지시키고 백트래킹을 진행함
                    backtracking(info,result,idx+1,n-(info[idx]+1),sum + (10-idx), goal);
                }
                
                //재귀호출 이후에는 마치 해당 점수대에 라이언이 화살을 쏜 적 없는 것처럼 처리함
                result[idx]=0;
            }
            
            //화살 보유량에 상관없이 이번에 탐색하는 점수대를 그냥 무시하고 다음 점수대로 탐색함
            backtracking(info,result,idx+1,n,sum, goal);
            return;
        }
    }
    public int[] solution(int n, int[] info) {
        
        int goal = 0;
        
        //어피치와 라이언의 점수 차이를 MIN_VALUE로 초기화 함
        gap = Integer.MIN_VALUE;
        
        //현재 어피치가 받을 수 있는 점수를 계산함
        for(int i=0; i<info.length; i++){
            if(info[i]!=0){
                goal += (10-i);
            }
        }
        
        //현재 어피치가 쏜 화살의 상태를 바탕으로, 라이언이 어피치를 가장 큰 점수차이로 이기는 방법을 백트래킹을 통해 구함
        backtracking(info,new int[11],0,n,0,goal);
        
        //만약 정답 배열이 null이 아니라면
        if(answer!=null){
        	//그것이 정답임
            return answer;
        //만약 정답 배열이 null이라면 라이언이 어피치를 이길 수 있는 경우가 없으므로 -1을 리턴함
        }else{
            return new int[]{-1};
        }
    }
}