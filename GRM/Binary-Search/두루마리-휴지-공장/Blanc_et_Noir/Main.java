import java.io.*;
class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		int N = Integer.parseInt(temp[0]);
		long M = Long.parseLong(temp[1]);
		
		temp = br.readLine().split(" ");
		
		//N개의 휴지 길이를 입력받음
		long[] arr = new long[N];
		for(int i=0;i<arr.length;i++){
			arr[i]=Long.parseLong(temp[i]);
		}
		
		//가장 긴 휴지 길이를 저장할 변수
		long max = 0;
		
		//가장 긴 휴지를 기준으로 다른 휴지들의 모자란 길이를 누적하여 더할 변수
		long sum = 0;
		
		//가장 긴 휴지 길이를 구함
		for(int i=0;i<arr.length;i++){
			if(arr[i]>max){
				max=arr[i];
			}
		}
		
		//가장 긴 휴지를 기준으로 나머지 휴지들의 모자란 길이들의 합을 구함
		for(int i=0;i<arr.length;i++){
			sum += max - arr[i];
		}
		
		//만약 모자란 정도의 합이 M보다 크다면
		if(sum>M){
			//절대로 모든 휴지의 길이를 동일하게 맞출 수 없음
			bw.write("No way!\n");
		//만약 모자란 정도의 합이 M보다 작거나 같다면
		}else{
			//동일한 길이가 되도록 휴지를 배분하고 남은 길이를 구함
			M -= sum;
			//최대길이를 기준으로 배분하고 남은 길이를 N으로 나눈 몫을 나머지 휴지들에게 추가적으로 배분함
			max += (M/N);
			//그때의 휴지 길이를 출력함
			bw.write(max+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}
}