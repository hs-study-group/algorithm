class Solution {
    //s노드부터 연결되어있는 노드로 DFS탐색을 실시
    public static void DFS(int[][] computers, boolean[] v, int s){
        //자기자신을 방문으로 표시
        v[s] = true;
        
        //자기자신은 이미 방문처리 되어있으므로 별다른 처리 X
        for(int i=0; i<computers.length; i++){
            //인접한 노드가 아직 방문하지 않은 노드라면 해당 노드부터 DFS탐색 실시
            if(!v[i]&&computers[s][i]==1){
                DFS(computers, v, i);
            }
        }
    }
    
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        //각 노드의 방문 여부를 표시할 배열
        boolean[] v = new boolean[n];
        
        for(int i=0; i<n; i++){
            //DFS탐색 이후에도 아직 방문하지 않은 노드들에 대해서 DFS탐색 실시
            if(!v[i]){
                DFS(computers, v, i);
                answer++;
            }
        }
        
        return answer;
    }
}