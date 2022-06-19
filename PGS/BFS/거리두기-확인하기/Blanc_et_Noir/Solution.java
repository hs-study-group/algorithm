import java.util.*;

//y, x��ǥ �� �Ÿ� c�� ������ ��� Ŭ����
class Node{
    int y, x, c;
    Node(int y, int x, int c){
        this.y = y;
        this.x = x;
        this.c = c;
    }
}

class Solution {
	//�����ϴ� ����� ������ ������ ť ����
    public static Queue<Node> persons;
    
    //1���� String�迭�� 2���� char �迭�� ��ȯ�Ͽ� ������ �� ����
    public static char[][] map;
    
    //�湮�迭 ����
    public static boolean[][] v;
    
    //������ �Է¹޾� 2���� map, �湮�迭, ����� ��ġ�� ������ ť�� ������
    public static void set(String[] place){
        persons = new LinkedList<Node>();
        
        //�������� ������ ũ��� 5x5�� ������
        v = new boolean[5][5];
        map = new char[5][5];
        
        //1���� ���ڿ��� �־��� ��ġ�� ���Ͽ�
        for(int i=0; i<place.length; i++){
            String[] str = place[i].split("");
            //2���� char �迭�� ��ȯ�ϰ�, �� ��ġ�� ����̶�� �ش� ������ ť�� �߰���
            for(int j=0; j<str.length; j++){
                if(str[j].equals("P")){
                    persons.add(new Node(i,j,0));
                }
                //String�� char�� ��ȯ�Ͽ� ����
                map[i][j]=str[j].charAt(0);
            }
        }
    }
    
    //��� ����� ���Ͽ� BFS Ž�� �ǽ�
    public static boolean BFS(){
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //���� Ž������ ���� ����� �ִٸ�
        while(!persons.isEmpty()){
            
        	//�ش� ������κ��� BFSŽ���� �ǽ���
            Node p = persons.poll();
            Queue<Node> q = new LinkedList<Node>();
            
            q.add(p);
            
            //�湮�迭�� Ư�� ����� ���Ͽ� BFSŽ���� �ǽ��Ҷ��� �ʱ�ȭ �ؾ���
            //�׷��� ������ �Ʒ��� ��쿡 ���� 1�� ����ϴ� ���� �߻�
            
            //POOOP
            //OXXOX
            //OXXPX
            //OPXOX
            //PXXXP
            
            v = new boolean[5][5];
            v[p.y][p.x] = true;
            
            while(!q.isEmpty()){
                
                Node n = q.poll();
                
                for(int i=0; i<dist.length; i++){
                    
                    int y = n.y + dist[i][0];
                    int x = n.x + dist[i][1];
                    
                    //�ش� ��ġ�� ���� ����� ������
                    if(y>=0&&y<5&&x>=0&&x<5){
                    	//���� ���� �湮���� ���� å���̶��
                        if(!v[y][x]&&map[y][x]=='O'){
                        	//�Ÿ��� 1 �������� ť�� �ٽ� �߰��ϰ� �湮ó����
                            q.add(new Node(y,x,n.c+1));
                            v[y][x] = true;
                        //���� �湮���� ���� P���
                        }else if(!v[y][x]&&map[y][x]=='P'){
                        	//���±��� ����� ����ư �Ÿ��� ���� 2�̸��̶��
                        	//P������ �Ÿ��� ���� ������ �Ÿ� + 1�̰�, �̰��� 2���϶�� �̾߱�Ƿ�
                        	//�Ÿ��α⸦ ��Ű�� ���� ����
                            if(n.c<2){     
                                return false;
                            }                            
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        
        //�� ���ǿ� ����
        for(int i=0; i<places.length; i++){
        	//���� ����, �迭���� �ʱ�ȭ ��
            set(places[i]);
            
            //BFSŽ�� ����� ���� 1�Ǵ� 0�� ������
            if(BFS()){
                answer[i] = 1;
            }else{
                answer[i] = 0;
            }
        }
        return answer;
    }
}