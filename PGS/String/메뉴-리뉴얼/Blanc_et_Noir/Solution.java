//https://programmers.co.kr/learn/courses/30/lessons/72411

import java.util.*;

class Solution {
    public static HashMap<String, Integer> hm = new HashMap<String,Integer>();
    
    //� �մ��� ������ �޴��� ���� ������ ���� �ڽ��� ������ ���ϰ�
    //�ش� �����ڽ��� ��Ÿ�� ������ �����ϴ� �޼ҵ�
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
        
        //�ֹ��� �׻� ���ĺ� ������������ �־����� ���� �ƴϹǷ�
        //�ֹ��� ���ĺ������� �����ϱ� ���� �ݺ�
        for(int i=0; i<orders.length; i++){
            char[] temp = orders[i].toCharArray();
            
            Arrays.sort(temp);
            
            //������ ��� �����ڽ��� ��
            for(int j=0; j<course.length; j++){
                combinate(new String(temp),"",0,course[j]);
            }
        }
        
        String[] arr = {"","","","","","","","","",""};
        int[] num = new int[10];
        
        //�����صξ��� �����ڽ�����
        for(String key : hm.keySet()){
            int n = hm.get(key);
            int idx = key.length()-1;
            
            //2�� �̻󿡰Լ� ����� �� �ִ� �����ڽ��� �����ϴµ�
            if(n>=2){
            	//������ ����� �ش� �������� �丮���� �� ����
            	//�����κ��� ���� �� �ִ� �����ڽ����ٸ� �װ����� ����
                if(n>num[idx]){
                    arr[idx] = key;
                    num[idx] = n;
                //������ ������κ��� ���� �� �ִ� �丮�� ���� ������
                }else if(n==num[idx]){
                    arr[idx] = arr[idx] + " "+ key;
                }
            }
        }
        
        LinkedList<String> list = new LinkedList<String>();
        
        //�����ߴ� ���ڿ��� �и��ϸ鼭 ����Ʈ�� �߰�
        for(String str : arr){
            if(!str.equals("")){
                String[] temp = str.split(" ");
                for(String str2 : temp){
                    list.add(str2);
                }
            }
        }
        
        //�ش� ���� �ڽ����� ���ĺ� ���������� ����
        Collections.sort(list);
        
        answer = new String[list.size()];
        
        //�迭�������� �����ؾ��ϹǷ� ������ ���� ó��
        for(int i=0; i<answer.length; i++){
            answer[i] = list.remove(0);
        }
        
        return answer;
    }
}