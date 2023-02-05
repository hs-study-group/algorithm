//https://www.acmicpc.net/problem/17298

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Stack;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String[] args) throws Exception {
		int N = Integer.parseInt(br.readLine());		
		String[] temp = br.readLine().split(" ");
		
		int[] arr = new int[N];
		
		//자신보다 오른쪽에 있으면서, 큰 수가 어떤 인덱스에 있는지를 저장할 배열
		int[] result = new int[N];
		
		//편의상 모든 수의 오큰수는 -1의 위치에 있다고 초기화함
		Arrays.fill(result, -1);
		
		//정수를 입력 받음
		for(int i=0; i<arr.length; i++) {
			arr[i] = Integer.parseInt(temp[i]);
		}
		
		//오큰수들을 저장할 스택 선언
		Stack<Integer> s = new Stack<Integer>();
		
		//배열의 마지막 인덱스부터 탐색함
		for(int i=arr.length-1; i>=0; i--) {
			//스택이 비어있지 않다면
			while(!s.isEmpty()) {
				//스택의 top이 자신보다 크다면
				if(s.peek()>arr[i]) {
					//그것이 바로 자신의 오큰수임
					result[i] = s.peek();
					break;
				//스택의 top이 자신보다 작다면
				}else {
					//pop()하여 top의 값을 제거함
					s.pop();
				}
			}
			
			//자신을 스택에 추가함
			s.push(arr[i]);
		}
		
		//오큰수들의 인덱스를 차례대로 출력함
		for(int i=0; i<result.length; i++) {
			bw.write(result[i]+" ");
		}
		
		bw.write("\n");
		bw.flush();
		bw.close();
		br.close();
	}
}