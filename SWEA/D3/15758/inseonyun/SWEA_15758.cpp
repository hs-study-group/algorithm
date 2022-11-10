
//////////////////////////////////////////////////
// SWEA : 15758_무한 문자열
//////////////////////////////////////////////////

#include <iostream>
#include <string>

using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;
	cin.get();

	for (int test_case = 1; test_case <= TC; test_case++) {
		string input;
		getline(cin, input, '\n');

		string s = input.substr(0, input.find(' '));
		string t = input.substr(input.find(' ') + 1, input.length() - 1);

		int s_size = s.length();
		int t_size = t.length();
		while (s.length() <= 50) {
			for (int i = 0; i < s_size; i++) {
				s += s[i];
			}
		}

		while (t.length() <= 50) {
			for (int i = 0; i < t_size; i++) {
				t += t[i];
			}
		}

		if (s.length() > t.length()) {
			if (s.find(t) == string::npos)
				cout << "#" << test_case << " no\n";
			else
				cout << "#" << test_case << " yes\n";

		}
		else if (s.length() < t.length()) {
			if (t.find(s) == string::npos)
				cout << "#" << test_case << " no\n";
			else
				cout << "#" << test_case << " yes\n";
		}
		else {
			if (s.compare(t) == 0)
				cout << "#" << test_case << " yes\n";
			else
				cout << "#" << test_case << " no\n";
		}

	}

	return 0;
}