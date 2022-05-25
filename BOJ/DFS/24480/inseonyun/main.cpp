
//////////////////////////////////////////////////
// BAEKJOON_24480번: 알고리즘 수업 - 깊이 우선 탐색 2
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>

using namespace std;

int N, M, R;
vector<vector<int>> v(100001, vector<int>());
vector<int> result(100001, 0);
bool check[100001];
int cnt = 1;
void input() {
	// 정점의 개수, 간선의 개수, 시작 정점 입력 받음
	cin >> N >> M >> R;

	// check 배열 초기화
	memset(check, false, sizeof(check));

	for (int i = 0; i < M; i++) {
		int a, b;
		cin >> a >> b;

		v[a].push_back(b);
		v[b].push_back(a);
	}
}

void sortingDesc() {
	// v 배열 각 행 내림차순 정렬
	for (int i = 1; i <= N; i++) {
		sort(v[i].begin(), v[i].end(), greater<>());
	}
}

void DFS(int startX) {
	check[startX] = true;
	result[startX] = cnt;
	for (int i = 0; i < v[startX].size(); i++) {
		if (check[v[startX][i]] == false) {
			cnt++;
			DFS(v[startX][i]);
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
	sortingDesc();
	DFS(R);
	output();

	return 0;
}