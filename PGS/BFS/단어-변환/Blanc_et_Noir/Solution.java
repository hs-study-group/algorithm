import java.util.*;

//문자열과 해당 문자열을 변환한 횟수를 기록하는 노드 클래스
class Node{
    String str;
    int cnt;
    Node(String str, int cnt){
        this.str = str;
        this.cnt = cnt;
    }
}

class Solution {
	//방문배열 대신 해시맵을 사용하여 방문처리
    public static HashMap<String,Integer> v;
    
    //두 문자열이 단 하나의 글자만 차이가나는지 아닌지 판단하는 메소드
    public static boolean isTransferable(String s1, String s2){
    	//두 문자열을 문자 배열로 변환함
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int cnt = 0;
        
        //서로 다른부분이 있을때마다 카운트함
        for(int i=0; i<c1.length; i++){
            if(c1[i]!=c2[i]){
                cnt++;
            }
        }
        
        //단 하나의 글자만 다르면 true
        if(cnt==1){
            return true;
        //둘 이상의 글자가 다르면 false
        }else{
            return false;
        }
    }
    
    //begin을 target으로 바꾸는 최소비용을 구하는 것이므로 DFS보다는 BFS로 푸는 것이 적합
    public static int BFS(String begin, String target, String[] words){
    	//방문처리용 해시맵 객체 선언
        v = new HashMap<String,Integer>();
        
        //BFS 탐색을 위한 큐 선언
        Queue<Node> q = new LinkedList<Node>();
        
        //해당 begin문자열부터 BFS탐색을 실시하도록 큐에 추가, 방문처리
        q.add(new Node(begin,0));
        v.put(begin,0);
        
        while(!q.isEmpty()){
            Node n = q.poll();
            
            //해당 문자열이 target과 같으면 BFS탐색 종료
            if(n.str.equals(target)){
                return n.cnt;
            }
            
            //아니라면
            for(int i=0; i<words.length; i++){
            	//아직 변환해본적 없는 결과물이며, 한 끗 차이라면
                if(!v.containsKey(words[i])&&isTransferable(n.str,words[i])){
                	//해당 문자열부터 다시 BFS탐색 실시
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