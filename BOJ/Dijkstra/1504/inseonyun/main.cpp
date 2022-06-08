
//////////////////////////////////////////////////
// BAEKJOON_1504번 : 특정한 최단 경로
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

#define INF 987654321

vector<pair<int, int>> map[801];
int dist[801] = { 0, };
int N, E;
int v1, v2;

void initializeDist() {
	for (int i = 0; i < 801; i++) {
		dist[i] = INF;
	}
}

void input() {
	cin >> N >> E;

	for (int i = 0; i < E; i++) {
		int a, b, c;
		cin >> a >> b >> c;
		
		map[a].push_back({ b, c });
		map[b].push_back({ a, c });
	}

	cin >> v1 >> v2;

	
}

void solution(int startX) {
	priority_queue<pair<int, int>> pq;
	pq.push({ 0, startX });
	dist[startX] = 0;

	while (!pq.empty()) {
		int cost = -pq.top().first;
		int cur = pq.top().second;

		pq.pop();

		for (int i = 0; i < map[cur].size(); i++) {
			int next = map[cur][i].first;
			int next_cost = map[cur][i].second;

			if (dist[next] > cost + next_cost) {
				dist[next] = cost + next_cost;
				pq.push({ -dist[next], next });
			}
		}
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();

	initializeDist();
	solution(1);
	int startToV1 = dist[v1];
	int startToV2 = dist[v2];

	initializeDist();
	solution(v1);
	int V1ToV2 = dist[v2];
	int V1ToN = dist[N];

	initializeDist();
	solution(v2);
	int V2ToN = dist[N];

	int res = INF;
	res = min(res, startToV1 + V1ToV2 + V2ToN);
	res = min(res, startToV2 + V1ToV2 + V1ToN);

	if (res >= INF || V1ToV2 >= INF) {
		cout << -1;
	}
	else {
		cout << res;
	}

	return 0;
}