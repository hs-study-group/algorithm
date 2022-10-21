import java.util.*;

class Solution {
	//카드가 담긴 상자를 연속해서 선택하고 열어보는 메소드
	//열어본 상자의 총 개수를 리턴함
    public static int DFS(int[] cards, boolean[] v, int n, int c){
    	//만약 아직 열어보지 못한 상자라면
        if(!v[n]){
        	//해당 상자를 열어본 것으로 처리함
            v[n] = true;
            
            //재귀적으로 다음 상자를 열어봄
            return DFS(cards,v,cards[n]-1,c+1);
            
        //해당 상자를 열어본 적이 있다면
        }else{
        	//더이상 다른 상자를 열 수 없으므로 여태까지 열어본 상자의 총 개수를 리턴함
            return c;
        }
    }
    
    public int solution(int[] cards) {
        int answer = 0;
        
        //그룹의 크기를 저장하는 우선순위 큐, 규모가 큰 그룹의 크기가 먼저 반환 됨
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
            @Override
            public int compare(Integer n1, Integer n2){
                if(n1>n2){
                    return -1;
                }else if(n1<n2){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //해당 상자를 열어본 적이 있는지 없는지 그 사실을 기록할 방문배열 선언
        boolean[] v = new boolean[cards.length];
        
        for(int i=0; i<cards.length; i++){
        	//해당 상자를 열어본 적이 없다면
            if(!v[i]){
            	//해당 상자부터 연속적으로 상자를 열어보고 그 그룹의 크기를 저장함
                int r = DFS(cards, v, cards[i]-1, 0);
                
                //해당 그룹의 크기를 우선순위 큐에 추가함
                pq.add(r);
            }
        }
        
        if(pq.size()>=2){
        	//가장 규모가 큰 두 그룹의 크기를 곱한 결과를 리턴함
            return pq.poll() * pq.poll();
        //그룹이 하나 또는 0개 생성되는 경우
        }else{
        	//두 수의 곱은 X*0 또는 0*0이므로 0을 리턴함
            return 0;
        }
    }
}