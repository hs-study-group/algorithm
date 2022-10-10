
//////////////////////////////////////////////////
// BAEKJOON: 14502_연구소
//////////////////////////////////////////////////

#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

int N, M;
int map[8][8];
int dx[] = { 0, 1, 0, -1 };
int dy[] = { 1, 0, -1, 0 };
int safezone = 0;
vector<pair<int, int>> virus;
queue<pair<int, int>> q;
int res = -1;

void input() {
	cin >> N >> M;
	for (int row = 0; row < N; row++) {
		for (int col = 0; col < M; col++) {
			cin >> map[row][col];

			if (map[row][col] == 2) {
				virus.push_back({ row, col });
				q.push({ row, col });
			}
			else if (map[row][col] == 0) {
				safezone++;
			}
		}
	}
}

bool checkComIsZero(vector<int> combi, int tmpmap[8][8]) {
	pair<int, int> first = {combi[0] / M, combi[0] % M};
	pair<int, int> second = { combi[1] / M, combi[1] % M };
	pair<int, int> third = { combi[2] / M, combi[2] % M };

	if (tmpmap[first.first][first.second] == 0 && tmpmap[second.first][second.second] == 0 && tmpmap[third.first][third.second] == 0) {
		tmpmap[first.first][first.second] = 1;
		tmpmap[second.first][second.second] = 1;
		tmpmap[third.first][third.second] = 1;
		return true;
	}
	return false;
}

void initQueue() {
	for (int i = 0; i < virus.size(); i++) {
		int x = virus[i].first;
		int y = virus[i].second;
		q.push({ x, y });
	}
}

void copyMap(int tmp[8][8]) {
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < M; j++) {
			tmp[i][j] = map[i][j];
		}
	}
}

int virusBFS(int bfsmap[8][8], int z) {
	int c = 0;
	while (!q.empty()) {
		int xx = q.front().first;
		int yy = q.front().second;
		q.pop();

		for (int i = 0; i < 4; i++) {
			int nx = xx + dx[i];
			int ny = yy + dy[i];

			if (nx >= 0 && ny >= 0 && nx < N && ny < M) {
				if (bfsmap[nx][ny] == 0) {
					bfsmap[nx][ny] = 2;
					c++;
					q.push({ nx, ny });
				}
			}
		}
	}
	return z - c - 3;
}

void comnination() {
	int r = 3;

	vector<int> arr;
	for (int i = 0; i <= N * M-1; i++) {
		arr.push_back(i);
	}
	vector<bool> temp(arr.size(), false);
	for (int i = 0; i < r; i++) // 앞부터 r개의 true가 채워진다. 나머지 뒤는 false.
		temp[i] = true;

	do {
		vector<int> tmp3;
		for (int i = 0; i < arr.size(); ++i) {
			if (temp[i]) {
				tmp3.push_back(arr[i]);
			}
		}
		int tmpmap[8][8] = { 0, };
		copyMap(tmpmap);
		
		if (checkComIsZero(tmp3, tmpmap)) {
			int tmpsafe = safezone;
			
			int safetmp = virusBFS(tmpmap, tmpsafe);

			if (safetmp > res)
				res = safetmp;
		}
		if (q.empty()) {
			initQueue();
		}
	} while (prev_permutation(temp.begin(), temp.end()));
}

void solution() {
	comnination();
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