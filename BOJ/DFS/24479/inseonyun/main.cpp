
//////////////////////////////////////////////////
// BAEKJOON_24479번: 알고리즘 수업 - 깊이 우선 탐색 1
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <algorithm>
#include <cstring>

using namespace std;

int N, M, R;
vector<vector<int>> v(100001, vector<int>());
vector<int> result(100001, 0);
bool checkedNode[100001];
int k = 1;
void input() {
	cin >> N >> M >> R;

	memset(checkedNode, false, sizeof(checkedNode));

	for (int i = 0; i < M; i++) {
		int a, b;
		cin >> a >> b;

		v[a].push_back(b);
		v[b].push_back(a);
	}
	
	for (int i = 1; i <= N; i++) {
		sort(v[i].begin(), v[i].end());
	}
}

void DFS(int start_idx) {
	checkedNode[start_idx] = true;
	result[start_idx] = k;
	for (int i = 0; i < v[start_idx].size(); i++) {
		if (checkedNode[v[start_idx][i]] == false) {
			k++;
			DFS(v[start_idx][i]);
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
	DFS(R);
	output();

	return 0;
}