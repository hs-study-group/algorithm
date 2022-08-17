
//////////////////////////////////////////////////
// PROGRAMMERS : 최소직사각형
//////////////////////////////////////////////////

#include <vector>

using namespace std;

int solution(vector<vector<int>> sizes) {
    int answer = 0;
    
    int max_row = 0;
    int max_col = 0;
    
    for(int i = 0; i < sizes.size(); i ++) {
        if(sizes[i][0] < sizes[i][1]) {
            int tmp = sizes[i][1];
            sizes[i][1] = sizes[i][0];
            sizes[i][0] = tmp;
        }
        if(max_row < sizes[i][0])
            max_row = sizes[i][0];
        if(max_col < sizes[i][1])
            max_col = sizes[i][1];
    }
    
    answer = max_row * max_col;
    
    return answer;
}