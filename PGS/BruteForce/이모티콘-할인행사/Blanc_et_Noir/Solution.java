//https://school.programmers.co.kr/learn/courses/30/lessons/150368

class Solution {
    public static int cnt;
    public static int sum;
    
    //m개의 이모티콘에 대해 모든 가능한 할인 경우의 수를 구함
    public void permutate(int[][] users,int[] emoticons, int[] discount, int idx){
    	//모든 경우의 수를 다 구했으면
        if(idx==discount.length){
        	//모든 사용자들이 이모티콘을 구매하거나, 이모티콘 플러스 서비스를 이용했을때의 구매 금액 총 합계
            int sum = 0;
            
            //이모티콘 플러스 서비스 가입자 수
            int cnt = 0;
            
            for(int i=0; i<users.length; i++){
                int temp = 0;
                int ratio = users[i][0];
                int amount = users[i][1];
                
                //사용자가 어떤 이모티콘을 구매할지 탐색함
                for(int j=0; j<discount.length; j++){
                	//만약 해당 사용자가 정했던 할인 퍼센티지 이상으로 할인을 하고 있는 이모티콘이라면
                    if(discount[j]>=ratio){
                    	//해당 이모티콘은 무조건 구매함
                        temp += (emoticons[j] * (100-discount[j])*0.01);
                    }
                }
                
                //만약 할인을 고려하여 구매한 이모티콘들의 금액 합계가 해당 사용자가 정했던 최대 금액 이상이라면
                if(temp >= amount){
                	//이모티콘을 직접 구매하기보다는 이모티콘 플러스 서비스를 이용하는게 좋음
                    cnt++;
                //만약 할인하여 이모티콘을 구매하면 최대 금액 미만으로 구매할 수 있는 경우
                }else{
                	//이모티콘 플러스 서비스를 이용하지 않고 그냥 직접 이모티콘을 구매함
                    sum += temp;
                }
            }
            
            //만약 이모티콘 플러스 서비스 가입자 수가 기존의 최대값보다 크다면
            if(cnt>this.cnt){
            	//그것을 최대값으로 갱신함
                this.cnt = cnt;
                
                //그때의 누적 금액 합계를 기록함
                this.sum = sum;
            //만약 이모티콘 플러스 서비스 가입자 수가 기존의 최대값과 동일하면서, 누적 금액 합계는 더 크다면
            }else if(cnt==this.cnt&&sum>this.sum){
            	//누적 금액 합계만 갱신함
                this.sum = sum;
            }
            
            return;
        //아직 모든 경우의 수를 구하지 못했다면
        }else{
            for(int i=idx; i<discount.length; i++){
            	//i번째 이모티콘에 10, 20, 30, 40% 할인율을 각각 고려하여 재귀적으로 모든 경우의 수를 구함
                for(int j=0; j<4; j++){
                    discount[i] = (j+1)*10;
                    permutate(users, emoticons, discount, i+1);
                }
            }
        }
    }
    
    public int[] solution(int[][] users, int[] emoticons) {
        this.cnt = 0;
        this.sum = 0;
        
        //m개의 이모티콘에 대하여 모든 가능한 할인 경우의 수를 구하고
        //그때의 이모티콘 판매 금액, 이모티콘 플러스 서비스 가입자 수를 구함
        permutate(users,emoticons, new int[emoticons.length], 0);
        
        return new int[]{this.cnt,this.sum};
    }
}