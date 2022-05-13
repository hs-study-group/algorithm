
//////////////////////////////////////////////////
// PROGRAMMERS : ������
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
    // stages���� �� ������� �����ִ� stage�� ����ִ�.
    // Ex) 2, 1, 2 �� 1���������� �����ִ� ��� 1��, 2���������� �����ִ� ��� 2��
    // ��� �������� 1�������� 1/3 , 2�������� 2/2�� �ȴ�.
    // �������� ��� ���� �ο� / �������� ���� �÷��̾� �� 
    double current_person = stages.size();

    // N : �������� ��
    for (int i = 0; i < N; i++) {
        double failure_rate = 0;
        double current_stage_person = 0;
        int tmp = 0;
        // �÷��̾� �湮
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

    // value�� �������� �������� ����
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