import java.util.Arrays;
import java.util.HashSet;

class Solution {
    //�ش� ���ڿ��� banned_id���İ� ��ġ�ϴ��� �Ǵ��ϴ� �޼ҵ�
    public static boolean isBannedId(String user_id, String banned_id){
        //���ڿ��� ���̰� ���� �ٸ��� ������ false
        if(user_id.length()!=banned_id.length()){
            return false;
        //���ڿ��� ���̰� ���� ������
        }else{
            //�� ���ڿ��� ���� �迭�� ��ȯ�ϰ�
            char[] arr1 = user_id.toCharArray();
            char[] arr2 = banned_id.toCharArray();
            
            //�� ���ھ� Ž���س����µ�
            for(int i=0; i<arr1.length; i++){
                //���� *���ڶ��
                if(arr2[i]=='*'){
                    //� ���ڰ� ������ ��� �����Ƿ� continue
                    continue;
                //���� *���ڰ� �ƴ϶��
                }else{
                    //���� ��ġ�Ѵٸ� continue
                    if(arr1[i]==arr2[i]){
                        continue;
                    //���� ��ġ���� �ʴ´ٸ� �ش� banned_id ���Ŀ� ���� �ʴ� ���̹Ƿ� false
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static HashSet<String> set = new HashSet<String>();
    
    public static void DFS(String[] user_id, String[] banned_id, String out, boolean[] v, int idx, int cnt){
        //������ ��� ��������
        if(cnt==0){
            String str = "";
            String[] arr = out.split(" ");
            
            //�ش� ���ڿ� ������ ���� ������ ��� ��ġ�ϴ��� Ȯ��
            for(int i=1; i<arr.length; i++){
                //�ϳ��� ������ ��ġ���� ������ �߸� ���Ѱ��̹Ƿ� return
                if(!isBannedId(arr[i],banned_id[i-1])){
                    return;
                }
            }
            
            //������� �����Ѱ��̸� �ش� ���ڿ� ������ ������ banned_id�� ��� ��ġ�� ���̸�
            //�̶� ���� �ٸ� ���ڿ� �����̶� �ϴ��� ������ü�� banned_id�� ���� ���� �� ����
            
            //������� frodo crodo������ crodo frodo ������ ���
            //***** ��� banned_id ���Ŀ� ����������
            //�ᱹ ���� *****��� ���Ŀ� �´� ���� �����ϹǷ� �ߺ��� ������ ó���ϱ����ؼ�
            //�ش� ������ ������
            
            Arrays.sort(arr);
            
            //���ĵ� ������ ���ڿ����� �ϳ��� ��ħ
            for(int i=1; i<arr.length; i++){
                str += arr[i]+" ";
            }
            //��������� frodo crodo ������ crodo frodo������ ���
            //crodo frodo ��� ���·� �ش� banned_id �� ������ ����
            //�ߺ��� ����������� set�� ������
            set.add(str);
        }else{
            //������ ����ϰ��� �ݺ���
            for(int i=0; i<user_id.length; i++){
                //���� ������������ ���ڿ��̶��
                if(!v[i]){
                    v[i] = true;
                    
                    //DFS ���� Ž���� ������
                    //�̶� out + " " + user_id[i]�� ���ؼ�
                    //ó�� ���ڿ� �ϳ��� �����Ҷ��� " "��� ������ ���� �� �տ� ���Ƿ�
                    //63�� ���ο��� �̸� �Ű�Ἥ ����������� �ݺ��ϵ��� i=1�� ������
                    DFS(user_id,banned_id,out+" "+user_id[i],v,idx+1,cnt-1);
                    
                    v[i] = false;
                }
            }
        }
    }
    
    public int solution(String[] user_id, String[] banned_id) {
        int answer;

        DFS(user_id, banned_id, "", new boolean[user_id.length],0, banned_id.length);
        
        //������ ũ�Ⱑ �� ������
        answer = set.size();
        
        return answer;
    }
}