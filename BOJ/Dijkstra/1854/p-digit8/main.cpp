// https://www.acmicpc.net/problem/1854

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

typedef pair<int, int> edge;

vector<vector<edge>> T_node;

priority_queue<int> T_dist[1005]; 

priority_queue<edge, vector<edge>, greater<edge>> pq;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int N, M, K;
    cin >> N >> M >> K;

    T_node.resize(N + 1);
    
    for(int i = 0; i < M; ++i) {
        int from, to, weight;
        cin >> from >> to >> weight;
        T_node[from].push_back(make_pair(to, weight));
    }

    pq.push(make_pair(0, 1));
    T_dist[1].push(0);
    
    while(!pq.empty()) {
        int cur_dist = pq.top().first;
        int cur_node = pq.top().second;
        pq.pop();

        for(edge next : T_node[cur_node]) {
            int next_node = next.first;
            int next_weight = next.second;
            int next_dist = cur_dist + next_weight;

            if(T_dist[next_node].size() < K) {
                T_dist[next_node].push(next_dist);
                pq.push(make_pair(next_dist, next_node));
            } 
            else if(T_dist[next_node].top() > next_dist) {
                T_dist[next_node].pop();
                T_dist[next_node].push(next_dist);
                pq.push(make_pair(next_dist, next_node));
            }
        }
    }

    for(int i = 1; i <= N; ++i) {
        if(T_dist[i].size() != K) {
            cout << "-1\n";
        } else {
            cout << T_dist[i].top() << '\n';
        }
    }

    return 0;
}