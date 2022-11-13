
//////////////////////////////////////////////////
// SWEA : 12368_24시간
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
		int A, B;
		cin >> A >> B;

		int sumTime = A + B;
		if (sumTime >= 24)
			sumTime = sumTime % 24;

		cout << "#" << test_case << " " << sumTime << "\n";
	}

	return 0;
}