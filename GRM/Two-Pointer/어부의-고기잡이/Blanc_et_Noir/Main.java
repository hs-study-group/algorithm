import java.io.*;
class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		int[] arr = new int[N];
		
		//물고기의 수를 입력 받음
		temp = br.readLine().split(" ");
		for(int i=0;i<arr.length;i++){
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//투포인터 알고리즘에 사용할 두 포인터를 선언함
		//s는 구간의 시작, e는 구간의 끝을 가리킴
		int s = 0;
		int e = 0;
		//구간의 연속합을 저장할 변수
		int sum = arr[0];
		//물고기를 정확히 M만큼만 잡을 수 있는 구간의 개수를 저장할 변수
		int cnt = 0;
		
		//만약 초기의 상태가 M개의 물고기를 잡을 수 있는 상태라면
		if(sum==M) {
			//그것을 카운트함
			cnt++;
		}
		
		while(true){
			//만약 현재 구간의 합이 M보다 크다면
			if(sum>M){
				//시작 포인터를 우측으로 이동시키고 구간합을 갱신함
				sum-=arr[s++];
			//만약 현재 구간의 합이 M이라면
			}else if(sum==M){
				//시작포인터를 우측으로 이동시키고 구간합을 갱신함
				sum-=arr[s++];
				//카운트함
				cnt++;
			//종료 포인터가 배열을 벗어난 경우
			}else if(e+1==arr.length){
				//반복을 종료함
				break;
			//만약 현재 구간의 합이 M보다 작다면
			}else{
				//종료 포인터를 우측으로 이동시키고 구간합을 갱신함
				sum+=arr[++e];
			}
		}
		
		bw.write(cnt+"\n");
		bw.flush();
		br.close();
		bw.close();
	}
}