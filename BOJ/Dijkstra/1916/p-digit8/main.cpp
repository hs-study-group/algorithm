// https://www.acmicpc.net/problem/1916

#include <iostream>
#include <vector>
#include <queue>
#include <limits.h>

using namespace std;

typedef pair<int, int> edge;

vector <vector <edge>> T_node;
vector <int> T_cost;
priority_queue <edge, vector<edge>, greater<edge>> pq;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    // input
    int N, M;
    cin >> N >> M;

    T_node.resize(N+1);
    T_cost.resize(N+1, INT_MAX);

    for(int i=0; i<M; ++i) {
        int from, to, cost;

        cin >> from >> to >> cost;
        T_node[from].push_back(make_pair(cost, to));
    }

    int start, end;
    cin >> start >> end;
    T_cost[start] = 0;
    pq.push(make_pair(0, start));

    while(!pq.empty()) {
        int cur_cost = pq.top().first;
        int cur_node = pq.top().second;
        pq.pop();

        if(cur_cost > T_cost[cur_node]) continue;

        for(edge next : T_node[cur_node]) {
            int weight = next.first;
            int next_node = next.second;

            int next_cost = cur_cost + weight;

            if(next_cost < T_cost[next_node]) {
                T_cost[next_node] = next_cost;
                pq.push(make_pair(next_cost, next_node));
            }
        }
    }

    cout << T_cost[end];

    return 0;

    
}