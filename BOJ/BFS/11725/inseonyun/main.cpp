
//////////////////////////////////////////////////
// BAEKJOON: 11725_트리의 부모 찾기
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

vector<int> v[100001];
int arr[100001];
int N;
bool visited[100001] = { false, };
const int left = 0, parent = 1, right = 2;
void input() {
	cin >> N;

	for (int i = 0; i < N - 1; i++) {
		int a, b;
		cin >> a >> b;

		v[a].push_back(b);
		v[b].push_back(a);
	}
}

void solution() {
	visited[1] = true;
	queue<int> q;
	q.push(1);
	while (!q.empty()) {
		int x = q.front();

		q.pop();

		for (int i = 0; i < v[x].size(); i++) {
			int next = v[x][i];

			if (!visited[next]) {
				q.push(next);
				visited[next] = true;
				arr[next] = x;
			}
		}
	}
}

void output() {
	for (int i = 2; i <= N; i++) {
		cout << arr[i] << "\n";
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