import java.util.*;

//������ �� ���� v1, v2, �� ������ ���� ��� c�� �����ϴ� Edge Ŭ����
class Edge{
    int v1, v2, c;
    Edge(int v1, int v2, int c){
        this.v1 = v1;
        this.v2 = v2;
        this.c = c;
    }
}

class Solution {
	//�� ������ �θ� ������ ����� �迭
    public static int[] parents;
    
    //�� �������� ������ ��Ƶ� ����Ʈ
    public static ArrayList<Edge> edges = new ArrayList<Edge>();
    
    //Ư�� ������ ���� �θ� ������ ��������� ã�� �޼ҵ�
    public static int find(int x){
    	//�ڱ� �ڽ��� �θ��� �ڽ��� ������
        if(parents[x] == x){
            return x;
        //�ڱ� �ڽ��� �θ� �ƴ϶��, �ڽ��� �θ��� �θ� ã��
        //���� �ڽ��� �θ� ������ ������
        }else{
            return parents[x] = find(parents[x]);
        }
    }
    
    //�� ������ �����ϴ� �޼ҵ�
    public static void union(int x, int y){
    	//�⺻������ y�� x���� �� ũ�ٰ� ����
    	//���� �۴ٸ� ������ ������
        if(x>y){
            int t = x;
            x = y;
            y = t;
        }
        
        //x, y�� �θ� ã��
        x = find(x);
        y = find(y);
        
        //�θ� �ٸ��ٸ� �� ū y�� �θ� �� ���� x�� ������
        if(x!=y){
            parents[y] = x;
        }
    }
    
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        
        parents = new int[n];
        
        //�� ����� �θ� �ڱ� �ڽ��� �ǵ��� �ʱ�ȭ
        for(int i=0; i<n; i++){
            parents[i] = i;
        }
        
        //���� �������� ����Ʈ�� �߰���
        for(int i=0; i<costs.length; i++){
            edges.add(new Edge(costs[i][0],costs[i][1],costs[i][2]));
        }
        
        //MST�� �����Ҷ����� ���� ���� ����� ���� ������ ���� ��ȯ�ǵ��� �ؾ���
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
        	//���� ����� ���� �������� �ϳ��� ������
            Edge e = edges.get(i);
            
            //�ش� �������� �� ������ �θ� ���� �ٸ��ٸ�
            if(find(e.v1)!=find(e.v2)){
            	//�� ������ �����ϰ� �ش� ����� ����� ������
                answer += e.c;
                union(e.v1,e.v2);
            }
        }
        
        return answer;
    }
}