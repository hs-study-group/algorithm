//https://school.programmers.co.kr/learn/courses/30/lessons/12987

import java.util.*;

class Solution {
    //val보다 큰 값이 처음 나타나는 인덱스를 반환하는 upperbound( )메소드
    //만약 그러한 값이 없다면 B.length 값이 반환됨
    public int upperbound(int[] B, int val){
        int start = 0, end = B.length;
        while(start<end){
            int mid = (start+end)/2;
            if(B[mid]<=val){
                start = mid + 1;
            }else{
                end = mid;
            }
        }
        return end;
    }
    
    public int solution(int[] A, int[] B) {
        int answer = 0;
        
        //전체적인 아이디어는 아래와 같음.
        //1. A와 B의 대결에서 B가 승점을 얻으려면 당연히 B[k] > A[i]를 만족해야함.
        
        //2. 그런데, 만약 A[i] = 1이라면, B[k] = 2, 3, 4, 5, 6 ... 중 어느 하나라면
        //   B는 승점을 얻을 수 있는데, 이때 최소의 값인 B[k] = 2 로 이길 수 있다면
        //   즉, A[i]보다 크긴 크지만, 가장 작은값인 2로 이길 수 있다면
        //   다음 A[i]와의 승리에서 이길 가능성이 높아짐
        
        //3. 즉, A의 값보다 큰 값중에서 가장 작은값, upperbound( )를 구해야 함.
        
        //4. 배열 자료구조의 특성상 동적으로 배열의 형태롤 변화시킬수는 없으며
        //   List를 사용하면 랜덤액세스가 불가능하고
        //   ArrayList를 사용하면 remove( )의 시간복잡도가 O(N)이므로 TLE가 발생할 수 있음.
        
        //5. 따라서 현재 A[i]의 값보다 큰 B[k]의 개수를 구하고, 거기서 승점을 빼면
        //   승부에서 사용하고 남은 A[i]의 값보다 큰 B[k]의 개수를 구할 수 있으며
        //   이것이 0보다 크다면 A[i]와의 승부에서 이길 수 있다는 의미임.
        
        //두 배열을 모두 정렬함
        Arrays.sort(A);
        Arrays.sort(B);
        int cnt = 0;
        
        //A[]의 가장 큰 값부터 탐색함, 작은 값부터 탐색하면 문제를 해결할 수 없음.
        for(int i=A.length-1; i>=0;i--){
            //A[i]값보다 더 큰 값의 개수가 몇개인지 탐색함
            int result = B.length-upperbound(B,A[i]);
            
            //자신보다 큰 값의 개수중, 기존에 B[k]와 대결하여 사라진
            //숫자들의 개수를 제외한 값이 0보다 크다면
            //즉, 아직 A[i]보다 큰 B[k]가 남아있다면
            if(result-cnt>0){
                //B가 승점을 얻음
                cnt++;
            }
        }
        
        return cnt;
    }
}