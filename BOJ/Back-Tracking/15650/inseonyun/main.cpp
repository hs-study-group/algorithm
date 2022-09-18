
//////////////////////////////////////////////////
// BAEKJOON: 15649_N과 M (1)
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

int N, M;
int arr[9] = { 0, };
bool visited[9] = { false, };
void input() {
	cin >> N >> M;
}

void solution(int now, int cnt) {
	if (cnt == M) {
		for (int i = 0; i < M; i++) 
			cout << arr[i] << " ";
		cout << "\n";
	}
	else {
		for (int i = now; i <= N; i++) {
			if (!visited[i]) {
				visited[i] = true;
				arr[cnt] = i;
				solution(i + 1, cnt + 1);
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
	solution(1, 0);

	return 0;
}