import java.util.*;

class Solution{
    public int solution(String s){
    	//���ŵ������� ���� �ֱٿ� Ž���� �� ���ڰ� ��ġ�ϴ��� �Ǵ��ϱ� ���� ���� ����
        Stack<Character> stack = new Stack<Character>();
        
        //���ڿ��� ���ڹ迭�� ��ȯ��
        char[] arr = s.toCharArray();
        
        //���ڸ� �ϳ��� Ž����
        for(int i=0; i<arr.length; i++){
        	//������ ����ִٸ� ������ ���ÿ� �߰�
            if(stack.isEmpty()){
                stack.push(arr[i]);
            //������ ������� �ʴٸ�
            }else{
            	//���� �ֱٿ� �߰��� ���ڿ� ���� ���ڰ� �����ϴٸ�
                if(stack.peek()==arr[i]){
                	//���ÿ��� ���� �ֱ� �߰��� ���ڸ� ������
                    stack.pop();
                //�������� �ʴٸ� �ش� ���ڸ� �߰���
                }else{
                    stack.push(arr[i]);
                }
            }
        }
        
        //��� Ž���� ���ÿ� ���ڰ� �������� �ʴٸ�
        //�̴� ��� ���ڰ� ¦�� �´ٴ� ����
        if(stack.isEmpty()){
            return 1;
        //��� Ž���� ���ÿ� ���ڰ� ����������
        //¦�� �����ʴ� ���ڰ� �����ϴٴ� ����
        }else{
            return 0;
        }
    }
}