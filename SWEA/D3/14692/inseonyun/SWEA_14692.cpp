
//////////////////////////////////////////////////
// SWEA: 14692_�볪�� �ڸ���
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

		// ���� �ݺ� �� �ص� ¦���� �� ������ ����� �̱�
		if (distance % 2 == 0) {
			cout << "#" << test_case << " " << "Alice" << "\n";
		}
		else {
			cout << "#" << test_case << " " << "Bob" << "\n";
		}
			
	}

	return 0;
}