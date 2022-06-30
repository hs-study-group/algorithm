
//////////////////////////////////////////////////
// BAEKJOON_14503번: 로봇청소기
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

int N, M;
int startX, startY, now_direction;
int map[50][50];
bool checkMap[50][50];
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, 1, 0, -1 };
int iterateCase = 0;
/*
* 청소 룰
* 1. 현재 위치 청소
* 2. a. 현재 위치의 바로 왼쪽에 아직 청소하지 않은 빈 공간이 존재한다면, 왼쪽 방향 한 칸을 전진하고 1번으로 돌아간다.
        그렇지 않을 경우, 왼쪽 방향으로 회전한다. 이때, 왼쪽은 현재 바라보는 방향을 기준으로 한다.
	 b. 1번으로 돌아가거나 후진하지 않고 2-a번 단계가 연속으로 네 번 실행되었을 경우, 바로 뒤쪽이 벽이라면 작동을 멈추고 벽이 아니라면 한 칸 후진한다.
*/

void input() {
	cin >> N >> M;

	// row, col, direction
	// now_direction : 0 (북), 1(동), 2(남), 3(서)
	cin >> startX >> startY >> now_direction;

	// 장소 상태 : 0은 빈칸, 1은 벽
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cin >> map[i][j];
		}
	}
}

void display() {
	cout << "--------------------\n";
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			cout << checkMap[i][j] << " ";
		}
		cout << "\n";
	}
	cout << "--------------------\n";
}

void BFS() {
	queue<pair<int, int>> q;

	q.push({ startX, startY });

	while (!q.empty()) {
		int xx = q.front().first;
		int yy = q.front().second;

		checkMap[xx][yy] = true;

		q.pop();
		cout << "now dir :" << now_direction << "\n";
		display();

		// 청소 조건 2-a 수행
		for (int i = 0; i < 4; i++) {
			now_direction = (now_direction + 3) % 4;

			int nx = xx + dx[now_direction];
			int ny = yy + dy[now_direction];

			if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
				if (checkMap[nx][ny] == false && map[nx][ny] == 0) {
					iterateCase = 0;
					q.push({ nx, ny });
					break;
				}
				iterateCase++;
			}
		}

		// 청소 조건 2-b 수행
		if (iterateCase == 4) {
			int nx = xx + dx[(now_direction + 2) % 4];
			int ny = yy + dy[(now_direction + 2) % 4];

			if (map[nx][ny] == 1)
				break;
			else {
				iterateCase = 0;
				q.push({ nx, ny });
			}
			continue;
		}
	}
}

void solution() {
	memset(checkMap, false, sizeof(checkMap));
	BFS();
}

void output() {
	int sum = 0;
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			if (checkMap[i][j])
				sum += 1;
		}
	}
	
	cout << sum;
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