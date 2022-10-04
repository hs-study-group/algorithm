//https://school.programmers.co.kr/learn/courses/30/lessons/49993

import java.util.*;

class Solution {
	//우선순위 및 진입차수를 고려하여 위상정렬을 수행하는 메소드
    public static boolean topologicalSort(ArrayList<ArrayList<Integer>> graph, int[] indegree, String skill_tree){
        char[] temp = skill_tree.toCharArray();
        
        for(int i=0; i<temp.length; i++){
        	//사용자가 배우고자 하는 스킬의 인덱스를 얻음
            int idx = temp[i]-'A';
            
            //만약 해당 스킬의 진입차수가 0이 아니라면
            //즉, 해당 스킬을 배우기 전에 다른 스킬을 배워야 했다면
            if(indegree[idx]!=0){
            	//해당 스킬트리는 불가능한 것임
                return false;
            //만약 해당 스킬의 진입차수가 0이라면
            //즉, 해당 스킬을 배우기전에 배워야할 모든 스킬을 이미 배웠다면
            }else{
            	//해당 스킬을 배우고나서 해당 스킬을 배워야만 배울수 있는 다른 스킬들의 진입차수를 1감소시킴
                for(int j=0; j<graph.get(idx).size(); j++){
                    indegree[graph.get(idx).get(j)]--;
                }
            }
        }
        
        //해당 스킬트리는 가능한 것임
        return true;
    }
    
    //주어진 스킬의 우선순위를 바탕으로 진입차수가 담긴 배열을 리턴하는 메소드
    public static int[] getIndegree(String skill){
    	//스킬은 모두 알파벳 대문자 하나로 표기되므로 최대 26개가 존재할 수 있음
        int[] indegree = new int[26];
        
        String[] temp = skill.split("");
        
        //스킬의 우선순위에 따라 스킬들의 진입차수를 설정함
        for(int i=0; i<temp.length-1; i++){
            int B = temp[i+1].charAt(0)-'A';
            indegree[B]++;
        }
        
        //진입차수 배열을 리턴함
        return indegree;
    }
    
    
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        
        String[] temp = skill.split("");
        
        //간선정보를 입력받을 이중 리스트를 선언함
        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        
        //이중 리스트를 초기화 함
        for(int i=0; i<26; i++){
            graph.add(new ArrayList<Integer>());
        }
        
        //간선정보를 입력받음
        for(int i=0; i<temp.length-1; i++){
            int A = temp[i].charAt(0)-'A';
            int B = temp[i+1].charAt(0)-'A';
            graph.get(A).add(B);
        }
        
        for(int i=0; i<skill_trees.length; i++){
        	//주어진 스킬의 우선순위에 맞게 진입차수 배열을 만듦
            int[] indegree = getIndegree(skill);
            
            //해당 우선순위 및 진입차수를 바탕으로 사용자가 제시한 순서대로 위상정렬이 가능하다면
            //즉, 해당 스킬트리가 가능하다면
            if(topologicalSort(graph,indegree,skill_trees[i])){
            	//정답을 1증가시킴
                answer++;
            }
        }
        
        return answer;
    }
}