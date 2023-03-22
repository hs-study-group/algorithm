//https://level.goorm.io/exam/47879/%EC%95%8C%EB%9E%8C-%EC%96%B4%ED%94%8C/quiz/1

import java.io.*;
import java.util.*;

class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	//문자열 str에서 len의 길이의 부분문자열을 추출하고, 이를 set에 추가하고, list에도 추가하는 메소드
	public static void segregate(ArrayList<String> list, HashSet<String> set,String str, int len){
		for(int i=0;i+len<=str.length();i++){
			//str 문자열로부터 len길이의 부분문자열을 추출함
			String key = str.substring(i,i+len);
			//set에 저장된 적이 없는 부분 문자열이라면
			if(!set.contains(key)){
				//그 문자열을 set과 list 모두에 추가함
				list.add(key);
				set.add(key);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String str = br.readLine();
		int K = Integer.parseInt(br.readLine());
		
		ArrayList<String> list = new ArrayList<String>();
		HashSet<String> set = new HashSet<String>();
		
		//문자열을 1부터 K까지의 길이의 부분 문자열들로 분리해보고 이를 list에 중복없이 추가함
		for(int i=1;i<=K;i++){
			segregate(list,set,str,i);
		}
		
		//list에 저장된 문자열을 오름차순 정렬함
		Collections.sort(list);

		//K-1인덱스에 해당하는 문자열을 출력함
		bw.write(list.get(K-1)+"\n");
		bw.flush();
		bw.close();
		br.close();
	}
}