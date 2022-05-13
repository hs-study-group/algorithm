
//////////////////////////////////////////////////
// PROGRAMMERS : 실패율
//////////////////////////////////////////////////

#include <string>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

bool cmp(pair<int, double>& a, pair<int, double>& b) {
    if (a.second == b.second) {
        return a.first < b.first;
    }
    else {
        return a.second > b.second;
    }

}

vector<int> solution(int N, vector<int> stages) {
    vector<int> answer;

    map<int, double> stage_failure_rate;
    // stages에는 각 사람들이 멈춰있는 stage가 들어있다.
    // Ex) 2, 1, 2 는 1스테이지에 멈춰있는 사람 1명, 2스테이지에 멈춰있는 사람 2명
    // 고로 실패율은 1스테이지 1/3 , 2스테이지 2/2가 된다.
    // 실패율은 통과 못한 인원 / 스테이지 도달 플레이어 수 
    double current_person = stages.size();

    // N : 스테이지 수
    for (int i = 0; i < N; i++) {
        double failure_rate = 0;
        double current_stage_person = 0;
        int tmp = 0;
        // 플레이어 방문
        for (int j = 0; j < stages.size(); j++) {
            if (stages[j] == i + 1) {
                tmp++;
                current_stage_person++;
            }
        }

        if (current_stage_person == 0) {
            failure_rate = 0;
        }
        else {
            failure_rate = current_stage_person / current_person;
        }
        current_person -= tmp;
        stage_failure_rate[i + 1] = failure_rate;
    }

    // value를 기준으로 내림차순 정렬
    vector<pair<int, double>> tmp;

    for (auto& it : stage_failure_rate) {
        tmp.push_back(it);
    }
    sort(tmp.begin(), tmp.end(), cmp);

    for (auto& i : tmp) {
        answer.push_back(i.first);
    }

    return answer;
}