
//////////////////////////////////////////////////
// SWEA : 1859_백만장자 프로젝트
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

vector<int> v;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;
	for (int test_case = 1; test_case <= TC; test_case++) {
		long long N;
		cin >> N;
		
		v.clear();
		for (long long i = 0; i < N; i++) {
			int price;
			cin >> price;
			v.push_back(price);
		}

		int max_price = v[N - 1];
		long long res = 0;
		for (int i = N - 1; i >= 0; i--) {
			if (max_price >= v[i]) {
				res = res + (max_price - v[i]);
			}
			else {
				max_price = v[i];
			}
		}

		cout << "#" << test_case << " " << res << "\n";
	}

	return 0;
}