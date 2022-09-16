
//////////////////////////////////////////////////
// BAEKJOON: 15649_N과 M (1)
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

int N, M;
int arr[9];
bool visited[9];
void input() {
	cin >> N >> M;
}

void solution(int now) {
	if (now == M) {
		for (int i = 0; i < M; i++) {
			cout << arr[i] << " ";
			cout << "\n";
		}
	}
	else {
		for (int i = 1; i <= N; i++) {
			if (!visited[i]) {
				visited[i] = true;
				arr[i] = i;
				solution(now + 1);
				visited[i] = false;
			}
		}
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	solution(0);

	return 0;
}