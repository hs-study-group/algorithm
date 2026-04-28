// https://www.acmicpc.net/problem/1197 //

#include <iostream>
#include <queue>
#include <vector>
#include <numeric>

using namespace std;

typedef struct Edge {
    int s, e, w;

    bool operator > (const Edge& rhs) const {
        return w > rhs.w;
    }

} Edge;

void unionFunc(int a, int b);
int findFunc(int num);

vector <int> parent;
priority_queue <Edge, vector <Edge>, greater<Edge>> pq;


int main () {

    ios::sync_with_stdio(false);
    cin.tie(0); cout.tie(0);

    int V, E;
    
    cin >> V >> E;

    parent.resize(V+1);
    iota(parent.begin(), parent.end(), 0);

    for(int i = 0; i < E; ++i) {
        int s, e, w;
        cin >> s >> e >> w;

        pq.push(Edge{s, e, w});

    }

    int used_edges = 0;
    int result = 0;

    while(used_edges < V - 1) {
        Edge medge = pq.top();
        pq.pop();

        if(findFunc(medge.s) != findFunc(medge.e)) {
            unionFunc(medge.s, medge.e);
            used_edges += 1;
            result += medge.w;
        }
    }

    cout << result;
    
    return 0;
}

void unionFunc(int a, int b) {
    int parent_a = findFunc(a);
    int parent_b = findFunc(b);

    if(parent_a != parent_b) {
        int lo = parent_a < parent_b ? parent_a : parent_b;
        int hi = parent_a > parent_b ? parent_a : parent_b;

        parent[hi] = lo;
    }
}

int findFunc(int num) {
    if(parent[num] == num) return num;
    return parent[num] = findFunc(parent[num]);
}