
//////////////////////////////////////////////////
// BAEKJOON_16928번: 뱀과 사다리 게임
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int N, M;
int map[101];
int dist[101];
void input() {
	cin >> N >> M;
	for (int i = 0; i < sizeof(map)/sizeof(int); i++) {
		map[i] = i;
		dist[i] = -1;
	}

	// 사다리, 뱀 정보 입력 받음
	for (int i = 0; i < N + M; i++) {
		int a, b;
		cin >> a >> b;

		map[a] = b;
	}
}

void BFS(int startX) {
	queue<int> q;
	q.push(startX);
	dist[startX] = 0;
	while (!q.empty()) {
		int xx = q.front();

		q.pop();

		for (int i = 1; i <= 6; i++) {
			int nx = xx + i;
			if (nx > 100) {
				return;
			}
			nx = map[nx];
			if (dist[nx] == -1) {
				dist[nx] = dist[xx] + 1;
				q.push(nx);
			}
		}

	}
}

void output() {
	cout << dist[100];
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	BFS(1);
	output();

	return 0;
}