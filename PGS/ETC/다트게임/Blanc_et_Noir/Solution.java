class Solution {
    public int solution(String dartResult) {
        int answer = 0;
        int idx = -1;
        int[] temp = new int[3];
        
        //���ڿ��� �־��� ��Ʈ���� ����� ���� �迭�� ��ȯ�Ͽ� ó���� �����ϰ���
        char[] arr = dartResult.toCharArray();
        
        for(int i=0; i<arr.length; i++){
        	//���� �ش� ��ġ�� ���ڰ� ���ڶ��
            if(arr[i]>='0'&&arr[i]<='9'){
            	//�� ���ڰ� 10�� �ƴ϶��
                if(arr[i+1]!='0'){
                	//������� �ش� ���ڸ� ������ ��ȯ�Ͽ� ������
                    temp[++idx] = arr[i]-'0';
                //�� ���ڰ� 10�̶��
                }else{
                	//������� 10�� �����ϰ�, i�� �ϳ� �� �������� ���� ���ڸ� Ž����
                    temp[++idx] = 10;
                    i++;
                }
            //���� S��� �ش� ���� 1�����Ͽ� �߰���
            }else if(arr[i]=='S'){
                temp[idx]=temp[idx];
            //���� D��� �ش� ���� 2�����Ͽ� �߰���
            }else if(arr[i]=='D'){
                temp[idx]=temp[idx]*temp[idx];
            //���� T��� �ش� ���� 3�����Ͽ� �߰���
            }else if(arr[i]=='T'){
                temp[idx]=temp[idx]*temp[idx]*temp[idx];
            //���� ��Ÿ���
            }else if(arr[i]=='*'){
            	//ù��° ��ȸ���� ��Ÿ�� ���� ���̸�
                if(idx==0){
                	//�׳� �ڱ� �ڽŸ� ���� 2�����
                    temp[idx] = temp[idx]*2;
                //��, ����° ��ȸ���� ���� ���̶��
                }else if(idx>=1){
                	//�ڱ� �ڽŰ� ������ ���� 2��� ��
                    temp[idx-1] = temp[idx-1]*2;
                    temp[idx] = temp[idx]*2;
                }
            //�������̶�� �ش� ������ -�� ��ȯ��
            }else{
                temp[idx] = temp[idx]*(-1);
            }
        }
        //����� ��ȯ��
        answer = temp[0]+temp[1]+temp[2];
        return answer;
    }
}