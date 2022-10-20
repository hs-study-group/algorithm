
////////////////////////////////////////////////////
//// BAEKJOON: 16234_인구 이동
////////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <cmath>

using namespace std;

// N : 맵 크기, 국경선을 여는 L: 최소 범위, R: 최대 범위 --> L <= 인구 <= R
int N, L, R;
int map[50][50] = { 0, };
bool visited[50][50] = { false, };
vector<pair<int, int>> v;
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
int today = 0; 
bool flag = true;
int sum = 0;

void input() {
	cin >> N >> L >> R;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> map[i][j];
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

		for (int i = 0; i < 4; i++) {
			int nx = xx + dx[i];
			int ny = yy + dy[i];

			if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
				if (!visited[nx][ny]) {
					int abs_value = abs(map[nx][ny] - map[xx][yy]);

					if (abs_value >= L && abs_value <= R) {
						sum += map[nx][ny];
						visited[nx][ny] = true;
						v.push_back({ nx, ny });
						q.push({ nx, ny });
					}
				}
			}
		}
	}
}

void solution() {

	while (flag) {
		flag = false;

		memset(visited, false, sizeof(visited));

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
					v.clear();
					v.push_back({ i, j });
					sum = map[i][j];
					bfs(i, j);
				}

				if (v.size() >= 2) {
					flag = true;

					for (int k = 0; k < v.size(); k++) {
						map[v[k].first][v[k].second] = sum / v.size();
					}
				}
			}
		}
		if (flag)
			today++;
	}
}

void output() {
	cout << today;
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