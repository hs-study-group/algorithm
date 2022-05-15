//https://programmers.co.kr/learn/courses/30/lessons/42890?language=java

import java.util.*;

class Solution {
	//�ĺ�Ű���� �����ϴ� ����Ʈ
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    
    //�ش� ������ ��Ʈ�� ġȯ�Ͽ� ������ ��ȯ�ϴ� �޼ҵ�
    public static int parse(boolean[] v){
        int n = 1;
        int sum = 0;
        //���� 0,1,2,3,4,5�� 0,1,3���� ���õǾ��ٸ�
        //1*2^0 + 1*2^1 + 1*2^3 = 11�� ��ȯ��
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
                //���õ� ���տ� �ش��ϴ� ��Ʈ����Ʈ���� ���ڿ��� ��ħ
                for(int j=0; j<v.length; j++){
                    if(v[j]){
                        str += relation[i][j]+" ";
                    }
                }
                //�̹� �����ϴ� ���ڿ��̶�� �ߺ��Ǿ��ٴ� �ǹ��̹Ƿ� ����
                //��, ���ϼ��� �������� ����
                if(hm.get(str)!=null){
                    return;
                //�ش� ���ڿ��� �ӽ÷� ������
                }else{
                    hm.put(str,true);
                }
            }
            
            //���õ� ������ ������ ��ȯ
            int num = parse(v);
            
            //�ش� ������ ���� �̹� �����ϴ� �ĺ�Ű�� �����Ͽ� �ٸ� ��Ʈ����Ʈ�� ���ٿ��� ���̶��
            //�̴� �ּҼ��� �������� ���� ���̹Ƿ� ����
            for(int i=0; i<list.size(); i++){
                if((list.get(i)&num)==num||(list.get(i)&num)==list.get(i)){
                    return;
                }
            }
            
            //���ϼ��� �ּҼ��� ��� ������ �ĺ�Ű�̹Ƿ� ����
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
        
        //��� Ʃ�ÿ� ���� ��� ������ select ������ ����
        for(int i=1; i<=relation[0].length; i++){
            combinate(relation, new boolean[relation[0].length],0,i);
        }
        
        //�ĺ�Ű�� ������ ����
        answer = list.size();
        
        return answer;
    }
}