
//////////////////////////////////////////////////
// BAEKJOON: 16236_아기 상어
//////////////////////////////////////////////////

#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>
#include <cmath>

using namespace std;

// N: 맵 크기, M: 물고기 수
int map[20][20] = { 0, };
int checked[20][20] = { 0, };
int N, startX, startY, now_shark_size = 2, cost_level_up = 0, res_time = 0;
int dx[] = { -1, 0, 1, 0 };
int dy[] = { 0, -1, 0, 1 };

void input() {
	cin >> N;
	
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			cin >> map[i][j];

			if (map[i][j] == 9) {
				startX = i;
				startY = j;
			}
		}
	}
}

void solution() {
	
	while (true) {
		for (int i = 0; i < N; i++) 
			for (int j = 0; j < N; j++) 
				checked[i][j] = -1;

		checked[startX][startY] = 0;
		map[startX][startY] = 0;
		
		vector<pair<int, int>> fish;
		queue <pair<int, int>> q;
		if (cost_level_up >= now_shark_size) {
			cost_level_up -= now_shark_size;
			now_shark_size++;
		}
		q.push({ startX, startY });

		while (!q.empty()) {
			int xx = q.front().first;
			int yy = q.front().second;

			q.pop();

			for (int i = 0; i < 4; i++) {
				int nx = xx + dx[i];
				int ny = yy + dy[i];

				if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
					if (checked[nx][ny] == -1) {
						if (map[nx][ny] < now_shark_size && map[nx][ny] >= 1) {
							checked[nx][ny] = checked[xx][yy] + 1;
							fish.push_back({ nx, ny });
							q.push({ nx, ny });
						}
						else if (map[nx][ny] == now_shark_size || map[nx][ny] == 0) {
							checked[nx][ny] = checked[xx][yy] + 1;
							q.push({ nx, ny });
						}
					}
				}
			}
		}

		if (fish.size() == 0) {
			return;
		}
		else if (fish.size() == 1) {
			startX = fish[0].first;
			startY = fish[0].second;
			map[startX][startY] = 0;
			cost_level_up++;
			res_time += checked[startX][startY];
		}
		else {
			int min_cost = 1e5;
			for (pair<int, int> row : fish) {
				min_cost = min(min_cost, checked[row.first][row.second]);
			}

			vector<pair<int, int>> min_fish;
			for (pair<int, int> row : fish) {
				if (min_cost == checked[row.first][row.second])
					min_fish.push_back({ row.first, row.second });
			}
			
			sort(min_fish.begin(), min_fish.end());

			startX = min_fish[0].first;
			startY = min_fish[0].second;
			map[startX][startY] = 0;
			res_time += checked[startX][startY];
			cost_level_up++;
		}
	}


	//visited[startX][startY] = true;
	//queue<pair<int, int>> q;

	//q.push({ startX, startY });

	//while (!q.empty()) {
	//	int xx = q.front().first;
	//	int yy = q.front().second;

	//	q.pop();

	//	int targetX = 21, targetY = 21;
	//	int idx_i = -1, idx_j = -1;
	//	for (int i = 1; i < now_shark_size; i++) {
	//		for (int j = 0; j < fish[i].size(); j++) {
	//			// 가장 가까운 물고기 좌표 찾기
	//			int now_cost = abs(targetX - xx) + abs(targetY - yy);
	//			int tmp_cost = abs(fish[i][j].first - xx) + abs(fish[i][j].second - yy);

	//			if (now_cost > tmp_cost) {
	//				targetX = fish[i][j].first;
	//				targetY = fish[i][j].second;
	//				idx_i = i;
	//				idx_j = j;
	//			}
	//			else if (now_cost == tmp_cost) {
	//				if (targetX > fish[i][j].first) {
	//					targetX = fish[i][j].first;
	//					targetY = fish[i][j].second;

	//					idx_i = i;
	//					idx_j = j;

	//				}
	//				else if (targetX == fish[i][j].first) {
	//					if (targetY > fish[i][j].second) {
	//						targetY = fish[i][j].second;

	//						idx_i = i;
	//						idx_j = j;
	//					}
	//				}
	//			}
	//		}
	//		if (targetX != 21 && targetY != 21) {
	//			fish[idx_i].erase(fish[idx_i].begin() + idx_j);
	//			break;
	//		}
	//	}
	//	if (targetX == 21 && targetY == 21) {
	//		continue;
	//	}
	//	int cost = abs(targetX - xx) + abs(targetY - yy);
	//	q.push({ targetX, targetY });
	//	cost_level_up--;
	//	res_time += cost;

	//	if (cost_level_up == 0) {
	//		now_shark_size++;
	//		cost_level_up = now_shark_size;
	//	}
	//}
}

void output() {
	cout << res_time;
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);

	input();
	solution();
	output();

	return 0;
}