//https://programmers.co.kr/learn/courses/30/lessons/17680

import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        final int time_hit = 1;
        final int tile_miss = 5;
        int answer = 0;
        
        //LRU ĳ�ø� �����ϴ� ������� ũ�� 2������ ������.
        //1. LinkedList�� �̿��ϵ�
        //   hit�� �߻��ϸ� �ش� �������� ������ �ǵڿ� �߰�
        //   miss�� �߻��ϸ� ĳ�ð� ���� á���� �Ǿ��� �������� ������ �ǵڿ� �ش� ������ �߰�, �ƴϸ� �ǵڿ� �������� �߰�
        //   �ش� ����� ����Ž���� �ݺ������� �̷�������ϹǷ� �ð����⵵�� �ʹ� ŭ
        
        //2. LinkedHashMap�� �̿��ϵ�
        //   hit�� �߻��ϸ� �ش� �������� ������ �� �ڿ� �߰�, �� �ش� �������� �����ϴ��� �ƴ��� �ؽø� ��ü�� get( )�޼ҵ带
        //   Ȱ���ϹǷ� O(1)�� Ž���ð��� �ʿ�, �̶� accessorder�� true�̹Ƿ� � �������� ����, �߰�, ��������
        //   ������ �����ϸ� �ڵ������� Linked�� ������ �� �ڷ� �̵��ϰ� ��.
        //   miss�� �߻��ϸ� �ش� �������� �߰�
        //   ĳ�ð� ���� á���� �ڵ������� �������̵��� removeEldestEntry( ) �޼ҵ忡 ���� ���� ������ ������ ����
        //   �������� �ʾ����� ������ �°� �� �ڿ� ������ �߰�
        
        //�� �Ķ���ʹ� �ʱ� ĳ��ũ��, �ε�����, accessorder�� �ǹ�
        //accessorder �� true�Ͻ� get, put���� (containsKey( )�� �ƴ�) �޼ҵ� ȣ��� �ڵ������� �� �ڷ� �̵���Ŵ
        //loadfactor�� ��Ŷ�� �������� �̻��� �����͸� ������ ��Ŷ�� ����ȭ �ϴ� ���� �۾��� �Ҷ��� ������ ����
        LinkedHashMap<String,Integer> cache = new LinkedHashMap<String,Integer>(10,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest){
                return this.size() > cacheSize ? true : false;
            }
        };
        
        for(int i=0; i<cities.length; i++){
        	//�ش� ���õ��� ��ҹ��� ������ ���� �־����Ƿ�
        	//��� �ҹ��ڷ� ���ǻ� �����Ͽ� ó����
            String key = cities[i].toLowerCase();
            
            //�ش� �������� ĳ�ÿ� �����ϸ� hit
            //accessorder�� true�̹Ƿ� ������ ����Ʈ�� �� �ڷ� �������� �̵���
            if(cache.get(key)!=null){
                answer+=time_hit;
            //miss�� �ش� ������ �߰�
            }else{
                answer+=tile_miss;
                //ĳ�ð� �ִ� ũ�� �̻����� �������� ĳ���ϴ� ���
                //�ռ� �������̵��� removeEldestEntry( )�޼ҵ尡 ȣ��Ǿ�
                //���� �������� ����� �������� ������
                cache.put(key,0);
            }
        } 
        return answer;
    }
}