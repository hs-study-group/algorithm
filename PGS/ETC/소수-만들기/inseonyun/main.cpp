// https://school.programmers.co.kr/learn/courses/30/lessons/12977

#include <vector>
#include <algorithm>

using namespace std;

int solution(vector<int> nums) {
    int answer = 0;

    vector<int> arr;
    
    // arr에 모든 경우의 수 숫자 다 넣음
    for(int i = 0; i < nums.size(); i++) {
        for(int j = i + 1; j < nums.size(); j++) {
            for(int k = j + 1; k < nums.size(); k++) {
                arr.push_back(nums[i] + nums[j] + nums[k]);
            }
        }
    }
    
    // sorting
    sort(arr.begin(), arr.end());
    
    // arr 탐색하며 소수 발견 시 answer count
    for(int i = 0; i < arr.size(); i++) {
        bool is_prime = true;
        for(int j = 2; j < arr[i]; j++) {
            if(arr[i] % j == 0) {
                is_prime = false;
                break;
            }
        }
        if(is_prime)
            answer++;
    }

    return answer;
}