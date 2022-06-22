import java.util.Arrays;
import java.util.HashSet;

class Solution {
    //해당 문자열이 banned_id형식과 일치하는지 판단하는 메소드
    public static boolean isBannedId(String user_id, String banned_id){
        //문자열의 길이가 서로 다르면 무조건 false
        if(user_id.length()!=banned_id.length()){
            return false;
        //문자열의 길이가 서로 같을때
        }else{
            //두 문자열을 문자 배열로 변환하고
            char[] arr1 = user_id.toCharArray();
            char[] arr2 = banned_id.toCharArray();
            
            //한 문자씩 탐색해나가는데
            for(int i=0; i<arr1.length; i++){
                //만약 *문자라면
                if(arr2[i]=='*'){
                    //어떤 문자가 오더라도 상관 없으므로 continue
                    continue;
                //만약 *문자가 아니라면
                }else{
                    //서로 일치한다면 continue
                    if(arr1[i]==arr2[i]){
                        continue;
                    //서로 일치하지 않는다면 해당 banned_id 형식에 맞지 않는 것이므로 false
                    }else{
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static HashSet<String> set = new HashSet<String>();
    
    public static void DFS(String[] user_id, String[] banned_id, String out, boolean[] v, int idx, int cnt){
        //순열을 모두 구했으면
        if(cnt==0){
            String str = "";
            String[] arr = out.split(" ");
            
            //해당 문자열 순열에 대해 형식이 모두 일치하는지 확인
            for(int i=1; i<arr.length; i++){
                //하나라도 형식이 일치하지 않으면 잘못 구한것이므로 return
                if(!isBannedId(arr[i],banned_id[i-1])){
                    return;
                }
            }
            
            //여기까지 도달한것이면 해당 문자열 순열은 형식이 banned_id와 모두 일치한 것이며
            //이때 서로 다른 문자열 순열이라 하더라도 형식자체는 banned_id가 서로 같을 수 있음
            
            //예를들어 frodo crodo순열과 crodo frodo 순열은 모두
            //***** 라는 banned_id 형식에 성립하지만
            //결국 같은 *****라는 형식에 맞는 것은 동일하므로 중복된 것으로 처리하기위해서
            //해당 순열을 정렬함
            
            Arrays.sort(arr);
            
            //정렬된 순열의 문자열들을 하나로 합침
            for(int i=1; i<arr.length; i++){
                str += arr[i]+" ";
            }
            //결과적으로 frodo crodo 순열과 crodo frodo순열은 모두
            //crodo frodo 라는 형태로 해당 banned_id 를 만족한 것임
            //중복을 허용하지않은 set에 저장함
            set.add(str);
        }else{
            //순열을 계산하고자 반복함
            for(int i=0; i<user_id.length; i++){
                //아직 선택하지않은 문자열이라면
                if(!v[i]){
                    v[i] = true;
                    
                    //DFS 순열 탐색을 수행함
                    //이때 out + " " + user_id[i]에 대해서
                    //처음 문자열 하나를 선택할때에 " "라는 공백이 가장 맨 앞에 들어가므로
                    //63번 라인에서 이를 신경써서 공백다음부터 반복하도록 i=1을 설정함
                    DFS(user_id,banned_id,out+" "+user_id[i],v,idx+1,cnt-1);
                    
                    v[i] = false;
                }
            }
        }
    }
    
    public int solution(String[] user_id, String[] banned_id) {
        int answer;

        DFS(user_id, banned_id, "", new boolean[user_id.length],0, banned_id.length);
        
        //집합의 크기가 곧 정답임
        answer = set.size();
        
        return answer;
    }
}