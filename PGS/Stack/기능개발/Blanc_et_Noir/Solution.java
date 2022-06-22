import java.util.ArrayList;
import java.util.Stack;

class Solution {
	//�������� ���� �Ⱓ�� �ӽ÷� ������ ����
    public static Stack<Integer> s = new Stack<Integer>();
    
    public int[] solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] answer = {};
        int cnt=0;
        
        for(int i=0; i<progresses.length; i++){
        	
        	//���� ���൵�� ���ǵ�� ������ �Ҽ������� �ø�ó���� �ϸ�, �������� ���� �ϼ��� ����� �� ����
            int d = (int) Math.ceil((100 - progresses[i])*1.0/speeds[i]);
            
            //������ ������� �ʴٸ�
            if(!s.isEmpty()){
            	//���� ���� �۾��� �� ���� �����ٸ�
                if(s.peek()<d){
                	//�����۾��� ���� �۾���� ������ ��ġ��, ������ �۾��� ���� �����ؾ���
                	//���� ���� �۾��� ���ÿ��� �����ϰ�, �� �۾��� ���� �Ⱓ�� �߰���
                    s.pop();
                    s.push(d);
                    
                    //���±��� �Ѳ����� ���������� ����� ���� ī��Ʈ �� ���� ������
                    list.add(cnt);
                    
                    //ī��Ʈ�� �ʱ�ȭ��
                    cnt=1;
                    
                //���� �۾��� �� �ʰ� �����ٸ�
                }else{
                	//������ �۾��� ���� �۾��� ���� ����������
                    cnt++;
                }
            //������ ����ִٸ�
            }else{
            	//���� �տ� ��ġ�� ����� ���� �Ⱓ�� ���ÿ� �߰���
                s.push(d);
                
                //������ ����� ���� �ʱ�ȭ��
                cnt=1;
            }
        }
        
        list.add(cnt);
        
        answer = new int[list.size()];
        
        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i);
        }
        
        return answer;
    }
}