//https://school.programmers.co.kr/learn/courses/30/lessons/178870

import java.util.*;

class Solution {
    public int[] solution(int[] sequence, int k) {
        long sum = sequence[0];
        int s=0, e=0;
        
        //l, r은 각각 부분 연속 수열의 합이 k가 될 때의
        //시작 및 종료위치를 저장하는 변수임
        //그러한 부분 수열이 여러 개라면 가장 짧은 구간이 되도록 값을 가지며
        //가장 짧은 구간이 시작 인덱스가 가장 앞설때의 구간을 사용함 
        int l=0, r=sequence.length;
        
        while(true){
        	//구간의 합이 k보다 크다면
            if(sum>k){
            	//시작포인터가 가리키는 값을 sum에서 제외하고, s 포인터를 이동시킴
                sum-=sequence[s++];
            //구간의 합이 k라면
            }else if(sum==k){
            	//기존에 기록해둔 구간의 시작 및 종료 인덱스보다
            	//더 짧은 구간을 갖는다면
                if(e-s<r-l){
                	//그때의 시작 및 종료 인덱스를 정답인 l, r로 갱신함
                    l = s;
                    r = e;
                }
                
                //s포인터가 가리키는 값을 sum에서 제외하고, s 포인터를 이동시킴
                //또는 e포인터를 한 칸 이동시키고 그 값을 sum에 추가해도 됨
                sum-=sequence[s++];
            //e포인터를 증가시키면 배열을 벗어나는 경우
            }else if(e+1==sequence.length){
            	//현재 e포인터가 배열의 끝까지 가버린 상태에서, sum은 k보다 작은 상태이므로
            	//제 아무리 e포인터를 우측으로 더 이동시켜봐도 sum이 k보다 커지거나, 같아질 일은 절대 없음
            	//따라서 반복을 즉시 종료함
                break;
            //구간의 합이 k보다 작다면
            }else{
            	//e포인터를 우측으로 이동시키고, e포인터가 가리키는 값을 합에 추가함
                sum+=sequence[++e];
            }
        }
        
        //합이 k가 될 때의 연속 부분 수열의 가장 짧은 구간의 시작, 종료 인덱스를 리턴함
        return new int[]{l,r};
    }
}