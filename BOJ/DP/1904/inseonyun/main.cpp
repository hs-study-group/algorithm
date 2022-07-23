
//////////////////////////////////////////////////
// BAEKJOON_1904번 : 01타일
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

int N;
int* dp;
void input() {
	cin >> N;

	dp = new int[N + 1];
}

void solution() {
	dp[1] = 1 % 15746;
	dp[2] = 2 % 15746;

	for (int i = 3; i <= N; i++) {
		dp[i] = (dp[i - 2] + dp[i - 1]) % 15746;	
	}
}

void output() {
	cout << dp[N];
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