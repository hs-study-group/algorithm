import java.util.*;

class Solution {
    public static String[] arr = {"A", "C", "F", "J", "M", "N", "R", "T"};
    public static int cnt = 0;
    
    //정수의 절대값을 반환하는 메소드
    public static int abs(int n){
        return n>=0?n:n*(-1);
    }
    
    //8P8 순열을 구하고 해당 순열을 구성하는 각 요소들의 인덱스를 저장한뒤
    //data배열에 주어진 조건들을 만족하는지 확인하여, 만족할때 cnt값을 증가시키는 메소드
    public static void permutate(String[] data, String s, boolean[] v, int n){
        
        //가능한 순열중 하나를 구했다면 아래의 조건문 수행
        if(n==0){
            //각 요소들의 인덱스를 임시로 저장할 해시맵
            HashMap<Character,Integer> idx = new HashMap<Character,Integer>();
            char[] temp = s.toCharArray();
            
            //각 요소들의 인덱스를 해시맵에 저장함
            for(int i=0; i<temp.length; i++){
                idx.put(temp[i],i);
            }
            
            for(int i=0; i<data.length; i++){
            	char[] arr = data[i].toCharArray();
                
                //조건에 제시된 두 사람의 거리를 계산함
            	int dist = abs(idx.get(arr[0])-idx.get(arr[2]))-1;
            	
                //주어진 값보다 거리가 커야할 때
            	if(arr[3]=='>') {
                    //해당 조건을 만족하지 않으면 그대로 메소드 종료
            		if(dist<=arr[4]-'0') {
            			return;
            		}
                //주어진 값보다 거리가 작아야할 때
            	}else if(arr[3]=='<') {
                    //해당 조건을 만족하지 않으면 그대로 메소드 종료
            		if(dist>=arr[4]-'0') {
            			return;
            		}
                //주어진 값과 거리가 같아야 할 때
            	}else {
                    //해당 조건을 만족하지 않으면 그대로 메소드 종료
            		if(dist!=arr[4]-'0') {
            			return;
            		}
            	}
            }
            //data에 주어진 모든 조건을 만족할때만 cnt값을 1 증가시키고 메소드 종료
            cnt++;
            return;
        }
        
        //순열을 구하기 위한 반복문
        for(int i=0; i<arr.length; i++){
            //자기 자신을 방문한 적이 있으면 굳이 재귀 방문하지 않음
            if(v[i]){
                continue;
            }
            //방문 표시를 하고 순열을 계속해서 구해나감
            v[i] = true;
            permutate(data,s+arr[i],v,n-1);
            
            //재귀가 종료되면 방문표시를 해제함으로써 순열을 구할 수 있음
            v[i] = false;
        }    
    }
    
    public int solution(int n, String[] data) {
        int answer = 0;
        
        //정확한 이유는 모르나, 프로그래머스 채점시 cnt를 반드시 0으로 한번 더 초기화
        //해야만 제대로 채점이 되는 것을 확인할 수 있었음
        cnt = 0;
        
        permutate(data,"",new boolean[8],8);
        answer = cnt;
        
        return answer;
    }
}