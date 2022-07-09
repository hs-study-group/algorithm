
//////////////////////////////////////////////////
// BAEKJOON_11404번 : 플로이드
//////////////////////////////////////////////////

#include <iostream>

#define INF 987654321

using namespace std;

int N, M;
int map[102][102];
void input() {
	cin >> N >> M;

	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			map[i][j] = INF;
		}
	}

	for (int i = 0; i < M; i++) {
		int curA, curB, cost;
		cin >> curA >> curB >> cost;

		if(map[curA][curB] > cost)
			map[curA][curB] = cost;
	}
}

void solution() {
	// k = 거쳐가는 정점
	for (int k = 1; k <= N; k++) {
		// i = 출발 정점
		for (int i = 1; i <= N; i++) {
			// j = 도착 정점
			for (int j = 1; j <= N; j++) {
				if (i == j)
					continue;
				if (map[i][j] > map[i][k] + map[k][j]) {
					map[i][j] = map[i][k] + map[k][j];
				}
			} 
		}
	}
}

void output() {
	for (int i = 1; i <= N; i++) {
		for (int j = 1; j <= N; j++) {
			if (map[i][j] == INF)
				cout << "0";
			else
				cout << map[i][j];
			if (j != N)
				cout << " ";
		}
		if (i != N)
			cout << "\n";
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