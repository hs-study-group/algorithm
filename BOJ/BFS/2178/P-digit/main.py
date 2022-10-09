from collections import deque
import sys

input = sys.stdin.readline

rs, cs = map(int, input().strip().split())

board = [input().strip('\n') for _ in range(rs)]
dx = (1, 0, -1, 0)
dy = (0, -1, 0, 1)

def is_valid(y, x):
    return 0 <= y < rs and 0 <= x < cs

def bfs():
    chk = [[False] * cs  for _ in range(rs)] 
    chk[0][0] = True
    dq = deque()
    dq.append((0, 0, 1))
    while dq:
        y, x, d = dq.popleft()

        if y == rs-1 and x == cs-1:
            return d

        for i in range(4):
            ny = y + dy[i]
            nx = x + dx[i]
            if is_valid(ny, nx) and board[ny][nx] == "1" and not chk[ny][nx]:
                chk[ny][nx] = True
                dq.append((ny, nx, d+1))

print(bfs())