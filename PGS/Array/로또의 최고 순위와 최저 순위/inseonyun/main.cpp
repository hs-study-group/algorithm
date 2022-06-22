
//////////////////////////////////////////////////
// PROGRAMMERS : 로또의 최고 순위와 최저 순위
//////////////////////////////////////////////////

#include <vector>

using namespace std;

vector<int> solution(vector<int> lottos, vector<int> win_nums) {
    vector<int> answer;
    
    // 0~1개 맞추면 6등, 2개 5등, 3개 4등, 4개 3등, 5개 2등, 6개 1등
    int rank_arr[] = {6, 6, 5, 4, 3, 2, 1};
    
    int first_hit_count = 0;
    int zero_count = 0;
    
    for(int i = 0; i < lottos.size(); i++) {
        if(lottos[i] == 0) {
            zero_count++;
        } 
        else {
             for(int j = 0; j < win_nums.size(); j++) {
                if(win_nums[j] == lottos[i]) {
                    first_hit_count++;
                    break;
                }
            }
        }
    }
    // best
    answer.push_back(rank_arr[first_hit_count + zero_count]);        
    
    // worst
    answer.push_back(rank_arr[first_hit_count]);
    
    
    return answer;
}
