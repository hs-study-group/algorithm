
//////////////////////////////////////////////////
// SWEA: 14557_카드 제거
//////////////////////////////////////////////////

#include <iostream>
#include <cstring>

using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		string S;
		cin >> S;

		int checked = 0;
		for (int i = 0; i < S.length(); i++) {
			if (S[i] == '1')
				checked++;
		}
		if (checked % 2 != 0)
			cout << "#" << test_case << " yes\n";
		else
			cout << "#" << test_case << " no\n";
	}

	return 0;
}