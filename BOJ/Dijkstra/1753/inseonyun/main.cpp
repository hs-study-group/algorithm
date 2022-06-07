
//////////////////////////////////////////////////
// BAEKJOON_1753번 : 최단 경로
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define INF 987654321

int V, E, startX;
int dist[20010] = { 0, };
vector<pair<int, int>> vec[20010];

void input() {
	cin >> V >> E;
	cin >> startX;
	
	for (int i = 1; i <= E; i++) {
		int a, b, c;
		cin >> a >> b >> c;

		vec[a].push_back({ b, c });
	}
	for (int i = 1; i <= V; i++) {
		dist[i] = INF;
	}
}

void solution() {
	priority_queue<pair<int, int>> pq;
	pq.push({ 0, startX });
	dist[startX] = 0;
	while (!pq.empty()) {
		int cost = -pq.top().first;
		int cur = pq.top().second;

		pq.pop();

		for (int i = 0; i < vec[cur].size(); i++) {
			int next = vec[cur][i].first;
			int next_cost = vec[cur][i].second;

			if (dist[next] > cost + next_cost) {
				dist[next] = cost + next_cost;
				pq.push({ -dist[next], next });
			}
		}
	}
}

void output() {
	for (int i = 1; i <= V; i++) {
		if (dist[i] == INF)
			cout << "INF\n";
		else
			cout << dist[i] << "\n";
	}
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