
////////////////////////////////////////////////////
//// BAEKJOON: 1926_±×¸²
////////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int N, M;
int picture[500][500];
int checked[500][500] = { false, };
int dx[] = { 0, -1, 0, 1 };
int dy[] = { -1, 0, 1, 0 };
int resultCount = 0;
int resultArea = 0;

void input() {
	cin >> N >> M;

	for (int row = 0; row < N; row++) {
		for (int col = 0; col < M; col++) {
			cin >> picture[row][col];
		}
	}
}

int bfs(int x, int y) {
	int nowArea = 1;

	checked[x][y] = true;

	queue<pair<int, int>> route;

	route.push({ x, y });

	while (!route.empty()) {
		int xx = route.front().first;
		int yy = route.front().second;

		route.pop();

		for (int dir = 0; dir < 4; dir++) {
			int nx = xx + dx[dir];
			int ny = yy + dy[dir];

			if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
				if (picture[nx][ny] == 1 && !checked[nx][ny]) {
					checked[nx][ny] = true;
					route.push({ nx, ny });
					nowArea++;
				}
			}
		}
	}

	return nowArea;
}

void solve() {
	for (int row = 0; row < N; row++) {
		for (int col = 0; col < M; col++) {
			if (!checked[row][col] && picture[row][col] == 1) {
				resultCount++;
				int nowArea = bfs(row, col);
				if (nowArea > resultArea)
					resultArea = nowArea;
			}
		}
	}
}

void output() {
	cout << resultCount << "\n" << resultArea;
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);

	input();
	solve();
	output();

	return 0;
}