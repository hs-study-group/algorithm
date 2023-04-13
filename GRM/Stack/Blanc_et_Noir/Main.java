//https://level.goorm.io/exam/174717/%ED%81%B0-%EC%88%98%EC%8B%9D-%EC%B0%BE%EA%B8%B0/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//두 연산자의 우선순위를 비교하는 메소드
	//op1이 더 높은 우선순위라면 음수
	//op2가 더 높은 우선순위라면 양수
	//동일한 우선순위를 갖는다면 0을 리턴함
	public static int compare(HashMap<Character, Integer> hm, char op1, char op2){
		return hm.get(op2)-hm.get(op1);
	}
	
	//infix 표현을 postfix 표현으로 변환하는 메소드
	public static String infixToPostfix(String infix){
		//변환과정에 사용할 스택 선언
		Stack<Character> stack = new Stack<Character>();
		
		//연산자의 우선순위를 저장할 해쉬맵 선언 및 초기화
		HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		hm.put('+',1);
		hm.put('-',1);
		hm.put('*',2);
		
		//infix 표현을 문자 배열로 변환함
		char[] arr = infix.toCharArray();
		
		//수식의 숫자를 임시로 저장할 버퍼 선언
		StringBuilder sb = new StringBuilder();
		
		//postfix 변환 결과를 저장할 버퍼 선언
		StringBuilder postfix = new StringBuilder();
		
		for(char ch : arr){
			//탐색중인 문자가 숫자라면
			if(ch>='0'&&ch<='9'){
				//버퍼에 저장함
				sb.append(Character.toString(ch));
			//탐색중인 문자가 기호라면
			}else{
				//버퍼에 저장해두었던 숫자를 결과 버퍼에 저장함
				postfix.append(sb.toString()+" ");
				
				//버퍼를 초기화함
				sb = new StringBuilder();
				
				//스택에 아직 연산자가 있는데, 그 연산자의 우선순위보다 지금 탐색중인 연산자의 우선순위가 더 크거나, 동일하다면
				while(!stack.isEmpty()&&compare(hm,stack.peek(),ch)<=0){
					//현재탐색중인 연산자보다 우선순위가 높거나, 같은 연산자들을 스택에서 꺼내어 결과 버퍼에 저장함
					postfix.append(stack.pop()+" ");
				}
				
				//현재 탐색중인 연산자를 스택에 추가함
				stack.push(ch);
			}
		}
		
		//버퍼에 숫자가 남아있으므로 이 숫자를 결과 버퍼에 저장함
		postfix.append(sb.toString()+" ");
		
		//스택에 남아있는 모든 연산자를 결과 버퍼에 저장함
		while(!stack.isEmpty()){
			postfix.append(stack.pop()+" ");
		}
		
		//변환 결과를 trim후에 리턴함
		return postfix.toString().trim();
	}
	
	//전달받은 postfix 연산식을 계산한 결과를 리턴하는 메소드
	public static long parse(String postfix){
		//계산의 편의를 위해 postfix 표현을 문자 배열로 변환함
		char[] arr = postfix.toCharArray();
		
		//계산에 사용할 스택 선언
		Stack<Long> stack = new Stack<Long>();
		
		//숫자를 임시로 저장할 버퍼 선언
		StringBuilder sb = new StringBuilder();
		
		//문자를 하나씩 탐색함
		for(char ch : arr){
			//만약 그 문자가 숫자라면
			if(ch>='0'&&ch<='9'){
				//버퍼에 저장함
				sb.append(Character.toString(ch));
			//만약 그 문자가 연산자라면
			}else if(ch=='+'||ch=='-'||ch=='*'){
				//두 피연산자를 스택에서 차례로 꺼냄
				long v2 = stack.pop();
				long v1 = stack.pop();
				
				//연산자에 따라 적절히 +, -, *한 결과를 구한후 그 값을 스택에 다시 추가함
				switch(ch){
					case '+': stack.push(v1+v2);break;
					case '-': stack.push(v1-v2);break;
					default : stack.push(v1*v2);break;
				}
			
			//만약 그 문자가 공백이라면
			}else{
				//버퍼에 저장된 숫자가 있다면
				if(sb.toString().length()>0){
					//그 숫자를 스택에 추가함
					stack.push(Long.parseLong(sb.toString()));
					
					//버퍼를 초기화 함
					sb = new StringBuilder();
				}
			}
		}
		
		//스택에 남아있는 유일한 값이 바로 연산의 최종결과이므로 그것을 리턴함
		return stack.pop();
	}
	
	public static void main(String[] args) throws Exception {
		String[] temp = br.readLine().split(" ");
		
		//입력받은 infix 형태의 수식을 postfix 형태로 변환함
		String postfix1 = infixToPostfix(temp[0]);
		String postfix2 = infixToPostfix(temp[1]);
		
		//두 postfix 수식을 계산한 결과중 더 큰값을 출력함
		bw.write(Math.max(parse(postfix1),parse(postfix2))+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}