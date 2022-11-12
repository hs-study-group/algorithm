
//////////////////////////////////////////////////
// SWEA : 14178_1차원 정원
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
		int N, D;
		cin >> N >> D;

		int range = D * 2 + 1;

		if (N % range == 0) {
			cout << "#" << test_case << " " << N / range << "\n";
		}
		else {
			cout << "#" << test_case << " " << N / range + 1 << "\n";
		}


	}

	return 0;
}