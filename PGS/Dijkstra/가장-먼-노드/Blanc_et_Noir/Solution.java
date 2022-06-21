import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//해당 노드까지 탐색하기까지 소요한 비용을 저장하는 노드 클래스
class Node{
    int index, dist;
    Node(int index, int dist){
        this.index = index;
        this.dist = dist;
    }
}

class Solution {
    //다익스트라 수행 결과를 저장하는 배열
    public static int[] d;
    
    //그래프 정보를 저장할 2차원 ArrayList 객체 선언
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
    
    public int solution(int n, int[][] edge) {
        
        //다익스트라 수행 결과를 저장할 배열을 생성하고, 모두 가장 큰 값으로 초기화함
        d = new int[n];
        Arrays.fill(d,Integer.MAX_VALUE);
        
        //다익스트라 알고리즘 사용에 필요한 우선순위큐 선언
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
            //두 노드에 대하여 더 작은 비용정보를 가진 노드가 우선적으로 반환됨
            @Override
            public int compare(Node n1, Node n2){
                if(n1.dist<n2.dist){
                    return -1;
                }else if(n1.dist>n2.dist){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        
        //그래프를 생성함
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<Node>());
        }
        
        //전달받은 간선 정보로 그래프를 구성함
        for(int i=0; i<edge.length; i++){
            graph.get(edge[i][0]-1).add(new Node(edge[i][1]-1,1));
            graph.get(edge[i][1]-1).add(new Node(edge[i][0]-1,1));
        }
        
        //시작 노드는 항상 1번으로 고정되어있으며, 비용을 0으로 초기화함
        pq.add(new Node(0,0));
        
        //자기 자신에 대한 최소비용은 0임
        d[0] = 0;
        
        while(!pq.isEmpty()){
            Node node = pq.poll();
            
            //해당 노드까지 탐색하며 사용한 비용이 최소비용보다 많다면
            //더이상 탐색할 필요가 없으므로 탐색을 중지함
            if(d[node.index]<node.dist){
                continue;
            }

            int now = node.index;
            
            //인접한 노드에 대하여
            for(int i=0; i<graph.get(now).size(); i++){
                //현재 위치한 노드까지의 최소비용 + 인접한 노드로 이동하는 비용을 계산함
                int cost = d[now] + graph.get(now).get(i).dist;
                int next = graph.get(now).get(i).index;
                
                //만약 앞서 계산한 비용이 인접한 노드로 가는데 필요한 최소 비용보다 더 작다면
                //해당 비용을 최소비용으로 간주하고, 인접한 노드부터 다시 탐색함
                if(cost<d[graph.get(now).get(i).index]){
                    d[next] = cost;
                    pq.add(new Node(next,cost));
                }
            }
        }
        
        //다익스트라 수행결과를 파악하여 비용이 최대가 되는 노드들의 수를 체크함
        int max = d[0];
        int cnt = 1;
        
        for(int i=1; i<d.length; i++){
            //기존의 최대비용보다 더 큰 비용이 필요하다면
            if(d[i]>max){
                //해당 비용을 최대비용으로 간주하고, 다시 처음부터 카운트를 함
                max = d[i];
                cnt = 1;
            //기존 최대비용과 똑같은 비용이라면 카운트함
            }else if(max==d[i]){
                cnt++;
            }
        }
        return cnt;
    }
}