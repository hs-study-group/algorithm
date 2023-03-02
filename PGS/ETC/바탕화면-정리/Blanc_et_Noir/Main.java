//https://school.programmers.co.kr/learn/courses/30/lessons/161990

class Solution {
    public int[] solution(String[] wallpaper) {
        int[] answer = {};
        
        char[][] arr = new char[wallpaper.length][wallpaper[0].length()];
        
        //주어진 문자열 배열을 문자 배열로 변환함
        for(int i=0;i<wallpaper.length;i++){
            arr[i] = wallpaper[i].toCharArray();
        }
        
        //가장 왼쪽의 y, x, 가장 오른쪽의 y, x좌표를 기록할 변수
        int minX=Integer.MAX_VALUE;
        int minY=Integer.MAX_VALUE;
        int maxX=Integer.MIN_VALUE;
        int maxY=Integer.MIN_VALUE;
        
        for(int i=0; i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
            	//만약 파일이 존재하는 위치라면
                if(arr[i][j]=='#'){
                	//해당 파일의 위치를 확인하여 각각의 좌표를 갱신함
                    minX = Math.min(minX,j);
                    maxX = Math.max(maxX,j+1);
                    minY = Math.min(minY,i);
                    maxY = Math.max(maxY,i+1);
                }
            }
        }
        
        return new int[]{minY,minX,maxY,maxX};
    }
}