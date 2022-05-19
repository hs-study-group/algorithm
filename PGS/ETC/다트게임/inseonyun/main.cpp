
//////////////////////////////////////////////////
// PROGRAMMERS : 다트 게임
//////////////////////////////////////////////////

#include <string>
#include <vector>
using namespace std;

int solution(string dartResult) {
    int answer = 0;
    vector<int> score;
    
    int dart_count = 0;
    int i = 0;
    while(i < dartResult.length()) {
        int current_score = dartResult[i] - '0';
        if(current_score == 1 && dartResult[i + 1] == '0') {
                current_score = 10;
                i++;
        }
        i++;
        if(dartResult[i] == 'D') {
            current_score = current_score * current_score;
        } else if(dartResult[i] == 'T') {
            current_score = current_score * current_score * current_score;
        }
        i++;
        dart_count++;
        
        int prev_score = 0;
        if(dartResult[i] == '*') {
            if(dart_count != 1) {
                prev_score = score[dart_count - 2];
                score[dart_count - 2] = prev_score * 2;
            }
            current_score = current_score * 2;
            score.push_back(current_score);
        } else if(dartResult[i] == '#') {
            current_score = current_score * -1;
            score.push_back(current_score);
        }else {
            score.push_back(current_score);
            continue;
        }
        
        i++;
    }
    
    for(int i : score) {
        answer += i;
    }
    
    return answer;
}