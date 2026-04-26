#include <iostream>
#include <limits.h>

using namespace std;

int T_cost[101][101] ;
int T_mcost[101][101];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int N, M;
    cin >> N >> M;

    fill(&T_cost[0][0], &T_cost[0][0] + (101*101), INT_MAX / 2);

    for(int i=1; i<=N; ++i) {
        T_cost[i][i] = 0;
    }

    for(int i = 0; i<M; ++i) {
        int start, end, cost;

        cin >> start >> end >> cost;
        T_cost[start][end] = min(T_cost[start][end], cost);


    }

    for(int k = 1; k <= N; ++k) {
        for(int s = 1; s <= N; ++s) {
            for(int e = 1; e <= N; ++e) {
                T_cost[s][e] = min(T_cost[s][e], T_cost[s][k] + T_cost[k][e]);
            }
        }
    }

    for(int i = 1; i<=N; ++i) {
        for(int j = 1; j<=N; ++j) {
            if(T_cost[i][j] == (INT_MAX / 2))
                cout << 0 <<' ';
            else
                cout << T_cost[i][j] << ' ';

        }
        cout << '\n';
    }

    return 0;

}