//https://school.programmers.co.kr/learn/courses/30/lessons/42579

import java.util.*;

//어떤 노래의 장르, 고유번호, 재생횟수를 저장할 노드 클래스
class Node{
    String genre; int idx, play;
    Node(String genre, int idx, int play){
        this.genre = genre;
        this.idx = idx;
        this.play = play;
    }
}

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};
        
        //각 장르별로 총 재생 횟수를 기록할 해시맵
        HashMap<String,Integer> hm = new HashMap<String,Integer>();        
        
        //가장 많은 재생횟수를 가진 장르, 장르가 같다면 더 많이 재생된 노래,
        //재생횟수도 같다면 고유번호가 작은 노래가 먼저 반환되는 우선순위 큐
        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                if(hm.get(n1.genre)>hm.get(n2.genre)){
                    return -1;
                }else if(hm.get(n1.genre)<hm.get(n2.genre)){
                    return 1;
                }else{
                    if(n1.play>n2.play){
                        return -1;
                    }else if(n1.play<n2.play){
                        return 1;
                    }else{
                        if(n1.idx<n2.idx){
                            return -1;
                        }else if(n1.idx>n2.idx){
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                }
            }
        });
        
        //각 노래를 탐색하면서 각 장르별 총 재생횟수를 기록함
        for(int i=0; i<genres.length; i++){
            if(hm.containsKey(genres[i])){
                hm.put(genres[i],hm.get(genres[i])+plays[i]);
            }else{
                hm.put(genres[i],plays[i]);
            }
        }
        
        //각 노래를 우선순위큐에 추가함.
        //1. 해당 노래가 속한 장르의 총 재생횟수가 높은 노래
        //2. 같은 장르라면 재생횟수가 더 높은 노래
        //3. 재생횟수도 같다면 고유번호가 더 작은 노래가 반환됨
        for(int i=0; i<genres.length; i++){
            pq.add(new Node(genres[i],i,plays[i]));
        }
        
        List<Integer> list = new LinkedList<Integer>();
        
        //해당 장르에 속한 노래가 최대 2개까지만 수록 되도록 하기 위해
        //해당 장르로 수록된 노래의 개수를 저장할 해시맵
        HashMap<String,Integer> temp = new HashMap<String,Integer>();  
        
        //우선순위 큐에서 우선순위대로 노래를 탐색함
        for(int i=0; i<genres.length; i++){
            Node n = pq.poll();
            //만약 해당 장르의 노래가 수록된 적이 있고
            if(temp.containsKey(n.genre)){
                //수록된 노래의 수가 2 미만이라면
                if(temp.get(n.genre)<2){
                    //해당 노래를 수록함
                    list.add(n.idx);
                    
                    //해당 장르의 노래가 수록된 횟수를 1 증가시킴
                    temp.put(n.genre,temp.get(n.genre)+1);
                }
            //해당 장르의 노래가 수록된 적이 없다면
            }else{
                //해당 노래를 수록함
                list.add(n.idx);
                
                //수록된 노래의 개수를 1 증가시킴
                temp.put(n.genre,1);
            }
        }
        
        //결과를 리스트 -> 배열로 변환함
        answer = new int[list.size()];
        Iterator<Integer> itor = list.iterator();
        
        int idx = 0;
        while(itor.hasNext()){
            answer[idx++] = itor.next();
        }
        
        return answer;
    }
}