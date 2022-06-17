import java.util.*;

//인접한 두 정점 v1, v2, 그 사이의 간선 비용 c를 저장하는 Edge 클래스
class Edge{
    int v1, v2, c;
    Edge(int v1, int v2, int c){
        this.v1 = v1;
        this.v2 = v2;
        this.c = c;
    }
}

class Solution {
	//각 정점의 부모 정점을 기록할 배열
    public static int[] parents;
    
    //각 간선들의 정보를 담아둘 리스트
    public static ArrayList<Edge> edges = new ArrayList<Edge>();
    
    //특정 정점에 대해 부모 정점을 재귀적으로 찾는 메소드
    public static int find(int x){
    	//자기 자신이 부모라면 자신을 리턴함
        if(parents[x] == x){
            return x;
        //자기 자신이 부모가 아니라면, 자신의 부모의 부모를 찾음
        //그후 자신의 부모를 새로이 갱신함
        }else{
            return parents[x] = find(parents[x]);
        }
    }
    
    //두 정점을 연결하는 메소드
    public static void union(int x, int y){
    	//기본적으로 y가 x보다 더 크다고 가정
    	//만약 작다면 순서를 변경함
        if(x>y){
            int t = x;
            x = y;
            y = t;
        }
        
        //x, y의 부모를 찾음
        x = find(x);
        y = find(y);
        
        //부모가 다르다면 더 큰 y의 부모를 더 작은 x로 설정함
        if(x!=y){
            parents[y] = x;
        }
    }
    
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        
        parents = new int[n];
        
        //각 노드의 부모가 자기 자신이 되도록 초기화
        for(int i=0; i<n; i++){
            parents[i] = i;
        }
        
        //간선 정보들을 리스트에 추가함
        for(int i=0; i<costs.length; i++){
            edges.add(new Edge(costs[i][0],costs[i][1],costs[i][2]));
        }
        
        //MST를 구현할때에는 가장 적은 비용의 간선 정보가 먼저 반환되도록 해야함
        Collections.sort(edges, new Comparator<Edge>(){
            @Override
            public int compare(Edge e1, Edge e2){
                if(e1.c<e2.c){
                    return -1;
                }else if(e1.c>e2.c){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        for(int i=0; i<edges.size(); i++){
        	//가장 비용이 적은 간선정보 하나를 가져옴
            Edge e = edges.get(i);
            
            //해당 간선에서 두 정점의 부모가 서로 다르다면
            if(find(e.v1)!=find(e.v2)){
            	//두 정점을 연결하고 해당 비용을 결과에 더해줌
                answer += e.c;
                union(e.v1,e.v2);
            }
        }
        
        return answer;
    }
}