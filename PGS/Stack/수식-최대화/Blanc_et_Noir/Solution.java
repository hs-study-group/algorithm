//https://school.programmers.co.kr/learn/courses/30/lessons/67257#

import java.util.*;

class Solution {
    public static String expression;
    public static long answer = 0;
    
    public static HashMap<Character,Integer> setOp(String exp){
        char[] arr = exp.toCharArray();
        HashMap<Character,Integer> op = new HashMap<Character,Integer>();
        for(int i=0; i<arr.length; i++){
            if(arr[i]=='+'||arr[i]=='-'||arr[i]=='*'){
                op.put(arr[i],0);
            }
        }
        return op;
    }
    
    //순열을 계산하기전에 해당 연산문자열에 존재하는 연산의 종류와 그 갯수를 구함
    public static void beforePermuation(HashMap<Character,Integer> op){
        char[] arr = new char[op.size()];
        int idx = 0;
        for(char str : op.keySet()){
            arr[idx++] = str;
        }
        //순열을 계산함
        permutation(arr,new boolean[arr.length], 0, new char[arr.length]);
    }
    
    //순열을 계산하는 메소드
    public static void permutation(char[] arr,boolean[] v, int idx, char[] out){
        //순열을 모두 구했으면
        if(idx==arr.length){
            
            //해당 순열에 맞게 연산자에 우선순위를 부여함
            HashMap<Character,Integer> op = new HashMap<Character,Integer>();
            for(int i=0; i<out.length; i++){
                op.put(out[i],arr.length-i);
            }
            
            //연산자 우선순위에 맞게 postfix변환후 계산함
            long result = Math.abs(calculate(postfix(expression,op)));
            
            //계산된 값과 최대 값중 더 큰 값을 최대 값으로 설정
            if(answer < result){
                answer = result;
            }
        }else{
            //순열을 재귀적으로 계산함
            for(int i=0; i<arr.length; i++){
                if(v[i]){
                    continue;
                }
                v[i] = true;
                out[idx] = arr[i];
                permutation(arr,v,idx+1,out);
                v[i] = false;
            }
        }
    }
    
    //infix 수식을 postfix로 변환하는 메소드
    public static String postfix(String exp, HashMap<Character,Integer> op){
        //postfix변환에는 스택 자료구조가 필요함
        Stack<Character> s = new Stack<Character>();
        char[] arr = exp.toCharArray();
        
        //임시로 문자열을 저장할 변수
        String str = "";
        
        //postfix 변환 결과를 저장할 변수
        String result = "";
        
        for(int i=0; i<arr.length; i++){
            //숫자라면 임시 문자열에 저장함
            if(arr[i]>='0'&&arr[i]<='9'){
                str += Character.toString(arr[i]);
            //문자라면 기존에 저장해두었던 숫자를 postfix 결과변수에 저장함
            }else{
                result += str+" ";
                str = "";
                
                //스택이 비어있지 않다면
                if(!s.isEmpty()){
                    char cur = arr[i];
                    //우선순위를 비교해서 top값보다 우선순위가 크다면
                    if(op.get(cur)>op.get(s.peek())){
                        //그대로 스택에 쌓음
                        s.push(cur);
                    //우선순위가 같거나 top보다 작다면
                    }else{
                        //자신보다 우선순위가 작은 연산자가 나올때 까지 pop을 수행함
                        while(!s.isEmpty()&&op.get(cur)<=op.get(s.peek())){
                            result+=s.pop()+" ";
                        }
                        //그 후에 자신을 push함
                        s.push(cur);
                    }
                //스택이 비어있다면 연산자를 그냥 추가함
                }else{
                    s.push(arr[i]);
                } 
            }
        }
        //마지막에 저장되지 못한 숫자를 추가함
        result += str+" ";
        //스택에 저장된 남은 연산자들을 모조리 postfix 결과에 덧붙임
        while(!s.isEmpty()){
            result+=s.pop()+" ";
        }
        return result;
    }
    
    //postfix연산식을 계산하는 메소드
    public static long calculate(String post){
        String[] arr = post.split(" ");
        Stack<Long> s = new Stack<Long>();
        
        for(int i=0; i<arr.length; i++){
            //연산자라면
            if(arr[i].equals("*")||arr[i].equals("+")||arr[i].equals("-")){
                //스택에 저장된 두 피연산자를 꺼내어 계산함
                //나누기 또는 빼기의 경우 스택에 더 오래전에 있었던 숫자에서 바로 그 이후에 추가된 숫자를 빼거나 나눠야함
                long num2 = s.pop();
                long num1 = s.pop();
                if(arr[i].equals("*")){
                    s.push(num1*num2);
                }else if(arr[i].equals("+")){
                    s.push(num1+num2);
                }else{
                    s.push(num1-num2);
                }
            //숫자라면 그냥 스택에 추가함
            }else{
                s.push(Long.parseLong(arr[i]));
            }
        }
        //스택에 들어있는 마지막 값이 곧 결과값임
        return s.pop();
    }
    
    public long solution(String exp) {
        String post;
        expression = exp;
        beforePermuation(setOp(exp));
        return answer;
    }
}