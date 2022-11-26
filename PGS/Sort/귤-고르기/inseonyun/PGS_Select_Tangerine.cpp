
//////////////////////////////////////////////////
// PROGRAMMERS: Level2_±Ö °í¸£±â
//////////////////////////////////////////////////

#include <string>
#include <algorithm>
#include <vector>

using namespace std;

bool compare(int a, int b) {
    return a > b;
}

int solution(int k, vector<int> tangerine) {
    int answer = 0;

    sort(tangerine.begin(), tangerine.end());
    vector<int> t_cnt;
    t_cnt.push_back(0);
    int prev = -1;
    for (int i = 0; i < tangerine.size(); i++) {
        if (prev == tangerine[i])
            continue;
        else {
            int cnt = count(tangerine.begin(), tangerine.end(), tangerine[i]);
            t_cnt.push_back(cnt);
            prev = tangerine[i];
        }
    }

    sort(t_cnt.begin(), t_cnt.end(), compare);

    for (int i = 0; i < t_cnt.size(); i++) {
        k -= t_cnt[i];
        answer++;
        if (k <= 0)
            break;
    }

    return answer;
}
