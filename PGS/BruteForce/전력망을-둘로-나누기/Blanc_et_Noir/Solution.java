import java.util.*;

class Solution {
	//하나의 간선에 포함된 두 정점 v1, v2에 대하여, v1과 v2의 간선을 제거하고
	//v1과 연결된 정점의 개수를 리턴하는 메소드
    public static int query(ArrayList<ArrayList<Integer>> graph, int v1, int v2){
        int cnt = 1;
        boolean[] v = new boolean[graph.size()];

        //기본적으로는 BFS탐색을 통하여 정점의 개수를 구할 것이므로 큐를 선언함
        Queue<Integer> q = new LinkedList<Integer>();
        
        //v1에 포함되는 영역만 탐색할 것이므로 v1만 큐에 추가함
        q.add(v1);
        
        //v1, v2모두 방문한 것으로 처리함으로써 v2방향으로는 탐색하지 않도록 함
        v[v1] = true;
        v[v2] = true;
        
        //아직 탐색할 정점이 있다면 계속 탐색함
        while(!q.isEmpty()){
        	//현재 정점을 얻음
            int c = q.poll();
            
            //현재 정점과 연결된 다른 정점들을 탐색함
            for(int i=0; i<graph.get(c).size(); i++){
            	//인접한 정점을 얻음
                int n = graph.get(c).get(i);
                
                //아직 인접한 정점을 방문한 적이 없다면
                if(!v[n]){
                	//해당 인접한 정점을 방문함
                    q.add(n);
                    v[n] = true;
                    
                    //포함된 그룹원의 개수를 1증가시킴
                    cnt++;
                }
            }
        }
        
        //그룹에 포함된 정점의 개수를 리턴함
        return cnt;
    }
    
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        
        //간선 정보를 저장할 2차원 어레이 리스트 선언
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        
        //어레이 리스트 초기화
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<Integer>());
        }
        
        //어레이 리스트에 간선정보를 양방향으로 저장함
        for(int i=0; i<wires.length; i++){
            graph.get(wires[i][0]-1).add(wires[i][1]-1);
            graph.get(wires[i][1]-1).add(wires[i][0]-1);
        }
        
        //간선정보를 순차적으로 탐색함
        for(int i=0; i<wires.length; i++){
        	//어떤 간선을 선택하여 그 간선을 제거하고, 그 간선에 포함된 두 정점의 각각의 그룹원의 개수를 구하고
        	//그 그룹원의 개수의 최소값을 누적하여 구함
            answer = Math.min(answer,Math.abs(query(graph, wires[i][0]-1,wires[i][1]-1)-query(graph, wires[i][1]-1,wires[i][0]-1)));
        }
        
        return answer;
    }
}