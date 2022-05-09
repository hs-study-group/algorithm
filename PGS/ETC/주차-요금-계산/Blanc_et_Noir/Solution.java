import java.util.*;

class Solution {
	
	//������ ����, ���� �ð��� �Է¹޾� �� ���̸� �� ������ �����ϴ� �޼ҵ�
    public static int dif(String start, String end){
        String[] temp = start.split(":");
        int s = Integer.parseInt(temp[0])*60+Integer.parseInt(temp[1]);
        
        temp = end.split(":");
        int e = Integer.parseInt(temp[0])*60+Integer.parseInt(temp[1]);
        
        return e - s;
    }
    
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        //������ ���� ����� �����ϴ� �ؽø�
        HashMap<String,String> hm = new HashMap<String,String>();
        
        //�� �ڵ����� answer �迭�� ��� ��ġ�� �����ϴ��� �ε����� ��Ƶδ� �ؽø�
        HashMap<String,Integer> idx = new HashMap<String,Integer>();
        
        //�� �ڵ����� ��ȣ�� ������������ answer�� ��ġ�ϱ����� record�� �������� ����
        Arrays.sort(records,new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                int n1 = Integer.parseInt(s1.split(" ")[1]);
                int n2 = Integer.parseInt(s2.split(" ")[1]);
                if(n1<n2){
                    return -1;
                }else if (n1>n2){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //answer�� �ε����� ����Ű�� ����
        int cnt = 0;
        
        //�ߺ����� �ʵ��� �ؽøʿ� �� �ڵ������� �ε����� ������
        for(int i=0; i<records.length; i++){
            String[] temp = records[i].split(" ");
            if(!idx.containsKey(temp[1])){
                idx.put(temp[1],cnt++);
            }
        }
        
        answer = new int[idx.size()];
        
        for(int i=0; i<records.length; i++){
            String[] temp = records[i].split(" ");
            
            //������ ���� �õ���� �ڵ��� ��ȣ�� ���� ������ ���� �ð��� �����
            if(temp[2].equals("IN")){
                hm.put(temp[1],temp[0]);
            //������ ���� �õ���� �ش� �ڵ�����ȣ�� ���� ������ ���� �ð��� ��ȸ�ϰ�
            //�ش� �ð��� �����ð��� ���̸� answer�� �ӽ÷� �����
            }else{
                String start = hm.get(temp[1]);
                int m = dif(start, temp[0]);

                answer[idx.get(temp[1])] += m;
                //����� �������� �ش� ������ ���� ����� ���ҽ�Ŵ
                hm.remove(temp[1]);
            }
        }
        
        //���� ������ ���� ����� ������� ���� �ڵ�������
        //�������� ���� ������ �Ǵ��ϰ� 23:59�п� �ڵ����� ������ ������ ó��
        
        for(String num : hm.keySet()){
            String start = hm.get(num);
            int m = dif(start, "23:59");
            answer[idx.get(num)] += m;
        }
        
        for(int i=0; i<answer.length; i++){
            //�ش� �ڵ����� ���� ���� �ð��� �⺻ �ð� ������ ���
            if(answer[i]<=fees[0]){
            	//�⺻��ݸ� �ΰ�
                answer[i] = fees[1];
            //���� ���� �ð��� �⺻ �ð��� �ʰ��������
            }else{
            	//�⺻��� + �ʰ��� ���� �ð���ŭ�� ������� ������
                answer[i] = fees[1] + (int)Math.ceil((answer[i]-fees[0])*1.0/fees[2])*fees[3];
            }
        }
        
        return answer;
    }
}