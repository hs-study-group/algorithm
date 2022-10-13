N = int(input())
M = int(input())
btn = set(num for num in range(10))
def is_valid(n):
    for e in str(n):
        if int(e) not in btn:
            return False
    return True
if M:
    for e in map(int, input().split()):
        btn.remove(e)

if btn:
    btn_channel = 1000001
    for i in range(1000001):
        if is_valid(i):
            distance = abs(N-i)
            if distance > abs(btn_channel-N):
                break
            if distance < abs(N-btn_channel):
                btn_channel = i
    clicked_num = len(str(btn_channel))
    count = clicked_num + abs(N-btn_channel)
    print(count if count < abs(N-100) else abs(N-100))
else:
    print(abs(N-100))