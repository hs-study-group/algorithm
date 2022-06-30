
//////////////////////////////////////////////////
// BAEKJOON_9093번: 단어 뒤집기
//////////////////////////////////////////////////

#include <iostream>
#include <stack>
#include <string>

using namespace std;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(0);
    cout.tie(0);

    int test_case;
    cin >> test_case;
    cin.ignore();

    for (int tc = 0; tc < test_case; tc++) {
        string str;
        getline(cin, str);
        string result = "";

        stack<char> stack;
        for (int i = 0; i <= str.length(); i++)
        {
            if (str[i] == ' ' || str[i] == NULL)
            {
                while (!stack.empty())
                {
                    result += stack.top();
                    stack.pop();
                }
                if (i != str.length())
                    result += ' ';
                continue;
            }
            stack.push(str[i]);
        }
        cout << result << "\n";
    }
    
    return 0;
}
