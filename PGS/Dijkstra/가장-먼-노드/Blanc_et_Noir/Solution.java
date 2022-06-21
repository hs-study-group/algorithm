import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//�ش� ������ Ž���ϱ���� �ҿ��� ����� �����ϴ� ��� Ŭ����
class Node{
    int index, dist;
    Node(int index, int dist){
        this.index = index;
        this.dist = dist;
    }
}

class Solution {
    //���ͽ�Ʈ�� ���� ����� �����ϴ� �迭
    public static int[] d;
    
    //�׷��� ������ ������ 2���� ArrayList ��ü ����
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>();
    
    public int solution(int n, int[][] edge) {
        
        //���ͽ�Ʈ�� ���� ����� ������ �迭�� �����ϰ�, ��� ���� ū ������ �ʱ�ȭ��
        d = new int[n];
        Arrays.fill(d,Integer.MAX_VALUE);
        
        //���ͽ�Ʈ�� �˰��� ��뿡 �ʿ��� �켱����ť ����
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
            //�� ��忡 ���Ͽ� �� ���� ��������� ���� ��尡 �켱������ ��ȯ��
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
        
        //�׷����� ������
        for(int i=0; i<n; i++){
            graph.add(new ArrayList<Node>());
        }
        
        //���޹��� ���� ������ �׷����� ������
        for(int i=0; i<edge.length; i++){
            graph.get(edge[i][0]-1).add(new Node(edge[i][1]-1,1));
            graph.get(edge[i][1]-1).add(new Node(edge[i][0]-1,1));
        }
        
        //���� ���� �׻� 1������ �����Ǿ�������, ����� 0���� �ʱ�ȭ��
        pq.add(new Node(0,0));
        
        //�ڱ� �ڽſ� ���� �ּҺ���� 0��
        d[0] = 0;
        
        while(!pq.isEmpty()){
            Node node = pq.poll();
            
            //�ش� ������ Ž���ϸ� ����� ����� �ּҺ�뺸�� ���ٸ�
            //���̻� Ž���� �ʿ䰡 �����Ƿ� Ž���� ������
            if(d[node.index]<node.dist){
                continue;
            }

            int now = node.index;
            
            //������ ��忡 ���Ͽ�
            for(int i=0; i<graph.get(now).size(); i++){
                //���� ��ġ�� �������� �ּҺ�� + ������ ���� �̵��ϴ� ����� �����
                int cost = d[now] + graph.get(now).get(i).dist;
                int next = graph.get(now).get(i).index;
                
                //���� �ռ� ����� ����� ������ ���� ���µ� �ʿ��� �ּ� ��뺸�� �� �۴ٸ�
                //�ش� ����� �ּҺ������ �����ϰ�, ������ ������ �ٽ� Ž����
                if(cost<d[graph.get(now).get(i).index]){
                    d[next] = cost;
                    pq.add(new Node(next,cost));
                }
            }
        }
        
        //���ͽ�Ʈ�� �������� �ľ��Ͽ� ����� �ִ밡 �Ǵ� ������ ���� üũ��
        int max = d[0];
        int cnt = 1;
        
        for(int i=1; i<d.length; i++){
            //������ �ִ��뺸�� �� ū ����� �ʿ��ϴٸ�
            if(d[i]>max){
                //�ش� ����� �ִ������� �����ϰ�, �ٽ� ó������ ī��Ʈ�� ��
                max = d[i];
                cnt = 1;
            //���� �ִ���� �Ȱ��� ����̶�� ī��Ʈ��
            }else if(max==d[i]){
                cnt++;
            }
        }
        return cnt;
    }
}