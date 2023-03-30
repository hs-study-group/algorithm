//https://school.programmers.co.kr/learn/courses/30/lessons/161988

class Solution {
	//배열에서 연속합이 가장 클 때의 값을 리턴하는 메소드
    public long getMax(long[] arr){
        long max = Long.MIN_VALUE;
        
        //dp[ i ] = i번째 요소를 연속 수열의 가장 마지막 숫자로 할때의 연속합의 최대값
        long[] dp = new long[arr.length];
        
        //0번째 요소가 음수인경우, 선택하지 않아야 연속합이 최대가 됨
        dp[0] = Math.max(arr[0],0);
        
        //연속합의 최대값을 max로 갱신함
        max = Math.max(max,dp[0]);
        
        for(int i=1;i<arr.length;i++){
        	//i번째 요소를 연속 수열의 마지막 숫자로 했을때의 연속합의 최대값은
        	//i-1번째 요소를 연속수열의 마지막 숫자로 했을때의 연속합의 최대값 + i번째 숫자의 합과
        	//i-1번째 요소까지의 선택을 모두 버려버리고 i번째 숫자만을 선택했을때의 값 중에서
        	//더 큰값을 dp[ i ]값으로 설정함
            dp[i] = Math.max(dp[i-1]+arr[i],arr[i]);
            max = Math.max(max,dp[i]);
        }
        
        return max;
    }
    
    public long solution(int[] sequence) {
        long answer = 0;
        long[][] arr = new long[2][sequence.length];
       
        for(int i=0;i<2;i++){
            int val;
            
            if(i==0){
                val = -1;
            }else{
                val = 1;
            }
            
            //주어진 sequence 배열에서 -1, 1의 가중치, 1, -1의 가중치를
            //차례대로 곱했을때의 결과를 새로운 배열로 담음
            for(int j=0;j<arr[0].length;j++){
                arr[i][j] = sequence[j]*val;
                val*=-1;
            }
        }

        //-1, 1, -1, 1과 같이 가중치를 곱했을 때의 배열과
        //1, -1, 1, -1과 같이 가중치를 곱했을 때의 배열에 대하여
        //각각 연속합이 가장 클때의 값을 구하고, 둘 중 더 큰값을 리턴함
        return Math.max(getMax(arr[0]),getMax(arr[1]));
    }
}