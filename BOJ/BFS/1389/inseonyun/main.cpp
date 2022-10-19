
//////////////////////////////////////////////////
// BAEKJOON: 1389_케빈 베이컨의 6단계 법칙
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>
#include <cstring>
#include <algorithm>

using namespace std;

int N, M;
vector<int> v[101];
bool visited[101] = { false, };

struct result {
	int cost;
	int idx;
};
vector<result> res;

void input() {
	cin >> N >> M;

	for (int i = 0; i < M; i++) {
		int a, b;
		cin >> a >> b;

		v[a].push_back(b);
		v[b].push_back(a);
	}
}

int bfs(int now) {
	vector<int> row[101];

	visited[now] = true;
	queue<pair<int, int>> q;
	q.push({ now, 0 });

	while (!q.empty()) {
		int cur = q.front().first;
		int cost = q.front().second;

		q.pop();

		row[cur].push_back(cost);

		for (int i = 0; i < v[cur].size(); i++) {
			int next_cur = v[cur][i];

			if (!visited[next_cur]) {
				visited[next_cur] = true;
				q.push({ next_cur, cost + 1 });
			}
		}
	}

	for (int i = 1; i <= N; i++) {
		sort(row[i].begin(), row[i].end());
	}

	int now_cost = 0;
	for (int i = 1; i <= N; i++) {
		if (i != now)
			now_cost += row[i][0];

	}

	return now_cost;
}

void solution() {

	for (int i = 1; i <= N; i++) {
		memset(visited, false, sizeof(visited));
		int now_node_cnt = bfs(i);

		res.push_back({ now_node_cnt, i });
	}
}

bool cmp(result a, result b) {
	
	if (a.cost == b.cost)
		return a.idx < b.idx;

	return a.cost < b.cost;
}

void output() {
	sort(res.begin(), res.end(), cmp);
	
	cout << res[0].idx;
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