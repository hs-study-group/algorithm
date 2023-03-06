//https://level.goorm.io/exam/49063/%EC%A0%9C%EC%84%A4%EC%9E%91%EC%A0%84/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//v2, v1사이의 너비를 구하는 메소드
	public static int getWidth(int v1, int v2){
		if(v2<v1){
			int t = v2;
			v2 = v1;
			v1 = t;
		}
		
		return v2 - v1 - 1;
	}
	
	//너비가 d일때, d보다 작거나 같은 삽중에서 가장 큰 삽의 인덱스를 리턴하는 메소드
	//그러한 삽이 없다면 -1을 리턴한다
	public static int findShovel(int[] shovels, int d){
		int s = 0, e = shovels.length;
		
		while(s<e){
			int m = (s+e)/2;
			
			if(shovels[m]>d){
				e = m;
			}else{
				s = m + 1;
			}
		}
		
		return e - 1;
	}
	
	//s의 너비를 갖는 눈삽으로 l부터 r까지의 구간에서 한 번에 치울 수 있는 눈의 최대 양을 구하는 메소드
	public static int findMax(int[] snow, int l, int r, int s){
		int sum = 0;
		
		for(int i=l+1; i<l+1+s; i++){
			sum += snow[i];
		}
		
		int max = sum;

		for(int i=l+1+s; i<r; i++){
			sum += snow[i];
			sum -= snow[i-s];
			
			max = Math.max(max,sum);
		}
		
		max = Math.max(max,sum);
		return max;
	}
	
	public static void main(String[] args) throws Exception {
		int[] answer = new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE};
		int L = Integer.parseInt(br.readLine());
		int[] snow = new int[L+2];
		String[] temp = br.readLine().split(" ");
		
		//눈의 높이를 입력받음
		for(int i=1; i<snow.length-1; i++){
			snow[i] = Integer.parseInt(temp[i-1]);
		}

		int N = Integer.parseInt(br.readLine());
		int[] shovels = new int[N];
		temp = br.readLine().split(" ");
		
		//삽의 너비를 입력 받음
		for(int i=0; i<shovels.length; i++){
			shovels[i] = Integer.parseInt(temp[i]);
		}
		
		int R = Integer.parseInt(br.readLine());
		int[] stones = new int[R+2];
		temp = br.readLine().split(" ");
		
		//편의상 돌 배열의 가장 첫위치와 마지막 위치에 대한 정보도 추가함
		stones[0] = 0;
		stones[R+1] = L+1;
		
		//돌의 위치를 입력 받음
		for(int i=1; i<stones.length-1; i++){
			stones[i] = Integer.parseInt(temp[i-1]);
		}

		//돌과 삽의 정보를 오름차순 정렬함
		Arrays.sort(stones);
		Arrays.sort(shovels);
		
		for(int i=0;i<stones.length-1; i++){
			//두 돌 사이에 존재하는 공간의 너비를 구함
			int w = getWidth(stones[i],stones[i+1]);
			//해당 너비보다 작거나 같은 삽 중에서 가장 큰 삽의 인덱스를 구함
			int idx = findShovel(shovels,w);
			
			//그러한 삽이 있다면
			if(idx!=-1){
				//해당 삽의 너비
				int s = shovels[idx];
				//그 삽으로 두 돌 사이에 존재하는 공간의 눈을 한번 치울때 얻을 수 있는 최대 값을 구함
				int max = findMax(snow,stones[i],stones[i+1],s);
				
				//그 최대값이 기존 값보다 크다면
				if(max > answer[1]){
					//그때 치울 수 있는 눈의 양과 삽의 너비를 기록함
					answer[1] = max;
					answer[0] = s;
				//그 최대값이 기존과 같으나, 더 작은 삽으로도 구할 수 있다면
				}else if(max == answer[1] && s < answer[0]){
					//그때의 삽의 너비를 기록함
					answer[0] = s;
				}
			}
		}
		
		bw.write(answer[0]+" "+answer[1]+"\n");
		bw.flush();
		br.close();
		bw.close();
	}
}