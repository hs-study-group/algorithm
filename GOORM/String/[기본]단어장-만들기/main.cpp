
//////////////////////////////////////////////////
// GOORM : [기본] 단어장 만들기
//////////////////////////////////////////////////
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool comp(string a, string b) {
	if (a.length() == b.length()) {
		return a < b;
	}
	return a.length() < b.length();
}

int main() {
	int n, target;
	cin >> n >> target;

	string result = "";
	vector<string> string_arr;
	for (int i = 1; i <= n; i++) {
		string str;
		cin >> str;

		string_arr.push_back(str);
	}
	sort(string_arr.begin(), string_arr.end(), comp);
	result = string_arr[target - 1];

	cout << result;
	return 0;
}