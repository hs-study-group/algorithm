//https://school.programmers.co.kr/learn/courses/30/lessons/12924

class Solution {
    public int solution(int n) {
        int answer = 0;
        
        int[] arr = new int[n];
        
        //임시 배열에 1부터 n까지 대입함
        for(int i=0; i<arr.length; i++){
            arr[i] = i+1;
        }
        
        //s포인터는 0부터, e포인터는 1부터 시작
        int s = 0;
        int e = 1;
        
        //sum값은 s포인터와 e-1포인터가 가리키는 구간의 합을 말하며
        //초기값은 s와 e-1포인터가 가리키는 인덱스 [0:0]구간의 합임
        int sum = 1;
        
        while(true){
        	//sum이 n과 같다면
            if(sum==n){
            	//카운트함
                answer++;
            }
            //sum이 n보다 크면
            //s포인터를 우측으로 이동시켜 sum을 감소시킴 
            if(sum>n){
                sum -= arr[s];
                s++;
            //e 포인터가 배열 범위를 벗어나더라도
            //sum이 n과 같을 수 있으므로 반복문의 조건에 넣지 않고
            //내부 if문에서 탈출하도록 처리해야함
            }else if(e>=arr.length){
                break;
            //sum이 n보다 작으면
            }else if(sum<n){
            	//e 포인터를 우측으로 이동시켜 sum을 증가시킴
                sum += arr[e];
                e++;
            //sum이 n과 같으면
            //해당 문제에서는 s포인터나 e포인터중 아무거나 이동시켜도 됨
            //여기서는 e포인터를 이동시키는 것으로 처리함
            }else{
                sum += arr[e];
                e++;
            }
        }
        
        return answer;
    }
}