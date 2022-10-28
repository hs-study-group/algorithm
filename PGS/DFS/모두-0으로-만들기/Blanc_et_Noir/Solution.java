import java.util.*;

class Solution {
    public static long answer;
    
    public static long DFS(ArrayList<ArrayList<Integer>> graph,boolean[] v,int[] a, int idx){
        long sum = a[idx];
        long cnt = 0;
        
        //부모노드에 대하여 자식노드를 탐색함
        for(int i=0; i<graph.get(idx).size(); i++){
            int next = graph.get(idx).get(i);
            
            //인접한 자식노드에 방문한 적이 없다면
            if(!v[next]){
            	//자식노드를 방문한 것으로 처리함
                v[next] = true;
                
                //자식 노드에 대하여 재귀적으로 탐색을 수행함
                long val = DFS(graph, v, a, next);
                
                //부모노드를 기준으로 재귀탐색한 이후 얻은 결과값의 절대값을 누적하여 더함, 이는 이동하는데 필요한 비용임
                cnt += Math.abs(val);
                
                //재귀적으로 얻은 합의 결과만큼 이동해야 하므로 누적하여 계산함
                //가령 현재 노드와 인접한 자식 노드가 -3, 2, 4의 값을 가지고 있다면
                //자식 노드들의 값을 부모노드로 옮기는데 필요한 비용 cnt = (3 + 2 + 4)이며
                //부모노드가 자신의 부모노드에게 값을 전달하는데 필요한 비용 sum = (-3 + 2 + 4)임
                sum += val;
            }
        }

        //cnt만큼의 비용을 소모하여 자식노드의 값을 부모노드로 이동시킴
        answer += cnt;
        
        //부모노드가 자신의 부모노드에게 전달해야할 값은 절대값을 고려하지 않은 단순 누적합만큼을 전달해야함
        return sum;
    }
    
    public long solution(int[] a, int[][] edges) {
        answer = 0;
        
        //간선 정보를 저장받을 이중 어레이리스트 선언
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        long sum = 0;
        
        //간선정보를 입력받을 리스트 초기화
        for(int i=0; i<a.length; i++){
            graph.add(new ArrayList<Integer>());
            sum += a[i];
        }
        
        //간선정보를 입력받음
        for(int i=0; i<edges.length; i++){
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }
        
        //만약 모든 값들의 합이 0이 아니라면, 절대로 0을 만들 수 없다는 의미이므로 -1을 리턴함
        if(sum!=0){
            return -1;
        //만약 모두 합하여 0으로 만들수 있다면
        }else{
        	//첫번째 간선의 정점중 하나를 기준 정점으로 삼아서 모두 해당 정점으로 값을 전달하도록 함
            DFS(graph,new boolean[a.length], a, edges[0][0]);
        }
        
        return answer;
    }
}