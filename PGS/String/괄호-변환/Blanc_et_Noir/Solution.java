import java.util.Stack;

class Solution {
    //�ش� ���ڿ��� �ùٸ� ��ȣ ���ڿ����� �Ǵ��ϴ� �޼ҵ�
    public static boolean isGood(String p){
        //���ڿ��� ���ڹ迭�� ��ȯ
        char[] arr = p.toCharArray();
        
        //�ش� ��ȣ�� ¦�� �´��� �Ǵ��ϱ����� ���� ���
        Stack<Character> s = new Stack<Character>();
        
        //���ڸ� �ϳ��� Ž����
        for(int i=0; i<arr.length; i++){
            //���� ��ȣ��� ���ÿ� �߰���
            if(arr[i]=='('){
                s.push('(');
            //�ݴ� ��ȣ���
            }else{
                //������ ��������� ¦�� ���� �ʴ� ����
                if(s.isEmpty()){
                    return false;
                //���ÿ� ���� ��ȣ�� �ִٸ� �ش� ��ȣ�� ����
                }else{
                    s.pop();
                }
            }
        }
        //Ž���� �Ϸ�� �Ŀ� ������ ������� �ʴٸ�
        //���� ��ȣ�� ���� ���� ��ȣ ¦�� ������ ���̹Ƿ� false
        if(!s.isEmpty()){
            return false;
        //��� ��ȣ�� ¦�� �����Ƿ� true
        }else{
            return true;
        }
    }
    
    //�������� ��ȣ ���ڿ����� �ƴ��� �Ǵ��ϴ� �޼ҵ�
    //�ܼ��� ���� ��ȣ�� �ݴ� ��ȣ�� ���ڰ� �������� �ƴ����� �Ǵ��ϸ� ��
    public static boolean isBalanced(String p){
        //�ش� ���ڿ��� ���� �迭�κ�ȯ��
        char[] arr = p.toCharArray();
        
        //���� ��ȣ�� �ݴ� ��ȣ�� ������ ���̸� ������ ����
        int cnt = 0;
        
        for(int i=0; i<arr.length; i++){
            //���� ��ȣ��� 1�� ������Ŵ
            if(arr[i]=='('){
                cnt++;
            //�ݴ� ��ȣ��� 1�� ���ҽ�Ŵ
            }else{
                cnt--;
            }
        }
        //cnt���� 0�̸�, �� ���� ��ȣ�� �ݴ� ��ȣ�� ���� ���̰� 0�̸�
        if(cnt==0){
            //true�� ������
            return true;
        }else{
            //�ƴϸ� false�� ������
            return false;
        }
    }
    
    public static String process(String p){
        //�� ���ڿ��̸� �״�� ������
        if(p.equals("")){
            return p;
        }
        //�ش� ���ڿ����� �� ���ڿ� u, v�� �����ϱ� ���� Ž����
        for(int i=2; i<=p.length(); i=i+2){
            String t = p.substring(0,i);
            //���� ���̻� �и��� �� ���� �������� ��ȣ�� ã�Ҵٸ�
            if(isBalanced(t)){
                //�ش� ���ڿ��� u, ������ ���ڿ��� v�� ������
                String u = p.substring(0,i);
                String v = p.substring(i,p.length());
                //u�� �ùٸ� ��ȣ ���ڿ��̶��
                if(isGood(u)){
                    //v�� ���� ��������� ó���� ������ ����� ������
                    return u + process(v);
                //u�� �������� ��ȣ ���ڿ��̱��ϳ�, �ùٸ� ��ȣ ���ڿ��� �ƴ� ���
                }else{
                    //�� ���ڿ��� (���ڸ� �߰���
                    t = "(";
                    //�ű⿡ v�� ���� ��������� ó���� ������ ����� ������
                    t = t + process(v);
                    //�ݴ� ��ȣ�� ������
                    t = t + ")";
                    //u�� ���Ͽ� ���۰� �� ��ȣ�� ������ ������ ��ȣ�� ������Ų ����� ������
                    t = t + reverse(u.substring(1,u.length()-1));
                    return t;
                }
            }
        }
        return "";
    }
    
    //Ư�� ��ȣ ���ڿ��� ��ȣ���� ������ ������Ű�� �޼ҵ�
    public static String reverse(String p){
        //�ش� ���ڿ��� ���� �迭�� ��ȯ��
        char[] arr = p.toCharArray();
        String r = "";
        
        //�� ��ȣ�� ���Ͽ� �Ʒ��� ���� ó���� ������
        for(int i=0; i<arr.length; i++){
            //���� ��ȣ��� �ݴ� ��ȣ�� ��ȯ
            if(arr[i]=='('){
                r += ")";
            //�ݴ� ��ȣ��� ���� ��ȣ�� ��ȯ
            }else{
                r += "(";
            }
        }
        return r;
    }
    
    public String solution(String p) {
        return process(p);
    }
}