
//////////////////////////////////////////////////
// BAEKJOON: 10026_적록색약
//////////////////////////////////////////////////

#include <iostream>
#include <queue>
#include <vector>

using namespace std;

char normal_map[100][100];
char blindness_map[100][100];
bool visited[100][100] = { false, };
bool visited2[100][100] = { false, };
int N;
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
int normal_area = 0;
int blindness_area = 0;
void input() {
	cin >> N;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> normal_map[i][j];

			if (normal_map[i][j] == 'R' || normal_map[i][j] == 'G') {
				blindness_map[i][j] = 'N';
			}
			else {
				blindness_map[i][j] = normal_map[i][j];
			}
		}
	}
}

void bfs(int i, int j, char key, char tmpmap[100][100], bool checkd[100][100]) {
	checkd[i][j] = true;

	queue<pair<int, int>> q;

	q.push({ i, j });

	while (!q.empty()) {
		int xx = q.front().first;
		int yy = q.front().second;

		q.pop();

		for (int i = 0; i < 4; i++) {
			int nx = xx + dx[i];
			int ny = yy + dy[i];

			if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
				if (!checkd[nx][ny] && tmpmap[nx][ny] == key) {
					q.push({ nx, ny });
					checkd[nx][ny] = true;
				}
			}
		}
	}
}

void solution() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			if (!visited[i][j]) {
				normal_area++;
				bfs(i, j, normal_map[i][j], normal_map, visited);
			}
			if (!visited2[i][j]) {
				blindness_area++;
				bfs(i, j, blindness_map[i][j], blindness_map, visited2);
			}
		}
	}
}

void output() {
	cout << normal_area << " " << blindness_area;
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