
//////////////////////////////////////////////////
// BAEKJOON: 13459_구슬 탈출
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

char map[10][10];
bool visited[10][10][10][10] = { false, };
int N, M, res = 0;
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, 1, 0, -1 };
pair<int, int> red;
pair<int, int> blue;
void input() {
	cin >> N >> M;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];

			if (map[i][j] == 'R') {
				red.first = i;
				red.second = j;
			}
			else if (map[i][j] == 'B') {
				blue.first = i;
				blue.second = j;
			}
		}
	}
}

void bfs(int dir_x, int dir_y, int& x, int& y, int& cost) {

	while (map[x + dir_x][y + dir_y] != '#' && map[x][y] != 'O') {
		cost += 1;
		x += dir_x;
		y += dir_y;
	}
}

void solution() {

	queue<pair<int, pair<int, int>>> red_q;
	queue<pair<int, pair<int, int>>> blue_q;

	red_q.push({ 0, { red.first, red.second } });
	blue_q.push({ 0, { blue.first, blue.second } });

	visited[red.first][red.second][blue.first][blue.second] = true;

	while (!red_q.empty() || !blue_q.empty()) {
		int red_x = red_q.front().second.first;
		int red_y = red_q.front().second.second;

		int blue_x = blue_q.front().second.first;
		int blue_y = blue_q.front().second.second;

		int red_cost = red_q.front().first;
		int blue_cost = blue_q.front().first;

		red_q.pop();
		blue_q.pop();

		if (red_cost >= 10 || blue_cost >= 10) {
			break;
		}

		for (int i = 0; i < 4; i++) {
			int next_red_x = red_x;
			int next_red_y = red_y;

			int next_blue_x = blue_x;
			int next_blue_y = blue_y;

			int next_red_cost = 0;
			int next_blue_cost = 0;

			bfs(dx[i], dy[i], next_red_x, next_red_y, next_red_cost);
			bfs(dx[i], dy[i], next_blue_x, next_blue_y, next_blue_cost);

			if (map[next_blue_x][next_blue_y] == 'O') {
				continue;
			}
			if (map[next_red_x][next_red_y] == 'O') {
				res = 1;
				return;
			}

			if (next_red_x == next_blue_x && next_red_y == next_blue_y) {
				if (next_red_cost > next_blue_cost) {
					next_red_x -= dx[i];
					next_red_y -= dy[i];
				}
				else {
					next_blue_x -= dx[i];
					next_blue_y -= dy[i];
				}
			}

			if (visited[next_red_x][next_red_y][next_blue_x][next_blue_y])
				continue;

			visited[next_red_x][next_red_y][next_blue_x][next_blue_y] = true;

			red_q.push({ red_cost + 1, { next_red_x, next_red_y } });
			blue_q.push({ blue_cost + 1, { next_blue_x, next_blue_y } });

		}
	}
	res = 0;
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