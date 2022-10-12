
//////////////////////////////////////////////////
// BAEKJOON: 2583_영역 구하기
//////////////////////////////////////////////////

#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

int map[100][100] = { 0, };
bool visited[100][100] = { false, };
vector<pair<int, int>> left_bottom;
vector<pair<int, int>> right_top;
int M, N, K;
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
int area = 0;
vector<int> area_cnt;
void input() {
	cin >> M >> N >> K;

	for (int i = 0; i < K; i++) {
		int leftX, leftY, rightX, rightY;
		cin >> leftX >> leftY >> rightX >> rightY;

		left_bottom.push_back({ leftX, leftY });
		right_top.push_back({ rightX, rightY });
	}
}

void setMap() {
	for (int i = 0; i < K; i++) {
		int leftX = left_bottom[i].first, leftY = left_bottom[i].second, rightX = right_top[i].first, rightY = right_top[i].second;

		for (int row = leftY; row < rightY; row++) {
			for (int col = leftX; col < rightX; col++) {
				map[row][col] = 1;
				visited[row][col] = true;
			}
		}
	}
}

int bfs(int x, int y) {
	int res_cnt = 0;
	queue<pair<int, int>> q;

	q.push({ x, y });
	visited[x][y] = true;

	while (!q.empty()) {
		int xx = q.front().first;
		int yy = q.front().second;

		q.pop();

		res_cnt++;

		for (int i = 0; i < 4; i++) {
			int nx = xx + dx[i];
			int ny = yy + dy[i];

			if (nx >= 0 && ny >= 0 && nx < M && ny < N) {
				if (visited[nx][ny] == false) {
					q.push({ nx, ny });
					visited[nx][ny] = true;
				}
			}
		}
	}

	return res_cnt;
}

void solution() {
	setMap();

	for (int i = 0; i < M; i++) {
		for (int j = 0; j < N; j++) {
			if (visited[i][j] == false) {
				area++;
				area_cnt.push_back(bfs(i, j));
			}
		}
	}

	sort(area_cnt.begin(), area_cnt.end());
}

void output() {
	cout << area << "\n";
	for (int i = 0; i < area; i++) {
		cout << area_cnt[i] << " ";
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