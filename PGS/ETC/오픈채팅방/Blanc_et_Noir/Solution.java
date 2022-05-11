import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        String[] answer = {};
        
        //Ư�� ������� ID�� ���� �ֽ� �г��� ������ ������ �ؽø�
        HashMap<String,String> db = new HashMap<String,String>();
        
        //ä�ù� ���Ա���� ������� ������ ť
        Queue<String> q = new LinkedList<String>();
        
        for(int i=0; i<record.length; i++){
            String[] temp = record[i].split(" ");
            //�г��� ������ ��� DB�� �ֽ�ȭ
            if(temp[0].equals("Change")){
                db.put(temp[1],temp[2]);
            //ä�ù� ������ ��� ����� �г����� �� �����Ƿ� DB �ֽ�ȭ
            //ä�ù� ���Ա���� �����ϵ�, ���� ID�� ���⿩�θ� E,L�� �������� ǥ��
            }else if(temp[0].equals("Enter")){
                db.put(temp[1],temp[2]);
                q.add(temp[1]+" E");
            //ä�ù� ������ ��� ��ϸ� �صθ� ��
            }else{
                q.add(temp[1]+" L");
            }
        }
        
        answer = new String[q.size()];
        int idx = 0;
        
        while(!q.isEmpty()){
            String[] temp = q.poll().split(" ");
            //����, ���� ��Ͽ� ���� ID�� �ֽ�ȭ�� �г������� ��ü��
            if(temp[1].equals("E")){
                answer[idx++] = db.get(temp[0])+"���� ���Խ��ϴ�.";
            }else{
                answer[idx++] = db.get(temp[0])+"���� �������ϴ�.";
            }
        }
        
        return answer;
    }
}