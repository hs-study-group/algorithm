// https://www.acmicpc.net/problem/11657 //

#include <iostream>
#include <vector>
#include <limits.h>
#include <tuple>

using namespace std;

typedef tuple<int, int, int> edge;

vector <edge> T_edges;
vector <long long> T_dist;

int main() {

    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int N, M;
    cin >> N >> M;

    T_dist.resize(N+1, LLONG_MAX);
    T_dist[1] = 0;
    
    for(int i=0; i<M; ++i) {
        int start, end, time;
        cin >> start >> end >> time;
        edge cur_edge = make_tuple(start, end, time);
        
        T_edges.push_back(cur_edge);
    }
    
    for(int i = 0; i<N-1; ++i) {
        for(int j = 0; j < M; ++j) {
            edge cur_edge = T_edges[j];

            int start = get<0>(cur_edge);
            int end = get<1>(cur_edge);
            int time = get<2>(cur_edge);

            if(T_dist[start] != LLONG_MAX && T_dist[end] > T_dist[start] + time) {
                T_dist[end] = T_dist[start] + time;
            }
        }
    }

    bool is_ncycle = false;

    for(int i = 0; i < M; ++i) {
        edge cur_edge = T_edges[i];

        int start = get<0>(cur_edge);
        int end = get<1>(cur_edge);
        int time = get<2>(cur_edge);

        if(T_dist[start] != LLONG_MAX && T_dist[end] > T_dist[start] + time) {
            is_ncycle = true;
            break;
        }
    }

    if(!is_ncycle) {
        for(int i = 2; i <= N; ++i) {
            if(T_dist[i] == LLONG_MAX) {
                cout << "-1\n";
            }
            else {
                cout << T_dist[i] << '\n';
            }
        }
    }
    else cout << "-1\n";

    return 0;
    
}


