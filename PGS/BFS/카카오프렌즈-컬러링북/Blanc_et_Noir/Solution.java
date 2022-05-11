//https://programmers.co.kr/learn/courses/30/lessons/1829

import java.util.*;

//y, x�� ���� ��ǥ, c�� �׸��� ������ ��Ÿ��
class Node{
    int y, x, c;
    Node(int c, int y, int x){
        this.c = c;
        this.y = y;
        this.x = x;
    }
}

class Solution {
    
	//���� Ž������ ���� ������ �ִٸ�, �ش� ������ ���� ��带 �����ϴ� �޼ҵ�
    public static Node find(int[][] n, boolean[][] v){
        for(int i=0; i<n.length; i++){
            for(int j=0; j<n[i].length; j++){
                if(n[i][j]>=1&&!v[i][j]){
                    return new Node(n[i][j],i,j);
                }
            }
        }
        return null;
    }
    
    //m�� �׸�, v�� �湮�迭, sn�� ���� ���
    public static int bfs(int[][] m, boolean [][] v, Node sn){
        int cnt = 1;
        int[][] dist = {{-1,0},{1,0},{0,-1},{0,1}};
        
        //ù ���۳�带 ť�� �߰��ϰ� �湮������ ǥ��
        Queue<Node> q = new LinkedList<Node>();
        q.add(sn);
        v[sn.y][sn.x]=true;
        
        while(!q.isEmpty()){
            Node n = q.poll();
            int y, x;
            for(int i=0; i<dist.length; i++){
                y = n.y + dist[i][0];
                x = n.x + dist[i][1];
                //���� �湮���� ���� ������ ����϶�
                if(y>=0&&y<m.length&&x>=0&&x<m[0].length&&!v[y][x]){
                	//�ش� ������ ����� ���� ���� ����� ���� ��ġ�Ѵٸ�
                    if(m[y][x]==n.c){
                    	//�ش� ��忡�� �ٽ� Ž���� �� �ֵ��� ť�� �߰� �� �湮 ó��
                        q.add(new Node(n.c,y,x));
                        v[y][x]=true;
                        //������ ũ�⸦ 1������Ŵ
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
    
    public int[] solution(int m, int n, int[][] picture) {
        int[] answer = new int[2];
        
        boolean[][] v = new boolean[m][n];
        
        Node node = null;
        
        //���� Ž������ ���� ��ĥ�� ������ �ִٸ�
        while((node = find(picture,v))!=null){
        	//�ش� ������ ũ�⸦ ����
            int cnt = bfs(picture,v,node);
            
            //�ش� ������ ũ�Ⱑ �ִ밪�̶�� ������
            answer[1] = answer[1] < cnt ? cnt : answer[1];
            
            //������ ���� 1������Ŵ
            answer[0]++;
        }
        
        return answer;
    }
}