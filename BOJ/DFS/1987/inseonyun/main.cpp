
//////////////////////////////////////////////////
// BAEKJOON: 1987_알파벳
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int R, C;
char map[20][20];
bool visited[26] = { false, };
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };

int res = 0;

void input() {
	cin >> R >> C;

	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			cin >> map[i][j];
		}
	}
}

void dfs(int x, int y, int cost) {
	if (res < cost)
		res = cost;

	for (int i = 0; i < 4; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && ny >= 0 && nx < R && ny < C) {
			if (!visited[map[nx][ny] - 'A']) {
				visited[map[nx][ny] - 'A'] = true;
				dfs(nx, ny, cost + 1);
				// 백트래킹
				visited[map[nx][ny] - 'A'] = false;
			}
		}
	}
}

void solution() {
	visited[map[0][0] - 'A'] = true;
	dfs(0, 0, 1);
}

void output() {
	cout << res;
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