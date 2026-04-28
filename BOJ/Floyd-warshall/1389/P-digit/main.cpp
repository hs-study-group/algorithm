// https://www.acmicpc.net/problem/1389 //

#include <iostream>
#include <queue>
#include <limits.h>


using namespace std;

typedef pair<int, int> bacon_level;
priority_queue <bacon_level, vector <bacon_level>, greater<bacon_level>> myqueue;
int T_level[101][101];
bool T_bud[101][101];

int main() {
    int N, M;

    cin >> N >> M;

    fill(&T_level[0][0], &T_level[0][0] + (101 * 101), INT_MAX / 2);
    fill(&T_bud[0][0], &T_bud[0][0] + (101 * 101), false);

    for(int i = 0; i < M; ++i) {
        int a, b;
        cin >> a >> b;

        T_level[a][b] = 1;
        T_level[b][a] = 1;
    }

    for(int k = 1; k <= N; ++k) {
        for(int s = 1; s <= N; ++s) {
            for(int e = 1; e <= N; ++e) {
                T_level[s][e] = min(T_level[s][e], T_level[s][k] + T_level[k][e]);
            }
        }
    }

    
    for(int i = 1; i <= N; ++i) {
        int bacon = 0;
        for(int j = 1; j <= N; ++j) {
            if(i == j) continue;
            bacon += T_level[i][j];
        }
        myqueue.push(make_pair(bacon, i));
    }

    cout << myqueue.top().second;

    return 0;
}