// https://school.programmers.co.kr/learn/courses/30/lessons/12906

#include <vector>
#include <algorithm>

using namespace std;

vector<int> solution(vector<int> arr) 
{
    vector<int> answer;
    
    // arr.erase(unique(arr.begin(), arr.end()), arr.end());
    // answer = arr;
    
    int i = 1, tmp = arr[0], a_size = arr.size();
    answer.push_back(arr[0]);
    while(a_size > i) {
        if(tmp != arr[i]) {
            answer.push_back(arr[i]);
            tmp = arr[i];
        }
        i++;
    }
    
    return answer;
}