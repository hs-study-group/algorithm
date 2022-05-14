//https://programmers.co.kr/learn/courses/30/lessons/72411

import java.util.*;

class Solution {
    public static HashMap<String, Integer> hm = new HashMap<String,Integer>();
    
    //어떤 손님이 선택한 메뉴에 대해 가능한 서브 코스의 조합을 구하고
    //해당 서브코스가 나타난 정도를 저장하는 메소드
    public static void combinate(String str, String result, int idx, int cnt){
        if(cnt==0){
            if(hm.get(result)!=null){
                hm.put(result,hm.get(result)+1);
            }else{
                hm.put(result,1);
            }
            return;
        }
        for(int i=idx; i<str.length(); i++){
            combinate(str,result+str.substring(i,i+1),i+1,cnt-1);
        }
    }
    
    public String[] solution(String[] orders, int[] course) {
        String[] answer = {};
        
        //주문이 항상 알파벳 오름차순으로 주어지는 것이 아니므로
        //주문을 알파벳순으로 정렬하기 위한 반복
        for(int i=0; i<orders.length; i++){
            char[] temp = orders[i].toCharArray();
            
            Arrays.sort(temp);
            
            //가능한 모든 서브코스를 얻어냄
            for(int j=0; j<course.length; j++){
                combinate(new String(temp),"",0,course[j]);
            }
        }
        
        String[] arr = {"","","","","","","","","",""};
        int[] num = new int[10];
        
        //저장해두었던 서브코스들중
        for(String key : hm.keySet()){
            int n = hm.get(key);
            int idx = key.length()-1;
            
            //2명 이상에게서 얻어질 수 있는 서브코스만 저장하는데
            if(n>=2){
            	//기존에 저장된 해당 가짓수의 요리보다 더 많은
            	//사람들로부터 얻을 수 있는 서브코스였다면 그것으로 갱신
                if(n>num[idx]){
                    arr[idx] = key;
                    num[idx] = n;
                //동일한 사람수로부터 얻을 수 있는 요리는 같이 저장함
                }else if(n==num[idx]){
                    arr[idx] = arr[idx] + " "+ key;
                }
            }
        }
        
        LinkedList<String> list = new LinkedList<String>();
        
        //저장했던 문자열을 분리하면서 리스트에 추가
        for(String str : arr){
            if(!str.equals("")){
                String[] temp = str.split(" ");
                for(String str2 : temp){
                    list.add(str2);
                }
            }
        }
        
        //해당 서브 코스들을 알파벳 사전순으로 정렬
        Collections.sort(list);
        
        answer = new String[list.size()];
        
        //배열형식으로 리턴해야하므로 다음과 같이 처리
        for(int i=0; i<answer.length; i++){
            answer[i] = list.remove(0);
        }
        
        return answer;
    }
}