
//////////////////////////////////////////////////
// BAEKJOON: 5639_이진 검색 트리
//////////////////////////////////////////////////

#include <iostream>

using namespace std;

long long tree[10000];
int tree_idx = 0;

void input() {
	long long node;
	while (cin >> node) {
		if (cin.eof() == true) break;

		tree[tree_idx] = node;
		tree_idx++;
	}
}

void postOrder(int start, int end) {
	if (start >= end)
		return;
	if (start == end - 1) {
		cout << tree[start] << "\n";
		return;
	}
	int idx = start + 1;

	while (idx < end) {
		if (tree[start] < tree[idx]) {
			break;
		}
		idx++;
	}

	postOrder(start + 1, idx);
	postOrder(idx, end);
	cout << tree[start] << "\n";
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	postOrder(0, tree_idx);

	return 0;
}