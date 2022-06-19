class Solution {
    public String solution(String s) {
        String answer = "";
        
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        //���ڿ��� ������ �������� �и���
        String[] str = s.split(" ");
        
        //�� ���ڿ��� ���Ͽ�
        for(String strnum : str){
        	//�ش� ���ڿ��� ������ ��ȯ��
            int num = Integer.parseInt(strnum);
            //���±����� �ּڰ����� �ش� ���� �۴ٸ� �ش� ���� �ּҰ����� �ʱ�ȭ
            if(min>num){
                min = num;
            }
            //���±����� �ִ񰪺��� �ش� ���� ũ�ٸ� �ش� ���� �ִ밪���� �ʱ�ȭ
            if(max<num){
                max = num;
            }
        }
        
        //�ּҰ��� �ִ밪�� ���ڿ��� ��ȯ�Ͽ� ������
        answer = Integer.toString(min)+" "+Integer.toString(max);
        return answer;
    }
}