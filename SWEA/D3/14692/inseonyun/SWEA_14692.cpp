
//////////////////////////////////////////////////
// SWEA: 14692_통나무 자르기
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		long long distance;
		cin >> distance;

		// 굳이 반복 안 해도 짝수면 그 차례인 사람이 이김
		if (distance % 2 == 0) {
			cout << "#" << test_case << " " << "Alice" << "\n";
		}
		else {
			cout << "#" << test_case << " " << "Bob" << "\n";
		}
			
	}

	return 0;
}