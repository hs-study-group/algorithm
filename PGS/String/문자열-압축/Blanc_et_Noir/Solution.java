class Solution {
    public int solution(String s) {
        int answer = 0;
        int len = s.length();
        int div = len/2;
        
        //���ڿ� �������� ���̴� �ּ��� ���� ���ڿ��� ���̺��ٴ� �۰ų� ���� ����
        int min = len;
        
        for(int i=div; i>=1; i--){
        	//prev�� Ž���� ������ �Ǵ� ���ڿ�
            String prev = s.substring(0,i);
            String result="";
            
            //���ڿ��� �ݺ��Ǵ� Ƚ���� ����� ����
            int cnt=1;
            
            for(int j=i; j+i<len+1; j=j+i){
            	
            	//next�� �����̵Ǵ� ���ڿ� ������ ���ڿ����� i��ŭ�� ������ �ξ� ����
                String next = s.substring(j,j+i);
                
                //���ع��ڿ��� ��ġ�Ѵٸ� �ݺ��� Ƚ���� 1 ������Ŵ
                if(prev.equals(next)){
                    cnt++;
                }else{
                	//���� 1���� �ݺ����� �����ٸ� ���� ������ Ƚ�� 1�� ǥ���� �ʿ䰡 ����
                    if(cnt==1){
                        result += prev;
                    }else{
                        result += cnt + prev;
                    }
                    //������ �Ǵ� ���ڿ��� ������
                    prev = next;
                    cnt=1;
                }
                
                //���� i���� ũ�Ⱑ ���� ���ڿ�, �� ������ ���ڿ��� ����������
                if(j+i*2>=len+1){
                    //�������� Ž���ߴ� ���ڿ��� ������ ���ڿ��� ������
                    if(cnt==1){
                        result += next+s.substring(j+i,len);
                    }else{
                        result += cnt+next+s.substring(j+i,len);
                    }
                    
                    //�� ���ڿ��� ���̰� �ּҰ����� ������ ������
                    if(min>result.length()){
                        min = result.length();
                    }
                    break;
                }
            }
        }
        answer = min;
        return answer;
    }
}