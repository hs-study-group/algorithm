
//////////////////////////////////////////////////
// BAEKJOON: 1991_트리 순회
//////////////////////////////////////////////////

#include <iostream>
#include <vector>

using namespace std;

int N;

struct node {
	char left;
	char right;
};
vector<node> tree(26);

void input() {
	cin >> N;
	
	for (int i = 0; i < N; i++) {
		char root, left, right;
		cin >> root >> left >> right;

		tree[root - 'A'] = {left, right};
	}
}

void preOrder(char node) {
	if (node == '.')
		return;
	cout << node;
	preOrder(tree[node - 'A'].left);
	preOrder(tree[node - 'A'].right);
}

void inOrder(char node) {
	if (node == '.')
		return;
	inOrder(tree[node - 'A'].left);
	cout << node;
	inOrder(tree[node - 'A'].right);

}

void postOrder(char node) {
	if (node == '.')
		return;
	postOrder(tree[node - 'A'].left);
	postOrder(tree[node - 'A'].right);
	cout << node;
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();

	preOrder('A');
	cout << "\n";

	inOrder('A');
	cout << "\n";

	postOrder('A');

	return 0;
}