//https://programmers.co.kr/learn/courses/30/lessons/42890?language=java

import java.util.*;

class Solution {
    //후보키들을 저장하는 리스트
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    
    //해당 조합을 비트로 치환하여 정수로 반환하는 메소드
    public static int parse(boolean[] v){
        int n = 1;
        int sum = 0;
        //만약 0,1,2,3,4,5중 0,1,3번이 선택되었다면
        //1*2^0 + 1*2^1 + 1*2^3 = 11을 반환함
        for(int i=0; i<v.length; i++){
            if(v[i]){
                sum+=(n<<i);
            }
        }
        return sum;
    }

    public static void combinate(String[][] relation, boolean[] v, int idx, int cnt){
        if(cnt==0){
            HashMap<String,Boolean> hm = new HashMap<String,Boolean>();
            for(int i=0; i<relation.length; i++){
                String str = "";
                //선택된 조합에 해당하는 애트리뷰트들을 문자열로 합침
                for(int j=0; j<v.length; j++){
                    if(v[j]){
                        str += relation[i][j]+" ";
                    }
                }
                //이미 존재하는 문자열이라면 중복되었다는 의미이므로 종료
                //즉, 유일성을 만족하지 않음
                if(hm.get(str)!=null){
                    return;
                //해당 문자열을 임시로 저장함
                }else{
                    hm.put(str,true);
                }
            }
            
            //선택된 조합을 정수로 변환
            int num = parse(v);
            
            //해당 조합이 만약 이미 존재하는 후보키를 포함하여 다른 애트리뷰트가 덧붙여진 것이라면
            //이는 최소성을 만족하지 않은 것이므로 종료
            for(int i=0; i<list.size(); i++){
                if((list.get(i)&num)==num||(list.get(i)&num)==list.get(i)){
                    return;
                }
            }
            
            //유일성과 최소성이 모두 만족된 후보키이므로 저장
            list.add(num);
            return;
        }else{
            for(int i=idx; i<relation[0].length; i++){
                v[i] = true;
                combinate(relation, v, i+1, cnt-1);
                v[i] = false;
            }
        }
    }
    
    public int solution(String[][] relation) {
        int answer = 0;
        
        //모든 튜플에 대해 모든 가능한 select 연산을 수행
        for(int i=1; i<=relation[0].length; i++){
            combinate(relation, new boolean[relation[0].length],0,i);
        }
        
        //후보키의 개수를 리턴
        answer = list.size();
        
        return answer;
    }
}
