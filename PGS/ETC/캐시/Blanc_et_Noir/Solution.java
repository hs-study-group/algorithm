//https://programmers.co.kr/learn/courses/30/lessons/17680

import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        final int time_hit = 1;
        final int tile_miss = 5;
        int answer = 0;
        
        //LRU 캐시를 구현하는 방법에는 크게 2가지가 존재함.
        //1. LinkedList를 이용하되
        //   hit가 발생하면 해당 페이지를 제거후 맨뒤에 추가
        //   miss가 발생하면 캐시가 가득 찼으면 맨앞의 페이지를 제거후 맨뒤에 해당 페이지 추가, 아니면 맨뒤에 페이지를 추가
        //   해당 방식은 선형탐색이 반복적으로 이루어져야하므로 시간복잡도가 너무 큼
        
        //2. LinkedHashMap을 이용하되
        //   hit가 발생하면 해당 페이지를 제거후 맨 뒤에 추가, 단 해당 페이지가 존재하는지 아닌지 해시맵 객체의 get( )메소드를
        //   활용하므로 O(1)의 탐색시간이 필요, 이때 accessorder가 true이므로 어떤 페이지를 접근, 추가, 수정등의
        //   동작을 수행하면 자동적으로 Linked된 순서의 맨 뒤로 이동하게 됨.
        //   miss가 발생하면 해당 페이지를 추가
        //   캐시가 가득 찼으면 자동적으로 오버라이딩된 removeEldestEntry( ) 메소드에 의해 가장 오래된 페이지 삭제
        //   가득차지 않았으면 순서에 맞게 맨 뒤에 페이지 추가
        
        //각 파라미터는 초기 캐시크기, 로드팩터, accessorder를 의미
        //accessorder 가 true일시 get, put등의 (containsKey( )는 아님) 메소드 호출시 자동적으로 맨 뒤로 이동시킴
        //loadfactor는 버킷이 일정량의 이상의 데이터를 가질시 버킷을 세분화 하는 등의 작업을 할때의 비율을 말함
        LinkedHashMap<String,Integer> cache = new LinkedHashMap<String,Integer>(10,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest){
                return this.size() > cacheSize ? true : false;
            }
        };
        
        for(int i=0; i<cities.length; i++){
        	//해당 도시들은 대소문자 구별이 없이 주어지므로
        	//모두 소문자로 편의상 통일하여 처리함
            String key = cities[i].toLowerCase();
            
            //해당 페이지가 캐시에 존재하면 hit
            //accessorder가 true이므로 저절로 리스트의 맨 뒤로 페이지가 이동됨
            if(cache.get(key)!=null){
                answer+=time_hit;
            //miss면 해당 페이지 추가
            }else{
                answer+=tile_miss;
                //캐시가 최대 크기 이상으로 페이지를 캐싱하는 경우
                //앞서 오버라이딩된 removeEldestEntry( )메소드가 호출되어
                //가장 오래전에 사용한 페이지를 제거함
                cache.put(key,0);
            }
        } 
        return answer;
    }
}