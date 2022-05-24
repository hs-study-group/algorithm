//https://programmers.co.kr/learn/courses/30/lessons/42748

import java.util.*;

class Solution {
    //�ذ������� ũ�� 3���������� ����
    
    //1. �����̽��� ������ �����ϱ�
    
    //2. �� ���� �� �� �ε��� ���� �����ϴ� �����͸� �����Ͽ�
    //   ����Ʈ�� ��� �߰��ϰ�, ���� �������� ������ ��
    //   �ش� �ε��� �������� �����ϴ� ���϶����� ī��Ʈ�Ͽ�
    //   K��°�� ī��Ʈ �Ǵ� ���� ��ȯ�ϱ�
    
    //3. ���׸�ƮƮ���� �� ������ ������ƮƮ�� Ȱ��
    
    public static List<Integer>[] mst;
    public static int[] arr;
    
    //������ƮƮ���� �ʱ�ȭ�ϴ� �޼ҵ�
    public static List<Integer> init(int node, int start, int end){
        //��������� �ش� ������忡 Ư�� �����϶��� ���ڵ��� �����Ͽ� ������
        //����Ʈ�� �����ϰ�, �� �ϳ��� �߰���
        if(start==end){
            mst[node] = new ArrayList<Integer>();
            mst[node].add(arr[start]);
            return mst[node];
        //������尡 �ƴ϶�� ��͸� ���� ������ ��������
        }else{
            int mid = (start+end)/2;
            mst[node] = merge(init(node*2,start,mid),init(node*2+1,mid+1,end));
            return mst[node];
        }
    }
    
    //�� ����Ʈ�� �����ϴ� �޼ҵ�
    //�����Ҷ����� �Ź� �ڵ����� �����������ĵǴ� ������Ʈ�� ����� ���
    public static List<Integer> merge(List<Integer> llist, List<Integer> rlist){
        
        List<Integer> result = new ArrayList<Integer>();
        
        //��̸���Ʈ�̹Ƿ� ������ �����׼����� �� �ִ� ������ ������
        //remove���� O(N) ��ŭ�� �ð����⵵�� �ʿ��ϹǷ�
        //�����ϴ� ���, �ε����� ���� �������� ��ġ ���ŵ� ��ó�� ���
        int l = 0, r=0;
        
        //�� ����Ʈ ��� ���� ���� �ִٸ�
        while(l<llist.size()&&r<rlist.size()){
            //�� �߿� �� ���� ���� ��� ����Ʈ�� �߰�
            if(llist.get(l)<=rlist.get(r)){
                result.add(llist.get(l++));
            }else{
                result.add(rlist.get(r++));
            }
        }
        
        //�̰��� �����Ҷ����̸� �� ����Ʈ�� ��� �ϳ��� �ݵ�� �������
        //���� �� �߿� ���� ��Ұ� �����ִ� ����Ʈ�� ������ ���� ��� ����Ʈ�� �߰�
        
        while(l<llist.size()){
            result.add(llist.get(l++));
        }
        
        while(r<rlist.size()){
            result.add(rlist.get(r++));
        }
        
        return result;
    }
    
    //�����ϴ� �޼ҵ�
    public static List<Integer> query(int node, int start, int end, int left, int right){
        //Ž���ϰ��� �ϴ� ������ ������ �������
        //����� ������ ��ġ�� �ʵ��� �� ����Ʈ ��ȯ
        if(left>end||right<start){
            return new ArrayList<Integer>();
        }
        
        //Ž���ϰ��� �ϴ� �����ȿ� ������ ���Ե� �����ΰ��
        //�ش� ��带 ������
        if(left<=start&&end<=right){
            return mst[node];
        }
        
        //�ָ��ϰ� �����ִ� ���, ���Ž���� �ǽ���
        int mid = (start+end)/2;
        
        //���Ž���� �� ����� ������
        return merge(query(node*2,start,mid,left,right),query(node*2+1,mid+1,end,left,right));
    }
    
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        mst = new List[array.length*3];
        arr = array;
        
        //������ƮƮ�� �ʱ�ȭ
        init(1,0,array.length-1);
        
        //�����ؾ��� ���� ���� node���� �ݵ�� 0�̾ƴ� 1���� �����ؾ��ϸ�
        //start, end, left, right�� ��� �ε��������� �־�������
        for(int i=0; i<commands.length; i++){
            answer[i] = query(1,0,array.length-1,commands[i][0]-1,commands[i][1]-1).get(commands[i][2]-1);
        }
        
        return answer;
    }
}