
//////////////////////////////////////////////////
// SWEA : 15230_알파벳 공부
//////////////////////////////////////////////////

#include <iostream>
#include <cstring>

using namespace std;

string alphabet = "abcdefghijklmnopqrstuvwxyz";

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		string str;
		cin >> str;

		int res = 0;
		for (int i = 0; i < 26; i++) {
			if (alphabet[i] == str[i]) {
				res++;
			}
			else
				break;
		}
		cout << "#" << test_case << " " << res << "\n";
	}

	return 0;
}