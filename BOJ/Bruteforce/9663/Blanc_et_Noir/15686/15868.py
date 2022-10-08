from itertools import combinations as cb

dic = {}
for i in range(1, 3):
    dic[i] = []

N, M = map(int, input().split())
for j in range(N):
    y = j + 1
    x = 1
    for e in map(int, input().split()):
        if e != 0:
            dic[e].append((x, y))
        x += 1

chiken_set = set()

for case in cb(dic[2], M):
    case_distance = 0
    for h in dic[1]:
        h_min_distance = 650
        for e in case:
            chiken_num = abs(h[0]-e[0]) + abs(h[1]-e[1])
            if chiken_num < h_min_distance:
                h_min_distance = chiken_num
        case_distance += h_min_distance
    chiken_set.add(case_distance)

print(min(chiken_set))

