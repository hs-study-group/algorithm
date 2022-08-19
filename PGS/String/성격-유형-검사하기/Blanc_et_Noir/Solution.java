//https://school.programmers.co.kr/learn/courses/30/lessons/118666

class Solution {
    public static int[] result = new int[8];
    
    //어떤 성격유형을 묻는 문제에 대해 선택한 점수를 분석하여 해당 성격에 가산점을 부여하는 메소드
    public static void set(String str, int num){
        char a = str.charAt(0);
        char b = str.charAt(1);
        
        //배우 비동의 ~ 약간 비동의라면
        if(num>=1&&num<=3){
        	//AB유형중에서 A에 가산점을 부여함
            result[get(a)] += (4 - num);
        //매두 동의 ~ 약간 동의라면
        }else if(num>=5&&num<=7){
        	//AB유형중에서 B에 가산점을 부여함
            result[get(b)] += (num - 4);
        }
    }
    
    //어떤 성격 유형에 대하여 인덱스를 반환하는 메소드
    public static int get(char c){        
        switch(c){
            case 'R':return 0;
            case 'T':return 1;
            case 'C':return 2;
            case 'F':return 3;
            case 'J':return 4;
            case 'M':return 5;
            case 'A':return 6;
            case 'N':return 7;
        }
        return -1;
    }
    
    public String solution(String[] survey, int[] choices) {
        String answer = "";
        
        //성격 유형을 분석함
        for(int i=0; i<choices.length; i++){
            set(survey[i],choices[i]);
        }
        
        //점수가 더 높거나 동일하다면 알파벳 순으로 더 먼저인 성격 유형이 되도록 해야함
        if(result[0]>=result[1]){
            answer += "R";
        }else{
            answer += "T";
        }
        
        if(result[2]>=result[3]){
            answer += "C";
        }else{
            answer += "F";
        }
        
        if(result[4]>=result[5]){
            answer += "J";
        }else{
            answer += "M";
        }
        
        if(result[6]>=result[7]){
            answer += "A";
        }else{
            answer += "N";
        }
        
        return answer;
    }
}