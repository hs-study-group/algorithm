class Solution {
    
    //���밪�� ��ȯ�ϴ� �޼ҵ�
    public int abs(int num){
        return num<0?num*(-1):num;
    }
    
    //Ű�е尣 �Ÿ��� ���ϴ� �޼ҵ�
    //��Ŭ���尡 �ƴ� ����ư �Ÿ��� ��ȯ
    //�������� �����¿� 4�������θ� �̵��� �� �ִٰ� �����
    public int func(int a, int b){
        a=a-1;
        b=b-1;
        int ax = a%3,ay = a/3, bx = b%3, by = b/3;
        return abs(ax-bx)+abs(ay-by);
    }
    
    public String solution(int[] numbers, String hand) {
        String answer = "";
        
        //*�� #�� ���� 10, 12��ġ�� �����Ѵٰ� ó��
        int left = 10, right = 12;
        
        for(int i=0; i<numbers.length; i++){
            int num = numbers[i];
            
            //0�� ��� 11����ġ�� �����Ѵٰ� ó��
            if(num==0){
                num = 11;
            }
            
            //ù°���� �����ϴ� Ű�� �޼����� ����
            if(num%3==1){
                left = num;
                answer+="L";
            //��°���� �����ϴ� Ű�� ���������� ����
            }else if(num%3==0){
                right = num;
                answer+="R";
            //��°���� �����ϴ� Ű�� �Ÿ��� ���� ó��
            }else{
                //�޼հ� �������� �������� ���� ����ư �Ÿ� ���
                int d1 = func(left,num);
                int d2 = func(right,num);
                
                //�޼��� �� ������ �޼�����
                if(d1<d2){
                    left = num;
                    answer+="L";
                //�������� �� ������ ����������
                }else if(d1>d2){
                    right = num;
                    answer+="R";
                //�Ÿ��� ������ �޼�����, ���������� ���ο� ���� ó��
                }else{
                    if(hand.equals("left")){
                        left = num;
                        answer+="L";
                    }else{
                        right = num;
                        answer+="R";                        
                    }
                }
            }
        }
        
        return answer;
    }
}