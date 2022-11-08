
//////////////////////////////////////////////////
// SWEA: 15612_체스판 위의 룩 배치
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

char map[8][8];
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, 1, 0, -1 };
bool checkRook = true;

void dfs(pair<int, int> coordi, int dir) {
	int nx = coordi.first + dx[dir];
	int ny = coordi.second + dy[dir];

	if (nx >= 0 && ny >= 0 && nx < 8 && ny < 8) {
		if (map[nx][ny] == 'O')
			checkRook = true;
		else {
			dfs({ nx, ny }, dir);
		}
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		checkRook = true;
		queue<pair<int, int>> q;
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				cin >> map[row][col];

				if (map[row][col] == 'O') {
					q.push({ row, col });
				}
			}
		}

		if (q.size() == 8) {
			checkRook = false;
			while (!q.empty()) {
				for (int i = 0; i < 4; i++) {
					dfs(q.front(), i);
				}
				q.pop();

				if (checkRook)
					break;
			}
		}
		
		if (checkRook)
			cout << "#" << test_case << " no\n";
		else
			cout << "#" << test_case << " yes\n";
	}

	return 0;
}