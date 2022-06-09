
//////////////////////////////////////////////////
// PROGRAMMERS : 네트워크
//////////////////////////////////////////////////

#include <string>
#include <cstring>
#include <vector>

using namespace std;

bool check[200];
void DFS(vector<vector<int>> computer,int startX) {
    check[startX] = true;
    
    for(int i = startX, j = 0; j < computer.size(); j++) {
        if(i == j)
            continue;
        if(!check[j] && computer[i][j] == 1) {
            DFS(computer, j);
        }
    }
}

int solution(int n, vector<vector<int>> computers) {
    int answer = 0;
    
    memset(check, false, sizeof(check));
    
    for(int i = 0; i < n; i++) {
        if(!check[i]) {
            answer++;
            DFS(computers, i);
        }
    }
    
    return answer;
}