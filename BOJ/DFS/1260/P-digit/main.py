from collections import deque


N, M, V = map(int, input().split())

dic = {}

for n in range(1, N+1):
    dic[n] = []

for _ in range(M):
    n1, n2 = map(int, input().split())
    for _ in range(2):
        if n2 not in dic[n1]:
            dic[n1].append(n2)
        n1, n2 = n2, n1
for i in range(1, N+1):
    dic[i] = sorted(dic[i])

def bfs():
    dq = deque()
    chk = [False for _ in range(N+1)]
    dq.append(V)
    chk[V] = True
    result = [V]
    while dq:
        chk_node = dq.popleft()
        for e in dic[chk_node]:
            if not chk[e]:
                dq.append(e)
                chk[e] = True
                result.append(e)
    print(*result, sep=' ')


def dfs():
    result = []
    stk = [V]
    chk = [False for _ in range(N+1)]
    while stk:
        chk_node = stk.pop()
        if not chk[chk_node]:
            chk[chk_node] = True
            result.append(chk_node)
            for e in dic[chk_node][::-1]:
                if not chk[e]:
                    stk.append(e)
    print(*result, sep=' ')

dfs()
bfs()