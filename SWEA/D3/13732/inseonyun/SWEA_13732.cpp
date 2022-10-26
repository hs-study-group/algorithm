
//////////////////////////////////////////////////
// SWEA: 13732_정사각형 판정
//////////////////////////////////////////////////

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int N;
char map[20][20] = { 0, };

bool checkSquare(vector<pair<int,int>> v) {
	int left_x = v[0].first, left_y = v[0].second;
	int right_x = v[1].first, right_y = v[1].second;

	if (right_x - left_x != right_y - left_y) 
		return false;

	for (int i = left_x; i <= right_x; i++) {
		for (int j = left_y; j <= right_y; j++) {
			if (map[i][j] != '#')
				return false;
		}
	}
	return true;
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	int TC;
	cin >> TC;

	for (int test_case = 1; test_case <= TC; test_case++) {
		cin >> N;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				cin >> map[i][j];
			}
		}
		vector<pair<int, int>> p(2);
		p[0].first = N;
		p[0].second = N;
		p[1].first = -1;
		p[1].second = -1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == '#') {
					p[0].first = min(p[0].first, i);
					p[0].second = min(p[0].second, j);
					p[1].first = max(p[1].first, i);
					p[1].second = max(p[1].second, j);
				}
			}
		}

		if (checkSquare(p))
			cout << "#" << test_case << " yes\n";
		else
			cout << "#" << test_case << " no\n";

	}

	return 0;
}