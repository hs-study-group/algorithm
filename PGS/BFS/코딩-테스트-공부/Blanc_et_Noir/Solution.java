import java.util.*;

class Node{
    //현재의 알고력, 코딩력, 사용한 비용 
    int alp, cop, cost;
    Node(int alp, int cop, int cost){
        this.alp = alp;
        this.cop = cop;
        this.cost = cost;
    }
}

class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        int answer = -1;
        int max_alp = Integer.MIN_VALUE, max_cop = Integer.MIN_VALUE;

        //BFS탐색에 필요한 방문배열
        //v[알고력][코딩력] = 해당 알고력, 코딩력에 도달하기위해 사용한 최소비용이 저장됨
        int[][] v = new int[500][500];

        //모든 문제를 풀기 위해서는 결국 모든 문제들의 알고력, 코딩력의 최대값
        //이상의 알고력, 코딩력을 겸비해야만 하므로 모든 문제에 대해
        //최고로 요구되는 알고력, 코딩력을 저장해둠
        for(int i=0; i<problems.length; i++){
            if(max_alp<problems[i][0]){
                max_alp = problems[i][0];
            }
            if(max_cop<problems[i][1]){
                max_cop = problems[i][1];
            }
        }

        //방문배열을 초기화함
        for(int i=0; i<v.length; i++){
            for(int j=0; j<v[0].length; j++){
                //사용한 비용을 정수의 최댓값으로 초기화해둠
                v[i][j] = Integer.MAX_VALUE;
            }
        }

        //BFS탐색에 사용할 우선순위큐, 비용이 적은 노드가 우선적으로 출력됨
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                if(n1.cost<n2.cost){
                    return -1;
                }else if(n1.cost>n2.cost){
                    return 1;
                }else{
                    return 0;
                }
            }
        });

        pq.add(new Node(alp,cop,0));
        v[alp][cop]=0;

        while(!pq.isEmpty()){
            Node n = pq.poll();

            //만약 모든 문제를 해결할 수 있는 알고력, 코딩력을 겸비했다면
            //그 즉시 BFS 탐색을 종료함
            if(n.alp >= max_alp && n.cop >= max_cop){
                answer = n.cost;
                break;
            }

            //단순히 알고력을 1만큼 증가시키는 알고리즘 공부를 할때
            //해당 알고력, 코딩력을 갖추기 위해 사용했던 기존 최소 비용보다
            //더 적은 비용으로 도달할 수 있다면
            if(v[n.alp+1][n.cop]>n.cost+1){
                //알고력 및 비용을 1증가시킨 노드를 기준으로 다시 탐색을 시작함
                pq.add(new Node(n.alp+1,n.cop,n.cost+1));
                //해당 알고력, 코딩력을 갖추기 위한 최소 비용을 갱신함
                v[n.alp+1][n.cop] = n.cost+1;
            }

            //단순히 코딩력을 1만큼 증가시키는 알고리즘 공부를 할때
            //해당 알고력, 코딩력을 갖추기 위해 사용했던 기존 최소 비용보다
            //더 적은 비용으로 도달할 수 있다면
            if(v[n.alp][n.cop+1]>n.cost+1){
                //코딩력 및 비용을 1증가시킨 노드를 기준으로 다시 탐색을 시작함
                pq.add(new Node(n.alp,n.cop+1,n.cost+1));
                //해당 알고력, 코딩력을 갖추기 위한 최소 비용을 갱신함
                v[n.alp][n.cop+1] = n.cost+1;
            }

            for(int i=0; i<problems.length; i++){
                //1. 해당 문제를 풀 수 있는 알고력, 코딩력을 겸비했고
                //2. 이 문제를 해결했을때의 최종 알고력, 코딩력에 대하여
                //   다른 방식보다 더 적은 비용으로 해당 알고력, 코딩력에 도달할 수 있다면
                if(n.alp>=problems[i][0]&&n.cop>=problems[i][1]&&v[n.alp+problems[i][2]][n.cop+problems[i][3]]>n.cost+problems[i][4]){
                    //알고력, 코딩력을 문제 해결의 보상만큼 증가시키고, 비용또한 문제해결에 소요되는 만큼 증가시킴
                    pq.add(new Node(n.alp+problems[i][2],n.cop+problems[i][3],n.cost+problems[i][4]));
                    //해당 알고력, 코딩력에 도달하기 위한 최소 비용을 갱신함
                    v[n.alp+problems[i][2]][n.cop+problems[i][3]]=n.cost+problems[i][4];
                }
            }
        }

        return answer;
    }
}