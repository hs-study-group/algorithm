//https://www.acmicpc.net/problem/2239

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//중복되는 숫자가 존재하는지 아닌지 여부를 확인하기 위한 HashMap 객체 선언
	static HashMap[][] section = new HashMap[3][3];
	static HashMap[] row = new HashMap[9];
	static HashMap[] col = new HashMap[9];
	
	//단 하나의 올바른 스도쿠 결과만 출력하기 위한 변수
	static boolean flag = false;
	
	public static void backTracking(int[][] map, int idx) throws IOException {
		//이번에 배치할 인덱스를 y, x좌표로 변환함
		int y = idx/9;
		int x = idx%9;
		
		//이미 올바른 스도쿠 결과를 얻은 적이 있으면 더이상 백트래킹을 할 필요 없음
		if(flag) {
			return;
		}
		
		//만약 인덱스가 맵을 벗어났다면, 이는 스도쿠에 숫자를 모두 올바르게 채웠다는 것임
		if(idx==9*9) {
			
			//완성된 스도쿠의 상태를 출력함
			for(int i=0; i<map.length; i++) {
				for(int j=0; j<map.length; j++) {
					bw.write(map[i][j]+"");
				}
				bw.write("\n");
			}
			bw.write("\n");
			
			//스도쿠를 완성한 적이 있다고 표시함
			flag = true;
			return;
			
		//만약 해당 위치에 이미 숫자가 채워져 있다면
		}else if(map[y][x]!=0){
			//다음 위치에 숫자를 배치하도록 재귀 메소드를 호출함
			backTracking(map, idx+1);
		//해당 위치에 숫자를 채워야 한다면
		}else{
			//1 ~ 9 값에 대하여
			for(int i=0; i<9; i++) {
				//해당 위치에 그 숫자를 배치할 수 있다면
				if(check(map,y,x,i+1)) {
					
					//3x3 영역의 인덱스를 얻음
					int sy = y/3;
					int sx = x/3;
					
					//해당 위치에 실제로 숫자를 배치함
					map[y][x] = i + 1;
					
					//3x3 영역에 해당 숫자를 배치한 적이 있음을 기록함
					section[sy][sx].put(i+1, true);
					
					//해당 행에 해당 숫자를 배치한 적이 있음을 기록함
					row[y].put(i+1, true);
					
					//해당 열에 해당 숫자를 배치한 적이 있음을 기록함
					col[x].put(i+1, true);
					
					//다음 위치로 이동하여 숫자를 배치함
					backTracking(map, idx+1);
					
					//해당 열에 해당 숫자를 배치한 적이 없는 것으로 처리함
					col[x].remove(i+1);
					
					//해당 행에 해당 숫자를 배치한 적이 없는 것으로 처리함
					row[y].remove(i+1);
					
					//3x3 영역에 해당 숫자를 배치한 적이 없는 것으로 처리함
					section[sy][sx].remove(i+1);
					
					//해당 위치에 숫자를 배치한 적이 없는 것으로 처리함
					map[y][x] = 0;
				}
			}
		}
	}
	
	//해당 y, x위치에 i숫자를 배치할 수 있는지 없는지 판단하는 메소드
	public static boolean check(int[][] map, int y, int x, int i) {
		//어떤 3x3영역에 포함되는지를 계산함
		int sy = y/3;
		int sx = x/3;
		
		//같은 행에 같은 숫자가 존재한다면
		if(row[y].containsKey(i)) {
			//해당 위치에 해당 숫자를 배치할 수 없음
			return false;
		}
		
		//같은 열에 같은 숫자가 존재한다면
		if(col[x].containsKey(i)) {
			//해당 위치에 해당 숫자를 배치할 수 없음
			return false;
		}
		
		//같은 3x3 영역에 같은 숫자가 존재한다면
		if(section[y/3][x/3].containsKey(i)) {
			//해당 위치에 해당 숫자를 배치할 수 없음
			return false;
		}
		
		//해당 위치에 해당 숫자를 배치할 수 있음
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		int[][] map = new int[9][9];
		
		//3x3 영역에 담긴 숫자를 저장할 HashMap 객체 초기화
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				section[i][j] = new HashMap();
			}
		}
		
		//각 행과 열에 숫자를 저장할 HashMap 객체 초기화
		for(int i=0; i<9; i++) {
			col[i] = new HashMap();
			row[i] = new HashMap();
		}
		
		//스도쿠 초기 상태를 입력 받음
		for(int i=0; i<map.length; i++) {
			String[] temp = br.readLine().split("");
			for(int j=0; j<map[0].length; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
				
				//해당되는 3x3 영역에 해당 숫자 정보를 저장함
				section[i/3][j/3].put(map[i][j], true);
				
				//해당되는 행에 해당 숫자 정보를 저장함
				row[i].put(map[i][j], true);
				
				//해당되는 열에 해당 숫자 정보를 저장함
				col[j].put(map[i][j], true);
			}
		}
		
		//백트래킹을 수행함
		backTracking(map,0);
		
		bw.flush();
		bw.close();
		br.close();
	}
}