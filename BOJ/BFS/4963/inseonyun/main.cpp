
//////////////////////////////////////////////////
// BAEKJOON: 4963_¼¶ÀÇ °³¼ö
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int w = -1, h = -1;
int map[50][50] = { 0, };
bool visited[50][50] = { false, };
int dx[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
int dy[] = { 1, 1, 0, -1, -1, -1, 0, 1 };
vector<int> res;
void input() {
	cin >> w >> h;

	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; j++) {
			cin >> map[i][j];
			visited[i][j] = false;
		}
	}
}

void bfs(int x, int y) {
	visited[x][y] = true;
	queue<pair<int, int>> q;
	q.push({ x, y });

	while (!q.empty()) {
		int xx = q.front().first;
		int yy = q.front().second;

		q.pop();

		for (int i = 0; i < 8; i++) {
			int nx = xx + dx[i];
			int ny = yy + dy[i];

			if (nx >= 0 && ny >= 0 && nx < h && ny < w) {
				if (!visited[nx][ny] && map[nx][ny] == 1) {
					visited[nx][ny] = true;
					q.push({ nx, ny });
				}
			}
		}
	}
}

void solution() {
	int island_cnt = 0;
	for (int i = 0; i < h; i++) {
		for (int j = 0; j < w; j++) {
			if (!visited[i][j] && map[i][j] == 1) {
				island_cnt++;
				bfs(i, j);
			}
		}
	}
	res.push_back(island_cnt);
}

void output() {
	for (int i = 0; i < res.size(); i++) {
		cout << res[i] << "\n";
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	while (true) {
		input();
		if (w != 0 && h != 0)
			solution();
		else break;
	}
	output();

	return 0;
}