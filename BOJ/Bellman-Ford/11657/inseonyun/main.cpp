
//////////////////////////////////////////////////
// BAEKJOON_11657번 : 타임머신
//////////////////////////////////////////////////

#include <iostream>
#include <vector>

#define INF 987654321

using namespace std;

// N : 도시의 개수, M : 버스 노선의 개수
int N, M;
vector<pair<int, int>> map[501];
long long dist[501] = { 0, };
bool cycle = false;
void input() {
	cin >> N >> M;

	// initialize
	for (int i = 1; i <= N; i++) {
		dist[i] = INF;
	}

	for (int i = 0; i < M; i++) {
		int A, B, C;
		cin >> A >> B >> C;

		map[A].push_back({B, C});
	}
}

void solution() {
	dist[1] = 0;

	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			for (int k = 0; k < map[j].size(); k++) {
				int next_cur = map[j][k].first;
				int next_cost = map[j][k].second;

				if (dist[j] != INF && dist[next_cur] > dist[j] + next_cost) {
					dist[next_cur] = dist[j] + next_cost;
					if (i == N)
						cycle = true;
				}
			}
		}
	}

}

void output() {
	if (cycle)
		cout << -1 << "\n";
	else {
		for (int i = 2; i <= N; i++) {
			if (dist[i] == INF)
				cout << -1 << "\n";
			else
				cout << dist[i] << "\n";
		}
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	solution();
	output();

	return 0;
}