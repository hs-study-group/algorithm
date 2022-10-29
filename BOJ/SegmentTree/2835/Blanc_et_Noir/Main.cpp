#include <stdio.h>
#include <malloc.h>

//세그먼트 트리와 lazy 배열 선언 및 0으로 초기화
long long tree[270000] = { 0, };
long long lazy[270000] = { 0, };

//타임스탬프 문자열을 입력받고 그것을 초단위로 변환하여 리턴하는 함수
int parseTime(char** p, int offset) {
	int i = 0, d = 10, t = 0, r = 0, c = 0;
	int arr[] = { 60 * 60,60,1 };

	//해당 문자열의 시작부분부터 8자리를 확인함
	for (i = 0 + offset; i < offset + 8; i++) {
		//이번에 탐색할 문자
		char ch = *(*p + i);

		//현재 탐색중인 문자가 0 ~ 9 사이의 정수라면
		if (ch >= '0' && ch <= '9') {
			//임시 결과에 자릿수를 고려하여 누적합을 구함
			t += (ch - '0') * d;

			//자릿수를 1단계 낮춤
			d /= 10;
		}
		//현재 탐색중인 문자가 정수가 아니라면
		else {
			//여태까지 누적하여 구한 누적합이 시, 분, 초중 어디에 해당하는지를 나타내는
			//c 변수를 이용하여 가중치를 곱하고, 그것을 최종결과에 누적하여 더함
			r += t * arr[c++];

			//자릿수를 다시 10으로 변경함
			d = 10;

			//임시 결과를 0으로 초기화 함
			t = 0;
		}
	}

	//반복문이 종료되면 남아있는 임시 결과를 가중치를 곱하여 더함
	r += t * arr[c++];

	//최종적으로 변환된 초단위 값을 리턴함
	return r;
}

//특정 노드에 lazy값이 있을때 이를 처리하는 함수
void process(int n, int s, int e) {
	//해당 노드에 lazy값이 있다면
	if (lazy[n] != 0) {
		//해당 노드의 값을 누적된 lazy * 구간에 포함되는 인덱스의 개수만큼 증가시킴
		tree[n] += lazy[n] * (e - s + 1);

		//리프노드가 아니라면
		if (s != e) {
			//두 자식노드에게 lazy를 전파함
			lazy[n * 2] += lazy[n];
			lazy[n * 2 + 1] += lazy[n];
		}

		//자신의 lazy를 0으로 초기화 함
		lazy[n] = 0;
	}
}

//특정 구간에서의 총 시청자 수를 구하는 쿼리 함수
long long query(int n, int s, int e, int l, int r) {
	//해당 노드에 대해 lazy값이 남아있다면 이를 처리함
	process(n, s, e);
	//탐색해야할 구간을 완전히 벗어났다면
	if (s > r || e < l) {
		//즉시 종료함
		return 0;
	}
	//탐색해야할 구간에 완전히 포함된다면
	else if (l <= s && e <= r) {
		//해당 노드의 값을 리턴함
		return tree[n];
	}
	//탐색해야할 구간에 걸치는 경우
	else {
		//두 자식 노드에 대한 쿼리를 수행한 후 그 결과값의 합을 리턴함
		return query(n * 2, s, (s + e) / 2, l, r) + query(n * 2 + 1, (s + e) / 2 + 1, e, l, r);
	}
}

//특정 구간에 대하여 모두 v만큼을 더하는 갱신 함수
void update(int n, int s, int e, int l, int r, int v) {
	//해당 노드에 대해 lazy값이 남아있다면 이를 처리함
	process(n, s, e);
	//탐색해야할 구간을 완전히 벗어났다면
	if (s > r || e < l) {
		//즉시 종료함
		return;
	}
	//탐색해야할 구간에 완전히 포함된다면
	else if (l <= s && e <= r) {
		//해당 노드의 값을 구간에 포함되는 인덱스의 개수 * v만큼 증가시킴
		tree[n] += v * (e - s + 1);

		//리프노드가 아니라면
		if (s != e) {
			//자신의 자식 노드에게도 lazy를 전파함
			lazy[n * 2] += v;
			lazy[n * 2 + 1] += v;
		}

		return;
	}
	//탐색해야할 구간에 걸치는 경우
	else {
		//두 자식노드에 대하여 재귀적으로 갱신을 수행함
		update(n * 2, s, (s + e) / 2, l, r, v);
		update(n * 2 + 1, (s + e) / 2 + 1, e, l, r, v);

		//부모노드의 값을 두 자식노드의 값의 합으로 갱신함
		tree[n] = tree[n * 2] + tree[n * 2 + 1];
		return;
	}
}

int main(void) {
	//자정은 편의상 23:59:59로 설정함, 이를 초단위로 변환
	const int MID_NIGHT = 24 * 60 * 60 - 1;

	//_msize( ) 함수를 사용할 수 없으므로, 대신 타임스탬프를 20자리로 받겠다고 상수로 선언함
	const int SIZE = 20;

	//타임스탬프를 입력받을 메모리 공간을 할당함
	//타임스탬프는 문자열 끝을 알리는 null 문자까지 포함하여 총 20자리임
	char* p = (char*)malloc(sizeof(char) * SIZE);

	//시청자 수, 쿼리의 개수, 반복문에 사용할 i변수 선언
	int N, Q, i;

	//시청자 수 N을 입력 받음
	scanf("%d", &N);
	//개행문자 제거
	getchar();

	for (i = 0; i < N; i++) {
		//시청기록을 입력 받음
		gets(p);

		//입력받은 시청 기록을 파싱하여 초단위로 변환함
		int l = parseTime(&p, 0);
		int r = parseTime(&p, 11);

		//만약 시청 시작 시각이 시청 종료 시각보다 이전이거나 동일하다면
		if (l <= r) {
			//해당 구간에 대해서 모든 노드를 1만큼 증가시킴
			update(1, 0, MID_NIGHT, l, r, 1);
		}
		//만약 시청 시작 시각이 시청 종료 시각보다 늦다면
		else {
			//시청 시작 시각부터 23:59:59까지, 00:00:00부터 시청 종료 시각까지의 구간에 대하여
			//모든 노드를 1만큼 값을 증가시킴
			update(1, 0, MID_NIGHT, l, MID_NIGHT, 1);
			update(1, 0, MID_NIGHT, 0, r, 1);
		}
	}

	//쿼리의 개수 Q를 입력 받음
	scanf("%d", &Q);
	//개행문자 제거
	getchar();

	//Q개의 쿼리에 대하여 반복적으로 쿼리를 수행함
	for (i = 0; i < Q; i++) {
		//시청기록을 입력 받음
		gets(p);

		//입력받은 시청 기록을 파싱하여 초단위로 변환함
		int l = parseTime(&p, 0);
		int r = parseTime(&p, 11);

		//만약 시청 시작 시각이 시청 종료 시각보다 이전이거나 동일하다면
		if (l <= r) {
			//해당 구간에 대하여 총 시청자 수 / 시청 구간의 크기를 구함
			printf("%.10Lf\n", (query(1, 0, MID_NIGHT, l, r) * ((long double)1.0)) / (r - l + 1));
		}
		//만약 시청 시작 시각이 시청 종료 시각보다 늦다면
		else {
			//시청 시작 시각부터 23:59:59까지, 00:00:00부터 시청 종료 시각까지의 구간에 대하여 총 시청자 수 / 시청 구간의 크기를 구함
			printf("%.10Lf\n", ((query(1, 0, MID_NIGHT, l, MID_NIGHT) + query(1, 0, MID_NIGHT, 0, r)) * ((long double)1.0)) / (MID_NIGHT - l + 1 + r - 0 + 1));
		}
	}

	//할당된 메모리를 해제함
	free(p);
	return 0;
}