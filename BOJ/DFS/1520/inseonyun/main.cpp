
//////////////////////////////////////////////////
// BAEKJOON: 1520_내리막 길
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int M, N;
int map[500][500] = { 0, };
bool visited[500][500] = { false, };
long long checked[500][500];
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
long long res = 0;

void input() {
	cin >> N >> M;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];
		}
	}
}

int dfs(int x, int y) {
	if (x == N - 1 && y == M - 1)
		return 1;
	if (visited[x][y])
		return checked[x][y];

	visited[x][y] = true;

	for (int i = 0; i < 4; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
			if (map[nx][ny] < map[x][y]) {
				checked[x][y] = checked[x][y] + dfs(nx, ny);
			}
		}
	}

	return checked[x][y];
}


void solution() {
	res = dfs(0, 0);
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