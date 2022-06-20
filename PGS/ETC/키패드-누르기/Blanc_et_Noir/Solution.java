class Solution {
    
    //절대값을 반환하는 메소드
    public int abs(int num){
        return num<0?num*(-1):num;
    }
    
    //키패드간 거리를 구하는 메소드
    //유클리드가 아닌 맨해튼 거리를 반환
    //문제에서 상하좌우 4방향으로만 이동할 수 있다고 명시함
    public int func(int a, int b){
        a=a-1;
        b=b-1;
        int ax = a%3,ay = a/3, bx = b%3, by = b/3;
        return abs(ax-bx)+abs(ay-by);
    }
    
    public String solution(int[] numbers, String hand) {
        String answer = "";
        
        //*과 #은 각각 10, 12위치에 존재한다고 처리
        int left = 10, right = 12;
        
        for(int i=0; i<numbers.length; i++){
            int num = numbers[i];
            
            //0의 경우 11번위치에 존재한다고 처리
            if(num==0){
                num = 11;
            }
            
            //첫째열에 존재하는 키는 왼손으로 누름
            if(num%3==1){
                left = num;
                answer+="L";
            //셋째열에 존재하는 키는 오른손으로 누름
            }else if(num%3==0){
                right = num;
                answer+="R";
            //둘째열에 존재하는 키는 거리에 따라 처리
            }else{
                //왼손과 오른손을 기준으로 각각 맨해튼 거리 계산
                int d1 = func(left,num);
                int d2 = func(right,num);
                
                //왼손이 더 가까우면 왼손으로
                if(d1<d2){
                    left = num;
                    answer+="L";
                //오른손이 더 가까우면 오른손으로
                }else if(d1>d2){
                    right = num;
                    answer+="R";
                //거리가 같으면 왼손잡이, 오른손잡이 여부에 따라 처리
                }else{
                    if(hand.equals("left")){
                        left = num;
                        answer+="L";
                    }else{
                        right = num;
                        answer+="R";                        
                    }
                }
            }
        }
        
        return answer;
    }
}