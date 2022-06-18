class Solution {
	//퀸은 1개의 열에는 반드시 1개만 존재할 수 있으므로 굳이 2차원배열 선언 X
	//arr[x] = y와 같은 방법으로 표현하는데 이는, (x,y) 위치에 퀸이 존재함을 의미함
    public static int[] arr;
    
    //가능한 배치수를 저장할 변수
    public static int sum = 0;
    
    //절대값을 반환하는 메소드
    public static int abs(int x){
        return x>0?x:x*-1;
    }
    
    //실제로 퀸을 배치하는 메소드
    public static void diploy(int x){
    	//체스판의 범위를 벗어나면 종료, 여기까지 왔으면 퀸을 모두 N개 배치하는데 성공한 것임
        if(x==arr.length){
            sum++;
            return;
        }
        //현재 x의 위치에서 N개의 탐색지에 대해 전부 배치가 가능한지 아닌지 판단함
        for(int i=0; i<arr.length; i++){
        	//(x,y) 위치에 배치가 가능하다면 배치함
            if(canDiploy(x,i)){
                arr[x] = i;
                diploy(x+1);
            }
        }
    }
    
    //퀸을 (x,y) 위치에 배치할 수 있는지 판단하는 메소드
    public static boolean canDiploy(int x, int y){
    	//자신보다 왼쪽에 위치한 퀸에 대해서만 판단함
        for(int i=0; i<x; i++){
        	//자신과 같은 행에 있는 퀸이 존재하면 false
            if(arr[i]==y){
                return false;
            }
            //자신과 좌상단, 우하단 동일 대각선 선상에 다른 퀸이 존재할 경우 false
            if(abs(arr[i]-y)==abs(i-x)){
                return false;
            }
        }
        //자신보다 왼쪽에 위치한 퀸에 대해 모든 조건을 만족하면 true
        return true;
    }
    
    public int solution(int n) {
        arr = new int[n];
        diploy(0);
        return sum;
    }
}