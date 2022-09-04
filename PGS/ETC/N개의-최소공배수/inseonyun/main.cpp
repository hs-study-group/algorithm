// https://school.programmers.co.kr/learn/courses/30/lessons/12953

#include <iostream>
#include <string>
#include <vector>

using namespace std;

int gcd(int a, int b) {
    if (b == 0)
        return a;
    else
        return gcd(b, a % b);
}

int solution(vector<int> arr) {
    int answer = arr[0];
    
    for (int i = 1; i < arr.size(); i++) {
        int tmp = gcd(answer, arr[i]);
        answer = (answer * arr[i]) / tmp;
    }
    
    return answer;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    cout << solution({ 2,6,8,14 });

    return 0;
}