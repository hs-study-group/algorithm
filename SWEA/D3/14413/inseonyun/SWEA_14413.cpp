
//////////////////////////////////////////////////
// SWEA : 14413_������ ĥ�ϱ�
//////////////////////////////////////////////////

#include <iostream>
#include <cstring>
#include <queue>

using namespace std;

int N, M;
char map[50][50];
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
string answer = "possible";

void dfs(int x, int y) {
	for (int i = 0; i < 4; ++i) {
		int nx = x + dx[i];
		int ny = y + dy[i];

		if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
			if (map[x][y] != '?' && map[x][y] == map[nx][ny]) {
				answer = "impossible";
				return;
			}

			if (map[nx][ny] == '?') {
				if (map[x][y] == '#') {
					map[nx][ny] = '.';
					dfs(nx, ny);
				}
				else if (map[x][y] == '.') {
					map[nx][ny] = '#';
					dfs(nx, ny);
				}
			}
		}
	}
}

bool checkMap() {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			// ���� ��ġ�� ����ǥ�� �ƴϿ��� ��
			if (map[i][j] != '?') {
				for (int k = 0; k < 4; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];

					if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
						if (map[nx][ny] == '?') {
							// ����ǥ�� �Ѿ
							continue;
						}
						if (map[nx][ny] == map[i][j]) {
							// ���� ���ٸ� �ٷ� �����ϵ��� ��.
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}

int main() {

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		cin >> N >> M;

		bool res = true;
		queue<pair<int, int>> q;

		// input
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				cin >> map[i][j];

				if (map[i][j] == '?')
					q.push({ i, j });
			}
		}

		// solution
		while (!q.empty()) {
			int xx = q.front().first;
			int yy = q.front().second;

			q.pop();

			char ch = 'A'; // �ʱⰪ ����
			for (int i = 0; i < 4; i++) {
				int nx = xx + dx[i];
				int ny = yy + dy[i];

				if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
					if (ch == 'A') {
						// �ʱⰪ�̶��, �� ���� �ش� ĭ�� �� �־���
						if (map[nx][ny] == '?') {
							//����ǥ�� continue
							continue;
						}
						else {
							ch = map[nx][ny];
						}
					}
					else {
						if (map[nx][ny] != '?') {
							if (ch != map[nx][ny]) {
								// ch�� ���� ���� ���� �ٸ� -> �Ұ�������
								res = false;
								break;
							}
						}
					}
				}
			}

			if (!res)
				break;

			// �ٸ� ���� ĥ�� �� �����Ƿ�, �ݴ�Ǵ� ���� ĥ����
			if (ch == '#')
				map[xx][yy] = '.';
			else if(ch == '.')
				map[xx][yy] = '#';
		}


		/*
		DFS Ǯ��
		for (int i = 0; i < N; i++) {
			if (answer == "impossible") {
				break;
			}
			for (int j = 0; j < M; j++) {
				if (answer == "impossible") {
					break;
				}
				dfs(i, j);
			}
		}

		cout << '#' << test_case << ' ' << answer << '\n';
		answer = "possible";
		*/

		if (!res)
			cout << "#" << test_case << " impossible\n";
		else {
			res = checkMap();

			if (!res)
				cout << "#" << test_case << " impossible\n";
			else
				cout << "#" << test_case << " possible\n";
		}

	}

	return 0;
}