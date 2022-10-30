//https://school.programmers.co.kr/learn/courses/30/lessons/67258

import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
    	//현재 구간에 포함된 보석의 종류와 그 수를 저장할 해시맵 선언
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        
        //보석의 종류를 구하기 위해 해시맵에 추가함
        for(int i=0; i<gems.length; i++){
            hm.put(gems[i],1);
        }
        
        int[] answer = {1,gems.length};
        
        //l, r은 포인터, c는 현재 탐색중인 구간의 보석 종류의 수, d는 모든 보석의 종류의 수임
        int l = 0, r = 0, c=1, d = hm.size();
        
        //해시맵을 초기화 함
        hm = new HashMap<String,Integer>();
        //현재 포인터는 0:0위치의 보석 하나를 가리키고 있으므로
        //현재 구간에 0번 보석을 가지고 있음을 나타냄
        hm.put(gems[0],1);
        
        while(l<gems.length&&r<gems.length){
        	//현재 구간에 모든 종류의 보석이 포함된 경우
            if(c==d){
            	//기존에 기록했던 구간의 길이보다 현재 탐색중인 구간의 길이가 더 작다면
                if(answer[1]-answer[0]>r-l){
                	//정답을 그 구간으로 갱신함
                    answer = new int[]{l+1,r+1};
                //기존에 기록했던 구간의 길이와 동일하다면
                }else if(answer[1]-answer[0]==r-l){
                	//현재 탐색중인 구간의 시작 위치가 더 앞서있는 경우
                    if(l<answer[0]){
                    	//그것을 정답 구간으로 갱신함
                        answer = new int[]{l+1,r+1}; 
                    }
                }
                
                //현재 좌측 포인터가 가리키는 종류의 보석이 2개이상 있다면
                if(hm.get(gems[l])>1){
                	//보석의 개수를 1개 제거함
                    hm.put(gems[l],hm.get(gems[l])-1);
                //현재 좌측 포인터가 가리키는 종류의 보석이 1개뿐이라면
                }else{
                	//현재 가진 보석의 정보를 저장하는 해시맵에서 해당 보석을 제거함
                    hm.remove(gems[l]);
                    //현재 구간의 보석의 종류를 1 감소시킴
                    c--;
                }
                
                //좌측포인터를 1 증가시킴
                l++;
            //우측 포인터가 범위를 벗어났으면
            }else if(r+1>=gems.length){
            	//그 즉시 탐색 종료
                break;
            //아직 모든 종류의 보석을 구매하지 못했다면
            }else if(c<d){
            	//우측 포인터의 다음위치가 가리키는 보석이 이미 구매하려는 보석에 포함된다면
                if(hm.containsKey(gems[r+1])){
                	//보석의 개수를 그냥 1증가시키기만 함
                    hm.put(gems[r+1],hm.get(gems[r+1])+1);
                //이미 구매하려는 보석에 포함되지 않는, 새로운 보석 종류라면
                }else{
                	//해당 보석의 개수를 1 증가시킴
                    hm.put(gems[r+1],1);
                    //현재 구매하려는 보석의 종류를 1 증가시킴
                    c++;
                }
                
                //우측포인터를 1 증가시킴
                r++;
            }
        }
        return answer;
    }
}