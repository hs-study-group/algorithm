
//////////////////////////////////////////////////
// BAEKJOON: 3055_탈출
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

int R, C;
char map[50][50];
bool visited[50][50] = { false, };
int checked[50][50] = { 0, };
int startX, startY;
queue<pair<int, int>> water_q;
int endX, endY;

int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };

bool flag = false;

void input() {
	cin >> R >> C;

	for (int i = 0; i < R; i++) {
		for (int j = 0; j < C; j++) {
			cin >> map[i][j];

			if (map[i][j] == 'S') {
				startX = i;
				startY = j;
			}
			else if (map[i][j] == '*') {
				water_q.push({ i, j });
			}
			else if (map[i][j] == 'D') {
				endX = i;
				endY = j;
			}
		}
	}
}


void bfs() {
	visited[startX][startY] = true;

	queue<pair<int, int>> hadge_q;

	hadge_q.push({ startX, startY });
	int date_time = -1;
	while (!hadge_q.empty()) {
		if (flag)
			return;

		// 물 전이 시키고 시작함
		int water_size = water_q.size();

		for (int i = 0; i < water_size; i++) {
			int w_x = water_q.front().first;
			int w_y = water_q.front().second;

			water_q.pop();

			for (int j = 0; j < 4; j++) {
				int next_w_x = w_x + dx[j];
				int next_w_y = w_y + dy[j];

				if (next_w_x >= 0 && next_w_y >= 0 && next_w_x < R && next_w_y < C) {
					if (map[next_w_x][next_w_y] == '.') {
						water_q.push({ next_w_x , next_w_y });
						map[next_w_x][next_w_y] = '*';
					}
				}
			}
		}

		int hadge_size = hadge_q.size();
		for (int i = 0; i < hadge_size; i++) {
			int h_x = hadge_q.front().first;
			int h_y = hadge_q.front().second;

			int now_cost = checked[h_x][h_y];

			hadge_q.pop();

			for (int i = 0; i < 4; i++) {
				int next_h_x = h_x + dx[i];
				int next_h_y = h_y + dy[i];

				if (next_h_x >= 0 && next_h_y >= 0 && next_h_x < R && next_h_y < C) {
					if (!visited[next_h_x][next_h_y] || checked[next_h_x][next_h_y] > now_cost + 1) {
						if (map[next_h_x][next_h_y] == '.') {
							visited[next_h_x][next_h_y] = true;
							checked[next_h_x][next_h_y] = now_cost + 1;
							hadge_q.push({ next_h_x , next_h_y });
						}
						else if (map[next_h_x][next_h_y] == 'D') {
							checked[next_h_x][next_h_y] = now_cost + 1;
							flag = true;
						}
					}
				}
			}
			
		}
	}

}

void solution() {
	bfs();
}

void output() {
	if (flag)
		cout << checked[endX][endY];
	else
		cout << "KAKTUS";
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