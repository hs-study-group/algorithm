class Solution {
    public static int max;
    
    //DFS탐색을 통해 완전탐색을 실시하는 메소드
    public static void DFS(int[][] dungeons, boolean[] v, int stamina, int cnt){
    	//만약 던전에 입장한 횟수가 기존 최대치보다 크면
        if(cnt>max){
        	//그것을 최대치로 갱신함
            max = cnt;
        }
        //모든 던전에 대하여 탐색실시
        for(int i=0; i<dungeons.length; i++){
        	//만약 방문하지 않았던 던전이라면
            if(!v[i]){
            	//현재 피로도가 던전 입장에 필요한 최소 피로도보다 많은 경우
                if(stamina >= dungeons[i][0]){
                	//해당 던전에 입장한 것으로 처리하고 다시 DFS탐색 수행
                	v[i] = true;
                    DFS(dungeons,v,stamina-dungeons[i][1],cnt+1);
                    
                    //DFS 종료시에 마치 해당 던전을 방문하지 않았던 것처럼 처리해야함
                    v[i] = false;
                }
            }
        }
    }
    
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        max = 0;
        DFS(dungeons, new boolean[dungeons.length],k,0);
        return max;
    }
}