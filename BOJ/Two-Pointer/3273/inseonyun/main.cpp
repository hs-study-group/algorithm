#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n, x, result = 0;
vector<int> varr;

void input()
{
	cin >> n;

	for (int i = 0; i < n; i++) 
	{
		int a;
		cin >> a;

		varr.push_back(a);
	}
	
	cin >> x;
}

void solution()
{
	sort(varr.begin(), varr.end());

	int left = 0, right = n - 1;
	while (true) {
		if (left >= right) {
			break;
		}

		int sum = varr[left] + varr[right];

		if (sum == x) {
			result++;
			left++;
			right--;
		}
		else if (sum < x) {
			left++;
		}
		else {
			right--;
		}

	}
	
}

void output()
{
	cout << result;
}

int main() 
{
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	solution();
	output();

	return 0;
}