#include <string>
#include <vector>
#include <malloc.h>

using namespace std;

int len = 0;
int cnt = 0;

//입력받은 정수 n을 이진수로 변환하고 그 길이를 계산하는 함수
void getBinaryString(int *p, int n, int idx){
    //n값이 0이 아니라면
    if(n!=0){
        //이진 문자열의 길이를 1 증가시킴
        len++;

        //해당 자릿수를 이진 변환된 값으로 초기화 함.
        *(p+idx) = n%2;

        //재귀적으로 이진수를 구함
        getBinaryString(p,n/2,idx+1);

    //n값이 0이라면
    }else{
        //이진수의 가장 맨 앞에 0을 추가하여 계산에 용이하게 함
        *(p+idx) = 0;
        
        //이진 문자열의 길이를 1 증가시킴
        len++;
    }
}

//idx위치를 기준으로 그 하위의 1을 모두 최하단으로 치우는 함수
void putAside(int *p, int idx){
    //최하단부터 1값을 채워넣음
    for(int i=0; i<idx; i++){
        //아직 치워야 할 1이 남아있다면
        if(cnt>1){
            //해당 자리를 1로 초기화 함
            *(p+i) = 1;
            //치워야 할 1의 개수를 1감소시킴
            cnt--;
        //모든 1을 최하단으로 치웠다면
        }else{
            //해당 자리를 0으로 초기화 함
            *(p+i) = 0;
        }
    }
}

int solution(int n) {
    int answer = 0;
    
    //이진변환된 값을 저장할 포인터 선언 및 메모리 할당
    int *p = (int*) malloc(sizeof(int)*1000);
    
    //n을 이진변환 함
    getBinaryString(p,n,0);

    for(int i=0; i<len; i++){

        //만약 해당 위치의 비트가 1이라면
        if(*(p+i)==1){
            //치워야할 1의 개수를 1증가시킴
            cnt++;
        }
        
        //만약 해당 위치의 비트가 1이고, 그 보다 자릿수가 하나 높은 자리의 비트가 0이라면
        if(*(p+i)==1&&*(p+i+1)==0){

            //두 비트를 교환함
            int temp = *(p+i);
            *(p+i) = *(p+i+1);
            *(p+i+1) = temp;
            
            //기준이 되는 자리 이전의 모든 1값을 갖는 비트를 전부 최하단으로 치움
            putAside(p,i);
            
            int digit = 1;
            
            //다음 큰 숫자가 비트로 저장되어있으므로, 이를 10진수로 변환함
            for(int j=0; j<len; j++){
                    
                if(*(p+j)==1){
                    answer += digit;
                }
                    
                digit*=2;
            }
            break;
        }
    }
    
    //메모리 할당을 해제함
    free(p);
    
    //다음 큰 숫자를 반환함
    return answer;
}