import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

//Ư�� ���������� ���� �������� ������ ��� Ŭ���� ����
class Node{
    int stage;
    double rate;
    Node(int stage, double rate){
        this.stage = stage;
        this.rate = rate;
    }
}

class Solution {
    public int[] solution(int N, int[] stages) {
        
        int[] answer = new int[N];
        
        //p�� �ش� ���������� ������ �� �ο�
        //��� ���������� Ŭ������ �ο����� �����ϱ� ���� N�� �ƴ� N + 1�� ���̸� ����
        int[] p = new int[N+1];
        
        //c�� ���� ���������� ������ �� �ο�
        //��� ���������� Ŭ������ �ο����� �����ϱ� ���� N�� �ƴ� N + 1�� ���̸� ����
        int[] c = new int[N+1];
        
        //Ư������������ ������ ������� ���� üũ��
        for(int i=0; i<stages.length; i++){
            c[stages[i]-1]++; 
        }
        
        //���� ������ ������������ ������ ������� ���� üũ��
        //���������� 1, 2, 3, 4, 5 �� 5���� �����Ѵٸ�
        //c[6]�� ��� ������ ��� ���������� Ŭ������ ����̸�
        //�̵��� �������� 5�� ������ ������� ����ؾ���
        //���� ��罺�������� Ŭ������ ��� + 5���������� �ӹ����ִ� ����� ���� ����
        //5���������� ������ ����� ����
        p[N-1] = c[N]+c[N-1]; 
        
        //�ٷ� ���� ���������� ������ ��� ����� + �ٷ� ���� ���������� �ӹ��� �ִ� �������
        //�� ���� ���������� ������ ��� ��� ����
        for(int i=N-1; i>0; i--){
            p[i-1] = p[i]+c[i-1];
        }
        
        //�������� ��ȣ �� �������� ������ ����Ʈ ����
        List<Node> list = new LinkedList<Node>();
        
        for(int i=0; i<N; i++){
        	//�ش� ���������� ������ ����� ���� 0�̶��, �������� �翬�� 0��
            if(p[i]==0){
                list.add(new Node(i+1,0));
            //�ش� ���������� �ӹ��� �ִ� ��� �� / �ش� ���������� ������ ��� ���� �� ��������
            }else{
                list.add(new Node(i+1,c[i]*1.0/p[i]));
            }
        }
        
        Collections.sort(list,new Comparator<Node>(){
            @Override
            //�������� �������� ������������ ������
            public int compare(Node n1, Node n2){
                if(n1.rate<n2.rate){
                    return 1;
                }else if(n1.rate>n2.rate){
                    return -1;
                //�������� ���ٸ�
                }else{
                	//�������� ��ȣ�� �������� ������������ ������
                    if(n1.stage<n2.stage){
                        return -1;
                    }else if(n1.stage>n2.stage){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        });
        
        int idx = 0;
        
        //���ĵ� ����� �迭�� ��ȯ��
        while(!list.isEmpty()){
            answer[idx++] = list.remove(0).stage; 
        } 
            
        return answer;
    }
}