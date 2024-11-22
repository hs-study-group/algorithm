#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <limits>
using namespace std;

int N, M, Q;
int MAX = 2147483647;
int MIN = -2147483648;

vector<int> tree1(400000);
vector<int> tree2(400000);
vector<int> arr1(400000);
vector<int> arr2(400000);
vector<int> result;

/* 두 노드를 병합하는 병합 메소드 */
int merge1(int v1, int v2) {
	return min(v1, v2);
}

/* 두 노드를 병합하는 병합 메소드 */
int merge2(int v1, int v2) {
	return v1 + v2;
}

/* 최솟값 세그트리를 초기화하는 초기화 메소드 */
void init1(int n, int s, int e) {
	if (s == e) {
		tree1[n] = arr1[s];
	}
	else {
		init1(n * 2, s, (s + e) / 2);
		init1(n * 2 + 1, (s + e) / 2 + 1, e);

		tree1[n] = merge1(tree1[n * 2], tree1[n * 2 + 1]);
	}
}

/* 구간합 세그트리를 초기화하는 초기화 메소드 */
void init2(int n, int s, int e) {
	if (s == e) {
		tree2[n] = arr2[s];
	}
	else {
		init2(n * 2, s, (s + e) / 2);
		init2(n * 2 + 1, (s + e) / 2 + 1, e);

		tree2[n] = merge2(tree2[n * 2], tree2[n * 2 + 1]);
	}
}

/* l ~ r 구간의 최솟값을 리턴하는 쿼리 메소드 */
int query1(int n, int s, int e, int l, int r) {
	if (s > r || e < l) {
		return MAX;
	}
	else if (l <= s && e <= r) {
		return tree1[n];
	}
	else {
		return merge1(
			query1(n * 2, s, (s + e) / 2, l, r),
			query1(n * 2 + 1, (s + e) / 2 + 1, e, l, r)
		);
	}
}

/* l ~ r 구간의 구간합을 리턴하는 쿼리 메소드 */
int query2(int n, int s, int e, int l, int r) {
	if (s > r || e < l) {
		return 0;
	}
	else if (l <= s && e <= r) {
		return tree2[n];
	}
	else {
		return merge2(
			query2(n * 2, s, (s + e) / 2, l, r),
			query2(n * 2 + 1, (s + e) / 2 + 1, e, l, r)
		);
	}
}

/* i번째 값을 v만큼 증가시키는 업데이트 메소드 */
void update2(int n, int s, int e, int i, int v) {
	if (i < s || i > e) {
		return;
	}
	else if (s == e) {
		tree2[n] = v;
	}
	else {
		update2(n * 2, s, (s + e) / 2, i, v);
		update2(n * 2 + 1, (s + e) / 2 + 1, e, i, v);

		tree2[n] = merge2(tree2[n * 2], tree2[n * 2 + 1]);
	}
}

int main(){
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	cin >> N;
	M = N - 1;

	for (int i = 0; i < N; i++) {
		int V;
		cin >> V;
		arr1[i] = V;
	}

	for (int i = 0; i < M; i++) {
		int V;
		cin >> V;
		arr2[i] = V;
	}

    /* 세그트리 초기화 */
	init1(1, 0, N-1);
	init2(1, 0, M-1);

	cin >> Q;

	for (int i = 0; i < Q; i++) {
		string O;
		int I, V;

		cin >> O >> I >> V;

		I--;

		if (O == "UPDATE") {
			update2(1, 0, M - 1, I, V);
		}
		else if (O == "CALL") {
		    /* I 위치를 기준으로 좌측으로 어디까지 갈 수 있는지 이분탐색 */
			int L = 0;
			int R = I - 1;
			int LEFT = I;

			while (L <= R) {
				int MID = (L + R) / 2;

				if (query2(1, 0, M - 1, MID, I - 1) <= V) {
					LEFT = MID;
					R = MID - 1;
				}else{
					L = MID + 1;
				}
			}

            /* I 위치를 기준으로 우측으로 어디까지 갈 수 있는지 이분탐색 */
			L = I;
			R = M - 1;
			int RIGHT = I;

			while (L <= R) {
				int MID = (L + R) / 2;

				if (query2(1, 0, M - 1, I, MID) <= V) {
					RIGHT = MID + 1;
					L = MID + 1;
				}
				else {
					R = MID - 1;
				}
			}

            /* 좌측한계지점 ~ 우측한계지점 구간의 비용의 최소값을 출력함 */
			result.push_back(query1(1, 0, N - 1, LEFT, RIGHT));
		}
	}

    /* 결과값 출력 */
	for (int i = 0; i < result.size(); i++) {
		cout << result[i] << "\n";
	}

	return 0;
}