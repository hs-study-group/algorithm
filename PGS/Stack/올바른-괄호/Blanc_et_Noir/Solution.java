import java.util.*;

class Solution {
    boolean solution(String s) {
        //괄호가 올바른 괄호인지 아닌지 판단할때엔 Stack 자료구조 사용
        Stack<Character> stack = new Stack<Character>();
        
        //효율성을 위해 char 배열로 변경함
        char[] arr = s.toCharArray();
        
        for(char c : arr){
            //여는 괄호라면 종류에 상관없이 스택에 추가함
            if(c=='('){
                stack.push('(');
            //닫는 괄호라면
            }else{
                //괄호의 종류가 소괄호 하나뿐이므로 굳이 괄호의 종류를 따지지 않고
                //여는 괄호가 있는지 없는지 유무만 판단함
                if(stack.isEmpty()){
                    return false;
                }else{
                    stack.pop();
                }
            }
        }
        
        //문자열을 모두 탐색했는데도 여는 괄호의 짝이 없다면 올바른 괄호가 아님
        if(!stack.isEmpty()){
            return false;
        //여는 괄호의 짝을 모두 찾았으므로 올바른 괄호임
        }else{
            return true;
        }
    }
}