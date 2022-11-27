
//////////////////////////////////////////////////
// PROGRAMMERS : Level2_숫자 카드 나누기
//////////////////////////////////////////////////

#include <algorithm>
#include <vector>

using namespace std;

int gcd(int a, int b) {
    if (b == 0)
        return a;
    else
        return gcd(b, a % b);
}

int solution(vector<int> arrayA, vector<int> arrayB) {
    int answer = 0;

    int arrayA_gcd = arrayA[0];
    int arrayB_gcd = arrayB[0];

    int resA = 0;
    int resB = 0;
    for (int i = 1; i < arrayA.size(); i++) {
        arrayA_gcd = gcd(arrayA_gcd, arrayA[i]);
        arrayB_gcd = gcd(arrayB_gcd, arrayB[i]);
    }

    if (arrayA_gcd == 1 && arrayB_gcd == 1) {
        return 0;
    }

    if (arrayA_gcd != 1) {
        bool checked = true;

        for (int i = 0; i < arrayB.size(); i++) {
            if (arrayB[i] % arrayA_gcd == 0) {
                checked = false;
                break;
            }
        }
        if (checked)
            resA = arrayA_gcd;
    }

    if (arrayB_gcd != 1) {
        bool checked = true;

        for (int i = 0; i < arrayA.size(); i++) {
            if (arrayA[i] % arrayB_gcd == 0) {
                checked = false;
                break;
            }
        }
        if (checked)
            resB = arrayB_gcd;
    }

    answer = max(resA, resB);

    return answer;
}