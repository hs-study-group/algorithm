
//////////////////////////////////////////////////
// BAEKJOON_13549번 : 숨바꼭질3
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

#define INF 987654321

int N, K;
int map[100000];
int dx[] = { -1, 1 };
void input() {
	cin >> N >> K;

	// 초기화
	for (int i = 0; i < 100002; i++) {
		map[i] = INF;
	}
}

void solution() {
	priority_queue<pair<int, int>> pq;

	pq.push({ 0, N });
	map[N] = 0;

	while (!pq.empty()) {
		int sec = -pq.top().first;
		int xx = pq.top().second;

		pq.pop();

		if (sec > map[xx])
			continue;

		if (xx * 2 >= 0 && xx * 2 <= 100000 && map[xx * 2] > sec) {
			map[xx * 2] = sec;
			pq.push({ -sec, xx * 2 });
		}

		for (int i = 0; i < 2; i++) {
			int nx = xx + dx[i];
			int ns = sec + 1;
			if (nx >= 0 && nx <= 100000) {
				if (map[nx] > ns) {
					map[nx] = ns;
					pq.push({ -ns, nx });
				}
			}
		}
	}
}

void output() {
	cout << map[K];
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