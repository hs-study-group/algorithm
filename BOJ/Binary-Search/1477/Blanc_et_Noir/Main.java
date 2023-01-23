//https://www.acmicpc.net/problem/1477

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//총 휴게소의 개수
		int N = Integer.parseInt(temp[0]);
		
		//설치해야할 휴게소의 개수
		int M = Integer.parseInt(temp[1]);
		
		//고속도로의 길이
		int L = Integer.parseInt(temp[2]);
		
		//편의상 0, L위치에도 휴게소가 있다고 가정함으로써 계산을 편하게함
		int[] arr = new int[N+2];
		
		//기존에 설치된 휴게소의 개수가 0일수도 있으므로 0이상일때만 입력받음
		if(N>0) {
			temp = br.readLine().split(" ");
			
			for(int i=0;i<temp.length; i++) {
				arr[i+1] = Integer.parseInt(temp[i]);
			}
		}
		
		//0, L위치에 휴게소가 있다고 가정함
		arr[0] = 0;
		arr[arr.length-1] = L;
		
		//휴게소를 오름차순 정렬함
		Arrays.sort(arr);
		
		//휴게소의 최소 위치는 1, 최대 위치는 1000임
		int min = 1;
		int max = 1000;
		int answer = 0;
		
		while(min<=max) {
			//휴게소간 최대 거리를 mid로 설정함
			int mid = (min+max)/2;
			
			//두 휴게소간 거리가 mid보다 크다면 중간에 휴게소를 설치해야만
			//mid를 넘지 않게 되므로, 새로 휴게소를 설치한 횟수를 기록할 변수를 선언함
			int cnt = 0;
			
			for(int i=0; i<arr.length-1; i++) {
				//인접한 두 휴게소의 거리를 구함
				int distance = arr[i+1] - arr[i];
				
				//휴게소의 거리를 최대거리로 나눈 몫은 새로 설치해야할 휴게소의 개수를 나타냄
				//그런데 정확히 나누어 떨어지는 경우에는 마지막 위치에는 이미 휴게소가 존재하게 되므로
				//1을 빼줌으로써 해당 위치에는 휴게소를 설치하지 않아야함
				int v = distance%mid!=0?0:-1;
				
				//만약 최대 거리인 mid보다 두 휴게소의 거리가 더 크다면
				if(distance>mid) {
					//그 사이에 휴게소를 설치함
					cnt += (distance)/mid+v;
				}
			}

			//휴게소를 M개보다 더 많이 설치한 경우에는
			if(cnt>M) {
				//mid를 너무 작게 잡은 것이므로 이를 늘려야함
				min = mid + 1;
			//휴게소를 M개보다 더 적게 설치한 경우에는
			}else if(cnt<M) {
				//mid를 너무 크게 잡은 것이므로 이를 줄여야함
				max = mid - 1;
				
				//mid를 크게잡아서 M개 미만으로 휴게소를 설치한 경우
				//사이사이에 휴게소를 더 설치하더라도 최대값인 mid가 변하지 않으므로 이를 정답으로 갱신함
				answer = mid;
			//휴게소를 정확히 M개만큼 설치한 경우에는
			}else {
				//문제에서 구하고자 하는 것은, 인접한 두 휴게소간 거리의 최대값이 "최소"가 되도록 하는 것임
				//따라서, 아무리 현재 M개만큼 정확하게 휴게소를 설치했다하더라도,
				//동일하게 M개 만큼 설치하면서 그보다 거리가 최소가 되는경우가 있을 수 있으므로
				//mid를 줄임으로써 더 탐색을 이어나가야함
				max = mid - 1;
				
				//그때의 최대 거리를 정답으로 갱신함
				answer = mid;
			}
		}
		
		bw.write(answer+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}