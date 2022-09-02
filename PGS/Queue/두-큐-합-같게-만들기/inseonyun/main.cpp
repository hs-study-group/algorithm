#include <string>
#include <vector>
#include <queue>

using namespace std;

int solution(vector<int> queue1, vector<int> queue2) {
    int answer = 0;

    long long sum_of_q1 = 0;
    long long sum_of_q2 = 0;

    queue<int> q1;
    queue<int> q2;

    for (int i = 0; i < queue1.size(); i++) {
        sum_of_q1 += queue1[i];
        sum_of_q2 += queue2[i];

        q1.push(queue1[i]);
        q2.push(queue2[i]);
    }

    while (sum_of_q1 != sum_of_q2) {
        int value;

        if (sum_of_q1 > sum_of_q2) {
            value = q1.front();

            sum_of_q1 -= value;
            sum_of_q2 += value;

            q1.pop();
            q2.push(value);
        }
        else {
            value = q2.front();

            sum_of_q1 += value;
            sum_of_q2 -= value;

            q1.push(value);
            q2.pop();
        }
        answer++;

        if (answer > (q1.size() + q2.size()) * 2) {
            answer = -1;
            break;
        }
    }

    return answer;
}