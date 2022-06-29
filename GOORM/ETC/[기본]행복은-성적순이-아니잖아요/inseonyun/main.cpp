
//////////////////////////////////////////////////
// GOORM : [기본] 행복은 성적순이 아니잖아요
//////////////////////////////////////////////////
#include <iostream>
#include <cmath>

using namespace std;

/*
*	규칙
*	시험 성적이 전체 학생 n%보다 높은 백분위
*	성적이 좋더라도 k개의 수행 평가 모두 m점보다 높아야 함
*/

int main() {
	int t;
	cin >> t;
	bool flag = true;
	for(int test_case = 0; test_case < t; test_case++) {
		int v[1000] = {0,};
		int total_student_cnt, goormi_rank, n, k, m;
		cin >> total_student_cnt >> goormi_rank >> n >> k >> m;
		
		for(int i = 0; i < k; i++) {
			cin >> v[i];
			
			if(v[i] <= m)
				flag = false;
		}
		
		// 랭크 구함
		double igeobodankeoyaham = total_student_cnt * n * 0.01;
		int igeobodankeoyaham2 = floor(igeobodankeoyaham);
		if(goormi_rank > igeobodankeoyaham2)
			flag = false;
		
		if(!flag)
			break;
		
	}
	
	if(!flag)
		cout << 0;
	else
		cout << 1;
	
	return 0;
}