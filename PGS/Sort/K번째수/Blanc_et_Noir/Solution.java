//https://programmers.co.kr/learn/courses/30/lessons/42748

import java.util.*;

class Solution {
    //해결방법에는 크게 3가지정도가 존재
    
    //1. 슬라이싱후 일일이 정렬하기
    
    //2. 각 숫자 및 그 인덱스 값을 저장하는 포인터를 선언하여
    //   리스트에 모두 추가하고, 값을 기준으로 정렬한 후
    //   해당 인덱스 범위내에 존재하는 값일때마다 카운트하여
    //   K번째로 카운트 되는 숫자 반환하기
    
    //3. 세그먼트트리의 한 종류인 머지소트트리 활용
    
    public static List<Integer>[] mst;
    public static int[] arr;
    
    //머지소트트리를 초기화하는 메소드
    public static List<Integer> init(int node, int start, int end){
        //리프노드라면 해당 리프노드에 특정 구간일때의 숫자들을 정렬하여 저장할
        //리스트를 선언하고, 값 하나를 추가함
        if(start==end){
            mst[node] = new ArrayList<Integer>();
            mst[node].add(arr[start]);
            return mst[node];
        //리프노드가 아니라면 재귀를 통해 범위를 좁혀나감
        }else{
            int mid = (start+end)/2;
            mst[node] = merge(init(node*2,start,mid),init(node*2+1,mid+1,end));
            return mst[node];
        }
    }
    
    //두 리스트를 병합하는 메소드
    //병합할때마다 매번 자동으로 오름차순정렬되는 머지소트의 방식을 사용
    public static List<Integer> merge(List<Integer> llist, List<Integer> rlist){
        
        List<Integer> result = new ArrayList<Integer>();
        
        //어레이리스트이므로 빠르게 랜덤액세스할 수 있는 장점이 있으나
        //remove사용시 O(N) 만큼의 시간복잡도가 필요하므로
        //제거하는 대신, 인덱스를 점차 증가시켜 마치 제거된 것처럼 사용
        int l = 0, r=0;
        
        //두 리스트 모두 무언가 값이 있다면
        while(l<llist.size()&&r<rlist.size()){
            //둘 중에 더 작은 값을 결과 리스트에 추가
            if(llist.get(l)<=rlist.get(r)){
                result.add(llist.get(l++));
            }else{
                result.add(rlist.get(r++));
            }
        }
        
        //이곳에 도달할때쯤이면 두 리스트중 어느 하나는 반드시 비어있음
        //따라서 둘 중에 아직 요소가 남아있는 리스트의 내용을 전부 결과 리스트에 추가
        
        while(l<llist.size()){
            result.add(llist.get(l++));
        }
        
        while(r<rlist.size()){
            result.add(rlist.get(r++));
        }
        
        return result;
    }
    
    //질의하는 메소드
    public static List<Integer> query(int node, int start, int end, int left, int right){
        //탐색하고자 하는 범위를 완전히 벗어났으면
        //결과에 영향을 미치지 않도록 빈 리스트 반환
        if(left>end||right<start){
            return new ArrayList<Integer>();
        }
        
        //탐색하고자 하는 범위안에 완전히 포함된 구간인경우
        //해당 노드를 리턴함
        if(left<=start&&end<=right){
            return mst[node];
        }
        
        //애매하게 걸쳐있는 경우, 재귀탐색을 실시함
        int mid = (start+end)/2;
        
        //재귀탐색한 두 결과를 병합함
        return merge(query(node*2,start,mid,left,right),query(node*2+1,mid+1,end,left,right));
    }
    
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        mst = new List[array.length*3];
        arr = array;
        
        //머지소트트리 초기화
        init(1,0,array.length-1);
        
        //주의해야할 것은 시작 node값은 반드시 0이아닌 1부터 시작해야하며
        //start, end, left, right는 모두 인덱스값으로 주어져야함
        for(int i=0; i<commands.length; i++){
            answer[i] = query(1,0,array.length-1,commands[i][0]-1,commands[i][1]-1).get(commands[i][2]-1);
        }
        
        return answer;
    }
}