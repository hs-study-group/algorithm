import java.util.*;

class Solution {
    public static String[] arr = {"A", "C", "F", "J", "M", "N", "R", "T"};
    public static int cnt = 0;
    
    //������ ���밪�� ��ȯ�ϴ� �޼ҵ�
    public static int abs(int n){
        return n>=0?n:n*(-1);
    }
    
    //8P8 ������ ���ϰ� �ش� ������ �����ϴ� �� ��ҵ��� �ε����� �����ѵ�
    //data�迭�� �־��� ���ǵ��� �����ϴ��� Ȯ���Ͽ�, �����Ҷ� cnt���� ������Ű�� �޼ҵ�
    public static void permutate(String[] data, String s, boolean[] v, int n){
        
        //������ ������ �ϳ��� ���ߴٸ� �Ʒ��� ���ǹ� ����
        if(n==0){
            //�� ��ҵ��� �ε����� �ӽ÷� ������ �ؽø�
            HashMap<Character,Integer> idx = new HashMap<Character,Integer>();
            char[] temp = s.toCharArray();
            
            //�� ��ҵ��� �ε����� �ؽøʿ� ������
            for(int i=0; i<temp.length; i++){
                idx.put(temp[i],i);
            }
            
            for(int i=0; i<data.length; i++){
            	char[] arr = data[i].toCharArray();
                
                //���ǿ� ���õ� �� ����� �Ÿ��� �����
            	int dist = abs(idx.get(arr[0])-idx.get(arr[2]))-1;
            	
                //�־��� ������ �Ÿ��� Ŀ���� ��
            	if(arr[3]=='>') {
                    //�ش� ������ �������� ������ �״�� �޼ҵ� ����
            		if(dist<=arr[4]-'0') {
            			return;
            		}
                //�־��� ������ �Ÿ��� �۾ƾ��� ��
            	}else if(arr[3]=='<') {
                    //�ش� ������ �������� ������ �״�� �޼ҵ� ����
            		if(dist>=arr[4]-'0') {
            			return;
            		}
                //�־��� ���� �Ÿ��� ���ƾ� �� ��
            	}else {
                    //�ش� ������ �������� ������ �״�� �޼ҵ� ����
            		if(dist!=arr[4]-'0') {
            			return;
            		}
            	}
            }
            //data�� �־��� ��� ������ �����Ҷ��� cnt���� 1 ������Ű�� �޼ҵ� ����
            cnt++;
            return;
        }
        
        //������ ���ϱ� ���� �ݺ���
        for(int i=0; i<arr.length; i++){
            //�ڱ� �ڽ��� �湮�� ���� ������ ���� ��� �湮���� ����
            if(v[i]){
                continue;
            }
            //�湮 ǥ�ø� �ϰ� ������ ����ؼ� ���س���
            v[i] = true;
            permutate(data,s+arr[i],v,n-1);
            
            //��Ͱ� ����Ǹ� �湮ǥ�ø� ���������ν� ������ ���� �� ����
            v[i] = false;
        }    
    }
    
    public int solution(int n, String[] data) {
        int answer = 0;
        
        //��Ȯ�� ������ �𸣳�, ���α׷��ӽ� ä���� cnt�� �ݵ�� 0���� �ѹ� �� �ʱ�ȭ
        //�ؾ߸� ����� ä���� �Ǵ� ���� Ȯ���� �� �־���
        cnt = 0;
        
        permutate(data,"",new boolean[8],8);
        answer = cnt;
        
        return answer;
    }
}