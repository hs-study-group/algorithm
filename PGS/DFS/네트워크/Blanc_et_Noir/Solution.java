class Solution {
    //s������ ����Ǿ��ִ� ���� DFSŽ���� �ǽ�
    public static void DFS(int[][] computers, boolean[] v, int s){
        //�ڱ��ڽ��� �湮���� ǥ��
        v[s] = true;
        
        //�ڱ��ڽ��� �̹� �湮ó�� �Ǿ������Ƿ� ���ٸ� ó�� X
        for(int i=0; i<computers.length; i++){
            //������ ��尡 ���� �湮���� ���� ����� �ش� ������ DFSŽ�� �ǽ�
            if(!v[i]&&computers[s][i]==1){
                DFS(computers, v, i);
            }
        }
    }
    
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        //�� ����� �湮 ���θ� ǥ���� �迭
        boolean[] v = new boolean[n];
        
        for(int i=0; i<n; i++){
            //DFSŽ�� ���Ŀ��� ���� �湮���� ���� ���鿡 ���ؼ� DFSŽ�� �ǽ�
            if(!v[i]){
                DFS(computers, v, i);
                answer++;
            }
        }
        
        return answer;
    }
}