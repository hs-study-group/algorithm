
//////////////////////////////////////////////////
// BAEKJOON_1956번 : 운동
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

#define INF 987654321

int V, E;
int map[402][402];
void input() {
	cin >> V >> E;
	for (int i = 1; i <= V; i++) {
		for (int j = 1; j <= V; j++) {
			map[i][j] = INF;
		}
	}
	
	for (int i = 0; i < E; i++) {
		int a, b, c;
		cin >> a >> b >> c;

		map[a][b] = c;
	}
}

void solution() {
	// k = 거쳐가는 정점
	for (int k = 1; k <= V; k++) {
		// i = 출발 정점
		for (int i = 1; i <= V; i++) {
			// j = 도착 정점
			for (int j = 1; j <= V; j++) {
				if (map[i][j] > map[i][k] + map[k][j]) {
					map[i][j] = map[i][k] + map[k][j];
				}
			}
		}
	}
}

void output() {
	int result = INF;
	for (int i = 1; i <= V; i++)
		result = (result < map[i][i]) ? result : map[i][i];

	if (result == INF) {
		cout << "-1" << "\n";
	}
	else {
		cout << result << "\n";
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	solution();
	output();

	return 0 ;
}