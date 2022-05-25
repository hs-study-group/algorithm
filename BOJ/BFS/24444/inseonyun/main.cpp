
//////////////////////////////////////////////////
// BAEKJOON_24444번: 알고리즘 수업 - 너비 우선 탐색 1
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <algorithm>

using namespace std;

int N, M, R;
vector<vector<int>> v(100001, vector<int>());
vector<int> result(100001, 0);
bool check[100001];
void input() {
	cin >> N >> M >> R;

	memset(check, false, sizeof(check));

	for (int i = 0; i < M; i++) {
		int a, b;
		cin >> a >> b;

		v[a].push_back(b);
		v[b].push_back(a);
	}
}

void sortingAsc() {
	for (int i = 1; i <= N; i++) {
		sort(v[i].begin(), v[i].end());
	}
}

void BFS(int startX) {
	check[startX] = true;
	queue<int> q;

	q.push(startX);

	int cnt = 1;

	while (!q.empty()) {
		int xx = q.front();

		result[xx] = cnt;
		cnt++;

		q.pop();

		for (int i = 0; i < v[xx].size(); i++) {
			int nx = v[xx][i];
			if (check[nx] == false) {
				check[nx] = true;
				q.push(nx);
			}
		}
	}
}

void output() {
	for (int i = 1; i <= N; i++) {
		cout << result[i] << "\n";
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	sortingAsc();
	BFS(R);
	output();

	return 0;
}