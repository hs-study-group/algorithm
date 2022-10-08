n , m = map(int, input().split())

board = [input() for _ in range(n)]
dp = [[0] * m for _ in range(n)] 

for i in range(m):
    dp[0][i] = int(board[0][i])
for j in range(n):
    dp[j][0] = int(board[j][0])

for r in range(1, n):
    for c in range(1, m):
        if board[r][c] == '1':
            dp[r][c] = min(dp[r][c-1], dp[r-1][c-1], dp[r-1][c]) + 1
        else:
            dp[r][c] = 0

ans = 0
for k in range(n):
    ans = max(max(dp[k]), ans)

print(ans ** 2)