
//////////////////////////////////////////////////
// BAEKJOON_2573번: 빙산
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

int N, M;
int map[300][300];
int copymap[300][300];
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, 1, 0, -1 };
int iceberg = 0;
int year = 0;
int checkMap[300][300];
void copyArr(int paste_arr[300][300], int copy_arr[300][300]) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			paste_arr[i][j] = copy_arr[i][j];
		}
	}
}

void input() {
	cin >> N >> M;

	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];
		}
	}
	copyArr(copymap, map);
}

void display(int arr[300][300]) {
	cout << "------------------\n";
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++)
			cout << arr[i][j] << " ";
		cout << "\n";
	}
	cout << "------------------\n";
}

void calc(int x, int y) {
	for (int i = 0; i < 4; i++) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
			if (map[nx][ny] == 0) {
				if (copymap[x][y] > 0)
					copymap[x][y] -= 1;
			}
		}
	}
}

void DFS(int startX, int startY) {
	if (checkMap[startX][startY] > 0) {
		checkMap[startX][startY] = 0;

		for (int i = 0; i < 4; i++) {
			int nx = startX + dx[i];
			int ny = startY + dy[i];

			if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
				DFS(nx, ny);
			}
		}
	}
}

void searcharr() {
	copyArr(checkMap, map);
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (checkMap[i][j] > 0) {
				iceberg++;
				DFS(i, j);
			}
		}
	}
}

bool isAllZero() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (map[i][j] > 0)
				return false;
		}
	}
	return true;
}

void solution() {

	while (true) {
		year++;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] > 0) {
					calc(i, j);
					//display(copymap);
				}
			}
		}
		copyArr(map, copymap);
		searcharr();

		if (iceberg >= 2)
			break;
		else {
			if (isAllZero()) {
				if (iceberg < 2)
					year = 0;
				break;
			}
			iceberg = 0;
			continue;
		}
	}
}

void output() {
	cout << year;
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