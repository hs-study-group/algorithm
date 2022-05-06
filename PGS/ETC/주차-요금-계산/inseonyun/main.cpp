
//////////////////////////////////////////////////
// PROGRAMMERS : 주차 요금 계산
//////////////////////////////////////////////////

#include <string>
#include <vector>
#include <map>

using namespace std;

vector<string> parse(string str) {
    vector<string> result;
    
    string tmp = str;
	int first_space = tmp.find(' ');
	string time = tmp.substr(0, first_space);

	tmp = tmp.erase(0, first_space + 1);

	int second_space = tmp.find(' ');
	string car_number = tmp.substr(0, second_space);

	string inout = tmp.substr(second_space + 1, tmp.length() - 1);
    
    result.push_back(time);
    result.push_back(car_number);
    result.push_back(inout);
    
    return result;
}

vector<int> time_parse(string str) {
	vector<int> result;

	string hour, min;

	int f = str.find(':');

	hour = str.substr(0, f);
	min = str.substr(f + 1, str.length() - 1);

	int h = stoi(hour);
	int m = stoi(min);

	result.push_back(h);
	result.push_back(m);
	
	return result;
}

vector<int> solution(vector<int> fees, vector<string> records) {
    vector<int> answer;
    
    vector<pair<string, string>> in_record;
	vector<pair<string, string>> out_record;
	
	map<string, int> total_time_record;

	for (int i = 0; i < records.size(); i++) {
		vector<string> row = parse(records[i]);

		if (row[2].compare("IN") == 0) {
			in_record.push_back({ row[1], row[0] });
		}
		else {
			out_record.push_back({ row[1], row[0] });
		}
	}

	for (int i = 0; i < in_record.size(); i++) {
		// out_record에서 in_record 찾음
		int idx = -1;
		int out_recordSize = out_record.size();
		for (int j = 0; j < out_recordSize; j++) {
			if (in_record[i].first.compare(out_record[j].first) ==0) {
				idx = j;
				break;
			}
		}

		int h, m;
		if (idx == -1) {
			// 출차 기록이 없다는 것
			// 출차시간 11:59로 계산하면 됨.
			string in_time_str = in_record[i].second;
			vector<int> in_time = time_parse(in_time_str);
			int out_hour = 23;
			int out_min = 59;
			
			h = out_hour - in_time[0];
			m = out_min - in_time[1];
		}
		else {
			// 출차 기록이 있는 것
			// 출차 시간 - 입차시간 후 요금 계산
			string in_time_str = in_record[i].second;
			string out_time_str = out_record[idx].second;

			vector<int> in_time = time_parse(in_time_str);
			vector<int> out_time = time_parse(out_time_str);

			if (out_time[1] - in_time[1] < 0) {
				int tmp = 60 - in_time[1];
				m = tmp + out_time[1];
				h = out_time[0] - in_time[0] - 1;
			}
			else {
				h = out_time[0] - in_time[0];
				m = out_time[1] - in_time[1];
			}
			out_record.erase(out_record.begin() + idx);
		}
		int total_use_time = (h * 60) + m;
		total_time_record[in_record[i].first] += total_use_time;
		
	}
	for (pair<string, int> i : total_time_record) {
		int time = i.second;
		int total_use_cost = 0;

		if (time <= fees[0]) {
			//기본 요금만 부과
			total_use_cost += fees[1];
		}
		else {
			// 기본 요금 부과 후, 추가 요금 부과
			time = time - fees[0];
			total_use_cost += fees[1];

			int mul = time / fees[2];
			if (mul == 0) {
				total_use_cost += fees[3];
			}
			else {
				total_use_cost += mul * fees[3];
				if (time % fees[2] != 0) {
					total_use_cost += fees[3];
				}
			}
		}
		answer.push_back(total_use_cost);
	}
    return answer;
}