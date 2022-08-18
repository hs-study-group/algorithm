
//////////////////////////////////////////////////
// PROGRAMMERS : 성격 유형 검사하기
//////////////////////////////////////////////////

#include <string>
#include <vector>

using namespace std;

int scoreA[] = {0, 3, 2, 1};
int scoreB[] = {0, 1, 2, 3};

vector<int> calc_score(string mbti, int choice) {
    int a = 0, b = 0;
    if(mbti[0] == 'R' || mbti[0] == 'C' || mbti[0] == 'J' || mbti[0] == 'A') {
        if(choice <= 3) {
            a = scoreA[choice];
        }else {
            b = scoreB[choice - 4];
        }
    } else {
        if(choice <= 3) {
            b = scoreA[choice];
        }else {
            a = scoreB[choice - 4];
        }
    }
    
    return {a, b};
}

string solution(vector<string> survey, vector<int> choices) {
    string answer = "";
    
    vector<int> total_first(2, 0);
    vector<int> total_second(2, 0);
    vector<int> total_third(2, 0);
    vector<int> total_four(2, 0);
    
    for(int i = 0; i < choices.size(); i ++) {
        vector<int> score = calc_score(survey[i], choices[i]);
        if(survey[i].compare("RT") == 0 || survey[i].compare("TR") == 0) {
            total_first[0] += score[0];
            total_first[1] += score[1];
        } else if(survey[i].compare("CF") == 0 || survey[i].compare("FC") == 0) {
            total_second[0] += score[0];
            total_second[1] += score[1];
        } else if(survey[i].compare("JM") == 0 || survey[i].compare("MJ") == 0) {
            total_third[0] += score[0];
            total_third[1] += score[1];
        } else {
            total_four[0] += score[0];
            total_four[1] += score[1];
        }
    }

    if(total_first[0] < total_first[1]) {
        answer += "T";
    } else {
        answer += "R";
    }

    if(total_second[0] < total_second[1]) {
        answer += "F";
    } else {
        answer += "C";
    }

    if(total_third[0] < total_third[1]) {
        answer += "M";
    }else {
        answer += "J";
    }

    if(total_four[0] < total_four[1]) {
        answer += "N";
    } else  {
        answer += "A";
    }

    return answer;
}
