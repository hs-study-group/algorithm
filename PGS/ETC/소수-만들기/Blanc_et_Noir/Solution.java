class Solution {
	//에라토스테네스의체 알고리즘
    public boolean[] eratosthenes(int n){
        boolean[] primes = new boolean[n+1];
        boolean[] v = new boolean[n+1];
        
        //2부터 탐색을 실시
        for(int i=2;i<=n;i++){
        	//아직 탐색하지 않았다면
            if(!v[i]){
            	//그것을 소수로 처리함
                primes[i]=true;
                //i의 배수를 모두 방문처리함
                for(int j=i;j<=n;j+=i){
                    v[j]=true;
                }
            }
        }
        
        return primes;
    }
    
    public int solution(int[] nums) {
    	//에라토스테네스의 체 알고리즘을 수행
        boolean[] primes = eratostenes(3000);
        int answer = 0;

        for(int i=0;i<nums.length-2;i++){
            for(int j=i+1;j<nums.length-1;j++){
                for(int k=j+1;k<nums.length;k++){
                	//중복되지 않게 3개의 숫자를 선택한 합이 소수라면
                    if(primes[nums[i]+nums[j]+nums[k]]){
                    	//카운트함
                        answer++;
                    }
                }
            }
        }

        return answer;
    }
}