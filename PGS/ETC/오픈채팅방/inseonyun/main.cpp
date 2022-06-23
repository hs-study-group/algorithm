
//////////////////////////////////////////////////
// PROGRAMMERS : 오픈채팅방
//////////////////////////////////////////////////

#include <string>
#include <vector>
#include <map>

using namespace std;

vector<string> string_split(string str) {
    vector<string> result;

    int find_space = str.find(' ');
    string status = str.substr(0, find_space);
    str = str.erase(0, find_space + 1);
    find_space = str.find(' ');
    string user_id = str.substr(0, find_space);
    str = str.erase(0, find_space + 1);
    string user_nickname = str;

    result.push_back(user_id);
    result.push_back(user_nickname);
    result.push_back(status);

    return result;
}

vector<string> solution(vector<string> record) {
    vector<string> answer;

    // first : user_id, second : Enter or Leave
    vector<pair<string, string>> user_status;

    // key : user_id, value : user_nickname
    map<string, string> user_info;

    for (int i = 0; i < record.size(); i++) {
        vector<string> row = string_split(record[i]);
        string row_id = row[0];
        string row_nickname = row[1];
        string row_status = row[2];

        if (row_status.compare("Enter") == 0) {
            user_info[row_id] = row_nickname;
        }
        else if (row_status.compare("Leave") == 0) {
            // find user_nickname
            row_nickname = user_info[row_id];
        }
        else {
            // change user_nickname
            user_info[row_id] = row_nickname;
            continue;
        }
        user_status.push_back({ row_id, row_status });
    }

    for (int i = 0; i < user_status.size(); i++) {
        string user_nickname = user_info[user_status[i].first];
        string user_inout = user_status[i].second;

        if (user_inout.compare("Enter") == 0) {
            answer.push_back(user_nickname + "님이 들어왔습니다.");
        }
        else {
            answer.push_back(user_nickname + "님이 나갔습니다.");
        }
    }

    return answer;
}