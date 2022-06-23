import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

//특정 스테이지에 대한 실패율을 저장할 노드 클래스 선언
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
        
        //p는 해당 스테이지에 도달한 총 인원
        //모든 스테이지를 클리어한 인원까지 저장하기 위해 N이 아닌 N + 1의 길이를 가짐
        int[] p = new int[N+1];
        
        //c는 현재 스테이지에 도달한 총 인원
        //모든 스테이지를 클리어한 인원까지 저장하기 위해 N이 아닌 N + 1의 길이를 가짐
        int[] c = new int[N+1];
        
        //특정스테이지에 도달한 사람들의 수를 체크함
        for(int i=0; i<stages.length; i++){
            c[stages[i]-1]++; 
        }
        
        //가장 마지막 스테이지까지 도달한 사람들의 수를 체크함
        //스테이지가 1, 2, 3, 4, 5 총 5개가 존재한다면
        //c[6]에 담긴 정보는 모든 스테이지를 클리어한 사람이며
        //이들은 스테이지 5에 도달한 사람으로 취급해야함
        //따라서 모든스테이지를 클리어한 사람 + 5스테이지에 머물러있는 사람을 합한 것이
        //5스테이지에 도달한 사람의 수임
        p[N-1] = c[N]+c[N-1]; 
        
        //바로 다음 스테이지에 도달한 모든 사람수 + 바로 이전 스테이지에 머물러 있는 사람수가
        //곧 이전 스테이지에 도달한 모든 사람 수임
        for(int i=N-1; i>0; i--){
            p[i-1] = p[i]+c[i-1];
        }
        
        //스테이지 번호 및 실패율을 저장할 리스트 선언
        List<Node> list = new LinkedList<Node>();
        
        for(int i=0; i<N; i++){
        	//해당 스테이지에 도달한 사람의 수가 0이라면, 실패율은 당연히 0임
            if(p[i]==0){
                list.add(new Node(i+1,0));
            //해당 스테이지에 머물고 있는 사람 수 / 해당 스테이지에 도달한 사람 수가 곧 실패율임
            }else{
                list.add(new Node(i+1,c[i]*1.0/p[i]));
            }
        }
        
        Collections.sort(list,new Comparator<Node>(){
            @Override
            //실패율을 기준으로 내림차순으로 정렬함
            public int compare(Node n1, Node n2){
                if(n1.rate<n2.rate){
                    return 1;
                }else if(n1.rate>n2.rate){
                    return -1;
                //실패율이 같다면
                }else{
                	//스테이지 번호를 기준으로 오름차순으로 정렬함
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
        
        //정렬된 결과를 배열로 변환함
        while(!list.isEmpty()){
            answer[idx++] = list.remove(0).stage; 
        } 
            
        return answer;
    }
}