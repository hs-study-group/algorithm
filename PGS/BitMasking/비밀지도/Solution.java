//https://programmers.co.kr/learn/courses/30/lessons/17681

class Solution {
	
	//���� �� ���� ���� n, �� ������ �� ���� or�� ����� b�� ���޹޾�
	//�׿� ���� ������ �������� ����� �����ϴ� �޼ҵ�
    public String func(int n, int b){
        String r = "";
        int m = 1;
        
        //MSB���� LSB���� ���ʷ� ��Ʈ�� Ž���ϸ�
        //�׿� ���� �� �Ǵ� ������ ��������
        for(int i=n-1; i>=0; i--){
            if((b&(m<<i))!=0){
                r+="#";
            }else{
                r+=" ";
            }
        }
        return r;
    }
    
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        
        int[] arr3 = new int[n];
        
        //�� ������ ���� or������ ������
        //�� ������ �ּ� ��� �ϳ��� ���̶�� ǥ�õǾ������� 1
        //�� ���� ��� ������ 0����Ÿ���� ���� ������ ������ 0�̱� ����
        for(int i=0; i<n; i++){
            arr3[i] = arr1[i]|arr2[i];
            answer[i] = func(n,arr3[i]);
        }

        return answer;
    }
}