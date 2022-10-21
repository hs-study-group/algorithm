
//////////////////////////////////////////////////
// BAEKJOON: 16953_A->B
//////////////////////////////////////////////////

#include <iostream>
#include <queue>

using namespace std;

#define MAX 987654321

long long A, B;
long long minimum = MAX;

void input() {
	cin >> A >> B;
}

void solution() {
	queue<pair<int, int>> q;

	q.push({ A, 0 });

	while (!q.empty()) {
		long long now_num = q.front().first;
		long long now_cost = q.front().second;

		q.pop();

		if (now_num == B) {
			if (minimum > now_cost)
				minimum = now_cost;
		}

		if (now_num * 10 + 1 <= B) {
			q.push({ now_num * 10 + 1, now_cost + 1 });
		}
		if (now_num * 2 <= B) {
			q.push({ now_num * 2, now_cost + 1 });
		}
	}

}

void output() {
	if (minimum == MAX)
		cout << -1;
	else
		cout << minimum + 1;
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