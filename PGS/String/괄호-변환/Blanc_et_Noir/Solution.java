import java.util.Stack;

class Solution {
    //해당 문자열이 올바른 괄호 문자열인지 판단하는 메소드
    public static boolean isGood(String p){
        //문자열을 문자배열로 변환
        char[] arr = p.toCharArray();
        
        //해당 괄호가 짝이 맞는지 판단하기위해 스택 사용
        Stack<Character> s = new Stack<Character>();
        
        //문자를 하나씩 탐색함
        for(int i=0; i<arr.length; i++){
            //여는 괄호라면 스택에 추가함
            if(arr[i]=='('){
                s.push('(');
            //닫는 괄호라면
            }else{
                //스택이 비어있으면 짝이 맞지 않는 것임
                if(s.isEmpty()){
                    return false;
                //스택에 여는 괄호가 있다면 해당 괄호를 꺼냄
                }else{
                    s.pop();
                }
            }
        }
        //탐색이 완료된 후에 스택이 비어있지 않다면
        //여는 괄호에 대한 닫힌 괄호 짝이 부족한 것이므로 false
        if(!s.isEmpty()){
            return false;
        //모든 괄호의 짝이 맞으므로 true
        }else{
            return true;
        }
    }
    
    //균형잡힌 괄호 문자열인지 아닌지 판단하는 메소드
    //단순히 여는 괄호와 닫는 괄호의 숫자가 동일한지 아닌지만 판단하면 됨
    public static boolean isBalanced(String p){
        //해당 문자열을 문자 배열로변환함
        char[] arr = p.toCharArray();
        
        //여는 괄호와 닫는 괄호의 갯수의 차이를 저장할 변수
        int cnt = 0;
        
        for(int i=0; i<arr.length; i++){
            //여는 괄호라면 1을 증가시킴
            if(arr[i]=='('){
                cnt++;
            //닫는 괄호라면 1을 감소시킴
            }else{
                cnt--;
            }
        }
        //cnt값이 0이면, 즉 여는 괄호와 닫는 괄호의 수의 차이가 0이면
        if(cnt==0){
            //true를 리턴함
            return true;
        }else{
            //아니면 false를 리턴함
            return false;
        }
    }
    
    public static String process(String p){
        //빈 문자열이면 그대로 리턴함
        if(p.equals("")){
            return p;
        }
        //해당 문자열에서 두 문자열 u, v를 선정하기 위해 탐색함
        for(int i=2; i<=p.length(); i=i+2){
            String t = p.substring(0,i);
            //만약 더이상 분리할 수 없는 균형잡힌 괄호를 찾았다면
            if(isBalanced(t)){
                //해당 문자열은 u, 나머지 문자열은 v로 설정함
                String u = p.substring(0,i);
                String v = p.substring(i,p.length());
                //u가 올바른 괄호 문자열이라면
                if(isGood(u)){
                    //v에 대해 재귀적으로 처리를 수행한 결과를 덧붙임
                    return u + process(v);
                //u가 균형잡힌 괄호 문자열이긴하나, 올바른 괄호 문자열은 아닌 경우
                }else{
                    //빈 문자열에 (문자를 추가함
                    t = "(";
                    //거기에 v에 대해 재귀적으로 처리를 수행한 결과를 덧붙임
                    t = t + process(v);
                    //닫는 괄호를 덧붙임
                    t = t + ")";
                    //u에 대하여 시작과 끝 괄호를 제외한 나머지 괄호를 반전시킨 결과를 덧붙임
                    t = t + reverse(u.substring(1,u.length()-1));
                    return t;
                }
            }
        }
        return "";
    }
    
    //특정 괄호 문자열의 괄호들을 완전히 반전시키는 메소드
    public static String reverse(String p){
        //해당 문자열을 문자 배열로 변환함
        char[] arr = p.toCharArray();
        String r = "";
        
        //각 괄호에 대하여 아래와 같은 처리를 수행함
        for(int i=0; i<arr.length; i++){
            //여는 괄호라면 닫는 괄호로 변환
            if(arr[i]=='('){
                r += ")";
            //닫는 괄호라면 여는 괄호로 변환
            }else{
                r += "(";
            }
        }
        return r;
    }
    
    public String solution(String p) {
        return process(p);
    }
}