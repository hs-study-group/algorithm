//https://www.acmicpc.net/problem/9935

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main(String[] args) throws Exception{
		//문자열을 저장할 스택
		Stack<Character> s = new Stack<Character>();
		
		//입력받은 문자열을 문자 배열로 변환함
		char[] temp = br.readLine().toCharArray();
		
		//폭탄의 역할을 수행할 문자열
		String str = br.readLine();
		
		//폭탄 문자열을 문자 배열로 변환함
		char[] bomb = str.toCharArray();
		
		//문자를 탐색하면서
		for(int i=0; i<temp.length; i++) {
			//문자를 일단 Stack에 push함
			s.push(temp[i]);
			
			//만약 문자가 폭탄의 끝 문자라면
			if(temp[i]==bomb[bomb.length-1]) {
				//Stack에서 문자를 폭탄의 길이만큼 꺼낼 수 있을때
				if(s.size()>=bomb.length) {
					
					StringBuilder sb = new StringBuilder();
					
					//폭탄의 길이만큼 Stack에서 문자를 꺼냄
					for(int j=0; j<bomb.length; j++) {
						sb.append(s.pop()+"");
					}
					
					//해당 문자열을 반대로 역순으로 배치한 후 폭탄과 비교함
					//이때 reverse( )사용시 원본 문자열 자체가 변함
					//따라서 이후에 또 다시 reverse 할 필요는 없음
					if(sb.reverse().toString().equals(str)) {
						
					//폭탄과 일치하지 않으면
					}else {
						//해당 문자열들을 다시 Stack에 순서를 유지한채 push함
						char[] arr = sb.toString().toCharArray();
						for(int j=0; j<arr.length; j++) {
							s.push(arr[j]);
						}
					}
				}
			}
		}
		
		//모든 문자를 탐색했을때 Stack이 비어있다면 모든 문자가 사라질 수 있다는 의미임
		if(s.isEmpty()) {
			bw.write("FRULA\n");
		//모든 문자를 탐색했을때 Stack이 비어있지 않다면 모든 문자가 사라질 수 없다는 의미임
		}else {
			StringBuilder sb = new StringBuilder();

			//Stack에 저장된 문자들을 역순으로 정렬하기위해 StringBuilder에 추가함
			while(!s.isEmpty()) {
				sb.append(s.pop()+"");
			}
			
			//StringBuilder를 역순으로 정렬함
			sb.reverse();
			
			//남은 문자열을 출력함
			bw.write(sb.toString()+"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}