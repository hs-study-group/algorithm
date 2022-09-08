// https://school.programmers.co.kr/learn/courses/30/lessons/42748

#include <vector>
#include <algorithm>

using namespace std;

vector<int> solution(vector<int> array, vector<vector<int>> commands) {
    vector<int> answer;

    for (int test_case = 0; test_case < commands.size(); test_case++) {
        int i = commands[test_case][0] - 1;
        int j = commands[test_case][1] - 1;
        int k = commands[test_case][2] - 1;

        vector<int> copy_array(j - i + 1);
        copy(array.begin() + i, array.begin() + j + 1, copy_array.begin());
        sort(copy_array.begin(), copy_array.end());

        answer.push_back(copy_array[k]);
    }

    return answer;
}

int main() {

    solution({ 1, 5, 2, 6, 3, 7, 4 }, { {2, 5, 3},{4, 4, 1},{1, 7, 3} });

    return 0;
}