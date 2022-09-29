//https://school.programmers.co.kr/learn/courses/30/lessons/49191

import java.util.*;

class Solution {
	//주어진 간선정보를 바탕으로 모든 정점간 최소 비용을 구하는 플로이드 워셜 알고리즘 메소드
    public static void floydWarshall(int[][] graph){
        for(int k=0; k<graph.length; k++){
            for(int i=0; i<graph.length; i++){
                for(int j=0; j<graph.length; j++){
                    graph[i][j] = Math.min(graph[i][j],graph[i][k]+graph[k][j]);
                }
            }
        }
    }
    
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        //해당 값 대신 Integer.MAX_VALUE를 사용하게 되면
        //플로이드 워셜 알고리즘 수행시 Math.min() 메소드에서 오버플로우가 발생함
        //따라서 충분히 큰 값을 사용하되, MAX_VALUE보다 한참 작은 값을 INF로서 처리함
        final int INF = 100000000;
        int[][] graph = new int[n][n];
        
        //모든 간선 정보를 INF로 초기화함
        for(int i=0; i<graph.length; i++){
            for(int j=0; j<graph.length; j++){
                graph[i][j] = INF;
            }
        }
        
        //간선 정보를 바탕으로 간선 배열을 초기화함
        for(int i=0; i<results.length; i++){
            graph[results[i][0]-1][results[i][1]-1] = 1;
        }
        
        //graph[i][j] != INF라면 이는 i -> j로 이동할 수 있음을 의미함.
        //이를 마치 i가 j를 이길 수 있다고 처리하는 것이 핵심임        
        
        //graph[j][i] != INF라면 이는 j -> i로 이동할 수 있음을 의미함.
        //이를 마치 i가 j를 이길 수 없다고 처리하는 것이 핵심임
        
        //플로이드 워셜 알고리즘을 수행하여 모든 정점간 최소비용을 계산함
        floydWarshall(graph);
        
        for(int i=0; i<graph.length; i++){
        	//특정 선수가 이긴 횟수, 진 횟수를 각각 저장할 변수 선언
            int win=0,lose=0;
            
            //i에서 자신을 제외한 모든 j로 이동할 수 있는지 탐색함
            //즉, i가 자신을 제외한 모든 정점인 j를 이길 수 있는지 탐색함
            for(int j=0; j<graph.length; j++){
            	//i -> j로 가는 최소 비용이 INF가 아니라면
                if(graph[i][j]!=INF){
                	//즉, i가 j를 이길 수 있는 것이므로 i의 승리 횟수를 1증가시킴
                    win++;
                }
            }
            
            //j에서 자기 자신을 제외한 모든 정점인 i를 이길 수 있는지 탐색함
            //즉, j가 자신을 제외한 모든 정점인 i를 이길 수 있는지 탐색함
            for(int j=0; j<graph.length; j++){
            	//j -> i로 가는 최소 비용이 INF가 아니라면
                if(graph[j][i]!=INF){
                	//즉, j가 i를 이길 수 있는 것이므로 i의 패배 횟수를 1증가시킴
                    lose++;
                }
            }
            
            //N명의 사람이 있을때, 자신의 순위를 확정짓기 위해서는
            //자신을 제외한 나머지 N-1명의 사람과 대결하여 승패를 정해야만 함
            //따라서 자신을 제외한 나머지 사람들과 대결을 했을때 승리 및 패배의 수는 N-1이어야만
            //자신의 순위를 확정 지을 수 있음.
            if(win+lose==n-1){
                answer++;
            }
        }
        
        return answer;
    }
}