
//////////////////////////////////////////////////
// PROGRAMMERS : 크레인 인형뽑기 게임
//////////////////////////////////////////////////
#include <string>
#include <vector>

using namespace std;

int solution(vector<vector<int>> board, vector<int> moves) {
    int answer = 0;
    int idx = 0;
    vector<int> basket;
    for(int i = 0; i < moves.size(); i++) {
        for(int j = 0; j < board.size(); j++) {
            if(board[j][moves[i]-1] > 0) {
                basket.push_back(board[j][moves[i]-1]);
                board[j][moves[i]-1] = 0;
                idx++;
                break;
            } else {
                continue;
            }
        }
        if(basket.size() > 1) {
            if(basket[idx-1] == basket[idx-2]) {
                basket.pop_back();
                basket.pop_back();
                idx = idx - 2;
                answer = answer +2;
            }
        }
    }
    
    return answer;
}
