
//////////////////////////////////////////////////
// PROGRAMMERS : 숫자 문자열과 영단어
//////////////////////////////////////////////////

#include <string>

using namespace std;

int solution(string s) {
    int answer = 0;
    string arr[10] = {
		"zero",
		"one",
		"two",
		"three",
		"four",
		"five",
		"six",
		"seven",
		"eight",
		"nine"
	};
    string result = "";
	string tmp = "";
    
	for(char c : s) {
		if (c >= '0' && c <= '9') {
			result += c;
			continue;
		}
		tmp += c;

		if (tmp.length() >= 3) {
			for (int i = 0; i < 10; i++) {
				if (tmp.compare(arr[i]) == 0) {
					result += to_string(i);
					tmp = "";
					break;
				}
			}
		}
	}
    answer = stoi(result);
    return answer;
}
