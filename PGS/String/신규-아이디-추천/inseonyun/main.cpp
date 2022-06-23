
//////////////////////////////////////////////////
// PROGRAMMERS : 신규 아이디 추천
//////////////////////////////////////////////////

#include <string>

using namespace std;

string solution(string new_id) {
    string answer = "";

    int dot_flag = 0;
    for (char c : new_id) {
        char ch = tolower(c);

        if (!((ch >= 'a' && ch <= 'z') || (ch == '.' || ch == '-' || ch == '_') || (ch >= '0' && ch <= '9')))
            continue;

        if ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-' || ch == '_')) {
            answer += ch;
            dot_flag = 0;
            continue;
        }
        if (ch == '.') {
            dot_flag++;

            if (dot_flag >= 2) {
                dot_flag -= 1;
                continue;
            }
            answer += '.';
        }
    }
    if (answer[0] == '.')
        answer.erase(0, 1);

    if (answer.length() > 15)
        answer = answer.substr(0, 15);

    if (answer.length() > 0) {
        if (answer[answer.length() - 1] == '.')
            answer.erase(answer.length() - 1, 1);
    }

    if (answer.compare("") == 0)
        answer = "aaa";

    if (answer.length() < 3) {
        char ch = answer[answer.length() - 1];
        while (answer.length() < 3) {
            answer += ch;
        }
    }


    return answer;
}