// https://www.acmicpc.net/problem/11403 //
#include <iostream>

using namespace std;

int T[101][101];

int main() {
    int N;

    cin >> N;

    for(int i = 1; i<=N; ++i) {
        for(int j = 1; j <= N; ++j) {
            cin >> T[i][j];
        }
    }

    for(int k = 1; k <= N; ++k) {
        for(int s = 1; s <= N; ++s) {
            for(int e = 1; e <= N; ++e) {
                if(!T[s][e] && T[s][k] && T[k][e]) {
                    T[s][e] = 1;
                }
            }
        }
    }

    for(int i = 1; i <= N; ++i) {
        for(int j = 1; j <= N; ++j) {
            cout << T[i][j] << ' ';
        }
        cout << '\n';
    }


    return 0;
}