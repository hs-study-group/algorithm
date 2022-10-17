
//////////////////////////////////////////////////
// BAEKJOON: 13460_구슬 탈출2
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int N, M;
char map[10][10];
bool visited[10][10][10][10] = { false, };
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, 1, 0, -1 };

struct coordinate {
	int x;
	int y;
};

coordinate red;
coordinate blue;
int res = -1;

void input() {
	cin >> N >> M;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];

			if (map[i][j] == 'R') {
				red.x = i;
				red.y = j;
			}
			else if (map[i][j] == 'B') {
				blue.x = i;
				blue.y = j;
			}
		}
	}
}

void bfs(int dir_x, int dir_y, coordinate &bead, int &cost) {
	while (true) {
		if (map[bead.x + dir_x][bead.y + dir_y] == '#' || map[bead.x][bead.y] == 'O') {
			return;
		}
		cost += 1;
		bead.x += dir_x;
		bead.y += dir_y;
	}
}

void solution() {

	queue<pair<int, coordinate>> red_q;
	queue<pair<int, coordinate>> blue_q;

	red_q.push({ 0, red });
	blue_q.push({ 0, blue });

	visited[red.x][red.y][blue.x][blue.y] = true;

	while (!red_q.empty() || !blue_q.empty()) {
		coordinate now_red = red_q.front().second;
		coordinate now_blue = blue_q.front().second;

		int now_red_cost = red_q.front().first;
		int now_blue_cost = blue_q.front().first;

		red_q.pop();
		blue_q.pop();

		if (now_red_cost >= 10 || now_blue_cost >= 10) {
			res = -1;
			return;
		}

		for (int i = 0; i < 4; i++) {
			coordinate next_red = now_red;
			coordinate next_blue = now_blue;

			int next_red_cost = 0;
			int next_blue_cost = 0;
			bfs(dx[i], dy[i], next_red, next_red_cost);
			bfs(dx[i], dy[i], next_blue, next_blue_cost);

			if (map[next_blue.x][next_blue.y] == 'O')
				continue;
			
			if (map[next_red.x][next_red.y] == 'O') {
				res = now_red_cost + 1;
				return;
			}

			//  서로 같은 위치에 있다면 cost가 높은거(더 많이 이동한게 원래 뒤에 있던 것)
			if (next_red.x == next_blue.x && next_red.y == next_blue.y) {
				if (next_red_cost > next_blue_cost) {
					next_red.x -= dx[i];
					next_red.y -= dy[i];
				}else{
					next_blue.x -= dx[i];
					next_blue.y -= dy[i];
				}
			}

			if (visited[next_red.x][next_red.y][next_blue.x][next_blue.y])
				continue;

			visited[next_red.x][next_red.y][next_blue.x][next_blue.y] = true;
			red_q.push({ now_red_cost + 1, next_red });
			blue_q.push({ now_blue_cost + 1, next_blue });
		}
	}
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