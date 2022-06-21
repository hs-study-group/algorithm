import java.util.*;

//���ڿ��� �ش� ���ڿ��� ��ȯ�� Ƚ���� ����ϴ� ��� Ŭ����
class Node{
    String str;
    int cnt;
    Node(String str, int cnt){
        this.str = str;
        this.cnt = cnt;
    }
}

class Solution {
	//�湮�迭 ��� �ؽø��� ����Ͽ� �湮ó��
    public static HashMap<String,Integer> v;
    
    //�� ���ڿ��� �� �ϳ��� ���ڸ� ���̰������� �ƴ��� �Ǵ��ϴ� �޼ҵ�
    public static boolean isTransferable(String s1, String s2){
    	//�� ���ڿ��� ���� �迭�� ��ȯ��
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int cnt = 0;
        
        //���� �ٸ��κ��� ���������� ī��Ʈ��
        for(int i=0; i<c1.length; i++){
            if(c1[i]!=c2[i]){
                cnt++;
            }
        }
        
        //�� �ϳ��� ���ڸ� �ٸ��� true
        if(cnt==1){
            return true;
        //�� �̻��� ���ڰ� �ٸ��� false
        }else{
            return false;
        }
    }
    
    //begin�� target���� �ٲٴ� �ּҺ���� ���ϴ� ���̹Ƿ� DFS���ٴ� BFS�� Ǫ�� ���� ����
    public static int BFS(String begin, String target, String[] words){
    	//�湮ó���� �ؽø� ��ü ����
        v = new HashMap<String,Integer>();
        
        //BFS Ž���� ���� ť ����
        Queue<Node> q = new LinkedList<Node>();
        
        //�ش� begin���ڿ����� BFSŽ���� �ǽ��ϵ��� ť�� �߰�, �湮ó��
        q.add(new Node(begin,0));
        v.put(begin,0);
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //�ش� ���ڿ��� target�� ������ BFSŽ�� ����
            if(n.str.equals(target)){
                return n.cnt;
            }
            
            //�ƴ϶��
            for(int i=0; i<words.length; i++){
            	//���� ��ȯ�غ��� ���� ������̸�, �� �� ���̶��
                if(!v.containsKey(words[i])&&isTransferable(n.str,words[i])){
                	//�ش� ���ڿ����� �ٽ� BFSŽ�� �ǽ�
                    q.add(new Node(words[i],n.cnt+1));
                    v.put(words[i],n.cnt+1);
                }
            }
        }
        return 0;
    }
    public int solution(String begin, String target, String[] words) {
        int answer = 0;
        
        answer = BFS(begin,target,words);
        
        return answer;
    }
}