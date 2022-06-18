class Solution {
	//���� 1���� ������ �ݵ�� 1���� ������ �� �����Ƿ� ���� 2�����迭 ���� X
	//arr[x] = y�� ���� ������� ǥ���ϴµ� �̴�, (x,y) ��ġ�� ���� �������� �ǹ���
    public static int[] arr;
    
    //������ ��ġ���� ������ ����
    public static int sum = 0;
    
    //���밪�� ��ȯ�ϴ� �޼ҵ�
    public static int abs(int x){
        return x>0?x:x*-1;
    }
    
    //������ ���� ��ġ�ϴ� �޼ҵ�
    public static void diploy(int x){
    	//ü������ ������ ����� ����, ������� ������ ���� ��� N�� ��ġ�ϴµ� ������ ����
        if(x==arr.length){
            sum++;
            return;
        }
        //���� x�� ��ġ���� N���� Ž������ ���� ���� ��ġ�� �������� �ƴ��� �Ǵ���
        for(int i=0; i<arr.length; i++){
        	//(x,y) ��ġ�� ��ġ�� �����ϴٸ� ��ġ��
            if(canDiploy(x,i)){
                arr[x] = i;
                diploy(x+1);
            }
        }
    }
    
    //���� (x,y) ��ġ�� ��ġ�� �� �ִ��� �Ǵ��ϴ� �޼ҵ�
    public static boolean canDiploy(int x, int y){
    	//�ڽź��� ���ʿ� ��ġ�� ���� ���ؼ��� �Ǵ���
        for(int i=0; i<x; i++){
        	//�ڽŰ� ���� �࿡ �ִ� ���� �����ϸ� false
            if(arr[i]==y){
                return false;
            }
            //�ڽŰ� �»��, ���ϴ� ���� �밢�� ���� �ٸ� ���� ������ ��� false
            if(abs(arr[i]-y)==abs(i-x)){
                return false;
            }
        }
        //�ڽź��� ���ʿ� ��ġ�� ���� ���� ��� ������ �����ϸ� true
        return true;
    }
    
    public int solution(int n) {
        arr = new int[n];
        diploy(0);
        return sum;
    }
}