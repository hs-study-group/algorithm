
//////////////////////////////////////////////////
// GOORM : [00증권] 주식 투자자 A
//////////////////////////////////////////////////
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

bool comp(pair<int, double> a, pair<int, double> b) {
	if (a.second == b.second)
		return a.first < b.first;

	return a.second > b.second;
}

int main() {
	int jusik_cnt;
	cin >> jusik_cnt;
	vector<pair<int, double>> score;
	for (int i = 0; i < jusik_cnt; i++) {
		double price;
		int cnt;
		cin >> price >> cnt;

		double tmp = price * cnt;
		tmp = floor(tmp * 10.f) / 10.f;

		score.push_back({ i + 1, tmp });
	}
	sort(score.begin(), score.end(), comp);

	for (int i = 0; i < score.size(); i++) {
		cout << score[i].first;
		if (i == score.size() - 1)
			break;
		cout << " ";
	}

	return 0;
}